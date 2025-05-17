package com.example.huckathon.presentation.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.huckathon.R
import com.example.huckathon.presentation.screens.profile.CardBackgroundColor
import com.example.huckathon.presentation.screens.profile.SecondaryTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search Destinations"
) {
    var query by remember { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = { query = it },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.mercek),
                contentDescription = "Search Icon",
                tint = SecondaryTextColor
            )
        },
        placeholder = {
            androidx.compose.material3.Text(
                text = placeholder,
                color = SecondaryTextColor
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackgroundColor),
//        colors = TextFieldDefaults.textFieldColors(
//            containerColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent
//        )
    )
}