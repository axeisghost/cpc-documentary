package centralcpccommittee.shopwithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Map;

public class ItemListActivity extends ActionBarActivity {

//    private dataExchanger mData = dataExchanger.getInstance();
    private String userEmail;
    private UserProfile user;
    private Map<String, JSONArray> itemMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        user = new UserProfile(userEmail);
        itemMap = user.getItemList();
        try {
            populateListView();
        } catch (Exception e) {
            System.out.println("What am I supposed to do!!!!");
        }

}


    private void populateListView() throws Exception{
        String[] items;
        if (!itemMap.isEmpty()) {
            int size = itemMap.size();
            items = new String[size];
            int i = 0;
            for (Map.Entry<String,JSONArray> element: itemMap.entrySet()) {
                items[i] = element.getKey().toString();
                items[i] = items[i] + " : ";
                JSONArray jData = element.getValue();
                items[i] = items[i] + jData.getDouble(0);
                i = i + 1;
            }
        } else {
            items = new String[1];
            items[0] = "";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.items_font,
                    items);
        ListView list = (ListView) findViewById(R.id.item_list_view);
        list.setAdapter(adapter);
    }

    public void MainActivityPage(View view) {
        Intent move = new Intent(this, MainActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
        finish();
    }
    public void itemOnMapPressed(View view) {
        Intent move = new Intent(this, ItemsOnMapActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
        finish();
    }
}
