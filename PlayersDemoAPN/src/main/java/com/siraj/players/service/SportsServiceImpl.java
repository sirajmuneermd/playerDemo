package com.siraj.players.service;

import com.siraj.players.entity.Sports;
import com.siraj.players.repository.SportsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SportsServiceImpl implements SportsService {

    private final SportsRepository sportsRepository;

    @Autowired
    public SportsServiceImpl(SportsRepository sportsRepository) {
        this.sportsRepository = sportsRepository;
    }

    /**
     * Retrieves all sports.
     *
     * @return The list of all sports
     */
    @Override
    public List<Sports> getAllSports() {
        return sportsRepository.findAll();
    }

    /**
     * Retrieves sports by name with pagination.
     *
     * @param sportsName The name of the sports to search
     * @param page       The pagination information
     * @return The page of sports matching the name
     */
    @Override
    public Page<Sports> getSportsByNameAndPagination(String sportsName, Pageable page) {
        return sportsRepository.findBySports(sportsName, page);
    }

    /**
     * Retrieves sports by sport names.
     *
     * @param sports The array of sport names
     * @return The list of sports matching the names
     */
    @Override
    public List<Sports> getSportsBySportNames(String[] sports) {
        List<Sports> sp = new ArrayList<>();
        for (String sportsName : sports) {
            Sports result = getSportsInfoByname(sportsName);
            if (result != null) {
                sp.add(result);
            }
        }
        return sp;
    }

    /**
     * Retrieves sports list by name.
     *
     * @param sportsName The name of the sports to search
     * @return The list of sports matching the name
     */
    @Override
    public List<Sports> getSportsListByName(String sportsName) {
        System.out.println("getSportsListByName " + sportsName);
        List<Sports> sp = sportsRepository.findSportsListByName(sportsName);
        System.out.println("---------------- " + sp.size());
        return sp;
    }

    /**
     * Checks if sports exist by name.
     *
     * @param sportsName The name of the sports to check
     * @return true if the sports exist, false otherwise
     */
    @Override
    public boolean findBySportsName(String sportsName) {
        try {
            Optional<Sports> sp = sportsRepository.findSportsByName(sportsName);
            System.out.println("-------------------- " + sp.get());
            return sp.isPresent();
        } catch (NoSuchElementException e) {
            System.out.println("No sports found");
        }
        return false;
    }

    /**
     * Retrieves sports information by name.
     *
     * @param sportsName The name of the sports to search
     * @return The sports information matching the name
     */
    @Override
    public Sports getSportsInfoByname(String sportsName) {
        Optional<Sports> sp = sportsRepository.findSportsByName(sportsName);
        System.out.println("-------------------- " + sp.get());
        return sp.orElse(null);
    }

    /**
     * Adds a sports entry.
     *
     * @param sports The sports to be added
     * @return The added sports
     */
    @Transactional
    @Override
    public Sports addSports(Sports sports) {
        sportsRepository.save(sports);
        return sports;
    }

    /**
     * Adds multiple sports entries.
     *
     * @param list The list of sports to be added
     */
    @Override
    public void addAllSports(List<Sports> list) {
        sportsRepository.saveAll(list);
    }

    /**
     * Updates sports for a player.
     *
     * @param sport The sports to be updated
     * @return The updated sports
     */
    @Transactional
    @Override
    public Sports updateSportsForThePlayer(Sports sport) {
        return sportsRepository.save(sport);
    }

    /**
     * Finds sports by ID.
     *
     * @param sportsId The ID of the sports to find
     * @return The sports matching the ID
     */
    @Override
    public Sports findSportsById(Long sportsId) {
        return sportsRepository.getReferenceById(sportsId);
    }

    /**
     * Deletes sports by ID.
     *
     * @param sportsId The ID of the sports to delete
     */
    @Override
    public void deleteBySportId(Long sportsId) {
        sportsRepository.deleteById(sportsId);
    }
}
