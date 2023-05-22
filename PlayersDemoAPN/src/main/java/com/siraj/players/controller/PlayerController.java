package com.siraj.players.controller;

import com.siraj.players.entity.Players;
import com.siraj.players.entity.PlayersData;
import com.siraj.players.service.PlayerServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/players")
public class PlayerController {

    private final PlayerServiceManager serviceManager;

    @Autowired
    public PlayerController(PlayerServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    /**
     * Add a new player to the system.
     *
     * @param player The player object to be added.
     * @return The added player object.
     */
    @PostMapping("/addPlayer")
    public Players addPlayer(@RequestBody Players player) {
        System.out.println(player.toString());
        Players playerAdded = serviceManager.addPlayerToDB(player);
        System.out.println("Player added: " + playerAdded.toString());
        return playerAdded;
    }

    /**
     * Get all players in the system.
     *
     * @return A list of players.
     */
    @GetMapping
    public List<Players> getPlayers() {
        return serviceManager.getPlayers();
    }

    /**
     * Get players by age, gender, and player level.
     *
     * @param age         The age of the players to retrieve.
     * @param gender      The gender of the players to retrieve (optional).
     * @param playerLevel The player level of the players to retrieve (optional).
     * @return A list of players matching the specified criteria.
     */
    @GetMapping("/getPlayersByAge")
    public List<Players> getPlayersByAge(
            @RequestParam Integer age,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer playerLevel
    ) {
        return serviceManager.getPlayersByAgeAndGenderAndPlayerLevel(age, gender, playerLevel);
    }

    /**
     * Get players who participate in more than one sport.
     *
     * @return A list of player data.
     */
    @GetMapping("/getListByMorethanOneSports")
    public List<PlayersData> getPlayersByMoreThanOneSports() {
        return serviceManager.getPlayersMoreThanOneSports();
    }

    /**
     * Get players who are not associated with any sport.
     *
     * @return A list of players.
     */
    @GetMapping("/getPlayersWhoIsNotInSports")
    public List<Players> getPlayersWhoIsNotInSports() {
        return serviceManager.getPlayersWhoIsNotInSports();
    }
}
