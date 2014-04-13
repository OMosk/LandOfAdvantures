package km23.loa.characters.effects;

import km23.loa.characters.BaseCharacter;

/**
 * Created by mosk on 13.04.14.
 */
public class BaseEffect {
    private String name;
    private EffectStatus status;

    public BaseEffect() {
         String name;
         String status;
    }

    public void act(BaseCharacter character) {
    }

    public String getName() {
        return name;
    }
}
