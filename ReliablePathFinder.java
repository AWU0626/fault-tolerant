import java.util.Collections;
import java.util.List;

import edu.upenn.cis573.graphs.*;

public class ReliablePathFinder extends PathFinder {

    public ReliablePathFinder(Graph g) {
        super(g);
    }

    /*
     * Implement this method using a Recovery Block and Retry Block as described in
     * the assignment specification.
     */
    public List<Integer> findPath(int src, int dest) throws PathNotFoundException {

        List<Integer> path;

        // Try bfs(dest, src) and reverse if valid
        path = this.bfs(dest, src);
        if (isValidPath(dest, src, path)) {
            Collections.reverse(path);
            return path;
        }

        // Try dfs(dest, src) and reverse if valid
        path = this.dfs(dest, src);
        if (isValidPath(dest, src, path)) {
            Collections.reverse(path);
            return path;
        }

        // Try bfs(src, dest)
        path = this.bfs(src, dest);
        if (isValidPath(src, dest, path)) {
            return path;
        }

        // Try dfs(src, dest)
        path = this.dfs(src, dest);
        if (isValidPath(src, dest, path)) {
            return path;
        }

        throw new PathNotFoundException();

    }

    /*
     * Implement this acceptance test as described in the assignment specification.
     */
    public boolean isValidPath(int src, int dest, List<Integer> path) {
        if (path.isEmpty()) {
            if (src == dest) {
                return true;
            }

            return false;
        }

        int pathSize = path.size();
        if (path.get(0) != src || path.get(pathSize - 1) != dest) {
            return false;
        }

        for (int i = 0; i < pathSize - 1; i++) {
            int currNode = path.get(i);
            int nextNode = path.get(i + 1);
            boolean hasNextNode = false;

            Iterable<Integer> neighbors = this.graph.adj(currNode);

            for (int neighbor : neighbors) {
                if (neighbor == nextNode) {
                    hasNextNode = true;
                    break;
                }
            }

            if (!hasNextNode) {
                return false;
            }
        }

        return true;

    }

}
