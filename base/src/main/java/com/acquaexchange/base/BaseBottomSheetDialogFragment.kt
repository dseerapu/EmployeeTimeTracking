package com.acquaexchange.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.acquaexchange.base.utils.collect
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import timber.log.Timber


/**
 * Base Fragment of all the fragments
 *
 * @param <VM>
 * @param <Binding>
</Binding></VM> */
abstract class BaseBottomSheetDialogFragment<VM : BaseViewModel, Binding : ViewDataBinding> :
    BottomSheetDialogFragment() {

    private var baseActivity: BaseActivity<*, *>? = null

    @Suppress("PropertyName")
    protected abstract val TAG: String

    lateinit var vm: VM

    lateinit var dataBinding: Binding

    @LayoutRes
    protected abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        try {
            dataBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
            dataBinding.lifecycleOwner = viewLifecycleOwner
        } catch (e: Exception) {
            throw RuntimeException(TAG, e)
        }
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToast()
        setUp()
        initObservers(viewLifecycleOwner)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("Base:onAttach - $TAG")
        if (context is BaseActivity<*, *>) {
            this.baseActivity = context
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    abstract fun initObservers(viewLifecycleOwner: LifecycleOwner)

    abstract fun setUp()

    abstract fun getViewModel(): VM

    private fun setUpToast() {
        lifecycleScope.launchWhenResumed {
            vm.displayToastChannel.collect { toastMessage ->
                baseActivity!!.displayToast(toastMessage)
            }
        }
    }

}