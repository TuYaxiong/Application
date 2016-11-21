
package com.example.tyxiong.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;

class my_SQLite extends SQLiteOpenHelper {
    static String CREATE_TABLE = "create table my_table(_id integer primary key,my_name ,my_id)";

    public my_SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//当第一次创建数据库时回调该方法.调一次.适合建表...
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//版本更新会回调方法.
        Log.w("xxx", "onUpgrade: ");
    }
}

public class MyContentProvider extends ContentProvider {
    private final String MY_FILE = "/myFile1.db3";
    File storageDirectory;
    String INSERT = "insert into my_table values(null,?,?)";
    String UPDATE = "update my_table set my_id=222 where _id=2";
    String SELECT = "select * from my_table";
    SQLiteDatabase sqLiteDatabase = null;
    my_SQLite mysql;
    UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private final int WORDS = 1;
    private final int WORD = 2;

    {
        uriMatcher.addURI("com.tuyaxiong.providers", "words", WORDS);
        uriMatcher.addURI("com.tuyaxiong.providers", "word/#", WORD);
    }


    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case WORD: {
                sqLiteDatabase.execSQL("delete from my_table " + selection, selectionArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                return 1;
            }
            default:
                return 0;
        }
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case WORD: {
                return "vnd.android.cursor.item/com.tuyaxiongdb3";
            }
            case WORDS: {
                return "vnd.android.cursor.dir/com.tuyaxiongdb3";
            }
            default:
                return null;
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {


        switch (uriMatcher.match(uri)) {
            case WORD: {
                sqLiteDatabase.execSQL(INSERT, new String[]{values.getAsString("my_name"),
                        values.getAsString("my_id")});
                getContext().getContentResolver().notifyChange(uri, null);//需要有这句,通知监听器,监听器的处理方法都会触发.
                return uri;
            }
            default:
                return null;
        }

    }

    @Override
    public boolean onCreate() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            storageDirectory = Environment.getExternalStorageDirectory();

            mysql = new my_SQLite(getContext(), storageDirectory + MY_FILE, null, 1);
            sqLiteDatabase = mysql.getReadableDatabase();
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case WORDS: {
                sqLiteDatabase = mysql.getReadableDatabase();
                return sqLiteDatabase.rawQuery(SELECT, null);
            }
            default:
                return null;
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case WORD: {
                sqLiteDatabase.execSQL(UPDATE, null);
                return 1;
            }
            default:
                return 0;
        }


    }
}
