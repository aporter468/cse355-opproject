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
public class ModelPanel extends JPanel 
{
    private int PANEL_WIDTH, PANEL_HEIGHT;

    private static final int FRAME_HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final int FRAME_WIDTH = FRAME_HEIGHT ;

   public  int nodeCount;
   private Graphics2D dbg2;
    private BufferedImage dbImage = null;

    int initHeight;
    int initWidth;
    GUIFrame frame;
    ModelPanel(GUIFrame frame)    {
        this.frame= frame;
         update();
            render();
            paintPanel();
       
    }
    

    public void setSize(int width, int height)
    {
        super.setSize(width, height);

    }

    public void resizeNetwork()
    {
      //  net.setPanelSize(getWidth(), getHeight());
        //         if (optimizationIndex==0 || optimizationIndex==1)
        //             net.setPanelSize(getWidth(), getHeight());
        //         else if (optimizationIndex==2)
        //             lNet.setPanelSize(getWidth(), getHeight());
        //         else if (optimizationIndex==3)
        //             cNet.setPanelSize(getWidth(), getHeight());
    }

    private void update()
    {
    }
   /* public void run()
 
    {

        long afterTime, timeDiff, sleepTime;
        long overSleepTime = 0L;
        int noDelays = 0;
        long excess = 0L;

        beforeTime = System.nanoTime();

        movementFramesCount = 0;

        while (true) {
            // System.out.println("in while:"+getWidth()+" "+getHeight());
            //if (optimizationIndex==0 || optimizationIndex==1)
            //     net.getNodeSteps();

            update();
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

          
            int skips = 0;
            while (excess > period && skips < MAX_FRAME_SKIPS) {
                excess -= period;
                update();   // update state but don't render
                skips++;
            }
        }
    }
*/
   
    private void render()
    // draw the current frame to an image buffer
    {
        //System.out.println("(run)"+getHeight() + " "+getWidth());

        if (dbImage == null){//System.out.println(getWidth()+"----"+getHeight());
            dbImage = new BufferedImage(200,200, BufferedImage.TYPE_INT_ARGB);
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
       
       // net.draw(dbg2);
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

}