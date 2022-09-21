package datamanager.jacksonschemas;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "agentPackPurchased",
        "maxOnlineAgentLimit",
        "orgName",
        "category",
        "touchGoType",
        "feature",
        "hasBalance",
        "agentAssistant",
        "touchButtonEnabled",
        "canPurchaseAgents",
        "canUpgradePackage",
        "botMode"
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureStatusUpdate {

    @JsonProperty("agentPackPurchased")
    private String agentPackPurchased;
    @JsonProperty("maxOnlineAgentLimit")
    private int maxOnlineAgentLimit;
    @JsonProperty("orgName")
    private String orgName;
    @JsonProperty("category")
    private String category;
    @JsonProperty("touchGoType")
    private String touchGoType;
    @JsonProperty("agentFeedback")
    private String agentFeedback;
    @JsonProperty("hasBalance")
    private String hasBalance;
    @JsonProperty("agentAssistant")
    private String agentAssistant;
    @JsonProperty("touchButtonEnabled")
    private String touchButtonEnabled;
    @JsonProperty("canPurchaseAgents")
    private String canPurchaseAgents;
    @JsonProperty("canUpgradePackage")
    private String canUpgradePackage;
    @JsonProperty("botMode")
    private String botMode;

    public void updateSomeValueByMethodName(String attribute, String value) {
        if (attribute.equals("agentPackPurchased")) {
            setAgentPackPurchased(value);
        } else if (attribute.equals("maxOnlineAgentLimit")) {
            setMaxOnlineAgentLimit(Integer.parseInt(value));
        } else if (attribute.equals("orgName")) {
            setOrgName(value);
        } else if (attribute.equals("category")) {
            setCategory(value);
        } else if (attribute.equals("touchGoType")) {
            setTouchGoType(value);
        } else if (attribute.equals("agentFeedback")) {
            setAgentFeedback(value);
        }else if (attribute.equals("hasBalance")) {
            setHasBalance(value);
        } else if (attribute.equals("agentAssistant")) {
            setAgentAssistant(value);
        } else if (attribute.equals("touchButtonEnabled")) {
            setTouchButtonEnabled(value);
        } else if (attribute.equals("canPurchaseAgents")) {
            setCanPurchaseAgents(value);
        } else if (attribute.equals("canUpgradePackage")) {
            setCanUpgradePackage(value);
        } else if (attribute.equals("botMode")) {
            setBotMode(value);
        }else {
            throw new AssertionError("Was provided incorrect Api attribute :" + attribute);
        }
    }

    public String getSomeValueByMethodName(String attribute) {
        if (attribute.equals("agentPackPurchased")) {
            return String.valueOf(getAgentPackPurchased());
        } else if (attribute.equals("maxOnlineAgentLimit")) {
            return String.valueOf(getMaxOnlineAgentLimit());
        } else if (attribute.equals("orgName")) {
            return getOrgName();
        } else if (attribute.equals("category")) {
            return getCategory();
        } else if (attribute.equals("touchGoType")) {
            return getTouchGoType();
        } else if (attribute.equals("agentFeedback")) {
            return String.valueOf(getAgentFeedback());
        } else if (attribute.equals("hasBalance")) {
            return String.valueOf(getHasBalance());
        } else if (attribute.equals("agentAssistant")) {
            return String.valueOf(getAgentAssistant());
        } else if (attribute.equals("touchButtonEnabled")) {
            return String.valueOf(getTouchButtonEnabled());
        } else if (attribute.equals("canPurchaseAgents")) {
            return String.valueOf(getCanPurchaseAgents());
        } else if (attribute.equals("canUpgradePackage")) {
            return String.valueOf(getCanUpgradePackage());
        } else if (attribute.equals("botMode")) {
            return getBotMode();
        }else {
            throw new AssertionError("Was provided incorrect Api attribute :" + attribute);
        }
    }
}