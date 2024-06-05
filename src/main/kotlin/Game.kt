class Game 
{
    private val player1:Player = Player("Player 1", "X")
    private val player2:Player = Player("Player 2", "O")
    
    private var currentPlayer : Player = player1
    
    fun getCurrentPlayer(): Player = currentPlayer
    
    fun changePlayer()
    {
        if(getCurrentPlayer() == player1) {
            currentPlayer = player2
            return
        }
        currentPlayer = player1
    }
    
}