package com.werockstar.reactivelyusecase


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_ui.*

class UiActivity : AppCompatActivity(R.layout.activity_ui) {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //TODO: Click and visual time of stream
//        button.clicks()
//            .subscribe()

        //TODO: Duplicate Click
//        button.clicks()
//            .debounce(150, TimeUnit.MILLISECONDS)
//            .subscribe()

        //TODO: Buffer
//        button.clicks()
//            .debounce(150, TimeUnit.MILLISECONDS)
//            .buffer(2)
//            .subscribe()

        val clicks1 = button.clicks()
            .map { button }
        val clicks2 = button2.clicks()
            .map { button2 }

        Observable.merge(clicks1, clicks2)
            .first(button)
            .toObservable()
            .subscribe {
                Log.d("Button", it.toString())
            }

    }

}
