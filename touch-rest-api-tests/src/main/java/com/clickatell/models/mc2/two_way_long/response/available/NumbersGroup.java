package com.clickatell.models.mc2.two_way_long.response.available;

import com.clickatell.models.mc2.integration.general.TwoWayNumber;

import java.util.List;

/**
 * Created by sbryt on 8/23/2016.
 */
public class NumbersGroup {
    private String coverage;
    private List<TwoWayNumber> numbers;

    public NumbersGroup(String coverage, List<TwoWayNumber> numbers) {
        this.coverage = coverage;
        this.numbers = numbers;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public List<TwoWayNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<TwoWayNumber> numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "NumbersGroup{" +
                "coverage='" + coverage + '\'' +
                ", numbers=" + numbers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumbersGroup that = (NumbersGroup) o;

        if (getCoverage() != null ? !getCoverage().equals(that.getCoverage()) : that.getCoverage() != null)
            return false;
        return getNumbers() != null ? getNumbers().equals(that.getNumbers()) : that.getNumbers() == null;

    }

    @Override
    public int hashCode() {
        int result = getCoverage() != null ? getCoverage().hashCode() : 0;
        result = 31 * result + (getNumbers() != null ? getNumbers().hashCode() : 0);
        return result;
    }
}
