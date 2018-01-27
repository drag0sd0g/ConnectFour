Basic Connect4 game (https://en.wikipedia.org/wiki/Connect_Four)

1. Clone the project
2. Open a cmd line in the project root folder
3. run "gradle --console=plain clean build run"

The project will build and then each player (RED/GREEN) will be prompted for column numbers where they would want their
respective discs inserted until a winner has been established.

Insert a column number for the current player when prompted and press enter. You will see an updated board

If you make an invalid input, you will be given a second chance to input a correct value

Game stops when a winner has been found or when the game ended in a draw