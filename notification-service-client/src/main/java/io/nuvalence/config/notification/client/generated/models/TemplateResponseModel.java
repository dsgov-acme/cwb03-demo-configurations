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
import io.nuvalence.config.notification.client.generated.models.EmailFormatModel;
import io.nuvalence.config.notification.client.generated.models.TemplateRequestModelSmsFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * TemplateResponseModel
 */
@JsonPropertyOrder({
  TemplateResponseModel.JSON_PROPERTY_ID,
  TemplateResponseModel.JSON_PROPERTY_KEY,
  TemplateResponseModel.JSON_PROPERTY_VERSION,
  TemplateResponseModel.JSON_PROPERTY_STATUS,
  TemplateResponseModel.JSON_PROPERTY_NAME,
  TemplateResponseModel.JSON_PROPERTY_DESCRIPTION,
  TemplateResponseModel.JSON_PROPERTY_PARAMETERS,
  TemplateResponseModel.JSON_PROPERTY_EMAIL_FORMAT,
  TemplateResponseModel.JSON_PROPERTY_SMS_FORMAT,
  TemplateResponseModel.JSON_PROPERTY_CREATED_BY
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-07-18T18:20:35.808139-05:00[America/Bogota]")
public class TemplateResponseModel {
  public static final String JSON_PROPERTY_ID = "id";
  private UUID id;

  public static final String JSON_PROPERTY_KEY = "key";
  private String key;

  public static final String JSON_PROPERTY_VERSION = "version";
  private Integer version;

  public static final String JSON_PROPERTY_STATUS = "status";
  private String status;

  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_DESCRIPTION = "description";
  private String description;

  public static final String JSON_PROPERTY_PARAMETERS = "parameters";
  private Map<String, String> parameters = new HashMap<>();

  public static final String JSON_PROPERTY_EMAIL_FORMAT = "emailFormat";
  private EmailFormatModel emailFormat;

  public static final String JSON_PROPERTY_SMS_FORMAT = "smsFormat";
  private TemplateRequestModelSmsFormat smsFormat;

  public static final String JSON_PROPERTY_CREATED_BY = "createdBy";
  private String createdBy;

  public TemplateResponseModel() { 
  }

  public TemplateResponseModel id(UUID id) {
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


  public TemplateResponseModel key(String key) {
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_KEY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getKey() {
    return key;
  }


  @JsonProperty(JSON_PROPERTY_KEY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setKey(String key) {
    this.key = key;
  }


  public TemplateResponseModel version(Integer version) {
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getVersion() {
    return version;
  }


  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setVersion(Integer version) {
    this.version = version;
  }


  public TemplateResponseModel status(String status) {
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


  public TemplateResponseModel name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setName(String name) {
    this.name = name;
  }


  public TemplateResponseModel description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_DESCRIPTION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getDescription() {
    return description;
  }


  @JsonProperty(JSON_PROPERTY_DESCRIPTION)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setDescription(String description) {
    this.description = description;
  }


  public TemplateResponseModel parameters(Map<String, String> parameters) {
    this.parameters = parameters;
    return this;
  }

  public TemplateResponseModel putParametersItem(String key, String parametersItem) {
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


  public TemplateResponseModel emailFormat(EmailFormatModel emailFormat) {
    this.emailFormat = emailFormat;
    return this;
  }

   /**
   * Get emailFormat
   * @return emailFormat
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_EMAIL_FORMAT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public EmailFormatModel getEmailFormat() {
    return emailFormat;
  }


  @JsonProperty(JSON_PROPERTY_EMAIL_FORMAT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setEmailFormat(EmailFormatModel emailFormat) {
    this.emailFormat = emailFormat;
  }


  public TemplateResponseModel smsFormat(TemplateRequestModelSmsFormat smsFormat) {
    this.smsFormat = smsFormat;
    return this;
  }

   /**
   * Get smsFormat
   * @return smsFormat
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_SMS_FORMAT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public TemplateRequestModelSmsFormat getSmsFormat() {
    return smsFormat;
  }


  @JsonProperty(JSON_PROPERTY_SMS_FORMAT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setSmsFormat(TemplateRequestModelSmsFormat smsFormat) {
    this.smsFormat = smsFormat;
  }


  public TemplateResponseModel createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

   /**
   * Get createdBy
   * @return createdBy
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_CREATED_BY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getCreatedBy() {
    return createdBy;
  }


  @JsonProperty(JSON_PROPERTY_CREATED_BY)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }


  /**
   * Return true if this TemplateResponseModel object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateResponseModel templateResponseModel = (TemplateResponseModel) o;
    return Objects.equals(this.id, templateResponseModel.id) &&
        Objects.equals(this.key, templateResponseModel.key) &&
        Objects.equals(this.version, templateResponseModel.version) &&
        Objects.equals(this.status, templateResponseModel.status) &&
        Objects.equals(this.name, templateResponseModel.name) &&
        Objects.equals(this.description, templateResponseModel.description) &&
        Objects.equals(this.parameters, templateResponseModel.parameters) &&
        Objects.equals(this.emailFormat, templateResponseModel.emailFormat) &&
        Objects.equals(this.smsFormat, templateResponseModel.smsFormat) &&
        Objects.equals(this.createdBy, templateResponseModel.createdBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, version, status, name, description, parameters, emailFormat, smsFormat, createdBy);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TemplateResponseModel {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    parameters: ").append(toIndentedString(parameters)).append("\n");
    sb.append("    emailFormat: ").append(toIndentedString(emailFormat)).append("\n");
    sb.append("    smsFormat: ").append(toIndentedString(smsFormat)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
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

    // add `key` to the URL query string
    if (getKey() != null) {
      joiner.add(String.format("%skey%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getKey()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `version` to the URL query string
    if (getVersion() != null) {
      joiner.add(String.format("%sversion%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getVersion()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `status` to the URL query string
    if (getStatus() != null) {
      joiner.add(String.format("%sstatus%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getStatus()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `name` to the URL query string
    if (getName() != null) {
      joiner.add(String.format("%sname%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getName()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `description` to the URL query string
    if (getDescription() != null) {
      joiner.add(String.format("%sdescription%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getDescription()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    // add `parameters` to the URL query string
    if (getParameters() != null) {
      for (String _key : getParameters().keySet()) {
        joiner.add(String.format("%sparameters%s%s=%s", prefix, suffix,
            "".equals(suffix) ? "" : String.format("%s%d%s", containerPrefix, _key, containerSuffix),
            getParameters().get(_key), URLEncoder.encode(String.valueOf(getParameters().get(_key)), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
      }
    }

    // add `emailFormat` to the URL query string
    if (getEmailFormat() != null) {
      joiner.add(getEmailFormat().toUrlQueryString(prefix + "emailFormat" + suffix));
    }

    // add `smsFormat` to the URL query string
    if (getSmsFormat() != null) {
      joiner.add(getSmsFormat().toUrlQueryString(prefix + "smsFormat" + suffix));
    }

    // add `createdBy` to the URL query string
    if (getCreatedBy() != null) {
      joiner.add(String.format("%screatedBy%s=%s", prefix, suffix, URLEncoder.encode(String.valueOf(getCreatedBy()), StandardCharsets.UTF_8).replaceAll("\\+", "%20")));
    }

    return joiner.toString();
  }
}

