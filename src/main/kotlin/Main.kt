import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

var gameActive:Boolean by mutableStateOf(false)


@Composable
@Preview
fun App() {
    val game:Game = remember { Game() }
    var buttonStates = mutableStateListOf(mutableStateListOf("", "", ""), mutableStateListOf("", "", ""), mutableStateListOf("", "", ""))
    
    Button(onClick = {
        buttonStates.clear()
        buttonStates = mutableStateListOf(mutableStateListOf("", "", ""), mutableStateListOf("", "", ""), mutableStateListOf("", "", ""))
        gameActive = true
    }) {
        Text("Start New Game")
    }   
    
    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxSize().padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0..2) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(100.dp)
                ) {
                    for (j in 0..2) {
                        Button(onClick = {
                            if(buttonStates[i][j] != "" || !gameActive)
                            {
                                return@Button
                            }
                            
                            buttonStates[i][j] = game.getCurrentPlayer().getSign()
                            if(checkBoard(game, buttonStates))
                            {
                                //TODO Display some cool text, add button to restart the game
                                println("${game.getCurrentPlayer().getName()} Won!")
                            }
                            
                            game.changePlayer()
                        }) {
                            Text(buttonStates[i][j])
                        }
                    }
                }
            }
        }
    
    }
}

@Composable
fun PrintToScreen(text: String) {
    Text(text);
}

fun checkBoard(game:Game, buttonStates: SnapshotStateList<SnapshotStateList<String>>): Boolean{
    val player = game.getCurrentPlayer()

    var freeSpaces = 0
    
    for (i in 0 .. 2) {
        for (j in 0 .. 2) {
            if(buttonStates[i][j] == ""){
                freeSpaces++
            }
        }
    }
    
    if(freeSpaces == 0)
    {
        return gameOver()
    }
    
    var horizontalSign:String
    var verticalSign:String
    
    var horizontalCount = 0
    var verticalCount = 0
    
    // vertical and horizontal check
    for (i in 0 .. 2) {
        for (j in 0 .. 2) {
            verticalSign = buttonStates[i][j]
            horizontalSign = buttonStates[j][i]
            
            if(verticalSign == player.getSign()){
                verticalCount++
            }
            if(horizontalSign == player.getSign()){
                horizontalCount++
            }
        }
        
        if(horizontalCount == 3 || verticalCount == 3){
            gameActive = false
            return true
        } else {
            horizontalCount = 0
            verticalCount = 0
        }
    }

    var mainDiagonalSign:String
    var secondDiagonalSign:String

    var mainDiagonalCount = 0
    var secondDiagonalCount = 0
    
    // diagonal check
    for (i in 0 .. 2) {
        mainDiagonalSign = buttonStates[i][i]
        secondDiagonalSign = buttonStates[i][2 - i]
        
        if(mainDiagonalSign == player.getSign()){
            mainDiagonalCount++
        }
        if(secondDiagonalSign == player.getSign()){
            secondDiagonalCount++
        }
    }
    
    if(mainDiagonalCount == 3 || secondDiagonalCount == 3){
        gameActive = false
        return true
    }
    
    return false
}

fun gameOver(): Boolean
{
    gameActive = false
    //TODO Display some cool text, add button to restart the game
    println("Game over!")
    return false
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
