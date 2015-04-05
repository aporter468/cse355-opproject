
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
public class Main
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
        Main mainReader = new Main(fileName);
        System.out.println("Load another file? (y/n) ");
        try{
            String input = br.readLine();
            while(!input.equals("n"))
            {
                System.out.print("Enter filename with extension (ie example.txt):\n");
                fileName = "";
                fileName = br.readLine(); 
                Main mainReader1 = new Main(fileName);
                System.out.println("Load another file? (y/n) ");
                input = br.readLine();
            }

        }  catch(Exception e){}
        System.out.println("program finished.");
    }

    public Main(String fileName)
    {
        this.fileName = fileName;
        try
        {
            fileIn = new Scanner (new File(fileName));

            fileIn.nextLine();
            ArrayList<String> alphabet = readAlphabet();
            System.out.println("Alphabet read: "+alphabet.toString());
            String nextSection = fileIn.next();
            FiniteAutomaton SpecFA = null;
            FiniteAutomaton SysFA= null;
            if(nextSection.equals("Specification"))
            {
                System.out.println("Read specification automaton:");
                fileIn.nextLine();
                fileIn.nextLine();
                SpecFA= readAutomata(alphabet,specFA);
            }
            if(fileIn.hasNextLine())
            {
                System.out.println("Read system automaton: "); 
                String nextLine = fileIn.nextLine();

                if(nextLine.startsWith("%"))
                {
                    SysFA= readAutomata(alphabet,sysFA);
                }
            }
            if(SpecFA!=null && SysFA!=null)
            {
                //L(A) intersection 'L(S) = (L(A)' union L(SL))'
                           SysFA.convertToComplement();
               SpecFA.makeUnionWith(SysFA);
               SpecFA.convertToComplement();
               System.out.println("accepted string: "+SpecFA.findAcceptedString(10));
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

    private FiniteAutomaton readAutomata(ArrayList<String> alphabet, FiniteAutomaton FA)
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
        if(fileIn.hasNext())
        {
            String finalStateLine = fileIn.nextLine();

            while(!finalStateLine.startsWith("%") )
            {
                finalStates.add(finalStateLine);
                if(fileIn.hasNext())
                    finalStateLine = fileIn.nextLine();
                else
                    break;

            }
        }
        FA = new FiniteAutomaton(alphabet,initState,finalStates,transitions);
       FA.convertToDFA();
      //  FA.convertToComplement();
      return FA;

    }

}
