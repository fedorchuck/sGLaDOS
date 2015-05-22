package io.github.fedorchuck.sglados_server.dataBase;

import io.github.fedorchuck.sglados_server.model.Message;
import io.github.fedorchuck.sglados_server.model.MessageBuilder;
import io.github.fedorchuck.sglados_server.helperUnits.ThrowThrowable;
import org.postgresql.util.PSQLException;

import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by v on 22.05.2015.
 */
public class DataBase {
    private Connection connection;
    private ThrowThrowable throwThrowable;

    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/sglados_srv";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "cote";

    public Connection getConnection() {
        return connection;
    }

    public DataBase() {
        ThrowThrowable th = new ThrowThrowable();
        this.throwThrowable = th;
    }

    public void connectionOpen() {
        String what = "problem with open connection to dataBase";
        try {
            this.connection = DriverManager
                    .getConnection(DB_CONNECTION,
                            DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            this.throwThrowable.throwError(this.getClass(), ex, what);
        }

    }

    public void connectionClose() {
        if (getConnection() == null)
            this.throwThrowable.throwError(this.getClass(), new NullPointerException(), "connection is null");

        try {
            getConnection().close();
        } catch (SQLException e) {
            this.throwThrowable.throwError(this.getClass(), e, "problem with close connection to dataBase");
        }

    }

    public boolean createTable(String sql) {
        if (this.connection == null) return false;
        if (sql == null) return false;
        if (sql.isEmpty()) return false;

        Statement stmt;
        try {
            stmt = this.connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLTimeoutException e) {
            this.throwThrowable.throwError(this.getClass(), e, null);
            return false;
        } catch (PSQLException e) {
            if ((e.getMessage().endsWith("already exists"))
                    && (e.getMessage().startsWith("ERROR: relation")))
                return true;
            else {
                this.throwThrowable.throwError(this.getClass(), e, null);
                return false;
            }
        } catch (SQLException  e ) {
            this.throwThrowable.throwError(this.getClass(), e, null);
            return false;
        }
        return true;
    }

    public boolean insertOperation(String insertTableSQL,
                                   final int id,
                                   final Message data) {
        if (this.connection == null) return false;
        if (insertTableSQL == null) return false;
        if (insertTableSQL.isEmpty()) return false;

        PreparedStatement preparedStatement;

        try {
            preparedStatement = this.connection.prepareStatement(insertTableSQL);

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, data.getAddress());
            preparedStatement.setString(3, data.getName());
            preparedStatement.setString(4, data.getData());
            preparedStatement.setString(5, data.getMessage());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            this.connection.commit();
        } catch (SQLTimeoutException e) {
            this.throwThrowable.throwError(this.getClass(), e, null);
            return false;
        } catch (SQLException e) {
            this.throwThrowable.throwError(this.getClass(), e, null);
            return false;
        }
        return true;
    }

    public ArrayList selectOperation_ThematicQuery(String insertTableSQL) {
        if (this.connection == null)
            this.throwThrowable.throwError(this.getClass(), new NullPointerException(), "connection is null");
        if (insertTableSQL == null)
            this.throwThrowable.throwError(this.getClass(), new NullPointerException(), "try insert null");

        Statement stmt;
        ResultSet rs;
        ArrayList result = new ArrayList<String>();
        try {
            this.connection.setAutoCommit(false);

            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(insertTableSQL);
            ResultSetMetaData mata = rs.getMetaData();
            int columnCount = mata.getColumnCount();
            //show result:
            while (rs.next()) {
                ArrayList row = new ArrayList();
                for (int i = 0; i < columnCount; i++) {
                    row.add(rs.getObject(i+1));
                }
                result.add(row);
            }
            rs.close();
            stmt.close();
        } catch ( SQLException e ) {
            this.throwThrowable.throwError(this.getClass(), e, null);
        }
        return result;
    }

    public ArrayList selectOperation(String insertTableSQL) {
        if (this.connection == null)
            this.throwThrowable.throwError(this.getClass(), new NullPointerException(), "connection is null");
        if (insertTableSQL == null)
            this.throwThrowable.throwError(this.getClass(), new NullPointerException(), "try insert null");

        Statement stmt;
        ResultSet rs;
        ArrayList result = new ArrayList<String>();
        try {
            this.connection.setAutoCommit(false);

            stmt = this.connection.createStatement();
            rs = stmt.executeQuery(insertTableSQL);

            /**I know - it's bad practice(ex. SELECT * FROM database1, dataBase2; or JOIN)
             * but at this moment I have only 1 table and this functional make good job.
             * here should be parser. */
            String[] parts = insertTableSQL.split(" ");
            String nameOfTable = parts[parts.length-1];

            while (rs.next()) {
                Message row = new MessageBuilder()
                        .setNameOfTable(nameOfTable)
                        .setAddress(rs.getString("address"))
                        .setName(rs.getString("name"))
                        .setData(rs.getString("data"))
                        .setMessage(rs.getString("message"))
                        .newBuild();
                result.add(row);
            }
            rs.close();
            stmt.close();
        } catch ( SQLException e ) {
            this.throwThrowable.throwError(this.getClass(), e, null);
        }
        return result;
    }

    /**
     * Executes the given SQL statement, which may be an <code>INSERT</code>,
     * <code>UPDATE</code>, or <code>DELETE</code> statement or an
     * SQL statement that returns nothing, such as an SQL DDL statement.
     *<p>
     * <strong>Note:</strong>This method cannot be called on a
     * <code>PreparedStatement</code> or <code>CallableStatement</code>.
     * @param sql an SQL Data Manipulation Language (DML) statement, such as <code>INSERT</code>, <code>UPDATE</code> or
     * <code>DELETE</code>; or an SQL statement that returns nothing,
     * such as a DDL statement.
     * Example: <code>"UPDATE COMPANY set SALARY = 25000.00 where ID=1;"</code>
     *
     * @return true if operation completed successfully and false if failed.
     *
     * @exception SQLException if a database access error occurs,
     * this method is called on a closed <code>Statement</code>, the given
     * SQL statement produces a <code>ResultSet</code> object, the method is called on a
     * <code>PreparedStatement</code> or <code>CallableStatement</code>*
     * @throws SQLTimeoutException when the driver has determined that the
     * timeout value that was specified by the {@code setQueryTimeout}
     * method has been exceeded and has at least attempted to cancel
     * the currently running {@code Statement}
     */
    public boolean updateOperation(String sql) {
        if (this.connection == null) return false;
        if (sql == null) return false;
        if (sql.isEmpty()) return false;

        Statement stmt;
        try {
            this.connection.setAutoCommit(false);

            stmt = this.connection.createStatement();
            stmt.executeUpdate(sql);
            this.connection.commit();

            stmt.close();
        } catch (SQLTimeoutException e) {
            this.throwThrowable.throwError(this.getClass(), e, null);
            return false;
        } catch ( SQLException e ) {
            this.throwThrowable.throwError(this.getClass(), e, null);
            return false;
        }
        return true;
    }

    /**
     * Executes the given SQL statement, which may be an <code>INSERT</code>,
     * <code>UPDATE</code>, or <code>DELETE</code> statement or an
     * SQL statement that returns nothing, such as an SQL DDL statement.
     *<p>
     * <strong>Note:</strong>This method cannot be called on a
     * <code>PreparedStatement</code> or <code>CallableStatement</code>.
     * @param sql an SQL Data Manipulation Language (DML) statement, such as <code>INSERT</code>, <code>UPDATE</code> or
     * <code>DELETE</code>; or an SQL statement that returns nothing,
     * such as a DDL statement.
     * Example: <code>"DELETE from COMPANY where ID=2;"</code>
     *
     * @return true if operation completed successfully and false if failed.
     *
     * @exception SQLException if a database access error occurs,
     * this method is called on a closed <code>Statement</code>, the given
     * SQL statement produces a <code>ResultSet</code> object, the method is called on a
     * <code>PreparedStatement</code> or <code>CallableStatement</code>
     * @throws SQLTimeoutException when the driver has determined that the
     * timeout value that was specified by the {@code setQueryTimeout}
     * method has been exceeded and has at least attempted to cancel
     * the currently running {@code Statement}
     */
    public boolean deleteOperation(String sql) {
        if (this.connection == null) return false;
        if (sql == null) return false;
        if (sql.isEmpty()) return false;

        Statement stmt = null;
        try {
            this.connection.setAutoCommit(false);

            stmt = this.connection.createStatement();
            stmt.executeUpdate(sql);
            this.connection.commit();

            stmt.close();
        } catch (SQLTimeoutException e) {
            this.throwThrowable.throwError(this.getClass(), e, "SQLTimeoutException");
            return false;
        } catch ( SQLException e ) {
            this.throwThrowable.throwError(this.getClass(), e, "no table with such name");
            return false;
        }
        return true;
    }

    public int getLastId(String tableName)
    {
        if (tableName.isEmpty())  this.throwThrowable.throwError(
                this.getClass(), new NullPointerException(), "table name is null");
        if (tableName.isEmpty())  this.throwThrowable.throwError(
                this.getClass(), new NullPointerException(), "table name is empty");

        String selectSQL = "SELECT MAX(id) FROM " + tableName;

        ArrayList result = this.selectOperation_ThematicQuery(selectSQL);
        if (((ArrayList)result.get(0)).get(0) != null)
            return (Integer)((ArrayList)result.get(0)).get(0);
        else return 0;
    }

    public static boolean psqlAvailable(String host, int port) {
        try {
            Socket s = new Socket(host, port);
            s.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
