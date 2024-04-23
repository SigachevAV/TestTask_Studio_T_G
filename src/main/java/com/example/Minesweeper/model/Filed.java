package com.example.Minesweeper.model;

import com.example.Minesweeper.exception.MinesweeperException;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Filed implements Serializable
{
    private ArrayList<ArrayList<Cell>> field;

    public Filed(ArrayList<ArrayList<Cell>> field)
    {
        this.field = (ArrayList<ArrayList<Cell>>) field.clone();
    }

    public boolean Dig(int _x, int _y) throws MinesweeperException {
        if (this.field.get(_x).get(_y).getIs_dug())
        {
            throw new MinesweeperException("Клетка уже использована");
        }
        this.field.get(_x).get(_y).setIs_dug(true);
        if(this.field.get(_x).get(_y).getValue() == -1)
        {
            return true;
        }
        if(this.field.get(_x).get(_y).getValue() == 0)
        {
            for(int i = 0; i < 3; i++)
            {
                for (int j =0; j<3; j++)
                {
                    if(_x-1+i < 0 || _x-1+i > this.field.size()-1 || _y-1+j < 0 || _y-1+j > this.field.get(_x).size()-1)
                    {
                        continue;
                    }
                    if(field.get(_x-1+i).get(_y-1+j).getValue() == 0 && !field.get(_x-1+i).get(_y-1+j).getIs_dug())
                    {
                        this.Dig(_x-1+i, _y-1+j);
                    }
                }
            }
        }

        return false;
    }

    public List<List<String>> ToDTO(boolean _isWin, boolean _isComplete)
    {
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i< this.field.size(); i++)
        {
            result.add(new ArrayList<>());
            for (int j = 0; j<this.field.get(i).size(); j++)
            {
                if (this.field.get(i).get(j).getIs_dug() || _isComplete)
                {
                    if (this.field.get(i).get(j).getValue() == -1)
                    {
                        if (_isWin)
                        {
                            result.get(i).add("M");
                        }
                        else
                        {
                            result.get(i).add("X");
                        }
                    }
                    else
                    {
                        result.get(i).add(Integer.toString(this.field.get(i).get(j).getValue()));
                    }
                }
                else
                {
                    result.get(i).add(" ");
                }
            }
        }
        return result;
    }

}
