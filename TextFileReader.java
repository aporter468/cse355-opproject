
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
    private FiniteAutomaton specFA;
    private FiniteAutomaton sysFA;
    public static void main(String args[])
    {
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter filename with extension (ie example.txt):\n");
        String fileName = "";
        try{
         fileName = br.readLine();}
        catch(Exception e){}
        TextFileReader tfr = new TextFileReader(fileName);
        
    }
    public TextFileReader(String fileName)
    {
        this.fileName = fileName;
        try
        {
            fileIn = new Scanner (new File(fileName));

            fileIn.nextLine();
            ArrayList<String> alphabet = readAlphabet();
            System.out.println("Alphabet read: "+alphabet.toString());
            String nextSection = fileIn.next();
            if(nextSection.equals("Specification"))
            {
                System.out.println("Read specification automaton:");
                fileIn.nextLine();
                fileIn.nextLine();
                readAutomata(alphabet,specFA);
            }
            if(fileIn.hasNextLine())
            {
                System.out.println("Read system automaton: "); 
                String nextLine = fileIn.nextLine();

                if(nextLine.startsWith("%"))
                {
                    readAutomata(alphabet,sysFA);
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

    private void readAutomata(ArrayList<String> alphabet, FiniteAutomaton FA)
    {


        String  transitionLine = fileIn.nextLine();
        ArrayList<String> transitions = new ArrayList<String>();
        while(!transitionLine.startsWith("%"))
        {
            transitions.add(transitionLine);
            transitionLine = fileIn.nextLine();
        }
        String initState = fileIn.nextLine();
        fileIn.nextLine();//junk for final...
         ArrayList<String> finalStates = new ArrayList<String>();
        String finalStateLine = fileIn.nextLine();

        while(!finalStateLine.startsWith("%") )
        {
            finalStates.add(finalStateLine);
            if(fileIn.hasNext())
                finalStateLine = fileIn.nextLine();
            else
                break;

        }
    FA = new FiniteAutomaton(alphabet,initState,finalStates,transitions);
    FA.convertToDFA();
    FA.convertToComplement();
    System.out.println("String accepted by L(D'): "+FA.findAcceptedString(100));
    }


}
