# PigGame
Backend for two-player Pig Game | Spring, Web, WebSockets

**This game is live and can be played [here](https://rgbk21.github.io/Pig_Game/index.html).**

## How to play
Game rules and instructions can be read up on wiki [here](https://en.wikipedia.org/wiki/Pig_(dice_game)). 
The objective of the game is to be the first one to reach the target score.
Each turn, a player repeatedly rolls a die until either a 1 is rolled or the player decides to "hold":

* If the player rolls a 1, they score nothing and it becomes the next player's turn.
* If the player rolls any other number, it is added to their turn total and the player's turn continues.
* If a player chooses to "hold", their turn total is added to their score, and it becomes the next player's turn.
