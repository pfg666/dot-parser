package com.pfg666.dotparser.fsm.mealy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.pfg666.dotparser.exceptions.MalformedEdgeException;
import com.pfg666.dotparser.fsm.FSMConstants;
import com.pfg666.dotparser.fsm.FSMDotParser;

import net.automatalib.automata.transducers.impl.FastMealy;
import net.automatalib.automata.transducers.impl.FastMealyState;
import net.automatalib.automata.transducers.impl.MealyTransition;
import net.automatalib.words.Alphabet;
import net.automatalib.words.impl.ListAlphabet;

public class MealyDotParser<I,O> extends FSMDotParser<FastMealyState<O>, I, MealyTransition<FastMealyState<O>,O>, FastMealy<I,O>>{
	
	private MealyProcessor<I,O> processor;

	public MealyDotParser(MealyProcessor<I,O> processor) {
		this.processor = processor;
	}
	
	protected FastMealy<I, O> processGraph(Graph g) {
		Alphabet<I> inputAlphabet = buildAlphabet(g);
		FastMealy<I,O> muttable = new FastMealy<I,O>(inputAlphabet);
		Map<Node, FastMealyState<O>> nodeToState = super.addStates(g, muttable);
		List<Edge> edges = g.getEdges();
		
		for (Edge edge : edges) {
			String label = edge.getAttribute(FSMConstants.EDGE_LABEL);
			if (label == null) {
				continue;
			}
			String[] io = label.split("/");
			if (io.length != 2) {
				throw new MalformedEdgeException(edge);
			}
			I input = processor.processInput(io[0]);
			O output = processor.processOutput(io[1]);
			
			FastMealyState<O> sourceState = nodeToState.get(edge.getSource().getNode());
			FastMealyState<O> targetState = nodeToState.get(edge.getTarget().getNode()); 
			MealyTransition<FastMealyState<O>, O> transition = new MealyTransition<FastMealyState<O>,O>(targetState, output);
			muttable.setTransition(sourceState, input, transition);
		}

		return muttable;
	}
	
	private Alphabet<I> buildAlphabet(Graph g) {
		List<I> inputs = new ArrayList<>();
		List<Edge> edges = g.getEdges();
		for (Edge edge : edges) {
			String label = edge.getAttribute(FSMConstants.EDGE_LABEL);
			if (label == null) {
				continue;
			}
			String[] io = label.split("/");
			if (io.length != 2) {
				throw new MalformedEdgeException(edge);
			}
			I input = processor.processInput(io[0]);
			if (!inputs.contains(input)) {
				inputs.add(input);
			}
		}
		return new ListAlphabet<I>(inputs);
		
	}
	
}
