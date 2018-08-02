package com.tester.hjx.criminallntent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.tester.hjx.criminallntent.database.CrimeBaseHelper;
import com.tester.hjx.criminallntent.database.CrimeDbSchema;
import com.tester.hjx.criminallntent.database.CrimeDbSchema.CrimeTable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
//    private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimes = new ArrayList<>();
//        for (int i=0; i<100; i++){
//            Crime crime = new Crime();
//            crime.setTitle("Crime #"+i);
//            crime.setSolved(i%2 == 0);
//            mCrimes.add(crime);
//        }
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle().toString());
        values.put(CrimeTable.Cols.DATE, crime.getDate().toString());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved()? 1:0);
        return values;

    }


    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);



//        mCrimes.add(c);
    }

    public List<Crime> getmCrimes() {
        return new ArrayList<>();

//        return mCrimes;
    }

    public Crime getCrime(UUID id){
//        for (Crime crime: mCrimes){
//            if (crime.getId().equals(id)){
//                return crime;
//            }
//        }
        return null;
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID+"=?",new String[]{uuidString});
    }


}
