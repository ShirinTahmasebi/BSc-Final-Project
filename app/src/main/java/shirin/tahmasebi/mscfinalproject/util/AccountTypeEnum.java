package shirin.tahmasebi.mscfinalproject.util;

public enum AccountTypeEnum {
    NothingSelected("NOTHING", 0),
    Google("EMAIL", 1),
    Other("OTHER", 3);

    private String stringValue;
    private int intValue;

    private AccountTypeEnum(String toString, int value) {
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
