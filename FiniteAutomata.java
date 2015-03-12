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
    private ArrayList<String> transitionsList;
    private State[] states;
    private int numStates;

    public FiniteAutomata(ArrayList<String> alphabet,String initState, ArrayList<String>finalStates, ArrayList<String> transitionsList)
    {
        this.alphabet = alphabet;
        this.initState =initState;
        this.finalStates =finalStates;
        this.transitionsList = transitionsList;
        int numStates = 0;
        states = new State[100];//max specified is 100. need array to have access by index from init.

        construct();
    }

    private void construct()
    {
        //loop over transitions to make states list
        for(int i =0; i<transitionsList.size();i++)
        {
            Scanner tScan = new Scanner(transitionsList.get(i));
            int start = Integer.parseInt(tScan.next());
            String trans = tScan.next();
            int end = Integer.parseInt(tScan.next());
            if(states[start]==null)
            {
                states[start] = new State(start,alphabet,false,false);

                System.out.println("start: "+start+" constructed");

            }
            if(states[end] == null)
            {
                states[end] = new State(end,alphabet,false,false);
                System.out.println("end: "+end+" constructed");
            }
        }
        //set initial, accept states
        int startIndex = Integer.parseInt(initState);
        states[startIndex].setStart(true);
        for(int i =0; i<finalStates.size(); i++)
        {
            int stateIndex = Integer.parseInt(finalStates.get(i));
            states[stateIndex].setAccept(true);
        }
        //loop over transitions to add transitions to existing states
        for(int i =0; i<transitionsList.size();i++)
        {
            Scanner tScan = new Scanner(transitionsList.get(i));
            int start = Integer.parseInt(tScan.next());
            String trans = tScan.next();
            int end = Integer.parseInt(tScan.next());
            states[start].addTransition(trans,states[end]);

        }
    }

    public boolean isDFA()
    {
        return false;
    }

    public void convertToDFA()
    {
       // if (isDFA()) return null;
        ArrayList<State> stateQueue = new ArrayList<State>();
        ArrayList<State> newStatesList = new ArrayList<State>();
        //first combined state is just initial; push new init onto queue, add to newStatesList
        //while has queue
            //go through alphabet as transition from first in queue
            //add states to combinedStates list, stateQueue and statesList as necessary
     
      //map newStatesLsit to states[], initState, and acceptStates so conversion is complete.
    }

    public void convertToComplement()
    {
        for(int i = 0; i<100; i++)
        {
            if(states[i]!=null)
            {
                boolean isAccept = states[i].getAccept();
                states[i].setAccept(!isAccept);
            }
        }

    }
    public String findAcceptedString()//do this recursively
    {
        return "";
    }

    public void makeIntersection(FiniteAutomata FA2)
    {
    }

}
