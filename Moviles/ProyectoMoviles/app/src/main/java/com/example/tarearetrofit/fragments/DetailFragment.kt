package com.example.tarearetrofit.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarearetrofit.databinding.FragmentDetailBinding
import com.example.tarearetrofit.recyclerview.FilmAdapter
import com.example.tarearetrofit.repository.CharacterProvider
import com.example.tarearetrofit.retrofit.RetrofitInstance
import com.example.tarearetrofit.ui.FilmUiState
import com.example.tarearetrofit.ui.FilmViewModel
import com.example.tarearetrofit.ui.FilmViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
private const val ARG_NAME = "character_name"
private const val ARG_FILMS = "film_urls"
class DetailFragment : Fragment() {
    private var characterName: String? = null
    private var filmUrls: ArrayList<String>? = null

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: FilmViewModel by lazy {
        val provider = CharacterProvider(RetrofitInstance.api)
        val factory = FilmViewModelFactory(provider)
        ViewModelProvider(this, factory)[FilmViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            characterName = it.getString(ARG_NAME)
            filmUrls = it.getStringArrayList(ARG_FILMS)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCharacterName.text = characterName

        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        filmUrls?.let { urls ->
            viewModel.loadFilms(urls)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when(state) {
                        is FilmUiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvMovies.visibility = View.GONE
                        }
                        is FilmUiState.Success -> {
                            binding.rvMovies.adapter = FilmAdapter(state.films, characterName ?: "")
                            binding.progressBar.visibility = View.GONE
                            binding.rvMovies.visibility = View.VISIBLE


                        }
                        is FilmUiState.Error -> {
                            binding.progressBar.visibility = View.GONE

                            Snackbar.make(
                                binding.root,
                                state.message,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(name: String, films: ArrayList<String>) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putStringArrayList(ARG_FILMS, films)
                }
            }
    }
}