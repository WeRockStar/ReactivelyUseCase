package com.werockstar.reactivelyusecase


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.activity_ui.*

class UiActivity : AppCompatActivity(R.layout.activity_ui) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        button.clicks()
            .subscribe()
    }
}
