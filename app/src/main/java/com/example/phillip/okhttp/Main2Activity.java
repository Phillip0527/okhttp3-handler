package com.example.phillip.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Observer<String> observer=new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onStart() {
                super.onStart();
            }
        };

        //create方式
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext( "aa" ) ;
                subscriber.onNext( "bb" ) ;
                subscriber.onNext( "cc" ) ;
                subscriber.onCompleted();
            }
        });

        //just方式  最多支持10个数据
        Observable<String> observable1 = Observable.just( "aa" , "bb" , "cc") ;

        //from方式
        //1:集合
        List<String> list = new ArrayList<>() ;
        list.add( "aa" ) ;
        list.add( "bb" ) ;
        list.add( "cc" ) ;
        Observable<String> observable2 = Observable.from( list ) ;

        //2：数组
        String[] words = { "aa", "bb", "cc" };
        Observable<String> observable3 = Observable.from( words ) ;


        observable.subscribe(observer);
        observable.subscribe(subscriber);
    }

}
