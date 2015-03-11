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
    public State(int index, ArrayList<String> alphabet, boolean isStart, boolean isAccept)
    {
        this.index = index;
        this.alphabet = alphabet;
        transTo = new ArrayList<State>();
        transOn = new ArrayList<String>();
        this.isStart = isStart;
        this.isAccept = isAccept;
    }

    public ArrayList<State> getTransitionsOn(String a)
    {
        //find indices of a in transOn, return list of corresponding states
        ArrayList<State> transOnA = new ArrayList<State>();
        return transOnA;
        
    }

    public State getFirstTransitionOn(String a)
    {
        //return first transition on a; will be only if is DfA...
        return null;
    }

    public void addTransition(String a, State toState)
    {
        //check if a is in trans on and toState = correspoinding transTo, else add to end
    }

    
    
    //getters and setters
   
    public void setStart(boolean isStart){ this.isStart = isStart; }

    public void setAccept(boolean isAccept){ this.isAccept = isAccept; }

    public boolean getStart(){return isStart;}

    public boolean getAccept(){return isAccept;}

    public int getIndex(){ return index;}

}
