package com.example.activityresultcontractsjetpackcompose

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.activityresultcontractsjetpackcompose.ui.theme.ActivityResultContractsJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivityResultContractsJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ImagePicker()
                }
            }
        }
    }
}

@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
) {
    var hasImage by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )

    val context = LocalContext.current
    Box(
        modifier = modifier,
    ) {
        if (hasImage && imageUri != null) {
            AsyncImage(
                model = imageUri,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Selected image",
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = {
                    imagePicker.launch("image/*")
                },
            ) {
                Text(
                    text = "Select Image"
                )
            }
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    Log.i("anisham", "anisham 1")
                    val uri = ComposeFileProvider.getImageUri(context)
                    Log.i("anisham", "anisham + ${uri}")
                    imageUri = uri
                    cameraLauncher.launch(uri)
                },
            ) {
                Text(
                    text = "Take photo"
                )
            }
        }
    }
}
