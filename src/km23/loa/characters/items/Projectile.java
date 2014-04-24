package km23.loa.characters.items;

import km23.loa.characters.BaseCharacter;
import km23.loa.locations.Location;

import java.util.Observable;

/**
 * Created by mosk on 22.04.14.
 */
public abstract class Projectile extends Observable {
    protected int x, y;
    protected int velocity;
    protected short direction;
    protected Location location;

    public void update(int msec){
        switch (direction){
            case 0: break;
            case 1: break;
            case 2: break;
            case 3: break;
            case 4: break;
            case 5: break;
            case 6: break;
            case 7: break;


        }
    }
    abstract void act(BaseCharacter character);
}
