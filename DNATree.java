import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class DNATree {
    private Node root;
    //trees always start with the root being a leaf
    //at level 0 with no sequence.
    public DNATree() {
        root = new Node(true, null, 0);
    }
    //insert takes the sequence and calls
    //insert helper with the private root and
    //level 0 to start
    public void insert(String sequence) {
        root = insertHelper(root, sequence, 0);
    }
    //useful helper method to convert
    //the letter to an index for the
    //child array
    public int getChildID(char i) {
        switch (i) {
            case 65:
                return 0;
            case 67:
                return 1;
            case 71:
                return 2;
            case 84:
                return 3;
            default:
                return 4;
        }
    }
    //starts at the root of the tree and takes
    //a sequence to insert.
    private Node insertHelper(Node root, String sequence, Integer level) {
        //this base case means an empty leaf has been found.
        //it returns a new node with the correct information
        //for a previous call to set as a child
        if (root == null) {
            System.out.println("Sequence " + sequence + " inserted at level " + level);
            return new Node(true, sequence, level);
        }
        //This branch only happens when the tree is completely empty.
        //It just sets the root's information.
        if (root.getIsLeaf() && root.getSequence() == null) {
            System.out.println("sequence " + sequence + " inserted at level " + level);
            root.setSequence(sequence);
            root.setLevel(level);
            return root;
        }
        //This branch happens if a sequence is already in the tree
        //it can tell if a sequence is in the tree by seeing
        //if the sequence equals the sequence at a leaf. It prints
        //the message and does nothing else.
        if (root.getIsLeaf() && root.getSequence().equals(sequence)) {
            System.out.println("Sequence " + sequence + " already exists");
            return root;
        }
        //this branch means we need to insert somewhere
        //that is already filled. It bumps the current child
        //to the dollar sign if the prefix is already
        //at its max. It bumps to the next prefix node
        //if there is still some chars left for prefixes
        if (root.getIsLeaf()) {
            Node temp = root;
            temp.incrementLevel();
            root = new Node(false, null, level);
            if (level < temp.getSequence().length()) {
                root.setChild(getChildID(temp.getSequence().charAt(level)), temp);
            } else {
                root.setChild(4, temp);
            }
        }
        //This branch handles when we are at an internal node
        //It could be an internal node already, or it could have been
        //made an internal node by the previous branch. If the sequence
        //has no chars left to go another layer down the prefix tree,
        //it is set to the dollar sign. If it has chars left, it sets the
        //correct child with a recursive call of the function
        if (!root.getIsLeaf()) {
            if (sequence.length() == level) {
                root.setChild(4, new Node(true, sequence, level + 1));
                System.out.println("sequence " + sequence + " inserted at level " + (level + 1));
            } else {
                root.setChild(getChildID(sequence.charAt(level)), insertHelper(root.getChildren()[getChildID(sequence.charAt(level))], sequence, level + 1));
            }
        }
        return root;
    }
    //similar to insert(), this method serves
    //to allow printing with no private information
    //being passed.
    public void print() {
        System.out.print("tree dump:");
        printHelper(root, 0);
        System.out.println();
    }
    //this method is called by print() with the
    //private root (start of the tree) and it starts
    //with zero.
    private void printHelper(Node root, int level) {
        //makes a new line
        System.out.print("\n");
        //this means an empty node and serves as
        //the base case. It prints the correct
        //number of spaces and the letter: E
        if (root == null) {
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            System.out.print("E");
            return;
        }

        //prints the right number of spaces
        //for the indent.
        for (int i = 0; i < level; i++) {
            System.out.print("  ");
        }

        //this branch is for an internal node.
        //It prints an I. Because internal nodes
        //have children, it recursively calls the method
        //on each child of the internal node
        if (!root.getIsLeaf()) {
            System.out.print("I");
            for (int i = 0; i <= 4; i++) {
                printHelper(root.getChildren()[i], level + 1);
            }
        } else {
            //this branch is for leafs. it doesn't
            //need to call the method again because
            //leafs have no children. If the sequence
            //is null, then it prints E, meaning an
            //unoccupied leaf. This should only happen
            // for an empty tree. Otherwise, it prints
            //the sequence.
            if (root.getSequence() == null) {
                System.out.print("E");
            } else {
                System.out.print(root.getSequence());
            }
        }
    }
    //the commands print and insert when split with
    //a regex of " " will always produce a String array
    //where the 0th value is the command. If it is insert,
    //then the 1th value is the sequence being inserted.
    //It calls insert on that sequence. If it is print,
    //then it just calls print. anything else in the 0th
    //value is a bad command and is printed as such.
    public void runCommand(String command) {
        String[] commandBits = command.split(" ");
        switch (commandBits[0]) {
            case "insert":
                insert(commandBits[1]);
                break;
            case "print":
                print();
                break;
            default:
                System.err.println("Bad command: " + command);
        }
    }
    //The main method takes the file and reads
    //every command. It sends it to runCommand to be
    //run.
    public static void main(String[] args) {
        DNATree tree = new DNATree();
        try {
            FileReader file = new FileReader(args[0]);
            Scanner in = new Scanner(file);
            String command = "";
            while (in.hasNext()) {
                command = in.nextLine();
                tree.runCommand(command);
            }
        } catch (FileNotFoundException e) {
            System.err.println(args[0] + " could not be found.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Please pass the file name.");
        }
    }
}