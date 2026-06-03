package main.gameobjects.player;

public class Player {
    int money;
    int mp;
    int maxMP;
    int strength;

    int doraSkill, drawSkill, swapSkill;
    private int tdDrawPrice;
    private int tdDrawSkill;
    
    public Player() {
        this.money = 4;
        this.mp = 4;
        this.maxMP = 4;

        this.doraSkill = 1;
        this.swapSkill = 4;
        this.drawSkill = 36;
        this.strength = 1;
        this.tdDrawPrice = 4;
        this.tdDrawSkill = 4;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getMoney() {
        return this.money;
    }

    public int getMP() {
        return this.mp;
    }

    public int getMaxMP() {
        return this.maxMP;
    }

    public int getDoraSkill() {
        return this.doraSkill;
    }

    public int getDrawSkill() {
        return this.drawSkill;
    }

    public int getSwapSkill() {
        return this.swapSkill;
    }

    public void increaseMoney(int n) {
        this.money += n;
    }

    public boolean spendMoney(int n) {
        if (n > this.money) return false;
        this.money -= n;
        return true;
    }

    public void increaseMP(int n) {
        this.mp += n;
    }

    public boolean spendMP(int n) {
        if (n < this.mp) return false;
        this.mp -= n;
        return true;
    }

    public void increaseMaxMP(int n) {
        this.maxMP += n;
    }

    public int decreaseMaxMP(int n) {
        int oldMaxMP = this.maxMP;
        this.maxMP -= n;
        this.maxMP = Math.max(this.maxMP, 0);
        return this.maxMP - oldMaxMP;
    }

    public int updateMP() {
        int oldMP = this.mp;
        this.mp = Math.max(this.maxMP, this.mp);
        return this.mp - oldMP;
    }

    public int getTCDrawSkill() {
        return this.tdDrawSkill;
    }

    public int getTCDrawPrice() {
        return this.tdDrawPrice;
    }
}
