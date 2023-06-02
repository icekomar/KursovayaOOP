package models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="footballers")
public class Footballers extends Human {
    private int Id;
    private String Position;
    private float Rating;
    private int NumberOfMatches;
    private String Number;
    private List<TeamRating> GamesRating;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    public String getPosition() {
        return Position;
    }

    public int getNumberOfMatches() {
        return NumberOfMatches;
    }

    public String getNumber() {
        return Number;
    }

    public float getRating() {
        return Rating;
    }
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "player")
    public List<TeamRating> getGamesRating() {
        return GamesRating;
    }

    public void setNumberOfMatches(int numberOfMatches) {
        NumberOfMatches = numberOfMatches;
    }
    public void setPosition(String position) {
        Position = position;
    }
    public void updateNumberOfMatches() {
        NumberOfMatches +=1 ;
    }
    public void setRating(float rating) {
        Rating = rating;
    }
    public void updateRating(float rat){
        setRating((getRating()*getNumberOfMatches()+rat)/(getNumberOfMatches()+1));
        updateNumberOfMatches();
    }
    public void setNumber(String number) {
        Number = number;
    }

    public void setGamesRating(List<TeamRating> gamesRating) {
        GamesRating = gamesRating;
    }


}
