package com.clickatell.models.mc2.two_way_long.response.available;

import java.util.List;

/**
 * Created by sbryt on 8/23/2016.
 */
public class TwoWayAvailableNumbers {

    private List<NumbersGroup> numberGroups;

    public TwoWayAvailableNumbers(List<NumbersGroup> numberGroups) {
        this.numberGroups = numberGroups;
    }

    public List<NumbersGroup> getNumberGroups() {
        return numberGroups;
    }

    public void setNumberGroups(List<NumbersGroup> numberGroups) {
        this.numberGroups = numberGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TwoWayAvailableNumbers that = (TwoWayAvailableNumbers) o;

        return getNumberGroups() != null ? getNumberGroups().equals(that.getNumberGroups()) : that.getNumberGroups() == null;

    }

    @Override
    public String toString() {
        return "TwoWayAvailableNumbers{" +
                "numberGroups=" + numberGroups +
                '}';
    }

    @Override
    public int hashCode() {
        return getNumberGroups() != null ? getNumberGroups().hashCode() : 0;
    }
}
