# Project 1: A* on OpenStreetMap for Cyclists

Original code developed by Yudong (William) Xu.

Route planning application on OpenStreetMap for cyclists.

## Questions (submission is via github, see syllabus for the due date)

* **[1 pts]** Implement an **admissable** AStarHeuristic.java (subclassing Heuristic.java) for CostFunctionAllFeatures.  Do not implement a 0 or constant heuristic -- it should be computed from properties of *both* the start and end nodes.
* **[3 pts]** Implement UniformCostPlanner.java (1 pt), GreedyBestFirstPlanner.java (1 pt), AStarPlanner.java (1 pts).  For simplicity, use the tree search (not graph search) version.  When a heuristic is needed, use your own AStarHeuristic.  All must subclass Planner.java so we can autograde them. Your implementations must take less than 60 seconds (60000 ms) to find a route (if it exists) between two points on toronto.osm.
* **[2 pts]** Code Review

**Note 1:** Do **NOT** modify abstract classes or other classes we provide -- we rely on these exact class definitions for autograding.  You may add additional helper classes and you may add as many additional methods to your solution classes as you want. When submitting, please comment out any print statements you may add for debugging as they will slow down your algorithm and may result in the autograder timing out.

**Note 2:** Make sure all of the above are viewable from your github repo URL on the web -- if you cannot see your files on the web at your github URL, we cannot see them either (i.e., remember to add new files to your repo when necessary, e.g., using "git add FILENAME").




## How to run:

Run *main.Demo.java* to start the application.

Within the application: 
* Use left mouse button to pan.
* Use mouse scroll wheel to zoom.
* Use right mouse button to set start/destination way point, and to clear existing way points.


### Map configuration options:

Demo.java is set to run for toronto.osm which will also be used to autograde your code, it will take
a few minutes to load. For easier development, we have included a smaller snapshot of Toronto (near the U. of Toronto) in file "toronto_small.osm"

**Toy maps for debugging:**
We have also included three toy maps each containing very few nodes to help you debug your algorithm. Read [toymapsinfo.pdf](toymapsinfo.pdf) for more details.


To run for other OpenStreetMap locations in the world, 
download the area from the open street map website, put the .osm file under /data and 
change the osmFile variable in main method accordingly. 
(Note: the cyclist dataset is only relevant for toronto.)




## Data sources:
osm files can be exported from the official openStreetMap website
https://www.openstreetmap.org/export

Cycling safety data for toronto can be downloaded from https://data.torontopolice.on.ca/pages/traffic


