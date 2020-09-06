This Repository contains Computer Architecture Lab (CS311) Assignment-0 Solutions and End-Report.

Steps to reproduce Whole Experiment

1. cd to **’ca assignment 0’** folder.
2. Run command **$ javac *.java**
3. To run series of experiments with P-value from 0.00 to 0.95 (Steps of 0.05) and Width value from 5 to
100 (Steps of 5), run command **$ java -cp . Main**
4. To run an experiment with command-line input as number of iterations, P-value, Width,
run command **$ java -cp . Experiment numExp pvalue width**
e.g. **$ java -cp . Experiment 1000 57.3 32**
5. Now, since the ouput of series of experiments is stored in 'ca_assignment_0/output.csv' file. To plot 3D Plot for the values obtained, run command **$ cd ..** to change current directory to 'Assignment0', and run command **$ python3 outputgraph.py** or **$ python outputgraph.py**. A Graph Window will open, having an option to save the graph as an image.