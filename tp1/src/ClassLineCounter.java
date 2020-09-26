import java.io.*;
public class ClassLineCounter {
    protected void run(String directory, String extension) throws IOException {
        File dir = new File(directory);
        if(dir.exists()) {
            int lineSum = this.processDirectory(dir,extension)[0];
            int commentSum = this.processDirectory(dir,extension)[1];
            float density = (float)commentSum / (float)lineSum;
            System.out.println("Lines Of Code with comment Line : " + lineSum);
            System.out.println("Lines Of Only Comments : " + commentSum);
            System.out.println("Density Of Comments(CLOC / LOC) : " + density);
        } else {
            System.err.printf("%s there is no directory", directory);
        }
    }
    private int[] processDirectory(File directory, String extension) throws IOException {
        File[] files = directory.listFiles();
        int lineCount = 0;
        int commentCount = 0;
        int[] count = new int[2];
        for(File file : files) {
            //if it's directory, it will be recursive call.
            if(file.isDirectory()) {
                lineCount += this.processDirectory(file, extension)[0];
                commentCount += this.processDirectory(file, extension)[1];
            } else {
                // if it's normal file, not directory
                if(file.getName().toLowerCase().endsWith("." + extension.toLowerCase())) {
                    lineCount += this.countLines_LOC(file);
                    commentCount += this.countComments_CLOC(file);
                }
            }
        }
        count[0] = lineCount;
        count[1] = commentCount;
        return count;
    }
    private int countLines_LOC(File file) throws IOException {
        // counting all the line except the empty line.
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        int count = 0;
        String line= "";
        while((line = bufferedReader.readLine()) != null) {
            if(line.trim().isEmpty()) {
                continue;
            } else {
                count++;
            }
        }
        return count;
    }
    private int countComments_CLOC(File file) throws IOException {
        // counting only comments
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        int count = 0;
        String line= "";
        while((line = bufferedReader.readLine()) != null) {
//            if(line.trim().contains("/**")) {
//                count++;
//                if(!line.trim().contains("*/")) {
//                    while(!(line = bufferedReader.readLine()).contains("*/")) {
//                        count++;
//                    }
//                }
//                count++; // because the last line including "*/" need to be counted.
//            } else if(line.trim().contains("//")) {
//                System.out.println(line + count);
//                count++;
//            }

            if(line.trim().contains("//")) {  // need to change from contain to matches(for using regex)
                count++;
                //                System.out.println(line + count);
            }
        }
        return count;
    }
}

