package stage_3.src.Level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class IntermediateLevel implements IDifficultyAdjustment {

    private boolean isAdjacent(Point obstacle, Point curPos) {
        return Math.abs(obstacle.x - curPos.x) + Math.abs(obstacle.y - curPos.y) == 1;
    }

    @Override
    public void removeObstaclesNear(Point position, List<Point> obstacles) {
        List<Point> toRemove = new ArrayList<>();
        for (Point obstacle : obstacles) {
            if (isAdjacent(obstacle, position)) {
                toRemove.add(obstacle);
            }
        }
        obstacles.removeAll(toRemove);
    }

    @Override
    public void addObstaclesNear() {

    }

    @Override
    public void placeObstaclesRandomly(int width, int height, List<Point> obstacles) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.random() < 0.1) { // 10% chance to place an obstacle
                    obstacles.add(new Point(i, j));
                }
            }
        }
    }

}
