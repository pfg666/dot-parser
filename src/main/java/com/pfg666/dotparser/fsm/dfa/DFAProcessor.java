package com.pfg666.dotparser.fsm.dfa;

import com.pfg666.dotparser.fsm.Processor;

public interface DFAProcessor<L> extends Processor{
	
	/**
	 * Generates an label object from an input string encoded in an edge label.
	 */
	public L processLabel(String input);
	
}