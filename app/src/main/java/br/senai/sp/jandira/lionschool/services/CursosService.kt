package br.senai.sp.jandira.lionschool.services

import br.senai.sp.jandira.lionschool.model.ListaCursos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CursosService {

    //https://lionschool-api.cyclic.app/v1/lion-school/

    @GET("cursos")
    fun getCursos(): Call<ListaCursos>

}