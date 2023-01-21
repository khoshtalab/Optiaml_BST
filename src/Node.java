public class Node {
    private Node left;
    private Node right;
    private Integer integer;

    public Node(Integer integer) {
        this.integer = integer;
    }
    public Node() {

    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    @Override
    public String toString() {
        return "Node{" +
                " integer=" + integer +
                " ,left=" + left +
                ", right=" + right +

                '}';
//        return " -- "+right+"\n"
//                +"|"+"\n"
//                +left;
    }
}
