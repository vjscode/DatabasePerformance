package db.dbperfcomparision.snappydb;

import android.content.Context;
import android.os.SystemClock;

import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

import db.dbperfcomparision.snappydb.model.SnappyTestObject;
import db.dbperfcomparision.snappydb.model.SnappyTestSubObject;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by vijay on 6/12/16.
 */

public class SnappyDBManager {
    public Observable<Boolean> bulkInsertAsync(final Context context) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                DB snappydb = DBFactory.open(context, "test");
                for (int i = 1; i < 1000; i++) {
                    SnappyTestObject snappyTestObject = new SnappyTestObject();
                    snappyTestObject.setAge(i);
                    snappyTestObject.setName("" + i);
                    snappyTestObject.setId(i);
                    snappyTestObject.setUpdatedTime(SystemClock.elapsedRealtimeNanos());
                    snappyTestObject.setCreationTime(SystemClock.elapsedRealtimeNanos());
                    List<SnappyTestSubObject> snappyTestSubObjectList = new ArrayList<>();
                    for (int j = 0; j < 1000; j++) {
                        SnappyTestSubObject snappyTestSubObject = new SnappyTestSubObject();
                        snappyTestSubObject.setAge(j);
                        snappyTestSubObject.setName("" + j);
                        snappyTestSubObjectList.add(snappyTestSubObject);
                    }
                    snappyTestObject.setSubObjects(snappyTestSubObjectList);
                    snappydb.put(""+i, snappyTestObject);
                }
                } catch (SnappydbException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onNext(true);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Boolean> snappyBasicQuery(final Context context) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            boolean result = true;
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                DB snappydb = DBFactory.open(context, "test");
                    for (int i = 1; i < 1000; i++) {
                        SnappyTestObject snappyTestObject = snappydb.getObject("" + i, SnappyTestObject.class);
                        if (snappyTestObject.getId() != i) {
                            result = false;
                            break;
                        }
                    }
                    if (result) {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                } catch (SnappydbException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
