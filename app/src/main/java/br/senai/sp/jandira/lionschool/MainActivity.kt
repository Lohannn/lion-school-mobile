package br.senai.sp.jandira.lionschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                HomeScreenPreview()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val defaultFont = FontFamily(Font(R.font.roboto_black))
    var textField by remember {
        mutableStateOf("")
    }

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

            LazyRow(modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .padding(top = 50.dp)){
                items(2){ index ->
                    var buttonState by remember {
                        mutableStateOf(false)
                    }

                    IconToggleButton(checked = buttonState,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                // Desativa todos os outros botões
                                (0..1).forEachIndexed { i, _ ->
                                    if (i != index) {
                                        buttonState[i] = false
                                    }
                                }
                            }
                            buttonState = isChecked
                        },
                        modifier = Modifier
                            .padding(end = 15.dp)
                            .fillMaxHeight()
                            .width(171.dp)
                            .border(2.dp, if(!buttonState)Color(255, 194, 63)
                            else Color.White,
                                RoundedCornerShape(5.dp))
                            .background(if(!buttonState)Color(50, 71, 176)
                            else Color(255, 194, 63),
                                RoundedCornerShape(5.dp)
                            )) {

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}