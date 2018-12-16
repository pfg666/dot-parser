package com.pfg666.dotparser.fsm.mealy;

import com.pfg666.dotparser.fsm.Processor;

public interface MealyProcessor<I,O> extends Processor{
	public I processInput(String input);
	public O processOutput(String output);
}
