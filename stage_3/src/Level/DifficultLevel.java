package stage_3.src.Level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Position;

import stage_3.src.Candies;
import java.awt.Point;

public class DifficultLevel extends IntermediateLevel implements
        IDifficultyAdjustment {
    // // 0.1 = this.obstacleProbability, if we only change the EasyLevel method
    private Candies[][] candies; // Declare the candies array
    private int candies_width; // Declare the candies_width variable
    private int candies_height; // Declare the candies_height variable
    // Assuming you have a method to generate a random position

    @Override
    public void placeWallObstaclesRandomly(int width, int height, List<Point> wallObstacles) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.random() < 0.1) {
                    wallObstacles.add(new Point(i, j));
                }
            }
        }
    }

    @Override
    public void removeWallObstaclesNear(Point point, List<Point> wallObstacles) {
        List<Point> toRemove = new ArrayList<>();
        for (Point wall : wallObstacles) {
            if (isAdjacent(wall, point)) {
                toRemove.add(wall);
            } else {
                // Check if there are matched candies near the wallObstacle
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i == 0 && j == 0)
                            continue; // Skip the wallObstacle's own position
                        if (isMatchAt(wall.x + i, wall.y + j)) {
                            toRemove.add(wall);
                            break;
                        }
                    }
                }
            }
        }
        wallObstacles.removeAll(toRemove);
    }

    @Override
    protected boolean isAdjacent(Point obstacle, Point curPos) {
        return Math.abs(obstacle.x - curPos.x) + Math.abs(obstacle.y - curPos.y) == 1;
    }

    @Override
    protected boolean isNearMatch(Point obstaclePosition) {
        // Check the surrounding cells of the obstacle for matches
        int x = obstaclePosition.x;
        int y = obstaclePosition.y;

        // Check horizontally and vertically around the obstacle
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue; // Skip the obstacle's own position
                if (isMatchAt(x + i, y + j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isMatchAt(int x, int y) {
        // Check for horizontal match
        if (x > 0 && x < candies_width - 1) {
            if (candies[x - 1][y].getCandyType() == candies[x][y].getCandyType() &&
                    candies[x][y].getCandyType() == candies[x + 1][y].getCandyType()) {
                return true;
            }
        }
        // Check for vertical match
        if (y > 0 && y < candies_height - 1) {
            if (candies[x][y - 1].getCandyType() == candies[x][y].getCandyType() &&
                    candies[x][y].getCandyType() == candies[x][y + 1].getCandyType()) {
                return true;
            }
        }
        return false;
    }

}
