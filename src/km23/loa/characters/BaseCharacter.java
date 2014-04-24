package km23.loa.characters;

/**
 * Created by mosk on 13.04.14.
 */

import java.util.ArrayList;
import java.util.Observable;

public class BaseCharacter  extends Observable{
    private int x;
    private int y;
    private ArrayList<km23.loa.characters.effects.BaseEffect> effects;
    private ArrayList<km23.loa.characters.items.Weapon> weapons;
    private int healthPoints;
    private int manaPoints;
    private int stamina;
    private int velocity;
    private int level;
    private int armor;
    private int melleAttack;
    private int rangedAttack;
    private int regenerationHealthPoints;
    private int regenerationManaPoints;
    private int regenerationStamina;


}
