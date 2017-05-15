/*
Kevin Dang  		- n01051682
Vishwas Malhotra 	- n01055353
Jidesh
*/

var grid = [
	[0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0],
	[0,0,0,0,0,0,0]
];
var ship1 = Math.floor(Math.random()*4); //horizontal, size: 4
var shipX;
var ship2 = Math.floor(Math.random()*5); //vertical, size: 3
var shipY;

createShips();
displayGrid();

var b1 = document.getElementById("fireButton");
b1.onclick = fireMissile;

function createShips()
{	
	//horizontal ship placement
	var x = Math.floor(Math.random()*7);
	shipX = x;
	grid[x][ship1] = 1;
	grid[x][ship1+1] = 1;
	grid[x][ship1+2] = 1;
	grid[x][ship1+3] = 1;
	
	//vertical ship placement
	var exit = true;
	while(exit)
	{
		var y = Math.floor(Math.random()*7);
		shipY = y;
		if(grid[ship2][y] == 1 || grid[ship2+1][y] == 1 || grid[ship2+2][y] == 1)
		{
			exit = true;
		}
		else
		{
			grid[ship2][y] = 1;
			grid[ship2+1][y] = 1;
			grid[ship2+2][y] = 1;
			exit = false;
		}
	}

}

function fireMissile() //typing in textbox will create value
{
	//get the coordinates
	var textbox = document.getElementById("guessInput");
	var coordinates = textbox.value;
	console.log(coordinates);
	
	var row = coordinates.substr(0,1);  //0=index, 1=character number
	var col = coordinates.substr(1,1);
	console.log("row: " + row + ", col: " + col);
	
	if(coordinates.length == 2 && col < grid.length)   //error check the coordinate entered
	{
		//find ships
		switch(row)
		{
			case "a":	//A, ID = 0-6
			case "A":
				check(coordinates, row, col, "0");
				break;
			case "b":	//B, ID = 10-16
			case "B":
				check(coordinates, row, col, "1");
				break;
			case "c":	//C, ID = 20-26
			case "C":
				check(coordinates, row, col, "2");
				break;
			case "d":	//D, ID = 30-36
			case "D":
				check(coordinates, row, col, "3");
				break;
			case "e":	//E, ID = 40-46
			case "E":
				check(coordinates, row, col, "4");
				break;
			case "f":	//F, ID = 50-56
			case "F":
				check(coordinates, row, col, "5");
				break;
			case "g":	//G, ID = 60-66
			case "G":
				check(coordinates, row, col, "6");
				break;
			default:
				alert("invalid coordinate, try again");
		}
	}
	else
		alert("invalid coordinate, try again");
}

function missileHit(coordinates, row, beginID)
{
	var id = coordinates.replace(row, beginID);  //replace the row letter with matching row number to get td ID 
	var hit = document.getElementById(id);
	hit.setAttribute("class", "hit");
}

function missileMiss(coordinates, row, beginID)
{
	var id = coordinates.replace(row, beginID);
	var hit = document.getElementById(id);
	hit.setAttribute("class", "miss");
}

function check(coordinates, rowLetter, col, rowNum)
{
	if(grid[rowNum][col] == 1)   //1 shows where ship is
		{
				grid[rowNum][col] = 2;		//2 shows spots you hit
				missileHit(coordinates, rowLetter, rowNum);
				sunk(ship1, ship2, shipX, shipY);
				console.table(grid);
		}
		else if(grid[rowNum][col] == 0)  //0 shows misses, where there is no ship
		{
				grid[rowNum][col] = 2;
				missileMiss(coordinates, rowLetter, rowNum);
				console.table(grid);
		}
		else
			alert("You already hit this spot, try again");
}

function sunk(s1, s2, sX, sY)
{
	if(grid[sX][s1] == 2 && grid[sX][s1+1] == 2 && grid[sX][s1+2] == 2 && grid[sX][s1+3] == 2) //check if horizontal ship was hit
	{
		alert("You have sunk a battleships");
		//horizontal ship
		grid[sX][s1] = 3;		//make the ship 3 to show it sunk, if ship hit
		grid[sX][s1+1] = 3;
		grid[sX][s1+2] = 3;
		grid[sX][s1+3] = 3;
		if(grid[s2][sY] == 3 && grid[s2+1][sY] == 3 && grid[s2+2][sY] == 3) //check if vertical ship sunk
		{
			alert("You have sunk all battleships, You Won!!!!!!!!, refresh to play again");
		}
	}
	else if(grid[s2][sY] == 2 && grid[s2+1][sY] == 2 && grid[s2+2][sY] == 2)
	{
		alert("You have sunk a battleships");
		//vertical ship
		grid[s2][sY] = 3;
		grid[s2+1][sY] = 3;
		grid[s2+2][sY] = 3;
		if(grid[sX][s1] == 3 && grid[sX][s1+1] == 3 && grid[sX][s1+2] == 3 && grid[sX][s1+3] == 3)
		{
			alert("You have sunk all battleships, You Won!!!!!!!!, refresh to play again");
		}
	}	
}

function displayGrid()
{
	console.table(grid);
}
