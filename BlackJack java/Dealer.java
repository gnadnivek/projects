/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package BlackJack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author Kevin
 */
public class Dealer {
    
    public static void main(String args[]) throws IOException, ClassNotFoundException
    {
        ServerSocket server;
        Socket client;
        LinkedList handRecieved;
        ObjectOutputStream output;
        ObjectInputStream input;
        InputStreamReader inReader;
        BufferedReader remoteInput;
        InputStreamReader outReader;
        BufferedReader readString;
        PrintWriter remoteOutput;
        String moveRecieved;
        int totalValue = 0;
        Card cardSending;
        Card cardInHand;
        
        Deck deck = new Deck();  //make the deck
        deck.shuffleDeck();
        
        //set up server to find a client(player)
        server = new ServerSocket(40060);
        System.out.println("Awaiting a client on port " + server.getLocalPort());
        client = server.accept();
        System.out.println("Connection found");
        
        //for getting any Strings to the Client(Player)
        inReader = new InputStreamReader(client.getInputStream());
        remoteInput = new BufferedReader(inReader);
        
        //for sending any Strings to the Client(Player)
        outReader = new InputStreamReader(System.in);
        readString = new BufferedReader(outReader);
        remoteOutput = new PrintWriter(client.getOutputStream(), true);
        
        //for sending Objects to the Player
        output = new ObjectOutputStream(client.getOutputStream());
        //for reading Objects from the Player
        //input = new ObjectInputStream(client.getInputStream());
        
        while(true) 
        {
            moveRecieved = remoteInput.readLine();
            //System.out.println(moveRecieved);
            if(moveRecieved.equals("deal")) 
            {
                //send a card from the deck to the Player
                cardSending = deck.getCard();
                System.out.println("Dealing: " + cardSending.toString());
                output.writeObject(cardSending);
            }
            
            if(moveRecieved.equals("stay")) 
            {
		input = new ObjectInputStream(client.getInputStream());
                handRecieved = (LinkedList)input.readObject();
                System.out.println("----------------HAND--------------------");
                for(int i = 0; i < handRecieved.size(); i++) 
                {
                    System.out.println(handRecieved.get(i));
                    cardInHand = (Card)handRecieved.get(i);
                    totalValue = totalValue + cardInHand.getValue();
                }
                System.out.println("Hands Total Value: " + totalValue);
                //totalValue = readString.readLine();
                remoteOutput.println(totalValue);
		totalValue = 0;
            }
            
            if(moveRecieved.equals("new game")) 
            {
                System.out.println("----------------NEW GAME--------------------");
                deck.setIndex();
                deck.shuffleDeck();
            }
            
        }
        
    }
   
}
