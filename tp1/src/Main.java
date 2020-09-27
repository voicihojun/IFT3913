import java.io.IOException;
public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length != 2) {
            System.out.println("Usage: java LineCounter [directory] [extension]");
            System.exit(1);
        }
        ClassLineCounter classLineCounter = new ClassLineCounter();
        String[] classData = classLineCounter.run(args[0], args[1]);
        WriteCSV writeCSV = new WriteCSV();
        String classLabel ="chemin#class#classe_LOC#classe_CLOC#classe_DC";
        String methodeLabel = "chemin#class#methode#methode_LOC#methode_CLOC#methode_DC";
        writeCSV.writeDataToCsv(classLabel, classData,"/Users/hojunhwang/Documents/udem/IFT3913/tp/src/classes.csv");
        writeCSV.writeDataToCsv(methodeLabel, classData,"/Users/hojunhwang/Documents/udem/IFT3913/tp/src/methodes.csv");
        System.exit(0);

    }
}
