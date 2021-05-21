package viceCity;

import viceCity.core.ControllerImpl;
import viceCity.core.EngineImpl;
import viceCity.core.interfaces.Controller;
import viceCity.core.interfaces.Engine;
import viceCity.models.MainPlayer;
import viceCity.models.players.Player;

public class Main {
    public static void main(String[] args) {

        Player player = new MainPlayer();

        System.out.println();
        Controller controller = new ControllerImpl(); // TODO
        Engine engine = new EngineImpl(controller);
        engine.run();


    }
}
