package uz.itransition.collectin.payload.response;

public enum ElementType {
    COLLECTION("COLLECTION"),
    ITEM("ITEM"),
    COMMENT("COMMENT");

    public final String type;
    ElementType(String type) {
        this.type = type;
    }
}
