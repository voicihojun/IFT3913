
import java.io.IOException;

public class Main {
    /**
     *
     * @param args  mettre un répertoire à checker, java
     *              par exemple, apres avoir fait javac *java,
     *              tapez  "java Main [chemin] java"
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        if(args.length != 2) {
            System.out.println("Usage: java Main [directory] [extension]");
            System.exit(1);
        }
        String filePath = args[0];
        String extension = args[1];

        if(!extension.isEmpty() && !extension.equals("java")) {
            System.out.println("Wrong extension");
            System.exit(1);
        }

        Counter counter = new Counter();
        counter.run(filePath);
    }
}
// javac -cp /Users/hojunhwang/Downloads/jfreechart-1.0.19.jar *java
