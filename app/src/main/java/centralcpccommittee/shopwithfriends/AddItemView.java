package centralcpccommittee.shopwithfriends;

/**
 * Created by Yuhui on 4/18/2015.
 */
public interface AddItemView {
    public void initializeError();
    public void addNotExistItem(String itemName);
    public void updateExistItem(String itemName, double price);
}
