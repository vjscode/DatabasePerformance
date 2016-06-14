package db.dbperfcomparision.sqlite;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.SystemClock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.dbperfcomparision.sqlite.model.SqliteTestObject;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by vijay on 6/13/16.
 */

public class SqliteManager {
    private static final String DATABASE_NAME = "sqlite_test";
    private static final int DATABASE_VERSION = 1;
    private static final String TESTOBJECT_TABLE_NAME = "testobject";
    private static final String TESTSUBOBJECT_TABLE_NAME = "testsubobject";
    private SQLiteDatabase db;
    public SqliteManager(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    private String getSubObjects() {
        JSONArray jArraySubObjects = new JSONArray();
        try {
            for (int i = 1; i <= 100; i++) {
                JSONObject jObject = new JSONObject();
                jObject.put("name", ""+i);
                jObject.put("age", i);
                jArraySubObjects.put(jObject);
            }
        } catch (SQLiteConstraintException | JSONException se) {
            return null;
        }
        return jArraySubObjects.toString();
    }

    public Observable<Boolean> insertTestObjects() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                for (int i = 1; i <= 1000; i++) {
                    ContentValues values = getValues(i);
                    values.put("subObjects", getSubObjects());
                    db.insertOrThrow(TESTOBJECT_TABLE_NAME, "", values);
                }
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private ContentValues getValues(int i) {
        ContentValues values = new ContentValues();
        values.put("name", ""+i);
        values.put("color", i);
        values.put("age", i);
        values.put("creationTime", SystemClock.elapsedRealtimeNanos());
        values.put("updatedTime", SystemClock.elapsedRealtimeNanos());
        return values;
    }

    public Observable<Boolean> readTestObjects() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean result = true;
                for (int i = 1; i <= 1000; i++) {
                    ContentValues values = getValues(i);
                    values.put("subObjects", getSubObjects());
                    Cursor c = db.query(TESTOBJECT_TABLE_NAME, null, "_id=?", new String[]{""+i}, null, null, null);
                    if (c != null && c.moveToFirst()) {
                        SqliteTestObject sqliteTestObject =  SqliteTestObject.fromCursor(c);
                        if (sqliteTestObject.getId() != i) {
                            result = false;
                            break;
                        }
                    }
                    c.close();
                }
                if (result) {
                    subscriber.onNext(true);
                } else {
                    subscriber.onError(new Exception());
                }
                subscriber.onCompleted();
            }
        });
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String CREATE_TESTOBJECT_DB_TABLE = " CREATE TABLE " + TESTOBJECT_TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT NOT NULL, " +
                " color INTEGER NOT NULL, " +
                " age INTEGER NOT NULL, " +
                " creationTime INTEGER NOT NULL, " +
                " updatedTime INTEGER NOT NULL, " +
                " subObjects TEXT" +
                ");";
        private static final String CREATE_TESTSUBOBJECT_DB_TABLE = " CREATE TABLE " + TESTSUBOBJECT_TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " name TEXT NOT NULL, " +
                " age INTEGER NOT NULL " +
                ");";


        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TESTOBJECT_DB_TABLE);
            db.execSQL(CREATE_TESTSUBOBJECT_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TESTOBJECT_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TESTSUBOBJECT_TABLE_NAME);
            onCreate(db);
        }
    }
}
