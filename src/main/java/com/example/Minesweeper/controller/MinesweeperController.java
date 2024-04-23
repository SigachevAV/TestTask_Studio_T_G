package com.example.Minesweeper.controller;

import com.example.Minesweeper.dto.GameInfoResponse;
import com.example.Minesweeper.dto.GameTurnRequest;
import com.example.Minesweeper.dto.NewGameRequest;
import com.example.Minesweeper.exception.MinesweeperException;
import com.example.Minesweeper.service.MinesweeperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@Slf4j
@RequiredArgsConstructor
public class MinesweeperController {
    private final MinesweeperService minesweeperService;

    @CrossOrigin
    @PostMapping("/new")
    public GameInfoResponse newGame(@RequestBody NewGameRequest request) throws MinesweeperException, JsonProcessingException
    {
        log.atInfo().log("zapros");
        return minesweeperService.NewGame(request);
    }

    @CrossOrigin
    @PostMapping("/turn")
    public GameInfoResponse makeTurn(@RequestBody GameTurnRequest request) throws MinesweeperException, JsonProcessingException {
        log.atInfo().log("zapros");
        return minesweeperService.Turn(request);
    }
}
