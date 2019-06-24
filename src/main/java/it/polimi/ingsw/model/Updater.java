package it.polimi.ingsw.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.cards.AmmoPack;
import it.polimi.ingsw.model.cards.PowerUp;
import it.polimi.ingsw.model.cards.Weapon;
import it.polimi.ingsw.model.exceptions.NotAvailableAttributeException;
import it.polimi.ingsw.view.ClientModel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class containing static methods to build update messages to be sent to clients as JsonObjects
 *
 * @author marcobaga, BassaniRiccardo
 */

public class Updater {

    private static final Logger LOGGER = Logger.getLogger("serverLogger");

    public static final String UPD_HEADER = "UPD";

    public static final String RELOAD_UPD = "reload";
    public static final String POWER_UP_DECK_REGEN_UPD ="powerUpDeckRegen";
    public static final String REMOVE_SKULL_UPD = "skullRemoved";
    public static final String DRAW_POWER_UP_UPD = "drawPowerUp";
    public static final String DISCARD_POWER_UP_UPD = "discardPowerUp";
    public static final String DISCARD_WEAPON_UPD = "discardWeapon";
    public static final String PICKUP_WEAPON_UPD = "pickupWeapon";
    public static final String USE_AMMO_UPD = "useAmmo";
    public static final String ADD_AMMO_UPD = "addAmmo";
    public static final String MOVE_UPD = "move";
    public static final String FLIP_UPD = "flip";
    public static final String ADD_DEATH_UPD = "addDeath";
    public static final String DAMAGE_UPD = "damage";
    public static final String MARK_UPD = "mark";
    public static final String ADD_WEAPON_UPD = "addWeapon";
    public static final String REMOVE_WEAPON_UPD = "removeWeapon";
    public static final String SET_IN_GAME_UPD = "setInGame";
    public static final String REMOVE_AMMO_TILE_UPD = "removeAmmoTile";
    public static final String MODEL_UPD = "model";

    public static final String HEAD_PROP = "head";
    public static final String TYPE_PROP = "type";

    public static final String PLAYER_PROP = "player";
    public static final String WEAPON_PROP = "weapon";
    public static final String SQUARE_PROP = "square";

    public static final String LOADED_PROP = "loaded";
    public static final String CARDS_NUMBER_PROP = "cardNumber";
    public static final String SKULL_NUMBER_PROP = "skullNumber";
    public static final String KILLER_PROP = "killer";
    public static final String OVERKILL_PROP = "overkill";
    public static final String POWER_UP_NAME_PROP = "powerUpName";
    public static final String POWER_UP_COLOR_PROP = "powerUpColor";
    public static final String RED_AMMO_PROP = "redAmmo";
    public static final String BLUE_AMMO_PROP = "blueAmmo";
    public static final String YELLOW_AMMO_PROP = "yellowAmmo";
    public static final String PLAYER_LIST_PROP = "playerList";
    public static final String BOOLEAN_PROP = "boolean";
    public static final String MODEL_PROP = "model";



    private Updater(){}

    /**
     * All get methods take as input specific parameter related to the particular update being translated and output a serialization of the update.
     */

    public static JsonObject get(String msg, Weapon w, boolean loaded) {
        JsonObject j = getFreshUpdate(msg);
        j.addProperty(WEAPON_PROP, w.getWeaponName().toString());
        j.addProperty(LOADED_PROP, loaded);
        //loaded
        return j;
    }

    public static JsonObject get(String msg, int quantity) {
        JsonObject j = getFreshUpdate(msg);
        j.addProperty(CARDS_NUMBER_PROP, quantity);
        //pDeckRegen
        return j;
    }

    public static JsonObject get(String msg, int quantity, Player p, boolean ok) {
        JsonObject j = getFreshUpdate(msg);
        j.addProperty(SKULL_NUMBER_PROP, quantity);
        j.addProperty(KILLER_PROP, p.getId());
        j.addProperty(OVERKILL_PROP, ok);
        return j;
        //skullRemoved
    }

    public static JsonObject get(String s, Player p, PowerUp powerUp) {
        JsonObject j = getFreshUpdate(s);
        j.addProperty(PLAYER_PROP, p.getId());
        j.addProperty(POWER_UP_NAME_PROP, powerUp.getName().toString());
        j.addProperty(POWER_UP_COLOR_PROP, powerUp.getColor().toStringLowerCase());
        return j;
        //drawPowerUp and discardPowerup
    }

    public static JsonObject get(String s, Player p, Weapon w) {
        JsonObject j = getFreshUpdate(s);
        j.addProperty(PLAYER_PROP, p.getId());
        j.addProperty(WEAPON_PROP, w.getWeaponName().toString());
        return j;
        //discardWeapon and pickUpWeapon
    }

    public static JsonObject get(String s, Player p, AmmoPack a) {
        JsonObject j = getFreshUpdate(s);
        j.addProperty(PLAYER_PROP, p.getId());
        j.addProperty(RED_AMMO_PROP, a.getRedAmmo());
        j.addProperty(BLUE_AMMO_PROP, a.getBlueAmmo());
        j.addProperty(YELLOW_AMMO_PROP, a.getYellowAmmo());
        return j;
        //useAmmo, addAmmo
    }

    public static JsonObject get(String s, Player p, Square square) {
        JsonObject j = getFreshUpdate(s);
        j.addProperty(PLAYER_PROP, p.getId());
        j.addProperty(SQUARE_PROP, square.getId());
        return j;
        //move
    }

    public static JsonObject get(String s, Player p) {
        JsonObject j = getFreshUpdate(s);
        j.addProperty(PLAYER_PROP, p.getId());
        return j;
        //flip, addDeath
    }

    public static JsonObject get(String s, Player p, List<Player> l) {
        JsonObject j = getFreshUpdate(s);
        j.addProperty(PLAYER_PROP, p.getId());
        JsonArray array = new JsonArray();
        for (Player shooter : l) {
            array.add(shooter.getId());
        }
        j.add(PLAYER_LIST_PROP, array);
        return j;
        //damaged, marked
    }

    public static JsonObject get(String s, Square sq, Weapon w) {
        JsonObject j = getFreshUpdate(s);
        j.addProperty(SQUARE_PROP, sq.getId());
        j.addProperty(WEAPON_PROP, w.toString());
        return j;
        //weaponRemoved & addWeapon
    }

    public static JsonObject get(String s, Player p, boolean in) {
        JsonObject j = getFreshUpdate(s);
        j.addProperty(PLAYER_PROP, p.getId());
        j.addProperty(BOOLEAN_PROP, in);
        return j;
        //setInGame
    }

    public static JsonObject get(String s, Square square){
        JsonObject j = getFreshUpdate(s);
        j.addProperty(SQUARE_PROP, square.getId());
        return j;
        //removeAmmoTile
    }

    //the following block can be used if we decide to send messages to revert a not confirmed action
    //right now this is carried out automatically when reverting changes on the server's model
    /*
    public static JsonObject getRevert(Board board) {
        JsonObject j = getFreshUpdate("revert");
        JsonArray playerArray = new JsonArray();
        JsonArray positionArray = new JsonArray();
        JsonArray damageArray = new JsonArray();
        JsonArray powerUpArray = new JsonArray();
        JsonArray blueAmmoArray = new JsonArray();
        JsonArray redAmmoArray = new JsonArray();
        JsonArray yellowAmmoArray = new JsonArray();
        for (Player p : board.getPlayers()) {
            playerArray.add(p.getId());
            try {
                positionArray.add(p.getPosition().getId());
            } catch (NotAvailableAttributeException ex) {
                //manage
            }
            JsonArray temp = new JsonArray();
            for (Player q : p.getDamages()) {
                temp.add(p.getId());
            }
            damageArray.add(temp);
            JsonArray temp2 = new JsonArray();
            for (PowerUp q : p.getPowerUpList()) {
                temp2.add(q.getName().toString());
            }
            powerUpArray.add(temp2);
            blueAmmoArray.add(p.getAmmoPack().getBlueAmmo());
            redAmmoArray.add(p.getAmmoPack().getRedAmmo());
            yellowAmmoArray.add(p.getAmmoPack().getYellowAmmo());
        }
        j.add("players", playerArray);
        j.add("positions", positionArray);
        j.add("damage", damageArray);
        j.add("powerup", powerUpArray);
        j.add("blueammo", blueAmmoArray);
        j.add("redammo", redAmmoArray);
        j.add("yellowammo", yellowAmmoArray);

        JsonArray weaponArray = new JsonArray();
        JsonArray loadedWeaponArray = new JsonArray();
        for (Weapon w : board.getCurrentPlayer().getWeaponList()) {
            weaponArray.add(w.getWeaponName().toString());
            if (w.isLoaded()) {
                loadedWeaponArray.add(w.getWeaponName().toString());
            }
        }
        j.add("weapons", weaponArray);
        j.add("loadedweapons", loadedWeaponArray);

        JsonArray squareArray = new JsonArray();
        JsonArray weaponsInSquareArray = new JsonArray();
        for (WeaponSquare s : board.getSpawnPoints()) {
            squareArray.add(s.getId());
            JsonArray temp = new JsonArray();
            for (Weapon w : s.getWeapons()) {
                temp.add(w.getWeaponName().toString());
            }
            weaponsInSquareArray.add(temp);
        }
        j.add("squares", squareArray);
        j.add("weaponsinsquare", weaponsInSquareArray);

        return j;
    }*/


    /**
     * Creates a message that updates the ClientModel of the specified player on the specified board and returns it.
     *
     * @param board         the game board.
     * @param player        the player who will receive the updated model.
     * @return              the updated model.
     */
    public static JsonObject getModel(Board board, Player player) {

        ClientModel cm = new ClientModel();

        //simpleSquares
        List<ClientModel.SimpleSquare> simpleSquares = new ArrayList<>();
        for (Square s : board.getMap()){
            if (board.getSpawnPoints().contains(s))     simpleSquares.add(ClientModel.toSimpleSquare((WeaponSquare)s));
            else  simpleSquares.add(ClientModel.toSimpleSquare((AmmoSquare) s));
        }
        cm.setSquares(simpleSquares);


        //simplePlayers, currentPlayer, killShotTrack
        List<ClientModel.SimplePlayer> simplePlayers = new ArrayList<>();
        List<ClientModel.SimplePlayer> killers = new ArrayList<>();

        for (Player p : board.getPlayers()){

            //create a new simplePlayer
            List<Integer> damages = new ArrayList<>();
            for (Player shooter : p.getDamages()){
                damages.add(shooter.getId());
            }
            List<Integer> marks = new ArrayList<>();
            for (Player marker : p.getMarks()){
                marks.add(marker.getId());
            }
            List<ClientModel.SimpleWeapon> weapons = new ArrayList<>();
            for (Weapon weapon : p.getWeaponList()){
                weapons.add(ClientModel.toSimpleWeapon(weapon));
            }
            ClientModel.SimpleSquare position = null;
            boolean isInGame = false;
            try {
                if (board.getSpawnPoints().contains(p.getPosition())) position = ClientModel.toSimpleSquare((WeaponSquare)p.getPosition());
                else position = ClientModel.toSimpleSquare((AmmoSquare)p.getPosition());
                isInGame = true;

            } catch (NotAvailableAttributeException e) {
                LOGGER.log(Level.FINE, "The player is not on the board, is in game remains false");
            }
            ClientModel.SimplePlayer simplePlayer = new ClientModel().new SimplePlayer(p.getId(), p.getstringColor(), p.getPowerUpList().size(), damages, marks, weapons, position, p.getUsername(), p.getAmmoPack().getBlueAmmo(), p.getAmmoPack().getRedAmmo(), p.getAmmoPack().getYellowAmmo(), isInGame, p.isFlipped(), p.getPoints(), p.getDeaths());
            simplePlayers.add(simplePlayer);


            //creates the killShotTrack
            try {
                for (Player killer : board.getKillShotTrack().getKillers()) {
                    if (killer.equals(p)) {
                        killers.add(board.getKillShotTrack().getKillers().indexOf(killer), simplePlayer);
                    }
                }
            } catch (NotAvailableAttributeException e){ LOGGER.log(Level.SEVERE, "NotAvailableAtribute exception thrown while getting the killshot track");}

        }
        cm.setPlayers(simplePlayers);
        cm.setCurrentPlayer(cm.getPlayer(board.getCurrentPlayer().getId()));
        cm.setPlayerID(player.getId());

        cm.setKillShotTrack(killers);
        try {
            cm.setSkullsLeft(board.getKillShotTrack().getSkullsLeft());
        } catch (NotAvailableAttributeException e) {
            LOGGER.log(Level.SEVERE, "Impossible to get the number of skulls left, all skulls removed.");
            cm.setSkullsLeft(0);
        }

        //decks size
        cm.setPowerUpCardsLeft(board.getPowerUpDeck().getDrawable().size());
        cm.setWeaponCardsLeft(board.getWeaponDeck().getDrawable().size());
        cm.setAmmoTilesLeft(board.getAmmoDeck().getDrawable().size());
        cm.setMapID(board.getId());
        cm.setLeftWalls(board.getLeftWalls());
        cm.setTopWalls(board.getTopWalls());

        //powerUpInHand (of the selected player)
        List<String> powerUpInHand = new ArrayList<>();
        List<String> colorPowerUpInHand = new ArrayList<>();
        for (PowerUp powerUp: player.getPowerUpList()){
            powerUpInHand.add(powerUp.getName().toString());
            colorPowerUpInHand.add(powerUp.getColor().toStringLowerCase());
        }
        cm.setPowerUpInHand(powerUpInHand);
        cm.setColorPowerUpInHand(colorPowerUpInHand);

        Gson gson = new Gson();
        String json = gson.toJson(cm);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HEAD_PROP, UPD_HEADER);
        jsonObject.addProperty(TYPE_PROP, MODEL_UPD);
        jsonObject.addProperty(MODEL_PROP, json);

        return jsonObject;
    }


    /**
     * Helper method creating the common structure of most update messages.
     * @param msg   type of the update message
     * @return      a partial update message
     */
    private static JsonObject getFreshUpdate(String msg) {
        JsonObject j = new JsonObject();
        j.addProperty(HEAD_PROP, UPD_HEADER);
        j.addProperty(TYPE_PROP, msg);
        return j;
    }

}