package stage_3.src.Level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import stage_3.src.Candies; // Import the Candy class
import stage_3.src.Logic;

public class EasyLevel implements IDifficultyAdjustment {

    protected boolean isAdjacent(Point obstacle, Point curPos) {
        return Math.abs(obstacle.x - curPos.x) + Math.abs(obstacle.y - curPos.y) == 1;
    }

    public void removeObstaclesNear(Point position, List<Point> obstacles) {

    }

    public void placeObstaclesRandomly(int width, int height, List<Point> obstacles) {

    }

    public void removeWallObstaclesNear(Point point, List<Point> wallObstacles) {
    }

    @Override
    public void placeWallObstaclesRandomly(int width, int height, List<Point> wallObstacles) {
    }

}