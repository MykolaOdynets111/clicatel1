package datamanager.model.wa.template;

import com.google.gson.JsonObject;

public class UpdateHsmTemplateRequest {
    private String whatsAppTemplateNamespace;
    private String name;
    private int tagId;
    private String language;

    public UpdateHsmTemplateRequest(String whatsAppTemplateNamespace, String name, int tagId, String language) {
        this.whatsAppTemplateNamespace = whatsAppTemplateNamespace;
        this.name = name;
        this.tagId = tagId;
        this.language = language;
    }

    public String toJsonRequest() {
        JsonObject createHsmRequest = new JsonObject();

        createHsmRequest.addProperty("whatsAppTemplateNamespace", whatsAppTemplateNamespace);
        createHsmRequest.addProperty("name", name);
        createHsmRequest.addProperty("tagId", tagId);
        createHsmRequest.addProperty("language", language);

        return createHsmRequest.toString();
    }
}