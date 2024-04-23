package com.example.Minesweeper.model;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Embeddable
public class Cell implements Serializable
{
    private int value;
    private Boolean is_dug;

    public void Add()
    {
        this.value+=1;
    }

    public Cell(int _value)
    {
        this.is_dug = false;
        this.value = _value;
    }
}
