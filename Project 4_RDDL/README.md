Project 4: RDDL and Monte-Carlo Tree Search (MCTS)
---

RDDL has been covered during the lecture, but you will find the resources below useful for completing this project.

#### RDDL Resources:

* [RDDL Tutorial Website](https://ataitler.github.io/IPPC2023/pyrddlgym_rddl_tutorial.html) -- a step-by-step guide to building the Wildfire domain.
* [RDDL Language Guide](https://pyrddlgym.readthedocs.io/en/latest/rddl.html#)
* [The original RDDL repository](https://github.com/ssanner/rddlsim)

There will be no code review for this project.
The mark allocation for this project is as follows:

- Question 1: 2 pts
- Question 2: 4 pts
- Total: 6 pts

## [2 pts] Question 1. Design an Ambulance Domain in RDDL

#### Problem Description: the Ambulance Domain

There is a network of roads and locations where some locations are hospitals. 
For simplicity, assume a simple (n x m) grid of locations. 
Other than where a hospital is, emergency calls randomly occur at each location specified by its x and y position. 
Each location can have one emergency call at the most at a given time step. 
When there is no active call at a location, the arrival of a new emergency call 
follows the Bernoulli distribution with some location-dependent probability. 

One or more ambulances move along this road network. 
When an empty ambulance arrives at a location with an active emergency call, 
the ambulance can *choose* to pick up the patient (assume that a single ambulance can carry only one patient at a time).
Once an ambulance carrying a patient gets to a hospital, 
the patient is automatically dropped and 
the ambulance is freed up for taking in another patient from other locations.


Ambulances can move to any of four directions on the grid network 
(i.e., east, west, north, and south), which comprises four out of five available actions. 
The last action is to pick up a patient (regardless of whether there is one).

There is a large negative reward for unanswered emergency calls in the network 
and a small negative reward for patients on ambulances who have not made it to a hospital yet.


#### Your Task:

Complete the RDDL file *[files/mie369_project4/rddl/ambulance_mdp.rddl](files/mie369_project4/rddl/ambulance_mdp.rddl)*, 
which encodes the above described ambulance domain of any size (any number of hospitals, locations, and ambulances).

The non-fluents, state-fluents, and action-fluents are already defined for you.
**DO NOT modify these variables.**
Use this as a template (don't modify names or types) and complete the following:

<ol start="1">
  <li> The cpfs block</li>
  <br>
  In this part, you should complete the next state transitions 
  depending on the current state and action. 
</ol>

<ol start="2">
  <li> The reward function</li>
  <br>
  In this part (located just below the cpfs block), 
  you should specify the reward function given the current state.  
</ol>

<ol start="3">
  <li> The state-action-constraints block</li>
  <br>
  In this part (located below the reward definition), 
  add any action preconditions as necessary.  
</ol>

Some things to keep in mind when working on the ambulance domain:
- When more than one ambulance with no patients arrive at the same location with an emergency call,
  only a single ambulance should respond to the call. (Use the  _state-action-constraints_  block to enforce this condition.)
- An ambulance stays at the same location if it chooses to move towards outside of the given map of locations.
  For example, if an ambulance is at the far-right edge of the map, it will not move if it chooses the *moveEast* action.
- An ambulance can take only one action at a time. This should be enforced in the  *state-action-constraints* block as well.



#### Grading

This question will not be auto-graded. 
We will go through the rddl file to check if the domain is encoded correctly. 
We may use the animation to double-check your encoding.

#### Animating the simulation

Assuming your implementation of the ambulance_mdp.rddl file is correct, 
you can run a simulation using one of the example instance files provided in [files/mie369_project4/rddl/ambulance_inst.rddl](files/mie369_project4/rddl/ambulance_inst.rddl).
You can also define your own specific instances of the domain.
(Note that when you define the *location* non-fluents, 
you should **strictly** follow the naming convention used in the examples. 
That is, (1, 1) in the grid will be *(x1, y1)*, while (2, 1) will be *(x2, y1)*, and so on. )

To run the simulation and see the visualization, run **[rddl.sim.Simulator](src/rddl/sim/Simulator.java)** on IntelliJ with the following program arguments: 

```
[path_to_directory_with_rddl_files] [policy] [instance_name] [display_class] [random_seed_for_simulation] [random_seed_for_policy]
```
For example, to run the simulation of the 3 x 3 ambulance instance with the random policy:

```
files/mie369_project4/rddl rddl.policy.RandomBoolPolicy ambulance_inst_3x3 rddl.viz.AmbulanceDisplay 0 0
```
To run the simulation of the 3 x 3 ambulance instance with the handout MCTS solver (Note: MCTS only work with the ambulance domain of size up to 3 x 3.
This size limitation is due to computational inefficiency in the compilation step of the RDDL instance):

```
files/mie369_project4/rddl rddl.solver.mdp.mcts.MCTS ambulance_inst_3x3 rddl.viz.AmbulanceDisplay 0 0
```

## [4 pts] Question 2. Improve and compare with the handout MCTS solver implementation

In this part of the project, you will make some enhancements to the provided MCTS solver and compare the performances of the solvers on the **Elevators domain**. 
The RDDL encoding as well as a description of the domain can be found in [files/mie369_project4/rddl/elevators_mdp.rddl](files/mie369_project4/rddl/elevators_mdp.rddl).

#### Running simulations with the MCTS solver

To run the handout MCTS code for the elevator domain, run rddl.sim.Simulator with the following program arguments on IntelliJ:

```
files/mie369_project4/rddl rddl.solver.mdp.mcts.MCTS elevators_inst_mdp__5 rddl.viz.ElevatorDisplay
```
replace `rddl.solver.mdp.mcts.MCTS` with your implemented solvers (`rddl.solver.mdp.mcts.MySelectionMCTS`, `rddl.solver.mdp.mcts.MyBackpropMCTS`) as you work through Q2.


#### Your Tasks:

You will enhance the MCTS solver.
You can copy-paste the code from MCTS.java and update parts of it to implement your chosen strategy.
Alternatively, you can choose to simply override the method specified in each question below.
Either way, you need to make sure that we can use your implemented classes as the policy for running a simulation.

**There won't be a code review for this project.**
Therefore, for questions (a), (b) below, you will also need to edit [files/mie369_project4/my_mcts.txt](files/mie369_project4/my_mcts.txt), 
where you will explain the rationale behind the enhancements you make and cite any papers you have consulted.

Your implementations will first be autograded for correctness, then manually graded based on your write-up. 
A clear explanation of the implemented method with proper citations will get the full points. 
**Points will be deducted for mismatches between the explanation and the implementation.**


(a) **[1.5 pts]** Selection strategy

Implement your own **selection strategy** in [src/rddl/solver/mdp/mcts/MySelectionMCTS](src/rddl/solver/mdp/mcts/MySelectionMCTS.java).
You can copy-paste the code in MCTS.java and update parts of it, or override the *evaluateStateActionNode* method. 
Note that you should handle the case when (greedy = true) in the method. 


(b) **[1.5 pts]** Back-propagation strategy

Implement your own **backpropagation strategy** in [src/rddl/solver/mdp/mcts/MyBackpropMCTS](src/rddl/solver/mdp/mcts/MyBackpropMCTS.java).
You can copy-paste the code in MCTS.java and update parts of it, or override the *backPropagate* method.

(c) **[1 pts]** Comparison with Handout Implementation

Compare your implemented strategies to the given implemented strategy on the following instance:
[files/mie369_project4/rddl/elevators_inst_mdp__5.rddl](files/mie369_project4/rddl/elevators_inst_mdp__5.rddl).
In this Elevator instance, there are 2 elevators moving up and down in a 5-floor building.
The horizon of this instance is set to 40, and the total time allocated to action selections
in each simulation run is 10 seconds (the actual total running time will be longer).

Run the simulator 10 times for each of the three strategies (handout MCTS and your MCTS enhancements from part a,b), record the final cumulative reward for each run as well as the average across 10 runs in [files/mie369_project4/my_mcts.txt](files/mie369_project4/my_mcts.txt).
The results should be cleanly formatted when viewing on GitHub (align your columns and numbers). Briefly comment on which implementation had the best performance and why you think that is the case.

**Note**: the MCTS algorithm has the anytime property, in other words, the longer it runs,
it generally returns a better plan. Therefore, for a fair evaluation, it is critical that the same
amount of total planning time is spent for each planner.
This is enforced by checking the total cumulative time spent on planning is capped from above by 10 seconds (or 10^10 nanoseconds).
**Your `MySelectionMCTS` and `MyBackpropMCTS` class should implement the *getTimePerAction* method which returns how much time in nanoseconds
is allocated on planning for a single time step.** The sum of the return values of this method over 40 horizon should be below
10^10 nanoseconds (This is already included in the handout MCTS class).

To run this Elevators instance with your MCTS implementation, 
use the following program arguments on IntelliJ:

```
files/mie369_project4/rddl rddl.solver.mdp.mcts.MySelectionMCTS elevators_inst_mdp__5 rddl.viz.ElevatorDisplay
```

```
files/mie369_project4/rddl rddl.solver.mdp.mcts.MyBackpropMCTS elevators_inst_mdp__5 rddl.viz.ElevatorDisplay
```

You can also play with the [elevators_inst_mdp__3.rddl](files/mie369_project4/rddl/elevators_inst_mdp__3.rddl) file
which should run faster.



**Example resources you can consult:**

- [A Survey of Monte Carlo Tree Search Methods](http://www.incompleteideas.net/609%20dropbox/other%20readings%20and%20resources/MCTS-survey.pdf)
- [Using Monte Carlo Tree Search to Solve Planning Problems in Transportation Domains](http://www.ms.mff.cuni.cz/~truno7am/clanky/download/2013micai/paper.pdf)
- [Anytime Optimal MDP Planning with Trial-based Heuristic Tree Search](https://ai.dmi.unibas.ch/papers/keller-dissertation.pdf)
- [Trial-based Heuristic Tree Search for Finite Horizon MDPs](https://gki.informatik.uni-freiburg.de/papers/keller-helmert-icaps2013.pdf)


# Project 4 Checklist:
- Q1:
  - [ ] `files/mie369_project4/rddl/ambulance_mdp.rddl`
- Q2:
  - [ ] `files/mie369_project4/my_mcts.txt`
  - [ ] `src/rddl/solver/mdp/mcts/MySelectionMCTS.java`
  - [ ] `src/rddl/solver/mdp/mcts/MyBackpropMCTS.java`
