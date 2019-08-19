package apitests.data.wa.template;

public class CreateHsmTemplateRequest {
    private String name;
    private String template;
    private int tagId;
    private String language;

    public CreateHsmTemplateRequest(String name, String template, int tagId, String language) {
        this.name = name;
        this.template = template;
        this.tagId = tagId;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
