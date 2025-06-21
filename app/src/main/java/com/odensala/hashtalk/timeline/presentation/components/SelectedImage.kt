package com.odensala.hashtalk.timeline.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.odensala.hashtalk.R

@Composable
fun SelectedImage(
    imageUri: Uri,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = imageUri,
            contentDescription = stringResource(R.string.selected_image),
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(200.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp)),
        )
        IconButton(
            onClick = onRemove,
            modifier =
                Modifier.align(Alignment.TopEnd)
                    .padding(16.dp),
        ) {
            Icon(Icons.Default.Close, contentDescription = stringResource(R.string.remove_image))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSelectedImage() {
    SelectedImage(
        imageUri = Uri.EMPTY,
        onRemove = {},
    )
}