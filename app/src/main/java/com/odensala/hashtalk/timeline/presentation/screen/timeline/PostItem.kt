package com.odensala.hashtalk.timeline.presentation.screen.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.Timestamp
import com.odensala.hashtalk.R
import com.odensala.hashtalk.core.presentation.components.LoadingButton
import com.odensala.hashtalk.core.presentation.theme.paddingMedium
import com.odensala.hashtalk.timeline.presentation.util.formatToDateString

@Composable
fun PostItem(post: PostUiModel, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(paddingMedium)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = post.userEmail,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = post.timestamp.formatToDateString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyLarge
            )

            // Image
            post.imageUrl?.let { imageUrl ->

                Spacer(modifier = Modifier.height(12.dp))

                AsyncImage(
                    model = imageUrl,
                    contentDescription = stringResource(R.string.post_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            LoadingButton(
                onClick = { onDelete() },
                text = stringResource(R.string.delete)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostItemPreview() {
    PostItem(
        post =
        PostUiModel(
            id = "1",
            userEmail = "doraemon@gmail.com",
            content = "This is a sample post content.",
            imageUrl = "https://example.com/image.jpg",
            timestamp = Timestamp.now()
        ),
        onDelete = { }
    )
}
