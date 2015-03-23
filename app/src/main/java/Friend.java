/**
 * Created by Yuhui on 3/22/2015.
 */
public class Friend {
    public String id;
    public String content;

    public Friend(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return id;
    }
}
