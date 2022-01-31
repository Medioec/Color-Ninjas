package com.forgottenheroes.main.objects;

import java.util.ArrayList;

import javax.print.attribute.standard.DialogTypeSelection;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.forgottenheroes.main.DisplayScreen;
import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.Keyboard;
import com.forgottenheroes.main.objects.Player.PlayerNumber;
import com.forgottenheroes.main.objects.tiles.Tile;

public class ObjectManager {
    private ArrayList<Tile> tileList;
    private ArrayList<GameEntity> entityList;
    private ArrayList<GameObject> objectList;
    private ArrayList<GameObject> removalQueue;

    private FHeroes game;
    private Map map;
    private DisplayScreen gameScreen;
    private DisplayScreen mainMenuScreen;
    private Keyboard keyboard;
    private Leaderboard leaderboard;
    private Scoreboard scoreboard;
    

    public ObjectManager(FHeroes game){
        this.game = game;
        removalQueue = new ArrayList<GameObject>();
        tileList = new ArrayList<Tile>();
        entityList = new ArrayList<GameEntity>();
        objectList = new ArrayList<GameObject>();
        keyboard = new Keyboard(game);
    }

    public void render(FHeroes game){
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

    public DisplayScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(DisplayScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public DisplayScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    public void setMainMenuScreen(DisplayScreen mainMenuScreen) {
        this.mainMenuScreen = mainMenuScreen;
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

    public Player getPlayerByNumber(PlayerNumber number){
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
}
