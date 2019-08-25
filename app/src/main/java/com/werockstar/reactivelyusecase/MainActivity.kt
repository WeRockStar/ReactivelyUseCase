package com.werockstar.reactivelyusecase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        buttonUI.setOnClickListener {
            startActivity(Intent(this, UiActivity::class.java))
        }
    }
}
