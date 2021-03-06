package com.volcengine.zeus.plugin1_impl;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Create by WUzejian on 2021/6/21.
 * ContentProvider demo
 */
public class PanlgePluginContentProvider extends ContentProvider {

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "provider.db";
    static final String DATABASE_TABLE = "bookinfo";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table " +
            DATABASE_TABLE + " (_id integer primary key autoincrement, " + "name text not null, author text not null);";
    static final String PROVIDER_NAME = "com.volcengine.zeusdemo.zeus.provider.demo";
    static final int BOOKS = 1;
    static final int BOOK_ID = 2;
    static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "books", BOOKS);
        uriMatcher.addURI(PROVIDER_NAME, "books/#", BOOK_ID);
    }

    private String TAG = "Zeus/provider";

    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS BookInfo");
            onCreate(db);
        }
    }


    @Override
    public boolean onCreate() {
        DatabaseHelper helper = new DatabaseHelper(getContext());
        db = helper.getWritableDatabase();
        return false;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "Run in method getType() ,uri:" + uri);
        switch (uriMatcher.match(uri)) {

            case BOOKS:
                return "vnd.android.cursor.dir/vnd.com.volcengine.zeusdemo.zeus.provider.demo.books";
            case BOOK_ID:
                return "vnd.android.cursor.item/vnd.com.volcengine.zeusdemo.zeus.provider.demo.books";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? query ?????? ????????? uri ???" + uri);
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? query ??????");
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_ID:
                cursor = db.query(DATABASE_TABLE, projection, "_id = ", new String[]{uri.getPathSegments().get(1)}, null, null, sortOrder);
                break;
            case BOOKS:
                cursor = db.query(DATABASE_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? insert ?????? ????????? uri ???" + uri);
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? insert ?????? ????????? values ???" + values != null ? values.toString() : null);

        long rowId = db.insert(DATABASE_TABLE, null, values);
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? insert ?????? ?????? rowId ???" + rowId );
        if (rowId > 0) {
            Uri bookUri = ContentUris.withAppendedId(uri, rowId);
            return bookUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? delete ?????? ????????? uri ???" + uri);
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? delete ?????? ????????? selection ???" + selection + ",selectionArgs=" + selectionArgs);
        int rowIDs;
        switch (uriMatcher.match(uri)) {
            case BOOK_ID:
                String bookID = uri.getPathSegments().get(1);
                rowIDs = db.delete(DATABASE_TABLE, "_id = ", new String[]{bookID});
                break;
            case BOOKS:
                rowIDs = db.delete(DATABASE_TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return rowIDs;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? update ?????? ????????? uri ???" + uri);
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? update ?????? ????????? selection ???" + selection + ",selectionArgs=" + selectionArgs);
        int rowIDs;
        switch (uriMatcher.match(uri)) {
            case BOOK_ID:
                String bookID = uri.getPathSegments().get(1);
                rowIDs = db.update(DATABASE_TABLE, values, "_id = ?", new String[]{bookID});
                break;
            case BOOKS:
                rowIDs = db.update(DATABASE_TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return rowIDs;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String authority, @NonNull String method, @Nullable String arg, @Nullable Bundle extras) {

        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? call(4 params) ?????? ????????? authority ???" + authority + ",method=" + method + ",arg=" + arg + ",extras=" + extras.toString());
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? call(4 params) ?????? ????????? extras.get(\"pangle\") ???" + extras.get("pangle"));
        return super.call(authority, method, arg, extras);
    }


    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? call(3 params) ?????? ????????? method=" + method + ",arg=" + arg + ",extras=" + extras.toString());
        Log.d("Zeus/provider", "demo ?????? PanlgePluginContentProvider ?????? call(3 params) ?????? ????????? extras.get(\"pangle\") ???" + extras.get("pangle"));

        return super.call(method, arg, extras);
    }
}
