package com.clickatell.models.mc2.common_data.response.countries;

import java.util.List;

/**
 * Created by sbryt on 8/9/2016.
 */
public class CountriesGetResponse {
    private List<CountryShortResponse> countryShortResponseList;

    public CountriesGetResponse(List<CountryShortResponse> countryShortResponseList) {
        this.countryShortResponseList = countryShortResponseList;
    }

    public CountriesGetResponse() {
    }

    public List<CountryShortResponse> getCountryShortResponseList() {
        return countryShortResponseList;
    }

    public void setCountryShortResponseList(List<CountryShortResponse> countryShortResponseList) {
        this.countryShortResponseList = countryShortResponseList;
    }
}
