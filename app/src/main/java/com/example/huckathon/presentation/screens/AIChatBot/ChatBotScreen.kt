package com.example.huckathon.presentation.screens.AIChatBot

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotScreen(
    navController: NavHostController
) {
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var input by remember { mutableStateOf(TextFieldValue("")) }
    var showImageDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // ilk karşılama mesajı
    LaunchedEffect(Unit) {
        messages = messages + Message(
            text = "Merhaba, ben Biyo-Chat! Size nasıl yardımcı olabilirim? Trafikte bir ihbarınız mı var?",
            isUser = false
        )
    }

    // kamera / galeri launcher
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bmp: Bitmap? ->
        bmp?.let {
            saveBitmapToCache(context, it)?.let { uri ->
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
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
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

    Scaffold(
        containerColor = Color(0xFF121212),
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF121212)) {
                val items = listOf(
                    "map_screen"    to R.drawable.harita,
                    "chatbot"       to R.drawable.chatbot,
                    "profile"       to R.drawable.profile,
                    "settings"      to R.drawable.settings
                )
                val current = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { (route, icon) ->
                    NavigationBarItem(
                        icon = { Icon(painterResource(icon), contentDescription = route) },
                        selected = current == route,
                        onClick = {
                            when (route) {
                                "map_screen" -> navController.navigate(route)
                                "chatbot"    -> { /* zaten buradasınız */ }
                                "profile"    -> navController.navigate(route)
                                "settings"   -> navController.navigate(route)
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor   = Color(0xFF00E676),
                            unselectedIconColor = Color.Gray,
                            indicatorColor      = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(innerPadding)
                .pointerInput(Unit) {
                    // harita veya chat alanına özel gesture gerekirse buraya
                    detectHorizontalDragGestures { _, _ -> }
                }
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = true,
                contentPadding = PaddingValues(8.dp)
            ) {
                items(messages.reversed()) { msg ->
                    MessageBubble(msg)
                    Spacer(Modifier.height(8.dp))
                }
            }

            // input + iconlar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1E1E1E), RoundedCornerShape(24.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                IconButton(onClick = { showImageDialog = true }) {
                    Icon(Icons.Default.Image, contentDescription = "Görsel Ekle", tint = Color(0xFFFFC107))
                }
                Spacer(Modifier.width(8.dp))
                TextField(
                    value = input ,
                    onValueChange = { input = it},
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color(0xFF00E676),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = {
                    val text = input.text.trim()
                    if (text.isNotEmpty()) {
                        messages += Message(text = text, isUser = true)
                        input = TextFieldValue("")
                        messages += Message(text = "Yanıt bekleniyor...", isUser = false)
                        coroutineScope.launch {
                            val reply = OpenAIClient.getSuggestionWithSystemPrompt(
                                prompt = text,
                                systemPrompt = "Sen Biyo-Chat adında bir trafik yardımcısısın..."
                            )
                            messages = messages.dropLast(1) + Message(text = reply, isUser = false)
                        }
                    }
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Gönder", tint = Color(0xFF00E676))
                }
            }

            if (showImageDialog) {
                AlertDialog(
                    onDismissRequest = { showImageDialog = false },
                    confirmButton = {},
                    containerColor = Color(0xFF1E1E1E),
                    title = { Text("Görsel Seç", color = Color.White) },
                    text = {
                        Column {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        cameraLauncher.launch()
                                        showImageDialog = false
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color(0xFFFFC107))
                                Spacer(Modifier.width(12.dp))
                                Text("Kamera ile Fotoğraf Çek", color = Color.White)
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        galleryLauncher.launch("image/*")
                                        showImageDialog = false
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Image, contentDescription = null, tint = Color(0xFF64B5F6))
                                Spacer(Modifier.width(12.dp))
                                Text("Galeriden Görsel Yükle", color = Color.White)
                            }
                        }
                    }
                )
            }
        }
    }
}

private fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri? {
    return try {
        val cacheDir = File(context.cacheDir, "images").apply { mkdirs() }
        val file = File(cacheDir, "photo_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        Uri.fromFile(file)
    } catch (e: Exception) {
        e.printStackTrace(); null
    }
}
