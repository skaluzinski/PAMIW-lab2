package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import model.Resource

@Composable
fun ResourceColumn(modifier: Modifier, textHeader: String,items: List<Resource>, onClick: (Resource) -> Unit) {
    LazyColumn(
        modifier = modifier.background(Color.Cyan),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item(key = "button") {
            Text(text = textHeader,
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(4.dp))
                    .border(BorderStroke(2.dp, Color.Black))
            )
        }
        items(items) {
            TextButton(
                onClick = { onClick(it) },
                modifier = Modifier.padding(16.dp).clip(RoundedCornerShape(4.dp))
                    .border(BorderStroke(2.dp, Color.Black)),
            ) {
                Text(it.englishName)
            }
        }
    }

}