package br.senai.sp.jandira.lionschool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.model.ListaCursos
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ListAlunos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                ListAlunosPreview()
            }
        }
    }
}

@Composable
fun ListAlunosScreen() {
    val defaultFont = FontFamily(Font(R.font.roboto_black))
    var textField by remember {
        mutableStateOf("")
    }
    var listCursos by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Curso>())
    }

    val call = RetrofitFactory().getCursosService()
        .getCursos()




    call.enqueue(object : Callback<ListaCursos> {
        override fun onResponse(
            call: Call<ListaCursos>,
            response: Response<ListaCursos>
        ) {
            listCursos = response.body()!!.cursos
        }

        override fun onFailure(call: Call<ListaCursos>, t: Throwable) {
            TODO("Not yet implemented")
        }

    })


    Log.i("ds2t", "${listCursos}")

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(50, 71, 176))
                .padding(start = 30.dp, end = 30.dp, bottom = 30.dp, top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(183.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo da Escola Lion School",
                    modifier = Modifier.size(147.dp, 183.dp)
                )

                Text(
                    text = "Lion\nSchool",
                    fontFamily = defaultFont,
                    fontSize = 46.sp,
                    color = Color.White,
                    letterSpacing = 4.sp
                )
            }
            Text(
                text = "Escolha um curso para gerenciar:",
                fontFamily = defaultFont,
                fontSize = 25.sp,
                color = Color.White,
                letterSpacing = 3.sp,
                modifier = Modifier.padding(top = 25.dp, bottom = 50.dp)
            )

            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(border = BorderStroke(color = Color.Transparent, width = 0.dp)),
                value = textField,
                onValueChange = {textField = it},
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(110, 131, 235),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(35.dp),
                textStyle = TextStyle(fontSize = 15.sp,
                    fontFamily = defaultFont,
                ),
                placeholder = {
                    Text(
                        text = "Pesquise um curso...",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = defaultFont
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.busca),
                        contentDescription = "Search",
                        tint = Color(255, 194, 63),
                        modifier = Modifier.size(50.dp)
                    )
                })

            val buttonStates = remember { mutableStateListOf<Boolean>() }
            buttonStates.addAll(List(listCursos.size) { false })

            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(top = 50.dp)) {

                itemsIndexed(listCursos) { index, curso ->
                    val buttonState = buttonStates.getOrNull(index) ?: false

                    IconToggleButton(
                        checked = buttonState,
                        onCheckedChange = { checked ->
                            // Desativa todos os outros botões
                            buttonStates.fill(false)
                            // Ativa o botão atual
                            buttonStates[index] = checked
                        },
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .fillMaxHeight()
                            .width(171.dp)
                            .border(
                                width = 2.dp,
                                color = if (!buttonState) Color(255, 194, 63)
                                else Color.White,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .background(
                                color = if (!buttonState) Color(50, 71, 176)
                                else Color(255, 194, 63),
                                shape = RoundedCornerShape(5.dp)
                            )
                    ) {

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListAlunosPreview() {
    ListAlunosScreen()
}