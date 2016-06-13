package db.dbperfcomparision.realm;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.dbperfcomparision.realm.model.RealmTestObject;
import db.dbperfcomparision.realm.model.TestSubObject;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by vijay on 6/11/16.
 */

public class RealmManager {
    private static final String TAG = RealmManager.class.getSimpleName();
    private Context context;

    public RealmManager(Context context) {
        this.context = context;

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void bulkInsert(Realm realm) {
        Log.d(TAG, "start time: " + SystemClock.elapsedRealtimeNanos());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (int i = 1; i < 1000; i++) {
                    RealmTestObject realmTestObject = realm.createObject(RealmTestObject.class);
                    realmTestObject.setId(i);
                    realmTestObject.setName("" + i);
                    realmTestObject.setAge(i + 100);
                    realmTestObject.setCreationTime(SystemClock.elapsedRealtimeNanos());
                    realmTestObject.setUpdatedTime(SystemClock.elapsedRealtimeNanos());
                    List<TestSubObject> testSubObjectList = new ArrayList<>();
                    for (int j = 0; j < 100; j++) {
                        TestSubObject testSubObject = realm.createObject(TestSubObject.class);
                        testSubObject.setAge(j + 100);
                        testSubObject.setName("" + j);
                        realmTestObject.getSubObjects().add(testSubObject);
                    }
                }
                Log.d(TAG, "end time: " + SystemClock.elapsedRealtimeNanos());
            }
        });
    }

    public rx.Observable<Boolean> bulkInsertAsync(final RealmConfiguration realmConfig) {
        return rx.Observable.create(new rx.Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                final Realm realm = Realm.getInstance(realmConfig);
                bulkInsert(realm);
                try {
                    if (realm != null) {
                        realm.close();
                    }
                } catch (RealmException ex) {
                    subscriber.onError(ex);
                }
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> bulkRemove(final RealmConfiguration realmConfig) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                final Realm realm = Realm.getInstance(realmConfig);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(RealmTestObject.class);
                    }
                });
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> basicQuery(final RealmConfiguration realmConfig) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            boolean result = true;
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                final Realm realm = Realm.getInstance(realmConfig);
                for (int i = 1; i < 1000; i++) {
                    RealmResults<RealmTestObject> results = realm.where(RealmTestObject.class).equalTo("id", i).findAll();
                    if (results.size() != 1) {
                        result = false;
                        break;
                    }
                }
                try {
                    if (realm != null) {
                        realm.close();
                        if (!result) {
                            subscriber.onNext(false);
                        } else {
                            subscriber.onNext(true);
                        }
                        subscriber.onCompleted();
                    }
                } catch (RealmException ex) {
                    subscriber.onError(ex);
                }
            }
        });
    }
}
