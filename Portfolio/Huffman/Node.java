public class Node {
    private char letter;
    private int frequency;
    private Node left;
    private Node right;

    public Node (char newLetter) {
        letter = newLetter;
        frequency = 1;
    }
    public Node (Node newLeft, Node newRight) {
        left = newLeft;
        right = newRight;
        frequency = newLeft.getFreq() + newRight.getFreq();
    }

    public char getLetter() {
        return letter;
    }
    public void increment() {
        frequency++;
    }
    public int getFreq() {
        return frequency;
    }
    public Node getLeft() {
        return left;
    }
    public Node getRight() {
        return right;
    }
}
