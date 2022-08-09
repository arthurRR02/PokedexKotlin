package com.arthurribeiro.foundation.views

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.arthurribeiro.foundation.databinding.ViewModalBinding
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Modal() : BottomSheetDialogFragment() {

    var layout: View? = null
    var isFullScreen: Boolean = false

    constructor(layout: View? = null, isFullScreen: Boolean = false) : this(){
        this.layout = layout
        this.isFullScreen = isFullScreen
    }

    private var _binding: ViewModalBinding? = null
    private var onDismissListener: ((DialogInterface) -> Unit)? = null
    private var onCancelListener: ((DialogInterface) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewModalBinding.inflate(inflater, container, false)
        _binding?.root?.addView(layout)
        return _binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding?.root?.removeAllViews()
        _binding = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
            parentLayout?.let { view ->
                val behavior = BottomSheetBehavior.from(view)
                if (isFullScreen) setFullHeight(view)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
                behavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN){
                            dismissAllowingStateLoss()
                        }
                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                })
            }
        }

        dialog.setOnCancelListener(onCancelListener)
        dialog.setOnDismissListener(onDismissListener)

        return dialog
    }

    override fun show(manager: FragmentManager, tag: String?) {
        manager.executePendingTransactions()

        val tagFragment = manager.findFragmentByTag(tag)
        if (tagFragment != null){
            manager.beginTransaction().remove(tagFragment).commitNowAllowingStateLoss()
        }

        if (this.isAdded) return
        super.show(manager, tag)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke(dialog)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        onCancelListener?.invoke(dialog)
    }

    private fun setFullHeight(bottomSheet: View){
        val params = bottomSheet.layoutParams
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = params
    }

}