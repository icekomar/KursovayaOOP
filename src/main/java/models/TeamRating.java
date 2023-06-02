package models;


import javax.persistence.*;


@Entity
@Table(name = "team_rating")
public class TeamRating{
    private int Id;
    private  Game game;
    private Footballers player;
    private float rating;

    public TeamRating() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return Id;
    }

    public float getRating() {
        return rating;
    }
    public void setRating(float rating) {
        this.rating = rating;
    }
    @ManyToOne
    @JoinColumn(name="Game_Id",referencedColumnName = "id")
    public Game getGame() {
        return game;
    }
    @ManyToOne
    @JoinColumn(name="Footballers_Id",referencedColumnName = "id")
    public Footballers getPlayer() {
        return player;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPlayer(Footballers player) {
        this.player = player;
    }

    public void setId(int id) {
        Id = id;
    }
    public TeamRating(float rating,Game game,Footballers f){
        setRating(rating);
        setGame(game);
        setPlayer(f);
    }
}
