package com.kidnapsteal.coincommunity.presentation.conversation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kidnapsteal.coincommunity.R
import kotlinx.android.synthetic.main.layout_conversation_bottom_sheet.*

class ConversationBottomSheet(context: Context) : BottomSheetDialog(context/*, R.style.BottomSheetTransparent*/) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_conversation_bottom_sheet)
    }

    override fun show() {
        super.show()
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    var actionClick: View.OnClickListener = View.OnClickListener { }
        set(value) {
            field = value
            actionCancel.setOnClickListener(field)
            actionDelete.setOnClickListener(field)
        }
}