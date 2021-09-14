package com.databasesampleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.databasesampleapp.R
import com.databasesampleapp.adapter.DogAdapter
import com.databasesampleapp.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding = _binding!!

    private val dogAdapter = DogAdapter()

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

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogAdapter
        }

        binding.floatingAddButton.setOnClickListener {
            // TODO implement add on click listener
        }

        binding.listToolbar.setOnMenuItemClickListener { menuItem ->
             when(menuItem.itemId) {
                R.id.action_filter -> {
                    // TODO implement on click filter listener
                    true
                }
                R.id.settings -> {
                    // TODO implement on click settings listener
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


}