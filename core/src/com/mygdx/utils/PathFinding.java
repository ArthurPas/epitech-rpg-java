package com.mygdx.utils;

import com.mygdx.interfaces.Tile;
import com.mygdx.game.room.Room;

import java.util.*;

public class PathFinding {

    public static List<Tile> findPathOptimized(Room room, Tile start, Tile goal) {
        Set<Tile> closedSet = new HashSet<>();
        Set<Tile> openSet = new HashSet<>();
        openSet.add(start);

        Map<Tile, Tile> cameFrom = new HashMap<>();

        Map<Tile, Double> gScore = new HashMap<>();
        gScore.put(start, 0.0);

        Map<Tile, Double> fScore = new HashMap<>();
        fScore.put(start, heuristicCostEstimate(start, goal));

        while (!openSet.isEmpty()) {
            Tile current = getLowestFScore(openSet, fScore);
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);

            for (Tile neighbor : room.getNeighbors(current,1)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                double currentGScore = gScore.containsKey(current) ? gScore.get(current) : Double.MAX_VALUE;
                double tentativeGScore = currentGScore + distanceBetween(current, neighbor);

                if (!openSet.contains(neighbor) || tentativeGScore < (gScore.containsKey(neighbor) ? gScore.get(neighbor) : Double.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + heuristicCostEstimate(neighbor, goal));

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; // No path found
    }

    private static Tile getLowestFScore(Set<Tile> openSet, Map<Tile, Double> fScore) {
        Tile minTile = null;
        double minScore = Double.MAX_VALUE;

        for (Tile tile : openSet) {
            double score = fScore.get(tile);
            if (score < minScore) {
                minScore = score;
                minTile = tile;
            }
        }

        return minTile;
    }

    private static List<Tile> reconstructPath(Map<Tile, Tile> cameFrom, Tile current) {
        List<Tile> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private static double heuristicCostEstimate(Tile start, Tile goal) {
        // You can use different heuristics depending on your specific requirements
        return distanceBetween(start, goal);
    }

    private static double distanceBetween(Tile tile1, Tile tile2) {
        // Use whatever distance calculation is appropriate for your Tile class
        return Math.sqrt(Math.pow(tile1.getX() - tile2.getX(), 2) + Math.pow(tile1.getY() - tile2.getY(), 2));
    }
    public static List<Tile> findAPath(Room room, Tile start, Tile goal) {
        List<Tile> path = new ArrayList<>();
        Stack<Tile> stack = new Stack<>();
        Random random = new Random();

        stack.push(start);

        while (!stack.isEmpty()) {
            Tile current = stack.pop();
            path.add(current);

            if (current.equals(goal)) {
                // Goal reached
                return path;
            }

            List<Tile> neighbors = new ArrayList<>(room.getNeighbors(current, 1));

            Collections.shuffle(neighbors, random);

            for (Tile neighbor : neighbors) {
                if (!path.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }

        // Goal not reached
        return path;
    }
}
