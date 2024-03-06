package daniel.brian.introtocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import daniel.brian.introtocompose.ui.theme.IntroToComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntroToComposeTheme {
                // A surface container using the 'background' color from the theme
                 MyApp()
            }
        }
    }
}

@Composable
fun MyApp(){
    // we are hoisting our create circle state to make that composable function stateless.
    // This makes the CreateCircle composable more reusable
    var moneyCounter by remember {
        mutableIntStateOf(0)
    }
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        color = Color(0xFF546E7A),
    ) {
        Column (modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "$${moneyCounter}", style = androidx.compose.ui.text.TextStyle(
                color = Color.White,
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(40.dp))
            // We're passing the moneyCounter to the function as an argument.
            // We can open the curly brackets to update our function using a lambda
            CreateCircle(moneyCounter){newValue ->
                moneyCounter = newValue
            }

            // we can use money counter because we're having access to it through hoisting
            if (moneyCounter > 50){
                Text(text = "Hey Lots of money!")
            }
        }
    }
}


@Composable
// This composable function is now stateless - as it should be.
// inorder to update our money counter we need to pass some arguments to our composable
// We're passing the updateCount function to update our money counter once we click
fun CreateCircle(moneyCount : Int,updateCount: (Int) -> Unit){
    Card(
        modifier = Modifier
            .padding(3.dp)
            .size(65.dp)
            // setting the onclick property
            .clickable {
                updateCount(moneyCount + 1)
            },
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
                Text(text = "Tap")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IntroToComposeTheme {
        MyApp()
    }
}