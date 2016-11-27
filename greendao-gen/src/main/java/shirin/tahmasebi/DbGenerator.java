package shirin.tahmasebi;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;

import shirin.tahmasebi.schema.SchemaV2;

public class DbGenerator {
    public static void main(String[] args) throws Exception {

        Schema schemaV1 = new SchemaV2();
        new DaoGenerator().generateAll(schemaV1, "./app/src/main/java/");
    }
}
