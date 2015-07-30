package ua.kharkiv.dorozhan.mobidevwikitask.view_model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import ua.kharkiv.dorozhan.mobidevwikitask.R;
import ua.kharkiv.dorozhan.mobidevwikitask.model.WikiPageObject;

public class WikiListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_wiki);
        Bundle extras = getIntent().getExtras();
        ListView wikiPageObjectView = (ListView)findViewById(R.id.wiki_page_objects);
        ArrayList<WikiPageObject> wikiPageObjects = extras.getParcelableArrayList(
                MainActivity.LIST_OF_WIKI_PAGES);
        DataListAdapter wikiPageObjectAdapter = new DataListAdapter(this, wikiPageObjects);
        wikiPageObjectView.setAdapter(wikiPageObjectAdapter);
    }
}
