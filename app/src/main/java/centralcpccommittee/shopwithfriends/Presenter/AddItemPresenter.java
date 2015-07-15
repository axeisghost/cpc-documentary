package centralcpccommittee.shopwithfriends.Presenter;

/**
 * Created by Yuhui on 4/18/2015.
 */
public interface AddItemPresenter {
    public void addItem();
    public void updateExistItem(String itemName, double price);
    public void addNotExistItem(String temName);
}
