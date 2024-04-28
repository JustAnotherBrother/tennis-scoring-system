# Tennis Scoring System React

This app allows us to call our rest api that is at this url **http://localhost:8080/api/v1/games**

The rest api is a java spring application.

There are 2 main functions we call :
1) /initialise => allows us to reset the counter for both players to zero
2) /update/player/points?playerName= => which takes A or B in order to update the points of the players

When in deuce mode, where both players have 40 points, the column of points will be highlighted in red. 
