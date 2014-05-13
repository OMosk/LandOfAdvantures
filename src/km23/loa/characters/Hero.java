package km23.loa.characters;

import km23.loa.locations.Location;

/**
 * Created by mosk on 13.04.14.
 */
public class Hero extends BaseCharacter {

    protected int xp;

    public Hero(Location location, float x, float y, int radius, float velocity) {
        super(location, x, y, radius, velocity);
    }
    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

}
