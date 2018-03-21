
package dataprovider.jackson_schemas.tenant_address;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "firstAddressLine",
    "secondAddressLine",
    "city",
    "stateOrProvince",
    "postalCode",
    "lat",
    "lng",
    "businessHours",
    "phones"
})
public class TenantAddress {

    @JsonProperty("id")
    private String id;
    @JsonProperty("firstAddressLine")
    private String firstAddressLine;
    @JsonProperty("secondAddressLine")
    private String secondAddressLine;
    @JsonProperty("city")
    private String city;
    @JsonProperty("stateOrProvince")
    private String stateOrProvince;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("lat")
    private Integer lat;
    @JsonProperty("lng")
    private Integer lng;
    @JsonProperty("businessHours")
    private List<BusinessHour> businessHours = null;
    @JsonProperty("phones")
    private List<Phone> phones = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("firstAddressLine")
    public String getFirstAddressLine() {
        return firstAddressLine;
    }

    @JsonProperty("firstAddressLine")
    public void setFirstAddressLine(String firstAddressLine) {
        this.firstAddressLine = firstAddressLine;
    }

    @JsonProperty("secondAddressLine")
    public String getSecondAddressLine() {
        return secondAddressLine;
    }

    @JsonProperty("secondAddressLine")
    public void setSecondAddressLine(String secondAddressLine) {
        this.secondAddressLine = secondAddressLine;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("stateOrProvince")
    public String getStateOrProvince() {
        return stateOrProvince;
    }

    @JsonProperty("stateOrProvince")
    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    @JsonProperty("postalCode")
    public String getPostalCode() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @JsonProperty("lat")
    public Integer getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Integer lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public Integer getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Integer lng) {
        this.lng = lng;
    }

    @JsonProperty("businessHours")
    public List<BusinessHour> getBusinessHours() {
        return businessHours;
    }

    @JsonProperty("businessHours")
    public void setBusinessHours(List<BusinessHour> businessHours) {
        this.businessHours = businessHours;
    }

    @JsonProperty("phones")
    public List<Phone> getPhones() {
        return phones;
    }

    @JsonProperty("phones")
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
