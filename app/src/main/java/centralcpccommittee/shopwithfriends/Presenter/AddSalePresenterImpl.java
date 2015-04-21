package centralcpccommittee.shopwithfriends.Presenter;

import centralcpccommittee.shopwithfriends.AddSaleView;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.AddSaleSate;

/**
 * Created by Yuhui on 4/21/2015.
 */
public class AddSalePresenterImpl implements AddSalePresenter {
    private String userEmail;
    private double price;
    private double latitude;
    private double longitude;
    private String saleName;
    private AddSaleView view;
    private DataProcessor dataProcessor;

    public AddSalePresenterImpl(String userEmail, double price, String saleName,double latitude, double longitude, AddSaleView view) {
        this.userEmail = userEmail;
        this.price = price;
        this.saleName = saleName;
        this.view = view;
        this.latitude = latitude;
        this.longitude = longitude;
        dataProcessor = new DataProcessor();
    }

    @Override
    public void attemptAddSale() {
        view.initializeError();
        dataProcessor.setState(new AddSaleSate(userEmail, price, latitude, longitude, saleName, this));
        dataProcessor.process();
        view.backToMain("Your sale has been successfully reported");
    }
}
