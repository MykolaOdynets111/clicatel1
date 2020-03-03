package datamanager;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonAnySetter;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonIgnore;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;


public class SurveyManagement {

    @JsonProperty("ratingEnabled")
    private Boolean ratingEnabled;
    @JsonProperty("ratingTimeout")
    private String ratingTimeout;
    @JsonProperty("ratingThanksMessage")
    private String ratingThanksMessage;
    @JsonProperty("surveyQuestionTitle")
    private String surveyQuestionTitle;
    @JsonProperty("customerNoteTitle")
    private String customerNoteTitle;
    @JsonProperty("commentEnabled")
    private Boolean commentEnabled;
    @JsonProperty("ratingScale")
    private String ratingScale;
    @JsonProperty("ratingIcon")
    private String ratingIcon;
    @JsonProperty("ratingType")
    private String ratingType;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ratingEnabled")
    public Boolean getRatingEnabled() {
        return ratingEnabled;
    }

    @JsonProperty("ratingEnabled")
    public void setRatingEnabled(Boolean ratingEnabled) {
        this.ratingEnabled = ratingEnabled;
    }

    @JsonProperty("ratingTimeout")
    public String getRatingTimeout() {
        return ratingTimeout;
    }

    @JsonProperty("ratingTimeout")
    public void setRatingTimeout(String ratingTimeout) {
        this.ratingTimeout = ratingTimeout;
    }

    @JsonProperty("ratingThanksMessage")
    public String getRatingThanksMessage() {
        return ratingThanksMessage;
    }

    @JsonProperty("ratingThanksMessage")
    public void setRatingThanksMessage(String ratingThanksMessage) {
        this.ratingThanksMessage = ratingThanksMessage;
    }

    @JsonProperty("surveyQuestionTitle")
    public String getSurveyQuestionTitle() {
        return surveyQuestionTitle;
    }

    @JsonProperty("surveyQuestionTitle")
    public void setSurveyQuestionTitle(String surveyQuestionTitle) {
        this.surveyQuestionTitle = surveyQuestionTitle;
    }

    @JsonProperty("customerNoteTitle")
    public String getCustomerNoteTitle() {
        return customerNoteTitle;
    }

    @JsonProperty("customerNoteTitle")
    public void setCustomerNoteTitle(String customerNoteTitle) {
        this.customerNoteTitle = customerNoteTitle;
    }

    @JsonProperty("commentEnabled")
    public Boolean getCommentEnabled() {
        return commentEnabled;
    }

    @JsonProperty("commentEnabled")
    public void setCommentEnabled(Boolean commentEnabled) {
        this.commentEnabled = commentEnabled;
    }

    @JsonProperty("ratingScale")
    public String getRatingScale() {
        return ratingScale;
    }

    @JsonProperty("ratingScale")
    public void setRatingScale(String ratingScale) {
        this.ratingScale = ratingScale;
    }

    @JsonProperty("ratingIcon")
    public String getRatingIcon() {
        return ratingIcon;
    }

    @JsonProperty("ratingIcon")
    public void setRatingIcon(String ratingIcon) {
        this.ratingIcon = ratingIcon;
    }

    @JsonProperty("ratingType")
    public String getRatingType() {
        return ratingType;
    }

    @JsonProperty("ratingType")
    public void setRatingType(String ratingType) {
        this.ratingType = ratingType;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void updateSomeValueByMethodName(String methodName, String value){
        if(methodName.equals("setRatingEnabled")){
          setRatingEnabled(Boolean.valueOf(value));
        } else if (methodName.equals("setRatingTimeout")){
            setRatingTimeout(value);
        } else if (methodName.equals("setRatingThanksMessage")){
            setRatingThanksMessage(value);
        } else if(methodName.equals("setSurveyQuestionTitle")){
            setSurveyQuestionTitle(value);
        } else if (methodName.equals("setCustomerNoteTitle")){
            setCustomerNoteTitle(value);
        } else if (methodName.equals("setCommentEnabled")){
            setCommentEnabled(Boolean.valueOf(value));
        } else if (methodName.equals("setRatingScale")){
            setRatingScale(value);
        } else if (methodName.equals("setRatingIcon")){
            setRatingIcon(value);
        } else if (methodName.equals("setRatingType")){
            setRatingType(value);
        } else {
            new AssertionError("Was provided incorrect update type :" + value);
        }

    }
}