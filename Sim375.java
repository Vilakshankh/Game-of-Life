
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
import java.util.stream.IntStream;

import javax.swing.*;


 public class Sim375
 {
    private int size = 120;           
    private int[][] board;      
    private int[][] tempBoard;  
    private JFrame frame; 
    private int alive = 1;
    private int dead = 0;
    private int iterations;
    private Random alive_dead = new Random();
    
    // add parameteres: int iterations, char pattern
     public Sim375()
     {
        //this.pattern = pattern;
        //this.iterations = iterations;
        board = new int[size][size];
        tempBoard = new int[size][size];
        frame = new JFrame("Game of Life");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
     }
    
     
    private void InitBoard(int size, char pattern)
    {   
        if (pattern == 'R')
        {
            for (int x = 0; x < size; x++) 
            {
                for (int y = 0; y < size; y++) {board[x][y] = alive_dead.nextInt(2);}
            } 
        }
    
        if (pattern == 'T')
        {
            for (int x = 0; x < size; x++) 
            {
                for (int y = 0; y < size; y++) {board[x][y] = 0;}
            }
            
            board[1][2] = 1;
            board[1][8] = 1;

            board[2][1] = 1;
            board[2][3] = 1;
            board[2][7] = 1;
            board[2][9] = 1;

            board[3][1] = 1;
            board[3][4] = 1;
            board[3][6] = 1;
            board[3][9] = 1;
            
            board[4][3] = 1;
            board[4][7] = 1;

            board[5][3] = 1;
            board[5][4] = 1;
            board[5][6] = 1;
            board[5][7] = 1;
        }

        if (pattern == 'L')
        {
            for (int x = 0; x < size; x++) 
            {
                for (int y = 0; y < size; y++) {board[x][y] = 0;}
            }

            board[1][2] = 1;
            board[1][3] = 1;
            board[1][6] = 1;
            board[1][8] = 1;
            board[1][9] = 1;

            board[2][1] = 1;
            board[2][4] = 1;
            board[2][7] = 1;
            board[2][8] = 1;

            board[3][2] = 1;
            board[3][4] = 1;

            board[4][3] = 1;

            board[5][9] = 1;

            board[6][7] = 1;
            board[6][8] = 1;
            board[6][9] = 1;

            board[7][6] = 1;

            board[8][7] = 1;

            board[9][8] = 1;
            board[9][9] = 1;
        }

    }

    private int getNeighbors(int x, int y)
    {
        int aliveNeighbors = 0;
        for (int i = -1; i<=1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                if (!(i == 0 && j == 0))
                {
                    int x_coord = (x + i + size) % size;
                    int y_coord = (y + j + size) % size;
                    if (getLifeStatus(x, y)){aliveNeighbors++;}
                }
            }
        }
        return aliveNeighbors;
    }

    private int getLifeStatus(int x, int y)
    {
        if (board[x][y] == alive) {return 1;}
        else {return 0;}
    }

    private Color getCellColor(int x, int y)
    {
        Color CAlive = new Color(0,0,0);
        Color CDead = new Color(255,255,255);
        
    
        // set() colours an individual pixel
        if(getLifeStatus(x,y) == 1){return CAlive;}
        else  {return CDead;}
    }


    private void update()
    {
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                int aliveNeighbors = getNeighbors(x, y);
                if (board[x][y] == alive)
                {
                    if(aliveNeighbors < 2 || aliveNeighbors > 3){ tempBoard[x][y] = dead;}
                    else{tempBoard[x][y] = alive;}
                }
                else
                {
                    if (aliveNeighbors == 3) {tempBoard[x][y] = alive;}
                    else{tempBoard[x][y] = dead;}
                }
            }
        }

        for(int i = 0; i < size; i++) {System.arraycopy(tempBoard[i], 0, board[i], 0, size);}
    }


    

    public void displayBoard()
    {
        InitBoard(size, 'L');
        frame.setLayout(new GridLayout(size,size));
        for (int x = 0; x < size; x++) 
        {
            for(int y = 0; y < size; y++)
            {
                JPanel pixel = new JPanel();
                //pixel.setSize(100,100);
                pixel.setBackground(getCellColor(x,y));
                frame.add(pixel);
            }
            
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
 