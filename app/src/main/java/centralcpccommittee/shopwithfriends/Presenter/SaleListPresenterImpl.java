package centralcpccommittee.shopwithfriends.Presenter;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.SaleListState;
import centralcpccommittee.shopwithfriends.SaleListView;

/**
 * Created by Yuhui on 4/22/2015.
 */
public class SaleListPresenterImpl implements SaleListPresenter {
    private String email;
    private SaleListView view;
    private DataProcessor dataProcessor;

    public SaleListPresenterImpl(String email, SaleListView view) {
        this.email = email;
        this.view = view;
        this.dataProcessor = new DataProcessor();
    }

    public void populateListView() {
        dataProcessor.setState(new SaleListState(email, this));
        dataProcessor.process();
    }

    @Override
    public void displayList(String[] items) {
        view.displayList(items);
    }
}
