package com.example.codeledge_v2.DatabaseOps;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.codeledge_v2.ui.designPattern.DesignPatternData;

import java.util.ArrayList;
import java.util.List;

public class DbHelper {
    private static DbHelper instance = new DbHelper();
    private static SQLiteDatabase database;
    private static ProgramData[] programDatas;

    private DbHelper() {
    }

    public void assignSqliteDatabaseReference(SQLiteDatabase db) {
        database = db;
    }

    public static DbHelper getInstance() {
        return instance;
    }

    public void CreateTablenData() {
        createTables();
    }

    private void createTables() {
        database.execSQL("CREATE TABLE IF NOT EXISTS programs (name VARCHAR PRIMARY KEY, " +
                "type VARCHAR," +
                "descr VARCHAR, efficy VARCHAR )");
    }

    public void LoadData() {
        Cursor c = database.rawQuery("SELECT * FROM programs", null);

        int nameIndex = c.getColumnIndex("name");
        int typeIndex = c.getColumnIndex("type");
        int descrIndex = c.getColumnIndex("descr");
        int efficyIndex = c.getColumnIndex("efficy");

        int totalCount = c.getCount();

        programDatas = new ProgramData[totalCount];
        c.moveToFirst();

        int count = 0;
        while (totalCount > 0) {
            programDatas[count] = new ProgramData(c.getString(nameIndex),
                    c.getString(typeIndex), c.getString(descrIndex),
                    c.getColumnName(efficyIndex));
            count++;
            totalCount--;
            c.moveToNext();
        }
    }

    public ProgramData[] getProgramData() {
        return programDatas;
    }

    public String ValidateDataCounts() {
        return "Total counts: " + programDatas.length;
    }

    public String getProgramDescr(String programName, String tableName) {
        Cursor c = database.rawQuery(String.format("SELECT * FROM %s WHERE name = '%s'",
                tableName, programName), null);

        int descrIndex = c.getColumnIndex("descr");
        c.moveToFirst();
        while (c != null) {
            return c.getString(descrIndex);
        }
        return null;
    }

    public int getNumberofMainCategoryCounts(String tableName) {
        try {
            Cursor c = database.rawQuery(String.format("SELECT * FROM %s",
                    tableName), null);

            c.moveToFirst();
            while (c != null) {
                return c.getCount();
            }
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }
        return -1;
    }

    public int getNumberofCategoryCounts(String categoryName) {
        Cursor c = database.rawQuery(String.format("SELECT * FROM programs WHERE type = '%s'",
                categoryName), null);

        c.moveToFirst();
        while (c != null) {
            return c.getCount();
        }
        return -1;
    }

    public List<DesignPatternData> getDesignPatterName() {
        List<DesignPatternData> names = new ArrayList<>();

        Cursor c = database.rawQuery(String.format("SELECT DISTINCT type FROM designpattern"),
                null);

        int totalCount = c.getCount();

        c.moveToFirst();
        while (totalCount > 0) {
            names.add(new DesignPatternData(c.getString(0)));
            totalCount--;
            c.moveToNext();
        }
        return names;
    }

    public List<String> getNumberofCategoryElemnts(String categoryName, String tableName) {
        categoryName = categoryName.replaceAll("\\s+", "");
        List<String> temp = new ArrayList<>();
        Cursor c = database.rawQuery(String.format("SELECT * FROM %s WHERE type = '%s'",
                tableName,categoryName), null);

        int nameIndex = c.getColumnIndex("name");
        int totalCount = c.getCount();
        c.moveToFirst();
        if (c != null && c.moveToFirst()) {
            while (totalCount > 0) {
                temp.add(c.getString(nameIndex));
                totalCount--;
                c.moveToNext();
            }
        }
        return temp;
    }
}
