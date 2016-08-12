package shirin.tahmasebi.schema;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;


public class SchemaV1 extends Schema {

    private static final int VERSION = 1;
    private static final String PACKAGE = "shirin.tahmasebi.mscfinalproject.io.models";

    public SchemaV1() {
        super(VERSION, PACKAGE);
        createOrganization(this);
    }

    private Entity createOrganization(Schema schema) {
        Entity organization;
        organization = schema.addEntity("Organization");
        organization.addIdProperty().notNull().primaryKey();
        organization.addStringProperty("name").notNull();
        organization.addStringProperty("description").notNull();
        organization.addStringProperty("website").notNull();
        return organization;
    }
}