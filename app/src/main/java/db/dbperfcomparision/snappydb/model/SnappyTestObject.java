package db.dbperfcomparision.snappydb.model;

import java.util.List;

/**
 * Created by vijay on 6/12/16.
 */

public class SnappyTestObject {
    private int id;

    private String name;

    private int color;

    private int age;

    private long creationTime;

    private long updatedTime;

    private List<SnappyTestSubObject> subObjects;

    public SnappyTestObject() {}

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

    public List<SnappyTestSubObject> getSubObjects() {
        return subObjects;
    }

    public void setSubObjects(List<SnappyTestSubObject> subObjects) {
        this.subObjects = subObjects;
    }
}
