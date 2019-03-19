package app.com.baoviet.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import app.com.baoviet.constant.Constant;
import app.com.baoviet.entity.DataLocal;

/**
 * Created by Administrator on 5/24/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create notes table
        sqLiteDatabase.execSQL(Constant.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constant.TABLE_NAME);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public String getResponseLocal(DataLocal dataLocal) {
        String result = Constant.EMPTY;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        //select json data
        String selectJson = "SELECT " + Constant.COLUMN_RESPONSE + " FROM " + Constant.TABLE_NAME + " WHERE "
                + Constant.COLUMN_USERNAME + " ='" + dataLocal.getUsername() + "'"
                + " AND " + Constant.COLUMN_PASSWORD + " ='" + dataLocal.getPassword() + "'"
                + " AND " + Constant.COLUMN_API + " ='" + dataLocal.getApi() + "'"
                + " AND " + Constant.COLUMN_PARAMS + " ='" + dataLocal.getParams() + "'";
        try {
            cursor = db.rawQuery(selectJson, null);
            if (Constant.INT_0 < cursor.getCount()) {
                cursor.moveToFirst();
                result = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_RESPONSE));
            } else {
                result = Constant.ERROR_NETWORK;
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            e.getStackTrace();
        } finally {
            cursor.close();
        }
        return result;
    }

    public DataLocal getParamsResponseLocal(DataLocal dataLocal) {
        String result = Constant.EMPTY;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        DataLocal dataResult = new DataLocal();
        //select json data
        String selectJson = "SELECT " + Constant.COLUMN_PARAMS + ", " + Constant.COLUMN_RESPONSE + " FROM " + Constant.TABLE_NAME + " WHERE "
                + Constant.COLUMN_USERNAME + " ='" + dataLocal.getUsername() + "'"
                + " AND " + Constant.COLUMN_PASSWORD + " ='" + dataLocal.getPassword() + "'"
                + " AND " + Constant.COLUMN_API + " ='" + dataLocal.getApi() + "' ORDER BY " + Constant.COLUMN_ID + " DESC LIMIT 1";
        try {
            cursor = db.rawQuery(selectJson, null);
            if (cursor.moveToFirst()) {
                String params = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_PARAMS));
                String response = cursor.getString(cursor.getColumnIndex(Constant.COLUMN_RESPONSE));
                dataResult.setParams(params);
                dataResult.setResponse(response);
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            e.getStackTrace();
        } finally {
            cursor.close();
        }
        return dataResult;
    }

    public long insertDataLocal(DataLocal dataLocal) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        long numberRecord = Constant.INT_0;
        try {
//            String insertQuery = "INSERT INTO " + Constant.TABLE_NAME + " (" + Constant.COLUMN_USERNAME + ", " +
//                    Constant.COLUMN_PASSWORD + ", " + Constant.COLUMN_API + ", " + Constant.COLUMN_PARAMS + ", " +
//                    Constant.COLUMN_RESPONSE + ")" + " VALUES ('"
//                    + dataLocal.getUsername() + "','" + dataLocal.getPassword() + "','"
//                    + dataLocal.getApi() + "','" + dataLocal.getParams() + "','" + dataLocal.getResponse() + "')";
            ContentValues values = new ContentValues();
            values.put(Constant.COLUMN_USERNAME, dataLocal.getUsername());
            values.put(Constant.COLUMN_PASSWORD, dataLocal.getPassword());
            values.put(Constant.COLUMN_API, dataLocal.getApi());
            values.put(Constant.COLUMN_PARAMS, dataLocal.getParams());
            values.put(Constant.COLUMN_RESPONSE, dataLocal.getResponse());
            if (checkExistsDataInput(dataLocal)) {
                String responseOld = getResponseLocal(dataLocal);
                String responseNew = dataLocal.getResponse();
                boolean isEqual = responseNew.equals(responseOld);
                if (!isEqual) {
                    updateDataResponse(dataLocal);
                }
            } else {
                // insert row
                numberRecord = db.insert(Constant.TABLE_NAME, null, values);
//                cursor = db.rawQuery(insertQuery, null);
//                numberRecord = cursor.getCount();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            e.getStackTrace();
        } finally {
            // close db connection
            db.close();
        }
        // return newly inserted row id
        return numberRecord;
    }

    public boolean deleteAllData() {
        int numberRecord = Constant.INT_0;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            numberRecord = db.delete(Constant.TABLE_NAME, null, null);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            e.getStackTrace();
        } finally {
            // close db connection
            db.close();
        }
        if (Constant.INT_0 < numberRecord) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkEmptyData() {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isEmpty = false;
        try {
            long count = DatabaseUtils.queryNumEntries(db, Constant.TABLE_NAME);
            if (Constant.INT_0 < count) {
                isEmpty = true;
            } else {
                isEmpty = false;
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            isEmpty = false;
            e.getStackTrace();
        } finally {
            // close db connection
            db.close();
        }
        return isEmpty;
    }

    public boolean checkExistsDataInput(DataLocal dataLocal) {
        int count = Constant.INT_0;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        //select json data
        String selectJson = "SELECT " + Constant.COLUMN_USERNAME + " FROM " + Constant.TABLE_NAME + " WHERE "
                + Constant.COLUMN_USERNAME + "='" + dataLocal.getUsername() + "' AND "
                + Constant.COLUMN_PASSWORD + "='" + dataLocal.getPassword() + "' AND "
                + Constant.COLUMN_API + "='" + dataLocal.getApi() + "' AND "
                + Constant.COLUMN_PARAMS + "='" + dataLocal.getParams() + "'";
        try {
            cursor = db.rawQuery(selectJson, null);
            count = cursor.getCount();
            if (Constant.INT_0 < count) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            e.getStackTrace();
        } finally {
            cursor.close();
        }
        return false;
    }

    public boolean updateDataResponse(DataLocal dataLocal) {
        int count = Constant.INT_0;
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        //select json data
        String selectJson = "UPDATE " + Constant.TABLE_NAME + " SET " + Constant.COLUMN_RESPONSE + "='" + dataLocal.getResponse() + "' WHERE "
                + Constant.COLUMN_USERNAME + "='" + dataLocal.getUsername() + "' AND "
                + Constant.COLUMN_PASSWORD + "='" + dataLocal.getPassword() + "' AND "
                + Constant.COLUMN_API + "='" + dataLocal.getApi() + "' AND "
                + Constant.COLUMN_PARAMS + "='" + dataLocal.getParams() + "'";
        try {
            ContentValues values = new ContentValues();
            values.put(Constant.COLUMN_RESPONSE, dataLocal.getResponse());

            count = db.update(Constant.TABLE_NAME, values, Constant.COLUMN_USERNAME + "='" + dataLocal.getUsername() + "' AND "
                    + Constant.COLUMN_PASSWORD + "='" + dataLocal.getPassword() + "' AND "
                    + Constant.COLUMN_API + "='" + dataLocal.getApi() + "' AND "
                    + Constant.COLUMN_PARAMS + "='" + dataLocal.getParams() + "'", null);
//            cursor = db.rawQuery(selectJson, null);
//            count = cursor.getCount();
            if (Constant.INT_0 < count) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            e.getStackTrace();
        } finally {
            cursor.close();
            // close db connection
            db.close();
        }
        return false;
    }
}
