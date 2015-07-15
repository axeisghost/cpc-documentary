package centralcpccommittee.shopwithfriends.DataHandler;

/**
 * Created by Yuhui on 4/8/2015.
 */
public class User {
    private String email;
    private String name;
    private String password;
    private double rating;



    public static final int DEFAULT_RATING = -1;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        rating = DEFAULT_RATING;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
