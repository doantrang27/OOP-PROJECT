package stage_3.src.Level;

import java.awt.Point;
import java.util.List;

public interface IDifficultyAdjustment {
    public void removeObstaclesNear(Point position, List<Point> obstacles);

    public void placeObstaclesRandomly(int width, int height, List<Point> obstacles);

    public void removeWallObstaclesNear(Point point, List<Point> wallObstacles);

    public void placeWallObstaclesRandomly(int width, int height, List<Point> wallObstacles);

}
