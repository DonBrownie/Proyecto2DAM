package com.example.tarearetrofit.retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import com.example.tarearetrofit.recyclerview.Character
import com.example.tarearetrofit.recyclerview.Film
import com.example.tarearetrofit.recyclerview.FilmListResponse
interface StarWarsApi {
    @GET("people/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Character
    @GET
    suspend fun getFilm(@Url url: String): Film

    @GET("films/")
    suspend fun getFilms(): FilmListResponse
}