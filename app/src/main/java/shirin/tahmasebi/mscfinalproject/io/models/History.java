package shirin.tahmasebi.mscfinalproject.io.models;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table HISTORY.
 */
public class History {

    private Long id;
    /** Not-null value. */
    private java.util.Date date;
    /** Not-null value. */
    private String organizationName;
    private Boolean type;
    private String emailText;

    public History() {
    }

    public History(Long id) {
        this.id = id;
    }

    public History(Long id, java.util.Date date, String organizationName, Boolean type, String emailText) {
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
    public java.util.Date getDate() {
        return date;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDate(java.util.Date date) {
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

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }

}