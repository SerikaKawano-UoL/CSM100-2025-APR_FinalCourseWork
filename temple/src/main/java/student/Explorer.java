package student;

import game.ExplorationState;
import game.NodeStatus;

import java.util.*;

/**
 * A basic explorer implementation that always finds the Orb.
 * Uses DFS and properly backtracks using a path stack.
 */
public class Explorer {

    private final Set<Long> visited = new HashSet<>();
    private final Deque<Long> path = new ArrayDeque<>();

    public void explore(ExplorationState state) {
        dfs(state);
    }

    private boolean dfs(ExplorationState state) {
        long current = state.getCurrentLocation();
        visited.add(current);

        // Push current location to the path stack
        path.push(current);

        if (state.getDistanceToTarget() == 0) {
            return true; // Found the Orb
        }

        // Sort neighbours to explore the closer nodes first (optional but cleaner)
        List<NodeStatus> neighbours = new ArrayList<>(state.getNeighbours());
        neighbours.sort(Comparator.comparingInt(NodeStatus::distanceToTarget));

        for (NodeStatus neighbour : neighbours) {
            long next = neighbour.nodeID();
            if (!visited.contains(next)) {
                state.moveTo(next);
                if (dfs(state)) {
                    return true; // Orb found in deeper recursion
                }
                // Backtrack
                state.moveTo(path.pop()); // Return to previous location
                path.push(current); // Re-push current location
            }
        }

        // Done exploring this branch
        path.pop();
        return false;
    }

    public void escape(game.GameState state) {
        // Escape phase not needed for this individual coursework
    }
}