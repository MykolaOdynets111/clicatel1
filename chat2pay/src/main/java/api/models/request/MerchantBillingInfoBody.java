package api.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "addressLine1",
        "addressLine2",
        "stateId",
        "countryId",
        "postalCode",
        "city",
        "companyName",
        "phone"
})

@Setter
public class MerchantBillingInfoBody {

    @JsonProperty("email")
    public String email = "merchantinfo@billing.com";

    @JsonProperty("addressLine1")
    public String addressLine1 = "Merchant";

    @JsonProperty("addressLine2")
    public String addressLine2 = "BillingArea";

    @JsonProperty("stateId")
    public int stateId = 3367;

    @JsonProperty("countryId")
    public int countryId = 206;

    @JsonProperty("postalCode")
    public String postalCode = "ABC999";

    @JsonProperty("city")
    public String city = "CapeTown";

    @JsonProperty("companyName")
    public String companyName = "MBIInc";

    @JsonProperty("phone")
    public String phone = "6665557772";
}
