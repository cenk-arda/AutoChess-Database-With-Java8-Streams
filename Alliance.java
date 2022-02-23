public class Alliance {
    private final String name;
    private final int requiredCount;
    private final int levelCount;

    public Alliance(String name, int requiredCount, int levelCount) {
        this.name = name;
        this.requiredCount = requiredCount;
        this.levelCount = levelCount;
    }

    public String getName() {
        return name;
    }

    public int getRequiredCount() {
        return requiredCount;
    }

    public int getLevelCount() {
        return levelCount;
    }

    //I added a to string method
    @Override
    public String toString() {
        return "Alliance{" +
                "name='" + name + '\'' +
                ", requiredCount=" + requiredCount +
                ", levelCount=" + levelCount+
                '}';
    }
}
