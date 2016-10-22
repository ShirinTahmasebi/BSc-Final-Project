package shirin.tahmasebi.mscfinalproject.io.models;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table HISTORY.
 */
public class History {

    private Long id;
    /** Not-null value. */
    private String date;
    /** Not-null value. */
    private String organizationName;
    private Integer type;
    private String emailText;

    public History() {
    }

    public History(Long id) {
        this.id = id;
    }

    public History(Long id, String date, String organizationName, Integer type, String emailText) {
        this.id = id;
        this.date = date;
        this.organizationName = organizationName;
        this.type = type;
        this.emailText = emailText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getDate() {
        return date;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDate(String date) {
        this.date = date;
    }

    /** Not-null value. */
    public String getOrganizationName() {
        return organizationName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

}
