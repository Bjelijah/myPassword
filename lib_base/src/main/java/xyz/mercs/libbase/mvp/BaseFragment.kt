package xyz.mercs.libbase.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open abstract class BaseFragment : Fragment() {
    private var mLayout: View?=null
    private var isInitView = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if(mLayout==null){
            mLayout = inflater.inflate(getLayout(),container,false)
        }
        try {
            initView()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return mLayout
    }




    override fun onDestroyView() {
        super.onDestroyView()
        try{
            deinitView()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }


    @LayoutRes
    abstract fun getLayout():Int
    abstract fun initView()
    abstract fun deinitView()
}