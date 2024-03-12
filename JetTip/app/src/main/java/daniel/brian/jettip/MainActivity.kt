package daniel.brian.jettip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import daniel.brian.jettip.components.InputField
import daniel.brian.jettip.ui.theme.JetTipTheme
import daniel.brian.jettip.util.calculateTotalPerPerson
import daniel.brian.jettip.util.calculateTotalTip
import daniel.brian.jettip.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit){
    JetTipTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }

    }
}

// Designing the daniel.brian.jettip.TopHeader of our screen
@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 134.0){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(150.dp)
            // using method one to clip the corners of the surface
            // .clip(shape = CircleShape.copy(all = CornerSize(12.dp)))
            // method two to clip the corners of the surface
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        // adding the background color
        color = Color(0xFF9D5DCF)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Total Per Person", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
            Text(text = "$${totalPerPerson}", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineLarge)
        }

    }
}

@Preview
@Composable
fun MainContent(
    // the modifier here is optional. it will be used whenever we don't pass a modifier to it
    modifier: Modifier = Modifier
){
    // creating a state for the valueState
    val totalBillState = remember {
        mutableStateOf("")
    }

    // creating a state for the slider
    val sliderPositionState = remember {
        mutableFloatStateOf(0f)
    }

    // setting a range for our increment function
    val range = IntRange(start = 1, endInclusive = 100)

    // to increment and decrement the split counter
    val splitByState = remember {
        mutableIntStateOf(1)
    }

    // creating the tip amount state
    val tipAmountState = remember {
        mutableDoubleStateOf(0.0)
    }

    // creating a state for total per person
    val totalPerPersonState = remember {
        mutableDoubleStateOf(0.0)
    }

    Column {
        BillForm(modifier = Modifier,
            range = range,
            totalBillState = totalBillState,
            splitByState = splitByState,
            tipAmountState = tipAmountState,
            totalPerPersonState = totalPerPersonState,
            sliderPositionState = sliderPositionState
        )
    }
}

// you can pass a function as an argument of a composable
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    range: IntRange = 1..100,
    totalBillState: MutableState<String>,
    splitByState: MutableState<Int>,
    tipAmountState : MutableState<Double>,
    totalPerPersonState: MutableState<Double>,
    sliderPositionState: MutableState<Float>,
    onValChange: (String) -> Unit = {}
){
    // this will allow us to control the keyboard or manipulate it
    val keyboardActions = LocalSoftwareKeyboardController.current

    // handling the state of what was inputted
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    // creating a tip percentage
    val tipPercentage = (sliderPositionState.value * 100).toInt()


    // calling the top header
    TopHeader(totalPerPerson = totalPerPersonState.value)

    Surface(modifier = modifier
        .padding(2.dp)
        .border(border = BorderStroke(width = 1.dp, color = Color.LightGray)),
        shape = RoundedCornerShape(corner = CornerSize(15.dp))
    ) {
        Column(modifier = modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            InputField(valueState = totalBillState, labelId = "Enter Bill" , enabled = true, isSingleLine = true, onAction = KeyboardActions{
                if (!validState) return@KeyboardActions
                // we call the function argument we passed
                onValChange(totalBillState.value.trim())
                keyboardActions?.hide() // making sure it's not non-null
            })

            // is there is a number keyed-in on the input text do the following
            Row(modifier = modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start) {
                Text(text = "Split", modifier = modifier.align(
                    alignment = Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(120.dp))
                Row(modifier = modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End) {
                    // Adding the icon for subtract
                    RoundIconButton(imageVector = Icons.Default.Remove, onClick = {
                        if (splitByState.value > 1)
                            splitByState.value --
                        totalPerPersonState.value =
                            calculateTotalPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage,
                                splitBy = splitByState.value )
                    })
                    // Adding a text between the buttons
                    Text(text = splitByState.value.toString(),modifier = modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 9.dp, end = 9.dp))
                    // Adding the icon for add
                    RoundIconButton(imageVector = Icons.Default.Add, onClick = {
                        if(splitByState.value < range.last){
                            splitByState.value++
                            totalPerPersonState.value =
                                calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage,
                                    splitBy = splitByState.value ) }
                    })
                }
            }

            // Adding the tip row
            Row(modifier = modifier.padding(horizontal = 3.dp, vertical = 12.dp)) {
                Text(text = "Tip", modifier = Modifier.align(alignment = Alignment.CenterVertically))
                Spacer(modifier = modifier.width(200.dp))
                Text(text = "$${tipAmountState.value}",modifier = Modifier.align(alignment = Alignment.CenterVertically))
            }

            if(validState){
                // creating a column to store the slider and text
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${tipPercentage}%")
                    Spacer(modifier = modifier.height(14.dp))

                    // creating a slider
                    Slider(value = sliderPositionState.value, onValueChange = {
                            newValue ->
                        sliderPositionState.value = newValue
                        tipAmountState.value = calculateTotalTip(totalBill = totalBillState.value.toDouble(), tipPercentage = tipPercentage)
                        totalPerPersonState.value =
                            calculateTotalPerPerson(
                                totalBill = totalBillState.value.toDouble(),
                                tipPercentage = tipPercentage,
                                splitBy = splitByState.value )
                    },
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                        steps = 5,
                        onValueChangeFinished = { })
                }

            }

        }
    }
}

