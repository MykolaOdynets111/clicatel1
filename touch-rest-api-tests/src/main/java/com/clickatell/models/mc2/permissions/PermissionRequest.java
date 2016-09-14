package com.clickatell.models.mc2.permissions;

import com.clickatell.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;


/**
 * PermissionRequest
 */
public class PermissionRequest {

    private String id = null;
    private ActionEnum action = null;
    private String subject = null;
    private SolutionEnum solution = null;
    private CategoryEnum category = null;
    private String description = null;

    public PermissionRequest(String id) {
        this.id = id;
        action = ActionEnum.READ;
        category = CategoryEnum.SOLUTION_MANAGEMENT;
        description = "Description_" + StringUtils.generateRandomString(10);
        solution = SolutionEnum.PLATFORM;
        subject = "Subject_" + StringUtils.generateRandomString(10);
    }

    /**
     * Gets or Sets action
     */
    public enum ActionEnum {
        CREATE("CREATE"),
        UPDATE("UPDATE"),
        READ("READ"),
        DELETE("DELETE");

        private String value;

        ActionEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }


    /**
     * Gets or Sets solution
     */
    public enum SolutionEnum {
        PLATFORM("PLATFORM"),
        TOUCH("TOUCH"),
        SECURE("SECURE"),
        ENGAGE("ENGAGE"),
        ALL("ALL");

        private String value;

        SolutionEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }


    /**
     * Gets or Sets category
     */
    public enum CategoryEnum {
        USER_MANAGEMENT("USER_MANAGEMENT"),
        SOLUTION_MANAGEMENT("SOLUTION_MANAGEMENT"),
        BILLING_MANAGEMENT("BILLING_MANAGEMENT"),
        INTEGRATION_MANAGEMENT("INTEGRATION_MANAGEMENT");

        private String value;

        CategoryEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }


    /**
     **/
    public PermissionRequest id(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    /**
     **/
    public PermissionRequest action(ActionEnum action) {
        this.action = action;
        return this;
    }


    @JsonProperty("action")
    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }


    /**
     **/
    public PermissionRequest subject(String subject) {
        this.subject = subject;
        return this;
    }


    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    /**
     **/
    public PermissionRequest solution(SolutionEnum solution) {
        this.solution = solution;
        return this;
    }


    @JsonProperty("solution")
    public SolutionEnum getSolution() {
        return solution;
    }

    public void setSolution(SolutionEnum solution) {
        this.solution = solution;
    }


    /**
     **/
    public PermissionRequest category(CategoryEnum category) {
        this.category = category;
        return this;
    }


    @JsonProperty("category")
    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }


    /**
     **/
    public PermissionRequest description(String description) {
        this.description = description;
        return this;
    }


    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PermissionRequest permissionRequest = (PermissionRequest) o;
        return Objects.equals(this.id, permissionRequest.id) &&
                Objects.equals(this.action, permissionRequest.action) &&
                Objects.equals(this.subject, permissionRequest.subject) &&
                Objects.equals(this.solution, permissionRequest.solution) &&
                Objects.equals(this.category, permissionRequest.category) &&
                Objects.equals(this.description, permissionRequest.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action, subject, solution, category, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PermissionRequest {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    action: ").append(toIndentedString(action)).append("\n");
        sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
        sb.append("    solution: ").append(toIndentedString(solution)).append("\n");
        sb.append("    category: ").append(toIndentedString(category)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

