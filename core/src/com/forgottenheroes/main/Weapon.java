package com.forgottenheroes.main;

public class Weapon extends Equipment{
    private int weaponDamage;
    private int[] meleeRange;

    public Weapon(int weaponDamage, int[] meleeRange, int durability){
        super(durability);
    }

    public void setWeaponDamage(int weaponDamage){
        this.weaponDamage = weaponDamage;
    }

    public int getWeaponDamage(){
        return weaponDamage;
    }

    public void setMeleeRange(int x, int y){
        this.meleeRange = new int[]{x, y};
    }

    public int[] getMeleeRange(){
        return meleeRange;
    }
}
