package centralcpccommittee.shopwithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.Map;

import centralcpccommittee.shopwithfriends.Presenter.ItemListPresenter;
import centralcpccommittee.shopwithfriends.Presenter.ItemListPresenterImpl;

public class ItemListActivity extends ActionBarActivity implements ItemListView {

    private String userEmail;
    private Map<String, JSONArray> itemMap;
    private ItemListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        presenter = new ItemListPresenterImpl(userEmail, this);
        presenter.populateListView();
}

    public void displayList(String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.items_font,
                items);
        ListView list = (ListView) findViewById(R.id.item_list_view);
        list.setAdapter(adapter);
    }
    public void MainActivityPage(@SuppressWarnings("UnusedParameters") View view) {
        Intent move = new Intent(this, MainActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
        finish();
    }
    public void itemOnMapPressed(@SuppressWarnings("UnusedParameters") View view) {
        Intent move = new Intent(this, ItemsOnMapActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
        finish();
    }
}
