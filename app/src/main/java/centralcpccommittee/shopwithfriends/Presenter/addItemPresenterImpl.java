package centralcpccommittee.shopwithfriends.Presenter;

import centralcpccommittee.shopwithfriends.AddItemActivity;
import centralcpccommittee.shopwithfriends.AddItemView;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.AddItemState;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.DPState;

/**
 * Created by Yuhui on 4/18/2015.
 */
public class AddItemPresenterImpl implements AddItemPresenter {
    private String userEmail;
    private String itemName;
    private double price;
    private double latitude;
    private double longitude;
    private DataProcessor dataProcessor;
    private AddItemView view;

    public AddItemPresenterImpl(String userEmail, String itemName, double price, double latitude, double longitude, AddItemView view) {
        this.userEmail = userEmail;
        this.itemName = itemName;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
        dataProcessor = new DataProcessor();
        this.view = view;
    }
    public void addItem() {
        dataProcessor.setState(new AddItemState(this, userEmail, itemName, price, latitude, longitude));
        dataProcessor.process();
    }
    public void addNotExistItem(String itemName) {
        view.addNotExistItem(itemName);
    }

    public void updateExistItem(String itemName, double price) {
        view.updateExistItem(itemName, price);
    }
}
