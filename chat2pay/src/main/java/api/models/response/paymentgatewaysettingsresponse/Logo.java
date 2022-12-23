package api.models.response.paymentgatewaysettingsresponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

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

@Data
public class Logo {

    @JsonProperty("id")
    private String id;

    @JsonProperty("fileKey")
    private String fileKey;

    @JsonProperty("originalFilename")
    private String originalFilename;

    @JsonProperty("fileSizeBytes")
    private int fileSizeBytes;

    @JsonProperty("mediaType")
    private String mediaType;

    @JsonProperty("imageWidth")
    private int imageWidth;

    @JsonProperty("imageHeight")
    private int imageHeight;

    @JsonProperty("createdTime")
    private String createdTime;
}
