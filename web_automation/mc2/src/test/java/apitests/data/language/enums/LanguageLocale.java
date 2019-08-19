package apitests.data.language.enums;

public enum LanguageLocale {
    ENGLISH_BRITAIN(1, "en_gb");

    private int id;
    private String shortName;

    LanguageLocale(int id, String shortName) {
        this.id = id;
        this.shortName = shortName;
    }

    public int getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }
}
