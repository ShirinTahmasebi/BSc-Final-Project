package shirin.tahmasebi.schema;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class SchemaV2 extends Schema {

    private static final int VERSION = 2;
    private static final String PACKAGE = "shirin.tahmasebi.mscfinalproject.io.models";

    public SchemaV2() {
        super(VERSION, PACKAGE);
        createOrganization(this);
    }

    private Entity createOrganization(Schema schema) {
        Entity organization;
        organization = schema.addEntity("Organization");
        organization.addIdProperty().autoincrement();
        organization.addStringProperty("name").notNull();
        organization.addStringProperty("description").notNull();
        organization.addStringProperty("website").notNull().unique();
        organization.addStringProperty("image").notNull();
        return organization;
    }
}