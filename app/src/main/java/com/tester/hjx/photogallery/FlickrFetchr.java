package com.tester.hjx.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//https://api.m.panda.tv/ajax_card_newlist?cate=index&__plat=android&__version=4.0.17.7419&__channel=shoujizhushou

public class FlickrFetchr {
    private static final String TAG = "FlickrFetchr";
//    private static final String API_KEY = "_yourApiKeyHere_";
    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+": with" + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new  byte[1024];
            while ((bytesRead = in.read(buffer))>0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();

        }finally {
            connection.disconnect();
        }
    }


    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<GalleryItem> fetchItems(){
        List<GalleryItem> items = new ArrayList<>();
        try {
            String url = Uri.parse("https://api.m.panda.tv/ajax_card_newlist?")
                    .buildUpon()
                    .appendQueryParameter("cate","index")
                    .appendQueryParameter("__plat","android")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: "+jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items,jsonBody);
        }catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        }catch (JSONException je){
            Log.e(TAG,"Fail to parse JSON",je);
        }
        return items;
    }

    private void parseItems(List<GalleryItem> items, JSONObject jsonBody) throws IOException, JSONException{
//        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = jsonBody.getJSONArray("data");
        JSONObject photosJsonObject = photoJsonArray.getJSONObject(0);
        JSONArray pjA = photosJsonObject.getJSONArray("items");
        for (int i=0; i<pjA.length();i++){
            JSONObject photoJsonObject = pjA.getJSONObject(i);
            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("name"));
            if(!photoJsonObject.has("img")) {
                continue;
                //for 循环中遇到break则跳出for语句，执行for循环后面的语句。 遇到continue则结束此次循环，不执行for 循环中continue后面的语句，判断循环条件，执行下次循环
            }
            item.setUrl(photoJsonObject.getString("img"));
            items.add(item);
        }
    }
}

