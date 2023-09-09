import java.util.*;

public class Persona {
  private final String name;
  private final int arcana;
  private final int baselvl;
  private int lvl;
  private int hp;
  private int sp;
  private int def;
  private int atk;
  private int ag;
  private ArrayList<Skill> skills = new ArrayList<Skill>();
  private final int id;
  private int[] strengths;
  private int[] weaknesses;
  private Skill move;
  // name arcana baselvl lvl hp sp def atk ag skill1,2,3 id

  public void setStrengths(int[] strengths) {
    this.strengths = strengths;
  }

  public void setWeaknesses(int[] weaknesses) {
    this.weaknesses = weaknesses;
  }

  public Skill getMove() {
    return move;
  }

  public void setMove(Skill move) {
    this.move = move;
  }

  // 0 fool 1 magician 2 priestess
  // 3 empress 4 emperor 5 heirophant
  // 6 lovers 7 chariot 8 justice 9 hermit 10 fortune
  // 11 strength 12 hangedman
  // 13 death 14 temperence 15 devil 16 tower
  // 17 star 18 moon 19 sun 20 judgement
  private static final String[] arclist = new String[] { "Fool", "Magician", "Priestess", "Empress", "Emperor",
      "Heirophant", "Lovers", "Chariot", "Justice", "Hermit", "Fortune", "Strength", "Hanged Man", "Death",
      "Temperence", "Devil", "Tower", "Star", "Moon", "Sun", "Judgement" };

  public int[] getStrengths() {
    return strengths;
  }

  public int[] getWeaknesses() {
    return weaknesses;
  }

  public static String[] getArclist() {
    return arclist;
  }

  public Persona(String n, int a, int bl, int lvl, int hp, int sp, int def, int atk, int ag, Skill s1, Skill s2,
      Skill s3, int id, String weaknesses, String strengths) {
    this.name = n;
    this.arcana = a;
    this.baselvl = bl;
    this.lvl = lvl;
    this.hp = hp;
    this.sp = sp;
    this.def = def;
    this.atk = atk;
    this.ag = ag;

    this.skills.add(s1);
    this.skills.add(s2);
    this.skills.add(s3);
    this.id = id;

    String[] str = strengths.split("-");
    String[] wea = weaknesses.split("-");
    this.strengths = new int[str.length];
    this.weaknesses = new int[wea.length];
    for (int i = 0; i < str.length; i++) {
      this.strengths[i] = Integer.parseInt(str[i]);
    }
    for (int i = 0; i < wea.length; i++) {
      this.weaknesses[i] = Integer.parseInt(wea[i]);
    }
  }

  public String getName() {
    return name;
  }

  public int getArcana() {
    return arcana;
  }

  public int getBaselvl() {
    return baselvl;
  }

  public int getLvl() {
    return lvl;
  }

  public void setLvl(int lvl) {
    this.lvl = lvl;
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getSp() {
    return sp;
  }

  public void setSp(int sp) {
    this.sp = sp;
  }

  public int getDef() {
    return def;
  }

  public void setDef(int def) {
    this.def = def;
  }

  public int getAtk() {
    return atk;
  }

  public void setAtk(int atk) {
    this.atk = atk;
  }

  public int getAg() {
    return ag;
  }

  public void setAg(int ag) {
    this.ag = ag;
  }

  public ArrayList<Skill> getSkills() {
    return skills;
  }

  public void setSkills(ArrayList<Skill> skills) {
    this.skills = skills;
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    String[] elements = new String[] { "Physical", "Buff", "Fire", "Ice", "Wind", "Electric", "Curse", "Bless",
        "Almighty", "None" };
    ArrayList<String> weakwords = new ArrayList<String>();
    ArrayList<String> strongwords = new ArrayList<String>();
    for (int i = 0; i < weaknesses.length; i++) {
      weakwords.add(elements[weaknesses[i]]);
    }
    for (int i = 0; i < strengths.length; i++) {
      strongwords.add(elements[strengths[i]]);
    }

    String personaString = name.toUpperCase() + ": \n" + arclist[arcana] + "\n" + "Level " + lvl + "\n" + hp + " HP, "
        + sp + " SP, "
        + def + " DEF, " + atk + " ATK, " + ag + " AG" + "\n" + "\nSkills: " + "\n";
    for (int i = 0; i < skills.size(); i++) {
      personaString += skills.get(i).getName() + ": " + skills.get(i).toString() + "\n";
    }
    personaString += "\n" + "Weaknesses: " + weakwords.toString()
        + "\n" + "Strengths: " + strongwords.toString() + "\n";
    return personaString;
  }

  public String toShortString() {
    return name.toUpperCase() + ": " + hp + " HP, " + sp + " SP " + atk + " ATK, " + def + " DEF";
  }

  public void addSkill(Skill s) {
    this.skills.add(s);
  }

  public boolean equals(Persona p) {
    return (this.name == p.name);
  }
}