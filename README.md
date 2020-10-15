# dot-parser
dot-parser provides a simple library for extracting FSM models from .dot files. 
The models come in the form of [automatalib](https://github.com/LearnLib/automatalib) instances. 
The formalisms (i.e. model types) it currently supports are:
* Mealy machines
* Deterministic Finite Automata (DFA)

The overriding goal is simplicity.
Parsing is done by *processors* supplied by the user.
These processors convert .dot labels into the desired input/output or label objects.
The processor interface is very restrictive, as are the supported .dot encodings. 
As the need arise we can generalize it/provide support for additional .dot encodings.
