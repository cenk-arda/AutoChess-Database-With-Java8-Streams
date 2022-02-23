import java.util.*;

public class Player {
    private final String name;
    private final List<Hero> heroes;

    public Player(String name, List<Hero> heroes) {
        this.name = name;
        this.heroes = heroes;
    }

    public Player(String name) {
        this.name = name;
        this.heroes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }


    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", heroes=" + heroes +
                '}';
    }
}
