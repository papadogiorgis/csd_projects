# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util
from util import Stack
from util import Queue
from util import PriorityQueue


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]
#-------------------------------------------------------------------------
def depthFirstSearch(problem):

    dfs_stack = Stack() #stack that stores (state, path_to_state) pairs

    initial_state = problem.getStartState() #get the start state of the problem
    dfs_stack.push((initial_state, [])) #init stack with the start state and an empty stack

    visited = set() #set to keep visited states and prevent re-exploration

    #dfs loop: explore nodes until we find the goal or stack is empty
    while not dfs_stack.isEmpty():
        popped = dfs_stack.pop()
        current = popped[0]
        path_to_current = popped[1]

        # If the current state is the goal, return the path
        if problem.isGoalState(current):
            return path_to_current

        #if the state has not been visited before mark it as visited
        if current not in visited:
            visited.add(current)

            #expand successors for each current state
            for successor, action, _ in (problem.getSuccessors(current)):
                #create new path that includes the action to the successor
                path_to_successor = path_to_current + [action]
                #push successor state and its path onto the stack
                dfs_stack.push((successor, path_to_successor))

    #if no path to a goal is found return empty stack
    return []
#-------------------------------------------------------------------------
def breadthFirstSearch(problem):

    bfs_queue = Queue() #queue that stores (state, path_to_state) pairs
    
    initial_state = problem.getStartState() #get the start state of the problem
    bfs_queue.push((initial_state, []))  #init queue with the start state and an empty stack
    
    visited = set() #set to keep visited states and prevent re-exploration

    #bfs loop: explore nodes until we find the goal or queue is empty
    while not bfs_queue.isEmpty():
        dequeued = bfs_queue.pop()
        current = dequeued[0]
        path_to_current = dequeued[1]
        
        # If the current state is the goal, return the path
        if problem.isGoalState(current):
            return path_to_current
        
        #if the state has not been visited before mark it as visited
        if current not in visited:
            visited.add(current)
            
        #expand successors for each current state
            for successor, action, _ in (problem.getSuccessors(current)):
                if successor not in visited:
                    #create new path that includes the action to the successor
                    path_to_successor = path_to_current + [action]
                    #push successor state and its path onto the queue
                    bfs_queue.push((successor, path_to_successor))

    #if no path to a goal is found return empty stack
    return []
#-------------------------------------------------------------------------
def uniformCostSearch(problem):

    pq = PriorityQueue() #priority queue that stores (state, path, g(n))
    #Priority is f(n)=g(n)
    
    initial_state = problem.getStartState() #start state with an empty path and the cost g(n) = 0
    pq.push((initial_state, [], 0), 0) #init priority queue
    
    visited = {} #dictionary that stores the lowest cost
    
    #ucs loop: explore nodes until we find the goal or queue is empty
    while not pq.isEmpty():
        popped = pq.pop()
        current = popped[0]
        path_to_current = popped[1]
        g_n = popped[2]
        
        # If the current state is the goal, return the path
        if problem.isGoalState(current):
            return path_to_current
        
        # If the state has not been visited or if we found a cheaper way to reach it
        if current not in visited or g_n < visited[current]:
            visited[current] = g_n  #update cost
            
            # For each successor, calculate the new g(n)
            for successor, action, cost in problem.getSuccessors(current):
                new_g_n = g_n + cost
                #create new path that includes the action to the successor
                path_to_successor = path_to_current + [action]
                
                #push successor, the path to it and the new cost to pq. set new cost as priority
                pq.push((successor, path_to_successor, new_g_n), new_g_n)
    
    #if no path to a goal is found return empty stack
    return []
#-------------------------------------------------------------------------
def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0
#-------------------------------------------------------------------------
def aStarSearch(problem, heuristic=nullHeuristic):
    
    pq = PriorityQueue() #priority queue that stores (state, path, g(n))
    #Priority is f(n)=g(n)
    
    initial_state = problem.getStartState() #start state with an empty path and the cost g(n) = 0
    pq.push((initial_state, [], 0), heuristic(initial_state, problem))  #init prioriy queue
    
    visited = {} #dictionary that stores the lowest cost
    
    #astar loop: explore nodes until we find the goal or queue is empty
    while not pq.isEmpty():
        popped = pq.pop()
        current = popped[0]
        path_to_current = popped[1]
        g_n = popped[2]
        
        # Check if the current state is the goal state
        if problem.isGoalState(current):
            return path_to_current
        
        # Calculate the total cost f(n) = g(n) + h(n) for this node
        f_n = g_n + heuristic(current, problem)
        
        #process the state if it has not been visited or if it has lower f_n than the visited
        if current not in visited or f_n < visited[current]:
            visited[current] = f_n  #update f_n
            
            # For each successor, calculate the new g(n) and f(n)
            for successor, action, cost in problem.getSuccessors(current):
                new_g_n = g_n + cost
                #create new path that includes the action to the successor
                new_path = path_to_current + [action]
                
                # Compute f(n) = g(n) + h(n) for this successor
                f_n_successor = new_g_n + heuristic(successor, problem)
                
                #push the successor if it has not been visited or if it has lower f_n than the visited
                if successor not in visited or f_n_successor < visited[successor]:
                    pq.push((successor, new_path, new_g_n), f_n_successor)
    
    #if no path to a goal is found return empty stack
    return []
#-------------------------------------------------------------------------
# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
