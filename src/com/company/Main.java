package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 3000;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 260, 250, 600, 220, 240, 500, 400};
    public static int[] heroesDamage = {25, 20, 15, 5, 10, 15, 25, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Golem", "Lucky",
            "Berserk", "Thor", "MedicalHelp"};
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

    public static int golemProtection(){
        int softenedBossDamage;
        if (heroesHealth[3] >0){
            softenedBossDamage = bossDamage - 10;
            int countOfHeroes = 0;
            for (int i = 0; i < heroesHealth.length; i++) {
                if (!heroesAttackType[i].equals("Golem")) {
                    if (heroesHealth[i] > 0){
                        countOfHeroes++;
                    }
                }
            }
            if (heroesHealth[3] - countOfHeroes * 10 - 10 > 0) {
                heroesHealth[3] = heroesHealth[3] - countOfHeroes * 10 - 10;
                System.out.println("Golem взял на себя " + (countOfHeroes * 10) + " урона" );
            }else softenedBossDamage = 50;
        } else {
            softenedBossDamage = 50;
        }
        return softenedBossDamage;
    }

    public static void bossHits() {
        if (!thorShockedBoss()) {
            bossDamage = golemProtection();
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] <= 0) {
                    continue;
                }
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    if (heroesAttackType[i].equals("Lucky")){
                        luckyIsSuccesing(bossDamage);
                    } else if (heroesAttackType[i].equals("Berserk")){
                        berserkFighting(bossDamage, i);
                    }
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
        bossDamage=50;
    }

    private static boolean thorShockedBoss() {
        if (heroesHealth[6] > 0){
            Random random =new Random();
            boolean lostHummer = random.nextBoolean() && random.nextBoolean() && random.nextBoolean();
            if (lostHummer){
                System.out.println("Boss на этот раз оглушен молотом Thorа");
            } else {
                System.out.println("Thor не смог вызвать молнию и удивить Bossa");
            }
            return lostHummer;
        }
        return false;
    }

    private static void berserkFighting(int bossDamage, int i) {
        Random random = new Random();
        int randomNumber = random.nextInt(heroesAttackType.length) + 1;
        int def = bossDamage / randomNumber;
        if (bossHealth > 0 && bossHealth - def > 0){
            bossHealth -= def;
            heroesHealth[i] += def;
            System.out.println("Berserk отразил и возвратил " + def + " + к своему урон");
        }
    }

    private static void luckyIsSuccesing(int bossDamage) {
        Random random = new Random();
        int randomIndex =  random.nextInt(heroesAttackType.length);
        if (randomIndex == 4 || randomIndex == 0){
            System.out.println("Lucky везунчик");
            heroesHealth[4] += bossDamage;
        } else {
            System.out.println("Lucky получил по заслугам");
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

