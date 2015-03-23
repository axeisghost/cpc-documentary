package centralcpccommittee.shopwithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Map;

public class ItemListActivity extends ActionBarActivity {

//    private dataExchanger mData = dataExchanger.getInstance();
    private String userEmail;
    private UserProfile user;
    private Map<String, Map> itemMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        user = new UserProfile(userEmail);
        itemMap = user.getItemList();
        populateListView();
    }


    private void populateListView() {
        String[] items;
        if (!itemMap.isEmpty()) {
            int size = itemMap.size();
            items = new String[size];
            int i = 0;
            for (Map.Entry<String, Map> element: itemMap.entrySet()) {
                items[i] = element.getKey().toString();
                items[i] = items[i] + " : ";
                items[i] = items[i] + element.getValue().get("price").toString();
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
}
