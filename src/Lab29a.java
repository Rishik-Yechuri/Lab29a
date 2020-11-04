// Lab29ast.java
// This is the student version of the Lab29a assignment.
// Completing this file, as is, is the 100 point version.
// For 80 points you will be given the <getMove> code.


import java.util.*;


public class Lab29a {
    public static void main(String args[]) {
        System.out.println("\nLab 29a 80/100 Point Version\n");
        Scanner input = new Scanner(System.in);
        System.out.print("Enter random starting seed  ===>>  ");
        int seed = input.nextInt();

        Maze maze = new Maze(seed);
        maze.displayMaze();
        maze.solveMaze();
        maze.displayMaze();
        maze.mazeSolution();
    }
}


class Maze {

    char[][] mat;                // 2d character array that stores the maze display
    private Coord currentMove;        // object that stores current maze position
    private Stack visitStack;
    private boolean searchingDone = false;
    // stack that stores location that have been visited


    class Coord
            // Coord is a class that stores a single maze location.
    {
        private int rPos;
        private int cPos;

        public Coord(int r, int c) {
            rPos = r;
            cPos = c;
        }

        public boolean isFree() {
            return (rPos == 0 && cPos == 0);
        }

        public void setPos(int r, int c) {
            rPos += r;
            cPos += c;
        }
    }


    public Maze(int seed)
    // constructor which generates the random maze, random starting location
    // and initializes Maze class values.  If the random value equals 0 the maze
    // store an 'X' otherwise it store an 'O' in the maze.
    {
        Random random = new Random(seed);
        int startRow, startCol;
        mat = new char[12][12];
        for (int r = 0; r < 12; r++)
            for (int c = 0; c < 12; c++) {
                if (r == 0 || c == 0 || r == 11 || c == 11)
                    mat[r][c] = 'X';
                else {
                    int rndInt = random.nextInt(2);
                    if (rndInt == 0)
                        mat[r][c] = 'X';
                    else
                        mat[r][c] = 'O';
                }
            }
        mat[0][0] = 'O';
        startRow = random.nextInt(12);
        startCol = 11;
        mat[startRow][startCol] = '.';
        visitStack = new Stack();
        currentMove = new Coord(startRow, startCol);
        visitStack.push(currentMove);
    }


    void displayMaze()
    // displays the current maze configuration
    {
        System.out.println("\nRANDOM MAZE DISPLAY\n");
        for (int r = 0; r < 12; r++) {
            for (int c = 0; c < 12; c++)
                System.out.print(mat[r][c] + "  ");
            System.out.println();
        }
        System.out.println();
        pause();
    }


    public void solveMaze()
    // This methods solves the maze with private helper method <getMove>.
    // A loop is needed to repeat getting new moves until either a maze solution
    // is found or it is determined that there is no way out off the maze.
    {
        System.out.println("\n>>>>>   WORKING  ....  SOLVING MAZE   <<<<<\n");
        while (!searchingDone) {
            mat[currentMove.rPos][currentMove.cPos] = '.';
            if (currentMove.rPos == 0 && currentMove.cPos == 0) {
                searchingDone = true;
            } else if (!getMove()) {
                goBackAlgorithim();
            } else {
                /*Coord newCurrentMove*/
                currentMove = new Coord(currentMove.rPos, currentMove.cPos);
                visitStack.push(currentMove);
            }
        }
    }


    public void mazeSolution()
    // Short method to display the result of the maze solution
    {
        if (currentMove.isFree())
            System.out.println("\nTHE MAZE HAS A SOLUTION.\n");
        else
            System.out.println("\nTHE MAZE HAS NO SOLUTION.\n");
    }

    public void goBackAlgorithim() {
        //Do things that make it go back
        boolean keepGoingBack = true;
        while (keepGoingBack) {
            if (!visitStack.isEmpty()) {
                visitStack.pop();
                if (!visitStack.isEmpty()) {
                    currentMove = (Coord) visitStack.peek();
                }
            } else {
                searchingDone = true;
                keepGoingBack = false;
            }
            if (getMove()) {
                keepGoingBack = false;
            }
        }
        if (!visitStack.isEmpty()) {
            currentMove = (Coord) visitStack.peek();
        }
    }

    private boolean inBounds(int r, int c)
    // This method determines if a coordinate position is inbounds or not
    {
        if (r >= 0 && r < 12 && c >= 0 && c < 12) {
            return true;
        }
        return false;
    }


    private boolean getMove()
    // This method checks eight possible positions in a counter-clock wise manner
    // starting with the (-1,0) position.  If a position is found the method returns
    // true and the currentMove coordinates are altered to the new position
    {
        int rMovement = 0;
        int cMovement = 0;
        int a = 0;
        boolean keepLoopRunning = true;
        boolean foundSomething = false;
        int[][] holdPossibleMoves = new int[][]{{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};
        while (keepLoopRunning) {
            rMovement = holdPossibleMoves[a][0];
            cMovement = holdPossibleMoves[a][1];
            if (inBounds(currentMove.rPos + rMovement, currentMove.cPos + cMovement) && mat[currentMove.rPos + rMovement][currentMove.cPos + cMovement] == 'O') {
                currentMove.rPos += rMovement;
                currentMove.cPos += cMovement;
                return true;
            }
            if (a >= 7) {
                keepLoopRunning = false;
            }
            a++;
        }
        return false;
    }

    private void pause() {
        Scanner input = new Scanner(System.in);
        String dummy;
        System.out.print("\nPress <Enter> to continue  ===>>  ");
        dummy = input.nextLine();
    }


}
