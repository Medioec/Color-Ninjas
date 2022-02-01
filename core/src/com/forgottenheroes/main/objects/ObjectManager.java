package com.forgottenheroes.main.objects;

import java.util.ArrayList;


import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Gdx;
import com.forgottenheroes.main.ChatbotScreen;
import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.GameScreen;
import com.forgottenheroes.main.GameState;
import com.forgottenheroes.main.Keyboard;
import com.forgottenheroes.main.MainMenuScreen;
import com.forgottenheroes.main.Reset;
import com.forgottenheroes.main.objects.tiles.Tile;

public class ObjectManager {
    private ArrayList<Tile> tileList;
    private ArrayList<GameEntity> entityList;
    private ArrayList<GameObject> objectList;
    private ArrayList<GameObject> removalQueue;

    private FHeroes game;
    private Map map;
    private GameScreen gameScreen;
    private MainMenuScreen mainMenuScreen;
    private ChatbotScreen chatbotScreen;
    private Keyboard keyboard;
    private Leaderboard leaderboard;
    private Scoreboard scoreboard;
    private GameObject popup;

    private long roundOverTime;

    private final int ROUNDRESETMS = 3000;
    

    public ObjectManager(FHeroes game){
        this.game = game;
        removalQueue = new ArrayList<GameObject>();
        tileList = new ArrayList<Tile>();
        entityList = new ArrayList<GameEntity>();
        objectList = new ArrayList<GameObject>();
        keyboard = new Keyboard(game);
        Gdx.input.setInputProcessor(keyboard);
    }

    public void render(FHeroes game){
        if(game.getGameState() == GameState.ROUNDENDPAUSE){
            startNewRound();
        }
        clearRemovalQueue();
        keyboard.checkKeyPress();
        for(int i = 0; i < tileList.size(); i++){
            Tile tile = tileList.get(i);
            tile.render(game);
        }
        sortEntitiesByY();
        for(int i = 0; i < entityList.size(); i++){
            GameEntity entity = entityList.get(i);
            entity.render(game);
        }
        for(int i = 0; i < objectList.size(); i++){
            GameObject object = objectList.get(i);
            object.render(game);
        }
        leaderboard.render(game);
        scoreboard.render(game);
    }

    public GameObject getPopup() {
        return popup;
    }

    public void setPopup(GameObject popup) {
        this.popup = popup;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    public FHeroes getGame() {
        return game;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public void setMainMenuScreen(MainMenuScreen mainMenuScreen) {
        this.mainMenuScreen = mainMenuScreen;
    }

    public ChatbotScreen getChatbotScreen() {
        return chatbotScreen;
    }

    public void setChatbotScreen(ChatbotScreen chatbotScreen) {
        this.chatbotScreen = chatbotScreen;
    }

    public ArrayList<Tile> getTileList(){
        return tileList;
    }

    public ArrayList<GameEntity> getEntityList(){
        return entityList;
    }

    public ArrayList<GameObject> getUiList() {
        return objectList;
    }

    public void addToTileList(Tile tile){
        tileList.add(tile);
    }

    public void addToEntityList(GameEntity entity){
        entityList.add(entity);
    }

    public void addToObjectList(GameObject object){
        objectList.add(object);
    }

    // return tile if found. else return null
    public Tile getTile(int[] coord){
        for(int i = 0; i < tileList.size(); i++){
            Tile tile = tileList.get(i);
            int[] fetched = tile.getGridCoords();
            if(fetched[0] == coord[0] && fetched[1] == coord[1]){
                return tile;
            }
        }
        return null;
    }

    public Player getPlayerByNumber(int number){
        for(int i = 0; i < entityList.size(); i++){
            GameEntity entity = entityList.get(i);
            try{
                Player player = (Player) entity;
                if(player.getPlayerNumber() == number){
                    return player;
                }
            }
            catch (ClassCastException e){
                continue;
            }
        }
        return null;
    }

    public ArrayList<Player> getPlayerList(){
        ArrayList<Player> players = new ArrayList<Player>();
        for(int i = 0; i < entityList.size(); i++){
            GameEntity entity = entityList.get(i);
            try{
                Player player = (Player) entity;
                players.add(player);
            }
            catch (ClassCastException e){
                continue;
            }
        }
        if(players.size() > 0){
            return players;
        }
        else {
            return null;
        }
        
    }

    public void sortEntitiesByY(){
        entityList.sort((e1, e2) -> e2.getY() - e1.getY());
    }

    public void removeEntity(GameEntity toRemove){
        entityList.remove(toRemove);
    }

    public void removeObject(GameObject object){
        objectList.remove(object);
    }

    public void queueForRemoval(GameObject object){
        removalQueue.add(object);
    }

    public void clearRemovalQueue(){
        if (removalQueue.size() != 0){
            for(int i = 0; i < removalQueue.size(); i++){
                GameObject object = removalQueue.get(i);
                if(object instanceof GameEntity){
                    removeEntity((GameEntity)object);
                }
                else {
                    removeObject(object);
                }
            }
            removalQueue.clear();
        }
    }

    public void clearObjects(){
        tileList.clear();
        entityList.clear();
        objectList.clear();
    }

    public boolean checkRoundOver(){
        int remainingPlayers = 0;
        for(int i = 0; i < entityList.size(); i++){
            GameEntity entity = entityList.get(i);
            try{
                Player player = (Player) entity;
                if(!player.checkPlayerDefeated()){
                    remainingPlayers ++;
                };
            }
            catch (ClassCastException e){
                continue;
            }
        }
        if(remainingPlayers > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void setRoundOver(){
        for(int i = 0; i < entityList.size(); i++){
            GameEntity entity = entityList.get(i);
            try{
                Player player = (Player) entity;
                if(!player.checkPlayerDefeated()){
                    player.setWins(player.getWins() + 1);
                };
            }
            catch (ClassCastException e){
                continue;
            }
        }
        if(isGameOver()){
            roundOverTime = TimeUtils.millis();
            Popup popup = new Popup("Game Over", "Esc to return to menu, any other key for rematch...", "");
            popup.setTextScale(1.4f);
            game.setGameState(GameState.GAMEOVER);
        } else {
            roundOverTime = TimeUtils.millis();
            game.setGameState(GameState.ROUNDENDPAUSE);
            setPopup(new Popup("Round Over", "Next round starting soon...", ""));
        }
        
    }

    public void startNewRound(){
        if(TimeUtils.millis() - roundOverTime > ROUNDRESETMS){
            FHeroes.getObjectManager().getMap().resetMap(FHeroes.getObjectManager().getMap().getCurrentMap(), Reset.NEWROUND);
            game.setGameState(GameState.GAMERUNNING);
            removeObject(getPopup());
        }
    }

    public boolean isGameOver(){
        int[] highest = getLeaderboard().getHighestWins();
        if(highest[1] >= map.getRoundsToWin()){
            return true;
        }
        return false;
    }

    public void startNewGame(){
        if(TimeUtils.millis() - roundOverTime > ROUNDRESETMS){
            FHeroes.getObjectManager().getMap().resetMap(FHeroes.getObjectManager().getMap().getCurrentMap(), Reset.NEWGAME);
            game.setGameState(GameState.GAMERUNNING);
            removeObject(getPopup());
        }
    }
}
