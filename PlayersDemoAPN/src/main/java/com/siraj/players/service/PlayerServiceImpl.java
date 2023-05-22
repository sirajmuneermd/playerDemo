package com.siraj.players.service;

import com.siraj.players.entity.Players;
import com.siraj.players.repository.PlayersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The PlayerServiceImpl class implements the PlayerService interface and provides the implementation
 * for the player-related operations.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayersRepository repository;
    private final SportsService sportsService;
    private final EntityManager entityManager;

    @Autowired
    public PlayerServiceImpl(PlayersRepository repository, SportsService sportsService, EntityManager entityManager) {
        this.repository = repository;
        this.sportsService = sportsService;
        this.entityManager = entityManager;
    }

    @Override
    public List<Players> getPlayers() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Players savePlayerInfo(Players player) {
        try {
            return repository.save(player);
        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
            throw new IllegalArgumentException("Unable to add player to the database. Please contact customer care.");
        }
    }

    @Override
    public boolean findByEmail11(String email) {
        Optional<Players> playerByEmail = repository.findPlayersByEmail(email);
        return playerByEmail.isPresent();
    }

    @Override
    public Players getProfileByEmail(String email) {
        Optional<Players> playerByEmail = repository.findPlayersByEmail(email);
        return playerByEmail.orElse(null);
    }

    @Override
    public List<Players> getPlayerByAgeAndGenderAndLevel(Integer age, String gender, Integer playerLevel) {
        return repository.getPlayersByAge(age, gender, playerLevel);
    }

    @Override
    public List<Tuple> getPlayersMoreThanOneSports() {
        String countQuery = "SELECT p.playerId, p.firstName, p.emailId, p.lastName, p.age, p.gender, p.playerLevel, COUNT(p.playerId) " +
                "FROM Players p, Sports s " +
                "WHERE p.playerId = s.player " +
                "GROUP BY p.playerId " +
                "HAVING COUNT(p.playerId) >= 2";
        TypedQuery<Tuple> query = entityManager.createQuery(countQuery, Tuple.class);
        return query.getResultList();
    }

    @Override
    public List<Players> getPlayersWhoIsNotInSports() {
        return repository.getPlayersNotInSports();
    }
}
