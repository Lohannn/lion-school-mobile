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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import java.util.Vector

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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListAlunosScreen() {
    val defaultFont = FontFamily(Font(R.font.roboto_black))

    var textField by remember {
        mutableStateOf("")
    }

    var coursingState by remember {
        mutableStateOf(false)
    }

    var finalizedState by remember {
        mutableStateOf(false)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(86.dp),
            verticalAlignment = CenterVertically) {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .width(75.dp)
                    .background(
                        Color(50, 71, 176),
                        shape = RoundedCornerShape(bottomEnd = 30.dp)
                    ),
                contentAlignment = Center) {
                    Image(painter = painterResource(id = R.drawable.logo),
                        contentDescription = "",
                    modifier = Modifier
                        .height(54.dp)
                        .width(43.dp))
                }

                Spacer(modifier = Modifier.width(18.dp))

                Text(text = "Lion School",
                    fontFamily = defaultFont,
                    fontSize = 30.sp,
                    color = Color(50, 71, 176))
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(text = "Técnico em Desenvolvimento de Sistemas",
                fontFamily = defaultFont,
                fontSize = 17.sp,
                color = Color(50, 71, 176))

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(modifier = Modifier
                .width(328.dp)
                .height(50.dp)
                .border(border = BorderStroke(color = Color.Transparent, width = 0.dp)),
                value = textField,
                onValueChange = { textField = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(50, 71, 176),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(35.dp),
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = defaultFont,
                ),
                placeholder = {
                    Text(
                        text = "Pesquise um aluno...",
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
            
            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier
                .width(328.dp)
                .height(45.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
                IconToggleButton(
                    checked = coursingState,
                    onCheckedChange = { checked ->
                        // Ativa o botão atual
                        coursingState = checked
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(156.dp)
                        .background(
                            color = if (!coursingState) Color(50, 71, 176)
                            else Color(255, 194, 63),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Row(modifier = Modifier.fillMaxHeight().fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.baseline_create_24),
                            contentDescription = "cursando",
                            tint = if (!coursingState) Color(255, 194, 63)
                            else Color(50, 71, 176),
                            modifier = Modifier.size(25.dp))
                        Text(text = "Cursando",
                            color = if (!coursingState) Color(255, 194, 63)
                            else Color(50, 71, 176),
                            fontFamily = defaultFont,
                            fontSize = 19.sp)
                    }
                }
                IconToggleButton(
                    checked = finalizedState,
                    onCheckedChange = { checked ->
                        finalizedState = checked
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(156.dp)
                        .background(
                            color = if (!finalizedState) Color(50, 71, 176)
                            else Color(255, 194, 63),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Row(modifier = Modifier.fillMaxHeight().fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically) {
                        Icon(painter = painterResource(id = R.drawable.concluido),
                            contentDescription = "finalizado",
                            tint = if (!finalizedState) Color(255, 194, 63)
                            else Color(50, 71, 176),
                            modifier = Modifier.size(25.dp))
                        Text(text = "Finalizado",
                            color = if (!finalizedState) Color(255, 194, 63)
                            else Color(50, 71, 176),
                            fontFamily = defaultFont,
                            fontSize = 19.sp)
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