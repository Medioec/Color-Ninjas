package com.colorninjas.main;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.colorninjas.main.MainMenuScreen.CurrentChat;
import com.colorninjas.main.objects.Player.PlayerColor;

public class Chatbot extends Thread{
    private boolean exit; //whether or not to stop the chatbot
    private int inc; //number of words in user input
    private String[] inv; //delimited word array
    private String input; //input buffer
    private String output; //output buffer
    private int selectedPlayer;

    private Lock lock;
    private Condition inputReady;

    public Chatbot(){
        exit = false;
        lock = new ReentrantLock();
        inputReady = lock.newCondition(); //call await() to wait for input(and pause thread), call signal() to process input
        inc = 0;
        output = "";
        selectedPlayer = 0;
    }
    @Override
    public void run(){
        while(!exit){
            while(inc < 1){
                lock.lock();
                try {
                    while(!CNinjas.getObjectManager().getMainMenuScreen().isInputReady()){
                        inputReady.await();
                    }
                    input = CNinjas.getObjectManager().getMainMenuScreen().getInput();
                    inv = input.split("\\W+");
                    inc = inv.length;
                    CNinjas.getObjectManager().getMainMenuScreen().setInput("");
                    CNinjas.getObjectManager().getMainMenuScreen().updateInput();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    lock.unlock();
                }
            }
            MainMenuScreen menu = CNinjas.getObjectManager().getMainMenuScreen();
            output = "";
            exit = chatbotProcess(menu);
            menu.setTemporalResponse(output);
            menu.updateResponse();
            CNinjas.getObjectManager().getMainMenuScreen().setInputReady(false);
            inc = 0;
        }
        
    }

    public Condition getInputReady() {
        return inputReady;
    }

    public Lock getLock() {
        return lock;
    }

    public boolean chatbotProcess(MainMenuScreen menu){
        if(cmpString(inv[0], "help")){
            chatbotDoHelp(menu);
            return false;
        } else if(chatbotIsFunciton(inv)){
            chatbotDoFunction(menu);
            return false;
        } else if(cmpString(input, "back")){
            chatbotDoBack(menu);
            return false;
        } else if(cmpString(input, "start")){
            return chatbotDoStart(menu);
        } else if(cmpString(input, "exit")){
            return chatbotDoExit();
        } else {
            output = "I don't understand \"" + input + "\"";
            return false;
        }
    }

    public boolean cmpString(String input, String string){
        if(input.equalsIgnoreCase(string)){
            return true;
        }
        return false;
    }

    public boolean chatbotIsFunciton(String[] inv){
        return cmpString(inv[0], "name")||cmpString(inv[0], "color");
    }

    public void chatbotDoHelp(MainMenuScreen menu){
        if(menu.getCurrentChat() == CurrentChat.START){
            menu.displayHelpScreen();
        }
    }

    public void chatbotDoFunction(MainMenuScreen menu){
        if(menu.getCurrentChat() == CurrentChat.START){
            if(cmpString(inv[0], "name")){
                if(inc == 1){
                    output = "Please specify a player.";
                    return;
                }
                else if(cmpString(inv[1], "player1") || cmpString(inv[1], "player2") || cmpString(inv[1], "1") || cmpString(inv[1], "2")){
                    if(inc == 2){
                        output = "Please enter a name for the player.";
                        return;
                    }
                    else{
                        if(cmpString(inv[1], "player1") || cmpString(inv[1], "1")){
                            selectedPlayer = 1;
                        }
                        else{
                            selectedPlayer = 2;
                        }
                        menu.changePlayerName(selectedPlayer, inv[2]);
                        output = "Name Changed.";
                        return;
                    }
                    
                }
                else {
                    output = "Please enter a valid player.";
                    return;
                }
            }
            else if(cmpString(inv[0], "color")){
                if(inc == 1){
                    output = "Please specify a player.";
                    return;
                }
                else if(cmpString(inv[1], "player1") || cmpString(inv[1], "player2") || cmpString(inv[1], "1") || cmpString(inv[1], "2")){
                    if(inc == 2){
                        output = "Please enter a color for the player.";
                        return;
                    }
                    else{
                        PlayerColor color;
                        if(cmpString(inv[2], "red")){
                            color = PlayerColor.RED;
                        } else if(cmpString(inv[2], "blue")){
                            color = PlayerColor.BLUE;
                        } else if(cmpString(inv[2], "green")){
                            color = PlayerColor.GREEN;
                        } else if(cmpString(inv[2], "yellow")){
                            color = PlayerColor.YELLOW;
                        } else {
                            output = "Invalid color selected.";
                            return;
                        }
                        if(cmpString(inv[1], "player1") || cmpString(inv[1], "1")){
                            selectedPlayer = 1;
                        }
                        else{
                            selectedPlayer = 2;
                        }
                        boolean success = menu.changePlayerColor(selectedPlayer, color);
                        if(success){
                            output = "Color changed.";
                            return;
                        }
                        else{
                            output = "Color already taken by other player.";
                            return;
                        }
                    }
                }
                else {
                    output = "Please enter a valid player.";
                    return;
                }
            }
        }
    }

    public void chatbotDoBack(MainMenuScreen menu){
        if(menu.getCurrentChat() == CurrentChat.HELP){
            menu.displayDefault();
        }
    }

    public boolean chatbotDoStart(MainMenuScreen menu){
        if(menu.getCurrentChat() == CurrentChat.START){
            menu.setStartGame(true);
            return true;
        }
        return false;
    }

    public boolean chatbotDoExit(){
        Gdx.app.exit();
        return true;
    }
}
