package com.pfg666.dotparser.fsm;

import java.util.HashMap;
import java.util.Map;

import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.pfg666.dotparser.DotParser;
import com.pfg666.dotparser.exceptions.InitialStateNotFoundException;
import com.pfg666.dotparser.exceptions.MultipleInitialStatesFoundException;

import net.automatalib.automata.DeterministicAutomaton;
import net.automatalib.automata.MutableDeterministic;

public abstract class FSMDotParser<S, I, T, A extends DeterministicAutomaton<S, I, T>> extends DotParser<A>{

	public FSMDotParser() {
		
	}
	
	/**
	 * Helper function which extracts the states from a graph and adds them to the suppled 
	 * muttable automaton. 
	 * 
	 * @param graph - graph generated from .dot parsing
	 * @param muttable - a mutable FSM which is under construction
	 * @return mapping from graph nodes to states
	 */
	protected Map<Node, S> addStates(Graph graph, MutableDeterministic<S,I,T,?,?> muttable) {
		boolean initialStateFound = false;
		Map<Node, S> nodeToState = new HashMap<>(); 
		
		for (Node node : graph.getNodes(true)) {
			S state = muttable.addState();
			nodeToState.put(node, state);
			if (isInitialState(node)) {
				if (initialStateFound) {
					throw new MultipleInitialStatesFoundException();
				}
				initialStateFound = true;
				muttable.setInitialState(state);
			}
		}
		
		if (!initialStateFound) {
			throw new InitialStateNotFoundException();
		}
		return nodeToState;
	} 
	
	/**
	 * Checks whether a node corresponds to the initial state.
	 * @param node given node
	 * @return true if it does, false otherwise
	 */
	protected boolean isInitialState(Node node) {
		return node.getId().getId().equals("s0");
	}
}
