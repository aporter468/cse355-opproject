import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.util.*;
/**
 * Interface panel to encapsulate all controls, show output
 * @author Alex Porter
 */
public class GUIFrame
{

    ModelPanel modelPanel;
    private static String fileName;
    GUIFrame()
    {
        createAndShowGUI();
    }

    private void createAndShowGUI()
    {
        final JFrame frame = new JFrame("Model Checking and Verification");
        frame.setSize(800,800);
          modelPanel = new ModelPanel(this);
        JPanel content = new JPanel(new BorderLayout());

        JPanel importPanel =  new JPanel(new FlowLayout());

        final  JTextArea filePrompt = new JTextArea();
        filePrompt.setEditable(false);
        filePrompt.setText("Enter file name: ");

        final JTextField fileField= new JTextField(15);
        fileField.setText("filename");
        fileField.selectAll();
        fileField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    fileName = fileField.getText();
                    fileField.selectAll();
                }
            }); 

        JButton loadModelButton=new JButton("Load Model(s) from File");
        loadModelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   // txtFileInterface fileIO = new txtFileInterface(fileName);
                   // networkP.net.setNetwork(fileIO.getNetwork(), fileIO.getNodeCount(), fileIO.getNames(), fileIO.getNodeWeights());
                   // nodeCountSlider.setValue(nodeCount);
                }
            });
        importPanel.add(filePrompt);
        importPanel.add(fileField);
        importPanel.add(loadModelButton);

        importPanel.setOpaque(false);
        content.add(modelPanel, BorderLayout.CENTER);
        content.add(importPanel, BorderLayout.SOUTH);

        frame.add(content);
        frame.setVisible(true);
        frame.addWindowListener(new 
            WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    Window win = e.getWindow();
                    win.setVisible(false);
                    win.dispose();
                    System.exit(0);
                }
            });
frame.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e)
                {
                   modelPanel.myGame.setPanelSize(modelPanel.getWidth(), modelPanel.getHeight());

                }
            });
    }

    public static void main(String args[])
    {
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new GUIFrame();
                }
            });
    }
}
