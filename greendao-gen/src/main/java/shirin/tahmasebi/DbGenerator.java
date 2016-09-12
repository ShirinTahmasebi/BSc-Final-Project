package shirin.tahmasebi;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;
import shirin.tahmasebi.schema.SchemaV5;

public class DbGenerator {
    public static void main(String[] args) throws Exception {

        Schema schemaV5 = new SchemaV5();
        new DaoGenerator().generateAll(schemaV5, "./app/src/main/java/");
    }
}
