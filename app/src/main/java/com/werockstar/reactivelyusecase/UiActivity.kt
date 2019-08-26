package com.werockstar.reactivelyusecase


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.activity_ui.*
import retrofit2.HttpException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class UiActivity : AppCompatActivity(R.layout.activity_ui) {

    private val api by lazy { API() }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        button.clicks()
            .debounce(150, TimeUnit.MILLISECONDS)
            .subscribe()

        editText.afterTextChangeEvents()
            .debounce(150, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribe()

        editText.afterTextChangeEvents()
            .skipInitialValue()
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .map { it.editable.toString() }
            .switchMap {
                api.getResult(it)
                    .retryWhen {
                        it.flatMap { throwable ->
                            when (val isUnauthorized = throwable is HttpException && throwable.code() == 401) {
                                isUnauthorized -> api.getSomeToken("refresh_token")
                                else -> Observable.error(throwable)
                            }
                        }
                    }
            }
            .subscribe()
    }

    class API {
        fun getResult(keyword: String): Observable<String> {
            return Observable.error(TimeoutException())
        }

        fun getSomeToken(refresh: String): Observable<String> {
            return Observable.just("token")
        }
    }
}
