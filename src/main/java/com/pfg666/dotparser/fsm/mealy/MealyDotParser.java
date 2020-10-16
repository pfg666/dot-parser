package com.pfg666.dotparser.fsm.mealy;

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
import net.automatalib.commons.util.Pair;
import net.automatalib.words.Alphabet;
import net.automatalib.words.impl.SimpleAlphabet;

public class MealyDotParser<I,O> extends FSMDotParser<FastMealyState<O>, I, MealyTransition<FastMealyState<O>,O>, FastMealy<I,O>>{
	
	private MealyProcessor<I,O> processor;

	public MealyDotParser(MealyProcessor<I,O> processor) {
		this.processor = processor;
	}
	
	protected FastMealy<I, O> processGraph(Graph g) {
		Alphabet<I> inputAlphabet = new SimpleAlphabet<I>();
		FastMealy<I,O> muttable = new FastMealy<I,O>(inputAlphabet);
		Map<Node, FastMealyState<O>> nodeToState = super.addStates(g, muttable);
		List<Edge> edges = g.getEdges();
		
		for (Edge edge : edges) {
			String label = edge.getAttribute(FSMConstants.EDGE_ATTR_LABEL);
			if (label == null) {
				continue;
			}
			String[] io = label.split("/");
			if (io.length != 2) {
				throw new MalformedEdgeException(edge);
			}
			Pair<I,O> ioPair = processor.processInputOutput(io[0], io[1]);
			if (ioPair != null) {
				I input = ioPair.getFirst();
				O output = ioPair.getSecond();
				if (!muttable.getInputAlphabet().contains(input)) {
					muttable.addAlphabetSymbol(input);
				}
				FastMealyState<O> sourceState = nodeToState.get(edge.getSource().getNode());
				FastMealyState<O> targetState = nodeToState.get(edge.getTarget().getNode()); 
				MealyTransition<FastMealyState<O>, O> transition = new MealyTransition<FastMealyState<O>,O>(targetState, output);
				muttable.setTransition(sourceState, input, transition);
			}
		}

		return muttable;
	}
}
