import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MethodCounter {
    public static String methodPattern = "^[\\s]*(public|private|protected|static|void)+.*\\{$";
    public static String classPattern = ".*[\\s](class|enum|interface)[\\s].*";
    public static String constructorPattern = ".*[\\s](public|private|protected)[\\s][\\w]*\\([\\w\\s<>,]*\\)\\s?\\{";
    public static String commentPattern = ".*(\\/\\*([^*]|[\\r\\n]|(\\*+([^*\\/]|[\\r\\n])))*\\*+\\/).*|.*(\\/\\/).*";
    public static String openBracePattern = ".*\\s\\{\\s?.*";
    public static String invalidOpenBracePattern = ".*\".*\\{.*\" .*";
    public static String closeBracePattern = ".*\\s\\}\\s?.*";
    public static String invalidCloseBracePattern = ".*\".*\\}.*\".*";
    public static String ccPattern = "(.*if.*|.*while.*)";
    public static String invalidCcPattern = ".*(\\/\\/)\\s*(if|while).*";
    public static String commentPatternForMultiLines = ".*(\\/\\*([^*]|[\\r\\n]|(\\*+([^*\\/]|[\\r\\n])))*\\*+\\/).*";

    public static ArrayList<MethodCountData> countDataList = new ArrayList<>();

    public ArrayList<MethodCountData> getData(String filePath) {
        File file = new File(filePath);

        ArrayList<MethodCountData> countDataList = new ArrayList<>();
        try {
            if(file.isDirectory()) {
                countDataList = generatedDataByDirectory(file);
            } else {
                countDataList = generateData(file);
            }
        } catch (IOException e) {
            System.out.println("MethodCounter.getData(filePath"+filePath+")" + e.getMessage()) ;
        }
        return countDataList;
    }

    private ArrayList<MethodCountData> generatedDataByDirectory(File directory) throws IOException {
        File[] files = directory.listFiles();
        String path2;

        for(File file : files) {
            if(file.isDirectory()) {
                generatedDataByDirectory(file);
            } else {
                if(generateData(file) != null) {
                    countDataList.addAll(generateData(file));
                }

            }
        }

        return countDataList;
    }

    private boolean braceIsValid(int[] braces) {
        int open = braces[0];
        int close = braces[1];
        return open == close;
    }

    private ArrayList<MethodCountData> generateData(File file) throws IOException {
        if(!file.getName().endsWith("java")) {
            return null;
        }
        ArrayList<MethodCountData> methodDataList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String line = "";
        int cc = 1;
        int open = 0;
        int close = 0;
        int methodLineOfCode = 0;
        int methodLineOfComment = 0;
        String className = "";
        int[] braces = {open, close};

        while((line = bufferedReader.readLine()) != null) {
            String methodName = "";

            if(!line.matches(commentPatternForMultiLines) && line.matches(ccPattern) && !line.matches(invalidCcPattern)) {
                cc++;
            }

            if(!line.matches(commentPattern) && line.matches(classPattern)) {
                String[] splitLine = line.split(" ");
                for(int i = 0; i < splitLine.length; i++) {
                    if (className.isEmpty() && splitLine[i].equals("class")) {
                        className = splitLine[i+1].replace("{", "").trim();
                        break;
                    }
                }
            }

            if(line.matches(methodPattern) && !line.matches(classPattern) && !line.matches(constructorPattern)) {
                String[] methodNameSplit = line.trim().split(" ");

                for(String name: methodNameSplit) {
                    if(name.contains("(")){
                        methodName = name.substring(0, name.lastIndexOf("("));
                    }
                }

                methodLineOfCode++;
                open++;
                braces[0] = open;

                if(line.matches(commentPattern)) {
                    methodLineOfComment++;
                }

                while(!braceIsValid(braces)) {
                    if((line = bufferedReader.readLine()) != null) {
                        if(line.matches(openBracePattern) && !line.matches(invalidOpenBracePattern)) {
                            open++;
                            braces[0] = open;
                        }
                        if(line.matches(closeBracePattern) && !line.matches(invalidCloseBracePattern)) {
                            close++;
                            braces[1] = close;
                        }
                        if(!line.isEmpty()) {
                            methodLineOfCode++;
                        }
                        if(!line.isEmpty() && line.matches(commentPattern)) {
                            methodLineOfComment++;
                        }
                    }
                }

                MethodCountData data = new MethodCountData(
                        file.getPath(), className, methodName, methodLineOfCode, methodLineOfComment, cc
                );
                methodDataList.add(data);

                open = 0;
                close = 0;
                methodLineOfComment = 0;
                methodLineOfCode = 0;
                braces[0] = open;
                braces[1] = close;

            }
        }
        return methodDataList;
    }

}
