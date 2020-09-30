public class Classe {
    private String className;
    private String path;
    private int lineOfClass;
    private int lineOfComment;
    private float density;

    public Classe(String path, String className, int lineOfClass, int lineOfComment) {
        this.path = path;
        this.className = className;
        this.lineOfClass = lineOfClass;
        this.lineOfComment = lineOfComment;
        this.density = (float) lineOfComment / (float) lineOfClass;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getLineOfClass() {
        return lineOfClass;
    }

    public void setLineOfClass(int lineOfClass) {
        this.lineOfClass = lineOfClass;
    }

    public int getLineOfComment() {
        return lineOfComment;
    }

    public void setLineOfComment(int lineOfComment) {
        this.lineOfComment = lineOfComment;
    }
}
