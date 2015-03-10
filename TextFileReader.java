
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

            fileIn.nextLine();
            ArrayList<String> alphabet = readAlphabet();
            System.out.println(alphabet.toString());
            String nextSection = fileIn.next();
            if(nextSection.equals("Specification"))
            {
                readSpecificationAutomata();
            }
           // readSystemAutomata();

        }
        catch (Exception e)
        {
            System.out.println("file cannot be loaded");
        }

    }

    private ArrayList<String> readAlphabet()
    {
        ArrayList<String> a = new ArrayList<String>();
        String next = fileIn.next();

        while(!next.equals("%"))
        {
            a.add(next);
            next = fileIn.next();
        }
        return a;

    }

    private void readSpecificationAutomata()
    {
      fileIn.nextLine();
       fileIn.nextLine();
      String  transitionLine = fileIn.nextLine();
       while(!transitionLine.startsWith("%"))
       {
           System.out.println("transition: "+transitionLine+"\n");
           transitionLine = fileIn.nextLine();
        }
    }

    private void readSystemAutomata()
    {
        System.out.println(fileIn.next());
    }

}
