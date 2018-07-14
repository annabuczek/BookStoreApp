package com.example.android.bookstoreapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EditActivity extends AppCompatActivity {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch(menuItemId) {
            case R.id.save_button:
                // Save data inserted by the user to the database
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}