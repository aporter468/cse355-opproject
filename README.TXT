CSE355 Optional Project
Milestone 1
Alexandra Porter

Running the project:
1. Execute FA_reader.jar (or the static main() method within the Main class)
	a. In a terminal window, enter “java -jar FA_reader.jar”
2. Enter the filename at the prompt and press enter. The automata file must be in the same directory as the .jar file (or java class files if not using the .jar).
3. If the file is successfully read:
	a. The automaton contained, M, will be read and the alphabet, start, and accept states displayed.
 	b. The automaton will be converted to a DFA, D and the new states will be shown,  with the previous states they represent and the new transitions.
	c. D is converted to the complement, D’ and the accept states of D’ are shown.
	d. The first string found to be in L(D’) will then be outputted.

4. When prompted, enter y or n and “enter” to continue testing or terminate the program.