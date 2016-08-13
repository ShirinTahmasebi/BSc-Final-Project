package shirin.tahmasebi.mscfinalproject.io.models;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table ORGANIZATION.
 */
public class Organization {

    private Long id;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String description;
    /** Not-null value. */
    private String website;
    /** Not-null value. */
    private String image;

    public Organization() {
    }

    public Organization(Long id) {
        this.id = id;
    }

    public Organization(Long id, String name, String description, String website, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getDescription() {
        return description;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Not-null value. */
    public String getWebsite() {
        return website;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setWebsite(String website) {
        this.website = website;
    }

    /** Not-null value. */
    public String getImage() {
        return image;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setImage(String image) {
        this.image = image;
    }

}
