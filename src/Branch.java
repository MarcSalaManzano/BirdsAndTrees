import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Branch {

    private List<Bird> birds;
    public Branch() {
        birds = new ArrayList<>();
    }

    public void endStep() {
        Iterator<Bird> i = birds.listIterator();
        while(i.hasNext()) {
            Bird bird = i.next();
            bird.switchActionDone();
            if(bird.isDead()) {
                i.remove();
            }
        }
    }
    public void step(List<Branch> branches) {
        List<Bird> birdsThatEat = new ArrayList<>();
        List<Bird> birdsThatReproduceMale = new ArrayList<>();
        List<Bird> birdsThatReproduceFemale = new ArrayList<>();
        List<Bird> birdsThatFly = new ArrayList<>();
        for (Bird bird : birds) {
            Action action = bird.chooseAction();
            switch (action) {
                case EAT -> {
                    bird.incrementTurnsWithoutEat();
                    bird.switchActionDone();
                    birdsThatEat.add(bird);
                }
                case REPRODUCE -> {
                    bird.incrementTurnsWithoutEat();
                    bird.switchActionDone();
                    if (bird.isMale()) birdsThatReproduceMale.add(bird);
                    else birdsThatReproduceFemale.add(bird);
                }
                case FLY -> {
                    bird.incrementTurnsWithoutEat();
                    bird.switchActionDone();
                    birdsThatFly.add(bird);
                }
                default -> {
                }
            }
        }
        birdsEat(birdsThatEat);
        birdsReproduce(birdsThatReproduceMale, birdsThatReproduceFemale);
        birdsFly(birdsThatFly, branches);
    }

    private void birdsFly(List<Bird> birdsThatFly, List<Branch> branches) {
        Random random = new Random();
        for (Bird bird: birdsThatFly) {
            bird.fly(this, branches);
        }
    }

    private void birdsEat(List<Bird> birdsThatEat) {
        if (birdsThatEat.size() == 0) return;
        Random random = new Random();
        int chosenBird = random.nextInt(birdsThatEat.size());
        birdsThatEat.get(chosenBird).eat();
    }

    private void birdsReproduce(List<Bird> birdsThatReproduceMale, List<Bird> birdsThatReproduceFemale) {
        Random random = new Random();
        int numberOfMales = birdsThatReproduceMale.size();
        int numberOfFemales = birdsThatReproduceFemale.size();
        int numberOfPairs = Math.min(numberOfMales, numberOfFemales);
        for(int i = 0; i < numberOfPairs; i++) {
            int maleIndex = random.nextInt(numberOfMales);
            int femaleIndex = random.nextInt(numberOfFemales);
            birdsThatReproduceMale.remove(maleIndex);
            birdsThatReproduceFemale.remove(femaleIndex);
            this.addBird(new Bird());
        }
    }

    public List<Bird> getBirds() {
        return birds;
    }

    public void addBird(Bird bird) {
        birds.add(bird);
    }

    public void removeBird(Bird bird) {
        birds.remove(bird);
    }

    @Override
    public String toString() {
        if(birds.isEmpty()) return "No birds \n";
        String aux = "";
        for(Bird bird: birds) {
            aux += bird.toString() + "\n";
        }
        return aux;
    }
}
