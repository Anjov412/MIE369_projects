package rddl.solver.mdp.mcts;

public class MyBackpropMCTS extends MCTS {

	/**
	 * You can either override the backPropagate method given below,
	 * or copy-paste the whole code from the MCTS class and make modifications as you want. 
	 * Either way, make sure that we can run simulations using this class as a policy.
	 */
	public MyBackpropMCTS(String instance_name) {
		super(instance_name);
	}

	/**
	 * Backpropagates the cumulative reward from a rollout trajectory to ancestral nodes.
	 * 
	 * @param node				the currently updated node (initially, a leaf node) 
	 * @param cumulativeRewardFromLeaf	the cumulative reward to backpropagate (initially, the cumulativeReward from simulation) 
	 */
	@Override
	public void backPropagate(TreeNode node, double cumulativeRewardFromLeaf) {
		// Base case: end of backpropagation
		if (node == null) {
			return;
		}
		int depth = 0;
		TreeNode temp = node;
		while (temp != null && temp.getParent() != null) {
			depth++;
			temp = temp.getParent();
		}

		double depthWeight = Math.pow(0.95, depth);
		node.increaseVisitCount();
		if (node instanceof StateActionNode) {
			StateActionNode saNode = (StateActionNode)node;
			double currentQ = saNode._QVal;
			int visits = saNode.getVisitCount();

			// New average = old average + (new value - old average) / count
			double newQ = currentQ + (cumulativeRewardFromLeaf * depthWeight - currentQ) / visits;
			saNode._QVal = newQ;
			if (node.getParent() instanceof DecisionNode) {
				DecisionNode parentNode = (DecisionNode)node.getParent();
				parentNode._value = newQ;
			}
		}

		// Continue backpropagation
		backPropagate(node.getParent(), cumulativeRewardFromLeaf);
	}
}
