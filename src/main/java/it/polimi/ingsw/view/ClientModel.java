package it.polimi.ingsw.view;

//TODO: reduce size and complexity to the minimum

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import it.polimi.ingsw.model.board.AmmoSquare;
import it.polimi.ingsw.model.board.WeaponSquare;
import it.polimi.ingsw.model.cards.AmmoTile;
import it.polimi.ingsw.model.cards.Weapon;
import it.polimi.ingsw.model.exceptions.NotAvailableAttributeException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class managing the client's model
 *
 * @author  marcobaga
 */
public class ClientModel {

    private List<SimpleSquare> squares;
    private List<SimplePlayer> players;

    private int weaponCardsLeft;
    private int powerUpCardsLeft;
    private int ammoTilesLeft;

    private int mapID;
    private boolean[][] leftWalls;
    private boolean[][] topWalls;

    private int currentPlayerId;
    private List<SimplePlayer> killShotTrack;
    private int skullsLeft;
    private List<String> powerUpInHand;
    private List<String> colorPowerUpInHand;
    private int playerID;

    private static final Logger LOGGER = Logger.getLogger("clientLogger");

    //TODO ADD PLAYER ID AND NUMBER OF DEATHS

    public ClientModel(){}

    /**
     * A simplified version of Square, containing only the things the user should see.
     */
    public class SimpleSquare {


        public SimpleSquare(int id, boolean spawnPoint, List<SimpleWeapon> weapons, int blueAmmo, int redAmmo, int yellowAmmo, boolean powerup) {
            this.id = id;
            this.spawnPoint = spawnPoint;
            this.weapons = weapons;
            this.blueAmmo = blueAmmo;
            this.redAmmo = redAmmo;
            this.yellowAmmo = yellowAmmo;
            this.powerup = powerup;
        }

        int id;
        boolean spawnPoint;
        List<SimpleWeapon> weapons;
        int blueAmmo;
        int redAmmo;
        int yellowAmmo;
        boolean powerup;

        public int getBlueAmmo() {
            return blueAmmo;
        }
        public void setBlueAmmo(int blueAmmo) {
            this.blueAmmo = blueAmmo;
        }
        public int getRedAmmo() {
            return redAmmo;
        }
        public void setRedAmmo(int redAmmo) {
            this.redAmmo = redAmmo;
        }
        public int getYellowAmmo() {
            return yellowAmmo;
        }
        public void setYellowAmmo(int yellowAmmo) {
            this.yellowAmmo = yellowAmmo;
        }
        public boolean isPowerup() {
            return powerup;
        }
        public void setPowerup(boolean powerup) {this.powerup = powerup;}
        public void SetId(int id) { this.id = id;  }
        public int getId(){return id;}
        public boolean isSpawnPoint() {return spawnPoint;}
        public List<SimpleWeapon> getWeapons() {       return weapons;    }
        public void setWeapons(List<SimpleWeapon> weapons) {   this.weapons = weapons; }

        public void removeWeapon(String name){
            for(SimpleWeapon w : weapons){
                if(w.getName()==name){
                    weapons.remove(w);
                    return;
                }
            }
        }

    }

    /**
     * A simplified version of Player, containing only the other players information that the user should see.
     */
    public class SimplePlayer{
        private int id;
        private String color;
        private int cardNumber;
        private List<Integer> damage;
        private List<Integer> marks;
        private List<SimpleWeapon> weapons;
        private SimpleSquare position;
        private String username;
        private int blueAmmo;
        private int redAmmo;
        private int yellowAmmo;
        private boolean flipped;
        private boolean inGame;
        private int points;
        private int deaths;

        public SimplePlayer(int id, String color, int cardNumber, List<Integer> damage, List<Integer> marks, List<SimpleWeapon> weapons, SimpleSquare position, String username, int blueAmmo, int redAmmo, int yellowAmmo, boolean inGame, boolean flipped, int points, int deaths) {
            this.id = id;
            this.color = color;
            this.cardNumber = cardNumber;
            this.damage = damage;
            this.marks = marks;
            this.weapons = weapons;
            this.position = position;
            this.username = username;
            this.blueAmmo = blueAmmo;
            this.redAmmo = redAmmo;
            this.yellowAmmo = yellowAmmo;
            this.inGame = inGame;
            this.flipped = flipped;
            this.points = points;
            this.deaths = deaths;
        }

        public boolean isInGame() { return inGame; }

        public boolean isFlipped() { return flipped; }

        public void flip(){
            this.flipped = !flipped;
        }

        public void setInGame(boolean inGame){this.inGame = inGame;}

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(int cardNumber) {
            this.cardNumber = cardNumber;
        }

        public List<Integer> getDamageID() {
            return damage;
        }

        public void setDamage(List<Integer> damage) {
            this.damage = damage;
        }

        public List<Integer> getMarksID() {
            return marks;
        }

        public void setMarks(List<Integer> marks) {
            this.marks = marks;
        }

        public List<SimpleWeapon> getWeapons() {
            return weapons;
        }

        public void setWeapons(List<SimpleWeapon> weapons) {
            this.weapons = weapons;
        }

        public SimpleSquare getPosition() {
            return position;
        }

        public void setPosition(SimpleSquare position) {
            this.position = position;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getPoints() {return points; }

        public void setPoints(int points) {this.points = points;}

        public int getDeaths() {return deaths;}

        public void setDeaths(int deaths) {this.deaths = deaths;}

        public void addDeath(){this.deaths++;}

        /**
         * Complex getters and setters
         */
        public List<SimplePlayer> toSimplePlayerList(List<Integer> IDs, List<SimplePlayer> players){

            List<SimplePlayer> toReturn = new ArrayList();

            for (Integer i: IDs){
                for(SimplePlayer p : players)
                    if(p.getId()==i)
                        toReturn.add(p);
            }
            return toReturn;
        }

        public List<SimplePlayer> getDamage(List<SimplePlayer> players){ return toSimplePlayerList (damage, players); }

        public List<SimplePlayer> getMark(List<SimplePlayer> players){ return toSimplePlayerList (marks, players); }

        public void pickUpWeapon(String name){
            System.out.println("vfffffffffffffffffffffffh");
            System.out.println(this);
            System.out.println(this.position);
            System.out.println(this.position.getWeapons());

            for(SimpleWeapon w : this.position.getWeapons()){
                if(w.getName().equals(name)){
                    this.position.getWeapons().remove(w);
                    this.weapons.add(w);
                    return;
                }
            }
        }

        public void discardWeapon(String name){
            for(SimpleWeapon w : this.position.getWeapons()){
                if(w.getName().equals(name)){
                    this.position.getWeapons().add(w);
                    this.weapons.remove(w);
                    return;
                }
            }
        }

        public void addAmmo(int b, int r, int y){
            blueAmmo=blueAmmo+b;
            redAmmo=redAmmo+r;
            yellowAmmo=yellowAmmo+y;
        }

        public void subAmmo(int b, int r, int y){
            blueAmmo=blueAmmo-b;
            redAmmo=redAmmo-r;
            yellowAmmo=yellowAmmo-y;
        }

        public void setAmmo(int b, int r, int y){
            blueAmmo=b;
            redAmmo=r;
            yellowAmmo=y;
        }

        public SimpleWeapon getWeapon(String name){
            for(SimpleWeapon s : weapons){
                if(s.getName()==name){
                    return s;
                }
            }
            return new SimpleWeapon("error", false);
        }

        public int getBlueAmmo() {
            return blueAmmo;
        }

        public int getRedAmmo() {
            return redAmmo;
        }

        public int getYellowAmmo() {
            return yellowAmmo;
        }
    }


    /**
     * A simplified version of Weapon, containing only the things the user should see.
     */
    public class SimpleWeapon{
        String name;
        boolean loaded;

        public SimpleWeapon(String name, boolean loaded) {
            this.name = name;
            this.loaded = loaded;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isLoaded() {
            return loaded;
        }

        public void setLoaded(boolean loaded) {
            System.out.println("loading weapon in client model");
            System.out.println(loaded);
            this.loaded = loaded;
        }
    }


    public List<SimpleSquare> getSquares() {
        return squares;
    }

    public void setSquares(List<SimpleSquare> squares) {
        this.squares = squares;
    }

    public List<SimplePlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<SimplePlayer> players) {
        this.players = players;
    }

    public int getWeaponCardsLeft() {
        return weaponCardsLeft;
    }

    public void setWeaponCardsLeft(int weaponCardsLeft) {
        this.weaponCardsLeft = weaponCardsLeft;
    }

    public int getPowerUpCardsLeft() {
        return powerUpCardsLeft;
    }

    public void setPowerUpCardsLeft(int powerUpCardsLeft) {
        this.powerUpCardsLeft = powerUpCardsLeft;
    }

    public int getAmmoTilesLeft() {
        return ammoTilesLeft;
    }

    public void setAmmoTilesLeft(int ammoTilesLeft) {
        this.ammoTilesLeft = ammoTilesLeft;
    }

    public int getMapID() {
        return mapID;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

    public SimplePlayer getCurrentPlayer() {
        return getPlayer(currentPlayerId);
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public List<SimplePlayer> getKillShotTrack() {
        return killShotTrack;
    }

    public void setKillShotTrack(List<SimplePlayer> killShotTrack) {
        this.killShotTrack = killShotTrack;
    }

    public List<String> getPowerUpInHand() {
        return powerUpInHand;
    }

    public void setPowerUpInHand(List<String> powerUpInHand) {
        this.powerUpInHand = powerUpInHand;
    }

    public List<String> getColorPowerUpInHand() {return colorPowerUpInHand;}

    public void setColorPowerUpInHand(List<String> colorPowerUpInHand) {this.colorPowerUpInHand = colorPowerUpInHand;}

    public int getSkullsLeft() {return skullsLeft;}

    public void setSkullsLeft(int skullsLeft) {this.skullsLeft = skullsLeft;}

    public int getPlayerID() {return playerID;}

    public void setPlayerID(int playerID) {this.playerID = playerID;}

    public boolean[][] getLeftWalls() { return leftWalls;}

    public void setLeftWalls(boolean[][] leftWalls) { this.leftWalls = leftWalls; }

    public boolean[][] getTopWalls() { return topWalls; }

    public void setTopWalls(boolean[][] topWalls) { this.topWalls = topWalls;}

    public void removeSkulls(int n){
        //do something with killshottrack
    }

    public void moveTo(int player, int square) {
        System.out.println(player);
        System.out.println(square);
        SimplePlayer p = getPlayer(player);
        SimpleSquare s = getSquare(square);
        System.out.println(p);
        System.out.println(s);
        p.setPosition(s);
    }

    public void flip(int player) {
        getPlayer(player).flip();
    }

    public void damage(int player, JsonArray damage){
        List<Integer> list = getPlayer(player).getDamageID();
        list.clear();
        for(JsonElement j : damage){
            list.add((j.getAsInt()));
        }
    }

    public void mark(int player, JsonArray marks){
        List<Integer> list = getPlayer(player).getMarksID();
        list.clear();
        for(JsonElement j : marks){
            list.add((j.getAsInt()));
        }
    }

    public SimplePlayer getPlayer(int id){
        for(SimplePlayer s : players){
            if(s.getId() == id){
                return s;
            }
        }
        return players.get(0);
        //watch out
    }

    public SimpleSquare getSquare(int id){
        for(SimpleSquare s : squares){
            if(s.getId()==id){
                return s;
            }
        }
        return squares.get(0);
    }

    public static SimpleWeapon toSimpleWeapon(Weapon weapon){
        return new ClientModel().new SimpleWeapon(weapon.toString(), weapon.isLoaded());
    }

    public static SimpleSquare toSimpleSquare(WeaponSquare square){
        List<SimpleWeapon> weapons = new ArrayList<>();
        for (Weapon weapon : square.getWeapons()){
            weapons.add(toSimpleWeapon(weapon));
        }
        return new ClientModel().new SimpleSquare(square.getId(), true, weapons, 0, 0, 0, false);
    }

    public static SimpleSquare toSimpleSquare(AmmoSquare square){
        int redAmmo =0;
        int blueAmmo =0;
        int yellowAmmo =0;
        boolean powerUp = false;
        try {
            AmmoTile ammoTile = square.getAmmoTile();
            redAmmo = ammoTile.getAmmoPack().getRedAmmo();
            blueAmmo = ammoTile.getAmmoPack().getBlueAmmo();
            yellowAmmo = ammoTile.getAmmoPack().getYellowAmmo();
            powerUp = ammoTile.hasPowerUp();

        } catch (NotAvailableAttributeException e){
            LOGGER.log(Level.FINE, "No ammotile on the square: 0,0,0, false is displayed.");
        }
        return new ClientModel().new SimpleSquare(square.getId(), false, new ArrayList<>(), redAmmo, blueAmmo, yellowAmmo, powerUp);
    }

    public static String getEscapeCode(String color){
        if(color==null){
            return "\u001b[35m";
        }
        switch(color){
            case "black": return "\u001b[30m";
            case "red": return "\u001b[31m";
            case "green": return "\u001b[32m";
            case "yellow": return "\u001b[33m";
            case "blue": return "\u001b[34m";
            case "purple": return "\u001b[35m";
            case "cyan": return "\u001b[36m";
            case "grey": return "\u001b[37m";           //actually white
            case "reset": return "\u001b[0m";
            default: return "";
        }
    }
}