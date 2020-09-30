public class Methode extends Classe {
    private String methodName;
    public Methode(String path, String className, String methodName, int totalLine, int lineOfComment) {
        super(path, className, totalLine, lineOfComment);
        this.methodName = methodName;
    }
}
