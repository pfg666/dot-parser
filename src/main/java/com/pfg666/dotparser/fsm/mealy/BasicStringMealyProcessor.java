package com.pfg666.dotparser.fsm.mealy;

/**
 * A basic processor for building Mealy machines whose inputs and outputs 
 * are equal to the i/o strings in graph edges.  
 */
public class BasicStringMealyProcessor implements MealyProcessor<String, String>{

	@Override
	public String processInput(String input) {
		return input;
	}

	@Override
	public String processOutput(String output) {
		return output;
	}

}
