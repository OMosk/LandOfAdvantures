package km23.loa.characters;

import km23.loa.locations.Location;

/**
 * Created by mosk on 13.04.14.
 */
public class Monster extends BaseCharacter {
    public Monster(Location location, float x, float y, int radius, float velocity) {
        super(location, x, y, radius, velocity);
    }
    public void update(int msec){
        if(!move(msec)) setDirection( (getDirection() + 4) % 8);
    }
}
