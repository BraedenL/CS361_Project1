package fa.dfa;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fa.State;

public class DFAState extends State
{
    boolean isStartState, isFinalState;
    //Second value of the hashmap is a character, however I think it may need to be a list or array
    //in order to be able to transition on multiple values. (q0 -- 0,1 --> q1)
    HashMap<String, ArrayList<Character>> nextStateMap;
    int transitionCharacterCounter = 0;
    ArrayList<Character> transitionList;

    public DFAState(String name)
    {
        super(name);
        isStartState = false;
        isFinalState = false;
        transitionList = new ArrayList<Character>();
        nextStateMap = new HashMap<String, ArrayList<Character>>();
        
    }
    
    public void toggleStartState()
    {
        isStartState = !isStartState;
    }
    //For usage when checking all 
    public boolean checkStartStatus()
    {
        return isStartState;
    }
    public void toggleFinalState()
    {
        isFinalState = !isFinalState;
    }
    public void addTransition(String nextStateName, Character transitionChar)
    {
        //Check if the transition already exists inside of the state, prevent duplicates from being added to a next state
        for(Character i: transitionList)
        {
            if(i.equals(transitionChar))
            {
                //Console output just for checking if state already has a transition for that character
                System.out.println("There is already a transition for that input on this state");
                return;
            }
        }
        transitionList.add(transitionChar);
        nextStateMap.put(nextStateName, transitionList);
        //I think this will work, might need to go through list and make ensure the transitionChar isn't 
        //already in the list... --ADDED--
    }

    //not sure if its really necessary but we could add a remove transition method? feel free to remove this 
    //Edited to remove state input in case we need to use it
    private void removeTransition(Character transitionChar)
    {
        for (Character i : transitionList)
        {
            if (i.equals(transitionChar))
            {
                transitionList.remove(i);
            }
        }
    }

    //returns the next State given a transition char
    public String findStateOnTransition(Character transitionChar)
    {
        String stateName = null;
        ArrayList<Character> transitionArr;

        //for each nextstate in the nextstate map
        for(String S_name: nextStateMap.keySet())
        {
            //get the transition characters used to get to that next state
            transitionArr = nextStateMap.get(S_name);
            for (Character c : transitionArr) {
                if (c == transitionChar)
                {
                    //if one of those transition characters is the one were looking for, return the state found
                    stateName = S_name;
                    return stateName;
                }
            }
        }

        return stateName;
    }

    //probably need a toString to use in conjunction with other toStrings for the DFA table at the end
    public String toString()
    {
        //we'll need to figure out the exact formatting for it later.
        return getName();
        
    }


}
