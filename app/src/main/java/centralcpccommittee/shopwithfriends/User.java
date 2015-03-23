package centralcpccommittee.shopwithfriends;

import java.util.Map;

/**
 * Created by Yuhui on 3/20/2015.
 */
public class User {
    private String username;
    private String password;
    private String email;


    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
