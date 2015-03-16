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
    private boolean stringFound = false;
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
        ArrayList<State> stateQueue = new ArrayList<State>();//FIFO queue for adding states as they are used
        ArrayList<State> newStatesList = new ArrayList<State>();

        //first combined state is just initial; push new init onto queue, add to newStatesList
        State newInitState = new State(0,alphabet,true,false);
        ArrayList<State> initPrevStates = new ArrayList<State>();
        initPrevStates.add(states[Integer.parseInt(initState)]);
        newInitState.setPrevStatesCombined(initPrevStates);
        System.out.println("queue starting with: "+initState);
        stateQueue.add(newInitState);
        newStatesList.add(newInitState);
        while(stateQueue.size()>0)
        {
            System.out.println("Queue: "+stateQueue.toString());
            State currentState = stateQueue.get(0);
            System.out.println("current: "+currentState.toString());
            for(int i = 0; i<alphabet.size(); i++)
            {
                ArrayList<State> toStatesList = getToStatesList(currentState,alphabet.get(i));
                if(toStatesList.size()>0)
                {
                    System.out.println("toStatesList: "+toStatesList.toString());
                    //check toStates list against existing combo states in new States list
                    int existingIndex = -1;
                    for(int j  =0; j<newStatesList.size(); j++)
                    {
                        if( newStatesList.get(j).matchesStatesCombined(toStatesList))
                        {
                            existingIndex = j;
                            System.out.println("Existing index: "+j+" state index: "+newStatesList.get(j).getIndex());
                        }
                    }

                    //determine if combination of states is new; connect to existing state for that set of states or create

                    if(existingIndex>-1)
                    {
                        //connect to existin
                        System.out.println("connection to existing.");
                        currentState.addTransition(alphabet.get(i),newStatesList.get(existingIndex));
                    }
                    else
                    {
                        System.out.println("adding combostate for: "+toStatesList.toString());
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
        //map to actual vals for this FA
        states = new State[100];
        finalStates = new ArrayList<String>();
        System.out.println("\nresult: ");
        for(int i =0;i<newStatesList.size();i++)
        {
            State newStatei = newStatesList.get(i);
            System.out.println(newStatei.getIndex()+"from "+newStatei.getPrevStatesCombined()+" "+newStatei.getTransitionsOn("a")+" "+newStatei.getTransitionsOn("b"));
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
                finalStates.add(newStatesList.get(i).getIndex()+"");
            }
            if(newStatei.getStart())
            {
                initState = newStatei.getIndex()+"";

            }
        }
        System.out.println("init state: "+initState);
        System.out.println("final states: ");
        for(int i =0; i<finalStates.size();i++)
        {
            System.out.println(finalStates.get(i));
        }

    }

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
        for(int i =0;i<100; i++)
        {

            if(states[i]!=null)
            {
                System.out.println(states[i].toString()+" "+states[i].getAccept());
            }
        }

    }

    public String findAcceptedString()
    {
        State currentState = states[Integer.parseInt(initState)];
        String w = "";

        w = buildAcceptedString(currentState,w);
        System.out.println("w built? "+w);
        return w;
    }

    private String buildAcceptedString(State s,String w)
    {
        if(s.getAccept() )
        {
            stringFound = true;
            if(w.length()==0)
            {
                return "(epsilon)";
            }
            return "";

        } 
        else if (w.length()>10)
        {
            return "nostring";
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
                        if(next.equals("nostring"))
                        {
                            attempt = "nostring";
                        }
                        else
                        {
                            return alphabet.get(i)+next;
                        }
                    }
                }

            }
            return "nostring";

        }

    }

    public void makeIntersection(FiniteAutomata FA2)
    {
    }

}
