
/**
 * Write a description of class Piece here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.*;

public class Piece
{
    private int type;
    private int[] x;
    private int[] y;
    private int numSquares = 4;
    private int boardW;
    private int boardH;
    private boolean settled;
    private int rotation;
   
    public Piece(int type, int x1, int boardWidth, int boardHeight)
    {
        this.type = type;
        boardW = boardWidth;
        boardH = boardHeight;
        rotation = 0;
        settled = false;
        x = new int[4];
        y = new int[4];

        if (type == 0)//rectangle
        {
            for (int i =0; i<4; i++)
            {
                x[i] = x1+i;
                y[i] = 0;
            }
        }
        if (type == 1)//L backward
        {
            for (int i =0; i<3; i++)
            {
                x[i] = x1+i;
                y[i] = 1;
            }
            x[3] =x1+ 0;
            y[3] = 0;

        }
        if (type == 2)//L
        {
            for (int i =0; i<3; i++)
            {
                x[i] = x1+i;
                y[i] = 1;
            }
            x[3] = x1+2;
            y[3] = 0;

        }

        if (type == 3)//sqaure
        {
            x[0] = 0 + x1;
            x[1] = 1+ x1;
            x[2] = 0+ x1; 
            x[3] = 1+ x1;

            y[0] = 0;
            y[1] = 0;
            y[2] = 1;
            y[3] = 1;

        }
        if (type == 4)//S
        {

            x[0] = 1+ x1;
            x[1] = 2+ x1;
            x[2] = 0+ x1; 
            x[3] = 1+ x1;

            y[0] = 0;
            y[1] = 0;
            y[2] = 1;
            y[3] = 1;

        }
        if (type == 5)//Z
        {

            x[0] = 0+ x1;
            x[1] = 1+ x1;
            x[2] = 1+ x1; 
            x[3] = 2+ x1;

            y[0] = 0;
            y[1] = 0;
            y[2] = 1;
            y[3] = 1;

        }

        if (type == 6)//other = |-
        {
            for (int i =0; i<3; i++)
            {
                x[i] = i+ x1;
                y[i] = 1;
            }
            x[3] = 1+ x1;
            y[3] = 0;

        }

    }

    public boolean[][] shiftRight( boolean[][] grid)
    {
        for (int i =0; i<4; i++)
        {
            grid[x[i]][y[i]] = false;
        }
        boolean possible = true;
        for (int i = 0; i<numSquares; i++)
        {
            x[i] ++;
            if (x[i] >= boardW || grid[x[i]][y[i]] == true)
            {
                possible = false;
            }
        }
        if (!possible)
        {
            for (int i = 0; i<numSquares; i++)
            {
                x[i] --;
            }
        }
        for (int i =0; i<4; i++)
        {
            grid[x[i]][y[i]] = true;
        }
        return grid;
    }

    public boolean[][] shiftLeft(boolean[][] grid)
    {
        for (int i =0; i<4; i++)
        {
            grid[x[i]][y[i]] = false;
        }
        boolean possible = true;
        for (int i = 0; i<numSquares; i++)
        {
            x[i] --;
            if (x[i] <0 || grid[x[i]][y[i]] == true)
            {
                possible = false;
            }
        }
        if (!possible)
        {

            for (int i = 0; i<numSquares; i++)
            {
                x[i] ++;
            }
        }
        for (int i =0; i<4; i++)
        {
            grid[x[i]][y[i]] = true;
        }
        return grid;
    }

    public boolean[][] shiftDown(boolean[][] grid)
    {
        for (int i =0; i<4; i++)
        {
            grid[x[i]][y[i]] = false;
        }
        for (int i =0; i<numSquares; i++)
        {
            y[i] ++;
        }
        for (int i =0; i<4; i++)
        {
            grid[x[i]][y[i]] = true;
        }
        return grid;
    }

    public Color getColor()
    {
        switch(type)
        {
            case 0: return Color.CYAN;
            case 1:  return Color.BLUE;
            case 2:  return Color.ORANGE;
            case 3:   return Color.YELLOW;
            case 4: return Color.GREEN;
            case 5: return Color.RED;
            default: return Color.MAGENTA;
        }
    }

    public boolean[][] rotateClockwise(boolean[][] grid)
    {
        if (type != 3)
        {
        for (int i =0; i<4; i++)
        {
            grid[x[i]][y[i]] = false;
        }
        int[] diffX = new int[4];
        int[] diffY = new int[4];
        int centerX = x[1];
        int centerY = y[1];
        for (int i =0; i<4; i++)
        {
            diffX[i] = x[i] - centerX;
            diffY[i] = y[i] - centerY;
        }
        
       int[] tempx = new int[4];
       
       for (int i =0; i<4; i++)
       {
           tempx[i] = diffX[i];
           diffX[i] = diffY[i];
           diffY[i] = -1*tempx[i];
        }
        for (int i = 0; i<4; i++)
        {
            x[i] = diffX[i]+ centerX;
            y[i] = diffY[i]+centerY;
        }

        for (int i =0; i<4; i++)
        {
            grid[x[i]][y[i]] = true;
        }
    }
        return grid;
    
    }

    public void rotateCounterClockwise()
    {
    }

    public int getBottomY()
    {
        switch(type)
        {
            case 0: return y[0];
            case 1:  return y[0];
            case 2:  return y[0];
            case 3:   return y[3];
            case 4: return y[3];
            case 5: return y[3];
            default: return y[0];
        }

    }

    public int getType() {return type;}

    public int[] getX(){return x;}

    public int[] getY(){return y;}

    public boolean isSettled(){ return settled;}

    public void setSettled(boolean in) { settled = in; }

    
}
