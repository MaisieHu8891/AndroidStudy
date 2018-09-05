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
//https://api.m.panda.tv/ajax_search_all?keyword=7000&__plat=android&__version=4.0.18.7465&__channel=huawei
//https://api.m.panda.tv/ajax_card_newlist?cate=index&__plat=android&__version=4.0.17.7419&__channel=shoujizhushou

public class FlickrFetchr {
    private static final String TAG = "FlickrFetchr";
    private  static final String INDEX = "ajax_slider_cate?cate=index";
    private static final String SEARCH = "ajax_search_all?keyword=7000";

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

    private String buildUrl(String difurl){
        String url = Uri.parse("https://api.m.panda.tv/"+difurl).buildUpon()
                .appendQueryParameter("cate","index")
                .appendQueryParameter("__plat","android")
                .build().toString();
        return url;
    }

    private List<GalleryItem> downloadGalleryItems(String url){
        List<GalleryItem> items = new ArrayList<>();
        try {
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: "+jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            if(url.contains(INDEX)){
                parseIndexItems(items,jsonBody);
            }

            if(url.contains(SEARCH)){
                parseSearchItems(items,jsonBody);
            }
        }catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        }catch (JSONException je){
            Log.e(TAG,"Fail to parse JSON",je);
        }
        return items;
    }

    public List<GalleryItem> fetchIndexPhotos(){
        String url = buildUrl(INDEX);
        return downloadGalleryItems(url);
    }

    public List<GalleryItem> searchPhotos(){
        String url = buildUrl(SEARCH);
        return downloadGalleryItems(url);
    }

    private void parseIndexItems(List<GalleryItem> items, JSONObject jsonBody) throws IOException, JSONException{
        JSONObject photosJsonObject = jsonBody.getJSONObject("data");
        JSONArray pjA = photosJsonObject.getJSONArray("banners");
        for (int i=0; i<pjA.length();i++){
            JSONObject photoJsonObject = pjA.getJSONObject(i);
            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("roomid"));
            item.setCaption(photoJsonObject.getString("title"));
            if(!photoJsonObject.has("img")) {
                continue;
                //for 循环中遇到break则跳出for语句，执行for循环后面的语句。 遇到continue则结束此次循环，不执行for 循环中continue后面的语句，判断循环条件，执行下次循环
            }
            item.setUrl(photoJsonObject.getString("img"));
            items.add(item);
        }
    }

    private void parseSearchItems(List<GalleryItem> items, JSONObject jsonBody) throws IOException, JSONException{

        JSONObject photoJsonObject = jsonBody.getJSONObject("data");
        JSONObject photoJsonObject_2 = photoJsonObject.getJSONObject("rooms");
        JSONArray pjA = photoJsonObject_2.getJSONArray("items");
        for (int i=0; i<pjA.length();i++){
            JSONObject photoJsonObject_3 = pjA.getJSONObject(i);
            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject_3.getString("id"));
            item.setCaption(photoJsonObject_3.getString("name"));
            if(!photoJsonObject_3.has("img")) {
                continue;
                //for 循环中遇到break则跳出for语句，执行for循环后面的语句。 遇到continue则结束此次循环，不执行for 循环中continue后面的语句，判断循环条件，执行下次循环
            }
            item.setUrl(photoJsonObject_3.getString("img"));
            items.add(item);
        }
    }
}

