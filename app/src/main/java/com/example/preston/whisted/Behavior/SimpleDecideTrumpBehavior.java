package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.Cards.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by preston on 12/14/14.
 */
public class SimpleDecideTrumpBehavior implements DecideTrumpBehavior {
    @Override
    public String decideTrump(Deck hand) {
        List<Card> temp = new ArrayList<>();
        String ret = "";
        for (String suit: Card.SUITS){
            if (hand.getCardsofSuit(suit).size() > temp.size()){
                temp = hand.getCardsofSuit(suit);
                ret = suit;
            }
        }
        return ret;
    }
}
