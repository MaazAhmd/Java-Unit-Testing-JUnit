package test;

import main.Product;
import main.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
// Mock WebServerUrl annotation
@interface WebServerUrl {
}

// Mock WebServerExtension class
class WebServerExtension implements BeforeEachCallback,AfterAllCallback,BeforeAllCallback {

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


// 1. Web Server Extension
@ExtendWith(WebServerExtension.class)
@ExtendWith(DatabaseExtension.class) // 2. Database Extension
class ProductServiceTest {
    private ProductService productService;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All Tests");
    }

    @BeforeEach
    void setUp() {
        productService = new ProductService();
        System.out.println("Before Each Test");
    }

    @Test
    void getProductList() {
        WebClient webClient = new WebClient();
        @WebServerUrl String serverUrl="http://localhost";
        // Simulate calling the WebServer URL and getting a response
        String url = serverUrl + "/products";
        Response response = webClient.get(url); // Direct call to WebClient

        // Assert that the response status is 200 (OK)
        assertEquals(200, response.getResponseStatus(), "Web Server is not responding properly");
    }

    @Test
    void addProductTest() {
        boolean isAdded = productService.addProduct(new Product(4,"Car","It is a Crolla GLI",1000));
        assertTrue(isAdded, "Product should be added successfully.");
    }

    @Test
    void testProductListSize() {
        assertEquals(3, productService.getProducts().size(), "Product list should have 3 products.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After Each Test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All Tests");
    }
}


class CustomTestWatcher implements TestWatcher {
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        System.out.printf("Test Disabled: %s, Reason: %s%n", context.getDisplayName(), reason.orElse("No reason provided."));
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        System.out.printf("Test Successful: %s%n", context.getDisplayName());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        System.out.printf("Test Aborted: %s, Cause: %s%n", context.getDisplayName(), cause.getMessage());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        System.out.printf("Test Failed: %s, Cause: %s%n", context.getDisplayName(), cause.getMessage());
    }
}

// 4. Lifecycle Callbacks Implementation
class LifecycleCallbacksTest implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) {
        System.out.println("Custom Before All Callback");
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        System.out.println("Custom Before Each Callback");
    }

    @Override
    public void afterEach(ExtensionContext context) {
        System.out.println("Custom After Each Callback");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        System.out.println("Custom After All Callback");
    }
}

// Mocked WebClient class for web requests
class WebClient {
    public Response get(String url) {
        return new Response(200); // Simulating HTTP 200 OK response
    }
}

// Mocked Response class
class Response {
    private final int status;

    public Response(int status) {
        this.status = status;
    }

    public int getResponseStatus() {
        return status;
    }
}

