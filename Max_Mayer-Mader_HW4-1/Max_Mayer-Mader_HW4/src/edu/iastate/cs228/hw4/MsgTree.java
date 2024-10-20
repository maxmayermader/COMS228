package edu.iastate.cs228.hw4;


import java.util.Stack;

/**
 * @author Max Mayer-Mader
 * MsgTree class used for decoding a message
 */
public  class  MsgTree{
    public char payloadChar;
    public MsgTree left;
    public MsgTree right;
    //binary code
    private static String binary;

    /*Can use a static char idx to the tree string for recursive
    solution, but it is not strictly necessary*/
    private static int staticCharIdx = 0;


    /**
     * Constructor building the tree from a string
     * @param encodingString
     */
    public MsgTree(String encodingString){
        if (encodingString != null && staticCharIdx < encodingString.length()) {

            Stack<MsgTree> stack = new Stack<MsgTree>();
            this.payloadChar = encodingString.charAt(staticCharIdx++);

            //push current message tree on stack
            stack.push(this);
            MsgTree curNode = this;

            //direction of node (0 for left, 1 for right)
            int direc = 0;

            //go through all characters in the encoding string
            while (staticCharIdx < encodingString.length()) {
                char alpha = encodingString.charAt(staticCharIdx++);
                MsgTree msgTree = new MsgTree(alpha);

                if (direc == 0) { //go to the left of the tree
                    curNode.left = msgTree; //new node is left child
                    if (alpha == '^') {
                        curNode = stack.push(msgTree);
                        direc = 0;
                    } else {
                        if (!stack.isEmpty())
                            curNode = stack.pop();
                        direc = 1;
                    }
                } else { //go to the right of the tree
                    curNode.right = msgTree; //new node is right child
                    if (alpha == '^') {
                        curNode = stack.push(msgTree);
                        direc = 0;
                    } else {
                        if (!stack.isEmpty())
                            curNode = stack.pop();
                        direc = 1;
                    }
                }
            }
        }
        else {
            return;
        }

//        //base case
//        if(payloadChar != '^' ){
//            return;
//        }
//
//        else{
//            this.left = new MsgTree(encodingString);
//            this.right = new MsgTree(encodingString);
//        }
    }

    /**
     * Constructor for a single node with null children
     * @param payloadChar
     */
    public MsgTree(char payloadChar){
        this.payloadChar = payloadChar;
        this.left = null;
        this.right = null;
    }

    /**
     * method to print characters and their binary codes
     * @param root
     * @param code
     */
    public static void printCodes(MsgTree root, String code) {
        System.out.println("character code\n-------------------------");
        char[] chars = code.toCharArray();
        for(int i=0; i<chars.length; i++){
            getBinCode(root, chars[i], binary="");
            String msg = "\t";
            if (chars[i] == '\n')
                msg += "\\n";
            else
                msg += chars[i] ;
            msg += "     " + binary;
            System.out.println(msg);
        }
        System.out.println();
    }


    /**
     * Recursively goes through the msgtree to get the binary code for a charecter
     * @param root
     * @param c
     * @param bin
     * @return
     */
    private static boolean getBinCode(MsgTree root, char c, String bin){
        if (root != null){
            if (root.payloadChar == c){
                binary = bin;
                return true;
            }
            return getBinCode(root.left, c, bin + "0") || getBinCode(root.right, c, bin + "1");
        }
        return false;
    }

    /**
     * Decodes a message using the MsgTree for decoding
     * @param codes Tree that has codes for decoding
     * @param msg message that you want to print out
     */
    public void deCode(MsgTree codes, String msg){
        System.out.println("MESSAGE:");
        MsgTree cur = codes;
        String decoded = "";
        //go through all the binary
        for (int i = 0; i<msg.length(); i++){
            char c = msg.charAt(i);
            //set the current root to left child
            if (c == '0')
                cur = cur.left;

            //set the current root to right child
            else if (c == '1')
                cur = cur.right;
            //if the payloadChar of the current node is not a hat then add the charecter to the string
            if (cur.payloadChar != '^'){
                decoded+=cur.payloadChar;
                cur = codes;
            }
        }
        System.out.println(decoded);
    }
}
