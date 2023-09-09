import java.io.BufferedReader;
import java.io.IOException;
// Persona RPG Simulator
// Nicholas Cope, Zachary Sanetick
// 5/18/2023
// A text-based RPG that mimics games from the persona series.
// We agree to abide by the Academic Honesty Agreement.

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Main {
  public static void main(String[] args) {
    Music musicPlayer = new Music();
    musicPlayer.playMusic("Music/lastsurprise.wav");

    ArrayList<Skill> skillList = new ArrayList<Skill>();
    skillList = initSkill();
    ArrayList<Persona> personaList = new ArrayList<Persona>();
    personaList = initPersona(skillList);

    Battle battle1 = new Battle(personaList.get(0), personaList.get(3));
    battle1.startBattle();

    checkLoss(battle1);

    Battle battle2 = new Battle(personaList.get(0), personaList.get(1));
    battle2.startBattle();

    checkLoss(battle2);

    Battle battle3 = new Battle(personaList.get(0), personaList.get(6));
    battle3.startBattle();

    checkLoss(battle3);

    System.out.println("You are the #1 Persona!");
  }

  public static ArrayList<Persona> initPersona(ArrayList<Skill> skillList) {
    // name arcana baselvl lvl hp sp def atk ag skill1,2,3 id

    // 0 fool 1 magician 2 priestess
    // 3 empress 4 emperor 5 heirophant
    // 6 lovers 7 chariot 8 justice 9 hermit 10 fortune
    // 11 strength 12 hangedman
    // 13 death 14 temperence 15 devil 16 tower
    // 17 star 18 moon 19 sun 20 judgement

    // load spreadsheet
    ArrayList<Persona> personaList = new ArrayList<Persona>();
    Path pathToFile = Paths.get("Spreadsheets/PersonaInfo.csv");
    try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
      br.readLine();
      String line = br.readLine();
      while (line != null) {
        String[] c = line.split(",");

        int[] d = new int[9];
        for (int i = 1; i < 9; i++) {
          d[i - 1] = Integer.parseInt(c[i]);
        }
        d[8] = Integer.parseInt(c[12]);

        Skill s1 = skillList.get(0);
        Skill s2 = skillList.get(0);
        Skill s3 = skillList.get(0);
        for (int i = 0; i < skillList.size(); i++) {
          if (skillList.get(i).nameEquals(c[9])) {
            s1 = skillList.get(i);
          }
        }
        for (int i = 0; i < skillList.size(); i++) {
          if (skillList.get(i).nameEquals(c[10])) {
            s2 = skillList.get(i);
          }
        }
        for (int i = 0; i < skillList.size(); i++) {
          if (skillList.get(i).nameEquals(c[11])) {
            s3 = skillList.get(i);
          }
        }

        Persona p = new Persona(c[0], d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], s1, s2, s3, d[8], c[13], c[14]);
        personaList.add(p);
        line = br.readLine();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return personaList;
  }

  public static ArrayList<Skill> initSkill() {
    ArrayList<Skill> skillList = new ArrayList<Skill>();
    Path pathToFile = Paths.get("Spreadsheets/SkillInfo.csv");
    try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
      br.readLine();
      String line = br.readLine();
      while (line != null) {
        String[] c = line.split(",");
        // String name, int dmg, int element, int buffType, int spCost, int hpCost, int
        // id

        
        Skill s = new Skill(c[0], Integer.parseInt(c[1]), Integer.parseInt(c[2]), Integer.parseInt(c[3]),
            Integer.parseInt(c[4]), Integer.parseInt(c[5]), Integer.parseInt(c[6]));
        skillList.add(s);
        line = br.readLine();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return skillList;
  }

  public static void checkLoss(Battle battle) {
    if (battle.getVictory() == false) {
      System.out.println("\nGame over! Get back up and try again!\n");
      System.exit(69);
    }
  }
}