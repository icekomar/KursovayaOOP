package models;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class GameCalendar {
    private List<Game> Games = new ArrayList<>();
    @PersistenceContext
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }
    public GameCalendar(EntityManager em){
        setEm(em);
    }
    public List<Game> getGames() {
        return Games;
    }

    public void setGames() {
        this.Games =  em.createQuery(
                "SELECT p FROM Game p").getResultList();
    }
    public Object[][] ListToObject(List<Game> Games) {
        Object[][] str = new Object[Games.size()][];

        for (int i = 0; i < Games.size(); i++) {

            str[i] = new Object[]{(Games.get(i).getId()), Games.get(i).getNameOpponent(), Games.get(i).getResult()
                    , Games.get(i).getSide(), Games.get(i).getDate()};
        }
        return str;
    }

    public String[][] ListToString() {
        String[][] str = new String[Games.size()][];

        for (int i = 0; i < Games.size(); i++) {

            str[i] = new String[]{String.valueOf((Games.get(i).getId())), Games.get(i).getNameOpponent(), Games.get(i).getResult()
                    , Games.get(i).getSide(), Games.get(i).getDate()};
        }
        return str;
    }
    public List<Game> getPlayedGames() {
        List<Game> n=new ArrayList<>();
        for (Game game : Games) {
            if (game.getResult().length() != 0) n.add(game);
        }
        return n;
    }
    public List<Game> getUpcomingGames() {
        List<Game> n=new ArrayList<>();
        for (Game game : Games) {
            if (game.getResult().length() == 0) n.add(game);
        }
        return n;
    }
}
