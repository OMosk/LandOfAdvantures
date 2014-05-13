package km23.loa.locations;

import km23.loa.characters.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.util.*;

/**
 * Created by mosk on 13.04.14.
 */
public class Location  implements Observer {
    protected int width, height;
    protected ArrayList<Hero> heroes = new ArrayList<Hero>();
    protected ArrayList<Monster> monsters = new ArrayList<Monster>();
    protected ArrayList<FriendlyCharacter> friendlyCharacters = new ArrayList<FriendlyCharacter>();
    protected LocationMap locationMap;

    protected HashMap<Integer, JSONObject> charactersChanges = new HashMap<Integer, JSONObject>();

    public Location()
    {
        width = 640;
        height = 480;
        Monster m = new Monster(this, 300, 300, 10, 0.1f);
        addMonster(m);
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public boolean isLinearPathFree(BaseCharacter character, float x, float y){
        //VERY IMPORTANT
        if(x + character.getRadius() <= width
                && x - character.getRadius() >= 0
                && y + character.getRadius() <= height
                && y - character.getRadius() >= 0) return true;
        else return false;
    }
    public void addHero(Hero hero){

        heroes.add(hero);
        hero.addObserver(this);
    }
    public void addMonster(Monster monster){
        monsters.add(monster);
        monster.addObserver(this);
    }
    public void update(int msec){
        //charactersChanges.clear();

        for(Hero hero: heroes)
            hero.update(msec);
        for(Monster monster: monsters)
            monster.update(msec);
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<FriendlyCharacter> getFriendlyCharacters() {
        return friendlyCharacters;
    }
    public void update(Observable o, Object arg){
        try{
            BaseCharacter character = (BaseCharacter) o;
            CharacterStateChange stateChange = (CharacterStateChange) arg;

            processCharacterStateChange(character, stateChange);

        }
        catch(ClassCastException e){
        }

    }
    public JSONArray getAllCharactersChangesJSON()
    {
        JSONArray characters = new JSONArray();
        Iterator it = charactersChanges.keySet().iterator();
        while(it.hasNext()){
            Integer id =(Integer)it.next();
            JSONObject characterChanges = (JSONObject)charactersChanges.get(id).clone();
            characterChanges.put("id", id);
            characters.add(characterChanges);
        }
        return characters;
    }
    public JSONArray getAllCharactersJSON(){
        JSONArray characters = new JSONArray();
        for(Hero hero: heroes){
            JSONObject jsonHero = new JSONObject();
            jsonHero.put("type", "hero");

            jsonHero.put("id", hero.getId());
            jsonHero.put("x", hero.getX());
            jsonHero.put("y", hero.getY());
            jsonHero.put("direction", hero.getDirection());
            jsonHero.put("healthPoints", hero.getHealthPoints());
            jsonHero.put("manaPoints", hero.getManaPoints());
            jsonHero.put("staminaPoints", hero.getStamina());
            jsonHero.put("maxHealthPoints", hero.getMaxHealthPoints());
            jsonHero.put("maxManaPoints", hero.getMaxManaPoints());
            jsonHero.put("maxStaminaPoints", hero.getMaxStamina());
            jsonHero.put("moving", hero.isMoving());
            jsonHero.put("weaponAttacking", hero.isWeaponAttacking());
            jsonHero.put("magicAttacking", hero.isMagicAttacking());

            characters.add(jsonHero);
        }

        for(Monster monster: monsters){
            JSONObject jsonMonster = new JSONObject();
            jsonMonster.put("type", "hero");

            jsonMonster.put("id", monster.getId());
            jsonMonster.put("x", monster.getX());
            jsonMonster.put("y", monster.getY());
            jsonMonster.put("direction", monster.getDirection());
            jsonMonster.put("healthPoints", monster.getHealthPoints());
            jsonMonster.put("manaPoints", monster.getManaPoints());
            jsonMonster.put("staminaPoints", monster.getStamina());
            jsonMonster.put("maxHealthPoints", monster.getMaxHealthPoints());
            jsonMonster.put("maxManaPoints", monster.getMaxManaPoints());
            jsonMonster.put("maxStaminaPoints", monster.getMaxStamina());
            jsonMonster.put("moving", monster.isMoving());
            jsonMonster.put("weaponAttacking", monster.isWeaponAttacking());
            jsonMonster.put("magicAttacking", monster.isMagicAttacking());

            characters.add(jsonMonster);
        }


        return characters;
    }

    private void processCharacterStateChange(BaseCharacter character, CharacterStateChange stateChange) {
        JSONObject characterChange = charactersChanges.get(character.getId());

        if(characterChange  == null) {
            characterChange = new JSONObject();
            charactersChanges.put(character.getId(), characterChange);
        }

        switch (stateChange){
            case POSITION:
                characterChange.put("x", character.getX());
                characterChange.put("y", character.getY());
                break;
            case DIRECTION:
                characterChange.put("direction", character.getDirection());
                break;
            case HEALTH_POINTS:
                characterChange.put("healthPoints", character.getHealthPoints());
                break;
            case MANA_POINTS:
                characterChange.put("manaPoints", character.getManaPoints());
                break;
            case STAMINA_POINTS:
                characterChange.put("staminaPoints", character.getStamina());
                break;
            case MAX_HEALTH_POINTS:
                characterChange.put("maxHealthPoints", character.getMaxHealthPoints());
                break;
            case MAX_MANA_POINTS:
                characterChange.put("maxManaPoints", character.getMaxManaPoints());
                break;
            case MAX_STAMINA_POINTS:
                characterChange.put("maxStaminaPoints", character.getMaxStamina());
                break;
            case LEVEL:
                characterChange.put("level", character.getLevel());
                break;
            case MOVING:
                characterChange.put("moving", character.isMoving());
                break;
            case WEAPON_ATTACKING:
                characterChange.put("weaponAttacking", character.isWeaponAttacking());
                break;
            case MAGIC_ATTACKING:
                characterChange.put("magicAttacking", character.isMagicAttacking());
                break;
            case XP:
                characterChange.put("xp", ((Hero)character).getXp());
                break;
        }
    }
    public void clearCharactersStateChanges(){
        charactersChanges.clear();
    }

}
