package xyz.mercs.libcommon.base.mvvm

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import xyz.mercs.libcommon.base.mvvm.vm.BaseViewModel

open abstract class BaseActivity:AppCompatActivity() {
    var mBinder: ViewDataBinding?=null
    var mViewModel: BaseViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        mBinder = DataBindingUtil.setContentView(this,getLayout())
        mViewModel = getViewModel().initResource(this)
        bind(mBinder!!,mViewModel!!)
        mViewModel!!.mMessage!!.observe(this, Observer{
            if (!TextUtils.isEmpty(it)) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    @LayoutRes
    protected abstract fun getLayout():Int
    protected abstract fun getViewModel(): BaseViewModel
    protected abstract fun bind(bind:ViewDataBinding,vm: BaseViewModel)

}