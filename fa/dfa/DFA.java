package fa.dfa;

import java.util.ArrayList;
import java.util.HashSet;
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
        for(DFAState dfaState : States)
        {
            if(dfaState.getName().equals(name))
            {
                //Searching set, have found the correct state
                dfaState.toggleFinalState();
                return true;
            }
        }
        finalState.add(name);
        return false;
    }

    @Override
    public boolean setStart(String name) {
        //Will need to check through all stored states and remove start so that only one state is the starting state
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
        //Update the start state tracker
        startState = name;
        return false;
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
        DFAState currentState;
        //Parse through and get the starting state from the dfa
        for(DFAState dfaState : States)
        {
            if(dfaState.isStartState)
            {
                currentState = dfaState;
            }
        }
        //Move through the characters of the string
        for(int i = 0; i < s.length(); i++)
        {
            char current = s.charAt(i);
            //Parse through the 
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
    
}
