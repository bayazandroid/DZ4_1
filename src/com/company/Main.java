package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 400};
    public static int[] heroesDamage = {25, 20, 15, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "MedicalHelp"};
    public static int round_number = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        round_number++;
        chooseBossDefence();
        bossHits();
        medicHelping();
        heroesHit();
        printStatistics();
    }

    private static void medicHelping() {
        if (heroesHealth[heroesHealth.length-1] > 0) {
            Random random = new Random();
            int indexOfRandomHero = random.nextInt(heroesHealth.length - 2);
            int coeff = random.nextInt(heroesHealth.length) + 1;
            int heroHealth = heroesHealth[indexOfRandomHero];
            if (heroesHealth[indexOfRandomHero] < 100){
                if (heroesHealth[indexOfRandomHero] > 0){
                    heroesHealth[indexOfRandomHero] = coeff * heroHealth;
                    System.out.println("Medic вылечил героя " + heroesAttackType[indexOfRandomHero]
                            + " здоровье у которого был " + heroHealth
                            + " с коеффициентом "+coeff + " подняв здоровье героя на "
                            + coeff * heroHealth + " единиц");
                } else {
                    System.out.println("Medic с коеффициентом " + coeff
                            + " хотел вылечить павшего героя " + heroesAttackType[indexOfRandomHero]
                            + " здоровье у которого был " + heroHealth
                    );
                }
            } else {
                System.out.println("Medic хотел вылечит героя "
                        + heroesAttackType[indexOfRandomHero]
                        + " здоровье у которого был " + heroHealth
                        + " используя коеффициент "
                        + coeff + " но герой был здоров");
            }
            System.out.println();
        }
    }


    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss chose: " + bossDefence);

    }

    public static void printStatistics() {
        System.out.println(round_number + " ROUND _____________");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] <= 0) {
                continue;
            }
            if (heroesHealth[i] - bossDamage < 0) {
                heroesHealth[i] = 0;
            } else {
                heroesHealth[i] = heroesHealth[i] - bossDamage;
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth <= 0 || heroesHealth[i] <= 0) {
                continue;
            }
            if (heroesAttackType[i] == bossDefence) {
                Random random = new Random();
                int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                int criticalDamage = heroesDamage[i] * coeff;
                if (bossHealth - criticalDamage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - criticalDamage;
                }
                System.out.println("Critical damage: " + criticalDamage);
            } else {
                if (bossHealth - heroesDamage[i] < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - heroesDamage[i];
                }
            }
        }
    }
}

