package backend.academy.maze.utils;

public class DisjointSetUnion {
    private final int[] parent;
    private final int[] count;

    public DisjointSetUnion(int size) {
        parent = new int[size];
        count = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            count[i] = 1;
        }
    }

    private int getLeader(int x) {
        if (parent[x] == x) {
            return x;
        }
        parent[x] = getLeader(parent[x]);
        return parent[x];
    }

    public boolean union(int u, int v) {
        if (u < 0 || u >= parent.length || v < 0 || v >= parent.length) {
            throw new IndexOutOfBoundsException("Vertex out of bounds");
        }

        int leaderU = getLeader(u);
        int leaderV = getLeader(v);
        if (parent[leaderU] == parent[leaderV]) {
            return false;
        }

        if (count[leaderU] > count[leaderV]) {
            parent[leaderV] = leaderU;
            count[leaderU] += count[leaderV];
        } else {
            parent[leaderU] = leaderV;
            count[leaderV] += count[leaderU];
        }
        return true;
    }

    public int size() {
        return parent.length;
    }
}
