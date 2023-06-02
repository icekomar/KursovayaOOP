package models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "game")
public class Game {

    private int id;
    private String Result;
    private String NameOpponent;
    private String Date;
    private String Side;
    private List<TeamRating> Rating = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    public List<TeamRating> getRating() {
        return this.Rating;
    }

    public void setRating(List<TeamRating> rating) {
        Rating = rating;
    }

    public String getDate() {
        return Date;
    }

    public String getNameOpponent() {
        return NameOpponent;
    }

    public String getResult() {
        return Result;
    }

    public String getSide() {
        return Side;
    }

    public void setDate(String data) {
        Date = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameOpponent(String nameOpponent) {
        NameOpponent = nameOpponent;
    }

    public void setResult(String result) {
        Result = result;
    }

    public void setSide(String side) {
        Side = side;
    }
    public Game(){}
    public Game(String result,String nameOpponent, String date, String side){
        setSide(side);
        setResult(result);
        setDate(date);
        setNameOpponent(nameOpponent);
    }
}
