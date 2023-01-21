
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


 public class Sim375
 {
    private static int size = 120;           
    private static int[][] board;      
    private static int[][] tempBoard;  
    
    private static int alive = 1;
    private static int dead = 0;
    static final int [] iterationCount = {0};
    static int delay = 250;

    private static Random alive_dead = new Random();
    
    // add parameteres: int iterations, char pattern
    public Sim375()
    {
        //this.pattern = pattern;
        //Sthis.iterations = iterations;
        board = new int[size][size];
        tempBoard = new int[size][size];




        InitBoard(size, 'L');
        
    }

     
    private static void InitBoard(int size, char pattern)
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

    private static int getNeighbors(int x, int y)
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
                    if (getLifeStatus(x_coord, y_coord)==1){aliveNeighbors++;}
                }
            }
        }
        return aliveNeighbors;
    }

    private static int getLifeStatus(int x, int y)
    {
        if (board[x][y] == alive) {return 1;}
        else {return 0;}
    }

    private static Color getCellColor(int x, int y)
    {
        Color CAlive = new Color(0,0,0);
        Color CDead = new Color(255,255,255);
        
    
        // set() colours an individual pixel
        if(getLifeStatus(x,y) == 1){return CAlive;}
        else  {return CDead;}
    }


    private static void update()
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


    



    

     // must provide grid size (x & y) and level of magnification (m)
     public static void main(String[] args)
     {
        //Sim375 window = new Sim375();
        int size = 120;
        //System.out.println(window.lifeStatus(100,100)); 
        //window.displayBoard();
        
        JFrame frame = new JFrame("Game of Life");
        frame.setLayout(new GridLayout(size,size));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 


        JPanel pixel = new JPanel()
        {
            @Override
            public void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                for (int i = 0; i < size; i++) 
                {
                    for (int j = 0; j < size; j++) 
                    {
                        if(getLifeStatus(i,j) == 1)
                        {
                            g.setColor(Color.BLACK);
                        }
                        else
                        {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(i * 4, j * 4, 4, 4);
                    }
                }
            }
        };
        
        pixel.setPreferredSize(new Dimension(size * 4, size * 4));
        frame.add(pixel);
        frame.pack();
        frame.setVisible(true);







                        // start animation
        int iterations = 0;
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iterationCount[0] < iterations) {
                    update();
                    
                    pixel.repaint();
                    iterationCount[0]++;
                } else {

                    ((Timer) e.getSource()).stop();

                }
            }
        });
        timer.start();
    



     }
 }
 