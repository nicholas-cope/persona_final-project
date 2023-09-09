import java.util.*;

public class Battle {
  private Persona enemy;
  private Persona player;
  private static int[] baseStats;
  private boolean victory;
  private static int[] baseEnemy;

  public Battle(Persona p, Persona e) {
    this.enemy = e;
    this.player = p;
    this.baseStats = new int[] { player.getHp(), player.getSp(), player.getAtk(), player.getDef() };
    this.baseEnemy = new int[] { enemy.getHp(), enemy.getSp(), enemy.getAtk(), enemy.getDef() };
    this.victory = false;
  }

  public boolean getVictory() {
    return victory;
  }

  public void setVictory(boolean victory) {
    this.victory = victory;
  }

  public void startBattle() {
    boolean gameOver = false;
    System.out.println(player.toString());
    System.out.println(enemy.toString());

    // battle loop
    while (!gameOver) {
      // decide who goes first based on agility
      if (player.getAg() >= enemy.getAg()) {
        playerTurn();
        if (enemy.getHp() <= 0) {
          System.out.println("\n" + player.getName() + " defeated " + enemy.getName() + "!");
          victoryTime(player, enemy);
          gameOver = true;
          break;
        }

        enemyTurn();
        if (player.getHp() <= 0) {
          System.out.println("\n" + enemy.getName() + " defeated " + player.getName() + "!");
          gameOver = true;
          break;
        }
        System.out.println("\n" + player.toShortString());
        System.out.println("\n" + enemy.toShortString());
      }

      else {
        enemyTurn();
        if (player.getHp() <= 0) {
          System.out.println("\n" + enemy.getName() + " defeated " + player.getName() + "!");
          gameOver = true;
          break;
        }

        playerTurn();
        if (enemy.getHp() <= 0) {
          System.out.println("\n" + player.getName() + " defeated " + enemy.getName() + "!");
          victoryTime(player, enemy);
          gameOver = true;
          break;
        }
        System.out.println("\n" + player.toShortString());
        System.out.println("\n" + enemy.toShortString() + "\n");
      }
    }
  }

  public void playerTurn() {
    ArrayList<Skill> skills = player.getSkills();
    System.out.println("\n" + "Choose your skill:");
    for (int i = 0; i < skills.size(); i++) {
      System.out.println((i + 1) + ") " + skills.get(i).getName());
    }
    System.out.println();

    Scanner sc = new Scanner(System.in);
    int skillNum = sc.nextInt();

    Skill move = skills.get(skillNum - 1);
    player.setMove(move);
    player.setHp(player.getHp() - move.getHpCost());
    if (player.getSp() >= move.getSpCost()) {
      player.setSp(player.getSp() - move.getSpCost());
      int damage = move.calculateDmg(player, enemy);
      if (damage > 0) {
        System.out.println("\n" + player.getName() + " used " + move.getName() + " and dealt " + damage + " damage!");
        enemy.setHp(enemy.getHp() - damage);
      }
      if (move.getBuffType() != 0) {
        calculateBuff(player, enemy, move);
      }
    }
  }

  public void enemyTurn() {
    ArrayList<Skill> skills = enemy.getSkills();
    int skillNum = (int) (Math.random() * skills.size());

    Skill move = skills.get(skillNum);
    enemy.setMove(move);
    enemy.setHp(enemy.getHp() - move.getHpCost());
    if (enemy.getHp() <= 0) {
      System.out.println("\n" + player.getName() + " defeated " + enemy.getName() + "!");
      victoryTime(player, enemy);
    }
    if (enemy.getSp() >= move.getSpCost()) {
      enemy.setSp(enemy.getSp() - move.getSpCost());
      int damage = move.calculateDmg(enemy, player);
      if (damage > 0) {
        System.out.println("\n" + enemy.getName() + " used " + move.getName() + " and dealt " + damage + " damage!");
        player.setHp(player.getHp() - damage);
      }
      if (move.getBuffType() != 0) {
        calculateBuff(enemy, player, move);
      }
    }
  }

  public void calculateBuff(Persona caster, Persona defender, Skill move) {
    // Tarakaja - increase caster attack
    if (move.getBuffType() == 1) {
      System.out.println("\n" + caster.getName() + " used " + move.getName() + " and increased its attack by "
          + (int) (Math.round(caster.getAtk() * 1.3 - caster.getAtk())) + " points!");
      caster.setAtk((int) (Math.round((caster.getAtk()) * 1.3)));
    }
    // Tarunda - decrease defender attack
    else if (move.getBuffType() == 2) {
      System.out.println("\n" + caster.getName() + " used " + move.getName() + " and decreased " + defender.getName()
          + "'s attack by " + (int) (Math.round(defender.getAtk() - defender.getAtk() * .7)) + " points!");
      defender.setAtk((int) (Math.round((player.getAtk()) * .7)));
    }
    // Rakukaja - increase caster defense
    else if (move.getBuffType() == 3) {
      System.out.println("\n" + caster.getName() + " used " + move.getName() + " and increased its defense by "
          + (int) (Math.round(caster.getDef() * 1.3 - caster.getDef())) + " points!");
      caster.setDef((int) ((caster.getDef()) * 1.3));
    }
    // Rakunda - decrease defender defense
    else if (move.getBuffType() == 4) {
      System.out.println("\n" + caster.getName() + " used " + move.getName() + " and decreased " + defender.getName()
          + "'s defense by " + (int) (Math.round(defender.getDef() - defender.getDef() * .7)) + " points!");
      defender.setDef((int) (Math.round((defender.getDef()) * .7)));
    }
    // In - decreases defender's attack and defense by half
    else if (move.getBuffType() == 5) {
      defender.setAtk((int) ((defender.getAtk()) * .5));
      defender.setDef((int) ((defender.getDef()) * .5));
    } else if (move.getBuffType() == 6) {
      if (enemy.equals(caster)) {
        System.out.println("\n" + caster.getName() + " used " + move.getName() + " and healed by "
            + (int) Math.round(baseEnemy[0] * (-1.0 * (1.0 / move.getDmg()))) + " points!");
        caster.setHp((int) Math.round(baseEnemy[0] * (-1.0 * (1.0 / move.getDmg()))) + caster.getHp());
        if (caster.getHp() > baseEnemy[0]) {
          caster.setHp(baseEnemy[0]);
        }
      } else {
        System.out.println("\n" + move.getDmg() + caster.getName() + " used " + move.getName() + " and healed by "
            + (int) Math.round(baseStats[0] * (-1.0 * (1.0 / move.getDmg()))) + " points!");
        caster.setHp((int) Math.round(baseStats[0] * (-1.0 * (1.0 / move.getDmg()))) + caster.getHp());
        if (caster.getHp() > baseStats[0]) {
          caster.setHp(baseStats[0]);
        }
      }
    }
  }

  public void victoryTime(Persona winner, Persona loser) {
    player.setHp(baseStats[0]);
    player.setSp(baseStats[1]);
    player.setAtk(baseStats[2]);
    player.setDef(baseStats[3]);

    winner.setLvl(winner.getLvl() + 1);
    winner.setHp(winner.getHp() + 3);
    winner.setSp(winner.getSp() + 2);
    winner.setAtk(winner.getAtk() + 1);
    winner.setDef(winner.getDef() + 1);
    winner.setAg(winner.getAg() + 1);
    System.out.println("\n" + winner.getName() + " leveled up! " + "All stats increased.\n");

    int skillAmount = loser.getSkills().size();
    String[] skillSteal = new String[skillAmount];
    for (int i = 0; i < skillAmount; i++) {
      skillSteal[i] = loser.getSkills().get(i).getName() + ": " + loser.getSkills().get(i).toString() + "\n";
    }
    System.out.println("Victory time!! Choose a move to steal: \n");
    for (int i = 0; i < skillAmount; i++) {
      System.out.println((i + 1) + ") " + skillSteal[i]);
    }
    Scanner sc = new Scanner(System.in);
    int skillNum = sc.nextInt();
    Skill loserSkill = loser.getSkills().get(skillNum - 1);
    winner.addSkill(loserSkill);
    System.out.println("\n" + winner.getName() + " learned the skill " + loserSkill.getName() + "!\n");
    this.setVictory(true);
  }
}
