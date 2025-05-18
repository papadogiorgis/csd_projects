# multiAgents.py
# --------------
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

#PAPADAKIS GEORGIOS 4975
from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
      A reflex agent chooses an action at each choice point by examining
      its alternatives via a state evaluation function.

      The code below is provided as a guide.  You are welcome to change
      it in any way you see fit, so long as you don't touch our method
      headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {North, South, West, East, Stop}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghost.scaredTimer for ghost in newGhostStates]
        "*** YOUR CODE HERE ***"
        from util import manhattanDistance
        total_score = successorGameState.getScore() - currentGameState.getScore()

        #FOOD
        all_foods = newFood.asList()
        #finds distances to each food item from successor state
        food_distances_successor = [manhattanDistance(newPos, food) for food in all_foods]
        total_score -= 10 * len(all_foods)  #penalty for leaving food behind
        food_available_successor = len(newFood.asList()) #successor state's available food items
        food_available_currently = len(currentGameState.getFood().asList()) #current state's available food items
        if food_available_successor < food_available_currently:
          total_score += 200  # Reward for eating food
        if food_distances_successor:
          total_score += 500 / (min(food_distances_successor) + 1)  # Reward for getting closer to food

        #GHOSTS
        ghost_distances_successor = [manhattanDistance(newPos, ghost.getPosition()) for ghost in newGhostStates]
        scared_ghosts = sum(newScaredTimes) #number of scared ghosts
        if scared_ghosts > 0:  #if ghosts are scared
          total_score += 200 / (min(ghost_distances_successor) + 1) #reward getting closer to them
        else:  #if ghosts are not scared
          if min(ghost_distances_successor) < 2:  # Avoid dangerous proximity
            total_score -= 1000
          else:#penalty for getting closer to them
            total_score -= 200 / (min(ghost_distances_successor) + 1)

        #POWER PALLETS
        if newPos in currentGameState.getCapsules():
          total_score += 150
        power_pallets_available_successor = len(successorGameState.getCapsules())
        power_pallets_available_currently = len(currentGameState.getCapsules())
        if power_pallets_available_currently > power_pallets_available_successor:
          total_score += 300  #reward for eating a power pellet

        #STOP
        if action == Directions.STOP:
          total_score -= 10#penalty fpr stopping

        return total_score

def scoreEvaluationFunction(currentGameState):
    """
      This default evaluation function just returns the score of the state.
      The score is the same one displayed in the Pacman GUI.

      This evaluation function is meant for use with adversarial search agents
      (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
      This class provides some common elements to all of your
      multi-agent searchers.  Any methods defined here will be available
      to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

      You *do not* need to make any changes here, but you can if you want to
      add functionality to all your adversarial search agents.  Please do not
      remove anything, however.

      Note: this is an abstract class: one that should not be instantiated.  It's
      only partially specified, and designed to be extended.  Agent (game.py)
      is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
      Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action from the current gameState using self.depth
          and self.evaluationFunction.

          Here are some method calls that might be useful when implementing minimax.

          gameState.getLegalActions(agentIndex):
            Returns a list of legal actions for an agent
            agentIndex=0 means Pacman, ghosts are >= 1

          gameState.generateSuccessor(agentIndex, action):
            Returns the successor game state after an agent takes an action

          gameState.getNumAgents():
            Returns the total number of agents in the game
        """
        "*** YOUR CODE HERE ***"
        def min_level(gameState, depth, agent_index):#for ghosts, agent_index>0
          value = float('+inf')
          if gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
          for action in gameState.getLegalActions(agent_index):
            successor = gameState.generateSuccessor(agent_index, action)
            if agent_index == gameState.getNumAgents()-1 :
              value = min(value, max_level(successor, depth))
            else:
              value = min(value, min_level(successor, depth, agent_index+1))
          return value

        def max_level(gameState, depth):#for pac-man, agent_index==0
          depth_current = depth +1
          if depth_current==self.depth or gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
          value = float('-inf')
          for action in gameState.getLegalActions(0):#pac-man is agent 0
            successor = gameState.generateSuccessor(0,action)
            value = max(value, min_level(successor, depth_current, 1)) #call min_level for ghosts
          return value

        optimal_action = None
        best_score = float('-inf')
        for action in gameState.getLegalActions(0): #pac-man's actions
          successor = gameState.generateSuccessor(0,action)
          score = min_level(successor, 0, 1) #call min_level for successors of the root
          if score > best_score:
            best_score = score
            optimal_action = action
        return optimal_action

class AlphaBetaAgent(MultiAgentSearchAgent):
    """
      Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        def min_level(gameState, depth, agent_index, alpha, beta):#for ghosts, agent_index>0
          value = float('+inf')
          if gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
          for action in gameState.getLegalActions(agent_index):
            successor = gameState.generateSuccessor(agent_index, action)
            if agent_index == gameState.getNumAgents()-1 :
              value = min(value, max_level(successor, depth, alpha, beta))
            else:
              value = min(value, min_level(successor, depth, agent_index+1, alpha, beta))
            if alpha > value:
              return value#prune
            beta = min(beta, value)
          return value

        def max_level(gameState, depth, alpha, beta):#for pac-man, agent_index==0
          depth_current = depth +1
          if depth_current==self.depth or gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
          value = float('-inf')
          for action in gameState.getLegalActions(0):#pac-man is agent 0
            successor = gameState.generateSuccessor(0,action)
            value = max(value, min_level(successor, depth_current, 1, alpha, beta)) #call min_level for ghosts
            if beta < value:
              return value#prune
            alpha = max(alpha, value)
          return value

        optimal_action = None
        alpha = float('-inf')
        beta = float('+inf')
        best_score = float('-inf')
        for action in gameState.getLegalActions(0): #pac-man's actions
          successor = gameState.generateSuccessor(0,action)
          score = min_level(successor, 0, 1, alpha, beta) #call min_level for successors of the root
          if score > best_score:
            best_score = score
            optimal_action = action
          alpha = max(alpha, best_score)
        return optimal_action

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
          Returns the expectimax action using self.depth and self.evaluationFunction

          All ghosts should be modeled as choosing uniformly at random from their
          legal moves.
        """
        "*** YOUR CODE HERE ***"
        def expect_level(gameState, depth, agent_index):#for ghosts, agent_index>0
          value = float('+inf')
          if gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
          legal_actions = gameState.getLegalActions(agent_index)
          number_of_actions = len(legal_actions)
          total_expected_value = 0
          for action in legal_actions:
            successor = gameState.generateSuccessor(agent_index, action)
            if agent_index == gameState.getNumAgents()-1 :
              value = max_level(successor, depth)
            else:
              value = expect_level(successor, depth, agent_index+1)
            total_expected_value = total_expected_value + value
          if number_of_actions == 0:
            return 0
          else:
            return float(total_expected_value) / float(number_of_actions)

        def max_level(gameState, depth):#for pac-man, agent_index==0
          depth_current = depth +1
          if depth_current==self.depth or gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
          value = float('-inf')
          for action in gameState.getLegalActions(0):#pac-man is agent 0
            successor = gameState.generateSuccessor(0,action)
            value = max(value, expect_level(successor, depth_current, 1))
          return value

        optimal_action = None
        best_score = float('-inf')
        for action in gameState.getLegalActions(0): #pac-man's actions
          successor = gameState.generateSuccessor(0,action)
          score = expect_level(successor, 0, 1) #call expect_level for successors of the root
          if score > best_score:
            best_score = score
            optimal_action = action
        return optimal_action

def betterEvaluationFunction(currentGameState):
    """
      Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
      evaluation function (question 5).

      DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    newPos = currentGameState.getPacmanPosition()
    newFood = currentGameState.getFood()
    newGhostStates = currentGameState.getGhostStates()
    newPowerPallets = currentGameState.getCapsules()
    newScore = currentGameState.getScore()

    #manhattan distance to the nearest food item
    food_list = newFood.asList()
    if food_list:
      food_distance = min([manhattanDistance(newPos, food) for food in food_list])
    else:
      food_distance = 0
    penalty_for_left_food = len(food_list)*10 #if pac-man leaves food behind he gets a penalty
    penalty_for_left_capsules = len(newPowerPallets) * 30#if pac-man leaves power pallets behind he gets an even bigger penalty
    
    #manhattan distance to ghosts
    penalty_for_ghosts = 0
    for ghost in newGhostStates:
      ghost_pos = ghost.getPosition()
      if ghost.scaredTimer > 0:#if ghost is scared, encourage pac-man to get close to it
        penalty_for_ghosts -= 200 / (manhattanDistance(newPos, ghost_pos) +1)
      else:#if the ghost isnt scared and pac-man gets close to it, pac-man gets a penalty
        penalty_for_ghosts += 200 / (manhattanDistance(newPos, ghost_pos) +1)
    
    total_evaluation = newScore - ((2*food_distance) + penalty_for_left_food + penalty_for_left_capsules + penalty_for_ghosts)
    return total_evaluation

# Abbreviation
better = betterEvaluationFunction


  