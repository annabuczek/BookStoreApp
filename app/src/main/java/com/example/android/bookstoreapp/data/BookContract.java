package com.example.android.bookstoreapp.data;

import android.provider.BaseColumns;

/**
 * API Contract for Book Store App.
 */

public final class BookContract {

    /**
     * Private constructor as this class should never be instantiated
     */
    private BookContract() {
    }

    /**
     * Inner class that defines constant values for book catalog database.
     * Each row in the table represent one product.
     */
    public class BookCatalogEntry implements BaseColumns {

        /**
         * Name of the database table for book products
         */
        public static final String TABLE_NAME = "book_catalog";

        /**
         * Unique ID number for a product
         * <p>
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Product Name f.e title of the book.
         * <p>
         * Type: TEXT
         */
        public static final String COLUMN_PRODUCT_NAME = "product_name";

        /**
         * Price for the product.
         * <p>
         * Type: INTEGER
         */
        public static final String COLUMN_PRICE = "price";

        /**
         * Amount of the products.
         * <p>
         * Type: INTEGER
         */
        public static final String COLUMN_QUANTITY = "quantity";

        /**
         * Product supplier name.
         * <p>
         * Type: TEXT
         */
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";

        /**
         * Product supplier phone number.
         * <p>
         * Type: TEXT
         */
        public static final String COLUMN_SUPPLIER_PHONE = "supplier_phone";
    }
}
