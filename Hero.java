public class Hero {
    private final String name;
    private final int level;
    private final int health;
    private final int mana;
    private final int DPS;
    private final int damageMin;
    private final int damageMax;
    private final double attackSpeed;
    private final int moveSpeed;
    private final int attackRange;
    private final int magicResist;
    private final int armor;


    public Hero(String name, int level, int health, int mana, int DPS, int damageMin, int damageMax, double attackSpeed, int moveSpeed, int attackRange, int magicResist, int armor) {
        this.name = name;
        this.level = level;
        this.health = health;
        this.mana = mana;
        this.DPS = DPS;
        this.damageMin = damageMin;
        this.damageMax = damageMax;
        this.attackSpeed = attackSpeed;
        this.moveSpeed = moveSpeed;
        this.attackRange = attackRange;
        this.magicResist = magicResist;
        this.armor = armor;
    }


    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", mana=" + mana +
                ", DPS=" + DPS +
                ", damageRange=" + (damageMin + "-" +  damageMax) +
                ", attackSpeed=" + attackSpeed +
                ", moveSpeed=" + moveSpeed +
                ", attackRange=" + attackRange +
                ", magicResist=" + magicResist +
                ", armor=" + armor +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getMana() {
        return mana;
    }

    public int getDPS() {
        return DPS;
    }

    public int getDamageMin() {
        return damageMin;
    }

    public int getDamageMax() {
        return damageMax;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public int getAttackRange() { 
        return attackRange; 
    }

    public int getMagicResist() {
        return magicResist;
    }

    public int getArmor() {
        return armor;
    }

}
