package shirin.tahmasebi.mscfinalproject.reminder;

import java.io.Serializable;

public class SerializableReminder implements Serializable {
    private Long id;
    /**
     * Not-null value.
     */
    private String date;
    /**
     * Not-null value.
     */
    private String time;
    /**
     * Not-null value.
     */
    private String organizationName;
    private String text;

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getText() {
        return text;
    }


    public SerializableReminder(Long id, String date, String time, String organizationName,
                                String text) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.organizationName = organizationName;
        this.text = text;
    }
}
