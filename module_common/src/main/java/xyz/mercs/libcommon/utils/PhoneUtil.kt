package xyz.mercs.libcommon.utils

import android.R.attr.y
import android.R.attr.bottom
import android.opengl.ETC1.getWidth
import android.R.attr.x
import android.R.attr.orientation
import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.NonNull
import java.lang.Exception


object PhoneUtil {

    /**
     * 判断虚拟导航栏是否显示
     *
     * @param context 上下文对象
     * @param window  当前窗口
     * @return true(显示虚拟导航栏)，false(不显示或不支持虚拟导航栏)
     */
    fun checkNavigationBarShow(@NonNull context: Context, @NonNull window: Window): Boolean {
        val display = window.windowManager.defaultDisplay
        val point = Point()
        display.getRealSize(point)

        val decorView = window.decorView
        val conf = context.resources.configuration
        return if (Configuration.ORIENTATION_LANDSCAPE === conf.orientation) {
            val contentView = decorView.findViewById<View>(android.R.id.content)
            point.x !== contentView.width
        } else {
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            rect.bottom !== point.y
        }
    }

    fun getVirtualBarHeight(context: Context):Int{
        var vh = 0
        var windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var display = windowManager.defaultDisplay
        var dm = DisplayMetrics()
        try{
            var c = Class.forName("android.view.Display")
            var method = c.getMethod("getRealMetrics",DisplayMetrics::class.java)
            method.invoke(display,dm)
            vh = dm.heightPixels - display.height

        }catch (e:Exception){
            e.printStackTrace()
        }
        return vh
    }

    fun getStateBarHeight(c:Context):Int
        = c.resources.getDimensionPixelSize(c.resources.getIdentifier("status_bar_height", "dimen", "android"))






}