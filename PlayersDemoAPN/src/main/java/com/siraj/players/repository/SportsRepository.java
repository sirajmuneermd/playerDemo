package com.siraj.players.repository;

import com.siraj.players.entity.Sports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The SportsRepository interface extends the JpaRepository interface, providing the basic CRUD operations
 * for the Sports entity. Additionally, it defines custom queries using the @Query annotation.
 */
@Repository
public interface SportsRepository extends JpaRepository<Sports, Long> {

    /**
     * Retrieves a sport by name.
     *
     * @param sportsName The name of the sport to retrieve.
     * @return Optional containing the sport entity with the specified name, or empty if not found.
     */
    @Query("SELECT s FROM Sports s WHERE s.sportsName = ?1")
    Optional<Sports> findSportsByName(String sportsName);

    /**
     * Retrieves a page of sports with the specified name.
     *
     * @param sportsName The name of the sports to retrieve.
     * @param pageable   The pageable information.
     * @return Page of Sports entities with the specified name.
     */
    @Query("SELECT s FROM Sports s WHERE s.sportsName = ?1")
    Page<Sports> findBySports(String sportsName, Pageable pageable);

    /**
     * Retrieves a list of sports with the specified name.
     *
     * @param sportsName The name of the sports to retrieve.
     * @return List of Sports entities with the specified name.
     */
    @Query("SELECT s FROM Sports s WHERE s.sportsName = ?1")
    List<Sports> findSportsListByName(String sportsName);

    /**
     * Updates the sports name for the sport with the specified ID.
     *
     * @param sportsId   The ID of the sport to update.
     * @param sportsName The new name of the sport.
     */
    @Modifying
    @Query("UPDATE Sports s SET s.sportsName = ?2 WHERE s.sportsId = ?1")
    void updateSportsInfoById(Long sportsId, String sportsName);
}
