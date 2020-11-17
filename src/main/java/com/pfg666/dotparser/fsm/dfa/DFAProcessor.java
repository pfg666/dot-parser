package com.pfg666.dotparser.fsm.dfa;

import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Node;
import com.pfg666.dotparser.fsm.FSMConstants;
import com.pfg666.dotparser.fsm.Processor;

public interface DFAProcessor<L> extends Processor{
	
	/**
	 * Generates a DFA label object from an edge.
	 * Returning {@code null} tells the parser to act as if the edge did not exist
	 */
	L processLabel(Edge edge);
	
	
	/**
	 * Determines whether the state identified by this node is accepting/rejecting.
	 * Returns <code>null</code> if node cannot be parsed.
	 */
	default Boolean isAccepting(Node node) {
		String shapeVal = node.getAttribute(FSMConstants.NODE_ATTR_SHAPE);
		if (shapeVal != null) {
			if (FSMConstants.NODE_ATTR_SHAPE_ACCEPTING.equals(shapeVal)) {
				return true;
			} else {
				if (FSMConstants.NODE_ATTR_SHAPE_REJECTING.equals(shapeVal)) {
					return false;
				}
			}
			return null;
		}
		return false;
	}
}