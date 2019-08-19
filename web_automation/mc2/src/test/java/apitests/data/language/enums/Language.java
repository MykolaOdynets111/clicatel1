package apitests.data.language.enums;

public enum Language {
    ENGLISH(1, "en"), FRENCH(2, "fr");

    private int id;
    private String shortName;

    Language(int id, String shortName) {
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
