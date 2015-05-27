package io.github.fedorchuck.sglados_server;

import io.github.fedorchuck.sglados_server.model.MessageBuilder;
import io.github.fedorchuck.sglados_server.dataBase.Postgre;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by v on 22.05.2015.
 */
public class DataBaseTest extends TestCase {
    public Postgre db = new Postgre();
    @Test
    public void testCreateTable() throws Exception {
        boolean psql = db.psqlAvailable("localhost", 5432);
        if (psql) {
            db.connectionOpen();
            boolean expected = true;
            boolean result = db.createTable("CREATE TABLE UNICORN "
                    + "(ID INT PRIMARY KEY        NOT NULL, "
                    + " hardware          TEXT    NOT NULL, "
                    + " date              TEXT    NOT NULL, "
                    + " nameOfTest        TEXT    NOT NULL, "
                    + " resultOfTest      TEXT    NOT NULL)");
            db.connectionClose();
            Assert.assertEquals(expected, result);
        } else Assert.assertTrue(true);
    }

    @Test
    public void testInsertOperation() throws Exception {
        boolean psql = db.psqlAvailable("localhost", 5432);
        if (psql) {
            db.connectionOpen();
            boolean expected = true;
            boolean result = db.insertOperation("INSERT INTO UNICORN (ID,hardware,date,nameOfTest,resultOfTest) " +
                            "VALUES (?,?,?,?,?);",
                    db.getLastId("UNICORN") + 1,
                    new MessageBuilder()
                            .setNameOfTable("First table with using builder")
                            .setAddress("unicorn")
                            .setName("2015/05/14 13:28:39") //2015/05/14 13:28:39
                            .setData("see the unicorn")
                            .setMessage("failed")
                            .newBuild()
            );
            db.connectionClose();
            Assert.assertEquals(expected, result);
        } else Assert.assertTrue(true);
    }

    @Ignore
    @Test
    public void testDeleteOperation() throws Exception {
        boolean psql = db.psqlAvailable("localhost", 5432);
        if (psql) {
            db.connectionOpen();
            boolean expected = false;//time construction
            String sql = "DELETE FROM UNICORN WHERE ID=1";
            boolean result = db.deleteOperation(sql);
            db.connectionClose();
            Assert.assertEquals(expected, result);
        } else Assert.assertTrue(true);
    }

    @Test
    public void testDropTable(){
        boolean psql = db.psqlAvailable("localhost", 5432);
        if (psql) {
            db.connectionOpen();
            boolean expected = true;
            boolean result = db.deleteOperation("DROP TABLE unicorn;");
            db.connectionClose();
            Assert.assertEquals(expected, result);
        } else Assert.assertTrue(true);
    }

}