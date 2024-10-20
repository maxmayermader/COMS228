package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Max Mayer-Mader
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //scnr gets user input
        Scanner scnr = new Scanner(System.in);
        System.out.println("Please enter a file name to decode: ");

        //file you want to access given the name
        String name = scnr.nextLine();
        File fileName = new File(name);
        scnr.close();

        //scanner to scan file
        Scanner fileScnr = new Scanner(fileName);
        String file ="";
        while (fileScnr.hasNextLine()){
            file += fileScnr.nextLine();
            if(fileScnr.hasNextLine())
                file+="\n";
        }
        fileScnr.close();

        int lastIndex = file.lastIndexOf('\n');

        //gets code for the tree and binary code on the next line
        String tree = file.substring(0, lastIndex);
        String bin = file.substring(lastIndex).trim();

        //count characters
        String charecters = "";
        for (int i=0; i<tree.length(); i++){
            char c = tree.charAt(i);
            if (c != '^')
                charecters += c;
        }

        //create new tree
        MsgTree msgTree= new MsgTree(tree);
        //print code & message
        MsgTree.printCodes(msgTree, charecters);
        msgTree.deCode(msgTree, bin);
    }
}