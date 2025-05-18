package com.example.huckathon.presentation.screens.AIChatBot

import MessageBubble
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.huckathon.R
import com.example.huckathon.network.OpenAIClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun ChatBotScreen() {
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var input by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    var showImageDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            uri?.let {
                messages = messages + Message(imageUri = it.toString(), isUser = true)
                coroutineScope.launch {
                    delay(400) // yükleme hissi
                    messages = messages + Message(
                        text = "İhbarınız alınmıştır. En kısa sürede incelenip tarafınıza dönüş yapılacaktır. Teşekkür ederiz.",
                        isUser = false
                    )
                }
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            messages = messages + Message(imageUri = it.toString(), isUser = true)
            coroutineScope.launch {
                delay(400)
                messages = messages + Message(
                    text = "İhbarınız alınmıştır. En kısa sürede incelenip tarafınıza dönüş yapılacaktır. Teşekkür ederiz.",
                    isUser = false
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(8.dp)
    ) {
        if (messages.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Biyo-Chat",
                    color = Color(0xFF00E5FF),
                    fontSize = 22.sp,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(messages.reversed()) { msg ->
                MessageBubble(msg)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E1E), RoundedCornerShape(24.dp))
                .padding(8.dp)
        ) {
            IconButton(onClick = { showImageDialog = true }) {
                Icon(Icons.Default.Image, contentDescription = "Fotoğraf Seç", tint = Color.White)
            }

            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Mesaj yaz...") },
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (input.text.isNotBlank()) {
                        val userMsg = Message(text = input.text, isUser = true)
                        messages = messages + userMsg
                        input = TextFieldValue("")

                        messages = messages + Message("Yanıt bekleniyor...", isUser = false)

                        coroutineScope.launch {
                            val reply = OpenAIClient.getSuggestion(userMsg.text!!)
                            messages = messages.dropLast(1) + Message(text = reply, isUser = false)
                        }
                    }
                }
            ) {
                Text("Gönder")
            }
        }
    }

    if (showImageDialog) {
        AlertDialog(
            onDismissRequest = { showImageDialog = false },
            confirmButton = {},
            title = { Text("Görsel Ekle") },
            text = {
                Column {
                    Text("📸 Kamera ile Fotoğraf Çek", modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            cameraLauncher.launch()
                            showImageDialog = false
                        })

                    Text("🖼️ Galeriden Seç", modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            galleryLauncher.launch("image/*")
                            showImageDialog = false
                        })
                }
            }
        )
    }
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()

        val file = File(cachePath, "photo_${System.currentTimeMillis()}.jpg")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()

        Uri.fromFile(file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


data class Message(
    val text: String? = null,
    val imageUri: String? = null,
    val isUser: Boolean
)



