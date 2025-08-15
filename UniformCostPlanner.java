package plan;

import map.MapEdge;
import map.MapNode;
import java.util.*;
/**
 * A class defining planning using Uniform Cost Search
 */
public class UniformCostPlanner extends Planner {
    /**
     * heuristics used for Uniform Cost Search
     */
    CostFunction costFunction;

    /**
     * Initializer
     *
     * @param costFunction a costFunction object
     */
    public UniformCostPlanner(CostFunction costFunction) {
        super();
        this.costFunction = costFunction;
    }

    /**
     * Runs Uniform Cost Search
     *
     * @param startNode the start node
     * @param goalNode  the goal node
     * @return a list of MapNode objects
     */
    @Override
    public PlanResult plan(MapNode startNode, MapNode goalNode) {
        HashMap<MapNode, Double> cost = new HashMap<>();
        HashMap<MapNode, MapNode> parents = new HashMap<>();
        PriorityQueue<MapNode> queue = new PriorityQueue<>(Comparator.comparingDouble(n -> cost.get(n)));
        Set<MapNode> expandedNodes = new HashSet<>();
        cost.put(startNode, 0.0);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            MapNode node = queue.poll(); //get the lowest cost node
            expandedNodes.add(node);
            if (node.id == goalNode.id) {
                return new PlanResult(expandedNodes.size(), getNodeList(parents, goalNode));
            }
            for (MapEdge edge : node.edges) {
                MapNode nextNode = edge.destinationNode;
                double newCost = cost.get(node) + costFunction.getCost(edge); //update the total cost by adding the cost to the new node
                if (!cost.containsKey(nextNode) || newCost < cost.get(nextNode)) { //update nodes if new nodes OR lower cost path
                    cost.put(nextNode, newCost);
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
    public String getName() {
        return getClass().getSimpleName();
    }
}
