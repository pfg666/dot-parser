package com.pfg666.dotparser.fsm.mealy;

import com.alexmerz.graphviz.objects.Edge;
import com.pfg666.dotparser.exceptions.MalformedEdgeException;

import net.automatalib.commons.util.Pair;

/**
 * Abstract Mealy Processor class which simplifies processor creation.
 */
public abstract class AbstractMealyProcessor<I,O> implements MealyProcessor<I, O>{

	public final Pair<I, O> processInputOutput(Edge edge) {
		Pair<String, String> io = parseEdgeInputOutput(edge);
		if (io == null) {
			return null;
		}
		return processMealyInputOutput(io.getFirst(), io.getSecond());
	}
	
	protected abstract Pair<I, O> processMealyInputOutput(String input, String output);
	
	protected Pair<String, String> parseEdgeInputOutput(Edge edge) {
		String label = parseEdgeLabel(edge);
		if (label == null) {
			return null;
		}
		String[] ioStrings = label.split("/");
		if (ioStrings.length != 2) {
			throw new MalformedEdgeException(edge);
		}
		return new Pair<>(ioStrings[0], ioStrings[1]);
	}

}
