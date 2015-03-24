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

public class SaleListActivity extends ActionBarActivity {

//    private dataExchanger mData = dataExchanger.getInstance();
    private String userEmail;
    private UserProfile user;
    private Map<String, JSONArray> itemMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_list);
        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        user = new UserProfile(userEmail);
        itemMap = user.getSaleList();
        populateListView();
    }


    private void populateListView() {
        String[] items;
        if (!itemMap.isEmpty()) {
            int size = itemMap.size();
            items = new String[size];
            int i = 0;
            for (Map.Entry<String, JSONArray> element: itemMap.entrySet()) {
                JSONArray arr = element.getValue();
                double p = 0;
                String l = "";
                try {
                    p = arr.getDouble(0);
                } catch(JSONException e) {
                    Log.d("JSONException", "Unexcepted JSON Exception. name should be existed");
                }
                try {
                    l = arr.getString(1);
                } catch(JSONException e) {
                    Log.d("JSONException", "Unexcepted JSON Exception. name should be existed");
                }
                items[i] = "Name: " + element.getKey().toString()
                            + "  Price: " + Double.toString(p)
                            + "  Location: " + l;
                i = i + 1;
            }
        } else {
            items = new String[1];
            items[0] = "";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.items_font,
                    items);
        ListView list = (ListView) findViewById(R.id.sale_list_view);
        list.setAdapter(adapter);
    }

    public void MainActivityPage(View view) {
        Intent move = new Intent(this, MainActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
        finish();
    }
}
