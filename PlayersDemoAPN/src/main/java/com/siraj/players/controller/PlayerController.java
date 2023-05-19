package com.siraj.players.controller;


import com.siraj.players.entity.Players;
import com.siraj.players.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    @PostMapping
    public String addPlayer(@RequestBody Players player){
        System.out.println(player.toString());

        return "Success";
    }

    @GetMapping
    public List<Players> getPlayers(){
        return  playerService.getPlayers();
    }

}
