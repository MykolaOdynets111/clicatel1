package data.models.response.merchantbillinginfo;

import data.models.response.paymentgatewaysettingsresponse.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "email",
        "addressLine1",
        "addressLine2",
        "state",
        "country",
        "postalCode",
        "city",
        "companyName",
        "phone"
})

public class MerchantBillingInfoResponse {

    @JsonProperty("email")
    public String email;

    @JsonProperty("addressLine1")
    public String addressLine1;

    @JsonProperty("addressLine2")
    public String addressLine2;

    @JsonProperty("state")
    public State state;

    @JsonProperty("country")
    public Country country;

    @JsonProperty("postalCode")
    public String postalCode;

    @JsonProperty("city")
    public String city;

    @JsonProperty("companyName")
    public String companyName;

    @JsonProperty("phone")
    public String phone;

}