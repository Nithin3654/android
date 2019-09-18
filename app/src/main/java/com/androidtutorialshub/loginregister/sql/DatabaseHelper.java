package com.androidtutorialshub.loginregister.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidtutorialshub.loginregister.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.androidtutorialshub.loginregister.R.id.fine;

/**
 * Created by lalit on 9/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_USER1 = "book";



    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_fine = "fine";
    private static final String COLUMN_USER_book1 = "book1";
    private static final String COLUMN_USER_book2 = "book2";
    private static final String COLUMN_USER_time = "time";
    private static final String COLUMN_USER1_book = "bookname";

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     * 
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     * @param id
     */
    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from user",null);
        return res;
    }
    public Cursor getAllbooks() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from book",null);

        return res;
    }




    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public void updatebooks(String userid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user ",null);
        while (res.moveToNext()) {
            if (res.getString(2).equals(userid)) {
                String book1 = res.getString(5);
                String book2 = res.getString(6);

                if (book1 != null) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_USER1_book, book1);

                    // Inserting Row
                    db.insert(TABLE_USER1, null, values);
                    ContentValues values2 = new ContentValues();
                    values2.put(COLUMN_USER_book1, (byte[]) null);
                    db.update(TABLE_USER, values2, COLUMN_USER_EMAIL + " = ?",
                            new String[]{String.valueOf(userid)});


                }
                if (book2 != null) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_USER1_book, book2);

                    // Inserting Row
                    db.insert(TABLE_USER1, null, values);
                    ContentValues values2 = new ContentValues();
                    values2.put(COLUMN_USER_book2, (byte[]) null);


                    // updating row
                    db.update(TABLE_USER, values2, COLUMN_USER_EMAIL + " = ?",
                            new String[]{String.valueOf(userid)});


                }
            }
        }

        db.close();

    }

    public int issuebooks(String bookname, String userid) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res1 = db.rawQuery("select * from user ",null);
        int flag=0;
        while (res1.moveToNext()) {
            if (res1.getString(2).equals(userid)) {
                String book1 = res1.getString(5);
                String book2 = res1.getString(6);

                Cursor res = db.rawQuery("select * from book ", null);
                while (res.moveToNext()) {
                    if (res.getString(0).equals(bookname)) {
                        int time=(int) new Date().getTime();

                        if (book1 != null && book2!=null) {


                        }
                        else if (book1 != null) {
                            ContentValues values = new ContentValues();
                            values.put(COLUMN_USER_book2, bookname);
                            values.put(COLUMN_USER_time, Integer.toString(time));
                            db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                                    new String[]{String.valueOf(userid)});
                            db.delete(TABLE_USER1, COLUMN_USER1_book + " = ?",
                                    new String[]{String.valueOf(bookname)});
                            flag=1;

                        }
                        else {
                            ContentValues values = new ContentValues();
                            values.put(COLUMN_USER_book1, bookname);
                            values.put(COLUMN_USER_time, Integer.toString(time));
                            db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                                    new String[]{String.valueOf(userid)});
                            db.delete(TABLE_USER1, COLUMN_USER1_book + " = ?",
                                    new String[]{String.valueOf(bookname)});
                            flag=1;
                        }
                    }
                }
            }
        }

        db.close();
        return flag;
    }

    public void calfine(int newtime, String userid) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user ",null);

        while (res.moveToNext()) {
            if (res.getString(2).equals(userid))
            {
                String book1=res.getString(5);
                String book2=res.getString(6);
                int newfine;
                int oldfine;
                int oldtime;
                if(res.getString(7)==null)
                {
                    oldtime=0;
                }
                else
                {
                    oldtime=Integer.parseInt(res.getString(7));
                }
                if(res.getString(4)==null)
                {
                    oldfine=0;
                }
                else
                {
                    oldfine=Integer.parseInt(res.getString(4));
                }
                if(book1==null && book2==null)
                {
                    newfine=oldfine;
                }
                else if(book1==null || book2==null)
                {
                    newfine=oldfine+(newtime-oldtime)/60000;
                    if(oldfine!=newfine)
                    {
                        ContentValues values = new ContentValues();
                        values.put(COLUMN_USER_fine, Integer.toString(newfine));
                        values.put(COLUMN_USER_time, Integer.toString(newtime));
                        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                                new String[]{String.valueOf(userid)});
                    }

                }
                else
                {
                    newfine=oldfine+2*(newtime-oldtime)/60000;
                    if(oldfine!=newfine)
                    {
                        ContentValues values = new ContentValues();
                        values.put(COLUMN_USER_fine, Integer.toString(newfine));
                        values.put(COLUMN_USER_time, Integer.toString(newtime));
                        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                                new String[]{String.valueOf(userid)});
                    }
                }

            }


            }

    }
}
