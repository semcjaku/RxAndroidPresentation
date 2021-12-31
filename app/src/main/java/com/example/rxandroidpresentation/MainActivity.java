package com.example.rxandroidpresentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.reactivestreams.Subscriber;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main activity";

    private TextView text;

    CompositeDisposable disposables = new CompositeDisposable();

    public enum Example {
        MAIN, CREATE, JUST, RANGE, REPEAT, INTERVAL, TIMER, FROMARRAY, FILTER, DISTINCT, TAKE, TAKEWHILE, LAB_TASK
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);

        Observable<Fruit> fruitObservable;
        Observable<Integer> integerObservable;
        Observable<Long> longObservable;

        // modify to switch between examples
        Example currentExample = Example.LAB_TASK;

        switch(currentExample) {
            case CREATE:
                fruitObservable = createObservable();
                defaultFruitSubscribe(fruitObservable);
                break;
            case JUST:
                fruitObservable = createJustObservable();
                defaultFruitSubscribe(fruitObservable);
                break;
            case RANGE:
                integerObservable = createRangeObservable();
                integerSubscribe(integerObservable);
                break;
            case REPEAT:
                fruitObservable = createDefaultObservable().repeat(3);
                defaultFruitSubscribe(fruitObservable);
                break;
            case INTERVAL:
                longObservable = createIntervalObservable();
                longSubscribe(longObservable);
                break;
            case TIMER:
                longObservable = createTimerObservable();
                longSubscribe(longObservable);
                break;
            case FROMARRAY:
                fruitObservable = createFromArrayObservable();
                defaultFruitSubscribe(fruitObservable);
                break;
            case FILTER:
                fruitObservable = createFilterObservable();
                defaultFruitSubscribe(fruitObservable);
                break;
            case DISTINCT:
                fruitObservable = createDistinctObservable();
                defaultFruitSubscribe(fruitObservable);
                break;
            case TAKE:
                fruitObservable = createDefaultObservable().take(2);
                defaultFruitSubscribe(fruitObservable);
                break;
            case TAKEWHILE:
                fruitObservable = createTakeWhileObservable();
                defaultFruitSubscribe(fruitObservable);
                break;
            case LAB_TASK:
                //fruitObservable = createLabTaskObservable();
                //defaultFruitSubscribe(fruitObservable);
                break;
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

//    private Observable<Fruit> createLabTaskObservable() {
//        return Observable
//                .fromIterable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());*/
//    }

    private Observable<Fruit> createObservable() {
        return Observable
                .create(new ObservableOnSubscribe<Fruit>() {
                    @Override
                    public void subscribe(@androidx.annotation.NonNull ObservableEmitter<Fruit> emitter){

                        try{
                            for(Fruit f: DataSource.createFruitsList()){
                                if(!emitter.isDisposed()){
                                    emitter.onNext(f);
                                }
                            }

                            if(!emitter.isDisposed()){
                                emitter.onComplete();
                            }
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Fruit> createDefaultObservable() {
        return Observable
                .fromIterable(DataSource.createFruitsList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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

    private Observable<Long> createIntervalObservable() {
        return Observable
                .interval(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    private Observable<Long> createTimerObservable() {
        return Observable
                .timer(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    private Observable<Fruit> createFromArrayObservable() {
        Fruit[] list = new Fruit[3];
        list[0] = (new Fruit("raspberry", true, 3));
        list[1] = (new Fruit("strawberry", false, 2));
        list[2] = (new Fruit("pineapple", true, 1));
        return Observable
                .fromArray(list)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    private Observable<Fruit> createFilterObservable() {
        return Observable
                .fromIterable(DataSource.createFruitsList())
                // predicate can be a simple lambda as well, for example: .filter(x -> x % 2 == 0), when we're operating on numbers
                .filter(new Predicate<Fruit>() {
                    @Override
                    public boolean test(Fruit fruit) {
                        return fruit.getName().equals("apple");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    private Observable<Fruit> createDistinctObservable() {
        return Observable
                .fromIterable(DataSource.createFruitsList())
                // if the data has the comparison function already defined, a simple argument-less .distinct() operator can be used
                .distinct(new Function<Fruit, String>() {
                    @Override
                    public String apply(Fruit fruit) {
                        return fruit.getName();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Fruit> createTakeWhileObservable() {
        return Observable
                .fromIterable(DataSource.createFruitsList())
                .takeWhile(fruit -> !fruit.isRotten())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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

    private void longSubscribe(Observable<Long> integerObservable) {
        integerObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposables.add(d);
                Log.d(TAG, "Observer subscribed.");
            }

            @Override
            public void onNext(@NonNull Long x) {
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