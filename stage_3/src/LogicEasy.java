package stage_3.src;

public class LogicEasy extends Logic {
    public LogicEasy(int candies_width, int candies_height, int moves_count, int AmountOfCandies) {
        super(candies_width, candies_height, moves_count, AmountOfCandies);
    }

    @Override
    void removeObstaclesNear(int i, int j) {
        // TODO Auto-generated method stub
        System.out.println("It is an EASY level, nothing to do with `removeObstaclesNear` ");
    }

    @Override
    void placeObstaclesRandomly() {
        // TODO document why this method is empty
        System.out.println("It is an EASY level, nothing to do with `removeObstaclesNear` ");
    }
}
