package com.example.Minesweeper.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameInfoResponse
{
    private String game_id;
    private Integer width;
    private Integer height;
    private Integer mines_count;
    private Boolean completed;
    private List<List<String>> field;
}
