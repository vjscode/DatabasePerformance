package db.dbperfcomparision.sqlite.model;

import android.database.Cursor;

import java.util.List;

/**
 * Created by vijay on 6/13/16.
 */

public class SqliteTestObject {
    private int id;

    private String name;

    private int color;

    private int age;

    private long creationTime;

    private long updatedTime;

    private List<SqliteTestSubObject> subObjects;

    public SqliteTestObject(int id, String name, int color, int age, long creationTime, long updatedTime,
                            List<SqliteTestSubObject> subObjects) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.age = age;
        this.creationTime = creationTime;
        this.updatedTime = updatedTime;
        this.subObjects = subObjects;
    }

    public static SqliteTestObject fromCursor(Cursor c) {
        if (c != null) {
            return
                    new SqliteTestObject(c.getInt(c.getColumnIndex("_id")),
                            c.getString(c.getColumnIndex("name")),
                            c.getInt(c.getColumnIndex("color")),
                            c.getInt(c.getColumnIndex("age")),
                            c.getLong(c.getColumnIndex("creationTime")),
                            c.getLong(c.getColumnIndex("updatedTime")),
                            SqliteTestSubObject.fromCursor(c));
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public List<SqliteTestSubObject> getSubObjects() {
        return subObjects;
    }

    public void setSubObjects(List<SqliteTestSubObject> subObjects) {
        this.subObjects = subObjects;
    }
}
