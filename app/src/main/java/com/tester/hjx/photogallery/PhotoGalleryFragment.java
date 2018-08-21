package com.tester.hjx.photogallery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PhotoGalleryFragment extends Fragment {
    private static final String TAG = "PhotoGalleryFragment";

    private RecyclerView mPhotoRecyclerView;

    private class FetchItemsTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
//            try {
////                https://api.m.panda.tv/ajax_card_newlist?cate=index&__plat=android&__version=4.0.17.7419&__channel=shoujizhushou
//                String result = new FlickrFetchr().getUrlString("https://www.bignerdranch.com");
//                Log.i(TAG, "Fetch contents of URL: "+result);
//            }catch (IOException ioe){
//                Log.e(TAG,"Failed to fetch URL: ",ioe);
//            }
            new FlickrFetchr().fetchItems();
            return null;
        }
    }
    public static PhotoGalleryFragment newInstance(){
        return new PhotoGalleryFragment();
    }
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View v = inflater.inflate(R.layout.fragment_photo_gallery,container,false);
        mPhotoRecyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        return v;
    }

}
