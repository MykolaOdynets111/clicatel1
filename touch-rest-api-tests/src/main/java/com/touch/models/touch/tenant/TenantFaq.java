
package com.touch.models.touch.tenant;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "question",
    "answer"
})
public class TenantFaq {

    @JsonProperty("question")
    private String question;
    @JsonProperty("answer")
    private String answer;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TenantFaq() {
    }

    /**
     * 
     * @param answer
     * @param question
     */
    public TenantFaq(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * 
     * @return
     *     The question
     */
    @JsonProperty("question")
    public String getQuestion() {
        return question;
    }

    /**
     * 
     * @param question
     *     The question
     */
    @JsonProperty("question")
    public void setQuestion(String question) {
        this.question = question;
    }

    public TenantFaq withQuestion(String question) {
        this.question = question;
        return this;
    }

    /**
     * 
     * @return
     *     The answer
     */
    @JsonProperty("answer")
    public String getAnswer() {
        return answer;
    }

    /**
     * 
     * @param answer
     *     The answer
     */
    @JsonProperty("answer")
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public TenantFaq withAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(question).append(answer).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TenantFaq) == false) {
            return false;
        }
        TenantFaq rhs = ((TenantFaq) other);
        return new EqualsBuilder().append(question, rhs.question).append(answer, rhs.answer).isEquals();
    }

}
