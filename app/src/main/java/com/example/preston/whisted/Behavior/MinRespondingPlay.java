package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.Cards.Deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by preston on 12/2/14.
 */
public class MinRespondingPlay implements PlayRespondingCardBehavior {
    public static final int MAX_VALUE = 99;
    private Deck hand;
    private String trump;

    @Override
    public Card playRespondingCard(Card leadCard) {
        //play minimum card to win the trick
        //see if we have a card in same suit
        hand.sort();

        List<Card> potentialCards = hand.getCardsofSuit(leadCard.getSuit());
        //if we can't follow suit
        if (potentialCards.size() == 0) {
            potentialCards = hand.getCardsofSuit(trump);
        }
        //if we don't have any trump
        if (potentialCards.size() == 0) {
            return slough(leadCard);
        }
        return minToWin(potentialCards, leadCard);
    }

    //assuming options are in order, return minimum winning card
    private Card minToWin(List<Card> options, Card lead){
        if (options.get(0).getSuit().equals(lead.getSuit())){
            for (Card card: options){
                if (card.getValue() > lead.getValue())
                    return card;
            }
        }
        //if we can't win after all, return the highest card with the lowest value
        return getHighestSameValuedCard(options.get(0), options);
    }

    private Card getHighestSameValuedCard(Card first, List<Card> options){
        Card previous = first;
        for (int i = 0; i < options.size() && options.get(i).getValue() - previous.getValue() <= 1; i++) {
            previous = options.get(i);
        }

        return previous;
    }

    private Card slough(Card leadCard){
        //we know that we don't have leadCard's suit, trump suit
        ArrayList<String> suits = new ArrayList<String>();
        for(String suit: Card.SUITS)
            suits.add(suit);
        suits.remove(leadCard.getSuit());
        suits.remove(trump);

        Card ret = new Card("test", MAX_VALUE);
        for (int i = 0; i < suits.size(); i ++){
            List<Card> cards = hand.getCardsofSuit(suits.get(i));
            Collections.sort(cards);

            if (cards.size() > 0 && ret.getValue() > cards.get(0).getValue()){
                ret = cards.get(0);
            }
        }
        return ret;
    }


    public void setHand(Deck deck) {
        this.hand = deck;
    }


    public void setTrump(String trump) {
        this.trump = trump;
    }
}
