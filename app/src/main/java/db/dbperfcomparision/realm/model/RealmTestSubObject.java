package db.dbperfcomparision.realm.model;

import io.realm.RealmObject;

/**
 * Created by vijay on 6/11/16.
 */

public class RealmTestSubObject extends RealmObject {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
