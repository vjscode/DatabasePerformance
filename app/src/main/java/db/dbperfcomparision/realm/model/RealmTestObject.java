package db.dbperfcomparision.realm.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vijay on 6/11/16.
 */

public class RealmTestObject extends RealmObject {
    @PrimaryKey
    private int id;

    private String name;

    private int color;

    private int age;

    private long creationTime;

    private long updatedTime;

    private RealmList<RealmTestSubObject> subObjects;

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

    public RealmList<RealmTestSubObject> getSubObjects() {
        return subObjects;
    }

    public void setSubObjects(RealmList<RealmTestSubObject> subObjects) {
        this.subObjects = subObjects;
    }
}
