package ua.kharkiv.dorozhan.mobidevwikitask.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ServerData {
    private static final String GET = "GET";
    private static final String WIKI_MAIN_URL = "https://en.wikipedia.org/";
    private static final String WIKI_QUERY_URL = "w/api.php?action=query&list=allpages&" +
            "format=json&rawcontinue=&apfrom=";
    private static final String QUERY = "query";
    private static final String ALL_PAGES = "allpages";
    private static final String PAGE_ID = "pageid";
    private static final String TITLE = "title";

    public static ArrayList<WikiPageObject> getWikiData(String pageTitle) throws IOException, JSONException {
        URL url = new URL(WIKI_MAIN_URL + WIKI_QUERY_URL + pageTitle);
        HttpURLConnection connectionToServer = getHttpConnectToServer(url, GET);
        ArrayList<WikiPageObject> wikiPageObjects = receiveDataFromServer(connectionToServer);
        return wikiPageObjects;
    }

    private static HttpURLConnection getHttpConnectToServer(URL url, String typeOfRequest) throws IOException {
        URLConnection connection = url.openConnection();
        HttpURLConnection httpConnection = (HttpURLConnection) connection;
        httpConnection.setRequestMethod(typeOfRequest);
        httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setDoInput(true);
        httpConnection.setDoOutput(true);
        return httpConnection;
    }

    private static ArrayList<WikiPageObject> receiveDataFromServer(HttpURLConnection connectionToServer)
            throws IOException, JSONException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connectionToServer.getInputStream()));
        String next;
        ArrayList<WikiPageObject> listOfWikiPageObjects = new ArrayList<>();
        while ((next = bufferedReader.readLine()) != null){
            JSONObject jsonWikiObject = new JSONObject(next);
            JSONArray jsonWikiPageObjects = jsonWikiObject.getJSONObject(QUERY).getJSONArray(ALL_PAGES);
            for (int i = 0; i < jsonWikiPageObjects.length(); i++) {
                JSONObject jsonWikiPageObject = (JSONObject) jsonWikiPageObjects.get(i);
                WikiPageObject wikiPageObject = new WikiPageObject();
                wikiPageObject.setPageId(jsonWikiPageObject.getString(PAGE_ID));
                wikiPageObject.setTitle(jsonWikiPageObject.getString(TITLE));
                listOfWikiPageObjects.add(wikiPageObject);
            }
        }
        return listOfWikiPageObjects;
    }
}