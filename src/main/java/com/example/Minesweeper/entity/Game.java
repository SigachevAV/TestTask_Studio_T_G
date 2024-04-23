package com.example.Minesweeper.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Game
{
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @Lob
    private String map;

    @Column
    private Integer mines_count;

    @Column
    private Integer width;

    @Column
    private Integer height;

    @Column
    private Boolean completed;
}
