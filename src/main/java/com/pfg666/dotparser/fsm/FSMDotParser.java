package com.pfg666.dotparser.fsm;

import java.util.HashMap;
import java.util.Map;

import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.pfg666.dotparser.DotParser;
import com.pfg666.dotparser.exceptions.InitialStateNotFoundException;
import com.pfg666.dotparser.exceptions.MultipleInitialStatesFoundException;

import net.automatalib.automata.DeterministicAutomaton;
import net.automatalib.automata.MutableAutomaton;
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
	protected Map<Node, S> addStates(Graph graph, MutableAutomaton<S,I,T,?,?> muttable) {
		boolean initialStateFound = false;
		boolean isDeterministic = muttable instanceof MutableDeterministic;
		Map<Node, S> nodeToState = new HashMap<>(); 
		
		for (Node node : graph.getNodes(true)) {
			if (!isInitialIndicator(node)) { 
				S state = muttable.addState();
				nodeToState.put(node, state);
				if (isInitialState(node)) {
					if (initialStateFound) {
						throw new MultipleInitialStatesFoundException();
					}
					initialStateFound = true;
					if (isDeterministic) {
						((MutableDeterministic<S,I,T,?,?>) muttable).setInitialState(state);
					}
				}
			}
		}
		
		if (isDeterministic && !initialStateFound) {
			throw new InitialStateNotFoundException();
		}
		return nodeToState;
	} 
	
	protected boolean isInitialIndicator(Node node) {
		return node.getId().getId().equals("__start0");
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
