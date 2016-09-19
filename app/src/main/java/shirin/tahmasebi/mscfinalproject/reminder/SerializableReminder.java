package shirin.tahmasebi.mscfinalproject.reminder;

import java.io.Serializable;
import java.util.Date;

public class SerializableReminder implements Serializable {
    private Long id;
    /**
     * Not-null value.
     */
    private Date date;
    /**
     * Not-null value.
     */
    private String organizationName;
    private String text;

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getText() {
        return text;
    }


    public SerializableReminder(Long id, Date date, String organizationName, String text) {
        this.id = id;
        this.date = date;
        this.organizationName = organizationName;
        this.text = text;
    }
}
