package app.services;

public interface AuthService {
    boolean authenticate(String login, String password);
}
