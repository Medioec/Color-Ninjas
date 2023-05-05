package com.colorninjas.main.objects;

import java.util.ArrayList;

import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.colorninjas.main.CNinjas;
import com.colorninjas.main.GameScreen;
import com.colorninjas.main.GameState;
import com.colorninjas.main.Keyboard;
import com.colorninjas.main.MainMenuScreen;
import com.colorninjas.main.Reset;
import com.colorninjas.main.objects.Player.Direction;
import com.colorninjas.main.objects.tiles.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ObjectManager {
    private ArrayList<Tile> tileList;
    private ArrayList<GameEntity> entityList;
    private ArrayList<GameObject> objectList;
    private ArrayList<GameObject> removalQueue;

    private SpriteBatch spriteBatch;
	private BitmapFont bitmapFont;
	private ShapeRenderer shapeRenderer;
	private Viewport viewport;
	private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;

    private CNinjas game;
    private Map map;
    private GameScreen gameScreen;
    private MainMenuScreen mainMenuScreen;
    private Keyboard keyboard;
    private Leaderboard leaderboard;
    private Scoreboard scoreboard;
    private GameObject popup;

    private long roundOverTime;
    private long lastScoring;

    private final int ROUNDRESETMS = 3000;
    private final int SCOREINTERVALMS = 1000;

    public ObjectManager(CNinjas game){
        this.game = game;
        removalQueue = new ArrayList<GameObject>();
        tileList = new ArrayList<Tile>();
        entityList = new ArrayList<GameEntity>();
        objectList = new ArrayList<GameObject>();
        keyboard = new Keyboard();
        Gdx.input.setInputProcessor(keyboard);
    }

    public void render(float delta){
        if(CNinjas.getGameState() == GameState.ROUNDENDPAUSE){
            startNewRound();
        }
        if(CNinjas.isGameState(GameState.GAMERUNNING)){
            if(TimeUtils.millis() - lastScoring > SCOREINTERVALMS){
                addTileScores();
            }
        }
        clearRemovalQueue();
        keyboard.checkKeyPress();
        for(int i = 0; i < tileList.size(); i++){
            Tile tile = tileList.get(i);
            tile.render(delta);
        }
        sortEntitiesByY();
        for(int i = 0; i < entityList.size(); i++){
            GameEntity entity = entityList.get(i);
            entity.render(delta);
        }
        for(int i = 0; i < objectList.size(); i++){
            GameObject object = objectList.get(i);
            object.render(delta);
        }
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public void setMapRenderer(OrthogonalTiledMapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public BitmapFont getBitmapFont() {
        return bitmapFont;
    }

    public void setBitmapFont(BitmapFont bitmapFont) {
        this.bitmapFont = bitmapFont;
    }

    public ArrayList<GameObject> getObjectList() {
        return objectList;
    }

    public void setEntityList(ArrayList<GameEntity> entityList) {
        this.entityList = entityList;
    }

    public int getROUNDRESETMS() {
        return ROUNDRESETMS;
    }
    
    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
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

    public CNinjas getGame() {
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

    public ArrayList<Tile> getTileList(){
        return tileList;
    }

    public ArrayList<GameEntity> getEntityList(){
        return entityList;
    }

    public long getLastScoring() {
        return lastScoring;
    }

    public void setLastScoring(long lastScoring) {
        this.lastScoring = lastScoring;
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
        entityList.sort((e1, e2) -> e2.getRelativeY() - e1.getRelativeY());
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

    public int checkRemainingPlayers(){
        int remainingPlayers = 0;
        for(int i = 0; i < entityList.size(); i++){
            GameEntity entity = entityList.get(i);
            try{
                Player player = (Player) entity;
                if(!player.checkPlayerDefeated()){
                    remainingPlayers ++;
                }
            }
            catch (ClassCastException e){
                continue;
            }
        }
        return remainingPlayers;
    }

    public void setRoundOver(){
        int highestScore = -1;
        Player winningPlayer = null;
        if(checkRemainingPlayers() == 1){
            for(int i = 0; i < entityList.size(); i++){
                GameEntity entity = entityList.get(i);
                try{
                    Player player = (Player) entity;
                    if(!player.checkPlayerDefeated()){
                        winningPlayer = player;
                    }
                }
                catch (ClassCastException e){
                    continue;
                }
            }
        }
        else{
            for(int i = 0; i < entityList.size(); i++){
                GameEntity entity = entityList.get(i);
                try{
                    Player player = (Player) entity;
                    if(player.getScore() == highestScore){
                        highestScore = player.getScore();
                        winningPlayer = null;
                    }
                    else if(player.getScore() > highestScore){
                        highestScore = player.getScore();
                        winningPlayer = player;
                    }
                }
                catch (ClassCastException e){
                    continue;
                }
            }
        }
        
        if(winningPlayer != null){
            winningPlayer.setWins(winningPlayer.getWins() + 1);
        }
        if(isGameOver()){
            roundOverTime = TimeUtils.millis();
            Popup popup = new Popup("Game Over", "Esc to return to menu, any other key for rematch...", "");
            popup.setTextScale(1.4f);
            setPopup(popup);
            CNinjas.setGameState(GameState.GAMEOVER);
        } else {
            roundOverTime = TimeUtils.millis();
            CNinjas.setGameState(GameState.ROUNDENDPAUSE);
            setPopup(new Popup("Round Over", "Next round starting soon...", ""));
        }
        
    }

    public void startNewRound(){
        if(TimeUtils.millis() - roundOverTime > ROUNDRESETMS){
            map.resetMap(map.getCurrentMap(), Reset.NEWROUND);
            CNinjas.setGameState(GameState.GAMERUNNING);
            removeObject(getPopup());
            //reset direction of all players
            for(int i = 0; i < entityList.size(); i++){
                GameEntity entity = entityList.get(i);
                try{
                    Player player = (Player) entity;
                    player.setCurrentDirection(Direction.S);
                }
                catch (ClassCastException e){
                    continue;
                }
            }
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
            CNinjas.getObjectManager().getMap().resetMap(CNinjas.getObjectManager().getMap().getCurrentMap(), Reset.NEWGAME);
            CNinjas.setGameState(GameState.GAMERUNNING);
            removeObject(getPopup());
        }
    }

    public void addTileScores(){
        int score1 = 0;
        int score2 = 0;
        for(int i = 0; i < tileList.size(); i++){
            Tile tile = tileList.get(i);
            switch(tile.getFloor().getOwner()){
                case 1:
                score1 += 5;
                break;
                case 2:
                score2 += 5;
                break;
            }
        }
        Player player = getPlayerByNumber(1);
        player.setScore(player.getScore() + score1);
        player = getPlayerByNumber(2);
        player.setScore(player.getScore() + score2);
        lastScoring = TimeUtils.millis();
    }

    public void resetTileOwners(){
        for(int i = 0; i < tileList.size(); i++){
            Tile tile = tileList.get(i);
            tile.getFloor().changeOwner(0);
        }
    }
}
