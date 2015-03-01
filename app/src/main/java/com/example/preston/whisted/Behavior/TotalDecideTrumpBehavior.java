package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.Cards.Deck;

/**
 * Created by preston on 1/11/15.
 */
public class TotalDecideTrumpBehavior implements DecideTrumpBehavior {
    @Override
    public String decideTrump(Deck hand) {
        //List<Card> temp = new ArrayList<>();
        String ret = "";
        int maxTotal = 0;
        for (String suit: Card.SUITS){
            int tempTotal = 0;
            for (Card card: hand.getCardsofSuit(suit)){
                tempTotal += card.getValue();
                if (tempTotal > maxTotal){
                    maxTotal = tempTotal;
                    ret = suit;
                }
            }
        }
        return ret;
    }
}


