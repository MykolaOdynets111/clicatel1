package com.touch.models.touch.tenant;

/**
 * Created by kmakohoniuk on 5/23/2017.
 */
public class TenantAddressForUpdate {
    private String firstAddressLine;
    private String secondAddressLine;
    private String city;
    private String stateOrProvince;
    private String postalCode;
    private Long lat;
    private Long lng;


    public TenantAddressForUpdate() {
        this.firstAddressLine = "test";
        this.secondAddressLine = "test";
        this.city = "Lviv";
        this.stateOrProvince = "Lviv";
        this.postalCode = "07090";
        this.lat = 0L;
        this.lng = 0L;
    }

    public TenantAddressForUpdate(String firstAddressLine, String secondAddressLine, String city, String stateOrProvince, String postalCode, Long lat, Long lng) {
        this.firstAddressLine = firstAddressLine;
        this.secondAddressLine = secondAddressLine;
        this.city = city;
        this.stateOrProvince = stateOrProvince;
        this.postalCode = postalCode;
        this.lat = lat;
        this.lng = lng;
    }

    public String getFirstAddressLine() {
        return firstAddressLine;
    }

    public void setFirstAddressLine(String firstAddressLine) {
        this.firstAddressLine = firstAddressLine;
    }

    public String getSecondAddressLine() {
        return secondAddressLine;
    }

    public void setSecondAddressLine(String secondAddressLine) {
        this.secondAddressLine = secondAddressLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public void setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLng() {
        return lng;
    }

    public void setLng(Long lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TenantAddressForUpdate that = (TenantAddressForUpdate) o;

        if (firstAddressLine != null ? !firstAddressLine.equals(that.firstAddressLine) : that.firstAddressLine != null)
            return false;
        if (secondAddressLine != null ? !secondAddressLine.equals(that.secondAddressLine) : that.secondAddressLine != null)
            return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (stateOrProvince != null ? !stateOrProvince.equals(that.stateOrProvince) : that.stateOrProvince != null)
            return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;
        return lng != null ? lng.equals(that.lng) : that.lng == null;
    }

    @Override
    public int hashCode() {
        int result = firstAddressLine != null ? firstAddressLine.hashCode() : 0;
        result = 31 * result + (secondAddressLine != null ? secondAddressLine.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (stateOrProvince != null ? stateOrProvince.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TenantAddressForUpdate{" +
                "firstAddressLine='" + firstAddressLine + '\'' +
                ", secondAddressLine='" + secondAddressLine + '\'' +
                ", city='" + city + '\'' +
                ", stateOrProvince='" + stateOrProvince + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
