package rddl.solver.mdp.mcts;

public class MySelectionMCTS extends MCTS {

	/**
	 * You can either override the evaluateStateActionNode method given below,
	 * or copy-paste the whole code from the MCTS class and make modifications as you want.
	 * Either way, make sure that we can run simulations using this class as a policy.
	 */
	public MySelectionMCTS(String instance_name) {
		super(instance_name);
	}

	/**
	 * Evaluates a StateActionNode based on some utility function.
	 * When greedy = true, do not the exploration bias.
	 */
	@Override
	public double evaluateStateActionNode(StateActionNode node, boolean greedy) {

		 //TODO: implement your chosen tree policy (note: you need to explain your rationale in /files/mie369_project4/mymcts.txt)
		if (greedy) {
			return (double)node.getVisitCount();
		}
		double avgValue = node._QVal / node.getVisitCount();
		double parentVisits = node.getParent().getVisitCount();
		double nodeVisits = node.getVisitCount();
		double variance = 0.0;
		if (!node._hmNextState2NextStateNode.isEmpty()) {
			// Calculate variance of rewards if we have multiple visits
			double sumSquaredDifferences = 0.0;
			for (DecisionNode nextNode : node._hmNextState2NextStateNode.values()) {
				sumSquaredDifferences += Math.pow(nextNode._value - avgValue, 2);
			}
			variance = sumSquaredDifferences / Math.max(1, node._hmNextState2NextStateNode.size());
		} else {
			// Default to maximum variance (0.25) when we have no data
			variance = 0.25;
		}
		variance = Math.max(0.0001, variance);
		double explorationTerm = Math.sqrt((Math.log(parentVisits) / nodeVisits) *
				Math.min(0.25, variance + Math.sqrt(2 * Math.log(parentVisits) / nodeVisits)));

		// Combine value and exploration terms
		return avgValue + _c * explorationTerm;
	}
}
