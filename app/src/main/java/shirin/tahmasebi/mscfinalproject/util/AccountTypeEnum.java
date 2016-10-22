package shirin.tahmasebi.mscfinalproject.util;

public enum AccountTypeEnum {
    NothingSelected("NothingSelected", 0),
    Google("Google", 1),
    Other("Other", 2);

    private String stringValue;
    private int intValue;

    AccountTypeEnum(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public int getIntValue() {
        return intValue;
    }
}
