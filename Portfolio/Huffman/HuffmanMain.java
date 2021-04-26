//Huffman Encoding by Noah Schmidt for Dr. Perkins
//Data Structures EGR221-A
//noahchristopher.schmidt@calbaptist.edu    642383

import java.util.*;

public class HuffmanMain {

    private static String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
            "tempor incididunt ut labore et dolore magna aliqua.";

    public static void main(String[] args) {
        List<Node> charNodes = buildList();

        while (charNodes.size() > 1) {
            charNodes = new ArrayList<Node>(sortList(charNodes));   //sort nodes by frequency
            Node newFirst = new Node(charNodes.remove(0), charNodes.remove(0));   //combine first two
            charNodes.add(newFirst);    //add it back
        }   //do this until we have one tree of frequencies

        Map conversions = new HashMap<Character, String >();        //create map of letters to binary
        addConversions(conversions, charNodes.get(0), "");

        String encoded = "";                            //create encoded string
        for (int i = 0; i < text.length(); i++) {
            encoded += conversions.get(text.charAt(i));
        }

        String decoded = "";
        StringBuilder codeWorkable = new StringBuilder(encoded);
        while(codeWorkable.length() > 0) {
            decoded += decode(codeWorkable, charNodes.get(0));
        }

        System.out.println("Plain text: " + text);
        System.out.println("Encoded text: " + encoded);
        System.out.println("Decoded text: " + decoded);

    }

    //build list of Nodes with correct chars and frequencies
    private static List buildList() {
        List charList = new ArrayList<Node>();

        for (int index = 0; index < text.length(); index++) {
            char letter = text.charAt(index);
            Node currentNode = findNode(charList, letter);  //find node with this letter
            if (currentNode == null) {
                currentNode = new Node(letter);
                charList.add(currentNode);
            }
            else
                currentNode.increment();
        }

        return charList;
    }

    //helper for build list
    //finds and returns node with corresponding node
    private static Node findNode(List<Node> charList, char letter) {
        for (Node leaf:charList) {
            if (leaf.getLetter() == letter) {
                return leaf;
            }
        }
        return null;
    }

    private static List sortList(List<Node> oldList) {
        List newList = new ArrayList<Node>();
        for(Node leaf:oldList) {
            int index = findIndex(leaf, newList, 0, newList.size());
            newList.add(index, leaf);
        }
        return newList;
    }

    //helper for sortList, finds index to insert Node in order
    private static int findIndex(Node node, List<Node> list, int min, int max) {
        if (min == max) {
            return max;
        }
        if (node.getFreq() < list.get(0).getFreq()) {   //remove a fluke with the sorting
            return 0;
        }
        if (min == max - 1) {
            return max;
        }
        if (list.get((max+min)/2).getFreq() > node.getFreq()) {
            return findIndex(node, list, min, (max+min)/2);
        }
        else {
            return findIndex(node, list, (max + min)/2, max);
        }
    }


    private static void addConversions(Map<Character, String> tree, Node root, String coded) {
        if (root.getLeft() == null && root.getRight() == null) {
            tree.put(root.getLetter(), coded);
        }
        if (root.getLeft() != null) {
            addConversions(tree, root.getLeft(), coded + "0");
        }
        if (root.getRight() != null) {
            addConversions(tree, root.getRight(), coded + "1");
        }
    }

    private static char decode(StringBuilder encoded, Node root) {
        if (root.getLeft() == null && root.getRight() == null) {
            return root.getLetter();
        }
        else if (encoded.charAt(0) == '0') {
            encoded.deleteCharAt(0);
            return decode(encoded, root.getLeft());
        }
        else {
            encoded.deleteCharAt(0);
            return decode(encoded, root.getRight());
        }
    }


}
