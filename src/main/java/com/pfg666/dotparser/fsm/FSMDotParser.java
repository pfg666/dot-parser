package com.pfg666.dotparser.fsm;

import com.alexmerz.graphviz.objects.Node;
import com.pfg666.dotparser.DotParser;

import net.automatalib.automata.DeterministicAutomaton;

public abstract class FSMDotParser<S, I, T, A extends DeterministicAutomaton<S, I, T>> extends DotParser<A>{

	public FSMDotParser() {
		
	}
	
	
	// this can be factored out
	protected boolean isInitialState(Node node) {
		return node.getId().getId().equals("s0");
	}

}
