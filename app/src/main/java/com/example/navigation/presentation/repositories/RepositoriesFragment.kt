package com.example.navigation.presentation.repositories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation.R
import com.example.navigation.databinding.FragmentRepositoriesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoriesFragment : Fragment() {
    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val repositoryViewModel by viewModels<RepositoriesViewModel>()
    private val repositoriesAdapter by lazy { RepositoriesAdapter() }
    private val args by navArgs<RepositoriesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRvRepositoriesAdapter()
        setVerticalItemDecoration()
        setTvEmptyListClickListener()
        setRepositoriesCollect()
        setLoadingCollect()
        repositoryViewModel.getRepositories(args.id)
    }

    private fun setRvRepositoriesAdapter() {
        binding.rvRepositories.adapter = repositoriesAdapter
    }

    private fun setVerticalItemDecoration() {
        binding.rvRepositories.addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        )
    }

    private fun setTvEmptyListClickListener() {
        binding.tvNoSearchResult.setOnClickListener {
            findNavController().navigate(R.id.action_repositoriesFragment_to_startFragment)
        }
    }

    private fun setRepositoriesCollect() {
        repeatOnLifecycle {
            repositoryViewModel.repositories.collect { list ->
                repositoriesAdapter.submitList(list) {
                    setTvNoSearchResultVisibility(list.size)
                }
            }
        }
    }

    private fun setTvNoSearchResultVisibility(size: Int) {
        binding.tvNoSearchResult.visibility = when (size) {
            0 -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }

    private fun setLoadingCollect() {
        repeatOnLifecycle {
            repositoryViewModel.loading.collect { loading ->
                binding.loadingProgress.visibility = when (loading) {
                    true -> View.VISIBLE
                    false -> View.INVISIBLE
                }
            }
        }
    }

    private fun Fragment.repeatOnLifecycle(collect: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
