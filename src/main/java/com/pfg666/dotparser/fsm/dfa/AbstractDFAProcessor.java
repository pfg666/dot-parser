package com.pfg666.dotparser.fsm.dfa;

import com.alexmerz.graphviz.objects.Edge;

/**
 * Abstract DFA Processor class which simplifies processor creation.
 */
public abstract class AbstractDFAProcessor<L>  implements DFAProcessor<L> {
	
	public final L processLabel(Edge edge) {
		String labelString = parseEdgeLabel(edge);
		if (labelString != null) {
			return processDFALabel(labelString);
		}
		return null;
	}
	
	protected abstract L processDFALabel(String labelString);
}
