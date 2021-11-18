import java.io.*;
import java.util.*;

public class CSVMerger {

    public static final String COMMA = ",";

    public static void main(String[] args) throws IOException {

        List<File> inputFiles = new ArrayList<>();
        for(int i=0;i<args.length-1;i++){
            inputFiles.add(new File(args[i]));
        }

        //Validate input files
        if(!isValid(inputFiles)){
            System.out.println("The input files must contain same number of columns and the column names must match");
            return;
        }

        //Open the output file in append mode to append the content from multiple input files.
        FileWriter fileWriter = new FileWriter(args[args.length-1], true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        //Add the column header to output file.
        printWriter.println("email_hash,category,filename");

        for(File file : inputFiles){
            String fileName = file.getName();
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            //Skips header line in input file
            br.readLine();
            List<StringBuilder> list = new ArrayList<>();
            int lineCount = 1;
            readFileAndWriteToOutputFile(printWriter, fileName, br, list, lineCount);
            br.close();
        }
        printWriter.close();
    }

    private static void readFileAndWriteToOutputFile(PrintWriter printWriter, String fileName, BufferedReader br, List<StringBuilder> list, int lineCount) throws IOException {
        String line;
        //Reads the file in bunch of 100 lines
        while((line = br.readLine()) != null && lineCount <=100){
            StringBuilder sb = new StringBuilder(line).append(COMMA).append(fileName);
            list.add(sb);
            lineCount++;
            if(lineCount==100){
                appendToOutPutFile(list, printWriter);
                lineCount =1;
                list.clear();
            }
        }
        appendToOutPutFile(list, printWriter);
    }

    private static void appendToOutPutFile(List<StringBuilder> list, PrintWriter printWriter) {
        for(StringBuilder  line : list){
            printWriter.println(line);
        }
    }

    private static boolean isValid(List<File> inputFiles) throws IOException {
        Set<String> columns = extractHeaderColumns(inputFiles.get(0));
        for(int i=1;i<inputFiles.size();i++){
            Set<String> cols = extractHeaderColumns(inputFiles.get(i));
            boolean columnMatch = compareColumns(columns,cols);
            if(!columnMatch || cols.size()!= columns.size())
                return false;
        }
        return true;
    }

    private static Set<String> extractHeaderColumns(File inputFile) throws IOException {
        FileReader fileReader = new FileReader(inputFile);
        BufferedReader br = new BufferedReader(fileReader);
        String header = br.readLine();
        return new HashSet<String>(Arrays.asList(header.split(COMMA)));
    }

    private static boolean compareColumns(Set<String> columns, Set<String> cols) {
        for(String col: cols){
            if(!columns.contains(col)){
                return false;
            }
        }
        return true;
    }
}
