package com.example.chatgptfree.ui.home

import com.example.chatgptfree.R
import com.example.chatgptfree.base.BaseFragment
import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.Message
import com.example.chatgptfree.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : BaseFragment<FragmentHomeBinding>(){
    override val layoutResource: Int
        get() = R.layout.fragment_home
    override val viewModel by viewModel<HomeViewModel>()

    override fun setupViews() {

    }

    override fun setupData() {

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            mainVM = viewModel
        }
        val message = Message("user","Say this is a test!")
        val content = CompletionChat("gpt-3.5-turbo", listOf(message),0.7)
        viewModel.getCompletion(content,"Bearer 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")
    }

    override fun setupActions() {

    }

}