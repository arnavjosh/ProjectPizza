public class Ability {
  private String name;
  private int damage;
  private int heal;

  public Ability(String n, int dmg) {
    name = n;
    damage = dmg;
  }

  public Ability(String n, int dmg, int hl) {
    name = n;
    damage = dmg;
    heal = hl;
  }

  public String getAbilityName() {
    return name;
  }
}
