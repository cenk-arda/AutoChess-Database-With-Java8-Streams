import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Database {
    private static final Database _inst = new Database();

    public static Database getInstance() {
        return _inst;
    }

    private List<Hero> heroes;
    private List<HeroAlliance> heroAlliances;
    private List<Alliance> alliances;
    private List<Player> players;

    // A static method for getting a hero from a string (used in parsing players). It is allowed according to: https://odtuclass2021f.metu.edu.tr/mod/forum/discuss.php?d=25260#p39047
    public static Hero getHeroFromString(String nameAndLevel) {
        String[] values = nameAndLevel.split("\\|");
        Optional<Hero> matchedHero = Database.getInstance().heroes.stream().filter(e -> Objects.equals(e.getName(), values[0]) && e.getLevel() == Integer.parseInt(values[1])).findFirst();
        return matchedHero.orElse(null);
    }


    public Database() {
        //TODO optional

        this.heroes = new ArrayList<Hero>();   //array list or another?
        this.heroAlliances = new ArrayList<HeroAlliance>();
        this.alliances = new ArrayList<Alliance>();
        this.players = new ArrayList<Player>();

    }

    //Parse the CSV files and fill the four lists given above.
    public void parseFiles(String playerCSVFile) throws IOException {
        //TODO
        if (Database.getInstance().heroes.isEmpty()) {
            Function<String, Hero> mapToHero = (line) -> {
                String[] values = line.split(",");
                return new Hero(values[0], //name:str
                        Integer.parseInt(values[1]),  //Level:int
                        Integer.parseInt(values[2]),  //Health:int
                        Integer.parseInt(values[3]),  //Mana:int
                        Integer.parseInt(values[4]),  //DPS:int
                        Integer.parseInt(values[5]),  //Damage-Min:int
                        Integer.parseInt(values[6]),  //Damage-Max:int
                        Double.parseDouble(values[7]),//Attack Speed:double
                        Integer.parseInt(values[8]),  //Move Speed:int
                        Integer.parseInt(values[9]),  //Attack Range:int
                        Integer.parseInt(values[10]), //Magic Resist:int
                        Integer.parseInt(values[11])  //Armor:int
                );
            };
            this.heroes = Files.lines(Paths.get("herostats.csv")).map(mapToHero).collect(Collectors.toList());
        }
        if (Database.getInstance().heroAlliances.isEmpty()) {
            Function<String, HeroAlliance> mapToHeroAlliance = (line) -> {
                String[] values = line.split(",");
                return new HeroAlliance(values[0], //name:str
                        Integer.parseInt(values[1]),  //Level:int
                        (Arrays.stream(values).skip(2).toArray(String[]::new))
                );
            };
            this.heroAlliances = Files.lines(Paths.get("heroalliances.csv")).map(mapToHeroAlliance).collect(Collectors.toList());
        }

        if (Database.getInstance().alliances.isEmpty()) {
            Function<String, Alliance> mapToAlliance = (line) -> {
                String[] values = line.split(",");
                return new Alliance(values[0], //AllianceName:str
                        Integer.parseInt(values[1]),  //RequiredHeroCount:int
                        Integer.parseInt(values[2])   //LevelCount:int
                );
            };
            this.alliances = Files.lines(Paths.get("alliances.csv")).map(mapToAlliance).collect(Collectors.toList());
        }

        //parse player everytime
        Function<String, Player> mapToPlayer = (line) -> {
            String[] values = line.split(",");
            String playerName = values[0];
            List<Hero> heroesOfPlayer = Stream.of(values).skip(1).map(Database::getHeroFromString).collect(Collectors.toList());
            return new Player(playerName, heroesOfPlayer);
        };
        this.players = Files.lines(Paths.get(playerCSVFile)).map(mapToPlayer).collect(Collectors.toList());


    }

    //Gets the heroes belonging to a particular alliance and sorts them according to their DPS. It should only return
    //count number of heroes. Heroes should be distinct in a sense that, different levels of the same hero should not be
    //in the result.
    //15pts
    public List<Hero> getHeroesOfParticularAlliance(String alliance, int count) {
        //TODO

        List<HeroAlliance> HeroAlliancesHasAlliance = heroAlliances.stream().filter(o -> o.getAlliances().contains(alliance)).collect(Collectors.toList());

        List<Hero> result = HeroAlliancesHasAlliance.stream().flatMap(
                        (ha) -> heroes.stream().filter(hero -> Objects.equals(hero.getName(), ha.getName())))
                .sorted(Comparator.comparing(Hero::getDPS).reversed()).distinct().limit(count).collect(Collectors.toList());
        return result;
    }

    //Returns a map of HeroAlliances based on tier where the alliance required count and alliance level counts match.
    //15pts
    public Map<Integer, List<HeroAlliance>> getHeroAllianceMatchingTier(int allianceRequiredCount, int allianceLevelCount) {
        //TODO
        Set<String> allianceSet = alliances.stream().filter(a -> a.getLevelCount() == allianceLevelCount && a.getRequiredCount() == allianceRequiredCount).map(Alliance::getName).collect(Collectors.toSet());

        List<HeroAlliance> resultList = heroAlliances.stream().
                filter(heroAlliance -> heroAlliance.getAlliances().stream().anyMatch(allianceSet::contains)).
                collect(Collectors.toList());

        return resultList.stream().collect(Collectors.groupingBy(HeroAlliance::getTier));
    }

    //Return the heroes of each player that have bigger than the mana, health and move speed given as arguments.
    //10pts
    public List<List<Hero>> getPlayerHeros(int mana, int health, int moveSpeed) {
        Predicate<Hero> cond = hero -> hero.getMana() > mana && hero.getHealth() > health && hero.getMoveSpeed() > moveSpeed;
        return players.stream().map(
                p -> p.getHeroes().stream().filter(cond).collect(Collectors.toList())
        ).collect(Collectors.toList());

    }

    //Calculate and print the average maximum damage of players whose heroes has minimum damage is bigger than the given first argument.
    //10 pts
    public void printAverageMaxDamage(int minDamage) {
        //TODO
        players.stream().filter(p -> p.getHeroes().stream().filter(hero -> hero.getDamageMin() > minDamage).
                mapToDouble(Hero::getDamageMax).average().isPresent()).forEach(player -> System.out.println(player.getHeroes().stream().filter(hero -> hero.getDamageMin() > minDamage).
                mapToDouble(Hero::getDamageMax).average().getAsDouble()));

    }

    //In this function, print each player and its heroes. However, you should only print heroes belonging to
    // any of the particular alliances and whose attack speed is smaller than or equal to the value given.
    //30pts
    public void printAlliances(String[] alliances, double attackSpeed) {
        //TODO
        Set<String> alliancesSet = Arrays.stream(alliances).collect(Collectors.toSet());
        List<HeroAlliance> resultList = heroAlliances.stream().
                filter(heroAlliance -> heroAlliance.getAlliances().stream().anyMatch(alliancesSet::contains)).
                collect(Collectors.toList());
        Set<String> ValidHeroNames = resultList.stream().map(HeroAlliance::getName).collect(Collectors.toSet());

        players.stream().filter(
                        p -> p.getHeroes().stream()
                                .filter(hero -> hero.getAttackSpeed() <= attackSpeed).anyMatch(hero -> ValidHeroNames.contains(hero.getName())))
                .forEach(
                        player -> {
                            System.out.println(player.getName());
                            player.getHeroes().stream()
                                    .filter(hero -> hero.getAttackSpeed() <= attackSpeed)
                                    .filter(hero -> ValidHeroNames.contains(hero.getName())).forEach(
                                            hero -> System.out.print("Name: " + hero.getName() + " Level: " + hero.getLevel() + "\n")
                                    );
                        }
                );
    }


}
