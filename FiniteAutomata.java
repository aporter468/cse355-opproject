import java.util.*;
/**
 * Represents a deterministic or nondeterministic finite automata by ArrayList of states and ?adjacency matrix?
 * 
 * @author Alex Porter
 */
public class FiniteAutomata
{
    private boolean deterministic;
    private ArrayList<String> states;
    private ArrayList<String> alphabet;
    private String initState;
    private ArrayList<String> finalStates;
    public FiniteAutomata(ArrayList<String> alphabet,String initState, ArrayList<String>finalStates)
    {
        this.alphabet = alphabet;
        this.initState =initState;
        this.finalStates =finalStates;
    }


}
