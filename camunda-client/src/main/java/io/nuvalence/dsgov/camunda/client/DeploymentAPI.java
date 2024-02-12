package io.nuvalence.dsgov.camunda.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.function.Consumer;

@AllArgsConstructor
public class DeploymentAPI {

    private final String memberVarBaseUri;
    private final Consumer<HttpPost> authInterceptior;

    /**
     * Creates a deployment.  **Security Consideration**  Deployments can contain custom code in form of scripts or EL expressions to customize process behavior. This may be abused for remote execution of arbitrary code.
     * @param deployChangedOnly A flag indicating whether the process engine should perform duplicate checking on a per-resource basis. If set to true, only those resources that have actually changed are deployed. Checks are made against resources included previous deployments of the same name and only against the latest versions of those resources. If set to true, the option enable-duplicate-filtering is overridden and set to true. (optional, default to false)
     * @param enableDuplicateFiltering A flag indicating whether the process engine should perform duplicate checking for the deployment or not. This allows you to check if a deployment with the same name and the same resouces already exists and if true, not create a new deployment but instead return the existing deployment. The default value is false. (optional, default to false)
     * @param deploymentName The name for the deployment to be created. (optional)
     * @param deploymentActivationTime Sets the date on which the process definitions contained in this deployment will be activated. This means that all process definitions will be deployed as usual, but they will be suspended from the start until the given activation date. By [default](https://docs.camunda.org/manual/7.17/reference/rest/overview/date-format/), the date must have the format &#x60;yyyy-MM-dd&#39;T&#39;HH:mm:ss.SSSZ&#x60;, e.g., &#x60;2013-01-23T14:42:45.000+0200&#x60;. (optional)
     * @param data The binary data to create the deployment resource. It is possible to have more than one form part with different form part names for the binary data to create a deployment. (optional)
     * @throws ApiException
     */
    public void createDeployment(Boolean deployChangedOnly, Boolean enableDuplicateFiltering, String deploymentName, OffsetDateTime deploymentActivationTime, File data) throws ApiException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(URI.create(memberVarBaseUri + "/deployment/create"));

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.addTextBody("deployment-source", "configuration-deployer");
        entityBuilder.addTextBody("deploy-changed-only", deployChangedOnly.toString());
        entityBuilder.addTextBody("enable-duplicate-filtering", enableDuplicateFiltering.toString());
        entityBuilder.addTextBody("deployment-name", deploymentName);
        entityBuilder.addTextBody("deployment-activation-time", deploymentActivationTime.toString());
        entityBuilder.addPart("data", new FileBody(data, ContentType.DEFAULT_BINARY));

        HttpEntity entity = entityBuilder.build();
        httpPost.setEntity(entity);

        if (authInterceptior != null) {
            authInterceptior.accept(httpPost);
        }

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            var j = new ObjectMapper().readTree(response.getEntity().getContent());
            System.out.println(j.toPrettyString());
            if (statusCode != 200) {
                throw new ApiException("HTTP request failed with status code: " + statusCode);
            }
        } catch (IOException e) {
            throw new ApiException("Failed to send the HTTP request: " + e.getMessage());
        }
    }
}
