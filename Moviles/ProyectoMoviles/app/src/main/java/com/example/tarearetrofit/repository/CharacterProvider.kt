package com.example.tarearetrofit.repository

import com.example.tarearetrofit.retrofit.StarWarsApi
import com.example.tarearetrofit.recyclerview.Character
import com.example.tarearetrofit.recyclerview.Film

class CharacterProvider(private val api: StarWarsApi)
{
    suspend fun getCharacter(id: Int): Result<Character>
    {
        try
        {
            val character: Character = api.getCharacter(id)
            return Result.success(character)
        }
        catch (e: Exception)
        {
            return Result.failure(e)
        }
    }

    suspend fun getFilm(url: String): Film {
        return api.getFilm(url)
    }

    suspend fun getFilms(): Result<List<Film>> {
        try {
            val response = api.getFilms()
            return Result.success(response.results)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}