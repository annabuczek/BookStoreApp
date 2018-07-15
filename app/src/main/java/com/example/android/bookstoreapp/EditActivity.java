package com.example.android.bookstoreapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.bookstoreapp.data.BookContract.BookCatalogEntry;
import com.example.android.bookstoreapp.data.BookDbHelper;

public class EditActivity extends AppCompatActivity {

    // Constant to specify limit of digits user will be able to input into quantity edit text field.
    private static final int QUANTITY_MAX_LENGTH = 3;

    // Constant to specify limit of digits user will be able to input
    // into supplier phone number edit text field
    private static final int PHONE_NUMBER_MAX_LENGTH = 9;

    private EditText mProductNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierPhoneNumber;

    private String supplierNameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Find views for further use
        mProductNameEditText = findViewById(R.id.product_name_input);
        mPriceEditText = findViewById(R.id.price_input);
        mQuantityEditText = findViewById(R.id.quantity_input);
        mSupplierPhoneNumber = findViewById(R.id.supplier_phone_input);

        // Set InputFilter.LengthFilter on mQuantityEditText to limit number of characters user may input
        mQuantityEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(QUANTITY_MAX_LENGTH)});

        // Apply InputFilter.LengthFilter on mSupplierPhoneNumber to limit number of characters user may input
        mSupplierPhoneNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PHONE_NUMBER_MAX_LENGTH)});

        // Apply TwoDigitsDecimalInputFilter which implements InputFilter interface
        // in order to limit user input to mach pattern
        // which in this case is "xxx.xx"
        mPriceEditText.setFilters(new InputFilter[]{new TwoDigitsDecimalInputFilter(3, 2)});

        setupSpinner();
    }

    private void setupSpinner() {
        Spinner supplierSpinner = findViewById(R.id.supplier_name_spinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.supplier_spinner, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        supplierSpinner.setAdapter(spinnerAdapter);

        supplierSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supplierNameString = (String) parent.getItemAtPosition(position);
                Log.v("EditActivity", "Supplier name is " + supplierNameString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                supplierNameString = getString(R.string.supplier_name_unknown_case);
            }
        });
    }

    private void insertProduct() {
        // Get information from the user input
        String productNameString = mProductNameEditText.getText().toString();
        String priceString = mPriceEditText.getText().toString().trim();
        double priceValue = Double.parseDouble(priceString);
        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantityValue = Integer.parseInt(quantityString);
        String supplierPhoneString = mSupplierPhoneNumber.getText().toString().trim();

        // Store information from the user in the ContentValues object
        ContentValues values = new ContentValues();
        values.put(BookCatalogEntry.COLUMN_PRODUCT_NAME, productNameString);
        values.put(BookCatalogEntry.COLUMN_PRICE, priceValue);
        values.put(BookCatalogEntry.COLUMN_QUANTITY, quantityValue);
        values.put(BookCatalogEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(BookCatalogEntry.COLUMN_SUPPLIER_PHONE, supplierPhoneString);

        // Create instance of BookDbHelper class to access database
        BookDbHelper dbHelper = new BookDbHelper(this);

        // Create instance of SQLiteDatabase object to insert data to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long newRowId = db.insert(BookCatalogEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Problem with inserting data into database", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "New row in the table created with id " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId) {
            case R.id.save_button:
                // Save data inserted by the user to the database
                insertProduct();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}