package com.tester.hjx.criminallntent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PictureUtils {
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight){
        BitmapFactory.Options options =  new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);
    }
}
