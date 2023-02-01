package fa.dfa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fa.State;

public class DFAState extends State
{
    boolean isStartState, isEndState;
    //Second value of the hashmap is a character, however I think it may need to be a list or array
    //in order to be able to transition on multiple values. (q0 -- 0,1 --> q1)
    HashMap<String, ArrayList<Character>> nextStateMap;
    int transitionCharacterCounter = 0;
    ArrayList<Character> transitionList;

    private DFAState(String name)
    {
        super(name);
        isStartState = false;
        isEndState = false;
        transitionList = new ArrayList<Character>();
        nextStateMap = new HashMap<String, ArrayList<Character>>();
        
        
    }
    
    private void toggleStartState()
    {
        isStartState = !isStartState;
    }
    private void toggleEndState()
    {
        isEndState = !isEndState;
    }
    private void addTransition(String nextStateName, Character transitionChar)
    {
        //Need to find a way to add a character to a list or array inside of the .put function for hashmap
        transitionList.add(transitionChar);
        nextStateMap.put(nextStateName, transitionList);
        //I think this will work, might need to go through list and make ensure the transitionChar isn't 
        //already in the list...
    }

    //not sure if its really necessary but we could add a remove transition method?
    //feel free to remove this 
    private void removeTransition(String nextStateName, Character transitionChar)
    {
        for (Character i : transitionList)
        {
            if (i.equals(transitionChar))
            {
                transitionList.remove(i);
            }
        }
    }

    //probably need a toString to use in conjunction with other toStrings for the DFA table at the end
    public String toString()
    {
        //we'll need to figure out the exact formatting for it later.
        return null;
        
    }

}
