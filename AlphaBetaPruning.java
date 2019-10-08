import java.util.ArrayList;

public class AlphaBetaPruning {

  private int moves;
  private double best;
  private GameState state;
  private int noOfNodesVisited;
  private int maxDepthReached;
  private int noOfNodesEvaluated;
  private double value;
  private boolean maxPlayer;
  private int depthStore;
  private ArrayList<Double> winning;
  private int internalNodes;
  private double branchingFactor;

  public AlphaBetaPruning() {
  }




  /**
   * This function will print out the information to the terminal,
   * as specified in the homework description.
   */
  public void printStats() {
    System.out.println("Move: "+state.getMoves().get(this.moves));
    System.out.println("Value: "+this.value);
    System.out.println("Number of Nodes Visited: "+this.noOfNodesVisited);
    System.out.println("Number of Nodes Evaluated: "+this.noOfNodesEvaluated);
    System.out.println("Max Depth Reached: "+this.maxDepthReached);
    System.out.println("Average Branching Factor: "+this.branchingFactor);
    // TODO Add your code here
  }

  /**
   * This function will start the alpha-beta search
   * @param state This is the current game state
   * @param depth This is the specified search depth
   */
  public void run(GameState state, int depth) {
    this.state = state;
    int count=0;
    for(int i=1; i<=state.getSize(); i++) {
      if(!state.getStone(i)) {
        count++;
      }
    }
    if(count%2.0==0) {
      maxPlayer = true;
    }
    else
    {
      maxPlayer = false;
    }
    this.depthStore = depth;
    this.value = alphabeta(state, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, maxPlayer);
    this.moves = (this.winning.indexOf(this.value));
    this.branchingFactor = (double)(this.noOfNodesVisited-1)/this.internalNodes;

  }

  private double maxValue(GameState state, int depth,double alpha, double beta) {
    this.maxDepthReached = Math.max(maxDepthReached, depthStore-depth);
    if(depth == 0 || state.getSuccessors().isEmpty()) {
      if(depth==depthStore-1) {
        winning.add(state.evaluate());
      }
      this.internalNodes--;
      this.noOfNodesEvaluated++;
      return state.evaluate();
    }
    double value = Double.NEGATIVE_INFINITY;
    for(GameState s: state.getSuccessors()) {
      this.noOfNodesVisited++;
      this.internalNodes++;

      value = Math.max(value, minValue(s, depth-1, alpha, beta));

      if(value >= beta) {
        if(depth==depthStore-1) {
          winning.add(value);
        }
        return value;
      }
      alpha = Math.max(alpha, value);

    }
    if(depth==depthStore-1) {
      winning.add(value);
    }
    return value;
  }

  private double minValue(GameState state, int depth, double alpha, double beta) {

    this.maxDepthReached = Math.max(maxDepthReached, depthStore-depth);

    if(depth == 0 || state.getSuccessors().isEmpty()) {
      if(depth==depthStore-1) {
        winning.add(state.evaluate());
      }
      this.noOfNodesEvaluated++;
      this.internalNodes--;
      return state.evaluate();
    }
    double value = Double.POSITIVE_INFINITY;
    for(GameState s: state.getSuccessors()) {
      this.noOfNodesVisited++;
      this.internalNodes++;

      value = Math.min(value, maxValue(s, depth-1,alpha, beta));

      if(value <= alpha) {
        if(depth==depthStore-1) {
          winning.add(value);
        }
        return value;
      }
      beta = Math.min(beta, value);
    }
    if(depth==depthStore-1) {
      winning.add(value);
    }
    return value;
  }

  /**
   * This method is used to implement alpha-beta pruning for both 2 players
   * @param state This is the current game state
   * @param depth Current depth of search
   * @param alpha Current Alpha value
   * @param beta Current Beta value
   * @param maxPlayer True if player is Max Player; Otherwise, false
   * @return int This is the number indicating score of the best next move
   */
  private double alphabeta(GameState state, int depth, double alpha, double beta, boolean maxPlayer) {
    this.winning = new ArrayList<>();
    this.noOfNodesEvaluated=0;
    this.noOfNodesVisited=1;
    this.internalNodes=1;
    this.best=0;
    this.branchingFactor=0;
    if(depth == 0 || state.getSuccessors().isEmpty()) {      //Check when depth is fully reached
      this.best = state.evaluate();
      return this.best;
    }
    if(maxPlayer) {
      this.value =  maxValue(state, depth, alpha, beta);
    }
    else {
      this.value = minValue(state, depth, alpha, beta);
    }
    return this.value;






  }

}
