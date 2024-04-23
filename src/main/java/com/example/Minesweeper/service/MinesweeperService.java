package com.example.Minesweeper.service;

import com.example.Minesweeper.dto.GameInfoResponse;
import com.example.Minesweeper.dto.GameTurnRequest;
import com.example.Minesweeper.dto.NewGameRequest;
import com.example.Minesweeper.entity.Game;
import com.example.Minesweeper.exception.MinesweeperException;
import com.example.Minesweeper.model.Cell;
import com.example.Minesweeper.model.Filed;
import com.example.Minesweeper.repository.GameRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.SerializationUtils;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MinesweeperService
{
    private final GameRepository gameRepository;
    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private GameInfoResponse GameInfoResponseFactory(Game _game, boolean _isWin, boolean _isComplete) throws JsonProcessingException {
        return GameInfoResponse.builder()
                .game_id(_game.getId().toString())
                .width(_game.getWidth())
                .height(_game.getHeight())
                .mines_count(_game.getMines_count())
                .field(objectMapper.readValue(_game.getMap(), Filed.class) .ToDTO(_isWin, _isComplete))
                .completed(_game.getCompleted())
                .build();

    }

    private boolean IsComplited(Filed _filed)
    {
        for (int x =0 ; x < _filed.getField().size(); x++)
        {
            for (int y = 0; y < _filed.getField().get(x).size(); y++)
            {
                if (!_filed.getField().get(x).get(y).getIs_dug() && _filed.getField().get(x).get(y).getValue() != -1)
                {
                    return false;
                }
            }
        }
        return true;
    }
    public GameInfoResponse Turn(GameTurnRequest _request) throws MinesweeperException, JsonProcessingException
    {
        Game game = gameRepository.findById(UUID.fromString( _request.getGame_id())).orElseThrow( () ->  new MinesweeperException("Игра не найдена"));
        if (_request.getCol() <0 || _request.getCol() > game.getWidth() || _request.getRow() < 0 || _request.getRow() > game.getHeight())
        {
            throw new MinesweeperException("Неверные координаты");
        }
        Filed filed = objectMapper.readValue(game.getMap(), Filed.class);
        boolean digResult = filed.Dig(_request.getRow(), _request.getCol());
        game.setMap(objectMapper.writeValueAsString(filed));
        GameInfoResponse result;
        if (IsComplited(filed) || digResult)
        {
            game.setCompleted(true);
            gameRepository.save(game);
            result = GameInfoResponseFactory(game, !digResult, true);
        }
        else
        {
            result= GameInfoResponseFactory(game, false, false);
        }
        gameRepository.save(game);
        return result;
    }

    public GameInfoResponse NewGame(NewGameRequest _request) throws MinesweeperException, JsonProcessingException {
        if(_request.getHeight() < 1)
        {
            throw new MinesweeperException("Высота не может быть меньше 1");
        }
        if(_request.getWidth() < 1)
        {
            throw new MinesweeperException("Ширина не может быть меньше 1");
        }
        if (_request.getWidth() * _request.getWidth() <= _request.getMines_count())
        {
            throw new MinesweeperException("Должна быть хотя бы одна свободная клетка");
        }
        ArrayList<ArrayList<Cell>> field = new ArrayList<>();
        int minesCount = 0;
        for (int i = 0; i< _request.getHeight(); i++)
        {
            field.add(new ArrayList<>());
            for (int j = 0; j < _request.getWidth(); j++)
            {
                if (minesCount < _request.getMines_count())
                {
                    field.get(i).add(new Cell(random.nextInt(-1, 1)));
                    if (field.get(i).get(j).getValue() == -1)
                    {
                        minesCount += 1;
                    }
                }
                else
                {
                    field.get(i).add(new Cell(0));
                }
            }
        }
        for (int x = 0; x< _request.getHeight(); x++)
        {
            for (int y = 0; y < _request.getWidth(); y++)
            {
                if(field.get(x).get(y).getValue() == -1)
                {
                    for(int i = 0; i < 3; i++)
                    {
                        for (int j =0; j<3; j++)
                        {
                            if(x-1+i < 0 || x-1+i > _request.getHeight()-1 || y-1+j < 0 || y-1+j > _request.getWidth()-1)
                            {
                                continue;
                            }
                            if(field.get(x-1+i).get(y-1+j).getValue() != -1)
                            {
                                field.get(x-1+i).get(y-1+j).Add();
                            }
                        }
                    }
                }
            }
        }
        Game game = Game.builder()
                .width(_request.getWidth())
                .height(_request.getHeight())
                .completed(false)
                .mines_count(_request.getMines_count())
                .map(objectMapper.writeValueAsString(new Filed(field)))
                .build();
        gameRepository.save(game);
        return GameInfoResponseFactory(game, false, false);
     }

}
