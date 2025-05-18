package com.example.huckathon.presentation.screens.mapScreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector


private val PanelBackground = Color(0xFF1E2A3A)
val CardBackground = Color(0xFF2B3A4D)
private val TextPrimary = Color.White
private val TextSecondary = Color(0xFFB0B8C1)

@Composable
fun RoutePersonaLeftSheet(
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { -310 },
            animationSpec = tween(300)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { -310 },
            animationSpec = tween(300)
        ),
        modifier = Modifier.zIndex(1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .width(310.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .fillMaxHeight()
                    .background(Color(0xFF1E2A3A))
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .background(Color(0xFF1E2A3A))
                    .padding(24.dp)
            ) {
                Text(
                    text = "Route Personas",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))
                PersonaCard("Relaxed Voyager", "2.5 km", Color(0xFFB388FF), Icons.Default.SelfImprovement)
                Spacer(modifier = Modifier.height(16.dp))
                PersonaCard("Eco Warrior", "2.5 km", Color(0xFF66BB6A), Icons.Default.Eco)
                Spacer(modifier = Modifier.height(16.dp))
                PersonaCard("Speed Seeker", "2.5 km", Color(0xFFFFA726), Icons.Default.Bolt)
                Spacer(modifier = Modifier.height(16.dp))
                PersonaCard("Accessibility", "2.5 km", Color(0xFF64B5F6), Icons.Default.Accessible)

                }
            }
        }
}

@Composable
fun LeftSheetToggleButton(
    isOpen: Boolean,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(2f),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = 8.dp)
                .size(48.dp)
                .clip(RoundedCornerShape(50))
                .background(Color(0xFF2B3A4D))
                .clickable { onToggle() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isOpen) Icons.Default.ChevronLeft else Icons.Default.ChevronRight,
                contentDescription = "Toggle Panel",
                tint = Color.White
            )
        }
    }
}


@Composable
fun PersonaCard(
    name: String,
    description: String,
    dotColor: Color,
    icon: ImageVector? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(50))
                .background(dotColor),
            contentAlignment = Alignment.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                color = TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "2.5 km",
                color = TextSecondary,
                fontSize = 13.sp
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextSecondary
        )
    }
}
