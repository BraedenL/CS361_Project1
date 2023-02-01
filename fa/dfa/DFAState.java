package fa.dfa;

import java.util.HashMap;
import java.util.List;

import fa.State;

public class DFAState extends State
{
    boolean isStartState, isEndState;
    //Second value of the hashmap is a character, however I think it may need to be a list or array
    //in order to be able to transition on multiple values. (q0 -- 0,1 --> q1)
    HashMap<String, List<Character>> nextStateMap;
    int transitionCharacterCounter = 0;

    private DFAState(String name)
    {
        super(name);
        isStartState = false;
        isEndState = false;
        nextStateMap = new HashMap<String, List<Character>>();
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
        nextStateMap.put(nextStateName, );
    }

}
