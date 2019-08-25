package com.werockstar.reactivelyusecase


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import kotlinx.android.synthetic.main.activity_ui.*
import java.util.concurrent.TimeUnit

class UiActivity : AppCompatActivity(R.layout.activity_ui) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        button.clicks()
            .debounce(150, TimeUnit.MILLISECONDS)
            .subscribe()

        editText.afterTextChangeEvents()
            .debounce(150, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribe()

    }
}
