# This is a simple CSV merger tool that merges multiple CSV files that are present in fixture directory.

# Assumptions
1. All input files contains unique columns.
2. The columns are maintained in same order in all input files.

# Validation of input files
1. All input files must contain same number of columns in the header
2. All the columns names in all input files must match.

# How to run the tool

1. Place your input files in fixtures directory
2. cd in to src directory inside csv-merger project
3. Compile the java program using `javac CSVMerger.java`
4. Execute the java program using the following command
   `java CSVMerger ../fixtures/accessories.csv ../fixtures/clothing.csv ../fixtures/household_cleaners.csv combined.csv`
5. combined.csv file will be generated in src directory
