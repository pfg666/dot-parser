package com.pfg666.dotparser.fsm;

import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Node;

/**
 * Process elements of a .dot graph turning them into model components (inputs/outputs for Mealy machines, or labels for DFAs). 
 */
public interface Processor {
	
	/**
	 * Indicates whether a state is the initial state.
	 */
	default boolean isInitialState(Node node) {
		return "s0".equals(node.getId().getId()) || "red".equals(node.getAttribute("color"));
	} 
	
	/**
	 * Indicates whether a state should be ignored, meaning, no corresponding automaton state is created for it.
	 */
	default boolean isIgnoredState(Node node) {
		return "__start0".equals(node.getId().getId());
	}
	
	/**
	 * Parses from an edge the string encoding the transition
	 */
	default String parseEdgeLabel(Edge edge) {
		return edge.getAttribute(FSMConstants.EDGE_ATTR_LABEL);
	}
	
}
