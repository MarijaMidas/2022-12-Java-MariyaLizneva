package app.crm.model;

public class Admin {

    private final long id;
    private final String login;
    private final String password;

    public Admin(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
