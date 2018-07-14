package com.example.android.bookstoreapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bookstoreapp.data.BookContract;
import com.example.android.bookstoreapp.data.BookContract.BookCatalogEntry;
import com.example.android.bookstoreapp.data.BookDbHelper;

public class CatalogActivity extends AppCompatActivity {

    BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Set OnClickListener to FloatingActionButton to start EditActivity
        FloatingActionButton fabButton = findViewById(R.id.fab_add);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CatalogActivity.this, EditActivity.class);
                startActivity(i);

            }
        });

        // Create instance of a subclass of SQLiteOpenHelper to to access database
        mDbHelper = new BookDbHelper(this);

        displayDatabaseInfo();
    }

    /**
     * Helper method to read data from the database and display it to the screen
     */
    private void displayDatabaseInfo() {

        // Create SQLiteDatabase object to read from the database file through it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Specify which columns from the database should be read
        String[] projection = {
                BookCatalogEntry._ID,
                BookCatalogEntry.COLUMN_PRODUCT_NAME,
                BookCatalogEntry.COLUMN_PRICE,
                BookCatalogEntry.COLUMN_QUANTITY,
                BookCatalogEntry.COLUMN_SUPPLIER_NAME,
                BookCatalogEntry.COLUMN_SUPPLIER_PHONE };

        // Perform a query on a database and result store in a Cursor object.
        Cursor c = db.query(BookCatalogEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        try {
            // Find reference to TextView for displaying data from the database table
            TextView displayView = findViewById(R.id.display_database_text_view);

            // Get the current number of the rows in the table
            int numberOfRows = c.getCount();

            displayView.setText("The number of the rows in a " + BookCatalogEntry.TABLE_NAME + " table: " + numberOfRows);
        }
        finally {
            c.close();
        }

    }
}
