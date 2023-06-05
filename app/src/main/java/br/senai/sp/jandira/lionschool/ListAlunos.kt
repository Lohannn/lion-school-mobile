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
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListAlunosPreview() {
    ListAlunosScreen()
}