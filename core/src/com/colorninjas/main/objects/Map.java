package com.colorninjas.main.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.colorninjas.main.CNinjas;
import com.colorninjas.main.GameScreen;
import com.colorninjas.main.Reset;
import com.colorninjas.main.objects.Player.PlayerColor;
import com.colorninjas.main.objects.tiles.Floor;
import com.colorninjas.main.objects.tiles.Tile;
import com.colorninjas.main.objects.tiles.Wall;

public class Map extends GameObject{

    private ObjectManager objectManager;
    private TiledMap map;

    private int tiledMapGridSize;
    private int scaleFactor;
    private int vSize; //horizontal size (in squares)
    private int hSize; //vertical size
    private int currentMap;
    private int roundsToWin;
    private int[] player1InitPos;
    private int[] player2InitPos;
    private static int gridSize;
    private static int numberOfPlayers;

    public Map(){
        this.objectManager = CNinjas.getObjectManager();
    }

    @Override
    public void render(float delta){
        
    }

    // return tile if found. else return null
    public Tile getTile(int[] coord){
        return objectManager.getTile(coord);
    }

    public ArrayList<Tile> getTileList(){
        return objectManager.getTileList();
    }

    public void addToTileList(Tile tile){
        objectManager.addToTileList(tile);
    }

    public void centerMap(){
        this.setRelativeY( (GameScreen.getWORLDHEIGHT() - this.getHeight() ) / 2 );
        this.setRelativeX( (GameScreen.getWORLDWIDTH() - this.getWidth() ) / 2 );
    }

    public void generateMap1(){
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("map/map.tmx");
        tiledMapGridSize = 16;
        gridSize = 64;
        scaleFactor = gridSize / tiledMapGridSize;
        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, scaleFactor, CNinjas.getObjectManager().getSpriteBatch());
        objectManager.setMapRenderer(renderer);
        setHSize(9);
        setVSize(9);
        setNumberOfPlayers(2);
        setRoundsToWin(3);
        setPlayer1InitPos(new int[]{0,0});
        setPlayer2InitPos(new int[]{8,8});
        int[][] wallList = { {1, 1}, {1, 2}, {1, 3}, {2, 1}, {3,1}, {7, 1}, {7, 2}, {7, 3}, {6, 1}, {5, 1}, 
            {1, 7}, {2, 7}, {3, 7}, {1, 6}, {1, 5}, {7, 7}, {7, 6}, {7, 5}, {6, 7}, {5, 7} };
        centerMap();
        for(int i = 0; i < getVSize(); i++){
            for(int j = 0; j < getHSize(); j++){
                Tile tile = new Tile(i, j);
                tile.addTileObject(new Floor(tile));
                objectManager.addToTileList(tile);
                for(int[] coord:wallList){
                    if(coord[0] == i && coord[1] == j){
                        //tile.addTileObject(new Wall(tile));
                    }
                }
            }
        }
        Player player1 = CNinjas.getObjectManager().getPlayerByNumber(1);
        Player player2 = CNinjas.getObjectManager().getPlayerByNumber(2);
        if(player1 == null || player2 == null){
            player1 = new Player(getPlayer1InitPos(), PlayerColor.RED, 1);
            player1.setName("Player 1");
            player2 = new Player(getPlayer2InitPos(), PlayerColor.BLUE, 2);
            player2.setName("Player 2");
        } else {
            player1.setGridCoords(getPlayer1InitPos());
            player2.setGridCoords(getPlayer2InitPos());
        }
        player1.setInitPos(getPlayer1InitPos());
        player2.setInitPos(getPlayer2InitPos());
        player1.prepareAnimation(player1.getPlayerColor());
        player2.prepareAnimation(player2.getPlayerColor());
        HealthDisplay p1Health = new HealthDisplay(player1);
        HealthDisplay p2Health = new HealthDisplay(player2);
        CNinjas.getObjectManager().getScoreboard().addPlayerToScoreBoard(player1);
        CNinjas.getObjectManager().getScoreboard().addPlayerToScoreBoard(player2);
        CNinjas.getObjectManager().getLeaderboard().addPlayerToLeaderboard(player1);
        CNinjas.getObjectManager().getLeaderboard().addPlayerToLeaderboard(player2);
        objectManager.addToObjectList(p1Health);
        objectManager.addToObjectList(p2Health);
        setCurrentMap(1);
    }

    public void resetMap(int map, Reset reset){
        switch(map){
            case 1:
            switch(reset){
                case NEWGAME:
                CNinjas.getObjectManager().getPlayerByNumber(1).resetToNewGame();
                CNinjas.getObjectManager().getPlayerByNumber(2).resetToNewGame();
                break;
                case NEWROUND:
                CNinjas.getObjectManager().getPlayerByNumber(1).resetToNewRound();
                CNinjas.getObjectManager().getPlayerByNumber(2).resetToNewRound();
                break;
                default:
                break;
            }
        }
    }

    public TiledMap getMap() {
        return map;
    }

    public int getRoundsToWin() {
        return roundsToWin;
    }

    public void setRoundsToWin(int roundsToWin) {
        this.roundsToWin = roundsToWin;
    }

    public int getVSize(){
        return vSize;
    }

    public void setVSize(int vSize){
        this.vSize = vSize;
        this.setHeight(vSize * gridSize);
    }

    public int getHSize(){
        return hSize;
    }

    public void setHSize(int hSize){
        this.hSize = hSize;
        this.setWidth(hSize * gridSize);
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;
    }

    public int[] getPlayer1InitPos() {
        return player1InitPos;
    }

    public void setPlayer1InitPos(int[] player1InitPos) {
        this.player1InitPos = player1InitPos;
    }

    public int[] getPlayer2InitPos() {
        return player2InitPos;
    }

    public void setPlayer2InitPos(int[] player2InitPos) {
        this.player2InitPos = player2InitPos;
    }

    public int getGridSize(){
        return gridSize;
    }

    public void setGridSize(int size){
        gridSize = size;
    }

    public boolean isPassable(int[] coords){
        Tile tile = CNinjas.getObjectManager().getTile(coords);
        if(tile != null){
            return tile.isPassable();
        }
        return false;
    }

    public void setNumberOfPlayers(int n){
        numberOfPlayers = n;
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }
}
