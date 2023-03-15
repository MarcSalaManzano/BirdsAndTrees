import java.util.List;
import java.util.Random;

public class Bird {
    public static final float FLY_CHANCE = 0.3f;
    public static final float EAT_CHANCE = 0.7f;

    private boolean male;
    private int turnsWithoutEat;
    private boolean actionDone;

    public Bird() {
        Random random = new Random();
        male = random.nextBoolean();
        turnsWithoutEat = 0;
    }

    public Action chooseAction() {
        if(actionDone) return Action.NONE;
        Random random = new Random();
        if (random.nextFloat() < FLY_CHANCE) return Action.FLY;
        else if (random.nextFloat() < EAT_CHANCE) return Action.EAT;
        else return Action.REPRODUCE;
    }

    public boolean isMale() {
        return male;
    }

    public boolean isDead() {
        return turnsWithoutEat >= 5;
    }

    public void fly(Branch actualBranch, List<Branch> forest) {
        Random random = new Random();
        int branchIndex = random.nextInt(forest.size());
        forest.get(branchIndex).addBird(this);
        actualBranch.removeBird(this);
    }

    public void eat() {
        turnsWithoutEat = 0;
    }

    public void switchActionDone() {
        actionDone = !actionDone;
    }

    public void incrementTurnsWithoutEat() {
        turnsWithoutEat++;
    }

    @Override
    public String toString() {
        return "Gender: " + (male ? "male" : "female") + " Dead: " + isDead();
    }

}
