package test;

import main.mockitoTesting.UserRepository;
import main.mockitoTesting.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    public void testGetUserDetails_UserFound() {
        // Mock the UserRepository
        UserRepository mockRepository = mock(UserRepository.class);

        // Define the behavior of the mock
        when(mockRepository.findUserById(1)).thenReturn("John Doe");
        when(mockRepository.findUserById(2)).thenReturn("Wahaj");

        // Create UserService with the mock
        UserService userService = new UserService(mockRepository);

        // Call the method under test
        String result = userService.getUserDetails(1);
        String result2 = userService.getUserDetails(2);

        // Verify the result
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
        assertEquals("User Not Found", result);

        // Verify interaction with the mock
        verify(mockRepository, times(1)).findUserById(2);
    }
}
