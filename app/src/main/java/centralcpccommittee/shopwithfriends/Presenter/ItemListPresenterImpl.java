package centralcpccommittee.shopwithfriends.Presenter;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.ItemListState;
import centralcpccommittee.shopwithfriends.ItemListView;

/**
 * Created by Yuhui on 4/18/2015.
 */
public class ItemListPresenterImpl implements ItemListPresenter {
    private String email;
    private ItemListView view;
    private DataProcessor dataProcessor;

    public ItemListPresenterImpl(String email, ItemListView view) {
        this.email = email;
        this.view = view;
        this.dataProcessor = new DataProcessor();
    }

    public void populateListView() {
        dataProcessor.setState(new ItemListState(email, this));
        dataProcessor.process();
    }

    @Override
    public void displayList(String[] items) {
        view.displayList(items);
    }
}
