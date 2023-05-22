package com.siraj.players.repository;

import com.siraj.players.entity.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The PlayersRepository interface extends the JpaRepository interface, providing the basic CRUD operations
 * for the Players entity. Additionally, it defines custom queries using the @Query annotation.
 */
@Repository
public interface PlayersRepository extends JpaRepository<Players, Long> {

    /**
     * Retrieves a player by email.
     *
     * @param email The email of the player to retrieve.
     * @return Optional containing the player entity with the specified email, or empty if not found.
     */
    @Query("SELECT s FROM Players s WHERE s.emailId = ?1")
    Optional<Players> findPlayersByEmail(String email);

    /**
     * Retrieves a list of players based on age, gender, and player level.
     *
     * @param age         The age of the players.
     * @param gender      The gender of the players.
     * @param playerLevel The player level.
     * @return List of Players entities matching the specified criteria.
     */
    @Query("SELECT s FROM Players s WHERE s.age = ?1 AND s.gender = ?2 AND s.playerLevel = ?3")
    List<Players> getPlayersByAge(Integer age, String gender, Integer playerLevel);

    /**
     * Retrieves a list of players who are not associated with any sports.
     *
     * @return List of Players entities not associated with any sports.
     */
    @Query("SELECT s FROM Players s WHERE s.sports IS EMPTY")
    List<Players> getPlayersNotInSports();
}
