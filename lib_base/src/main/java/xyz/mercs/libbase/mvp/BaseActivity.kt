package xyz.mercs.libbase.mvp

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        try{
            initView()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        try{
            deinitView()
        }catch (e:Exception){
            e.printStackTrace()
        }
        super.onDestroy()
    }

    @LayoutRes
    abstract fun getLayout():Int
    abstract fun initView()
    abstract fun deinitView()
}