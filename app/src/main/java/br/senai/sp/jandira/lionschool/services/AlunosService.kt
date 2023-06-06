package br.senai.sp.jandira.lionschool.services

import br.senai.sp.jandira.lionschool.model.Alunos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlunosService {

    //https://lionschool-api.cyclic.app/v1/lion-school/

    @GET("alunos")
    fun getAlunos(): Call<Alunos>

    @GET("alunos")
    fun getAlunosByCourse( @Query("curso") siglaCurso: String): Call<Alunos>

}