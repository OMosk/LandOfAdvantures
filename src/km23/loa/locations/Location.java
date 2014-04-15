package km23.loa.locations;

import km23.loa.characters.Hero;
import km23.loa.characters.Monster;

import java.util.ArrayList;

/**
 * Created by mosk on 13.04.14.
 */
public class Location {
    protected int width, height;
    protected ArrayList<Hero> heroes;
    protected ArrayList<Monster> monsters;
    protected LocationMap locationMap;


    public Location()
    {
        width = 300;
        height = 300;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
