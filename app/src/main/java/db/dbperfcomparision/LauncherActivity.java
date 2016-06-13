package db.dbperfcomparision;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import db.dbperfcomparision.databinding.ActivityLauncherBinding;
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
    private ActivityLauncherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launcher);
    }

    public void buttonClick(View v) {
        if (v.getId() == R.id.realmInsert) {
            startRealmBulkInsert();
        } else if (v.getId() == R.id.realmQuery) {
            startRealmBasicQuery();
        } else if (v.getId() == R.id.snappyDBInsert) {
            startSnappyDBBulkInsert();
        } else if (v.getId() == R.id.snappyDBQuery) {
            startSnappyDBBasicQuery();
        }
    }

    private void startRealmBasicQuery() {
        binding.txtRealmQuery.setText("Started realm query");
        RealmManager realmManager = new RealmManager(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        final long startTime = SystemClock.elapsedRealtimeNanos();
        realmManager.basicQuery(realmConfig)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                        long endTime = SystemClock.elapsedRealtimeNanos();
                        Log.d(TAG, "Total time taken by basic query on Realm: " +
                                (endTime - startTime));
                        binding.txtRealmQuery.setText((endTime - startTime) + "ns");
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

    private void startSnappyDBBasicQuery() {

            binding.txtSnappyDBQuery.setText("Started snappydb query");

            SnappyDBManager snappyDBManager = new SnappyDBManager();
            final long startTime = SystemClock.elapsedRealtimeNanos();
            snappyDBManager.snappyBasicQuery(this)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {
                            Log.d(TAG, "onCompleted");
                            long endTime = SystemClock.elapsedRealtimeNanos();
                            Log.d(TAG, "Total time taken by basic query on snappydb: " +
                                    (endTime - startTime));
                            binding.txtSnappyDBQuery.setText((endTime - startTime) + "ns");
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void startRealmBulkInsert() {
        binding.txtRealmInsert.setText("Started realm inserts");
        RealmManager realmManager = new RealmManager(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();

        final long startTime = SystemClock.elapsedRealtimeNanos();
        Observable.concat(realmManager.bulkRemove(realmConfig),
                realmManager.bulkInsertAsync(realmConfig))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                        long endTime = SystemClock.elapsedRealtimeNanos();
                        Log.d(TAG, "Total time taken by bulk inserts on Realm: " +
                                (endTime - startTime));
                        binding.txtRealmInsert.setText((endTime - startTime) + "ns");
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

    private void startSnappyDBBulkInsert() {

            binding.txtSnappyDBInsert.setText("Started snappydb inserts");
            SnappyDBManager snappyDBManager = new SnappyDBManager();
            final long startTime = SystemClock.elapsedRealtimeNanos();
            snappyDBManager.bulkInsertAsync(this)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(new Subscriber<Boolean>() {
                @Override
                public void onCompleted() {
                    Log.d(TAG, "onCompleted");
                    long endTime = SystemClock.elapsedRealtimeNanos();
                    Log.d(TAG, "Total time taken by bulk inserts on Realm: " +
                            (endTime - startTime));
                    binding.txtSnappyDBInsert.setText((endTime - startTime) + "ns");
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
}
