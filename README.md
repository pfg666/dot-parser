# dot-parser
dot-parser provides a simple library for extracting FSM models from .dot files. 
The models come in the form of [automatalib](https://github.com/LearnLib/automatalib) instances. 
The formalisms (i.e. model types) it currently supports are:
* Mealy machines
* Deterministic Finite Automata (DFA)

The overriding goal is simplicity.
Parsing is done by *processors* supplied by the user.
These processors convert .dot labels into the desired input/output or label objects.
The processor interface is somewhat restrictive, as are the supported .dot encodings. 
As the need arise we can generalize it/provide support for additional .dot encodings.

## UPDATE 2020-11-17
I became aware that .dot parsing has been added to automatalib in the [0.9.0 release](https://github.com/LearnLib/automatalib/releases/tag/automatalib-0.9.0).
It is better to use that library, since it does not depend on external .jars + it is more flexible. 
That said, I will keep this repository as a toy project. 
Not only that but some tools still depend on it. 
