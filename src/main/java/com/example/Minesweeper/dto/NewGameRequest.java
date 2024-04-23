package com.example.Minesweeper.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewGameRequest
{
    private Integer width;
    private Integer height;
    private Integer mines_count;
}
