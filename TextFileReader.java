
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
                fileIn.nextLine();
                readAutomata(alphabet);
            }
            if(fileIn.hasNextLine())
            {
                String nextLine = fileIn.nextLine();
                if(nextLine.startsWith("%"))
                {
                    readAutomata(alphabet);
                }
            }
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

    private void readAutomata(ArrayList<String> alphabet)
    {

        fileIn.nextLine();
        String  transitionLine = fileIn.nextLine();
        while(!transitionLine.startsWith("%"))
        {
            System.out.println("transition: "+transitionLine+"\n");
            transitionLine = fileIn.nextLine();
        }
        String initState = fileIn.nextLine();
        System.out.println("init: "+initState);
        fileIn.nextLine();//junk for final...
         ArrayList<String> finalStates = new ArrayList<String>();
        String finalStateLine = fileIn.nextLine();

        while(!finalStateLine.startsWith("%") )
        {
            System.out.println("finalStates: "+finalStateLine+"\n");
            finalStates.add(finalStateLine);
            if(fileIn.hasNext())
                finalStateLine = fileIn.nextLine();
            else
                break;

        }
    FiniteAutomata newFA = new FiniteAutomata(alphabet,initState,finalStates);
    }


}
