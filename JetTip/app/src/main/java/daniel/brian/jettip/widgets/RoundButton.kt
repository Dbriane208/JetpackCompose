package daniel.brian.jettip.widgets


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// customizing our round icon button to be used in add and subtract icon
val IconButtonSizeModifier = Modifier.size(40.dp)
@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    elevation: Dp = 4.dp,
    contentAlignment: Alignment = Alignment.Center
){
    Card(modifier = modifier
        .padding(all = 4.dp)
        // we're using the .then extension function to give an animation when the button is clicked
        .clickable { onClick.invoke() }
        .then(IconButtonSizeModifier)
        .background(backgroundColor),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(elevation),
        ){
        Box(modifier = modifier.fillMaxSize(),contentAlignment = contentAlignment) {
            Icon(imageVector = imageVector, contentDescription = "Plus or minus icon",
                // tint is changing the color of our button
                tint = tint)
        }
    }
}