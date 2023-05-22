package com.siraj.players.service;

import com.siraj.players.entity.Sports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SportsService {

    /**
     * Add multiple sports to the database.
     *
     * @param list The list of sports to add.
     */
    void addAllSports(List<Sports> list);

    /**
     * Add a single sports to the database.
     *
     * @param sports The sports to add.
     * @return The added sports.
     */
    Sports addSports(Sports sports);

    /**
     * Get sports information by name.
     *
     * @param sportsName The name of the sports.
     * @return The sports information.
     */
    Sports getSportsInfoByname(String sportsName);

    /**
     * Check if a sports exists by name.
     *
     * @param sportsName The name of the sports.
     * @return true if the sports exists, false otherwise.
     */
    boolean findBySportsName(String sportsName);

    /**
     * Get all sports.
     *
     * @return The list of all sports.
     */
    List<Sports> getAllSports();

    /**
     * Get sports by name with pagination.
     *
     * @param sportsName The name of the sports.
     * @param page       The pagination information.
     * @return The page of sports.
     */
    Page<Sports> getSportsByNameAndPagination(String sportsName, Pageable page);

    /**
     * Get sports by a list of sport names.
     *
     * @param sports The array of sport names.
     * @return The list of sports.
     */
    List<Sports> getSportsBySportNames(String[] sports);

    /**
     * Get sports list by name.
     *
     * @param sportsName The name of the sports.
     * @return The list of sports.
     */
    List<Sports> getSportsListByName(String sportsName);

    /**
     * Update sports information for a player.
     *
     * @param sport The updated sports information.
     * @return The updated sports object.
     */
    Sports updateSportsForThePlayer(Sports sport);

    /**
     * Find sports by ID.
     *
     * @param sportsId The ID of the sports.
     * @return The sports object.
     */
    Sports findSportsById(Long sportsId);

    /**
     * Delete sports by ID.
     *
     * @param sportsId The ID of the sports to delete.
     */
    void deleteBySportId(Long sportsId);
}
