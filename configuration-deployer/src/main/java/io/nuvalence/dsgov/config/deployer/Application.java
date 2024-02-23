package io.nuvalence.dsgov.config.deployer;

import io.nuvalence.dsgov.config.deployer.util.JacocoIgnoreInGeneratedReport;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * Implements Command Line interface to config Deployer.
 */
@SuppressWarnings("systemout")
@JacocoIgnoreInGeneratedReport(
        reason =
                "Poor fit for unit tests do to use of System.exit() and triggering Spring context initialization.")
public class Application {
    private static final Options CLI_OPTIONS =
            new Options()
                    .addOption(
                            Option.builder()
                                    .option("t")
                                    .longOpt("config-type")
                                    .desc(
                                            "Type of configuration to deploy. Values: schema, workflow, "
                                                    + "transaction, email-layout or message-template")
                                    .hasArg()
                                    .argName("TYPE")
                                    .build())
                    .addOption(
                            Option.builder()
                                    .option("k")
                                    .longOpt("key")
                                    .desc(
                                            "Key of specific configuration to deploy. Requires config-type to be "
                                                    + "specified.")
                                    .hasArg()
                                    .argName("KEY")
                                    .build());
    private static final List<String> KNOWN_CONFIG_TYPES =
            Arrays.asList(
                    "schema",
                    "workflow",
                    "transaction",
                    "email-layout",
                    "message-template",
                    "transaction-definition-set",
                    "transaction-definition-set-order",
                    "record-definition");

    /**
     * Java main method.
     *
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        final ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfiguration.class);
        final CommandLine cmd = getCommandLine(args);
        validateArguments(cmd);

        final String configSet = cmd.getArgList().get(0);
        final String environment = cmd.getArgList().get(1);
        final String configType = cmd.getOptionValue("t");
        final String configKey = cmd.getOptionValue("k");

        final Deployer deployer = context.getBean(Deployer.class);

        if (configKey != null) {
            deployer.deploySingle(configSet, environment, configType, configKey);
        } else if (configType != null) {
            deployer.deployType(configSet, environment, configType);
        } else {
            deployer.deployAll(configSet, environment);
        }
    }

    @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
    private static void validateArguments(CommandLine cmd) {
        if (cmd.getArgList().size() != 2) {
            printErrorAndExit("Missing Required arguments");
        }

        if (cmd.hasOption("k") && !cmd.hasOption("t")) {
            printErrorAndExit(
                    "Deploying a single config by KEY requires specifying a TYPE (-t option)");
        }

        if (cmd.hasOption("t") && !KNOWN_CONFIG_TYPES.contains(cmd.getOptionValue("t"))) {
            printErrorAndExit("Unknown TYPE: " + cmd.getOptionValue("t"));
        }
    }

    @SuppressWarnings("PMD.DoNotCallSystemExit")
    private static void printErrorAndExit(final String errorMessage) {
        System.err.println();
        System.err.println("    " + errorMessage);
        System.err.println();
        printUsage();
        System.exit(1);
    }

    private static CommandLine getCommandLine(final String[] args) {
        final CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(CLI_OPTIONS, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printUsage() {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(
                "deployer <CONFIG-SET> <ENVIRONMENT>",
                String.join(
                        "\n",
                        "Deploys a set of configuration to a DSGov environment.",
                        "",
                        "Arguments:",
                        "    <CONFIG-SET>  Name of the configuration set present in the local",
                        "                  config directory.",
                        "    <ENVIRONMENT> Name of environment to deploy configuration to.",
                        "                  Supported values include:",
                        "                      local Local environment running in ",
                        "                            developer's minikube.",
                        "                      dev   GCP hosted dev environment.",
                        "                      demo  GCP hosted demo environment.",
                        "\n",
                        "Options:"),
                CLI_OPTIONS,
                "",
                true);
    }
}
