package com.pfg666.dotparser.fsm.mealy;

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
