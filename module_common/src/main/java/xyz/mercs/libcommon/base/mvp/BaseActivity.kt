package xyz.mercs.libcommon.base.mvp

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import xyz.mercs.libcommon.utils.PhoneUtil

abstract class BaseActivity :AppCompatActivity() {

    protected var mNavigationBarHeight = 0
    protected var mStateBarHeight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        var v = LayoutInflater.from(this).inflate(getLayout(),null)
        setContentView(v)
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.statusBarColor = Color.TRANSPARENT//防止5.x以后半透明影响效果，使用这种透明方式
        }
        mStateBarHeight = PhoneUtil.getStateBarHeight(this)
        if (PhoneUtil.checkNavigationBarShow(this,window)){
            mNavigationBarHeight = PhoneUtil.getVirtualBarHeight(this)

            Log.i("123", "h=$mNavigationBarHeight   h2=$mStateBarHeight")
            v.setPadding(0,0,0,mNavigationBarHeight-mStateBarHeight)
        }
        setStateBarColor()

        try{
            initView()
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    override fun onResume() {
        super.onResume()
//        hideBottomUIMenu()
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



    fun hideBottomUIMenu(){
        val v = window.decorView
        if(Build.VERSION.SDK_INT in 12..18){
            v.systemUiVisibility = View.GONE
        }else if(Build.VERSION.SDK_INT>=19){
            var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE
            v.systemUiVisibility=uiOptions

        }
    }

    fun setStateBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }


    }














}