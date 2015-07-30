package ua.kharkiv.dorozhan.mobidevwikitask.view_model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ua.kharkiv.dorozhan.mobidevwikitask.R;
import ua.kharkiv.dorozhan.mobidevwikitask.model.WikiPageObject;

public class DataListAdapter extends BaseAdapter {
    private ArrayList<WikiPageObject> wikiPageObjects;
    private Context context;
    private LayoutInflater inflater;

    public DataListAdapter(Context context, ArrayList<WikiPageObject> wikiPageObjects) {
        this.wikiPageObjects = wikiPageObjects;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public int getCount() {
        return wikiPageObjects.size();
    }

    public WikiPageObject getItem(int position) {
        return wikiPageObjects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.wiki_page_object, parent, false);
        TextView title = (TextView) view.findViewById(R.id.titleTextView);
        TextView id = (TextView) view.findViewById(R.id.idTextView);
        TextView number = (TextView) view.findViewById(R.id.numberTextView);
        String pageTitle = context.getString(R.string.page_title);
        String pageId = context.getString(R.string.page_id);
        title.setText(pageTitle + getItem(position).getTitle());
        id.setText(pageId + getItem(position).getPageId());
        number.setText(String.valueOf(++position));
        return view;
    }
}
