package com.databasesampleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.databasesampleapp.DogItemListener
import com.databasesampleapp.R
import com.databasesampleapp.adapter.DogAdapter
import com.databasesampleapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var dogAdapter: DogAdapter

    interface ListFragmentListener {
        fun setListToAdapter(adapter: DogAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dogAdapter = DogAdapter(activity as DogItemListener)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(dy > 0)
                        binding.floatingAddButton.hide()
                    else
                        binding.floatingAddButton.show()
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }

        binding.floatingAddButton.setOnClickListener {
            navigateToAddScreen()
        }

        binding.listToolbar.setOnMenuItemClickListener { menuItem ->
             when(menuItem.itemId) {
                R.id.action_filter -> {
                    navigateToFilterScreen()
                    true
                }
                R.id.settings -> {
                    navigateToSettingsScreen()
                    true
                }
                else -> true
            }
        }

        (activity as ListFragmentListener).setListToAdapter(dogAdapter)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun navigateToAddScreen() {
        findNavController().navigate(R.id.action_listFragment_to_addFragment)
    }
    private fun navigateToFilterScreen() {
        findNavController().navigate(R.id.action_listFragment_to_filterFragment)
    }
    private fun navigateToSettingsScreen() {
        findNavController().navigate(R.id.action_listFragment_to_settingsFragment)
    }
}