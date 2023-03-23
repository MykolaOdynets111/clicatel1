package data.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "fileKey",
        "originalFilename",
        "fileSizeBytes",
        "mediaType",
        "imageWidth",
        "imageHeight",
        "createdTime"
})

public class Logo {

    @JsonProperty("id")
    public String id;

    @JsonProperty("fileKey")
    public String fileKey;

    @JsonProperty("originalFilename")
    public String originalFilename;

    @JsonProperty("fileSizeBytes")
    public int fileSizeBytes;

    @JsonProperty("mediaType")
    public String mediaType;

    @JsonProperty("imageWidth")
    public int imageWidth;

    @JsonProperty("imageHeight")
    public int imageHeight;

    @JsonProperty("createdTime")
    public String createdTime;
}
