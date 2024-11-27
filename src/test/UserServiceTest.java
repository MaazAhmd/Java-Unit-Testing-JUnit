package test;

import main.mockitoTesting.UserRepository;
import main.mockitoTesting.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    public void testGetUserDetails_UserFound() {
        // We create a Mock of the UserRepository interface using Mockito's mock() method.
        // This allows us to simulate the behavior of the repository without interacting with a real database.
        UserRepository mockRepository = mock(UserRepository.class);

        // Define the behavior of the mock
        when(mockRepository.findUserById(1)).thenReturn("John Doe");
        when(mockRepository.findUserById(2)).thenReturn("Wahaj");

        // Create UserService with the mock
        // UserService is being tested here. We inject the mock repository into the service constructor.
        UserService userService = new UserService(mockRepository);

        // Call the method under test
        // We call the getUserDetails method of the UserService, passing in 1 and 2.
        // The method should return the correct user details based on the mock repository.
        String result = userService.getUserDetails(1);
        String result2 = userService.getUserDetails(2);

        // Verify the result
        // We check if the results from the getUserDetails method match the expected values.
        // We are expecting "User Found: John Doe" for the first call and "User Found: Wahaj" for the second call.
        assertEquals("User Found: John Doe", result);
        assertEquals("User Found: Wahaj", result2);

        // Verify interaction with the mock
        verify(mockRepository, times(1)).findUserById(1);
        verify(mockRepository, times(1)).findUserById(2);
    }

    @Test
    public void testGetUserDetails_UserNotFound() {
        // Mock the UserRepository
        UserRepository mockRepository = mock(UserRepository.class);

        // Define the behavior of the mock
        when(mockRepository.findUserById(2)).thenReturn(null);

        // Create UserService with the mock
        UserService userService = new UserService(mockRepository);

        // Call the method under test
        String result = userService.getUserDetails(2);

        // Verify the result
        // Assert that the result from the getUserDetails method matches the expected value.
        // In this case, we expect the method to return "User Not Found" because the mock returns null.
        assertEquals("User Not Found", result);

        // Verify interaction with the mock
        // Verify that the mock repository's findUserById method was called exactly once with the argument 2.
        // This ensures that the UserService interacted with the repository correctly.
        verify(mockRepository, times(1)).findUserById(2);
    }
}
