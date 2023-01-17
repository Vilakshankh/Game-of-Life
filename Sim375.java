
/******************************************************************************
 *  Compilation:  javac *.java
 *  Execution:    java PictureDemo x y m
 *  Dependencies: Picture.java
 *
 *  Simple demo of how you can use the Picture class for graphical output
 *
 *  In this demo an x-by-y grid of cells will be drawn in random colours, to level of magnification m
 *
 *  m represents the number of screen pixels per cell
 *
 *  DOE221209.1934
 ******************************************************************************/

 import java.awt.*;
 import java.util.Random;
 import javax.swing.*;


 public class Sim375
 {
    private int size = 120;            // x-by-y grid of cell
    private int[][] board, nextBoard;      // intital board
// picture to be drawn on screen
    private JFrame frame; 
            

        //life status variables
    private int alive = 1;
        //private int dead = 0;

    //pattern type
    private char random = 'R';
    private char tumbler = 'T';
    private char loafer = 'R';


    private int iterations;
    private Random lifeStatus = new Random();
    
    // add parameteres: int iterations, char pattern
    public Sim375()
    {
        //this.pattern = pattern;
        //this.iterations = iterations;
        board = new int[size][size];
        nextBoard = new int[size][size];
        frame = new JFrame("Game of Life");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }

     

    /* function: initial board 2d array with random life status of each cell
    *   need to do: need to implement initial board setting with different patterns
    */

    private void rulesOfLife(int[][] board, int[][] nextBoard, int x, int y, int neighbors)
    {
        if      ((board[x][y] == 1) && (neighbors <  2)) nextBoard[x][y] = 0;
        else if ((board[x][y] == 1) && (neighbors >  3)) nextBoard[x][y] = 0;
        else if ((board[x][y] == 0) && (neighbors == 3)) nextBoard[x][y] = 1;
        else nextBoard[x][y] = board[x][y];
    }

    private int[][] InitBoard(int size)
    {   

        for (int x = 0; x < size; x++) 
        {
            for (int y = 0; y < size; y++) 
            {
                board[x][y] = lifeStatus.nextInt(2);
            }
        }
        return board;
    }
    private void NextBoard(int size, int[][] board)
    {
       
        for (int x = 1; x < size-1; x++) {
            for (int y = 1; y < size-1; y++) {
        
        
                int neighbors = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        neighbors += board[x+i][y+j];
                    }
                }
        
                neighbors -= board[x][y];
                rulesOfLife(board, nextBoard, x, y, neighbors);
            }
        }
        
        board = nextBoard;
    }

    private void Patterns(char pattern)
    {
        if (pattern == 'R')
        {
            //InitBoard(pattern, pattern);
        } 
        else if(pattern == 'T')
        {

        }
        else if(pattern == 'L')
        {

        }
    }

    /* life status of specific cell in board checker*/
    private int lifeStatus(int x, int y)
    {
        if (board[x][y] == alive) {return 1;}
        else {return 0;}
    }

    /* colors cell of coordinate xy of board
     * white or black depending on its life status
    */
    private Color colorCell(int x, int y)
    {
        Color CAlive = new Color(0,0,0);
        Color CDead = new Color(255,255,255);
        
    
        // set() colours an individual pixel
        if(lifeStatus(x,y) == 1){return CAlive;}
        else  {return CDead;}
    }

    private void createPixel(int size, JFrame frame)
    {
        for (int x = 0; x < size; x++) 
        {
            for(int y = 0; y < size; y++)
            {
                JPanel pixel = new JPanel();
                //pixel.setSize(100,100);
                pixel.setBackground(colorCell(x,y));
                frame.add(pixel);
            }
        }
    }


     // display (or update) the picture on screen
    public void displayBoard()
    {
        
        frame.setLayout(new GridLayout(size,size));

        for(int c = 0; c <= 500;c++){
            NextBoard(size, InitBoard(size));
            createPixel(size,frame);
        }



        
        frame.setSize(600,600);
        frame.setVisible(true);
    }
 

     // must provide grid size (x & y) and level of magnification (m)
     public static void main(String[] args)
     {
        Sim375 window = new Sim375();
        //System.out.println(window.lifeStatus(100,100)); 
        window.displayBoard();
     }
 }
 