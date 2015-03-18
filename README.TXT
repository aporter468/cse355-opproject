CSE355 Optional Project
Milestone 1
Alexandra Porter

Running the project:
1. Execute FA_reader.jar (or the static main() method within the Main class)
    a. In a command line, enter “java -jar FA_reader.jar” or “java Main” from within the project directory.

2. Enter the full filename, such as exmp01_M1.txt, at the prompt and press enter. The automata file must be in the same directory as the .jar file and java class files.

3. If the file is successfully read:
    a. The automaton contained, M, will be read and the alphabet, start, and accept states displayed.
    b. The automaton will be converted to a DFA, D and the new states will be shown,  with the previous states they represent and the new transiti  ons. 
    This is run even if the original was a DFA because it creates an automaton based on reachable states.
    c. D is converted to the complement, D’ and the accept states of D’ are shown.
    d. The first string found to be in L(D’) will then be outputted.

4. When prompted, enter y or n and “enter” to continue testing or terminate the program.