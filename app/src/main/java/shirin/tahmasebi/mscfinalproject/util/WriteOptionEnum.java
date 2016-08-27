package shirin.tahmasebi.mscfinalproject.util;

public enum WriteOptionEnum {
    EMAIL("EMAIL", 1),
    WEBSITE("WEBSITE", 2),
    CALL("CALL", 3);

    private String stringValue;
    private int intValue;

    private WriteOptionEnum(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }
}
