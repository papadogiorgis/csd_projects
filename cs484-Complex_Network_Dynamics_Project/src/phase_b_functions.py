# src/phase_b_functions.py

import networkx as nx
import numpy as np
import scipy.stats as stats
import matplotlib.pyplot as plt
from matplotlib.ticker import *
from pathlib import Path

#save plots in a folder
script_path = Path(__file__)
script_dir = script_path.parent
project_root = script_dir.parent
plots_dir = project_root / "plots" #plots directory
plots_dir.mkdir(exist_ok = True) #creates plots dir if it doesnt already exist
print(f"Plots will be saved to: {plots_dir}")

def calculate_pagerank(G, name):
    """
    Calculates Weighted PageRank for the network.
    PageRank simulates a random walker. Trust "flows" through
    positive edges, and negative edges (distrust) act as "blocks".
    For the purpose of "Global Reputation", we typically calculate
    PageRank on the subgrahp of positive edges.

    Args:
        G (nx.DiGraph): The graph (with 'rating' edge attribute)
        name (str): Name of rhe network
        
    Returns:
        pagerank (dict): Dictionary of {node: score}
    """
    print(f"--- Calculating Weighted PageRank for {name} ---")
    #filter for positive edges only
    positive_edges = [(u, v) for u, v, d in G.edges(data=True) if d.get('rating', 0)>0]
    G_positive = G.edge_subgraph(positive_edges).copy()
    print(f"{name} | Calculation uses {G_positive.number_of_edges()} positive edges (out of {G.number_of_edges()})")
    
    #calculate weighted pagerank, using rating as weight
    try:
        pagerank = nx.pagerank(G_positive, weight='rating', alpha=0.85)
        #sort and print the top 10 "pillars of trust"
        sorted_pr = sorted(pagerank.items(), key=lambda x: x[1], reverse=True)
        print(f"{name} | Top 10 Most Trusted Nodes (PageRank):")
        for i, (node, score) in enumerate(sorted_pr[:10]):
            print(f"    {i+1}. Node {node}: {score:.6f}")
        
        scores = list(pagerank.values())
        
        weights = np.ones_like(scores) / len(scores)
        min_score = min(scores)
        max_score = max(scores)
        #initialize figure with 2x2 subplots
        
        fig, axs = plt.subplots(2, 2, figsize=(14,10))
        fig.suptitle(f'PageRank Centrality Distribution for {name}', fontsize=16)
        
        #linear bins
        axs[0,0].hist(scores, bins=50, color='skyblue', alpha=0.7, edgecolor='black')
        axs[0,0].set_title('Linear Scale - Count')
        axs[0,0].set_xlabel('PageRank Score')
        axs[0,0].set_ylabel('Frequency (Count)')
        axs[0,0].grid(True, linestyle='--', alpha=0.5)
        
        #linear percentage
        axs[0,1].hist(scores, bins=50, weights=weights, color='skyblue', alpha=0.7, edgecolor='black')
        axs[0,1].set_title('Linear Scale - Percentage')
        axs[0,1].set_xlabel('PageRank Score')
        axs[0,1].set_ylabel('Percentage of Users')
        axs[0,1].yaxis.set_major_formatter(PercentFormatter(1.0))
        axs[0,1].grid(True, linestyle='--', alpha=0.5)
        
        #logarithmic bins (count)
        log_bins = np.logspace(np.log10(min_score), np.log10(max_score), 50)
        axs[1,0].hist(scores, bins=log_bins, log=True, color='blue', alpha=0.7, edgecolor='black')
        axs[1,0].set_title('Log-Log Scale - Count')
        axs[1,0].set_xlabel('PageRank Score (Log Scale)')
        axs[1,0].set_ylabel('Frequency (Count) - Log Scale')
        axs[1,0].set_xscale('log')
        axs[1,0].grid(True, linestyle='--', alpha=0.5, which='both')
        
        #logarithmic bins (percentage)
        axs[1,1].hist(scores, bins=log_bins, weights=weights, log=True, color='blue', alpha=0.7, edgecolor='black')
        axs[1,1].set_title('Log-Log Scale - Percentage')
        axs[1,1].set_xlabel('PageRank Score (Log Scale)')
        axs[1,1].set_ylabel('Percentage of Users - Log Scale')
        axs[1,1].set_xscale('log')
        axs[1,1].yaxis.set_major_formatter(PercentFormatter(1.0))
        axs[1,1].grid(True, linestyle='--', alpha=0.5, which='both')
        
        plt.tight_layout(rect=[0, 0.03, 1, 0.95])
        output_filename = plots_dir / f'{name}_pagerank_distribution.png'
        plt.savefig(output_filename)
        print(f"Plot saved as {output_filename.name}")
        plt.show()
        plt.clf()
        
        return pagerank
    except Exception as e:
        print(f"Error while calculating PageRank for {name}: {e}")
        return {}

def analyze_trust_transitivity(G, centrality_scores, name):
    """
    Analyzes Trust Transitivitt (Assortativity).
    I examine if trustworthy users (users with high PageRank) tend
    to rate other trustworthy users. I plot Node's PageRank vs
    Average Neighbor's PageRank.

    Args:
        G (nx.DiGraph): The graph
        centrality_scores (dict): The PageRank scores computed previously
        name (str): Name of the network
    """
    print(f"--- Analyzing Trust Transitivity (Assortativity) for {name} ---")
    if not centrality_scores:
        print("No centrality scores provided!")
        return
    
    x_values = []
    y_values = []
    for node in centrality_scores:
        my_score = centrality_scores[node]
        #find out-neighbors with positive rating
        #i only care about positive endorsements for trust transitivity
        successors = [n for n in G.successors(node) if G[node][n].get('rating', 0)>0]
        if successors:
            #calculating average PageRank of neighbors
            #i ignore the neighbors that are not in centrality_scores dictionary
            neighbor_scores = [centrality_scores[n] for n in successors if n in centrality_scores]
            if neighbor_scores:
                avg_neighbor_score = np.mean(neighbor_scores)
                x_values.append(my_score)
                y_values.append(avg_neighbor_score)
    #check if i have data
    if len(x_values) < 2:
        print("Not enough data to plot transitivity.")
        return
    #calculate correlation
    correlation, p_value = stats.pearsonr(x_values, y_values)
    print(f"{name} | Correlation (Trust vs Neighbor Trust): {correlation:.4f} (p={p_value:.4e})")
    if correlation > 0:
        print(f"    -> Result: Positive correlation! Trustworthy users tend to trust other trustworthy users (Assortative).")
    elif correlation<0:
        print(f"    -> Result: Negative correlation! Trustworthy users tend to trust low-reputation users (Disassortative).")
    else:
        print(f"    -> Result: No significant linear correlation.")
        
    #plots (log-log because PageRank is Power Law distributed)
    plt.figure(figsize=(8, 6))
    plt.scatter(x_values, y_values, alpha=0.3, color='blue', s=10)
    plt.title(f'Trust Transitivity: Node PageRank vs Neighbor Average PageRank ({name})')
    plt.xlabel('Node PageRank (Log)')
    plt.ylabel('Average Out-Neighbor PageRank (Log)')
    plt.xscale('log')
    plt.yscale('log')
    plt.grid(True, which="both", ls="--", alpha=0.3)
    #add a trend line
    try:
        #i need sorted arrays for the line plot to look correct
        #i filter out zeros or negative values before log
        x_log = np.log10(x_values)
        y_log = np.log10(y_values)
        m, b= np.polyfit(x_log, y_log, 1)
        #create sequence for the line
        x_fit = np.linspace(min(x_values), max(x_values), 100)
        y_fit = 10**(m * np.log10(x_fit) + b)
        plt.plot(x_fit, y_fit, color='red', label=f'Fit (correlation={correlation:.4f})')
    except Exception as e:
        print(f"Could not plot trend line: {e}")
        
    plt.legend()
    plt.tight_layout()
    #save plot and show
    output_filename = plots_dir / f'{name}_trust_transitivity.png'
    plt.savefig(output_filename)
    print(f"Plot saved as {output_filename.name}")
    plt.show()
    plt.clf()