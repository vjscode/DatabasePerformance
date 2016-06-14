package db.dbperfcomparision.sqlite.model;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 6/13/16.
 */

public class SqliteTestSubObject {
    private String name;
    private int age;
    public SqliteTestSubObject(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static List<SqliteTestSubObject> fromCursor(Cursor c) {
        if (c != null) {
            List<SqliteTestSubObject> result = new ArrayList<>();
            String subObjects = c.getString(c.getColumnIndex("subObjects"));
            try {
                JSONArray jsonArray = new JSONArray(subObjects);
                for (int i = 1; i <= 100; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i-1);
                    SqliteTestSubObject testSubObject = new SqliteTestSubObject(jsonObject.getString("name")
                            , jsonObject.getInt("age"));
                    result.add(testSubObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
