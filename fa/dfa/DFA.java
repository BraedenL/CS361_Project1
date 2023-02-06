package fa.dfa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fa.State;

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
    //Replace this with transition table variable

    public DFA()
    {
        Sigma = new HashSet<Character>();
        States = new HashSet<DFAState>();
        startState = "";
        finalState = new HashSet<String>(); 
        //Initialize transition table
    }


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
        States.add(newState);
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        //We are allowed to have multiple final states, so don't need to check if others are final like the start state function
        //Apperently we only having one final state with this code
        for(DFAState dfaState : States)
        {
            if(dfaState.isFinalState == true)
            {
                return false;
            }
        }
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

    @Override
    public boolean setStart(String name) {
        //Apperently we dont need to swap which states are starting once one has been initiated
        for(DFAState dfaState : States)
        {
            if(dfaState.checkStartStatus() == true)
            {
                return false;
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

    @Override
    public Set<Character> getSigma() {
        return Sigma;
    }

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

    @Override
    public DFA swap(char symb1, char symb2) {
        DFA swappedDFA = new DFA();

        //First add the states from the original to the new dfa
        for(DFAState dfaState : States)
        {
            swappedDFA.addState(dfaState.getName());
        }
        //Go through again, taking the transition. If a transition equals the first symbol, add the second and vise versa
        for(DFAState dfaState : States)
        {
            //Move through array of the transitions for a state, or move through the hashmap for next states
            //Moves through hashmap of nextStates
            for(Map.Entry<String, ArrayList<Character>> entry : dfaState.nextStateMap.entrySet())
            {
                //Go through transition list
                for(Character transition : entry.getValue())
                {
                    if(transition.equals(symb1))
                    {
                        swappedDFA.addTransition(dfaState.getName(), entry.getKey(), symb2);
                    }
                    if(transition.equals(symb2))
                    {
                        swappedDFA.addTransition(dfaState.getName(), entry.getKey(), symb1);
                    }
                }
            }         
        }
        return swappedDFA;
    }


    public String toString()
    {
        String retString = null;

        //add states
        Iterator<DFAState> itrS = States.iterator();
        retString = "Q = { ";
        while (itrS.hasNext()) {
            retString = retString + itrS.next() + " ";
        }
        retString = retString + "}\n";

        //add alphabet
        Iterator<Character> itrA = Sigma.iterator();
        retString = retString + "Sigma = { ";
        while (itrA.hasNext()) {
            retString = retString + itrA.next() + " ";
        }
        retString = retString + "}\n";


        //add transition table
        retString = retString + "delta =\n ";
        //possible transitions
        Iterator<Character> itrA2 = Sigma.iterator();
        while (itrA2.hasNext()) {
            retString = retString + "\t" + itrA2.next();
        }
        retString = retString + "\n";
        //transitions on states
        Iterator<DFAState> itrS_Col = States.iterator();
        Iterator<Character> itrA_RowsReference;
        DFAState currState;
        while (itrS_Col.hasNext()) {
            currState = itrS_Col.next();
            itrA_RowsReference = Sigma.iterator();
            retString = retString + " " + currState;
            while (itrA_RowsReference.hasNext()) {
                retString = retString + "\t" + currState.findStateOnTransition(itrA_RowsReference.next());
            }
            retString = retString + "\n";
        }

        
        //add intial state
        retString = retString + "q0 = " + startState + "\n";

        //add final states
        Iterator<String> itrF = finalState.iterator();
        retString = retString + "F = { ";
        while (itrF.hasNext()) {
            retString = retString + itrF.next() + " ";
        }
        retString = retString + "}";

        return retString;
        
    }
    
}
