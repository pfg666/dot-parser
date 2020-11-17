package com.pfg666.dotparser.fsm.dfa;

import java.util.List;
import java.util.Map;

import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.pfg666.dotparser.exceptions.MalformedNodeException;
import com.pfg666.dotparser.fsm.FSMDotParser;

import net.automatalib.automata.fsa.impl.FastDFA;
import net.automatalib.automata.fsa.impl.FastDFAState;
import net.automatalib.words.Alphabet;
import net.automatalib.words.impl.SimpleAlphabet;

public class DFADotParser<L> extends FSMDotParser<DFAProcessor<L>, FastDFAState, L, FastDFAState, FastDFA<L>>{
	

	public DFADotParser(DFAProcessor<L> processor) {
		super(processor);
	}
	
	protected FastDFA<L> processGraph(Graph g) {
		Alphabet<L> inputAlphabet = new SimpleAlphabet<>();
		FastDFA<L> muttable = new FastDFA<L>(inputAlphabet);
		Map<Node, FastDFAState> nodeToState = super.addStates(g, muttable);
		
		// making accepting/rejecting
		for (Map.Entry<Node, FastDFAState> entry : nodeToState.entrySet()) {
			Boolean acc = getProcessor().isAccepting(entry.getKey());
			if (acc != null) {
				entry.getValue().setAccepting(acc.booleanValue());
			} else {
				throw new MalformedNodeException(entry.getKey(), "Could not parse node");
			}
		}
		
		List<Edge> edges = g.getEdges();
		
		for (Edge edge : edges) {
			L label = getProcessor().processLabel(edge);
			if (label != null) {
				if (!muttable.getInputAlphabet().contains(label)) {
					muttable.addAlphabetSymbol(label);
				}
				FastDFAState sourceState = nodeToState.get(edge.getSource().getNode());
				FastDFAState targetState = nodeToState.get(edge.getTarget().getNode()); 
				muttable.setTransition(sourceState, label, targetState);
			}
		}
		
		return muttable;
	}
}