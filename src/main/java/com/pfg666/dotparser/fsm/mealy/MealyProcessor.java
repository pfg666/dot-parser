package com.pfg666.dotparser.fsm.mealy;

import com.pfg666.dotparser.fsm.Processor;

/**
 * A processor for Mealy machines.
 */
public interface MealyProcessor<I,O> extends Processor{
	
	/**
	 * Generates an input object from an input string encoded in an edge label.
	 */
	public I processInput(String input);
	
	/**
	 * Generates an input object from an input string encoded in an edge label.
	 */
	public O processOutput(String output);
}
