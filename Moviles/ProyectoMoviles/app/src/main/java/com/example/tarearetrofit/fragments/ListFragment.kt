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
import com.example.tarearetrofit.databinding.FragmentListBinding
import com.example.tarearetrofit.recyclerview.*
import com.example.tarearetrofit.retrofit.RetrofitInstance
import com.example.tarearetrofit.ui.CharacterViewModel
import kotlinx.coroutines.launch
import com.example.tarearetrofit.ui.*
import com.example.tarearetrofit.repository.*
import com.google.android.material.snackbar.Snackbar
import com.example.tarearetrofit.R

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val viewModel: CharacterViewModel by lazy {
        val provider = CharacterProvider(RetrofitInstance.api)
        val factory = CharacterViewModelFactory(provider)
        ViewModelProvider(this, factory)[CharacterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { state ->
                    when (state) {

                        CharacterUiState.Idle -> Unit

                        CharacterUiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rv.visibility = View.GONE

                            binding.rv.layoutManager = LinearLayoutManager(requireContext())
                        }

                        is CharacterUiState.Success -> {


                            val characters = listOf(state.character)


                            val adapter = CharacterAdapter(requireContext(), characters)


                            adapter.setOnItemClickListener(object: CharacterAdapter.OnItemClickListener {
                                override fun onItemClick(position: Int) {
                                    val character = characters[position]

                                    val filmList = character.films
                                    val fragment = DetailFragment.newInstance(character.name,ArrayList(filmList)
                                    )
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.nav_host_fragment, fragment)
                                        .addToBackStack(null)
                                        .commit()
                                }
                            })

                            binding.progressBar.visibility = View.GONE
                            binding.rv.visibility = View.VISIBLE
                            binding.rv.adapter = CharacterAdapter(requireContext(), characters)

                            }

                        is CharacterUiState.Error -> {

                            binding.progressBar.visibility = View.GONE
                            binding.rv.visibility = View.VISIBLE

                            Snackbar.make(
                                binding.root,
                                state.message,
                                Snackbar.LENGTH_LONG
                            ).show()

                        }

                        is CharacterUiState.SuccessList -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rv.visibility = View.VISIBLE
                            val adapter = CharacterAdapter(requireContext(), state.character)

                            adapter.setOnItemClickListener(object: CharacterAdapter.OnItemClickListener {
                                override fun onItemClick(position: Int) {
                                    val character = state.character[position]
                                    val filmList = character.films
                                    val fragment = DetailFragment.newInstance(character.name, ArrayList(filmList))
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.nav_host_fragment, fragment)
                                        .addToBackStack(null)
                                        .commit()
                                }
                            })
                            binding.rv.adapter = adapter
                        }

                        is CharacterUiState.SuccessFilms -> Unit
                    }
                }
            }

        }//Coroutine

        binding.button.setOnClickListener {
            val stringInput = binding.textInputLayout.editText?.text.toString()

            if(stringInput.isNotEmpty()) {
                val cantidad = stringInput.toInt()
                viewModel.loadMultipleCharacters(cantidad)
            } else {
                Snackbar.make(binding.root, "Por favor escribe una cantidad", Snackbar.LENGTH_LONG).show()
            }

        }


    }
}

