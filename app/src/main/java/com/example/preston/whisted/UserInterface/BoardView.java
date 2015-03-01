package com.example.preston.whisted.UserInterface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.Cards.Deck;
import com.example.preston.whisted.Game.Game;
import com.example.preston.whisted.R;

/**
 * Created by preston on 12/4/14.
 * TODO: when selecting a card, clicking the card next to it will play the first card
 */
public class BoardView extends View {
    private Context context;
    //drawing
    private Paint paintBg;
    private Paint paintLine;
    private Paint paintText;
    //hands
    private Deck hand1;
    private Deck hand2;
    //middle cards
    private Card firstCard;
    private Card secondCard;
    //card dimensions
    private int cardWidth = 200;
    private int cardHeight = 300;
    //constants for displaying human/ai cards
    private static boolean visible = true;
    private static boolean invisible = false;
    private static int redHandVisible = 0;
    private static int redHandInvisible = -1;

    private boolean sorted = false;
    private int selectedCardIndex = -1;
    private boolean doneSelectingCards = false;
    private boolean almostDone = false;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        doneSelectingCards = false;
        sorted = false;
        this.context = context;
        paintBg = new Paint();
        paintBg.setStyle(Paint.Style.FILL);
        paintBg.setColor(Color.GREEN);

        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setColor(Color.RED);
        paintLine.setStrokeWidth(5.0f);

        paintText = new Paint();
        paintText.setTextSize(80);
        paintText.setColor(Color.CYAN);
    }

    public void setTwoMiddleCards(Card firstCard, Card secondCard, boolean secondCardVisible){
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setTwoMiddleCards(Game.getInstance().getRedMiddleCard(), Game.getInstance().getBlueMiddleCard(),
                Game.getInstance().getBlueMiddleCardVisible());
        drawGameArea(canvas);
        //draw number of tricks won for each player
        drawNumberTricksWon(canvas);
        //draw cards last played if blue wins
        drawOldMiddleCards(canvas);
        //draw current middle cards
        drawMiddleCards(canvas);

        //draw player's hand
        drawHand(canvas, hand1, visible);
        //need to write draw opponent's hand
        drawHand(canvas, hand2, invisible);
    }

    private void drawNumberTricksWon(Canvas canvas) {
        int xDen = 5;
        int yDen = 4;
        canvas.drawText("" + Game.getInstance().getBlueTricksWon(),
                getWidth() / xDen, getHeight() / yDen, paintText);
        canvas.drawText("" + Game.getInstance().getRedTricksWon(),
                getWidth() * (xDen - 1) / xDen, getHeight() * (yDen - 1)/ yDen, paintText);
    }


    private void drawOldMiddleCards(Canvas canvas) {
        Card redCard = Game.getInstance().getOldRedCard();
        Card blueCard = Game.getInstance().getOldBlueCard();
        if (redCard != null )
            drawCard(canvas, redCard, getOldMiddleBounds(redHandVisible),visible);

        if (blueCard != null)
            drawCard(canvas, blueCard, getOldMiddleBounds(redHandInvisible), visible);

    }

    public void updateHand(){
        hand1 = Game.getInstance().getRedPlayer().getHand();
        hand2 = Game.getInstance().getBluePlayer().getHand();
        sorted = false;
        doneSelecting();
        invalidate();
    }

    //checks to see if we are done selecting or if we have been
    private boolean doneSelecting(){
        if (hand1.size() == 13 && !doneSelectingCards)
            doneSelectingCards = true;
        sortHand1();
        return doneSelectingCards;
    }

    private void drawGameArea(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);
    }

    private void drawMiddleCards(Canvas canvas){
        //if redPlayerPlayed the card, then we play it on the right
        Card redCard = Game.getInstance().getRedMiddleCard();
        Card blueCard = Game.getInstance().getBlueMiddleCard();
        boolean blueCardVisible = Game.getInstance().getBlueMiddleCardVisible();
        if (redCard != null )
            drawCard(canvas, redCard, getMiddleBounds(redHandVisible),visible);

        if (blueCard != null)
            drawCard(canvas, blueCard, getMiddleBounds(redHandInvisible), blueCardVisible);

    }

    //redhand is 0 if it is human's hand, else it is -1
    private Rect getHandBounds(int i, int handSize, int redHand){
        if (handSize == 0)
            return null;
        int width = getWidth();
        int height = getHeight() + redHand * getHeight();
        int dx = width / handSize;
        int right = (i * dx );

        int cardBottom =  height + 100 - (redHand == redHandVisible && i == selectedCardIndex? 100: 0);
        return new Rect(right, cardBottom - cardHeight , right + cardWidth, cardBottom);
    }

    //only difference for red hand is how far to the right it goes
    private Rect getMiddleBounds(int redHand){
        int width = getWidth();
        int height = getHeight();

        int right = (width / 2 + cardWidth / 2 + 2 * redHand * cardWidth);

        int cardBottom =  (height + cardHeight) / 2;
        return new Rect(right, cardBottom - cardHeight, right + cardWidth, cardBottom);
    }

    //returns bounds of cards which were played
    private Rect getOldMiddleBounds(int redHand){
        int offsetWidth = cardWidth / 2;
        int offsetHeight = cardHeight / 2;
        Rect temp = getMiddleBounds(redHand);
        Rect ret = new Rect(temp.left + offsetWidth, temp.top - offsetHeight,
                temp.right + offsetWidth, temp.bottom - offsetHeight);
        return ret;
    }

    //draws a card given a card and bounds and visible
    //todo: as cards are now views, we don't have to check for bounds
    //todo: also, as views now draw themselves, we should not force their bounds
    private void drawCard(Canvas canvas, Card cardToDraw, Rect bound, boolean visible){
        Drawable cardImage = getCardBack();
        if (visible)
            cardImage = getImage(cardToDraw);
        cardImage.setBounds(bound);
        cardImage.draw(canvas);
    }

    private void drawHand(Canvas canvas, Deck hand, boolean visible) {
        //start with the hand
        Deck tempHand = hand;
        if (tempHand == null)
            return;
        if (tempHand.size() == 0)
            return;

        for (int i = 0; i < tempHand.size(); i ++){
            drawCard(canvas, tempHand.get(i), getHandBounds(i, tempHand.size(), (visible? redHandVisible : redHandInvisible)),visible);
        }

    }

    //turns a card into the appropriate image
    private Drawable getImage(Card card) {
        String suit = card.getSuit();
        int value = card.getValue();
        String fileName = suit + "_" + getValueFromInt(value);

        //get id of image to draw
        int resID = getResources().getIdentifier(fileName , "drawable", context.getPackageName());
        Drawable ret = getResources().getDrawable(resID);
        return ret;
    }

    //returns the back of a card
    private Drawable getCardBack(){
        String filename = "b1fv";
        return getResources().getDrawable(getResources().getIdentifier(filename, "drawable", context.getPackageName()));
    }

    //necessary for parsing card file name from value
    private String getValueFromInt(int value) {
        if (value < 11 && value != 1)
            return "" + (value );
        String[] values = {"dummy", "jack", "queen", "king", "ace"};
        if (value == 1)
            return values[0];
        return values[value - 10];
    }


    private void sortHand1(){
        if (!sorted){
            sorted = true;
            hand1.sort(Card.DIAMOND);
        }
        invalidate();
    }

    private boolean clickSelectedCard(MotionEvent event){
        if (selectedCardIndex < 0 || selectedCardIndex >= hand1.size())
            return false;
        Rect temp = getHandBounds(selectedCardIndex, hand1.size(), redHandVisible);
        if (eventWithinBounds(event, temp))
            return true;
        return false;
    }

    //checks to see if we clicked within a given bound
    private boolean eventWithinBounds(MotionEvent event, Rect rect){
        return (rect != null && event.getX() > rect.left && event.getX() <= rect.right &&
                event.getY() > rect.top && event.getY() <= rect.bottom);
    }

    //updates which card in hand we select
    private void updateSelectedCard(MotionEvent event) {
        for (int i = 0; i < hand1.size(); i++) {
            Rect temp = getHandBounds(i, hand1.size(), redHandVisible);

            if (event.getY() < temp.top) {
                selectedCardIndex = -1;
                break;
            }

            if (eventWithinBounds(event, temp)) {

                selectedCardIndex = i;
            }
        }
    }

    public void almostDone(){
        almostDone = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (almostDone) {
            Game.getInstance().finishEndingGame();
            return true;
        }
        invalidate();
        //check within boundaries of selected index first, then travel through
        if (doneSelecting()) {
            if (clickSelectedCard(event)) {
                Game.getInstance().setRedPlayerCardIndex(selectedCardIndex);
                Game.getInstance().redPlayerPlayed();
                selectedCardIndex = -1;
            } else updateSelectedCard(event);
        } else {

            Card toPickUp = clickMiddleCard(event);

            if ((toPickUp) != null){
                //todo: next implementation, play animation here! wait to pickup card until animation is done
//                Animation receive = AnimationUtils.loadAnimation(getContext(), R.anim.receive_card);
//                toPickUp.startAnimation(receive); //todo: change this to the card!
                Game.getInstance().getRedPlayer().pickUpCard(toPickUp);
                sorted = false;
                Game.getInstance().updateMiddlePlayerChoiceCards();
                if (doneSelecting())
                    Game.getInstance().redPlayerDoneSelecting();
            }
        }

        invalidate();
        return super.onTouchEvent(event);
    }

    private Card clickMiddleCard(MotionEvent event) {
        if (eventWithinBounds(event, getMiddleBounds(redHandVisible)))
            return firstCard;
        if (eventWithinBounds(event, getMiddleBounds(redHandInvisible)))
            return secondCard;
        return null;
    }
}
