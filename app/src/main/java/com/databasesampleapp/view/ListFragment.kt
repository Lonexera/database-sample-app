package com.databasesampleapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.databasesampleapp.viewModels.ListViewModel
import com.databasesampleapp.R
import com.databasesampleapp.adapter.DogAdapter
import com.databasesampleapp.databinding.FragmentListBinding
import com.databasesampleapp.viewModels.DogViewModelFactory

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
    }

    private val dogAdapter = DogAdapter(
        onDeleteClick = {
            viewModel.onDelete(it)
        },
        onEditClick = {
            viewModel.onEdit(it)
        }
    )

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

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0)
                        binding.floatingAddButton.hide()
                    else
                        binding.floatingAddButton.show()
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }

        binding.floatingAddButton.setOnClickListener {
            viewModel.onAddClick()
        }

        binding.listToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_filter -> {
                    findNavController().navigate(R.id.action_listFragment_to_filterFragment)
                    true
                }
                R.id.settings -> {
                    findNavController().navigate(R.id.action_listFragment_to_settingsFragment)
                    true
                }
                else -> true
            }
        }

        with(viewModel) {
            toAdd.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }

            dogToUpdate.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { dog ->
                    findNavController().navigate(
                        ListFragmentDirections.actionListFragmentToUpdateFragment(
                            dogAge = dog.age,
                            dogId = dog.uid,
                            dogName = dog.name,
                            dogBreed = dog.breed
                        )
                    )
                }
            }

            getFilteredDogs(requireContext()).observe(viewLifecycleOwner) {
                dogAdapter.submitList(it)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
