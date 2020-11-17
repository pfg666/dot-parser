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

Note that .dot parsing has been added to [automatalib-0.9.0](https://github.com/LearnLib/automatalib/releases/tag/automatalib-0.9.0).
It is better to utilize that library, since it does not depend on external .jars + it is more flexible. 
That said, I will keep this repository for old time sakes, for improving my coding skills plus at present there are some tools which still depend on it.
