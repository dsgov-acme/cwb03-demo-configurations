/*
 * Notification Service
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.nuvalence.config.notification.client.generated.models;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;
import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * MessageResponseModel
 */
@JsonPropertyOrder({
  MessageResponseModel.JSON_PROPERTY_ID,
  MessageResponseModel.JSON_PROPERTY_USER_ID,
  MessageResponseModel.JSON_PROPERTY_TEMPLATE_KEY,
  MessageResponseModel.JSON_PROPERTY_PARAMETERS,
  MessageResponseModel.JSON_PROPERTY_STATUS,
  MessageResponseModel.JSON_PROPERTY_REQUESTED_TIMESTAMP,
  MessageResponseModel.JSON_PROPERTY_SENT_TIMESTAMP
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-07-18T18:20:35.808139-05:00[America/Bogota]")
public class MessageResponseModel {
  public static final String JSON_PROPERTY_ID = "id";
  private UUID id;

  public static final String JSON_PROPERTY_USER_ID = "userId";
  private UUID userId;

  public static final String JSON_PROPERTY_TEMPLATE_KEY = "templateKey";
  private String templateKey;

  public static final String JSON_PROPERTY_PARAMETERS = "parameters";
  private Map<String, String> parameters = new HashMap<>();

  public static final String JSON_PROPERTY_STATUS = "status";
  private String status;

  public static final String JSON_PROPERTY_REQUESTED_TIMESTAMP = "requestedTimestamp";
  private OffsetDateTime requestedTimestamp;

  public static final String JSON_PROPERTY_SENT_TIMESTAMP = "sentTimestamp";
  private OffsetDateTime sentTimestamp;

  public MessageResponseModel() { 
  }

  public MessageResponseModel id(UUID id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public UUID getId() {
    return id;
  }


  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setId(UUID id) {
    this.id = id;
  }


  public MessageResponseModel userId(UUID userId) {
    this.userId = userId;
    return this;
  }

   /**
   * Get userId
   * @return userId
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public UUID getUserId() {
    return userId;
  }


  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setUserId(UUID userId) {
    this.userId = userId;
  }


  public MessageResponseModel templateKey(String templateKey) {
    this.templateKey = templateKey;
    return this;
  }

   /**
   * Get templateKey
   * @return templateKey
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TEMPLATE_KEY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getTemplateKey() {
    return templateKey;
  }


  @JsonProperty(JSON_PROPERTY_TEMPLATE_KEY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTemplateKey(String templateKey) {
    this.templateKey = templateKey;
  }


  public MessageResponseModel parameters(Map<String, String> parameters) {
    this.parameters = parameters;
    return this;
  }

  public MessageResponseModel putParametersItem(String key, String parametersItem) {
    this.parameters.put(key, parametersItem);
    return this;
  }

   /**
   * Get parameters
   * @return parameters
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PARAMETERS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Map<String, String> getParameters() {
    return parameters;
  }


  @JsonProperty(JSON_PROPERTY_PARAMETERS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setParameters(Map<String, String> parameters) {
    this.parameters = parameters;
  }


  public MessageResponseModel status(String status) {
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getStatus() {
    return status;
  }


  @JsonProperty(JSON_PROPERTY_STATUS)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setStatus(String status) {
    this.status = status;
  }


  public MessageResponseModel requestedTimestamp(OffsetDateTime requestedTimestamp) {
    this.requestedTimestamp = requestedTimestamp;
    return this;
  }

   /**
   * Get requestedTimestamp
   * @return requestedTimestamp
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_REQUESTED_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getRequestedTimestamp() {
    return requestedTimestamp;
  }


  @JsonProperty(JSON_PROPERTY_REQUESTED_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setRequestedTimestamp(OffsetDateTime requestedTimestamp) {
    this.requestedTimestamp = requestedTimestamp;
  }


  public MessageResponseModel sentTimestamp(OffsetDateTime sentTimestamp) {
    this.sentTimestamp = sentTimestamp;
    return this;
  }

   /**
   * Get sentTimestamp
   * @return sentTimestamp
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SENT_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public OffsetDateTime getSentTimestamp() {
    return sentTimestamp;
  }


  @JsonProperty(JSON_PROPERTY_SENT_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSentTimestamp(OffsetDateTime sentTimestamp) {
    this.sentTimestamp = sentTimestamp;
  }


  /**
   * Return true if this MessageResponseModel object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageResponseModel messageResponseModel = (MessageResponseModel) o;
    return Objects.equals(this.id, messageResponseModel.id) &&
        Objects.equals(this.userId, messageResponseModel.userId) &&
        Objects.equals(this.templateKey, messageResponseModel.templateKey) &&
        Objects.equals(this.parameters, messageResponseModel.parameters) &&
        Objects.equals(this.status, messageResponseModel.status) &&
        Objects.equals(this.requestedTimestamp, messageResponseModel.requestedTimestamp) &&
        Objects.equals(this.sentTimestamp, messageResponseModel.sentTimestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, templateKey, parameters, status, requestedTimestamp, sentTimestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageResponseModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    templateKey: ").append(toIndentedString(templateKey)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    requestedTimestamp: ").append(toIndentedString(requestedTimestamp)).append("\n");
    sb.append("    sentTimestamp: ").append(toIndentedString(sentTimestamp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Convert the instance into URL query string.
   *
   * @return URL query string
   */
  public String toUrlQueryString() {
    return toUrlQueryString(null);
  }

  /**
   * Convert the instance into URL query string.
   *
   * @param prefix prefix of the query string
   * @return URL query string
   */
  public String toUrlQueryString(String prefix) {
    String suffix = "";
    String containerSuffix = "";
    String containerPrefix = "";
    if (prefix == null) {
      // style=form, explode=true, e.g. /pet?name=cat&type=manx
      prefix = "";
    } else {
      // deepObject style e.g. /pet?id[name]=cat&id[type]=manx
      prefix = prefix + "[";
      suffix = "]";
      containerSuffix = "]";
      containerPrefix = "[";
    }

    StringJoiner joiner = new StringJoiner("&");

    // add `id` to the URL query string
    if (getId() != null) {
      joiner.add(String.format("%sid%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getId()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `userId` to the URL query string
    if (getUserId() != null) {
      joiner.add(String.format("%suserId%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getUserId()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `templateKey` to the URL query string
    if (getTemplateKey() != null) {
      joiner.add(String.format("%stemplateKey%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getTemplateKey()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `parameters` to the URL query string
    if (getParameters() != null) {
      for (String _key : getParameters().keySet()) {
        joiner.add(String.format("%sparameters%s%s=%s", prefix, suffix,
            "".equals(suffix) ? "" : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
            getParameters().get(_key), URLEncoder.encode(String.valueOf(getParameters().get(_key)), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
      }
    }

    // add `status` to the URL query string
    if (getStatus() != null) {
      joiner.add(String.format("%sstatus%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getStatus()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `requestedTimestamp` to the URL query string
    if (getRequestedTimestamp() != null) {
      joiner.add(String.format("%srequestedTimestamp%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getRequestedTimestamp()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `sentTimestamp` to the URL query string
    if (getSentTimestamp() != null) {
      joiner.add(String.format("%ssentTimestamp%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getSentTimestamp()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    return joiner.toString();
  }
}

