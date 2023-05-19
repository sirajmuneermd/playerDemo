package com.siraj.players.service;

import com.siraj.players.entity.Players;
import com.siraj.players.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PlayerService {

    private final PlayersRepository repository;

    @Autowired
    public PlayerService(PlayersRepository repository) {
        this.repository = repository;
    }

    public List<Players> getPlayers(){

        return repository.findAll();
    }

    public Players save(Players player){

        repository.save(player);
        return player;
    }
}
