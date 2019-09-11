package xyz.mercs.main.ui

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import xyz.mercs.libcommon.ARouterPath
import xyz.mercs.libcommon.base.mvvm.BaseActivity
import xyz.mercs.libcommon.base.mvvm.vm.BaseViewModel
import xyz.mercs.main.vm.MainViewModel
import xyz.mercs.modulemain.BR
import xyz.mercs.modulemain.R

@Route(path = ARouterPath.MAIN_ATY)
class MainActivity:BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_main

    override fun getViewModel(): BaseViewModel =  ViewModelProviders.of(this).get(MainViewModel::class.java)

    override fun bind(bind: ViewDataBinding, vm: BaseViewModel) {
        bind.setVariable(BR.vm,vm)
    }
}