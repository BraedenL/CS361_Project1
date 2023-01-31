package fa.dfa;

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


    @Override
    public boolean addState(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setFinal(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setStart(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void addSigma(char symbol) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean accepts(String s) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<Character> getSigma() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public State getState(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isFinal(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isStart(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
