package centralcpccommittee.shopwithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Map;

import centralcpccommittee.shopwithfriends.Presenter.SaleListPresenter;
import centralcpccommittee.shopwithfriends.Presenter.SaleListPresenterImpl;

public class SaleListActivity extends ActionBarActivity implements SaleListView{
    private String userEmail;
    private SaleListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_list);
        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        presenter.populateListView();
    }

    public void MainActivityPage(@SuppressWarnings("UnusedParameters") View view) {
        Intent move = new Intent(this, MainActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
        finish();
    }
    public void itemOnMapPressed(@SuppressWarnings("UnusedParameters") View view) {
        Intent move = new Intent(this, SalesOnMapActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
        finish();
    }
    public void displayList(String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.items_font,
                items);
        ListView list = (ListView) findViewById(R.id.sale_list_view);
        list.setAdapter(adapter);
    }
}
