package com.example.lemonade

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.MainActivity.LemonState
import com.example.lemonade.ui.theme.LemonadeTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    enum class LemonState {
        Tree,
        Lemon,
        Lemonade,
        Empty
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LemonadeTheme {
                LemonApp(onLemonClick = {
                    Random.nextFloat() >= 0.2;
                })
            }
        }
    }
}

@Composable
fun LemonApp(onLemonClick: () -> Boolean) {
    var lemonState: LemonState by remember { mutableStateOf(LemonState.Tree) }
    val messageId = when(lemonState) {
        LemonState.Tree -> R.string.tap_tree
        LemonState.Lemon -> R.string.lemon_desc
        LemonState.Lemonade -> R.string.tap_drink
        LemonState.Empty -> R.string.tap_empty
    }
    val drawableId = when(lemonState) {
        LemonState.Tree -> R.drawable.lemon_tree
        LemonState.Lemon -> R.drawable.lemon_squeeze
        LemonState.Lemonade -> R.drawable.lemon_drink
        LemonState.Empty -> R.drawable.lemon_restart
    }
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(stringResource(messageId), fontSize = 20.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(drawableId),
                contentDescription = stringResource(messageId),
                modifier = Modifier.wrapContentSize().clickable {
                    lemonState = when(lemonState) {
                        LemonState.Tree -> LemonState.Lemon
                        LemonState.Lemon -> if (onLemonClick() == true) LemonState.Lemonade else LemonState.Lemon
                        LemonState.Lemonade -> LemonState.Empty
                        LemonState.Empty -> LemonState.Tree
                    }
                                                                },

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonApp(onLemonClick = {
            Random.nextFloat() >= 0.5
    })
    }
}
