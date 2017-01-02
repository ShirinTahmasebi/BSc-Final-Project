package shirin.tahmasebi;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Schema;
import shirin.tahmasebi.schema.SchemaV6;

public class DbGenerator {
    public static void main(String[] args) throws Exception {

        Schema schemaV6 = new SchemaV6();
        new DaoGenerator().generateAll(schemaV6, "./app/src/main/java/");
    }
}
