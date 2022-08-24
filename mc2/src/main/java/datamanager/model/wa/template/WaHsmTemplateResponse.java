package datamanager.model.wa.template;

import datamanager.model.language.LanguageResponse;
import datamanager.model.wa.account.WhatsAppAccountResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WaHsmTemplateResponse {
    private String id;
    private String name;
    private String tag;
    private float numOfParams;
    private String template;
    private LanguageResponse Language;
    private String status;
    private WhatsAppAccountResponse WhatsAppAccount;
    @JsonProperty("default")
    private boolean defaultField;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getNumOfParams() {
        return numOfParams;
    }

    public void setNumOfParams(float numOfParams) {
        this.numOfParams = numOfParams;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public LanguageResponse getLanguage() {
        return Language;
    }

    public void setLanguage(LanguageResponse language) {
        Language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WhatsAppAccountResponse getWhatsAppAccount() {
        return WhatsAppAccount;
    }

    public void setWhatsAppAccount(WhatsAppAccountResponse whatsAppAccount) {
        WhatsAppAccount = whatsAppAccount;
    }

    public boolean isDefaultField() {
        return defaultField;
    }

    public void setDefaultField(boolean defaultField) {
        this.defaultField = defaultField;
    }
}