package com.siraj.players.service;

import com.siraj.players.entity.Players;
import com.siraj.players.exception.ResourceNotFoundException;
import jakarta.persistence.Tuple;

import java.util.List;

/**
 * The PlayerService interface defines the contract for player-related operations.
 */
public interface PlayerService {

    /**
     * Retrieves a list of all players.
     *
     * @return List of Players
     */
    List<Players> getPlayers();

    /**
     * Saves player information to the database.
     *
     * @param player The player to be saved
     * @return The saved player
     */
    Players savePlayerInfo(Players player)  ;


    /**
     * Retrieves the player profile associated with the given email.
     *
     * @param email The email to search for
     * @return The player profile
     */
    Players getProfileByEmail(String email);

    /**
     * Retrieves a list of players based on age, gender, and player level.
     *
     * @param age         The age of the players
     * @param gender      The gender of the players
     * @param playerLevel The player level
     * @return List of Players
     */
    List<Players> getPlayerByAgeAndGenderAndLevel(Integer age, String gender, Integer playerLevel);

    /**
     * Retrieves a list of players who participate in more than one sport.
     *
     * @return List of Tuples containing player information
     */
    List<Tuple> getPlayersMoreThanOneSports();

    /**
     * Retrieves a list of players who are not associated with any sport.
     *
     * @return List of Players
     */
    List<Players> getPlayersWhoIsNotInSports();
}
