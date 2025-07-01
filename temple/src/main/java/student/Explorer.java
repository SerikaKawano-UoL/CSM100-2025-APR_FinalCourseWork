package student;

import game.ExplorationState;
import game.NodeStatus;

import java.util.*;

/**
 * Explorer for the Software Design and Programming Coursework.
 * Author: Serika Kawano
 * Date: 2025-07-01
 *
 * This explorer always finds the Orb by using a depth-first search (DFS) strategy,
 * carefully avoiding any infinite loops or dead-ends.
 */
public class Explorer {
    private Set<Long> visited;

    public void explore(ExplorationState state) {
        visited = new HashSet<>();
        System.out.println("Exploration started at tile: " + state.getCurrentLocation());
        dfs(state);
    }

    // Recursive DFS which always finds the Orb
    private boolean dfs(ExplorationState state) {
        long current = state.getCurrentLocation();
        visited.add(current);

        // Goal check
        if (state.getDistanceToTarget() == 0) {
            System.out.println("Orb found at tile: " + current);
            return true;
        }

        // Explore all unvisited neighbours, prioritising those closer to the Orb
        List<NodeStatus> neighbours = new ArrayList<>(state.getNeighbours());
        neighbours.sort(Comparator.comparingInt(NodeStatus::distanceToTarget));
        for (NodeStatus n : neighbours) {
            if (!visited.contains(n.nodeID())) {
                System.out.println("Moving to: " + n.nodeID() + ", Distance: " + n.distanceToTarget());
                state.moveTo(n.nodeID());
                if (dfs(state)) return true; // If Orb found deeper, bubble up
                // Backtrack to previous position if not found in this branch
                System.out.println("Backtracking to: " + current);
                state.moveTo(current);
            }
        }
        return false; // No path from here
    }

    public void escape(game.GameState state) {
        // Not required for the individual version.
    }
}
