package km23.loa.characters;

/**
 * Created by mosk on 13.04.14.
 */

import km23.loa.locations.Location;
import km23.loa.characters.CharacterStateChange;

import java.util.ArrayList;
import java.util.Observable;

public abstract class BaseCharacter  extends Observable{
    static protected int count = 0;

    protected final int id = ++count;

    protected final float SIN_PI_4 = 0.70710678f;
    protected float x;
    protected float y;
    protected int radius;
    protected int direction;

    protected boolean moving;
    protected boolean weaponAttacking;
    protected boolean magicAttacking;
    protected ArrayList<km23.loa.characters.effects.BaseEffect> effects;
    protected ArrayList<km23.loa.characters.items.Weapon> weapons;
    protected boolean alive;
    protected int healthPoints;
    protected int maxHealthPoints;
    protected int manaPoints;
    protected int maxManaPoints;
    protected int stamina;
    protected int maxStamina;
    protected float velocity;
    protected float defaultVelocity;
    protected int level;
    protected int armor;
    protected int meleeAttack;
    protected int rangedAttack;
    protected int regenerationHealthPoints;
    protected int regenerationManaPoints;
    protected int regenerationStamina;


    private Location location;
    public BaseCharacter(Location location, float x, float y, int radius, float defaultVelocity){
        this.location = location;
        setXY(x,y);
        setRadius(radius);
        setDefaultVelocity(defaultVelocity);
        direction = 0;
        level = 1;
        alive = true;
    }
    public void update(int msec){
        if(moving) move(msec);
        //System.out.println("Character" + id + " updating");
    }
    public boolean move(int msec){
        boolean moved = false;
        float old_x = x, old_y = y;
        switch (direction){
            case 0:{
                if(location.isLinearPathFree(this, x, y - velocity * msec)){
                    setY(y - velocity * msec);
                    moved = true;
                }
                break;
            }
            case 1:{
                if(location.isLinearPathFree(this, x + SIN_PI_4 * velocity * msec, y - SIN_PI_4 * velocity * msec)){
                    setXY(x + SIN_PI_4 * velocity * msec, y - SIN_PI_4 * velocity * msec);
                    moved = true;
                }
                break;
            }
            case 2:{
                if(location.isLinearPathFree(this, x + velocity * msec, y)){
                    setX(x + velocity * msec);
                    moved = true;
                }
                break;
            }
            case 3:{
                if(location.isLinearPathFree(this, x + SIN_PI_4 * velocity * msec, y + SIN_PI_4 * velocity * msec)){
                    setXY(x + SIN_PI_4 * velocity * msec, y + SIN_PI_4 * velocity * msec);
                    moved = true;
                }
                break;
            }
            case 4:{
                if(location.isLinearPathFree(this, x, y + velocity * msec)){
                    setY(y + velocity * msec);
                    moved = true;
                }
                break;
            }
            case 5:{
                if(location.isLinearPathFree(this, x - SIN_PI_4 * velocity * msec, y + SIN_PI_4 * velocity * msec)){
                    setXY(x - SIN_PI_4 * velocity * msec, y + SIN_PI_4 * velocity * msec);
                    moved = true;
                }
                break;
            }
            case 6:{
                if(location.isLinearPathFree(this, x - velocity * msec, y)){
                    setX(x - velocity * msec);
                    moved = true;
                }
                break;
            }
            case 7:{
                if(location.isLinearPathFree(this, x - SIN_PI_4 * velocity * msec, y - SIN_PI_4 * velocity * msec)){
                    setXY(x - SIN_PI_4 * velocity * msec, y - SIN_PI_4 * velocity * msec);
                    moved = true;
                }
                break;
            }

        }
        if(old_x!=x || old_y!=y) {
            setChanged();
            notifyObservers(CharacterStateChange.POSITION);
        }
        //notifyObservers();
        return moved;
    }
    public void moveInDirection(int msec, int direction){
        setDirection(direction);
        move(msec);
    }
    public void setXY(float x, float y){
        this.x = x;
        this.y = y;
        notifyObservers(CharacterStateChange.POSITION);
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        notifyObservers(CharacterStateChange.POSITION);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        notifyObservers(CharacterStateChange.POSITION);
    }

    public int getRadius() {
        return radius;
    }


    public int getDirection() {
        return direction;

    }

    public void setDirection(int direction) {
        this.direction = direction;
        notifyObservers(CharacterStateChange.DIRECTION);
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }


    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
        notifyObservers(CharacterStateChange.MAX_HEALTH_POINTS);
    }

    public int getMaxManaPoints() {
        return maxManaPoints;
    }

    public void setMaxManaPoints(int maxManaPoints) {
        this.maxManaPoints = maxManaPoints;
        notifyObservers(CharacterStateChange.MAX_MANA_POINTS);
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
        notifyObservers(CharacterStateChange.MAX_STAMINA_POINTS);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
        if(healthPoints <= 0){
            alive = false;
            this.healthPoints = 0;
        }
        notifyObservers(CharacterStateChange.HEALTH_POINTS);
    }

    public int getManaPoints() {
        return manaPoints;
    }

    public void setManaPoints(int manaPoints) {
        this.manaPoints = manaPoints;
        if(manaPoints < 0){
           this.manaPoints = 0;
        }
        notifyObservers(CharacterStateChange.MANA_POINTS);
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
        if(stamina < 0) this.stamina = 0;
        notifyObservers(CharacterStateChange.STAMINA_POINTS);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        notifyObservers(CharacterStateChange.LEVEL);
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getMeleeAttack() {
        return meleeAttack;
    }

    public void setMeleeAttack(int meleeAttack) {
        this.meleeAttack = meleeAttack;
    }

    public int getRangedAttack() {
        return rangedAttack;
    }

    public void setRangedAttack(int rangedAttack) {
        this.rangedAttack = rangedAttack;
    }

    public int getRegenerationHealthPoints() {
        return regenerationHealthPoints;
    }

    public void setRegenerationHealthPoints(int regenerationHealthPoints) {
        this.regenerationHealthPoints = regenerationHealthPoints;
    }

    public int getRegenerationStamina() {
        return regenerationStamina;
    }

    public void setRegenerationStamina(int regenerationStamina) {
        this.regenerationStamina = regenerationStamina;
    }

    public int getRegenerationManaPoints() {
        return regenerationManaPoints;
    }

    public void setRegenerationManaPoints(int regenerationManaPoints) {
        this.regenerationManaPoints = regenerationManaPoints;
    }



    public boolean isAlive() {
        return alive;
    }


    public float getDefaultVelocity() {
        return defaultVelocity;
    }

    public void setDefaultVelocity(float defaultVelocity) {
        this.defaultVelocity = defaultVelocity;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
        setChanged();
        notifyObservers(CharacterStateChange.MOVING);
        System.out.println("I`m change my moving state");
    }

    public boolean isWeaponAttacking() {
        return weaponAttacking;

    }

    public void setWeaponAttacking(boolean weaponAttacking) {
        this.weaponAttacking = weaponAttacking;
        notifyObservers(CharacterStateChange.WEAPON_ATTACKING);
    }

    public boolean isMagicAttacking() {
        return magicAttacking;
    }

    public void setMagicAttacking(boolean magicAttacking) {
        this.magicAttacking = magicAttacking;
        notifyObservers(CharacterStateChange.MAGIC_ATTACKING);
    }

    public int getId() {
        return id;
    }

}
