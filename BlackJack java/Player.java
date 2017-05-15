/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BlackJack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author Kevin
 */
public class Player {
    
    public static void main(String args[]) throws IOException, ClassNotFoundException
    {
        Socket connection; 
        Object cardRecieved;
        ObjectOutputStream output;
        ObjectInputStream input;
        InputStreamReader outReader;
        BufferedReader readString;
        PrintWriter remoteOutput;
        InputStreamReader inReader;
        BufferedReader remoteInput;
        String move;
        String totalValue;
        LinkedList<Card> hand = new LinkedList<Card>();
        
        connection = new Socket("localhost",40041);
        
        //writing Strings to the Server(Dealer)
        outReader = new InputStreamReader(System.in);
        readString = new BufferedReader(outReader);
        remoteOutput = new PrintWriter(connection.getOutputStream(), true);
        
        //reading Strings from the Server(Dealer)
        inReader = new InputStreamReader(connection.getInputStream());
        remoteInput = new BufferedReader(inReader);
        
        //for reading Objects to the Dealer
        input = new ObjectInputStream(connection.getInputStream());
        //for writing Objects to the Dealer
        output = new ObjectOutputStream(connection.getOutputStream());
        
        while(true) 
        {
            //send what you want to do to the Dealer
            System.out.println("Your Move: ");
            move = readString.readLine();
            remoteOutput.println(move);
            
            //get the Card the Dealer sends
            if(move.equals("deal")) 
            {
                cardRecieved = input.readObject();
                if(cardRecieved instanceof Card) 
                {
                    String ifAce = ((Card) cardRecieved).getRank();
                    if(ifAce.equals("ace")) 
                    {
                        System.out.println(cardRecieved);
                        System.out.println("Do you want ACE to be 11? Yes or No");
                        String prompt = readString.readLine();
                        if(prompt.equals("yes")) 
                        {
                            ((Card) cardRecieved).setValue(11);
                        }
                    }
                    
                    System.out.println(cardRecieved);
                    hand.add((Card)cardRecieved);
                }
            }
            
            if(move.equals("show")) 
            {
                System.out.println("----------------HAND--------------------");
                for(int i = 0; i < hand.size(); i++) 
                {
                    System.out.println(hand.get(i));
                }
            }
            
            if(move.equals("stay")) 
            {
                output.writeObject(hand);
                totalValue = remoteInput.readLine();
                System.out.println("Your Hands Value: " + totalValue);
            }
            
            if(move.equals("new game")) 
            {
                System.out.println("----------------NEW GAME--------------------");
            }
            
        }
    }
    
}
