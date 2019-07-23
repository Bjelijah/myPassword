package xyz.mercs.prefgo.model.rxbus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class RxBus private constructor(){
    companion object {
        @Volatile
        private var sInstance:RxBus ?= null
            get() {
                if (field==null){
                    field = RxBus()
                }
                return field
            }
        fun getDefault():RxBus = sInstance!!
    }

    private var bus : Subject<Any> = PublishSubject.create<Any>().toSerialized()

    fun post(action: Any) = bus.onNext(action)

    fun postWithCode(code:Int,action:Any) = bus.onNext(Action(code,action))

    fun <T> toObservable(eventType:Class<T>): Observable<T> = bus.ofType(eventType)

    fun <T> toObservableWithCode(code:Int,eventType:Class<T>): Observable<T> =
            bus.ofType(Action::class.java)
                .filter { it.code==code }
                .map { it.data }
                .cast(eventType)
}