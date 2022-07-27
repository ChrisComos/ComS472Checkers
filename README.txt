To Compile my work
	Used IDE Eclipse.
To play, just run the checkers.java file as a java application
The board will be printed to the console

It will prompt a user input.

THIS GAME ASSUMES THE USER KNOWS THE RULES AND FOLLOWS THEM
THIS GAME ASSUMES THE USER KNOWS THE RULES AND FOLLOWS THEM
THIS GAME ASSUMES THE USER KNOWS THE RULES AND FOLLOWS THEM
THIS GAME ASSUMES THE USER KNOWS THE RULES AND FOLLOWS THEM
(Just wanted to make that clear)
I figured that is an acceptable assumtion to make

The board is an 8x8 grid with 0,0 in the top left
The move that the bot makes will be printed above the board
there are lines to separate the boards and other printed items
When the game is over, it will print END GAME

The code to run a second bot is not working in this file. My computer crashed from overheating and did not save and I was not able to re make the files in time to get it working
I ran it multiple times before and it worked fine


To move a piece use
	'Move i,j to k,l'				(to move normally from position i,j to k,k)
	'Jump i,j to k,l'				(to jump to position k,l from i,j and removes piece in between the two locations)
	'Jump i,j to k,l to m,n'			(to multijump, for each postion to jump to add 'to o,p' to jump to position o,p)


EXAMPLE
Move 2,3 to 3,2 will result in
_m_m_m_m
m_m_m_m_
_m_o_m_m
o_m_o_o_
_o_o_o_o
w_w_w_w_
_w_w_w_w
w_w_w_w_