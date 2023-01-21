
import java.awt.*;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


 public class Sim375
 {
    private int size = 120;                                     //size of grid
    //private  int iterations;
    //public char pattern;
    

    private int[][] board;                                      //initialize boards
    private int[][] tempBoard;
    

    private JFrame frame;                                       //GUI elements


    private int alive = 1;                                      //initialize life status variables
    private Random alive_dead = new Random();

    private int [] iterationCount = {0};                        //time between iteration variables
    static int delay = 10;




    
    
    public Sim375()
    {
        //this.pattern = pattern;

        board = new int[size][size];
        tempBoard = new int[size][size];

        frame = new JFrame("Game of Life");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setLayout(new GridLayout(size,size));


        InitBoard(size, 'R');
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
                    if (getLifeStatus(x_coord, y_coord)==1){aliveNeighbors++;}
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
                if      ((board[x][y] == 1) && (aliveNeighbors <  2)) 
                        tempBoard[x][y] = 0;
                else if ((board[x][y] == 1) && (aliveNeighbors >  3)) 
                        tempBoard[x][y] = 0;
                else if ((board[x][y] == 0) && (aliveNeighbors == 3)) 
                        tempBoard[x][y] = 1;
                else tempBoard[x][y] = board[x][y];

            }
        }

        for(int i = 0; i < size; i++) {System.arraycopy(tempBoard[i], 0, board[i], 0, size);}
        
    }


    

    public void displayBoard()
    {
        
       
        for (int x = 0; x < size; x++) 
        {
            for(int y = 0; y < size; y++)
            {
                JPanel pixel = new JPanel();
                
                pixel.setBackground(getCellColor(x,y));
                pixel.setSize(10,10);
                frame.add(pixel);

            }
        }
        frame.setSize(700,700);
        frame.setVisible(true);
    }


    public void startAnimation(int iterations)
    {
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (iterationCount[0] < iterations) {
                    update();
                    frame.getContentPane().removeAll();
                    displayBoard();
                    iterationCount[0]++;
                } else {

                    ((Timer) e.getSource()).stop();

                }
            }
        });
        timer.start();
    }
 

     public static void main(String[] args)
     {
        /*
         * int iterations = Integer.parseInt(args[0]);
        String inputPattern = args[1];
        
        char pattern = inputPattern.charAt(0);
        */

        Sim375 window = new Sim375();
        window.startAnimation(500);
     }
 }
 