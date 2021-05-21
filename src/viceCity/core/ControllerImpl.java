package viceCity.core;

import viceCity.common.ConstantMessages;
import viceCity.core.interfaces.Controller;
import viceCity.models.*;
import viceCity.models.guns.Gun;
import viceCity.models.neighbourhood.Neighbourhood;
import viceCity.models.MainPlayer;
import viceCity.models.players.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ControllerImpl implements Controller {
    private List<Player> civilPlayers;
    private Deque<Gun> guns;
    private Player mainPlayer;
    private Neighbourhood neighbourhood;

    public ControllerImpl() {
        this.mainPlayer = new MainPlayer();
        this.civilPlayers = new ArrayList<>();
        this.guns = new ArrayDeque<>();
        this.neighbourhood = new GangNeighbourhood();
    }

    @Override
    public String addPlayer(String name) {
        Player player = new CivilPlayer(name);
        this.civilPlayers.add(player);
        return String.format(ConstantMessages.PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun = null;
        String massage;
        if (type.equals("Pistol")) {
            gun = new Pistol(name);
            massage = String.format(ConstantMessages.GUN_ADDED, name, type);
        } else if (type.equals("Rifle")) {
            gun = new Rifle(name);
            massage = String.format(ConstantMessages.GUN_ADDED, name, type);
        } else {
            massage = ConstantMessages.GUN_TYPE_INVALID;
        }
        if (gun != null) {
            guns.offer(gun);
        }
        return massage;
    }

    @Override
    public String addGunToPlayer(String name) {
        String massage;
        if (this.guns.size() == 0) {
            //massage = ConstantMessages.GUN_QUEUE_IS_EMPTY;
            return ConstantMessages.GUN_QUEUE_IS_EMPTY;
        }
       // Gun gun = this.guns.poll();
        if (name.equals("Vercetti")) {
            Gun gun = this.guns.poll();
            this.mainPlayer.getGunRepository().add(gun);
            massage = String.format(ConstantMessages.GUN_ADDED_TO_MAIN_PLAYER,
                    gun.getName(), this.mainPlayer.getName());
            return massage;
        }

        Player player = this.civilPlayers.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst().orElse(null);
        if (player == null){
           return  ConstantMessages.CIVIL_PLAYER_DOES_NOT_EXIST;
        }
            Gun gun = this.guns.poll();
            player.getGunRepository().add(gun);
            //this.civilPlayers.add(player);
            massage = String.format(ConstantMessages.GUN_ADDED_TO_CIVIL_PLAYER, gun.getName(), player.getName());

        return massage;
    }

    @Override
    public String fight() {
        int sizeBefourAction = this.civilPlayers.size();
        int playersLifePointsBeforeAction = 50 * sizeBefourAction;

        this.neighbourhood.action(this.mainPlayer, this.civilPlayers);

        int sizeCivilPlayersAfterAction = this.civilPlayers.size();
        int playersLifePoints = this.civilPlayers.stream().mapToInt(p -> p.getLifePoints()).sum();

        StringBuilder str = new StringBuilder();

        if (this.mainPlayer.getLifePoints() == 100  && playersLifePointsBeforeAction == playersLifePoints){
            return ConstantMessages.FIGHT_HOT_HAPPENED;
        }
            long bodyCount = this.civilPlayers.stream().filter(p -> !p.isAlive()).count();
            long lifeCount = this.civilPlayers.stream().filter(Player::isAlive).count();
            str.append(ConstantMessages.FIGHT_HAPPENED);
            str.append(System.lineSeparator());
            str.append(String.format(ConstantMessages.MAIN_PLAYER_LIVE_POINTS_MESSAGE, this.mainPlayer.getLifePoints()));
            str.append(System.lineSeparator());
            str.append(String.format(ConstantMessages.MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE, bodyCount));
            str.append(System.lineSeparator());
            str.append(String.format(ConstantMessages.CIVIL_PLAYERS_LEFT_MESSAGE, lifeCount));

        return str.toString();

    }
}
