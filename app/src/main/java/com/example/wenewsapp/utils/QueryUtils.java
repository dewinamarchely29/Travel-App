package com.example.wenewsapp.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.wenewsapp.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final int READ_TIMEOUT = 10000; //milliseconds //
    private static final int CONNECT_TIMEOUT = 15000; //milliseconds //
    private static final int SUCCESS_RESPONSE_CODE = 200;
    private static final String REQUEST_METHOD_GET = "GET";
    private static final String JSON_KEY_RESPONSE = "response";
    private static final String JSON_KEY_RESULTS = "results";
    private static final String JSON_KEY_WEB_TITLE = "webTitle";
    private static final String JSON_KEY_SECTION_NAME = "sectionName";
    private static final String JSON_KEY_WEB_PUBLICATION_DATE = "webPublicationDate";
    private static final String JSON_KEY_WEB_URL = "webUrl";
    private static final String JSON_KEY_TAGS = "tags";
    private static final String JSON_KEY_FIELDS = "fields";
    private static final String JSON_KEY_THUMBNAIL = "thumbnail";
    private static final String JSON_KEY_TRAIL_TEXT = "trailText";

    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<News> newsList = extractFeatureFromJSON(jsonResponse);
        return newsList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL.", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT /* milliseconds */);
            urlConnection.setRequestMethod(REQUEST_METHOD_GET);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == SUCCESS_RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJSON(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject responseJsonObject = baseJsonResponse.getJSONObject(JSON_KEY_RESPONSE);
            JSONArray resultsArray = responseJsonObject.getJSONArray(JSON_KEY_RESULTS);
            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentNews = resultsArray.getJSONObject(i);
                String webTitle = currentNews.getString(JSON_KEY_WEB_TITLE);
                String sectionName = currentNews.getString(JSON_KEY_SECTION_NAME);
                String webPublicationDate = currentNews.getString(JSON_KEY_WEB_PUBLICATION_DATE);
                String webUrl = currentNews.getString(JSON_KEY_WEB_URL);

                String author = null;
                if (currentNews.has(JSON_KEY_TAGS)) {
                    JSONArray tagsArray = currentNews.getJSONArray(JSON_KEY_TAGS);
                    if (tagsArray.length() != 0) {
                        JSONObject firstTagsItem = tagsArray.getJSONObject(0);
                        author = firstTagsItem.getString(JSON_KEY_WEB_TITLE);
                    }
                }
                String thumbnail = null;
                String trailText = null;
                if (currentNews.has(JSON_KEY_FIELDS)) {
                    JSONObject fieldsObject = currentNews.getJSONObject(JSON_KEY_FIELDS);
                    if (fieldsObject.has(JSON_KEY_THUMBNAIL)) {
                        thumbnail = fieldsObject.getString(JSON_KEY_THUMBNAIL);
                    }
                    if (fieldsObject.has(JSON_KEY_TRAIL_TEXT)) {
                        trailText = fieldsObject.getString(JSON_KEY_TRAIL_TEXT);
                    }
                }
                News news = new News(webTitle, sectionName, author, webPublicationDate, webUrl, thumbnail, trailText);
                newsList.add(news);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }
        return newsList;
    }
}
