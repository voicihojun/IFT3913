import java.io.*;
import java.sql.SQLOutput;

public class ClassLineCounter {
    private void run(String directory, String extension) {
        File dir = new File(directory);
        if(dir.exists()) {
            int lineSum = this.processDirectory(dir,extension);
            System.out.println(lineSum);
        } else {
            System.err.printf("%s there is no directory", directory);
        }
    }

    private int processDirectory(File directory, String extension) {
        File[] files = directory.listFiles();

        int lineCount = 0;

        for(File file : files) {
            //if it's directory, it will be recursive call.
            if(file.isDirectory()) {
                lineCount += this.processDirectory(file, extension);
            } else {  // if it's normal file, not directory
                if(file.getName().toLowerCase().endsWith("." + extension.toLowerCase())) {
                    lineCount += this.countLines(file);
                }
            }
        }
        return lineCount;
    }

    private int countLines(File file) {
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            int count = 0;
            while(bufferedReader.readLine() != null) {
                count++;
            }
            return count;
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
            return 0;
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex2) {

            }
        }
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Usage: java LineCounter [directory] [extension]");
            System.exit(1);
        }

        ClassLineCounter classLineCounter = new ClassLineCounter();
        classLineCounter.run(args[0], args[1]);

        System.exit(0);
    }

}
