package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.estados

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inhometfgandroidcarloshernandez.R

@Composable
fun EstadosActivity(
    viewModel: EstadosViewModel = hiltViewModel(),
    innerPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        if (uiState.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            PantallaEstados(
                titulo = uiState.nombreCasa ?: "",
                direccion = uiState.direccion ?: "",
                usuariosCasa = uiState.usuariosCasa,
                estadoActual = uiState.estadoActual ?: ""
            )
        }
    }
}

@Composable
fun PantallaEstados(
    titulo: String,
    direccion: String,
    usuariosCasa: List<EstadosContract.UsuarioCasa>,
    estadoActual: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = direccion,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Lista de usuarios
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(usuariosCasa) { usuario ->
                UsuarioItem(
                    nombre = usuario.nombre,
                    estado = usuario.estado
                )
            }
        }

        // Estado actual del usuario
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Acción al pulsar el botón */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = estadoActual, color = Color.White)
        }
    }
}

@Composable
fun UsuarioItem(nombre: String, estado: String) {
    val backgroundColor = when (estado) {
        "En casa" -> Color.Green
        "Fuera de casa" -> Color.Red
        "Durmiendo" -> Color.Yellow
        else -> Color.Gray
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            GreetingImage(modifier = Modifier.padding(end = 25.dp))
            Column {
                Text(text = nombre, color = Color.White, style = MaterialTheme.typography.bodyLarge)
                Text(text = estado, color = Color.White, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
@Composable
fun GreetingImage(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.fotoperfilandroid),
        contentDescription = "Imagen de foto de perfil",
        modifier = modifier
            .size(60.dp)
            .clip(RoundedCornerShape(8.dp))

    )
}


@Preview(showBackground = true)
@Composable
fun PantallaEstadosPreview() {
    PantallaEstados(
        titulo = "Casa de la Colina",
        direccion = "123 Calle Principal",
        usuariosCasa = listOf(
            EstadosContract.UsuarioCasa("Carlos", "Durmiendo"),
            EstadosContract.UsuarioCasa("Laura", "En casa"),
            EstadosContract.UsuarioCasa("David", "Fuera de casa")
        ),
        estadoActual = "Durmiendo"
    )
}
