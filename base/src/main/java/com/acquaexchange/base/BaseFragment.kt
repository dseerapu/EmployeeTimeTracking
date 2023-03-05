package com.acquaexchange.base

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.acquaexchange.base.utils.collect
import com.acquaexchange.base.utils.isAutomaticDateTimeEnabled
import timber.log.Timber


/**
 * Base Fragment of all the fragments
 *
 * @param <VM>
 * @param <Binding>
</Binding></VM> */
abstract class BaseFragment<VM : BaseViewModel, Binding : ViewDataBinding> : Fragment() {

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
        setUp()
        initObservers(viewLifecycleOwner)
    }

    private fun setUpToast() {
        lifecycleScope.launchWhenResumed {
            vm.displayToastChannel.collect { toastMessage ->
                baseActivity!!.displayToast(toastMessage)
            }
        }
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

    override fun onResume() {
        super.onResume()
        checkIsAutomaticDateTimeEnabled()
    }

    private var automaticDateSettingsDialog: AlertDialog? = null

    private fun checkIsAutomaticDateTimeEnabled() {
        if (context?.isAutomaticDateTimeEnabled() == false) {
            showAutomaticDateSettingsDialog()
        } else {
            automaticDateSettingsDialog?.dismiss()
        }
    }

    private fun showAutomaticDateSettingsDialog() {

        if (automaticDateSettingsDialog?.isShowing == true) return
        automaticDateSettingsDialog = AlertDialog.Builder(requireContext())
            .setTitle("Automatic date is disabled")
            .setMessage("need to enable automatic date in your device")
            .setCancelable(false)
            .setPositiveButton("Settings") { dialog, _ ->
                val intent = Intent(Settings.ACTION_DATE_SETTINGS)
                startActivity(intent)
                dialog.dismiss()
            }.create()
        automaticDateSettingsDialog!!.show()
    }

}