public class Node {
    private boolean isLeaf;
    private String sequence;
    private Integer level;
    private Node[] children;

    public Node(boolean isLeaf, String sequence, Integer level) {
        setIsLeaf(isLeaf);
        setSequence(sequence);
        setLevel(level);
    }
    //setters
    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
        if (isLeaf) {
            setChildren(null);
        } else {
            setChildren(new Node[5]);
        }
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    //it is more clear in the main code to make this a method
    public void incrementLevel() {
        setLevel(getLevel() + 1);
    }

    public void setChildren(Node[] children) {
        this.children = children;
    }

    public void setChild(int index, Node node) {
        if (isLeaf) {
            setIsLeaf(false);
        }
        children[index] = node;
    }
    //getters
    public boolean getIsLeaf() {
        return isLeaf;
    }

    public String getSequence() {
        return sequence;
    }

    public Integer getLevel() {
        return level;
    }

    public Node[] getChildren() {
        return children;
    }
}