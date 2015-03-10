
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
 *Constructs instances of FiniteAutomata from text file input and returns them to caller
 * 
 * @author Alex Porter
 */
public class TextFileReader
{
    private String fileName;
    private Scanner fileIn;
    public TextFileReader(String fileName)
    {
        this.fileName = fileName;
        try
        {
            fileIn = new Scanner (new File(fileName +".txt"));
            readSpecificationAutomata();
            readSystemAutomata();

        }
        catch (Exception e)
        {
            System.out.println("file cannot be loaded");
        }

    }

    private void readSpecificationAutomata()
    {
        System.out.println(fileIn.next());
    }

    private void readSystemAutomata()
    {
        System.out.println(fileIn.next());
    }

}
