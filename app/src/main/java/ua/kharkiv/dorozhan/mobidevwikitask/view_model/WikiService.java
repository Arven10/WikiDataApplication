package ua.kharkiv.dorozhan.mobidevwikitask.view_model;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ua.kharkiv.dorozhan.mobidevwikitask.model.ServerData;
import ua.kharkiv.dorozhan.mobidevwikitask.model.WikiPageObject;

public class WikiService extends Service {
    private static String SEARCH_TEXT = "text";
    private String searchText;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        searchText = intent.getStringExtra(SEARCH_TEXT);
        WikiData wikiData = new WikiData();
        wikiData.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class WikiData extends AsyncTask<Void, Void, List<WikiPageObject>> {
        ArrayList<WikiPageObject> wikiPageObjects;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<WikiPageObject> doInBackground(Void... params) {
            Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
            try {
                wikiPageObjects = ServerData.getWikiData(searchText);
                intent.putParcelableArrayListExtra(MainActivity.LIST_OF_WIKI_PAGES, wikiPageObjects);
                sendBroadcast(intent);
                stopSelf();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return wikiPageObjects;
        }

        @Override
        protected void onPostExecute(List<WikiPageObject> wikiPageObjects) {
            super.onPostExecute(wikiPageObjects);
        }
    }
}
