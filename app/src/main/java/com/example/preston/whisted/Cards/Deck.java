package com.example.preston.whisted.Cards;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by preston on 10/26/14.
 */
public class Deck {
    List<Card> deck = null;
    public Deck(){
        deck = new ArrayList<Card>();
    }
    public Deck(String[] suits, int[] values){
        deck = new ArrayList<Card>();
        for (String suit: suits)
            for (int value: values)
                deck.add(new Card( suit, value));
    }
    public Deck(String[] suits, List<Integer> values){
        deck = new ArrayList<Card>();
        for (String suit: suits)
            for (int value: values)
                deck.add(new Card( suit, value));
    }
    public Deck(List<String> suits, List<Integer> values){
        deck = new ArrayList<Card>();
        for (String suit: suits)
            for (int value: values)
                deck.add(new Card( suit, value));
    }

    public Deck(List<Card> deck) {
        this.deck = deck;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public void addCard(Card other){
        deck.add(other);
    }

    public List<Card> getCardsofSuit(String suit){
        List<Card> ret = new ArrayList<Card>();
        for (Card card: deck)
            if (card.getSuit().equals(suit))
                ret.add(card);
        return ret;
    }
    public Card removeTopCard(){return deck.remove(0);}
    public int size(){
        return deck.size();
    }
    public Card get(int card){
        return deck.get(card);
    }
    public Card remove(int indexToRemove){
        return deck.remove(indexToRemove);
    }
    public boolean remove(Card toRemove){
        return deck.remove(toRemove);
    }
    public void clear(){ deck.clear();}

    public void shuffle(){
        for (int i = deck.size() - 1; i > 0; i --){
            Random random = new Random();
            int randomInt = random.nextInt(i);
            Card temp = deck.get(i);
            deck.set(i, deck.get(randomInt));
            deck.set(randomInt, temp);
        }
    }
    public void sort(){
        Collections.sort(deck);
    }
    public void sort(String trump){
        //maybe sort then move all of the trump suit to the beginning of the array?
        Collections.sort(deck);
        int current = 0;
        while(current < deck.size() && !deck.get(current).getSuit().equals(trump)){
            current ++;
        }
        int index = 0;
        while(current < deck.size() && deck.get(current).getSuit().equals(trump)){
            deck.add(index,deck.remove(current));
            index ++;
            current ++;
        }
    }

    public String toString(){
        String ret = "";
        for (Card c : deck)
            ret = ret + c.toString() + " ";
        return ret;
    }
}
