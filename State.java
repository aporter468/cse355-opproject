import java.util.*;
/**
 *Represents a single state for an FA (DFA or NFA)
 * @author Alex Porter
 */
public class State
{
    private int index;
    private ArrayList<String> alphabet;
    private ArrayList<State> transTo;
    private ArrayList<String> transOn;
    private boolean isStart;
    private boolean isAccept;
    private ArrayList<State> prevStatesCombined;//for nfa to dfa- list of states from the nfa that this dfa state corresponds to
    public State(int index, ArrayList<String> alphabet, boolean isStart, boolean isAccept)
    {
        this.index = index;
        this.alphabet = alphabet;
        transTo = new ArrayList<State>();
        transOn = new ArrayList<String>();
        this.isStart = isStart;
        this.isAccept = isAccept;
        prevStatesCombined = new ArrayList<State>();
    }

    /**
     * Generates an ArrayList of states reached on input string a
     */
    public ArrayList<State> getTransitionsOn(String a)
    {
        //find indices of a in transOn, return list of corresponding states
        ArrayList<State> transOnA = new ArrayList<State>();
        for(int i =0; i<transTo.size(); i++)
        {
            if(transOn.get(i).equals(a))
            {
                transOnA.add(transTo.get(i));
            }
        }
        sortStatesList(transOnA);
        return transOnA;

    }
    /**
     * same process as getTransitionsOn but generates list of integer indices.
     */

    public ArrayList<Integer> getIndicesTransitionsOn(String a)
    {
        //find indices of a in transOn, return list of corresponding states
        ArrayList<Integer> transOnA = new ArrayList<Integer>();
        for(int i =0; i<transTo.size(); i++)
        {
            if(transOn.get(i).equals(a))
            {
                transOnA.add(transTo.get(i).getIndex());
            }
        }
        return transOnA;

    }

    /**
     * Returns first state reached on input string a
     */
    public State getFirstTransitionOn(String a)
    {
        //return first transition on a; will be only if is DfA...
        int indexOfa = transOn.indexOf(a);
        if(indexOfa>-1) return transTo.get(indexOfa);
        return null;
    }

    /**
     * construct transition by adding state to list if not already existing
     */
    public void addTransition(String a, State toState)
    {
        //check if a is in trans on and toState = correspoinding transTo, else add to end
        int indexOfa = transOn.indexOf(a);
        if(indexOfa>-1 && toState.getIndex() == transTo.get(indexOfa).getIndex())//alread exists
        {
            return;
        }
        else
        {
            transTo.add(toState);
            transOn.add(a);
        }
    }

    /**
     * checks if the states combined (from FA before conversion) is a match to prevent duplication
     */
    public boolean matchesStatesCombined(ArrayList<State> otherStatesList)//assumes in order
    {
        //TODO: match checking, even if out of order?
        if(prevStatesCombined.size()!=otherStatesList.size()) return false;
        for(int i =0; i<prevStatesCombined.size(); i++)
        {
            if(prevStatesCombined.get(i).getIndex()!=otherStatesList.get(i).getIndex())
                return false;
        }
        return true;
    }

    public String toString()
    {
        return "State: "+index;
    }
    //getters and setters
    public void setPrevStatesCombined(ArrayList<State> psc){  prevStatesCombined = psc; }

    public ArrayList<State> getPrevStatesCombined(){ return prevStatesCombined;}

    public void setStart(boolean isStart){ this.isStart = isStart; }

    public void setAccept(boolean isAccept){ this.isAccept = isAccept; }

    public boolean getStart(){return isStart;}

    public boolean getAccept(){return isAccept;}

    public int getIndex(){ return index;}

    public static ArrayList<State> sortStatesList(ArrayList<State> list)
    {
        return list;
    }

}
