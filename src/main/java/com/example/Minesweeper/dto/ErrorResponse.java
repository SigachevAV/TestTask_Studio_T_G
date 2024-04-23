package com.example.Minesweeper.dto;

import lombok.*;



@Data
public class ErrorResponse
{
    private String error;

    public ErrorResponse(String error)
    {
        this.error = error;
    }
}
