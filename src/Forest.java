import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Forest {
    private Branch[][][] forest;
    private List<Branch> forestList;

    public Forest(int numberOfTrees, int width, int height, int depth) {
        this.forest = new Branch[width][height][depth];
        this.forestList = new ArrayList<>();
        Random random = new Random();
        boolean[][] plantedTrees = new boolean[width][depth];
        for(int i = 0; i < numberOfTrees; i++) {
            createTree(random, plantedTrees, width, height, depth);
        }
        for(int i = 0; i < numberOfTrees*2; i++) {
            int branchPosition = random.nextInt(forestList.size());
            forestList.get(branchPosition).addBird(new Bird());
        }
    }

    public void start() {
        int times = 0;
        while(times < 100) {
            for (Branch branch: forestList) {
                branch.step(forestList);
            }
            System.out.println(this);
            for (Branch branch: forestList) {
                branch.endStep();
            }
            times++;
        }
    }

    private void createTree(Random random, boolean[][] plantedTrees, int width, int height, int depth) {
        int xTree, zTree;
        do {
            xTree = random.nextInt(width-2)+1;
            zTree = random.nextInt(depth-2)+1;
        } while(plantedTrees[xTree][zTree]);
        plantedTrees[xTree][zTree] = true;
        int numberOfBranches = random.nextInt(10)+1;
        for(int i = 0; i < numberOfBranches; i++) {
            createBranch(random,plantedTrees, xTree, zTree, height);
        }
    }

    private void createBranch(Random random, boolean[][] plantedTrees, int xTree, int zTree, int height) {
        int yBranch, xBranch, zBranch;
        yBranch = random.nextInt(height);
        xBranch = random.nextBoolean() ? -1 : 1;
        zBranch = random.nextBoolean() ? -1 : 1;
        if(forest[xTree+xBranch][yBranch][zTree+zBranch] != null || plantedTrees[xTree+xBranch][zTree+zBranch]) return;
        Branch newBranch = new Branch();
        forest[xTree+xBranch][yBranch][zTree+zBranch] = newBranch;
        forestList.add(newBranch);
    }

    public static void main(String[] args) {
        int numberOfTrees = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);
        int depth = Integer.parseInt(args[3]);
        Forest forest = new Forest(numberOfTrees, width, height, depth);
        forest.start();
    }

    @Override
    public String toString() {
        String aux = "";
        for(int i = 0; i<forest.length; i++) {
            for(int j = 0; j<forest[i].length; j++) {
                for(int z = 0; z<forest[i][j].length; z++) {
                    if(forest[i][j][z] != null) {
                        aux+="Branch at x: " + i + " y: " + j + " z: " + z + " has: \n" + forest[i][j][z];
                    }
                }
            }
        }
        return aux;
    }
}
