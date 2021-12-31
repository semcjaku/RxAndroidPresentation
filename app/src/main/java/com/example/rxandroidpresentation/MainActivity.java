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
        Example currentExample = Example.RANGE;

        switch(currentExample) {
            case CREATE:
            case JUST:
                fruitObservable = createJustObservable();
                defaultFruitSubscribe(fruitObservable);
                break;
            case RANGE:
                Observable<Integer> integerObservable = createRangeObservable();
                integerSubscribe(integerObservable);
                break;
            case REPEAT:
                fruitObservable = createRepeatObservable();
                defaultFruitSubscribe(fruitObservable);
                break;
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
                defaultFruitSubscribe(fruitObservable);
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

    private Observable<Fruit> createRepeatObservable() {
        return Observable
                .fromIterable(DataSource.createFruitsList())
                .repeat(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    private Observable<Fruit> createJustObservable() {
        Fruit cherry = new Fruit("cherry", false, 2);
        Fruit orange = new Fruit("orange", true, 10);
        return Observable
                .just(cherry, orange)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    private Observable<Integer> createRangeObservable() {
        return Observable
                .range(2,5)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    private void defaultFruitSubscribe(Observable<Fruit> fruitObservable) {
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

    private void integerSubscribe(Observable<Integer> integerObservable) {
        integerObservable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
                Log.d(TAG, "Observer subscribed.");
            }

            @Override
            public void onNext(@NonNull Integer x) {
                Log.i(TAG, "Processing number: " + x);
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