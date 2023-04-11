package com.example.chatgptfree.ui.home

import com.example.chatgptfree.R
import com.example.chatgptfree.base.BaseFragment
import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.Message
import com.example.chatgptfree.data.model.message.MessCompletion
import com.example.chatgptfree.databinding.FragmentHomeBinding
import com.example.chatgptfree.ui.adapter.MessageAdapter
import com.example.chatgptfree.utils.IntegerCallback
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
                    viewModel.getCompletion(message,preferenceHelper.token,messAdapter,callback)
                }
            }
        }
    }

    private val callback = object : IntegerCallback{
        override fun execute(int: Int) {
            binding?.recyclerView?.scrollToPosition(int -1 )
        }
    }

    private fun clickMess(mess: MessCompletion){

    }
}