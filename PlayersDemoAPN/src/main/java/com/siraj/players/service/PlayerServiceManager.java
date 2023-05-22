package com.siraj.players.service;

import com.siraj.players.entity.Players;
import com.siraj.players.entity.PlayersData;
import com.siraj.players.entity.Sports;
import com.siraj.players.repository.PlayersRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PlayerServiceManager {

    private final PlayersRepository repository;
    private final SportsService sportsService;
    private PlayerService playerService;

    @Autowired
    public PlayerServiceManager(PlayersRepository repository, SportsService sportsService, PlayerService playerService) {
        this.repository = repository;
        this.sportsService = sportsService;
        this.playerService = playerService;
    }

    /**
     * Validates player details and saves the player information along with associated sports.
     * If the player already exists, it updates the sports information.
     *
     * @param player     The player to be validated and saved
     * @param sportsInfo The list of sports associated with the player
     * @return true if the player exists and sports are updated, false otherwise
     */
    public boolean validatePlayerDetails(Players player, List<Sports> sportsInfo) {
        Players playerItem = playerService.getProfileByEmail(player.getEmailId());
        if (playerItem != null) {
            System.out.println("Profile already exists: " + playerItem.getPlayerId());
            saveSports(playerItem, sportsInfo);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Saves the sports information associated with the player.
     *
     * @param playerItem The player to whom the sports are associated
     * @param sports     The list of sports to be saved
     */
    private void saveSports(Players playerItem, List<Sports> sports) {
        for (Sports sport : sports) {
            sport.setPlayer(playerItem);
            Sports spAdded = sportsService.addSports(sport);
            System.out.println("Sports added: " + sport.getSportsId());
            playerItem.getSports().add(spAdded);
            playerService.savePlayerInfo(playerItem);
            System.out.println("PlayerItem updated: " + playerItem.getSports().toString());
        }
    }

    /**
     * Adds a player to the database along with associated sports.
     *
     * @param player The player to be added
     * @return The added player
     */
    public Players addPlayerToDB(Players player) {
        List<Sports> sportsData = new ArrayList<>();
        List<Sports> sportsInfo = player.getSports();
        Players addedPlayer = null;
        for (Sports sport : sportsInfo) {
            sport.setPlayer(player);
            sportsData.add(sport);
        }

        player.setSports(new ArrayList<>());
        boolean isPlayerExists = validatePlayerDetails(player, sportsInfo);
        if (!isPlayerExists) {
            addedPlayer = playerService.savePlayerInfo(player);
            if (!sportsInfo.isEmpty()) {
                validateAndAddSportsInfo(sportsData, addedPlayer);
            }
        }
        return addedPlayer;
    }

    /**
     * Validates and adds the sports information to the player.
     *
     * @param sportsInfo The list of sports to be validated and added
     * @param player     The player to whom the sports are associated
     * @return The list of added sports
     */
    private List<Sports> validateAndAddSportsInfo(List<Sports> sportsInfo, Players player) {
        List<Sports> listToAdd = new ArrayList<>();

        for (Sports sport : sportsInfo) {
            System.out.println("Searching sports name: " + sport.getSportsName());
            sport.setPlayer(player);
            Sports spAdded = sportsService.addSports(sport);
            listToAdd.add(spAdded);
        }

        System.out.println("List is not empty: " + listToAdd.toString());
        player.setSports(listToAdd);
        repository.save(player);
        return listToAdd;
    }

    /**
     * Retrieves the list of all players.
     *
     * @return The list of players
     */
    public List<Players> getPlayers() {
        return playerService.getPlayers();
    }

    /**
     * Retrieves the list of players based on age, gender, and player level.
     *
     * @param age         The age of the players
     * @param gender      The gender of the players
     * @param playerLevel The player level
     * @return The list of players matching the criteria
     */
    public List<Players> getPlayersByAgeAndGenderAndPlayerLevel(Integer age, String gender, Integer playerLevel) {
        return playerService.getPlayerByAgeAndGenderAndLevel(age, gender, playerLevel);
    }

    /**
     * Retrieves the list of players who participate in more than one sport.
     *
     * @return The list of players who participate in more than one sport
     */
    public List<PlayersData> getPlayersMoreThanOneSports() {
        List<PlayersData> playersData = new ArrayList<>();
        List<Tuple> resultSet = playerService.getPlayersMoreThanOneSports();

        for (Tuple tp : resultSet) {
            PlayersData playersInfo = new PlayersData();
            playersInfo.setPlayerId(tp.get(0, Long.class));
            playersInfo.setFirstName(tp.get(1, String.class));
            playersInfo.setEmailId(tp.get(2, String.class));
            playersInfo.setLastName(tp.get(3, String.class));
            playersInfo.setAge(tp.get(4, Integer.class));
            playersInfo.setGender(tp.get(5, String.class));
            playersInfo.setPlayerLevel(tp.get(6, Integer.class));
            playersData.add(playersInfo);
        }
        return playersData;
    }

    /**
     * Retrieves the list of players who are not associated with any sports.
     *
     * @return The list of players not in sports
     */
    public List<Players> getPlayersWhoIsNotInSports() {
        return playerService.getPlayersWhoIsNotInSports();
    }
}
