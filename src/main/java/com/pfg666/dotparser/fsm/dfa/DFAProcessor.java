package com.pfg666.dotparser.fsm.dfa;

import com.pfg666.dotparser.fsm.Processor;

public interface DFAProcessor<L> extends Processor{
	
	/**
	 * Generates a DFA label object from a label string encoded in a the label of an edge.
	 * Returning {@code null} tells the parser to act as if the edge did not exist
	 */
	public L processLabel(String labelString);
	
}