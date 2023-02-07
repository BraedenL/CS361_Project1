package fa.dfa;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fa.State;
/**
 * DFAState builds and composes the FA being managed in main programs. It tracks its transitions, the status on start or
 * final states, and other commands needed to manage states.
 * @author Braeden LaCombe, Andrew Lackey
 */
public class DFAState extends State
{
    boolean isStartState, isFinalState;
    //Second value of the hashmap is a character, however I think it may need to be a list or array
    //in order to be able to transition on multiple values. (q0 -- 0,1 --> q1)
    HashMap<String, ArrayList<Character>> nextStateMap;
    int transitionCharacterCounter = 0;
    ArrayList<Character> transitionList;
    int positionAddedInMap;

    /**
     * Constructor for DFAStates, in which a name is given to assign for the state.
     * @param name
     */
    public DFAState(String name)
    {
        super(name);
        isStartState = false;
        isFinalState = false;
        transitionList = new ArrayList<Character>();
        nextStateMap = new HashMap<String, ArrayList<Character>>();
        positionAddedInMap = -1;
        
    }
    /**
     * Toggles if the state is a start state, prefer to use toggle in order to have one overall function
     */
    public void toggleStartState()
    {
        isStartState = !isStartState;
    }
    //For usage when checking all 
    /**
     * Return if the state is a starting state, and so strings can begin on this state
     * @return boolean - start state status
     */
    public boolean checkStartStatus()
    {
        return isStartState;
    }
    /**
     * Toggles if the state should be a final state, allowing strings to end on it
     */
    public void toggleFinalState()
    {
        isFinalState = !isFinalState;
    }
    /**
     * Adds a transition to the HashMap that each state holds, and stores what state name to move to and on what character
     * @param nextStateName - The next state to move to on transition
     * @param transitionChar - The character to move to next state on
     */
    public void addTransition(String nextStateName, Character transitionChar)
    {
        ArrayList<Character> tempList = new ArrayList<Character>();
        ArrayList<Character> tempList_Test = new ArrayList<Character>();
        Boolean changeOccured = false;
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
        tempList.add(transitionChar);
        if(nextStateMap.containsKey(nextStateName)){//already a transition to a given state
            // System.out.println("hi number 1");
            // tempList_Test = nextStateMap.get(nextStateName);
            tempList_Test.add(transitionChar);
            if(!tempList_Test.isEmpty())
            {
                // System.out.println(tempList + "templist before");
                // tempList.addAll(tempList_Test);
                // System.out.println(tempList + "templist after");
                nextStateMap.merge(nextStateName, tempList_Test, (oldValue, newValue) -> 
                {
                    oldValue.addAll(tempList_Test);
                    return oldValue;
                }
                );
                changeOccured = true;
            }
        }
        
        if(!changeOccured) 
        {
            nextStateMap.put(nextStateName, tempList);
            System.out.println(nextStateMap.toString());
        }
        
       
        /*
        transitionList.add(transitionChar);
        System.out.println(nextStateMap.toString());
        nextStateMap.put(nextStateName, transitionList);
        System.out.println(nextStateMap.toString());
        */

        //I think this will work, might need to go through list and make ensure the transitionChar isn't 
        //already in the list... --ADDED--
    }

    /**
     * Returns the next State given a transition character
     * @param transitionChar - The character to search for
     * @return - The string states to move to
     */
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

    /**
     * Returns the current assigned position of when it was added to the map
     * @return
     */
    public int addedToMapOrder_getPos() 
    {
        return positionAddedInMap;
    }


    /**
     * Adds a new position if theres not one already
     * @param - newPos postion to be given to this state
     * @return - true if newPos was added sucessfully
     */
    public boolean addedToMapOrder_replace(int newPos)
    {
        if(positionAddedInMap < 0)
        {
            positionAddedInMap = newPos;
            return true;
        }
        else
        {
            return false;
        }
    }

    //probably need a toString to use in conjunction with other toStrings for the DFA table at the end
    /**
     * Returns the name of the DFAState, from State interface
     */
    public String toString()
    {
        //we'll need to figure out the exact formatting for it later.
        return getName();   
    }
}
