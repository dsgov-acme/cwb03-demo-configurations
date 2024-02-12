# Demo Configuration

This repository hosts work-manager configuration for DSGov demo projects. This
configuration scheme supports management of multiple concurrent configuration
sets.

## Configuration Files

Configuration files are located under `./data` and are organized into the following directory structure:

```
./data
    |- <configuration-set>
        |- schema
        |- transaction
        |- workflow
        |- notification
            |- email-layout
            |- message-template
    |- <other-configuration-set>
        |- schema
        |- transaction
        |- workflow
        |- notification
            |- email-layout
            |- message-template
```

Directories under each configuration set hold the following configurations:
 - **schema**: Dynamic entity schema as YAML files
   - Schema files will be named `{key}.yaml`
 - **transaction**: Transaction Definitions and Form Configurations as YAML files
   - Transaction Definition files will be named `{key}.yaml`
   - Form Configuration will be named `{parentTransactionDefintionKey}-form-{formKey}.yaml`
 - **workflow**: Camunda workflow and decision table file.
   - Workflow files will be named `{key}.bpmn`
   - Decision Table files will be named `{key}.dmn`
 - **notification/email-layout**: Email layouts for notifications. 
   - Email layout will be named `{key}.yaml`
 - **notification/message-template**: Message templates for notifications.
    - Message templates will be named `{key}.yaml`

## Configuration Deployment

To deploy a set of configuration to an environment, run the `./deployer` script in the root the repository.

Usage (run `./deployer` with no arguments to produce this message)

```
usage: deployer <CONFIG-SET> <ENVIRONMENT> [-k <KEY>] [-t <TYPE>]
Deploys a set of configuration to a DSGov environment.

Arguments:
    <CONFIG-SET>  Name of the configuration set present in the local
                  config directory.
    <ENVIRONMENT> Name of environment to deploy configuration to.
                  Supported values include:
                      local Local environment running in
                            developer's minikube.
                      dev   GCP hosted dev environment.


Options:
 -k,--key <KEY>            Key of specific configuration to deploy.
                           Requires config-type to be specified.
 -t,--config-type <TYPE>   Type of configuration to deploy. Values:
                           schema, workflow, transaction, email-layout, message-template
```

Environments are configured in [this file](./configuration-deployer/src/main/resources/deployer-config.yaml)

## Work Manager Client Maintenance

Can be updated by copying the swagger docs from the work-manager service into `./work-manager-client/swagger.yaml` and building this project.

## Notification Service Client Maintenance

The notification service admin client can be rebuilt from the swagger docs hosted by notification service
by running the following gradle task:

```shell
./gradlew updateNotificationServiceClient
```
By default this will point to the dev environment notification service, but the docs url can be
overridden with a flag like this:

```shell
./gradlew updateNotificationServiceClient -Pwork-manager.api-docs.url=http://api.dsgov.test/ns/v3/api-docs
```