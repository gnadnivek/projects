/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package BlackJack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
/**
 *
 * @author Kevin
 */
public class Deck implements Serializable {
    
    public LinkedList<Card> deck = new LinkedList<Card>();
    public int nextCard = 0;
    public Card newCard;    

    public Deck() 
    {
        for(int i = 2; i <= 10; i ++) 
        {
            deck.add(new Card(String.valueOf(i), "spades", i));
            deck.add(new Card(String.valueOf(i), "hearts", i));
            deck.add(new Card(String.valueOf(i), "diamonds", i));
            deck.add(new Card(String.valueOf(i), "clubs", i));
        }
        
        deck.add(new Card("jack", "spades", 10));
        deck.add(new Card("queen", "spades", 10));
        deck.add(new Card("king", "spades", 10));
        deck.add(new Card("ace", "spades", 1));
        
        deck.add(new Card("jack", "diamonds", 10));
        deck.add(new Card("queen", "diamonds", 10));
        deck.add(new Card("king", "diamonds", 10));
        deck.add(new Card("ace", "diamonds", 1));
        
        deck.add(new Card("jack", "hearts", 10));
        deck.add(new Card("queen", "hearts", 10));
        deck.add(new Card("king", "hearts", 10));
        deck.add(new Card("ace", "hearts", 1));
        
        deck.add(new Card("jack", "clubs", 10));
        deck.add(new Card("queen", "clubs", 10));
        deck.add(new Card("king", "clubs", 10));
        deck.add(new Card("ace", "clubs", 1));
        
    }
    
    public void shuffleDeck() 
    {
        Collections.shuffle(deck);
    }
    
    public Card getCard() 
    {
        newCard = deck.get(nextCard);
        nextCard++;
        
        return newCard;
    }
    
    public void setIndex() 
    {
        nextCard = 0;
    } 
}
