package com.acquaexchange.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.acquaexchange.base.logger.logException

/**
 * Base Activity for the activities
 *
 * @param <Binding>*/
abstract class BaseActivity<Binding : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity(),
    LifecycleOwner {

    @Suppress("PropertyName")
    protected abstract val TAG: String

    lateinit var viewModel: VM

    /**
     * Abstract method to init layout of activity
     *
     * @return
     */
    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    /**
     * Binding layout
     */
    lateinit var dataBinding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getHiltViewModel()

        try {
            dataBinding = DataBindingUtil.setContentView(this@BaseActivity, getLayoutResource())
        } catch (e: Exception) {
            logException(e)
        }

        dataBinding.lifecycleOwner = this

        setUp()

        initObservers()

        setUpToast()
    }

    private fun setUpToast() {
        lifecycleScope.launchWhenResumed {
            val toastMessage = viewModel.displayToastChannel.receive()
            displayToast(toastMessage)
        }
    }

    fun displayToast(toastMessage: String) {
        Toast.makeText(this@BaseActivity, toastMessage, Toast.LENGTH_SHORT).show()
    }

    abstract fun getHiltViewModel(): VM

    protected abstract fun setUp()

    protected abstract fun initObservers()

}
