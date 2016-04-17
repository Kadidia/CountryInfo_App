package com.example.kadi.countryinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kadi on 08/04/2016.
 */
public class UsersBDDHandler extends SQLiteOpenHelper {
    private ArrayList<User> users;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ConnectionDB";

    private static final String USER_INFO_KEY = "id";
    private static final String TYPE_TEXT = " TEXT";
    private static final String USER_INFO_USERNAME = "Username";
    private static final String USER_INFO_PASSWORD = "Password";
    private static final String USER_INFO_TABLE_NAME = "UserInfo";

    private static final String COUNTRY_INFO_KEY = "id";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_NUMERIC = " NUMERIC";
    private static final String COUNTRY_NAME = "CountryName";
    private static final String COUNTRY_CODE = "Country_code";
    private static final String TOPONYME_NAME = "Toponyme_code";
    private static final String FCL_NAME = "Fcl_name";
    private static final String FCODE_NAME = "Fcode_name";
    private static final String WIKIPEDIA_LINK = "Wikipedia_link";
    private static final String POPULATION = "Population";
    private static final String LNG = "lng";
    private static final String LAT = "lat";
    private static final String  COUNTRY_INFO_TABLE_NAME = "CountryInfo";

    private static final String TABLE_USER_INFO_CREATE =
            "CREATE TABLE " + USER_INFO_TABLE_NAME + " (" +
                    USER_INFO_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_INFO_USERNAME + TYPE_TEXT +"," +
                    USER_INFO_PASSWORD + TYPE_TEXT + ");";

    private static final String TABLE_COUNTRY_INFO_CREATE =
            "CREATE TABLE " + COUNTRY_INFO_TABLE_NAME + " (" +
                    COUNTRY_INFO_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COUNTRY_NAME + TYPE_TEXT +"," +
                    COUNTRY_CODE + TYPE_TEXT +"," +
                    TOPONYME_NAME+ TYPE_TEXT +"," +
                    FCL_NAME + TYPE_TEXT +"," +
                    FCODE_NAME + TYPE_TEXT +"," +
                    WIKIPEDIA_LINK + TYPE_TEXT +"," +
                    POPULATION + TYPE_INTEGER +"," +
                    LNG + TYPE_NUMERIC +"," +
                   LAT + TYPE_NUMERIC+ ");";

    private static final  String TABLE_USER_INFO_DELETE =
            "DROP TABLE IF EXISTS " + USER_INFO_TABLE_NAME + ";";
    private static final  String TABLE_COUNTRY_INFO_DELETE =
            "DROP TABLE IF EXISTS " +COUNTRY_INFO_TABLE_NAME + ";";

    public UsersBDDHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ok ", "ok creation");
        db.execSQL(TABLE_COUNTRY_INFO_CREATE);
        Log.i("ok", "after creation");
        db.execSQL(TABLE_USER_INFO_CREATE);
        String count = "SELECT count(*) FROM " + USER_INFO_TABLE_NAME;
        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();
        int icount = cursor.getInt(0);
        if(icount <= 0) initializeUsersDataBase(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_USER_INFO_DELETE);
        db.execSQL(TABLE_COUNTRY_INFO_DELETE);
        onCreate(db);

    }
    public void addUser(User user,  SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(USER_INFO_USERNAME, user.getUsername());
        values.put(USER_INFO_PASSWORD, user.getMdp());

        db.insert(USER_INFO_TABLE_NAME, null, values);
    }
    public void addCountry(CountryData data){

        ContentValues values = new ContentValues();
        values.put(COUNTRY_NAME, data.getName());
        values.put(COUNTRY_CODE, data.getCountryCode());
        values.put(TOPONYME_NAME, data.getToponymName());
        values.put(FCL_NAME, data.getFclName());
        values.put(FCODE_NAME, data.getFcodeName());
        values.put(WIKIPEDIA_LINK, data.getWikipediaLink());
        values.put(POPULATION, data.getPopulation());
        values.put(LNG, data.getLng());
        values.put(LAT, data.getLat());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(COUNTRY_INFO_TABLE_NAME, null, values);
        db.close();
    }
    public List<User> getAllUsers(SQLiteDatabase db){
        List<User> list = new ArrayList<User>();
        String selectQuery = "SELECT * FROM " + USER_INFO_TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                User user = new User("","");
                user.setUsername(cursor.getString(1));
                user.setMdp(cursor.getString(2));
                list.add(user);
            }while (cursor.moveToNext());
        }
        return list;

    }
    public List<CountryData> getAllCountries(){
        List<CountryData> list = new ArrayList<CountryData>();
        String selectQuery = "SELECT * FROM " + COUNTRY_INFO_TABLE_NAME;

         SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{

                CountryData data = new CountryData();
                data.setName(cursor.getString(1));
                data.setCountryCode(cursor.getString(2));
                data.setToponymName(cursor.getString(3));
                data.setFclName(cursor.getString(4));
                data.setFcodeName(cursor.getString(5));
                data.setWikipediaLink(cursor.getString(6));
                data.setPopulation(cursor.getInt(7));
                data.setLng(cursor.getDouble(8));
                data.setLat(cursor.getDouble(9));
                list.add(data);
            }while (cursor.moveToNext());
        }
         db.close();
        return list;

    }
    public void initializeUsersDataBase(SQLiteDatabase db){
        users = new ArrayList<User>();
        users.add(new User("kadidiatou","1234"));
        users.add(new User("Toto","toto"));
        users.add(new User("Alexandre","0000"));

        for(User user : users){

            addUser(user,db);
        }
    }

    public boolean isUserExist(User user){

        SQLiteDatabase db = this.getWritableDatabase();
        List<User> users = getAllUsers(db);
        if(users.contains(user)) {
            db.close();
            return true;
        }
        else
        {    db.close();
            return false;
        }
    }


}
