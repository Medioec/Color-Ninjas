package com.forgottenheroes.main.objects;

import java.util.ArrayList;
import java.lang.Math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.forgottenheroes.main.CNinjas;
import com.forgottenheroes.main.GameState;
import com.forgottenheroes.main.Pickup;
import com.forgottenheroes.main.objects.tiles.Floor;

public class Player extends GameEntity{
    private int playerNumber;
    private String name;
    private Color color;
    private PlayerColor playerColor;
    private State state;
    private Direction currentDirection;
    private TextureAtlas charAtlas;
    private Array<AtlasRegion> walkupArray;
    private Array<AtlasRegion> walkdownArray;
    private Array<AtlasRegion> walkleftArray;
    private Array<AtlasRegion> walkrightArray;
    private Animation<TextureRegion> walkup;
    private Animation<TextureRegion> walkdown;
    private Animation<TextureRegion> walkleft;
    private Animation<TextureRegion> walkright;
    private TextureRegion attackup;
    private TextureRegion attackdown;
    private TextureRegion attackleft;
    private TextureRegion attackright;
    private float timeElapsed;

    private int[] initPos;
    private int score;
    private int wins;
    private int hp;
    private int maxHP;
    private int attack;
    private int atkRange;
    private int atkWidth;
    private int moveSpeed;
    private long lastAttackTime;
    private long lastRevealTime;
    private long lastDamagedTime;
    private int[] currentGridCoord;
    private boolean visible;
    private boolean walking;

    private final int ATTACKCDMS = 500;
    private final int REVEALMS = 1000;
    private final int STUNMS = 1000;
    
    public enum PlayerNumber{
        PLAYER1,
        PLAYER2
    }

    public enum State{
        ATTACKING,
        IDLE,
        DEFEATED
    }

    public enum PlayerColor{
        RED,
        BLUE,
        GREEN,
        YELLOW,
        NONE
    }

    public enum Direction{
        N,
        NE,
        E,
        SE,
        S,
        SW,
        W,
        NW
    }

    public Player(int playerNumber){
        super();
        setHeight(48);
        setWidth(48);
        setMaxHP(200);
        setHp(getMaxHP());
        setScore(0);
        setWins(0);
        setAttack(40);
        setAtkRange(110);
        setAtkWidth(50);
        setMoveSpeed(5);
        setState(State.IDLE);
        setCurrentDirection(Direction.S);
        switch(playerNumber){
            case 1:
            color = Color.RED;
            playerColor = PlayerColor.RED;
            setName("Player 1");
            break;
            case 2:
            color = Color.BLUE;
            playerColor = PlayerColor.BLUE;
            setName("Player 2");
            break;
        }
        this.playerNumber = playerNumber;
        timeElapsed = 0;
        prepareAnimation(playerColor);
    }

    public Player(int[] spawncoords, PlayerColor color, int playerNumber){
        super(spawncoords);
        //setGridCoords(spawncoords);
        setHeight(48);
        setWidth(48);
        setMaxHP(200);
        setHp(getMaxHP());
        setName("player");
        setScore(0);
        setWins(0);
        setAttack(40);
        setAtkRange(100);
        setAtkWidth(40);
        setMoveSpeed(5);
        setState(State.IDLE);
        setCurrentDirection(Direction.S);
        playerColor = color;
        switch(color){
            case BLUE:
            this.color = Color.BLUE;
                break;
            case GREEN:
            this.color = Color.GREEN;
                break;
            case RED:
            this.color = Color.RED;
                break;
            case YELLOW:
            this.color = Color.YELLOW;
                break;
            default:
                break;
            
        }
        this.playerNumber = playerNumber;
        timeElapsed = 0;
        prepareAnimation(playerColor);
    }

    @Override
    public void render(float delta) {
        timeElapsed += delta;
        if(CNinjas.getGameState() != GameState.MAINMENU){
            autoHidePlayer();
            if(getState() != State.DEFEATED){
                walking = false;
                if(isValidXMovement()) updateXPos();
                if(isValidYMovement()) updateYPos();
                convertTile();
                if(visible){
                    CNinjas.getObjectManager().getShapeRenderer().begin();
                    CNinjas.getObjectManager().getShapeRenderer().setColor(Color.BLACK);
                    CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
                    //FHeroes.getObjectManager().getShapeRenderer().circle(getAbsoluteX(), getAbsoluteY(), getHeight() / 2);
                    CNinjas.getObjectManager().getShapeRenderer().setColor(color);
                    CNinjas.getObjectManager().getShapeRenderer().set(ShapeType.Filled);
                    //FHeroes.getObjectManager().getShapeRenderer().circle(getAbsoluteX(), getAbsoluteY(), getHeight() / 2 - 2);
                    CNinjas.getObjectManager().getShapeRenderer().end();
                    CNinjas.getObjectManager().getSpriteBatch().begin();
                    if(walking){
                        switch(currentDirection){
                            case E:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkright.getKeyFrame(timeElapsed, true), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case N:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkup.getKeyFrame(timeElapsed, true), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case NE:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkright.getKeyFrame(timeElapsed, true), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case NW:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkleft.getKeyFrame(timeElapsed, true), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case S:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkdown.getKeyFrame(timeElapsed, true), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case SE:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkright.getKeyFrame(timeElapsed, true), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case SW:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkleft.getKeyFrame(timeElapsed, true), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case W:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkleft.getKeyFrame(timeElapsed, true), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            default:
                                break;
                            
                        }
                    }
                    else if(isAttacking()){
                        switch(currentDirection){
                            case E:
                            CNinjas.getObjectManager().getSpriteBatch().draw(attackright, getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case N:
                            CNinjas.getObjectManager().getSpriteBatch().draw(attackup, getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case NE:
                            CNinjas.getObjectManager().getSpriteBatch().draw(attackright, getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case NW:
                            CNinjas.getObjectManager().getSpriteBatch().draw(attackleft, getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case S:
                            CNinjas.getObjectManager().getSpriteBatch().draw(attackdown, getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case SE:
                            CNinjas.getObjectManager().getSpriteBatch().draw(attackright, getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case SW:
                            CNinjas.getObjectManager().getSpriteBatch().draw(attackleft, getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case W:
                            CNinjas.getObjectManager().getSpriteBatch().draw(attackleft, getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            default:
                                break;
                            
                        }
                    }
                    else{
                        switch(currentDirection){
                            case E:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkrightArray.get(0), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case N:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkupArray.get(0), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case NE:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkrightArray.get(0), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case NW:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkleftArray.get(0), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case S:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkdownArray.get(0), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case SE:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkrightArray.get(0), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case SW:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkleftArray.get(0), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            case W:
                            CNinjas.getObjectManager().getSpriteBatch().draw(walkleftArray.get(0), getAbsoluteX() - getWidth()/2, getAbsoluteY() - getHeight()/2, 48, 48);
                                break;
                            default:
                                break;

                        }
                    }
                    CNinjas.getObjectManager().getSpriteBatch().end();
                }
            }
        }
    }
    
    public boolean isValidXMovement(){
        if(getVelX() != 0 && !checkPlayerDefeated() && !isAttacking()){
            int newX = getAbsoluteX() + getVelX();
            int newY = getAbsoluteY();
            int[] coords = getGridCoords(newX, newY);
            walking = true;
            if(coords != null){
                return CNinjas.getObjectManager().getMap().isPassable(coords);
            }
        }
        return false;
    }

    public boolean isValidYMovement(){
        if(getVelY() != 0 && !checkPlayerDefeated() && !isAttacking()){
            int newX = getAbsoluteX();
            int newY = getAbsoluteY() + getVelY();
            int[] coords = getGridCoords(newX, newY);
            walking = true;
            if(coords != null) return CNinjas.getObjectManager().getMap().isPassable(coords);
        }
        return false;
    }

    public void updateDirection(){
        if(getVelX() > 0){
            if(getVelY() > 0){
                setCurrentDirection(Direction.NE);
            }
            else if(getVelY() < 0){
                setCurrentDirection(Direction.SE);
            }
            else {
                setCurrentDirection(Direction.E);
            }
        }
        else if(getVelX() < 0){
            if(getVelY() > 0){
                setCurrentDirection(Direction.NW);
            }
            else if(getVelY() < 0){
                setCurrentDirection(Direction.SW);
            }
            else {
                setCurrentDirection(Direction.W);
            }
        }
        else {
            if(getVelY() > 0){
                setCurrentDirection(Direction.N);
            }
            else if(getVelY() < 0){
                setCurrentDirection(Direction.S);
            }
        }
    }

    public void addPowerup(Pickup powerup){

    }

    public int[] getInitPos() {
        return initPos;
    }

    public void setInitPos(int[] initPos) {
        this.initPos = initPos;
    }

    public void resetToNewRound(){
        setGridCoords(getInitPos());
        setHp(getMaxHP());
        setScore(0);
        CNinjas.getObjectManager().getPlayerByNumber(1).revealPlayer();
        CNinjas.getObjectManager().getPlayerByNumber(1).setCurrentGridCoord(new int[] {-1,-1});
        CNinjas.getObjectManager().getPlayerByNumber(2).revealPlayer();
        CNinjas.getObjectManager().getPlayerByNumber(2).setCurrentGridCoord(new int[] {-1,-1});
        CNinjas.getObjectManager().setLastScoring(TimeUtils.millis());
        CNinjas.getObjectManager().resetTileOwners();
    }

    public void resetToNewGame(){
        setGridCoords(getInitPos());
        setHp(getMaxHP());
        setScore(0);
        setWins(0);
        CNinjas.getObjectManager().getPlayerByNumber(1).revealPlayer();
        CNinjas.getObjectManager().getPlayerByNumber(1).setCurrentGridCoord(new int[] {-1,-1});
        CNinjas.getObjectManager().getPlayerByNumber(2).revealPlayer();
        CNinjas.getObjectManager().getPlayerByNumber(2).setCurrentGridCoord(new int[] {-1,-1});
        CNinjas.getObjectManager().setLastScoring(TimeUtils.millis());
        CNinjas.getObjectManager().resetTileOwners();
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAtkRange() {
        return atkRange;
    }

    public void setAtkRange(int atkRange) {
        this.atkRange = atkRange;
    }

    public int getAtkWidth() {
        return atkWidth;
    }

    public void setAtkWidth(int atkWidth) {
        this.atkWidth = atkWidth;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getCurrentDirection() {
        if(currentDirection != null){
            return currentDirection;
        }
        else {
            return Direction.S;
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int[] getCurrentGridCoord() {
        return currentGridCoord;
    }

    public void setCurrentGridCoord(int[] currentGridCoord) {
        this.currentGridCoord = currentGridCoord;
    }

    public Array<AtlasRegion> getWalkdownArray() {
        return walkdownArray;
    }

    public void performAttack(Direction dir){
        if(!isAttacking() && TimeUtils.millis() - lastDamagedTime > STUNMS){
            startAttackCD();
            new AttackSwing(this);
            int damage;
            double[] dirv;
            double[] posv = {0,0};
            switch(dir){
                case E:
                    dirv = new double[]{1,0};
                    break;
                case N:
                    dirv = new double[]{0,1};
                    break;
                case NE:
                    dirv = new double[]{1,1};
                    break;
                case NW:
                    dirv = new double[]{-1,1};
                    break;
                case S:
                    dirv = new double[]{0,-1};
                    break;
                case SE:
                    dirv = new double[]{1,-1};
                    break;
                case SW:
                    dirv = new double[]{-1,-1};
                    break;
                case W:
                    dirv = new double[]{-1,0};
                    break;
                default:
                    dirv = new double[]{0,0};
                    break;
            }
            double[] orthodirv = {-dirv[1], dirv[0]};
            ArrayList<Player> playerList = CNinjas.getObjectManager().getPlayerList();
            double magdirv = Math.sqrt(Math.pow(dirv[0], 2) + Math.pow(dirv[1], 2));
            double[] unitdirv = {dirv[0] / magdirv, dirv[1] / magdirv};
            double magorthodirv = Math.sqrt(Math.pow(orthodirv[0], 2) + Math.pow(orthodirv[1], 2));
            double[] unitorthodirv = {orthodirv[0] / magorthodirv, orthodirv[1] / magorthodirv};
            // loop through all players in list that are not attacking player
            for(Player target:playerList){
                if(target.getPlayerNumber() != this.getPlayerNumber() && !target.checkPlayerDefeated()){
                    posv[0] = target.getRelativeX() - this.getRelativeX();
                    posv[1] = target.getRelativeY() - this.getRelativeY();
                    int projdirv = (int)(unitdirv[0] * posv[0] + unitdirv[1] * posv[1]);
                    if(0 < projdirv && projdirv < target.getAtkRange()){
                        int projorthodirv = (int)(unitorthodirv[0] * posv[0] + unitorthodirv[1] * posv[1]);
                        if(Math.abs(projorthodirv) < target.getAtkWidth()){
                            if(target.getHp() - this.getAttack() < 0){
                                damage = target.getHp();
                                target.setHp(0);
                            }
                            else{
                                target.setHp(target.getHp() - this.getAttack());
                                damage = this.getAttack();
                            }
                            this.score += damage;
                            new DamageNumbers(target.getAbsoluteX() - 20, target.getAbsoluteY() + 60, 0, 1, damage);
                            damage = 0;
                            target.damageStun();
                        }
                    }
                    if(target.checkPlayerDefeated()){
                        if(CNinjas.getObjectManager().checkRoundOver()){
                            CNinjas.getObjectManager().setRoundOver();
                        }
                        
                    }
                }
            }
        }
    }

    public void startAttackCD(){
        setLastAttackTime(TimeUtils.millis());
        setState(State.ATTACKING);
        revealPlayer();
    }

    public boolean isAttacking(){
        if(TimeUtils.millis() - getLastAttackTime() > ATTACKCDMS){
            setState(State.IDLE);
        }
        if(getState() == State.ATTACKING){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkPlayerDefeated(){
        if(getHp() <= 0){
            setState(State.DEFEATED);
            return true;
        }
        return false;
    }

    @Override
    public void updatePos() {
        // TODO Auto-generated method stub
        
    }

    public void convertTile(){
        int[] temp;
        if(currentGridCoord == null){
            currentGridCoord = new int[] {-1,-1};
        }
        temp = currentGridCoord;
        currentGridCoord = getGridCoords();
        if(currentGridCoord == null){
            currentGridCoord = new int[] {-1,-1};
        }
        
        if(temp[1] != currentGridCoord[1] || temp[0] != currentGridCoord[0]){
            Floor floor = CNinjas.getObjectManager().getTile(currentGridCoord).getFloor();
            if(floor != null){
                floor.changeOwner(playerNumber);
            }
        }
    }

    public void revealPlayer(){
        setVisible(true);
        lastRevealTime = TimeUtils.millis();
    }

    public void autoHidePlayer(){
        if(TimeUtils.millis() - lastRevealTime > REVEALMS){
            setVisible(false);
        }
    }

    public void damageStun(){
        revealPlayer();
        lastDamagedTime = TimeUtils.millis();
    }

    public void prepareAnimation(PlayerColor color){
        String atlasFile = null;
        switch(color){
            case BLUE:
            atlasFile = "player/BluePlayer.atlas";
                break;
            case GREEN:
            atlasFile = "player/GreenPlayer.atlas";
                break;
            case NONE:
                break;
            case RED:
            atlasFile = "player/RedPlayer.atlas";
                break;
            case YELLOW:
            atlasFile = "player/YellowPlayer.atlas";
                break;
            default:
                break;
            
        }
        if(atlasFile != null){
            charAtlas = new TextureAtlas(Gdx.files.internal(atlasFile));
            walkupArray = charAtlas.findRegions("up");
            walkdownArray = charAtlas.findRegions("down");
            walkleftArray = charAtlas.findRegions("left");
            walkrightArray = charAtlas.findRegions("right");
            walkup = new Animation<TextureRegion>(1f/10f, walkupArray, PlayMode.LOOP);
            walkdown = new Animation<TextureRegion>(1f/10f, walkdownArray, PlayMode.LOOP);
            walkleft = new Animation<TextureRegion>(1f/10f, walkleftArray, PlayMode.LOOP);
            walkright = new Animation<TextureRegion>(1f/10f, walkrightArray, PlayMode.LOOP);
            attackup = charAtlas.findRegion("upattack");
            attackdown = charAtlas.findRegion("downattack");
            attackleft = charAtlas.findRegion("leftattack");
            attackright = charAtlas.findRegion("rightattack");
        }
    }
}
