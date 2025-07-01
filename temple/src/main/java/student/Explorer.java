package student;

import game.ExplorationState;
import game.NodeStatus;

import java.util.*;

/**
 * Explorer that always reaches the Orb using depth-first search (DFS) with backtracking.
 * Only moves to visible adjacent nodes as allowed by the interface.
 * All comments use British English spelling.
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
