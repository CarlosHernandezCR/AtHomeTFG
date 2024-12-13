package com.example.inhometfgandroidcarloshernandez.ui.framework.screens.registro
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inhometfgandroidcarloshernandez.R
import com.example.inhometfgandroidcarloshernandez.common.Constantes
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun RegistroActivity(
    onRegisterSuccess: () -> Unit,
    showSnackbar: (String) -> Unit,
    innerPadding: PaddingValues,
    viewModel: RegistroViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.mensaje) {
        uiState.mensaje?.let {
            showSnackbar(it)
            viewModel.handleEvent(RegistroContract.RegistroEvent.MensajeMostrado)
        }
    }
    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            onRegisterSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        RegistroScreen(
            username = uiState.nombre,
            password = uiState.password,
            correo = uiState.correo,
            telefono = uiState.telefono,
            color = uiState.color,
            onColorChange = { viewModel.handleEvent(RegistroContract.RegistroEvent.ColorChange(it)) },
            onUsernameChange = { viewModel.handleEvent(RegistroContract.RegistroEvent.UsernameChange(it)) },
            onPasswordChange = { viewModel.handleEvent(RegistroContract.RegistroEvent.PasswordChange(it)) },
            onCorreoChange = { viewModel.handleEvent(RegistroContract.RegistroEvent.CorreoChange(it)) },
            onTelefonoChange = { viewModel.handleEvent(RegistroContract.RegistroEvent.TelefonoChange(it)) },
            register = { viewModel.handleEvent(RegistroContract.RegistroEvent.Registro) },
            modifier = Modifier.align(Alignment.Center),
            isLoading = uiState.isLoading
        )
    }
}


@Composable
fun RegistroScreen(
    username: String,
    password: String,
    correo: String,
    telefono: String,
    color: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCorreoChange: (String) -> Unit,
    onTelefonoChange: (String) -> Unit,
    onColorChange: (String) -> Unit,
    register: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean
) {
    var showColorPicker by remember { mutableStateOf(false) }
    val defaultColor = Color.White
    var selectedColor by remember { mutableStateOf(
        if (color.isNotEmpty()) Color(android.graphics.Color.parseColor(color)) else defaultColor
    )}

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val audioWide = FontFamily(
                Font(R.font.audiowide)
            )
            Text(
                text = Constantes.REGISTRO,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = audioWide
            )
            Spacer(modifier = Modifier.height(25.dp))
            TextField(
                value = username,
                onValueChange = onUsernameChange,
                label = { Text(Constantes.NOMBRE) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(Constantes.CONTRASENA) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = correo,
                onValueChange = onCorreoChange,
                label = { Text(Constantes.CORREO) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = telefono,
                onValueChange = onTelefonoChange,
                label = { Text(Constantes.TELEFONO) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showColorPicker = true },
                modifier = Modifier.fillMaxWidth().
                background(color = selectedColor)
            ) {
                Text(Constantes.SELECCIONAR_COLOR)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = register,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Constantes.REGISTRARSE)
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }
        if (showColorPicker) {
            ColorPickerDialog(
                onDismiss = { showColorPicker = false },
                onColorSelected = { color ->
                    selectedColor = color
                    onColorChange("#${Integer.toHexString(color.toArgb())}")
                    showColorPicker = false
                }
            )
        }
    }
}

@Composable
fun ColorPickerDialog(
    onDismiss: () -> Unit,
    onColorSelected: (Color) -> Unit
) {
    val controller = rememberColorPickerController()
    var tempColor by remember { mutableStateOf(Color.White) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(10.dp),
                controller = controller,
                onColorChanged = { colorEnvelope ->
                    tempColor = Color(colorEnvelope.color.toArgb())
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                ) {
                    Text("Cerrar")
                }
                Button(
                    onClick = {
                        onColorSelected(tempColor)
                        onDismiss()
                    },
                    modifier = Modifier
                ) {
                    Text("Aceptar")
                }
            }
        }
    }
}


@Preview
@Composable
fun RegistroScreenPreview() {
    RegistroScreen(
        username = "",
        password = "",
        correo = "",
        telefono = "",
        color = "",
        onUsernameChange = {},
        onPasswordChange = {},
        onCorreoChange = {},
        onTelefonoChange = {},
        register = {},
        isLoading = false,
        onColorChange = {}
    )
}