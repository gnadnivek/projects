/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package BlackJack;

import java.io.Serializable;
/**
 *
 * @author Kevin
 */
public class Card implements Serializable{
    
    public String suit;
    public String rank;
    public int value;
    
    public Card(String Crank, String Csuit, int Cvalue) 
    {
        suit = Csuit;
        rank = Crank;
        value = Cvalue; 
    }
    
    public String getSuit() 
    {
        return suit;
    }
    
    public String getRank() 
    {
        return rank;
    }
    
    public int getValue() 
    {
        return value;
    }
    
    public void setValue(int newValue) 
    {
        value = newValue;
    }
    
    @Override
    public String toString()
    {
        return rank + ", " + suit + ", Value: " + value;
    }
}
