package db.dbperfcomparision.realm;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import db.dbperfcomparision.realm.model.TestObject;
import db.dbperfcomparision.realm.model.TestSubObject;
import io.realm.Realm;
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
                    TestObject testObject = realm.createObject(TestObject.class);
                    testObject.setId(i);
                    testObject.setName("" + i);
                    testObject.setAge(i + 100);
                    testObject.setCreationTime(SystemClock.elapsedRealtimeNanos());
                    testObject.setUpdatedTime(SystemClock.elapsedRealtimeNanos());
                    List<TestSubObject> testSubObjectList = new ArrayList<>();
                    for (int j = 0; j < 100; j++) {
                        TestSubObject testSubObject = realm.createObject(TestSubObject.class);
                        testSubObject.setAge(j + 100);
                        testSubObject.setName("" + j);
                        testObject.getSubObjects().add(testSubObject);
                    }
                }
                Log.d(TAG, "end time: " + SystemClock.elapsedRealtimeNanos());
            }
        });
    }

    public rx.Observable<Boolean> bulkInsertAsync(final Realm realm) {
        return rx.Observable.create(new rx.Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
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

    public Observable<Boolean> bulkRemove(final Realm realm) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(TestObject.class);
                    }
                });
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
    }
}
