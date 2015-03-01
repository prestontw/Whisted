package com.example.preston.whisted.Cards;

/**
 * Created by preston on 11/26/14.
 */
public class CardComparator implements CardComparatorInterface{
    private String trumpSuit;

    public CardComparator() {
        trumpSuit = "not instantiated";
    }

    public CardComparator(String trumpSuit) {

        this.trumpSuit = trumpSuit;
    }

    public String getTrumpSuit() {
        return trumpSuit;
    }

    public void setTrumpSuit(String trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    //returns whether card2 card is 'greater' than card1 card
    @Override
    public boolean compareFirstToSecond(Card card1, Card card2) {
        //if same suit
        if (card1.getSuit() == card2.getSuit()){
            return (card2.getValue() > card1.getValue());
        }
        if (card1.getSuit() == trumpSuit)
            return false;
        if (card2.getSuit() == trumpSuit)
            return true;
        return false;
    }

    @Override
    public boolean compareFirstToSecond(Card card1, Card card2, String trump) {
        String oldTrump = trump;
        this.trumpSuit = trump;
        boolean ret = compareFirstToSecond(card1, card2);
        this.trumpSuit = oldTrump;
        return ret;
    }
}
