package com.example.chatgptfree.ui.home

import android.util.DisplayMetrics
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearSmoothScroller
import com.example.chatgptfree.R
import com.example.chatgptfree.base.BaseFragment
import com.example.chatgptfree.data.model.completion.Message
import com.example.chatgptfree.data.model.message.MessCompletion
import com.example.chatgptfree.databinding.FragmentHomeBinding
import com.example.chatgptfree.ui.adapter.MessageAdapter
import com.example.chatgptfree.utils.IntegerCallback
import com.example.chatgptfree.utils.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(){
    override val layoutResource: Int
        get() = R.layout.fragment_home
    override val viewModel by viewModel<HomeViewModel>()

    private val messAdapter = MessageAdapter(::clickMess)
    override fun setupViews() {

    }

    override fun setupData() {

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            homeVM = viewModel
            recyclerView.adapter = messAdapter
        }
    }

    override fun setupActions() {
        binding?.apply {
            sendBtn.setOnClickListener {
                val text = messageEditText.text.toString()
                if (text.isNotBlank() && viewModel.isLoading.value == false){
                    viewModel.setContentToMe(MessCompletion(text,true),messAdapter,callback)
                    messageEditText.setText("")
                    val message = Message("user",text)
                    hideKeyboard()
                    messageEditText.clearFocus()
                    lifecycleScope.launch(Dispatchers.IO){
                        viewModel.getCompletion(message,preferenceHelper.token,messAdapter,callback)
                    }
                }
            }

            messageEditText.setOnFocusChangeListener { view, b ->
                if (b && messAdapter.itemCount > 0)
                    callback.execute(messAdapter.itemCount)
            }
        }
    }

    private val callback = object : IntegerCallback{
        override fun execute(int: Int) {
           // binding?.recyclerView?.smoothScrollToPosition(int -1 )
            val smoothScroller: LinearSmoothScroller =
                object : LinearSmoothScroller(context) {
                    override fun getVerticalSnapPreference(): Int {
                        return SNAP_TO_START
                    }

                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                        return 100f / displayMetrics.densityDpi
                    }
                }
            smoothScroller.targetPosition = int - 1
            binding?.recyclerView?.layoutManager?.startSmoothScroll(smoothScroller);
        }
    }

    private fun clickMess(mess: MessCompletion){

    }
}