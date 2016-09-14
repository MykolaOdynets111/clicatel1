package com.clickatell.models.mc2.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oshchur on 04.07.2016.
 */
public class SolutionsList {
    private List<Solution> solutions = new ArrayList<Solution>();

    /**
     * No args constructor for use in serialization
     *
     */
    public SolutionsList() {
    }

    /**
     *
     * @param solutions
     */
    public SolutionsList(List<Solution> solutions) {
        this.solutions = solutions;
    }

    /**
     *
     * @return
     * The solutions
     */
    public List<Solution> getSolutions() {
        return solutions;
    }

    /**
     *
     * @param solutions
     * The solutions
     */
    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public SolutionsList withSolutions(List<Solution> solutions) {
        this.solutions = solutions;
        return this;
    }
}
