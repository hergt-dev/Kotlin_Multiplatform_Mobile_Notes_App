package de.hergt.kmm.notes.android.modules

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconTextFloatingActionButton(text: String, icon: ImageVector, modifier: Modifier, onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text(text = text) },
        onClick = { onClick() },
        modifier = modifier,
        icon = { Icon(icon, icon.name) }
    )
}