package com.example.Minesweeper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameTurnRequest
{
    private String game_id;
    private  int col;
    private int row;
}
