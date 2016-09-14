package com.clickatell.models.mc2.roles.response;

/**
 * Created by oshchur on 18.07.2016.
 */
public class Permission implements Comparable {

    private String id;
    private String action;
    private String subject;
    private String solution;
    private String category;
    private String description;

    /**
     * No args constructor for use in serialization
     */
    public Permission() {
    }

    /**
     * @param id
     * @param category
     * @param subject
     * @param action
     * @param solution
     */
    public Permission(String id, String action, String subject, String solution, String category, String description) {
        this.id = id;
        this.action = action;
        this.subject = subject;
        this.solution = solution;
        this.category = category;
        this.description = description;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public Permission withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return The action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action The action
     */
    public void setAction(String action) {
        this.action = action;
    }

    public Permission withAction(String action) {
        this.action = action;
        return this;
    }

    /**
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Permission withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * @return The solution
     */
    public String getSolution() {
        return solution;
    }

    /**
     * @param solution The solution
     */
    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Permission withSolution(String solution) {
        this.solution = solution;
        return this;
    }

    /**
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public Permission withCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getAction() != null ? !getAction().equals(that.getAction()) : that.getAction() != null) return false;
        if (getSubject() != null ? !getSubject().equals(that.getSubject()) : that.getSubject() != null) return false;
        if (getSolution() != null ? !getSolution().equals(that.getSolution()) : that.getSolution() != null)
            return false;
        return getCategory() != null ? getCategory().equals(that.getCategory()) : that.getCategory() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getAction() != null ? getAction().hashCode() : 0);
        result = 31 * result + (getSubject() != null ? getSubject().hashCode() : 0);
        result = 31 * result + (getSolution() != null ? getSolution().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        Permission permission = (Permission) o;
        return this.id.compareTo(permission.getId());
    }

    @Override
    public String toString() {
        return "Permission{" + "\n" +
                "id='" + id + '\'' + "\n" +
                ", action='" + action + '\'' + "\n" +
                ", subject='" + subject + '\'' + "\n" +
                ", solution='" + solution + '\'' + "\n" +
                ", category='" + category + '\'' + "\n" +
                ", description='" + description + '\'' + "\n" +
                '}' + "\n\n";
    }
}
