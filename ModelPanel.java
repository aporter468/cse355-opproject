import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
/**
 * 
 * @author A Porter
 */
public class ModelPanel extends JPanel  implements Runnable
{
    private int PANEL_WIDTH, PANEL_HEIGHT;

    private static final int FRAME_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int FRAME_WIDTH = FRAME_HEIGHT ;

    public  int nodeCount;

    private static final int DEFAULT_FPS = 1;
    private static final int DEFAULT_PAUSE_TIME = 1000000000; // in ms
    private static final int NO_DELAYS_PER_YIELD = 16;
    /* Number of frames with a delay of 0 ms before the
    animation thread yields to other tunning threads. */
    private static int MAX_FRAME_SKIPS = 5;
    /* Number of frames that can be skipped in any one animation loop
    i.e the game state is updated but not rendered*/
    public Thread animator;

    public long period= 1000000; // in ns
    private long pauseTime; // in ms
    private Graphics2D dbg2;
    private BufferedImage dbImage = null;

    // private int optimizationIndex;
    // network net;
    Game myGame;
    int movementFramesCount;
    int numberOfSteps;
    public volatile boolean running;
    int initHeight;
    int initWidth;
    GUIFrame frame;
    ModelPanel(GUIFrame frame) 
    {
      this.frame= frame;
        running=true;
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        myGame = new Game(this, frame);
        myGame.setPanelSize(getWidth(), getHeight());
        newGame();
    }

    public void newGame()
    {

        running = false;

        myGame = new Game(this, frame);
        myGame.setPanelSize(getWidth(), getHeight());
        running = true;

    }

    /* public void reset()
    {
    this.optimizationIndex=optimizationIndex;
    nodeCount=nodeCountIn;
    running=false;

    //         /if (optimizationIndex==0 || optimizationIndex==1)
    //         {
    //             net=new GirvanNetwork();
    //         }
    //         else if (optimizationIndex==2)
    //         {
    //             lNet=new localOpNet(nodeCount);
    //             lNet.setPanelSize(getWidth(), getHeight());
    //             lNet.setup();
    //         }
    //         else if(optimizationIndex ==3)
    //         {
    //             cNet=new circleNet(nodeCount);
    //             cNet.setPanelSize(getWidth(), getHeight());
    //             cNet.setup();
    //         }
    //startAnimation();
    }*/

    public void setSize(int width, int height)
    {
        super.setSize(width, height);

    }

    public void resizeGame()
    {
        myGame.setPanelSize(getWidth(), getHeight());
        //         if (optimizationIndex==0 || optimizationIndex==1)
        //             net.setPanelSize(getWidth(), getHeight());
        //         else if (optimizationIndex==2)
        //             lNet.setPanelSize(getWidth(), getHeight());
        //         else if (optimizationIndex==3)
        //             cNet.setPanelSize(getWidth(), getHeight());
    }

    public void startAnimation()
    {
        if (animator == null || !animator.isAlive()) {
            animator = new Thread(this);
            animator.setName("Animations Thread");
            animator.start();
            // System.out.println(getWidth() +" "+getHeight());
        }

    }

    private long beforeTime;
    public void run()
    /* Repeatedly: update, render, sleep so loop takes close
    to period nsecs. Sleep inaccuracies are handled.*/
    {

        long afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;
        long excess = 0L;

        beforeTime = System.nanoTime();

        movementFramesCount = 0;
        int index = 0;
        while (true) {
            // System.out.println("in while:"+getWidth()+" "+getHeight());
            //if (optimizationIndex==0 || optimizationIndex==1)
            //     net.getNodeSteps();
            if (index == 100)
            {
                update();
                index = 0;
            }
           
            render();
            paintPanel();

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;  // time left in this loop

            if (sleepTime > 0) {    // some time left in this cycle
                try {
                    Thread.sleep(sleepTime/1000000L);   // nano -> ms
                } catch (InterruptedException ex) { }

                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
            } else {    // sleepTime <= 0; frame took longer than the period
                excess -= sleepTime;    // store excess time value
                overSleepTime = 0L;

                if (++noDelays >= NO_DELAYS_PER_YIELD) {
                    Thread.yield();     // give another thread a chance to run
                    noDelays = 0;
                }
            }

            beforeTime = System.nanoTime();

            /* If frame animation is taking too long, update the game state
            without rendering it, to get the updates/sec nearer to
            the required FPS. */
            int skips = 0;
            while (excess > period && skips < MAX_FRAME_SKIPS) {
                excess -= period;
               // update();   // update state but don't render
                skips++;
            }
            index ++;
        }
    }

    private void update( )
    {
        if(running )
        {
            myGame.move();

        } 
    }

    private void render()
    // draw the current frame to an image buffer
    {
        //System.out.println("(run)"+getHeight() + " "+getWidth());

        if (dbImage == null){//System.out.println(getWidth()+"----"+getHeight());
            dbImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            if (dbImage == null) {
                System.out.println("dbImage is null");
                return;
            }
            else
                dbg2 = dbImage.createGraphics();
        }

        // use anti-aliasing when possible
        dbg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // anti-alias text too
        dbg2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // clear the background
        dbg2.setPaint(Color.BLACK);
        dbg2.fillRect(0, 0, 2*getWidth(), getHeight());
        //  System.out.println(getWidth()+"---"+getHeight());

        /** draw elements -----------------------------
         * Nodes and Lines
         */

        if (running)
            myGame.draw(dbg2);

        // --------------------------------------------
    }

    private void paintPanel()
    {
        Graphics g;
        try {
            g = this.getGraphics();
            if (g != null && dbImage != null)
                g.drawImage(dbImage, 0, 0, null);
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        } catch (Exception e) {
            System.out.println("Graphics context error: " + e);
            System.exit(0);
        }
    }

    public void startLocalOp()
    {
        running=true;
    }

}