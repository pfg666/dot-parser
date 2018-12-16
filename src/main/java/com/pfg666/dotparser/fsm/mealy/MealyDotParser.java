package com.pfg666.dotparser.fsm.mealy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.pfg666.dotparser.exceptions.InitialStateNotFoundException;
import com.pfg666.dotparser.exceptions.MalformedEdgeException;
import com.pfg666.dotparser.exceptions.MultipleInitialStatesFoundException;
import com.pfg666.dotparser.fsm.FSMConstants;
import com.pfg666.dotparser.fsm.FSMDotParser;

import net.automatalib.automata.transout.impl.FastMealy;
import net.automatalib.automata.transout.impl.FastMealyState;
import net.automatalib.automata.transout.impl.MealyTransition;
import net.automatalib.words.impl.ListAlphabet;

public class MealyDotParser<I,O> extends FSMDotParser<FastMealyState<O>, I, MealyTransition<FastMealyState<O>,O>, FastMealy<I,O>>{
	
	private MealyProcessor<I,O> processor;

	public MealyDotParser(MealyProcessor<I,O> processor) {
		this.processor = processor;
	}
	
	protected FastMealy<I, O> processGraph(Graph g) {
		FastMealy<I,O> muttable = new FastMealy<I,O>(new ListAlphabet<I>(new ArrayList<I>()));
		List<Node> nodes = g.getNodes(false);
		Map<Node, FastMealyState<O>> nodeToState = new HashMap<>(); 
		boolean initialStateFound = false;
		
		for (Node node : nodes) {
			FastMealyState<O> state = muttable.addState();
			nodeToState.put(node, state);
			if (isInitialState(node)) {
				if (initialStateFound) {
					throw new MultipleInitialStatesFoundException();
				}
				initialStateFound = true;
				muttable.setInitialState(state);
			}
		}
		
		if (!initialStateFound) {
			throw new InitialStateNotFoundException();
		}
		
		List<Edge> edges = g.getEdges();
		for (Edge edge : edges) {
			String label = edge.getAttribute(FSMConstants.EDGE_LABEL);
			String[] io = label.split("/");
			if (io.length != 2) {
				throw new MalformedEdgeException(edge);
			}
			I input = processor.processInput(io[0]);
			O output = processor.processOutput(io[1]);
			if (!muttable.getInputAlphabet().contains(input)) {
				muttable.addAlphabetSymbol(input);
			}
			
			FastMealyState<O> sourceState = nodeToState.get(edge.getSource().getNode());
			FastMealyState<O> targetState = nodeToState.get(edge.getTarget().getNode()); 
			MealyTransition<FastMealyState<O>, O> transition = new MealyTransition<FastMealyState<O>,O>(targetState, output);
			muttable.setTransition(sourceState, input, transition);
		}

		return muttable;
	}
	
}
