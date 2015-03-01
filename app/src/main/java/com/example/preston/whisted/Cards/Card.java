package com.example.preston.whisted.Cards;

import android.content.Context;
import android.view.View;

/**
 * Created by preston on 11/26/14.
 */
public class Card implements Comparable {
    private String suit;
    private int value;

    public final static String CLUB = "club";
    public final static String DIAMOND = "diamond";
    public final static String HEART = "heart";
    public final static String SPADE = "spade";
    public final static String NO_TRUMP = "no trump";
    public final static String[] SUITS = {CLUB, DIAMOND, HEART, SPADE};


    public Card(String suit, int value) {

        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public boolean equals(Card other){
        return other.getValue() == this.getValue() &&
                other.getSuit().equals(this.getSuit());
    }

    @Override
    public int compareTo(Object o) {

        Card otherCard = (Card) o;
        boolean debug = false;
        if (debug)
            System.out.println("Comparing " + otherCard + " to " + this);
        if (otherCard.getSuit().compareTo(suit) == 0) {
            if (debug)
                System.out.println("cards are of same suit:" + (value - otherCard.getValue()));
            return value - otherCard.getValue();
        }
        if (debug)
            System.out.println("how suits compare: " + suit.compareTo(otherCard.getSuit()));
        return suit.compareTo(otherCard.getSuit());
    }
    public String toString(){
        return suit + "_" + value;
    }
}
