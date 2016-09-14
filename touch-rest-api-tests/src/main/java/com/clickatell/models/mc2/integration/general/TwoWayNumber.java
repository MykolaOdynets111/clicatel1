package com.clickatell.models.mc2.integration.general;

/**
 * Created by sbryt on 7/19/2016.
 */
public class TwoWayNumber {
    private String id;
    private String number;

    public TwoWayNumber() {
    }

    public TwoWayNumber(String id, String number) {
        this.id = id;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwoWayNumber that = (TwoWayNumber) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        return getNumber() != null ? getNumber().equals(that.getNumber()) : that.getNumber() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getNumber() != null ? getNumber().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TwoWayNumber{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
