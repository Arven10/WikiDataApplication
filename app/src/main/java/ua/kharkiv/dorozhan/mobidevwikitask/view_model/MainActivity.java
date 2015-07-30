package ua.kharkiv.dorozhan.mobidevwikitask.view_model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import ua.kharkiv.dorozhan.mobidevwikitask.R;
import ua.kharkiv.dorozhan.mobidevwikitask.model.WikiPageObject;

public class MainActivity extends AppCompatActivity {
    public final static String BROADCAST_ACTION = "ua.kharkiv.dorozhan.mobidevwikitask";
    public static String LIST_OF_WIKI_PAGES = "listOfWikiPages";
    private static String EMPTY_STRING = "";
    private static String SEARCH_TEXT = "text";
    private EditText searchEditText;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchEditText = (EditText)findViewById(R.id.searchEditText);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString();
                if (searchText.equals(EMPTY_STRING)) {
                    Toast message = Toast.makeText(getApplicationContext(),
                            R.string.toast_valid_message, Toast.LENGTH_SHORT);
                    message.show();
                } else {
                    startWikiService(searchText);
                }
            }
        });
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                ArrayList<WikiPageObject> wikiPageObjects = extras.getParcelableArrayList(
                        LIST_OF_WIKI_PAGES);
                Toast message = Toast.makeText(getApplicationContext(), R.string.toast_query_message,
                        Toast.LENGTH_SHORT);
                message.show();
                startWikiPageActivity(wikiPageObjects);
            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void startWikiService(String searchText) {
        Intent intent = new Intent(this, WikiService.class);
        intent.putExtra(SEARCH_TEXT, searchText);
        startService(intent);
    }

    private void startWikiPageActivity(ArrayList<WikiPageObject> wikiPageObjects) {
        Intent intent = new Intent(this, WikiListActivity.class);
        intent.putParcelableArrayListExtra(LIST_OF_WIKI_PAGES, wikiPageObjects);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
