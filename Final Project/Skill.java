import java.util.*;

public class Skill {
  private String name;
  private int dmg;
  private int element;
  private static final String[] elementList = new String[] { "Physical", "Buff", "Fire", "Ice", "Wind", "Electric",
      "Curse", "Bless", "Almighty", "None" };

  public void setName(String name) {
    this.name = name;
  }

  public int getElement() {
    return element;
  }

  public void setElement(int element) {
    this.element = element;
  }

  public static String[] getElementlist() {
    return elementList;
  }

  public int getBuffType() {
    return buffType;
  }

  public void setBuffType(int buffType) {
    this.buffType = buffType;
  }

  public int getId() {
    return id;
  }

  public int getHpCost() {
    return hpCost;
  }

  public int getSpCost() {
    return spCost;
  }

  private int buffType;
  private final int id;
  private final int hpCost;
  private final int spCost;

  public Skill(String name, int dmg, int element, int buffType, int spCost, int hpCost, int id) {
    this.name = name;
    this.dmg = dmg;
    this.element = element;
    this.buffType = buffType;
    this.id = id;
    this.hpCost = hpCost;
    this.spCost = spCost;
  }

  public String getName() {
    return name;
  }

  public void setSkillName(String name) {
    this.name = name;
  }

  public int getDmg() {
    return dmg;
  }

  public void setDmg(int dmg) {
    this.dmg = dmg;
  }

  public int calculateDmg(Persona attacker, Persona defender) {
    boolean resist = false;
    // check if defender is strong to attack
    for (int i = 0; i < defender.getStrengths().length; i++) {
      if (attacker.getMove().getElement() == defender.getStrengths()[i]) {
        resist = true;
        System.out.println("\n" + name + " was not very effective!");
      }
    }

    boolean buff = false;
    // check if defender is weak to attack
    for (int i = 0; i < defender.getWeaknesses().length; i++) {
      if (attacker.getMove().getElement() == defender.getWeaknesses()[i]) {
        buff = true;
        System.out.println("\n" + name + " was super effective!");
      }
    }

    if (resist) {
      if (dmg > 0) {
        int damage = (int) ((attacker.getAtk() + dmg) * 0.66 - defender.getDef());

        if (damage < 0) {
          damage = 0;
        }

        return damage;
      }
      return 0;
    }

    else if (buff) {
      if (dmg > 0) {
        int damage = (int) ((attacker.getAtk() + dmg) * 1.5 - defender.getDef());

        if (damage < 0) {
          damage = 0;
        }

        return damage;
      }
      return 0;
    } else {
      if (dmg > 0) {
        int damage = (int) ((attacker.getAtk() + dmg) - defender.getDef());

        if (damage < 0) {
          damage = 0;
        }

        return damage;
      }
      return 0;
    }
  }

  public boolean nameEquals(String s) {
    return (s.equals(this.name));
  }

  @Override
  public String toString() {
    String desc = "";
    if (dmg > 0) {
      desc += "Deals " + dmg + " " + elementList[element] + " damage";
    }
    if (buffType == 1) {
      desc += "Increases caster's attack power by 30%";
    }
    if (buffType == 2) {
      desc += "Decreases target's attack power by 30%";
    }
    if (buffType == 3) {
      desc += "Increases caster's defense by 30%";
    }
    if (buffType == 4) {
      desc += "Decreases target's defense by 30%";
    }
    if (buffType == 6) {
      desc += "Heals caster by a percentage of max HP";
    }
    if (hpCost > 0) {
      desc += " (" + hpCost + " HP)";
    }
    if (spCost > 0) {
      desc += " (" + spCost + " SP)";
    }
    return desc;
  }
}