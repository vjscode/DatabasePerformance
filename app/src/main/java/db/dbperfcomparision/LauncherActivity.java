package db.dbperfcomparision;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import db.dbperfcomparision.realm.RealmManager;
import db.dbperfcomparision.snappydb.SnappyDBManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LauncherActivity extends AppCompatActivity {

    private static final String TAG = LauncherActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        startRealm();
        //startSnappyDB();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void startRealm() {
        RealmManager realmManager = new RealmManager(this);

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        final Realm realm = Realm.getInstance(realmConfig);

        final long startTime = SystemClock.elapsedRealtimeNanos();
        Observable.concat(realmManager.bulkRemove(realm),
                realmManager.bulkInsertAsync(realm))
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                        long endTime = SystemClock.elapsedRealtimeNanos();
                        Log.d(TAG, "Total time taken by bulk inserts on Realm: " +
                                (endTime - startTime));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.d(TAG, "onNext: " + aBoolean);
                    }
                });
    }

    private void startSnappyDB() {
        try {
            final long startTime = SystemClock.elapsedRealtimeNanos();
            DB snappydb = DBFactory.open(this, "test");
            SnappyDBManager snappyDBManager = new SnappyDBManager();
            snappyDBManager.bulkInsertAsync(snappydb)
            .observeOn(Schedulers.newThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Boolean>() {
                @Override
                public void onCompleted() {
                    Log.d(TAG, "onCompleted");
                    long endTime = SystemClock.elapsedRealtimeNanos();
                    Log.d(TAG, "Total time taken by bulk inserts on Realm: " +
                            (endTime - startTime));
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "onError: " + e);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    Log.d(TAG, "onNext: " + aBoolean);
                }
            });
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }
}
