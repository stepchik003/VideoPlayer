package com.example.videoplayer.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videoplayer.R
import com.example.videoplayer.databinding.FragmentVideoListBinding
import com.example.videoplayer.domain.models.Video
import com.example.videoplayer.presentation.player.VideoPlayerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoListFragment: Fragment() {

    private var _binding: FragmentVideoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideoListViewModel by viewModels()

    private val adapter = VideoAdapter {onVideoClicked(it)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoListBinding.inflate(inflater, container, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadVideos()

        binding.recyclerView.apply {
            adapter = this@VideoListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        lifecycleScope.launch {
            viewModel.videos.collectLatest {
                adapter.submitList(it)
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshVideos()
        }

        lifecycleScope.launch {
            viewModel.isRefreshing.collect {
                binding.swipeRefreshLayout.isRefreshing = it
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect {
                it?.let {
                    Toast.makeText(context, "Ошибка: ${it.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onVideoClicked(video: Video) {
        val bundle = Bundle().apply {
            putParcelable("VIDEO", video)
        }
        val fragment = VideoPlayerFragment().apply {
            arguments = bundle
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}