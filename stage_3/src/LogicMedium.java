package stage_3.src;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class LogicMedium extends LogicEasy {
    public LogicMedium(int candies_width, int candies_height, int moves_count, int AmountOfCandies) {
        super(candies_width, candies_height, moves_count, AmountOfCandies);
    }

    @Override
    void removeObstaclesNear(int i, int j) {
        List<Point> toRemove = new ArrayList<>();
        for (Point obstacle : obstacles) {
            if (isAdjacent(obstacle, i, j)) {
                toRemove.add(obstacle);
            }
        }
        obstacles.removeAll(toRemove);
    }

    @Override
    void placeObstaclesRandomly() {
        for (int i = 0; i < candies_width; i++) {
            for (int j = 0; j < candies_height; j++) {
                if (Math.random() < 0.1) { // 10% chance to place an obstacle
                    obstacles.add(new Point(i, j));
                }
            }
        }
    }
}
