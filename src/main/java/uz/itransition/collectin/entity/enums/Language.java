package uz.itransition.collectin.entity.enums;

public enum Language {
    ENG("ENG", "АНГЛ", "ING"),
    RUS("RUS", "РУС", "RUS"),
    UZB("UZB", "UZB", "O'ZB");

    public final String languageENG;
    public final String languageRUS;

    public final String languageUZB;

    Language(String languageENG, String languageRUS, String languageUZB) {
        this.languageENG = languageENG;
        this.languageRUS = languageRUS;
        this.languageUZB = languageUZB;
    }
}
