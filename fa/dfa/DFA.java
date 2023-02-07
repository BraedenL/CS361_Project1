package fa.dfa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fa.State;
/**
 * The DFA class controls the machine. It uses HashSets to store any of the main information that is typically stored in a set, and
 * can manage DFAStates in order to create a machine with interconnecting states. It contains a variety of commands to 
 * manage the machine, using the DFAState commands in conjunction with other controls.
 * @author Braeden LaCombe, Andrew Lackey
 */
public class DFA implements DFAInterface{

    /*
     * 5-Tuples, what should we store them as?
     * Alphabet: EnumSet
     * States: EnumSet
     * q0: String
     * Final: EnumSet
     * Transition: Has to be an Interface Map class
     */
    HashSet<Character> Sigma;
    HashSet<DFAState> States;
    String startState;
    HashSet<String> finalState;
    int stateCounter;
    //Replace this with transition table variable

    /**
     * Constructor for the DFA. Initializes variables, and sets empty variables to be managed later on
     */
    public DFA()
    {
        Sigma = new HashSet<Character>();
        States = new HashSet<DFAState>();
        startState = "";
        finalState = new HashSet<String>(); 
        stateCounter = 0;
    }

    /**
     * Adds a state to the DFA machine. Takes in the name to give to the new state
     * @param name - name to give new state
     * @return true if successfull/false if fail
     */
    @Override
    public boolean addState(String name) {
        
        for (DFAState dfaState : States) 
        {
            if(dfaState.getName().equals(name))
            {
                //The state already exists inside of the DFA
                System.out.println("This state already exists for the machine");
                return false;
            }
        }
        DFAState newState = new DFAState(name);
        stateCounter++;
        newState.addedToMapOrder_replace(stateCounter);
        States.add(newState);
        return true;
    }
    /**
     * Sets a state inside of the machine as a final state, and uses the given name to search through the machine states
     * @param name - state to make final in machine
     * @return true if successful/false if failed
     */
    @Override
    public boolean setFinal(String name) {
        //We are allowed to have multiple final states, so don't need to check if others are final like the start state function
        //Check for existing state with name
        boolean nameStateExists = false;
        for(DFAState dfaState : States )
        {
            if(dfaState.getName().equals(name))
            {
                nameStateExists = true;
            }
        }
        if(nameStateExists == false)
        {
            return false;
        }
        /*
        for(DFAState dfaState : States)
        {
            if(dfaState.isFinalState == true)
            {
                return false;
            }
        }
        */
        for(DFAState dfaState : States)
        {
            if(dfaState.getName().equals(name))
            {
                //Searching set, have found the correct state
                dfaState.toggleFinalState();
            }
        }
        finalState.add(name);
        return true;
    }
    /**
     * Sets a state inside of the machine as the start, using a given name to find inside of the state
     * @param name - state in machine to set as start state
     * @return true if successful/false if fails
     */
    @Override
    public boolean setStart(String name) {
        //Check for existing state with name
        boolean nameStateExists = false;
        for(DFAState dfaState : States )
        {
            if(dfaState.getName().equals(name))
            {
                nameStateExists = true;
            }
        }
        if(nameStateExists == false)
        {
            return false;
        }

        for(DFAState dfaState : States)
        {
            if(dfaState.isStartState == true)
            {
                dfaState.isStartState = false;
            }
        }
        for(DFAState dfaState : States)
        {
            if(dfaState.getName().equals(name))
            {
                dfaState.toggleStartState();
            }
        }
        //Will need to check through all stored states and remove start so that only one state is the starting state
        /*
        for(DFAState dfaState : States)
        {
            //Check if any other states are already toggled as a start start
            if(dfaState.checkStartStatus() == true)
            {
                dfaState.toggleStartState();
            }
            //Set the state we want as the start state
            if(dfaState.getName().equals(name))
            {
                dfaState.toggleStartState();
            }
        }
        */
        //Update the start state tracker
        startState = name;
        return true;
    }
    /**
     * Adds characters to the alphabet for the machine
     * @param symbol - Character to add to machine alphabet
     */
    @Override
    public void addSigma(char symbol) {
        for(Character alphabet : Sigma)
        {
            if(alphabet.equals(symbol))
            {
                return;
            }
        }
        Sigma.add(symbol);
    }
    /**
     * Determines if the string given should be accepted by the machine. Moves through the machine one character at a time
     * and decides if the state is a proper end state
     * @param s - string to put into FA
     * @return true if accepted/false if rejected
     */
    @Override
    public boolean accepts(String s) {
        DFAState currentState = null;
        String nextStateName = null;
        //Parse through and get the starting state from the dfa
        for(DFAState dfaState : States)
        {
            if(dfaState.isStartState)
            {
                currentState = dfaState;
            }
        }
        
        if(currentState == null)
        {
            System.out.println("accepts() failed to find start state");
            return false;
        }

        /*
         * Move through the characters of the string
         * For clarification, since the alphabet holds all "letters" that 
         * can be transitioned upon, the "letters" variable will be each 
         * character in the string, 
         */
        for(int i = 0; i < s.length(); i++)
        {
            Boolean stateTransitionSuccess = false; //reset it
            nextStateName = null; //reset it
            char letter = s.charAt(i);//Parse through the proposed string
            nextStateName = currentState.findStateOnTransition(letter); //look for it
            if(nextStateName == null) //no state was found on that transition letter
            {
                return false; //immediately fail it when theres no state for a letter, self loops should not fall into this case.
            }

            for (DFAState dfaState : States) //look through the states for one with provided name
            {
                if(dfaState.getName().equals(nextStateName))
                {
                    currentState = dfaState; //if next state found, change current to it
                    stateTransitionSuccess = true;
                }
            }
            
            if(stateTransitionSuccess != true)//next state was not found...
            {
                System.out.println("Next State name provided from DFAState class " +
                    "\nmethod, However name not found in DFA class list.");
                return false; //obviously a problem/discrepancy, no point in continuing string test
            }
        }


        //Check that the state is a final state at end
        if(currentState.isFinalState)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Return alphabet/Sigma of the machine
     * @return the machine alphabet in a set
     */
    @Override
    public Set<Character> getSigma() {
        return Sigma;
    }
    /**
     * Returns the state given a name from the machine
     * @param name - the state name to return
     * @return a State searched for
     */
    @Override
    public State getState(String name) {
        for (DFAState dfaState : States) 
        {
            if(dfaState.getName().equals(name))
            {
                return dfaState;
            }
        }
        return null;
    }
    /**
     * Returns if a state is the final state
     * @param  name - State to return information from
     * @return true if successfull/false if failed
     */
    @Override
    public boolean isFinal(String name) {
        for (DFAState dfaState : States) 
        {
            if(dfaState.getName().equals(name))
            {
                if(dfaState.isFinalState)
                {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Return if state is a starting state
     * @param name - Name of state in FA
     * @return - true if success/false if failed
     */
    @Override
    public boolean isStart(String name) {
        for (DFAState dfaState : States) 
        {
            if(dfaState.getName().equals(name))
            {
                if(dfaState.isStartState)
                {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Adds a transition to the state with full information given
     * @param fromState - The state to add a transition to
     * @param toState - The state the transition should go to
     * @param onSymn - Symbol to transition to the next state on
     * @return true if success/false if fail
     */
    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        //Check if fromState exists
        boolean fromStateExists = false;
        for(DFAState dfaState : States)
        {
            if(dfaState.getName().equals(fromState))
            {
                fromStateExists = true;
            }
        }
        if(fromStateExists == false)
        {
            return false;
        }
        //Check if toState exists
        boolean toStateExists = false;
        for(DFAState dfaState : States)
        {
            if(dfaState.getName().equals(toState))
            {
                toStateExists = true;
            }
        }
        if(toStateExists == false)
        {
            return false;
        }
        //Check if the onSymb is part of the alphabet
        boolean onSymbExists = false;
        for(Character alphabet : Sigma)
        {
            if(onSymb == alphabet)
            {
                onSymbExists = true;
            }
        }
        if(onSymbExists == false)
        {
            return false;
        }
    
        for (DFAState dfaState : States) 
        {
            if(dfaState.getName().equals(fromState))
            {
                dfaState.addTransition(toState, onSymb);
                return true;
            }
        }
        return false;
    }
    /**
     * Swaps two transition characters without changing states
     * @param symb1 - The first symbol to swap
     * @param symb2 - Second symbol to swap with first
     * @return a new DFA with swapped transitions
     */
    @Override
    public DFA swap(char symb1, char symb2) {
        DFA swappedDFA = new DFA();
        String startState = "", finalState = "";
        if(symb1 == symb2)
        {
            return null;
        }
        //First add the states from the original to the new dfa, also tracking start and ending for moving over
        for(DFAState dfaState : States)
        {
            swappedDFA.addState(dfaState.getName());
            if(dfaState.isStartState)
            {
                startState = dfaState.getName();
            }
            if(dfaState.isFinalState)
            {
                finalState = dfaState.getName();
            }
        }
        swappedDFA.setStart(startState);
        swappedDFA.setFinal(finalState);
        swappedDFA.Sigma = Sigma;

        //Go through again, taking the transition. If a transition equals the first symbol, add the second and vise versa
        for(DFAState dfaState : States)
        {
            //System.out.println(dfaState.transitionList);
            
            //Move through array of the transitions for a state, or move through the hashmap for next states
            //Moves through hashmap of nextStates
            for(Map.Entry<String, ArrayList<Character>> entry : dfaState.nextStateMap.entrySet())
            {
                //System.out.println(entry);
                //Go through transition list
                for(Character transition : entry.getValue())
                {
                    if(transition.equals(symb1))
                    {
                        swappedDFA.addTransition(dfaState.getName(), entry.getKey(), symb2);
                        System.out.println(swappedDFA.States);
                    }
                    else if(transition.equals(symb2))
                    {
                        swappedDFA.addTransition(dfaState.getName(), entry.getKey(), symb1);
                        System.out.println(swappedDFA.States);
                    }
                    else
                    {
                        swappedDFA.addTransition(dfaState.getName(), entry.getKey(), transition);
                    }
                }
            }         
        }
        return swappedDFA;
    }


    /**
     * Not sure if need this method yet...
     * @param posToLookFor
     * @return
     */
    public Boolean addedToMapOrder_hasNext(int posToLookFor) 
    {
        for (DFAState dfaState : States) 
        {
            if(dfaState.addedToMapOrder_getPos() == posToLookFor)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Looks for the "next" state according to when it was added to the map
     * @param posToLookFor position added in the map to look for
     * @return DFAState of state found, or null if noState is found for that position
     * 
     * NOTE: the state's "positions" will start at one, not zero 
     */
    public DFAState addedToMapOrder_getNext(int posToLookFor) 
    {
        for (DFAState dfaState : States) 
        {
            if(dfaState.addedToMapOrder_getPos() == posToLookFor)
            {
                return dfaState;
            }
        }
        return null;
    }

    /**
     * Produces a string with all the information about the FA
     * @return a string with all the information about the FA
     */
    public String toString()
    {
        String retString = null;

        //add states
        Iterator<DFAState> itrS = States.iterator();
        retString = "Q={"; //
        while (itrS.hasNext()) {
            retString = retString + itrS.next();//
            if(itrS.hasNext())
            {
                retString = retString + " ";
            }
        }
        retString = retString + "}\n";

        //add alphabet
        Iterator<Character> itrA = Sigma.iterator();
        retString = retString + "Sigma = {"; //
        while (itrA.hasNext()) {
            retString = retString + itrA.next(); //
            if(itrA.hasNext())
            {
                retString = retString + " ";
            }
        }
        retString = retString + "}\n";


        //add transition table
        retString = retString + "delta =\n"; //
        //possible transitions
        Iterator<Character> itrA2 = Sigma.iterator();
        while (itrA2.hasNext()) {
            retString = retString + " " + itrA2.next(); //\t
        }
        retString = retString + "\n";
        //transitions on states
        Iterator<DFAState> itrS_Col = States.iterator();
        Iterator<Character> itrA_RowsReference;
        DFAState currState;
        for (int i = 1; i <= stateCounter; i++) {
            currState = addedToMapOrder_getNext(i);

            itrA_RowsReference = Sigma.iterator();
            retString = retString + "" + currState; //
            while (itrA_RowsReference.hasNext()) {
                retString = retString + " " + currState.findStateOnTransition(itrA_RowsReference.next());  //\t
            }
            retString = retString + "\n";
        }

        
        //add intial state
        retString = retString + "q0 = " + startState + "\n";

        //add final states
        Iterator<String> itrF = finalState.iterator();
        retString = retString + "F={"; //
        while (itrF.hasNext()) {
            retString = retString + itrF.next() + ""; //
        }
        retString = retString + "}\n";

        return retString;
        
    }
    
}
