package db.dbperfcomparision;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import db.dbperfcomparision.realm.RealmManager;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        startRealm();
    }

    private void startRealm() {
        RealmManager realmManager = new RealmManager(this);
        realmManager.bulkInsert();
    }
}
