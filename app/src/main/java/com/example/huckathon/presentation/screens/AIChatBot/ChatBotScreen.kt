package com.example.huckathon.presentation.screens.AIChatBot

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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
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

data class Message(
    val text: String? = null,
    val imageUri: String? = null,
    val isUser: Boolean
)

@Composable
fun ChatBotScreen() {
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var input by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    var showImageDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    
    LaunchedEffect(Unit) {
        messages = messages + Message(
            text = "Merhaba, ben Biyo-Chat! Size nasıl yardımcı olabilirim? Trafikte bir ihbarınız mı var?",
            isUser = false
        )
    }


    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                val uri = saveBitmapToCache(context, it)
                uri?.let {
                    messages = messages + Message(imageUri = uri.toString(), isUser = true)
                    coroutineScope.launch {
                        delay(400)
                        messages += Message(
                            text = "İhbarınız alınmıştır. En kısa sürede incelenip tarafınıza dönüş yapılacaktır.",
                            isUser = false
                        )
                    }
                }
            }
        }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                messages = messages + Message(imageUri = it.toString(), isUser = true)
                coroutineScope.launch {
                    delay(400)
                    messages += Message(
                        text = "İhbarınız alınmıştır. En kısa sürede incelenip tarafınıza dönüş yapılacaktır.",
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
                    text = "Biyo-Chat AI",
                    color = Color(0xFF00E676),
                    fontSize = 22.sp,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
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
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            IconButton(onClick = { showImageDialog = true }) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Default.Image),
                    contentDescription = "Görsel Ekle",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Mesajınızı yazın...") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedPlaceholderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = {
                if (input.text.isNotBlank()) {
                    val userMsg = Message(text = input.text, isUser = true)
                    messages += userMsg
                    input = TextFieldValue("")
                    messages += Message("Yanıt bekleniyor...", isUser = false)

                    coroutineScope.launch {
                        val reply = OpenAIClient.getSuggestionWithSystemPrompt(
                            prompt = userMsg.text!!,
                            systemPrompt = "Sen Biyo-Chat adında bir trafik yardımcısısın. Gelen mesajlara nazikçe ve açıklayıcı şekilde yanıt ver. Eğer bir fotoğraf gelirse bunun işleme alındığını belirt. Bana istersen ihbar verebilirsin"
                        )

                        messages = messages.dropLast(1) + Message(text = reply, isUser = false)
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Gönder",
                    tint = Color(0xFF00E676),
                    modifier = Modifier.size(28.dp)
                )
            }
        }


            if (showImageDialog) {
                AlertDialog(
                    onDismissRequest = { showImageDialog = false },
                    confirmButton = {},
                    containerColor = Color(0xFF1E1E1E),
                    title = {
                        Text("Görsel Seç", color = Color.White)
                    },
                    text = {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        cameraLauncher.launch()
                                        showImageDialog = false
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    tint = Color(0xFFFFC107)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Kamera ile Fotoğraf Çek", color = Color.White)
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        galleryLauncher.launch("image/*")
                                        showImageDialog = false
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Image,
                                    contentDescription = null,
                                    tint = Color(0xFF64B5F6)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Galeriden Görsel Yükle", color = Color.White)
                            }
                        }
                    }
                )
            }
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



