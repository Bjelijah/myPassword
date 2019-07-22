package xyz.mercs.libbase.mvvm

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import xyz.mercs.libbase.mvvm.vm.BaseViewModel

open abstract class BaseFragment :Fragment(){
    protected var mBinder: ViewDataBinding?=null
    protected var mViewModel: BaseViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinder = DataBindingUtil.inflate(inflater,getLayout(),container,false)
        if (mViewModel==null)mViewModel = getViewModel().setContext(context!!)
        bind(mBinder!!,mViewModel!!)
        mViewModel!!.mMessage!!.observe(this, Observer{
            if(!TextUtils.isEmpty(it)) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
        return mBinder!!.root
    }


    @LayoutRes
    protected abstract fun getLayout():Int
    protected abstract fun getViewModel():BaseViewModel
    protected abstract fun bind(bind:ViewDataBinding,vm:BaseViewModel)

}