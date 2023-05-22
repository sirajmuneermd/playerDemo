package com.siraj.players.controller;

import com.siraj.players.entity.Players;
import com.siraj.players.entity.PlayersData;
import com.siraj.players.entity.Sports;
import com.siraj.players.entity.SportsData;
import com.siraj.players.service.SportsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/sports")
public class SportsTypeController {

    private final SportsService sportsService;

    @Autowired
    public SportsTypeController(SportsService sportsService) {
        this.sportsService = sportsService;
    }

    /**
     * Get all sports.
     *
     * @return A list of all sports.
     */
    @GetMapping
    public List<Sports> getAllSports() {
        return sportsService.getAllSports();
    }

    /**
     * Add a new sports type.
     *
     * @param sportsName The name of the sports type to add.
     */
    @PutMapping("/add/{sportsName}")
    public void addSportsType(@PathVariable String sportsName) {
        if (sportsName != null) {
            boolean isSportsTypeExists = sportsService.findBySportsName(sportsName);
            if (isSportsTypeExists) {
                throw new IllegalArgumentException("Sports Type already exists");
            } else {
                Sports sp = new Sports();
                sp.setSportsName(sportsName);
                System.out.println("sports name ------------- " + sp.toString());
                sportsService.addSports(sp);
            }
        }
    }

    /**
     * Get sports by name with pagination.
     *
     * @param sportsName The name of the sports to retrieve.
     * @param page       The page number.
     * @param size       The number of items per page.
     * @return A response entity containing the paginated list of players associated with the sports.
     */
    @GetMapping("/getSportsByName")
    public ResponseEntity<Map<String, Object>> getSportsByName(
            @RequestParam String sportsName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        List<Sports> sp = new ArrayList<Sports>();
        Pageable paging = PageRequest.of(page, size);
        List<Players> playersDetails = new ArrayList<Players>();
        Page<Sports> sportsPage = sportsService.getSportsByNameAndPagination(sportsName, paging);
        sp = sportsPage.getContent();
        for (Sports s : sp) {
            playersDetails.add(s.getPlayer());
            System.out.println("player details   " + s.getPlayer());
        }
        Map<String, Object> response = new HashMap<>();
        response.put("PlayerDetails", playersDetails);
        response.put("currentPage", sportsPage.getNumber());
        response.put("totalItems", sportsPage.getTotalElements());
        response.put("totalPages", sportsPage.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get a list of sports with player information.
     *
     * @param sportsJson The JSON string containing sports information.
     * @return A list of SportsData objects containing sports and player information.
     */
    @GetMapping("/getSportsList")
    public List<SportsData> getSportsList(@RequestBody String sportsJson) {
        List<Sports> sportsInfo = new ArrayList<>();
        List<SportsData> result = new ArrayList<>();
        if (sportsJson != null && sportsJson.isEmpty()) {
            SportsData data = new SportsData();
            data.setErrorMessage("Please provide sports data");
            result.add(data);
            return result;
        }

        // Parse the JSON string
        JsonParser par = JsonParserFactory.getJsonParser();
        List<Object> list = par.parseList(sportsJson);
        if (list.isEmpty()) {

            if (sportsJson != null && sportsJson.isEmpty()) {
                SportsData data = new SportsData();
                data.setErrorMessage("Please provide sports data");
                result.add(data);
                return result;
            }
        }
        for (Object li : list) {
            HashMap<String, String> map = (HashMap<String, String>) li;
            String sportsList = map.get("sportsName");
            if (sportsList != null && sportsList.isEmpty()) {
                SportsData data = new SportsData();
                data.setErrorMessage("Please provide sports data");
                result.add(data);
                return result;
            }

            String[] strArray = sportsList.split(",");
            for (String sportName : strArray) {
                System.out.println("--json node   " + sportName);
                List<Sports> sports = sportsService.getSportsListByName(sportName);
                System.out.println("sports result  " + sports.toString());

                for (Sports sport : sports) {
                    SportsData res = new SportsData();
                    PlayersData player = new PlayersData();
                    res.setSportsName(sport.getSportsName());
                    if (sport.getPlayer() != null) {
                        player.setPlayerLevel(sport.getPlayer().getPlayerLevel());
                        player.setAge(sport.getPlayer().getAge());
                        player.setGender(sport.getPlayer().getGender());
                        player.setPlayerId(sport.getPlayer().getPlayerId());
                        player.setEmailId(sport.getPlayer().getEmailId());
                        player.setFirstName(sport.getPlayer().getFirstName());
                        player.setLastName(sport.getPlayer().getLastName());
                        res.setPlayersInfo(player);
                    }
                    result.add(res);
                }
            }
        }

        if (result.isEmpty()) {
            SportsData data = new SportsData();
            data.setErrorMessage("No Sports data found with " + sportsJson);
            result.add(data);
        }

        return result;
    }

    /**
     * Update sports data for a player.
     *
     * @param player The player object containing the updated sports data.
     * @return The updated player object.
     */
    @PutMapping("updateSports")
    public Players updateSportsData(@RequestBody Players player) {
        List<Sports> sp = player.getSports();
        List<Sports> updatedSports = new ArrayList<>();
        for (Sports s : sp) {
            Sports fromDB = sportsService.findSportsById(s.getSportsId());

            if (fromDB != null) {
                fromDB.setSportsName(s.getSportsName());
                try {
                    Sports updated = sportsService.updateSportsForThePlayer(fromDB);
                    updatedSports.add(updated);
                } catch (DataAccessException e) {
                    throw new IllegalArgumentException("Sports Data not updated check the player details");
                }
            } else {
                throw new IllegalArgumentException("Sports Data not updated check the player details");
            }
        }
        player.setSports(updatedSports);
        return player;
    }

    /**
     * Delete sports information and player by sports ID.
     *
     * @param sportsId The ID of the sports to delete.
     */
    @DeleteMapping("/deletePlayerBySportsId")
    public void deleteSportsInfoAndPlayer(@RequestParam Long sportsId) {
        Sports fromDB;
        try {
            fromDB = sportsService.findSportsById(sportsId);
            System.out.println("------------found the value by id " + fromDB.toString());
            sportsService.deleteBySportId(sportsId);
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            throw new EntityNotFoundException("No record found by Sports id " + sportsId);
        }
    }
}
