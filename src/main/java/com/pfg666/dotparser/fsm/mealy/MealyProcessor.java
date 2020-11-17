package com.pfg666.dotparser.fsm.mealy;

import com.alexmerz.graphviz.objects.Edge;
import com.pfg666.dotparser.fsm.Processor;

import net.automatalib.commons.util.Pair;

/**
 * A processor for Mealy machines.
 */
public interface MealyProcessor<I,O> extends Processor{
	
	/**
	 * Generates an input/output object pair from an edge.
	 * Returning {@code null} instructs the parser to act as if the edge did not exist.
	 */
	Pair<I,O> processInputOutput(Edge edge);
	
}
