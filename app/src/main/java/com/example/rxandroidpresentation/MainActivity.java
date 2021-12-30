package com.example.rxandroidpresentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main activity";

    private TextView text;

    CompositeDisposable disposables = new CompositeDisposable();

    public enum Example {
        MAIN, CREATE, JUST, RANGE, REPEAT, INTERVAL, TIMER, FROMARRAY, FILTER, DISTINCT, TAKE, TAKEWHILE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);

        Observable<Fruit> fruitObservable;

        // modify to switch between examples
        Example currentExample = Example.MAIN;

        switch(currentExample) {
            case CREATE:
            case JUST:
            case RANGE:
            case REPEAT:
            case INTERVAL:
            case TIMER:
            case FROMARRAY:
            case FILTER:
            case DISTINCT:
            case TAKE:
            case TAKEWHILE:
            case MAIN:
            default:
                fruitObservable = createDefaultObservable();
                defaultSubscribe(fruitObservable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    private Observable<Fruit> createDefaultObservable() {
        return Observable
                .fromIterable(DataSource.createFruitsList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void defaultSubscribe(Observable<Fruit> fruitObservable) {
        fruitObservable.subscribe(new Observer<Fruit>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
                Log.d(TAG, "Observer subscribed.");
            }

            @Override
            public void onNext(@NonNull Fruit fruit) {
                Log.i(TAG, "Processing fruit: " + fruit);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "Observer error: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Observer completed.");
            }
        });
    }
}