package test;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Mock WebServerUrl annotation
@interface WebServerUrl {
}

// Mock WebServerExtension class
class WebServerExtension implements BeforeEachCallback, AfterAllCallback, BeforeAllCallback {

    private static HttpServer server;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        try {
            // Setting up the server
            server = HttpServer.create(new InetSocketAddress(8081), 0);
            server.createContext("/products", exchange -> {
                String response = "[{\"id\":1,\"name\":\"Laptop\",\"price\":1200}]";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
            });
            server.start();
            System.out.println("Web server started successfully on port 8080.");
        } catch (IOException e) {
            System.err.println("Failed to start web server.");
            e.printStackTrace();
            throw new RuntimeException("Web server setup failed. Aborting tests.");
        }
    }
    @Override
    public void beforeEach(ExtensionContext context) {
        System.out.println("WebServerExtension Before Each Callback");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (server != null) {
            server.stop(0); // Gracefully stop the server
            System.out.println("Web server stopped.");
        }
    }


}

// Mock DatabaseExtension class
class DatabaseExtension implements  AfterAllCallback,BeforeAllCallback {
    private Connection connection;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception
    {
        String url = "jdbc:sqlserver://TAYYAB-PC1234;databaseName=AdventureGame;encrypt=true;trustServerCertificate=true";
        String username = "tks2";
        String password = "1234";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database. Aborting tests.");

        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("DatabaseExtension: Cleaning up the test database...");
        try {
            connection.close();
            System.out.println("Databse Connection Closed");
        } catch (SQLException e) {
            System.err.println("Database Connection close error");
            e.printStackTrace();
            throw new RuntimeException("Failed to close DB Connection");

        }

    }

}
