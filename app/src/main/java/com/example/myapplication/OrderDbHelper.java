package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "orders_db";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TOKEN = "token";
    private static final String COLUMN_CONTRACT_VALUE = "contract_value";
    private static final String COLUMN_TEXT = "text";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "CREATE TABLE " +
            TABLE_ORDERS + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TOKEN + " TEXT NOT NULL, " +
            COLUMN_CONTRACT_VALUE + " INTEGER NOT NULL, " +
            COLUMN_TEXT + " TEXT);";

    public OrderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    // Method to insert a new order record
    public long addOrder(String token, int contractValue, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOKEN, token);
        values.put(COLUMN_CONTRACT_VALUE, contractValue);
        values.put(COLUMN_TEXT, text);
        long result = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return result;
    }

    // Method to fetch orders based on page index and records per page
    public List<Order> getOrdersByPage(int offset, int limit) {
        List<Order> ordersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_TOKEN,
                COLUMN_CONTRACT_VALUE,
                COLUMN_TEXT
        };

        String orderBy = COLUMN_ID + " DESC"; // Ordering by _id in descending order
        String limitOffset = offset + "," + limit; // Constructing the limit offset

        Cursor cursor = db.query(
                TABLE_ORDERS,
                columns,
                null,
                null,
                null,
                null,
                orderBy,
                limitOffset // Applying the limit and offset
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int tokenIndex = cursor.getColumnIndex(COLUMN_TOKEN);
                String token = cursor.getString(tokenIndex);

                int contractValueIndex = cursor.getColumnIndex(COLUMN_CONTRACT_VALUE);
                int contractValue = cursor.getInt(contractValueIndex);

                int textIndex = cursor.getColumnIndex(COLUMN_TEXT);
                String text = cursor.getString(textIndex);

                Order order = new Order(token, contractValue, text);
                ordersList.add(order);
            }
            cursor.close();
        }

        db.close();
        return ordersList;
    }

}
