package org.d3if4118.asesmen.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

 abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected var binding: VB? = null

    abstract fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup): VB

    abstract fun setupViewModel()

    abstract fun setupUI(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container?.let { setupViewBinding(inflater, it) }
        setupViewModel()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}