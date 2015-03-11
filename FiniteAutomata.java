import java.util.*;
/**
 * Represents a deterministic or nondeterministic finite automata by ArrayList of states and ?adjacency matrix?
 * 
 * @author Alex Porter
 */
public class FiniteAutomata
{
    private boolean deterministic;

    private ArrayList<String> alphabet;
    private String initState;
    private ArrayList<String> finalStates;
    private int numStates;
    private String[][] transitions;
    public FiniteAutomata(ArrayList<String> alphabet,String initState, ArrayList<String>finalStates)
    {
        this.alphabet = alphabet;
        this.initState =initState;
        this.finalStates =finalStates;
        transitions = new String[10][10];
    }

    public void addTransitions(ArrayList<String> transitionsIn)
    {
        for(int i =0; i<transitionsIn.size();i++)
        {
            Scanner tScan = new Scanner(transitionsIn.get(i));
            int start = Integer.parseInt(tScan.next());
            String trans = tScan.next();
            int end = Integer.parseInt(tScan.next());
            transitions[start][end] = trans;
        }
    }

}
