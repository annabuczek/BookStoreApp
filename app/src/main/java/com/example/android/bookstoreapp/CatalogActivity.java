package com.example.android.bookstoreapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

        // Create SQLiteDatabase object get database in a readable mode
        // and make a connection with databale file through this object
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Specify which columns from the database should be read
        // That is correspondent to the SELECT SQL statement
        String[] projection = {
                BookCatalogEntry._ID,
                BookCatalogEntry.COLUMN_PRODUCT_NAME,
                BookCatalogEntry.COLUMN_PRICE,
                BookCatalogEntry.COLUMN_QUANTITY,
                BookCatalogEntry.COLUMN_SUPPLIER_NAME,
                BookCatalogEntry.COLUMN_SUPPLIER_PHONE};

        // Perform a query on a database and store the query result in a Cursor object.
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

            displayView.setText("The number of the rows in a " + BookCatalogEntry.TABLE_NAME + " table: " + numberOfRows + "\n\n");
            displayView.append(BookCatalogEntry._ID + " - "
                    + BookCatalogEntry.COLUMN_PRODUCT_NAME + " - "
                    + BookCatalogEntry.COLUMN_PRICE + " - "
                    + BookCatalogEntry.COLUMN_QUANTITY + " - "
                    + BookCatalogEntry.COLUMN_SUPPLIER_NAME + " - "
                    + BookCatalogEntry.COLUMN_SUPPLIER_PHONE + "\n\n");

            // Find indices of column we want to read data from
            int idColumnID = c.getColumnIndex(BookCatalogEntry._ID);
            int productNameColumnID = c.getColumnIndex(BookCatalogEntry.COLUMN_PRODUCT_NAME);
            int priceColumnID = c.getColumnIndex(BookCatalogEntry.COLUMN_PRICE);
            int quantityColumnID = c.getColumnIndex(BookCatalogEntry.COLUMN_QUANTITY);
            int supplierColumnID = c.getColumnIndex(BookCatalogEntry.COLUMN_SUPPLIER_NAME);
            int supplierPhoneColumnID = c.getColumnIndex(BookCatalogEntry.COLUMN_SUPPLIER_PHONE);

            while (c.moveToNext()) {
                // Read data from the column of the table
                String id = c.getString(idColumnID);
                String productName = c.getString(productNameColumnID);
                double price = c.getDouble(priceColumnID);
                int quantity = c.getInt(quantityColumnID);
                String supplier = c.getString(supplierColumnID);
                String supplierPhone = c.getString(supplierPhoneColumnID);

                displayView.append(id + ". " + productName + " - " + price + " pln | " + quantity);
                if (quantity <= 1) {
                    displayView.append(" item\n");
                } else {
                    displayView.append(" items\n");
                }
                displayView.append(supplier + " - phone: " + supplierPhone + "\n\n");
            }

        } finally {
            c.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
}
