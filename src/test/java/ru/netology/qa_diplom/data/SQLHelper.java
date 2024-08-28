package ru.netology.qa_diplom.data;


import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var conn = getConn();
        runner.execute(conn, "DELETE FROM order_entity");
        runner.execute(conn, "DELETE FROM payment_entity");
        runner.execute(conn, "DELETE FROM credit_request_entity");
    }

    @SneakyThrows
    public static Connection getConn() {
        String dbUrl = System.getProperty("db.url");
        String login = System.getProperty("login");
        String password = System.getProperty("password");
        return DriverManager.getConnection(dbUrl, login, password);
    }

    @SneakyThrows
    public static String getStatusPayment() {
        String status = "SELECT status FROM payment_entity order by created DESC LIMIT 1";
        Connection conn = getConn();
        return runner.query(conn, status, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getStatusCredit() {
        String status = "SELECT status FROM credit_request_entity order by created DESC LIMIT 1";
        Connection conn = getConn();
        return runner.query(conn, status, new ScalarHandler<>());
    }

}
