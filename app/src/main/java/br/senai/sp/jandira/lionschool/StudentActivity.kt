package br.senai.sp.jandira.lionschool

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.AlunoNotas
import br.senai.sp.jandira.lionschool.model.Alunos
import br.senai.sp.jandira.lionschool.model.CursoNotas
import br.senai.sp.jandira.lionschool.model.ListaCursos
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val matricula = intent.getStringExtra("matricula")
        setContent {
            LionSchoolTheme {
                if (matricula != null) {
                    StudentPreview(matricula)
                }
            }
        }
    }
}

@Composable
fun StudentScreen(matricula: String) {

    fun getBarColor(status: String): Color {
        return if (status == "Aprovado"){
            Color(50, 71, 176)
        } else if (status == "Exame") {
            Color(255, 194, 63)
        } else {
            Color(176, 50, 50)
        }
    }

    val defaultFont = FontFamily(Font(R.font.roboto_black))

    var aluno by remember {
        mutableStateOf(AlunoNotas("", "", "", "", CursoNotas("", "", emptyList())))
    }

    val call = RetrofitFactory().getAlunosService().getAlunoByMatricula(matricula = matricula)

    call.enqueue(object : Callback<AlunoNotas> {
        override fun onResponse(
            call: Call<AlunoNotas>,
            response: Response<AlunoNotas>
        ) {
            aluno = response.body()!!
        }

        override fun onFailure(call: Call<AlunoNotas>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })

    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(86.dp),
                verticalAlignment = CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(75.dp)
                        .background(
                            Color(50, 71, 176),
                            shape = RoundedCornerShape(bottomEnd = 30.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier
                            .height(54.dp)
                            .width(43.dp)
                    )
                }

                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = "Lion School",
                    fontFamily = defaultFont,
                    fontSize = 30.sp,
                    color = Color(50, 71, 176)
                )
            }

            LazyColumn(modifier = Modifier
                .fillMaxWidth().height(670.dp)
                .padding(start = 30.dp, end = 30.dp, bottom = 30.dp, top = 50.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween){
                item {

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(328.dp)
                        .background(
                            color = if (aluno.status == "Cursando")
                                Color(50, 71, 176)
                            else
                                Color(255, 194, 63), shape = RoundedCornerShape(30.dp)
                        )
                        .border(
                            width = 2.dp, shape = RoundedCornerShape(30.dp),
                            color = if (aluno.status == "Cursando")
                                Color(255, 194, 63)
                            else
                                Color(50, 71, 176)
                        ),
                    contentAlignment = Center) {
                        AsyncImage(model = aluno.foto, contentDescription = "Foto do Aluno",
                        modifier = Modifier.fillMaxSize())
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = aluno.nome,
                        color = Color(50, 71, 176),
                        fontFamily = defaultFont,
                        fontSize = 27.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = "RM: ${aluno.matricula}",
                        color = Color (50, 71, 176),
                        fontFamily = defaultFont,
                        fontSize = 15.sp)

                        Text(text = aluno.status.uppercase(),
                            color = Color (50, 71, 176),
                            fontFamily = defaultFont,
                            fontSize = 15.sp)
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                        aluno.curso.disciplinas.forEach(){disciplina ->
                            Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = CenterVertically) {
                                Text(text = disciplina.sigla,
                                    color = Color (50, 71, 176),
                                    fontFamily = defaultFont,
                                    fontSize = 20.sp,
                                modifier = Modifier.width(60.dp))

                                Row(modifier = Modifier
                                    .height(15.dp)
                                    .width(200.dp)
                                    .background(
                                        shape = RoundedCornerShape(
                                            topEnd = 5.dp,
                                            bottomEnd = 5.dp
                                        ),
                                        color = Color(217, 217, 217)
                                    )) {
                                    Row(modifier = Modifier
                                        .fillMaxHeight()
                                        .width(((disciplina.media.toDouble()) * 2).dp)
                                        .background(
                                            shape = RoundedCornerShape(
                                                topEnd = 5.dp,
                                                bottomEnd = 5.dp
                                            ),
                                            color = getBarColor(disciplina.status)
                                        )){
                                    }
                                }
                                
                                Text(text = disciplina.media,
                                    color = Color (50, 71, 176),
                                    fontFamily = defaultFont,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.width(40.dp))
                            }
                        }

                }
            }

            Row(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom) {
                Box(modifier = Modifier
                    .height(85.dp)
                    .background(
                        color = Color(50, 71, 176),
                        shape = RoundedCornerShape(topStart = 30.dp)
                    )
                    .width(156.dp))
            }
        }
    }
}

@Composable
fun StudentPreview(matricula: String) {
    StudentScreen(matricula)
}