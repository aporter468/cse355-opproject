import java.util.*;
/**
 * Represents a deterministic or nondeterministic finite automata by ArrayList of states (and initial matrix)
 * 
 * @author Alex Porter
 */
public class FiniteAutomaton
{
    private boolean deterministic;

    private ArrayList<String> alphabet;
    private String initStateName;
    private State initState;
    private ArrayList<String> acceptStates;
    private ArrayList<String> transitionsList;
    private State[] states;
    private ArrayList<State> statesList;
    private int numStates;
    private boolean stringFound = false;
    private ArrayList<String> visitedStates;
    public FiniteAutomaton(ArrayList<String> alphabet,String initStateName, ArrayList<String>acceptStates, ArrayList<String> transitionsList)
    {
        this.alphabet = alphabet;
        this.initStateName =initStateName;
        this.acceptStates =acceptStates;
        this.transitionsList = transitionsList;
        int numStates = 0;
        states = new State[100];//max specified is 100. need array to have access by index from init.
        statesList = new ArrayList<State>();
        System.out.println("Start state: "+initStateName);
        System.out.println("Accept states: "+acceptStates);
        construct();
    }

    /**
     * construct builds the FA from the transitions list, adding states as they are used
     */
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
                statesList.add(states[start]);

            }
            if(states[end] == null)
            {
                states[end] = new State(end,alphabet,false,false);
                statesList.add(states[end]);
            }
        }
        //set initial, accept states
        int startIndex = Integer.parseInt(initStateName);
        states[startIndex].setStart(true);
        initState = states[startIndex];
        for(int i =0; i<acceptStates.size(); i++)
        {
            int stateIndex = Integer.parseInt(acceptStates.get(i));
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

    /**
     * convertToDFA explores the FA starting at the initial state and placing states and combinations
     * on a queue to explore transitions from them and create new combinations as necessary.
     */
    public void convertToDFA()
    {

        ArrayList<State> stateQueue = new ArrayList<State>();//FIFO queue for adding states as they are used
        ArrayList<State> newStatesList = new ArrayList<State>();

        //first combined state is just initial; push new init onto queue, add to newStatesList
        State newInitState = new State(0,alphabet,true,false);
        ArrayList<State> initPrevStates = new ArrayList<State>();
        initPrevStates.add(initState);
        newInitState.setPrevStatesCombined(initPrevStates);
        initState = newInitState;
        stateQueue.add(newInitState);
        newStatesList.add(newInitState);
        while(stateQueue.size()>0)
        {
            State currentState = stateQueue.get(0);
            for(int i = 0; i<alphabet.size(); i++)
            {
                ArrayList<State> toStatesList = getToStatesList(currentState,alphabet.get(i));
                if(toStatesList.size()>0)
                {

                    //check toStates list against existing combo states in new States list
                    int existingIndex = -1;
                    for(int j  =0; j<newStatesList.size(); j++)
                    {
                        if( newStatesList.get(j).matchesStatesCombined(toStatesList))
                        {
                            existingIndex = j;
                        }
                    }

                    //determine if combination of states is new; connect to existing state for that set of states or create
                    if(existingIndex>-1)
                    {
                        //connect to existin
                        currentState.addTransition(alphabet.get(i),newStatesList.get(existingIndex));
                    }
                    else
                    {
                        State newComboState = new State(newStatesList.size(),alphabet,false,false);
                        newComboState.setPrevStatesCombined(toStatesList);

                        currentState.addTransition(alphabet.get(i),newComboState);
                        stateQueue.add(newComboState);
                        newStatesList.add(newComboState);
                    }

                }
            }
            stateQueue.remove(0);//pop off FIFO queue

        }

        //Set the states for this FA class to the new ones created
        states = new State[100];
        acceptStates = new ArrayList<String>();
        statesList = newStatesList;
        for(int i =0;i<newStatesList.size();i++)
        {
            State newStatei = newStatesList.get(i);
            states[newStatei.getIndex()] = newStatei;
            for(int k = 0; k<newStatesList.get(i).getPrevStatesCombined().size();k++)
            {
                if(newStatesList.get(i).getPrevStatesCombined().get(k).getAccept())
                {
                    newStatesList.get(i).setAccept(true);
                }
            }
            if(newStatesList.get(i).getAccept())
            {
                acceptStates.add(newStatesList.get(i).getIndex()+"");
            }
            if(newStatei.getStart())
            {
                initStateName = newStatei.getIndex()+"";
            }
        }

        System.out.println("Converted to DFA: " + newStatesList.size()+" states:");
        printFA();

    }

    private void printFA()
    {
        System.out.println("DFA: ");
        for(int i =0; i<statesList.size();i++)
        {
            State current = statesList.get(i);
            System.out.println("Index: "+current.getIndex()+" combined from originals: "+current.getPrevStatesCombined()+" ");
            for(int j = 0; j<alphabet.size();j++)
            {
                System.out.println("On "+alphabet.get(j)+" : "+current.getTransitionsOn(alphabet.get(j)));
            }
        }
        System.out.println("Accept states: "+acceptStates);
    }

    /**
     * getToStatesList combines all the toStates for the prev states combined in the current state (such as {1,2})
     * @return toStatesList
     */
    private ArrayList<State> getToStatesList(State start, String alph)
    {
        ArrayList<State> toStates = new ArrayList<State>();
        ArrayList<Integer> usedIndices = new ArrayList<Integer>();
        for(int i =0; i<start.getPrevStatesCombined().size(); i++)
        {
            ArrayList<State> temp = start.getPrevStatesCombined().get(i).getTransitionsOn(alph);
            for(int j = 0; j<temp.size(); j++)
            {
                int index = temp.get(j).getIndex();
                if(usedIndices.indexOf(index)==-1)
                {
                    toStates.add(temp.get(j));
                    usedIndices.add(index);
                }
            }

        }
        return toStates;

    }

    /**
     * Inverte the isAccept boolean for each state in the machine
     */
    public void convertToComplement()
    {

        acceptStates = new ArrayList<String>();
        for(int i =0; i<statesList.size(); i++)
        {
            boolean isAccept = statesList.get(i).getAccept();
            statesList.get(i).setAccept(!isAccept);
            if(statesList.get(i).getAccept())
            {
                acceptStates.add(statesList.get(i).toString());
            }

        }

        System.out.println("Accept states after complement: "+acceptStates);

    }

    /**
     * interface method for generating an accept string, within maximum length given
     * calls recursive buildAcceptedString method
     */
    public String findAcceptedString(int maxLength)
    {
        State currentState = initState;
        String w = "";
        if(acceptStates.size()==0) //since the DFA is constructed by exploration, all states that are accepted and included here are reachable
        return "no string";
        
        w = buildAcceptedString(currentState,w);
        return w;
    }

    /**
     * build string recursively, favoring transitioning to new states over current, but could find loops, so maxes out
     * @return acceptedString
     */
    private String buildAcceptedString(State s,String w)
    {

        if(s.getAccept() )
        {

            stringFound = true;
            if(w.length()==0)
            {
                return "epsilon";
            }
            return "";

        } 

        else
        {
            for(int i =0; i<alphabet.size();i++)
            {
                ArrayList<State> nextStates = s.getTransitionsOn(alphabet.get(i));
                String attempt = "";

                for(int j = 0;j<nextStates.size();j++)
                {

                    if(nextStates.get(j).getIndex()!= s.getIndex())
                    {
                        String next = buildAcceptedString(nextStates.get(j),w+alphabet.get(i));
                        if(next.equals("no string"))
                        {
                            attempt = "no string";
                        }
                        else
                        {
                            return alphabet.get(i)+next;
                        }
                    }
                }

            }
            return "no string";

        }

    }

    public void makeIntersection(FiniteAutomaton FA2)
    {
    }

}
