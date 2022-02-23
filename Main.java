/*
This file is for testing purposes
*/

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws IOException {

        Database.getInstance().parseFiles("players.csv");
        for (Hero hero: Database.getInstance().getHeroes()){
            System.out.println(hero.toString());
        }
        for (HeroAlliance hero_alliance: Database.getInstance().getHeroAlliances()){
            System.out.println(hero_alliance.toString());
        }
        for (Alliance alliance: Database.getInstance().getAlliances()){
            System.out.println(alliance.toString());
        }
        for (Player player: Database.getInstance().getPlayers()){
            System.out.println(player.toString());
        }
        int a = 0;
        Database.getInstance().parseFiles("players.csv");

//Q1 TEST START
        for (Hero hero: Database.getInstance().getHeroesOfParticularAlliance("Mage", 3)){
            System.out.println(hero);
        }
//Q1 TEST END

//Q2 TEST START
        Map<Integer, List<HeroAlliance>> map = Database.getInstance().getHeroAllianceMatchingTier(2,2);
        for ( Integer tier : map.keySet()) {
            System.out.print("Tier: " + tier + "\n");
            System.out.println("HeroAlliances:");
            map.get(tier).stream().forEach(System.out::println);
        }
//Q2 TEST END

//Q3 TEST START
        AtomicInteger i = new AtomicInteger(1);
        Database.getInstance().getPlayerHeros(80, 1800, 300).forEach(a -> {
            System.out.println("Player " + i.getAndIncrement());
            a.forEach(hero -> System.out.println(hero.toString()));
        });
//Q3 TEST END

//Q4 TEST START
       Database.getInstance().printAverageMaxDamage(60);
//Q4 TEST END

//Q5 TEST START
        Database.getInstance().printAlliances(new String[]{"Mage"},1.3);
//Q5 TEST END

    }
}
