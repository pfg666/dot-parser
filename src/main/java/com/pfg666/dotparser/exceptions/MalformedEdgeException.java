package com.pfg666.dotparser.exceptions;

import com.alexmerz.graphviz.objects.Edge;

public class MalformedEdgeException extends RuntimeException{
	public MalformedEdgeException(Edge edge) {
		super(String.format("Edge between %s and %s is malformed \n%s", 
				edge.getSource().getPort(), edge.getTarget().getPort()));
	}
	
}
