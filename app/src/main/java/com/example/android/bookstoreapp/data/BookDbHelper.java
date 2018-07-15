package com.example.android.bookstoreapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.bookstoreapp.data.BookContract.BookCatalogEntry;

/**
 * Database Helper for Book Store App. Manages database creation and version management.
 */

public class BookDbHelper extends SQLiteOpenHelper {

    /**
     * Version of the database
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "book_store.db";

    /**
     * BookDbHelper constructor
     *
     * @param context of the app
     */
    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create a book_catalog table
        String SQL_CREATE_BOOK_CATALOG_TABLE = "CREATE TABLE " + BookCatalogEntry.TABLE_NAME + "("
                + BookCatalogEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookCatalogEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + BookCatalogEntry.COLUMN_PRICE + " INTEGER NOT NULL, "
                + BookCatalogEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + BookCatalogEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL, "
                + BookCatalogEntry.COLUMN_SUPPLIER_PHONE + " TEXT NOT NULL );";

        // Execute SQL statement to create the table
        db.execSQL(SQL_CREATE_BOOK_CATALOG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // There is a valid version of database so for now no implementation is needed.
    }
}
