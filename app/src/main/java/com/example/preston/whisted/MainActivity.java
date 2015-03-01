package com.example.preston.whisted;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.preston.whisted.Game.Game;
import com.example.preston.whisted.UserInterface.BidFragment;
import com.example.preston.whisted.UserInterface.BoardView;
import com.example.preston.whisted.UserInterface.SettingsActivity;
import com.example.preston.whisted.UserInterface.TrumpFragment;


public class MainActivity extends Activity implements TrumpFragment.TrumpSelectionInterface, BidFragment.BidSelectionInterface{

    public static final int MENU_PREFERENCES = 0;
    public static String AUTO_HAND = "auto_hand";
    public static String RED_FIRST = "red_first";
    private boolean redFirst;
    private BoardView boardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restart();
    }

    //starts a game
    public void restart(){
        setContentView(R.layout.board_layout);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean greedyHand = prefs.getBoolean(AUTO_HAND, false);
        redFirst = prefs.getBoolean(RED_FIRST, false);
        //look at 229 for completing the settings activity
        boardView = (BoardView) findViewById(R.id.boardView);
        Game.getInstance().setActivity(this);
        Game.getInstance().setAutoHand(greedyHand);
        Game.getInstance().setRedPlayerFirst(redFirst);
        Game.getInstance().initializeGame();
        boardView.updateHand();
    }

    //middle point for fragment to set trump of human player
    @Override
    public void bidSelected(String bid) {
        Game.getInstance().setTrump(bid);
    }

    //middle point for fragment to set bid of human player
    @Override
    public void bidSelected(int bid) {
        Game.getInstance().setBid(bid);
    }
    public BoardView getView(){ return boardView;}

    public void changeFirstPlayer(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(RED_FIRST, !redFirst);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_PREFERENCES, Menu.NONE, R.string.menu_preferences);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case (MENU_PREFERENCES):{
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            }
        }
        return false;
    }
}
