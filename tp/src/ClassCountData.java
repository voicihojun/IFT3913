public class ClassCountData {
    private String classPath;
    private String className;
    private int countLineOfCode;
    private int countLineOfComment;
    private float density;

    public ClassCountData(String classPath, String className, int countLineOfCode, int countLineOfComment) {
        this.countLineOfCode = countLineOfCode;
        this.countLineOfComment = countLineOfComment;
        this.className = className;
        this.classPath = classPath;
        this.density = (float) countLineOfComment / (float) countLineOfCode;

    }

    public int getCountLineOfCode() {
        return countLineOfCode;
    }

    public void setCountLineOfCode(int countLineOfCode) {
        this.countLineOfCode = countLineOfCode;
    }

    public int getCountLineOfComment() {
        return countLineOfComment;
    }

    public void setCountLineOfComment(int countLineOfComment) {
        this.countLineOfComment = countLineOfComment;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public String toString() {
        return "::FilePath:: " + getClassPath() +"\n"
                + "::Class name:: "+ getClassName() + "\n"
                + "::Class_LOC:: "+ getCountLineOfCode() + "\n"
                + "::Class_CLOC:: "+ getCountLineOfComment() + "\n"
                + "::Class_DC:: " + getDensity();
    }
}