package test;

import main.Product;
import main.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import java.util.Optional;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

// Mock WebServerUrl annotation
@interface WebServerUrl {
}

// Mock WebServerExtension class
class WebServerExtension implements BeforeEachCallback,AfterAllCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        System.out.println("WebServerExtension Before Each Callback");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        System.out.println("WebServerExtension: Tearing down the mock web server...");
    }
}

// Mock DatabaseExtension class
class DatabaseExtension implements BeforeEachCallback, AfterAllCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        System.out.println("DatabaseExtension: Setting up the test database...");
        // Simulate database setup
    }

    @Override
    public void afterAll(ExtensionContext context) {
        System.out.println("DatabaseExtension: Cleaning up the test database...");
        // Simulate database cleanup
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
        assertEquals(200, response.getResponseStatus(), "Response status should be 200.");
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

// 3. TestWatcher Implementation
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

