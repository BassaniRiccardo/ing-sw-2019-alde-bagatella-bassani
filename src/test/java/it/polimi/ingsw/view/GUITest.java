package it.polimi.ingsw.view;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.BoardConfigurer;
import it.polimi.ingsw.controller.PowerUpFactory;
import it.polimi.ingsw.controller.WeaponFactory;
import it.polimi.ingsw.model.Updater;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Deck;
import it.polimi.ingsw.model.board.KillShotTrack;
import it.polimi.ingsw.model.board.Player;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exceptions.NoMoreCardsException;
<<<<<<< Updated upstream
import it.polimi.ingsw.model.exceptions.NotAvailableAttributeException;
=======
>>>>>>> Stashed changes
import it.polimi.ingsw.model.exceptions.UnacceptableItemNumberException;
import org.junit.Test;


public class GUITest {


    @Test
    public void render() throws UnacceptableItemNumberException, NoMoreCardsException {
<<<<<<< Updated upstream
=======

       /* Board board = BoardConfigurer.configureMap(4);
        WeaponFactory weaponFactory = new WeaponFactory(board);
        PowerUpFactory powerUpFactory = new PowerUpFactory(board);
        Deck wd = new Deck();
        for (int i = 0; i < 21; i++) {
            wd.addCard(weaponFactory.createWeapon(Weapon.WeaponName.LOCK_RIFLE));
        }
        board.setWeaponDeck(wd);
        Deck pd = new Deck();
        for (int i = 0; i < 24; i++) {
            pd.addCard(powerUpFactory.createPowerUp(PowerUp.PowerUpName.TELEPORTER, Color.RED));
        }
        board.setPowerUpDeck(pd);
        Deck ad = new Deck();
        for (int i = 0; i < 36; i++) {
            ad.addCard(new AmmoTile(true, new AmmoPack(1, 2, 0)));
        }
        board.setAmmoDeck(ad);
        board.setKillShotTrack(new KillShotTrack(7, board));
        board.getPlayers().add(new Player(1, Player.HeroName.BANSHEE, board));
        board.getPlayers().add(new Player(2, Player.HeroName.DOZER, board));
        board.getPlayers().get(0).setUsername("Giuliano");
        board.getPlayers().get(0).addWeapon(weaponFactory.createWeapon(Weapon.WeaponName.POWER_GLOVE));
        board.getSpawnPoints().get(2).addCard(weaponFactory.createWeapon(Weapon.WeaponName.LOCK_RIFLE));*/
>>>>>>> Stashed changes

        Board board = BoardConfigurer.simulateScenario();

       Board board = BoardConfigurer.simulateScenario();

        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GUI.class);
            }
        }.start();
        UI ui = GUI.waitGUI();
        ClientMain clientMain = new ClientMain();
        ((GUI) ui).setClientMain(clientMain);
        ((GUI)ui).getClientMain().setClientModel(Updater.getModelObject(board, board.getPlayers().get(0)));
        ((GUI) ui).render();
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e){}

    }



    @Test
    public void display() {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GUI.class);
            }
        }.start();
        GUI gui = GUI.waitGUI();
        gui.display("ciao");

    }
}