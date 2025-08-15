package plan;

import map.MapEdge;
import map.MapNode;
import java.util.*;
/**
 * A class defining planning using A* search
 */
public class AStarPlanner extends Planner {
    /**
     * heuristics used for A*
     */
    Heuristic heuristic;
    /**
     * cost function used for A*
     */
    CostFunction costFunction;

    /**
     * Initializer
     *
     * @param heuristic a heuristic object
     * @param costFunction    cost function option
     */
    public AStarPlanner(Heuristic heuristic, CostFunction costFunction) {
        super();
        this.heuristic = heuristic;
        this.costFunction = costFunction;
    }

    /**
     * Runs A* search
     *
     * @param startNode the start node
     * @param goalNode  the goal node
     * @return a list of MapNode objects
     */
    @Override
    public PlanResult plan(MapNode startNode, MapNode goalNode) {
        HashMap<MapNode, Double> cost = new HashMap<>();
        HashMap<MapNode, Double> heu = new HashMap<>();
        HashMap<MapNode, MapNode> parents = new HashMap<>();
        PriorityQueue<MapNode> queue = new PriorityQueue<>(Comparator.comparingDouble(n -> heu.get(n)));
        Set<MapNode> expandedNodes = new HashSet<>();
        cost.put(startNode, 0.0);
        heu.put(startNode, heuristic.getHeuristics(startNode, goalNode));
        queue.add(startNode);

        while (!queue.isEmpty()) {
            MapNode node = queue.poll();
            expandedNodes.add(node);
            if (node.id == goalNode.id) {
                return new PlanResult(expandedNodes.size(), getNodeList(parents, goalNode));
            }
            for (MapEdge edge : node.edges) {
                MapNode nextNode = edge.destinationNode;
                double newCost = cost.get(node) + costFunction.getCost(edge);
                if (!cost.containsKey(nextNode) || newCost < cost.get(nextNode)) {
                    cost.put(nextNode, newCost);
                    heu.put(nextNode, newCost + heuristic.getHeuristics(nextNode, goalNode));
                    parents.put(nextNode, node);
                    queue.add(nextNode);}
            }
        }

        return new PlanResult(expandedNodes.size(), null);
    }


    /**
     * Gets the name of the planner
     *
     * @return planner name
     */
    @Override
    public String getName() {return getClass().getSimpleName();
    }
}

