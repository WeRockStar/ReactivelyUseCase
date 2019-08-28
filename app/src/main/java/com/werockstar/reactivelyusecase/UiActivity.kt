package com.werockstar.reactivelyusecase


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_ui.*
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class UiActivity : AppCompatActivity(R.layout.activity_ui) {

    private val api by lazy { API() }
    private val db by lazy { Database() }


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
                            when (val isUnauthorized =
                                throwable is HttpException && throwable.code() == 401) {
                                isUnauthorized -> api.getSomeToken("refresh_token")
                                else -> Observable.mergeDelayError(
                                    api.delete1(),
                                    api.deleteWasError()
                                )
                            }
                        }
                    }
            }
            .subscribe()


        Observable.mergeDelayError(api.delete1(), api.deleteWasError(), api.delete3())
            .subscribe()
    }

    class Database {
        fun getResult(): Observable<String> {
            return Observable.just("DB")
                .delay(100, TimeUnit.MILLISECONDS)

        }
    }

    class API {
        fun getResult(keyword: String): Observable<String> {
            return Observable.just("API")
        }

        fun getResult(): Observable<String> {
            return Observable.just("API")
                .delay(50, TimeUnit.MILLISECONDS)
        }

        fun delete1(): Observable<Unit> {
            return Observable.empty()
        }

        fun deleteWasError(): Observable<Unit> {
            return Observable.empty()
        }

        fun delete3(): Observable<Unit> {
            return Observable.empty()
        }

        fun getSomeToken(refresh: String): Observable<String> {
            return Observable.just("token")
        }
    }
}
