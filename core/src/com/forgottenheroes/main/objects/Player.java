package com.forgottenheroes.main.objects;

import java.util.ArrayList;
import java.lang.Math;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;
import com.forgottenheroes.main.Equipment;
import com.forgottenheroes.main.FHeroes;
import com.forgottenheroes.main.Pickup;

public class Player extends GameEntity{
    private int playerNumber;
    private String name;
    private Color color;
    private State state;
    private Direction currentDirection;

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
    
    
    
    private ArrayList<Equipment> inventory;
    private ArrayList<int[]> killData;

    private final int ATTACKCDMS = 300;
    
    public enum PlayerNumber{
        PLAYER1,
        PLAYER2
    }

    public enum State{
        ATTACKING,
        IDLE,
        DEFEATED
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
        setHeight(40);
        setWidth(40);
        setMaxHP(200);
        setHp(getMaxHP());
        setScore(0);
        setWins(0);
        setAttack(40);
        setAtkRange(100);
        setAtkWidth(40);
        setMoveSpeed(5);
        setState(State.IDLE);
        switch(playerNumber){
            case 1:
            color = Color.RED;
            setName("Player 1");
            break;
            case 2:
            color = Color.BLUE;
            setName("Player 2");
            break;
        }
        this.playerNumber = playerNumber;
    }

    public Player(int[] spawncoords, Color color, int playerNumber){
        super(spawncoords);
        setGridCoords(spawncoords);
        setHeight(40);
        setWidth(40);
        setMaxHP(200);
        setHp(getMaxHP());
        setName("player");
        setScore(0);
        setWins(0);
        setAttack(10);
        setAtkRange(100);
        setAtkWidth(40);
        setMoveSpeed(5);
        setState(State.IDLE);
        this.color = color;
        this.playerNumber = playerNumber;
    }

    @Override
    public void render(FHeroes game) {
        if(getState() != State.DEFEATED){
            if(isValidXMovement()) updateXPos();
            if(isValidYMovement()) updateYPos();
            game.getShapeRenderer().begin();
            game.getShapeRenderer().setColor(color);
            game.getShapeRenderer().set(ShapeType.Filled);
            game.getShapeRenderer().circle(getX(), getY(), getHeight() / 2);
            game.getShapeRenderer().end();
        }

    }
    
    public boolean isValidXMovement(){
        if(getState() != State.DEFEATED){
            int newX = getX() + getVelX();
            int newY = getY();
            int[] coords = getGridCoords(newX, newY);
            if(coords != null) return FHeroes.getObjectManager().getMap().isPassable(coords);
            else return false;
        }
        else {
            return false;
        }
    }

    public boolean isValidYMovement(){
        if(getState() != State.DEFEATED){
            int newX = getX();
            int newY = getY() + getVelY();
            int[] coords = getGridCoords(newX, newY);
            if(coords != null) return FHeroes.getObjectManager().getMap().isPassable(coords);
            else return false;
        }
        else {
            return false;
        }
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
    }

    public void resetToNewGame(){
        setGridCoords(getInitPos());
        setHp(getMaxHP());
        setScore(0);
        setWins(0);
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

    public void performAttack(Direction dir){
        if(!isAttacking()){
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
            ArrayList<Player> playerList = FHeroes.getObjectManager().getPlayerList();
            double magdirv = Math.sqrt(Math.pow(dirv[0], 2) + Math.pow(dirv[1], 2));
            double[] unitdirv = {dirv[0] / magdirv, dirv[1] / magdirv};
            double magorthodirv = Math.sqrt(Math.pow(orthodirv[0], 2) + Math.pow(orthodirv[1], 2));
            double[] unitorthodirv = {orthodirv[0] / magorthodirv, orthodirv[1] / magorthodirv};
            // loop through all players in list that are not attacking player
            for(Player target:playerList){
                if(target.getPlayerNumber() != this.getPlayerNumber() && !target.checkPlayerDefeated()){
                    posv[0] = target.getX() - this.getX();
                    posv[1] = target.getY() - this.getY();
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
                            new DamageNumbers(target.getX() - 10, target.getY() + 50, 0, 1, damage);
                            damage = 0;
                            
                        }
                    }
                    if(target.checkPlayerDefeated()){
                        if(FHeroes.getObjectManager().checkRoundOver()){
                            FHeroes.getObjectManager().setRoundOver();
                        }
                        
                    }
                }
            }
        }
    }

    public void startAttackCD(){
        setLastAttackTime(TimeUtils.millis());
        setState(State.ATTACKING);
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
}
