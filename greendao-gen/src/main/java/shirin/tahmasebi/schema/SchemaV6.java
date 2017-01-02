package shirin.tahmasebi.schema;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class SchemaV6 extends Schema {

    private static final int VERSION = 6;
    private static final String PACKAGE = "shirin.tahmasebi.mscfinalproject.io.models";

    public SchemaV6() {
        super(VERSION, PACKAGE);
        createOrganization(this);
        createHistory(this);
        createReminder(this);
    }

    private Entity createOrganization(Schema schema) {
        Entity organization;
        organization = schema.addEntity("Organization");
        organization.addIdProperty();
        organization.addStringProperty("name").notNull();
        organization.addStringProperty("no");
        organization.addStringProperty("website").notNull();
        organization.addStringProperty("phoneNumber");
        organization.addStringProperty("smsNumber");
        organization.addStringProperty("created");
        organization.addStringProperty("updated");
        organization.addStringProperty("logo").notNull();
        organization.addBooleanProperty("isFavorite");
        organization.addStringProperty("emailAddress");
        return organization;
    }


    private Entity createHistory(Schema schema) {
        Entity history;
        history = schema.addEntity("History");
        history.addIdProperty();
        history.addStringProperty("date").notNull();
        history.addStringProperty("organizationName").notNull();
        history.addIntProperty("type"); // 0 -> call 1 -> email
        history.addStringProperty("emailText");
        return history;
    }

    private Entity createReminder(Schema schema) {
        Entity reminder;
        reminder = schema.addEntity("Reminder");
        reminder.addIdProperty();
        reminder.addDateProperty("date").notNull();
        reminder.addStringProperty("organizationName").notNull();
        reminder.addStringProperty("text");
        return reminder;
    }

}