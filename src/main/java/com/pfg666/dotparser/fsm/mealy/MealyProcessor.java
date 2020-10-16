package com.pfg666.dotparser.fsm.mealy;

import com.pfg666.dotparser.fsm.Processor;

import net.automatalib.commons.util.Pair;

/**
 * A processor for Mealy machines.
 */
public interface MealyProcessor<I,O> extends Processor{
	
	/**
	 * Generates an input/output object pair from the input/output strings encoded in an edge label.
	 * Returning {@code null} instructs the parser to act as if the edge did not exist.
	 */
	public Pair<I,O> processInputOutput(String input, String output);
}
