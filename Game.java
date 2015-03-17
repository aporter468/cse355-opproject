/**
 * @author A Porter
 *           
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.geom.*;
public class Game
{

    private final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private final int  SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    int panelWidth;
    int panelHeight;
    int squaresWidth = 16;
    int squaresHeight = 16;//sqaures
    int pixelWidth = 500;
    int pixelHeight = 500;
    int boxX = 20;
    int boxY = 20;
    int gapSize = 2;
    ModelPanel tetrisPan;
    GUIFrame tetrisFrame;
    int[] topYRow = new int[squaresWidth];//y coordinates of top row
    boolean[][] grid= new boolean[squaresWidth][squaresHeight];
    public boolean stopDraw;
    private ArrayList<Piece> pieceList = new ArrayList<Piece>();
    public Game( ModelPanel tetrisPan, GUIFrame tetrisFrame)
    {

        this.tetrisPan=tetrisPan;
        this.tetrisFrame=tetrisFrame;
        for (int i =0; i<squaresWidth; i++)
            topYRow[i] = squaresHeight;//bottom of screen; anything can go into first row
        // setup();
    }

    public void setup()
    {

        pieceList.add(new Piece(5, 5, squaresWidth, squaresHeight));

    }

    public void setPanelSize(int x, int y)
    {
        panelWidth = x;
        panelHeight = y;
        if (panelWidth!= SCREEN_WIDTH || panelHeight != SCREEN_HEIGHT)// if has been resized
        {
            setup();
            tetrisPan.startAnimation();

        }

    }

    public void draw(Graphics2D g2)
    {

        boolean repeat = false;
        if (!stopDraw)
        {
            g2.setPaint(Color.BLACK);
            g2.fillRect(0,0,panelWidth, panelHeight);
            g2.setPaint(Color.WHITE);
            g2.drawLine(boxX, boxY, boxX, boxY+pixelHeight);
            g2.drawLine(boxX, boxY+pixelHeight, boxX+pixelWidth, boxY+pixelHeight);
            for (int i =0; i< pieceList.size(); i++)
            {

                int[] x = pieceList.get(i).getX();
                int[] y = pieceList.get(i).getY();
                g2.setPaint(pieceList.get(i).getColor());
                for (int j =0; j<4; j++)//square
                {
                    /*  if (y[j] > squaresHeight)
                    {
                    pieceList.remove(i);
                    repeat = true;
                    }
                    else
                    {*/
                    g2.fillRect(x[j]*pixelWidth/squaresWidth +boxX, y[j]*pixelHeight/squaresHeight+boxY, pixelWidth/squaresWidth-gapSize,pixelHeight/squaresHeight-gapSize);
                    //  }
                }
                if (repeat)
                {
                    i--;
                    repeat = false;
                }
            }
            /* for (int i=0; i<nodeCount; i++)
            {
            if (communitiesAssigned)
            g2.setPaint(getNodeColor(community[i]));
            else
            g2.setPaint(Color.WHITE);
            if (nodesOn)
            g2.fillOval((int)xy[i][0]-5,(int)xy[i][1]-5,10,10);

            for (int j=0; j<nodeCount; j++)//go down nodes column looking for 1s=outlines
            {
            // //System.out.println(status[0][j]);

            if (network[i][j]>0 && connectionsOn  )//existing connection
            {

            g2.setPaint( getColor(i, j, pathWeight[i][j], network[i][j], nodeCount));
            g2.drawLine((int)xy[i][0],(int)xy[i][1], (int)xy[j][0], (int)xy[j][1]);
            }
            else if (network[i][j]<0 && connectionsOn)//former connection
            {
            g2.setPaint(new Color(255,255,255,50));
            g2.drawLine((int)xy[i][0],(int)xy[i][1], (int)xy[j][0], (int)xy[j][1]);
            }

            }
            g2.setPaint(Color.WHITE);
            if(labelsOn)
            g2.drawString(names[i]+"", (int)xy[i][0], (int)xy[i][1]+20);
            }
            }*/
            // else
            // {
            g2.setPaint(Color.WHITE);

        }
    }

    public void move()
    {

        for (int i =0; i< pieceList.size(); i++)
        {

            int bottomY =  pieceList.get(i).getBottomY();
            int[] x = pieceList.get(i).getX();
            int[] y = pieceList.get(i).getY();
            boolean canGoDown = true;
            //determine canGoDown
            for (int k = 0; k< 4; k++)
            {
                if (y[k] >= topYRow[x[k]]-1 || topYRow[x[k]] == 0)
                {
                    canGoDown = false;
                }
            }
            if (bottomY< squaresHeight && canGoDown)
            {
               grid = pieceList.get(i).shiftDown(grid);
            }
            else
            {
                pieceList.get(i).setSettled(true);

                for (int k = 0; k<4; k++)
                {
                    if (y[k] < topYRow[x[k]])//above top row
                    {
                        topYRow[x[k]] = y[k];
                    }
                }

            }

        }
        if (pieceList.get(pieceList.size()-1).isSettled())//if last is done, new piece
        {
            pieceList.add(new Piece((int)(Math.random()*7), (int)(Math.random()*(squaresWidth-3)), squaresWidth, squaresHeight));
        }
        for (int i = 0; i<squaresHeight; i++)
        {
            boolean rowDone = true;
            for (int j = 0; j<squaresWidth; j++)
            {
                if (grid[j][i] ==false)
                {
                    rowDone = false;
                    break;
                }
            }
            if (rowDone == true)
                System.out.println("row: "+i);
        }
        
        /* if (!stopDraw)
        {
        double kineticE=0.0;
        double[] force =new double[2];
        double[] nodeVelocity =new double[2];

        double timestep=0.1;
        double damping=.01;

        // double eMin
        //  while (kineticE> eMin)
        // {

        for (int i=0; i<nodeCount; i++)
        {

        force[0]=0;
        force[1]=0;
        nodeVelocity[0]=0;
        nodeVelocity[1]=0;
        for (int j=0; j<nodeCount; j++)
        {
        if (i!=j)
        {
        // System.out.println(getDistance(i,j));
        force[0] -=ck*weights[i]*weights[j]/getXDistance(i,j);
        force[1] -=ck*weights[i]*weights[j]/getYDistance(i,j);

        if (network[i][j]>0)//existing connection
        {

        force[0] +=hk*getXDistance(i,j);
        force[1] += hk*getYDistance(i,j);
        }
        }

        }

        force[0] += ck*weights[i]*weights[i]/(xy[i][0]-panelWidth);
        force[1] += ck*weights[i]*weights[i]/(xy[i][1]-panelHeight);

        force[0] += ck*weights[i]*weights[i]/(xy[i][0]);
        force[1] += ck*weights[i]*weights[i]/(xy[i][1]);

        nodeVelocity[0]=(nodeVelocity[0]+timestep*force[0])*damping;
        nodeVelocity[1]=(nodeVelocity[1]+timestep*force[1])*damping;
        // System.out.println(i+" "+force[0]+ " "+force[1]);
        //if (xy[i][0]+timestep*nodeVelocity[0]<panelWidth-100 && xy[i][0]+timestep*nodeVelocity[0]>0)
        xy[i][0]+=timestep*nodeVelocity[0];
        // if (xy[i][1]+timestep*nodeVelocity[1]<panelHeight-100 && xy[i][1]+timestep*nodeVelocity[1]>0)
        xy[i][1]+=timestep*nodeVelocity[1];
        // kineticE+=weights[i]*(nodeVelocity[1]*nodeVelocity[1] + nodeVelocity[0]*nodeVelocity[0]);
        //System.out.println(kineticE);
        }
        }*/

    }

    public void moveCurrentLeft()
    {
        if (!pieceList.get(pieceList.size()-1).isSettled())
         grid =   pieceList.get(pieceList.size()-1).shiftLeft(grid);

    }

    public void moveCurrentRight()
    {
        if (!pieceList.get(pieceList.size()-1).isSettled())
          grid =  pieceList.get(pieceList.size()-1).shiftRight(grid);
    }

    public void pushDown()
    {
        move();
    }
    public void rotateClockwise()
    {
        if (!pieceList.get(pieceList.size()-1).isSettled())
           grid = pieceList.get(pieceList.size()-1).rotateClockwise(grid);
    }
}