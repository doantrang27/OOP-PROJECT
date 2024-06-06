// package stage_3.src.Level;

// import java.awt.Point;
// import java.util.ArrayList;
// import java.util.List;

// import stage_3.src.Candies;

// public class LevelTest {
// // public IntermediateLevel() {
// // this.obstacleProbability = 0.0;
// // }
// private Candies[][] candies; // Declare the candies array
// public int candies_width; // Declare the candies_width variable
// private int candies_height; // Declare the candies_height variable

// @Override
// protected boolean isAdjacent(Point obstacle, Point curPos) {
// return Math.abs(obstacle.x - curPos.x) + Math.abs(obstacle.y - curPos.y) ==
// 1;
// }

// @Override
// public void removeObstaclesNear(Point position, List<Point> obstacles) {
// List<Point> toRemove = new ArrayList<>();
// for (Point obstacle : obstacles) {
// if (isAdjacent(obstacle, position)) {
// toRemove.add(obstacle);
// }
// if (isNearMatch(obstacle)) { // Pass the obstacles list as an argument
// toRemove.add(obstacle);
// }
// }
// obstacles.removeAll(toRemove);
// }

// @Override
// // 0.1 = this.obstacleProbability, if we only change the EasyLevel method
// public void placeObstaclesRandomly(int width, int height, List<Point>
// obstacles) {
// for (int i = 0; i < width; i++) {
// for (int j = 0; j < height; j++) {
// if (Math.random() < 0.1) { // 10% chance to place an obstacle
// obstacles.add(new Point(i, j));
// }
// }
// }
// }

// /**
// * Checks if there is a near match to the obstacle at the given position.
// * A near match is defined as a match in any of the surrounding cells of the
// * obstacle.
// *
// * @param obstaclePosition the position of the obstacle
// * @return true if there is a near match, false otherwise
// */
// protected boolean isNearMatch(Point obstaclePosition) {
// // Check the surrounding cells of the obstacle for matches
// int x = obstaclePosition.x;
// int y = obstaclePosition.y;

// // Check horizontally and vertically around the obstacle
// for (int i = -1; i <= 1; i++) {
// for (int j = -1; j <= 1; j++) {
// if (i == 0 && j == 0)
// continue; // Skip the obstacle's own position
// if (isMatchAt(x + i, y + j)) {
// return true;
// }
// }
// }
// return false;
// }

// /**
// * Checks if there is a match at the specified coordinates.
// *
// * @param x The x-coordinate of the candy.
// * @param y The y-coordinate of the candy.
// * @return true if there is a match at the specified coordinates, false
// * otherwise.
// */
// private boolean isMatchAt(int x, int y) {
// // Check for horizontal match
// if (x > 0 && x < candies_width - 1) {
// if (candies[x - 1][y].getCandyType() == candies[x][y].getCandyType() &&
// candies[x][y].getCandyType() == candies[x + 1][y].getCandyType()) {
// return true;
// }
// }
// // Check for vertical match
// if (y > 0 && y < candies_height - 1) {
// if (candies[x][y - 1].getCandyType() == candies[x][y].getCandyType() &&
// candies[x][y].getCandyType() == candies[x][y + 1].getCandyType()) {
// return true;
// }
// }
// return false;
// }
// }
