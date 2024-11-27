package main.mockitoTesting;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserDetails(int id) {
        String user = userRepository.findUserById(id);
        return user != null ? "User Found: " + user : "User Not Found";
    }
}

