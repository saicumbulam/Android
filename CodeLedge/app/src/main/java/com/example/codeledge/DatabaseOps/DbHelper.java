package com.example.codeledge.DatabaseOps;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.codeledge.DbSingleTon;
import com.example.codeledge.ProgramData;

public class DbHelper {
    private static DbHelper instance = new DbHelper();
    private static SQLiteDatabase database;
    private static ProgramsInfo programsInfo;
    private static ProgramData[] programDatas;

    private DbHelper() {
        programsInfo = new ProgramsInfo();
    }

    public void assignSqliteDatabaseReference(SQLiteDatabase db){
        database = db;
    }

    public static DbHelper getInstance(){
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

    public void LoadData(){
        Cursor c = database.rawQuery("SELECT * FROM programs", null);

        int nameIndex = c.getColumnIndex("name");
        int typeIndex = c.getColumnIndex("type");
        int descrIndex = c.getColumnIndex("descr");
        int efficyIndex = c.getColumnIndex("efficy");

        int totalCount = c.getCount();

        programDatas = new ProgramData[totalCount];
        c.moveToFirst();

        int count = 0;
        while (totalCount > 0){
            programDatas[count] = new ProgramData(c.getString(nameIndex),
                    c.getString(typeIndex), c.getString(descrIndex),
                    c.getColumnName(efficyIndex));
            count++;
            totalCount--;
            c.moveToNext();
        }
    }

    public void InsertData() {
        programDatas = programsInfo.GetProgramsData();

        try {
            for (ProgramData programData : programDatas
            ) {
                database.execSQL(String.format("INSERT INTO programs (name, type, descr, efficy) " +
                                "VALUES ('%s', '%s', '%s', '%s' )", programData.getName(),
                        programData.getType(), programData.getDescr(),
                        programData.getEfficy()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//            Cursor c = database.rawQuery("SELECT * FROM programs", null);
//
//            int nameIndex = c.getColumnIndex("name");
//            c.moveToFirst();
//            while (c != null){
//                Log.i("name", c.getString(nameIndex));
//            }

    }

    public ProgramData[] getProgramData() {
        return programDatas;
    }

    public String ValidateDataCounts() {
        return "Total counts: " + programDatas.length;
    }

    public String getProgramDescr(String programName){
        Cursor c = database.rawQuery(String.format("SELECT * FROM programs WHERE name = '%s'",
                programName), null);

        int descrIndex = c.getColumnIndex("descr");
        c.moveToFirst();
        while (c != null){
            return c.getString(descrIndex);
        }
        return null;
    }
}
