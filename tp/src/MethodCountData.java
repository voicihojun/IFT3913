public class MethodCountData {
    private String classPath;
    private String className;
    private String methodName;
    private int countLineOfCode;
    private int countLineOfComment;
    private float density;
    private int cyclomaticComplexity;
    private float methodeBc;

    public MethodCountData(String classPath, String className, String methodName, int countLineOfCode, int countLineOfComment, int cyclomaticComplexity) {
        this.classPath = classPath;
        this.className = className;
        this.methodName = methodName;
        this.countLineOfCode = countLineOfCode;
        this.countLineOfComment = countLineOfComment;
        this.density = (float) countLineOfComment / (float) countLineOfCode;
        this.cyclomaticComplexity = cyclomaticComplexity;
        this.methodeBc = density / (float) cyclomaticComplexity;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
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

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(int cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public float getMethodeBc() {
        return methodeBc;
    }

    public void setMethodeBc(float methodeBc) {
        this.methodeBc = methodeBc;
    }

    public String toString() {
        return "::FilePath:: " + getClassPath() +"\n"
                + "::Class name:: "+ getClassName() + "\n"
                + "::Method name:: "+ getClassName() + "\n"
                + "::Methode_LOC:: "+ getCountLineOfCode() + "\n"
                + "::Methode_CLOC:: "+ getCountLineOfComment() + "\n"
                + "::Methode_DC:: " + getDensity() + "\n"
                + "::CC:: " + getCyclomaticComplexity() + "\n"
                + "::Methode_BC:: " + getMethodeBc();
    }
}
