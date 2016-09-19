package shirin.tahmasebi.schema;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class SchemaV5 extends Schema {

    private static final int VERSION = 5;
    private static final String PACKAGE = "shirin.tahmasebi.mscfinalproject.io.models";

    public SchemaV5() {
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
        organization.addStringProperty("description").notNull();
        organization.addStringProperty("website").notNull().unique();
        organization.addStringProperty("image").notNull();
        organization.addBooleanProperty("isFavorite");
        organization.addStringProperty("siteUrl");
        organization.addStringProperty("phoneNumber");
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