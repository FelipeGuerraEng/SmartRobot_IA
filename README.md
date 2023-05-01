# SmartRobot_IA

Smart Robot is a robot whose objective is to find 2 items that are hidden in a 10x10 grid. In the environment, there are 2 ships that the robot can use to facilitate the exploration of the space, but there is also a hostile element for the agent - squares with oil that affect the state of the robot.

The application allows for the following actions:

- Entering data of a specific world through a text file following the established conventions.
- Graphically displaying the agent's world in its initial state, as read from the file.
- Selecting the type of search algorithm to apply: "Uninformed" or "Informed".
- If "Uninformed" search is selected, the options are "Breadth-first", "Uniform Cost", and "Depth-first avoiding cycles".
- If "Informed" search is selected, the options are "Greedy" and "A*".
- Once an algorithm is applied, the set of movements made by the agent is shown in the graphical interface.
- After applying an algorithm, a report is displayed with the number of expanded nodes, tree depth, and computation time."
