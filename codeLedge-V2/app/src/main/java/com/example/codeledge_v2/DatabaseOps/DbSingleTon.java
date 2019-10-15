package com.example.codeledge_v2.DatabaseOps;

import android.database.sqlite.SQLiteDatabase;

import com.example.codeledge_v2.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;

public class DbSingleTon {
    //create an object of SingleObject
    private static DbSingleTon instance = new DbSingleTon();
    private static SQLiteDatabase db;
    private static DbHelper dbHelper;
    private static String DB_NAME = "codeledge.db";;
    private static MainActivity activity;

    //make the constructor private so that this class cannot be
    //instantiated
    private DbSingleTon(){
    }

    //Get the only object available
    public static DbSingleTon getInstance(){
        return instance;
    }

    public void CreateSqliteTableWithData(){
        if (activity == null) {
            throw new IllegalArgumentException("Main activity not initialized");
        }
        //For debugging
        activity.deleteDatabase(DB_NAME);

        File dbFile = activity.getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {
            copyDatabase(dbFile);
        }
        db = activity.openOrCreateDatabase(DB_NAME,
                MODE_PRIVATE, null);

        dbHelper = DbHelper.getInstance();
        dbHelper.assignSqliteDatabaseReference(db);
        dbHelper.CreateTablenData();
        dbHelper.LoadData();
    }

    private static void copyDatabase(File dbFile) {
        if (activity == null) {
            throw new IllegalArgumentException("Main activity not initialized");
        }
        try {
            InputStream is = activity.getAssets().open(DB_NAME);
            OutputStream os = new FileOutputStream(dbFile);

            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }

            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void MainActivityAssigner(MainActivity mainActivity){
        activity = mainActivity;
    }
}
