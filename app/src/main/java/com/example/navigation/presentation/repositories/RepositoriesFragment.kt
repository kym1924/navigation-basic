package com.example.navigation.presentation.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation.R
import com.example.navigation.databinding.FragmentRepositoriesBinding
import com.example.navigation.util.repeatOnLifecycle
import com.example.navigation.util.visibleOrNot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

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
        initRecyclerView()
        initCollect()
        setTvEmptyListClickListener()
    }

    private fun initRecyclerView() {
        with(binding.rvRepositories) {
            setHasFixedSize(true)
            adapter = repositoriesAdapter.withLoadStateHeaderAndFooter(
                header = RepositoriesLoadAdapter { repositoriesAdapter.retry() },
                footer = RepositoriesLoadAdapter { repositoriesAdapter.retry() }
            )
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    private fun initCollect() {
        repeatOnLifecycle {
            repositoryViewModel.pagingRepositories(args.id).collectLatest { list ->
                repositoriesAdapter.submitData(list)
            }
        }

        repeatOnLifecycle {
            repositoriesAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvRepositories.scrollToPosition(0) }
        }

        repeatOnLifecycle {
            repositoriesAdapter.loadStateFlow.collectLatest { loadState ->
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && repositoriesAdapter.itemCount == 0
                binding.rvRepositories.visibleOrNot(!isListEmpty)
                binding.tvNoSearchResult.visibleOrNot(isListEmpty)
                binding.loadingProgress.visibleOrNot(loadState.source.refresh is LoadState.Loading)
                binding.tvRetry.visibleOrNot(loadState.source.refresh is LoadState.Error)
            }
        }
    }

    private fun setTvEmptyListClickListener() {
        binding.tvNoSearchResult.setOnClickListener {
            findNavController().navigate(R.id.action_repositoriesFragment_to_startFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
