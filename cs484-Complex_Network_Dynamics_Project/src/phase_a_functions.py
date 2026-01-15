# src/phase_a_functions.py

import networkx as nx
import numpy as np
import scipy.stats as stats
import matplotlib.pyplot as plt
from pathlib import Path
import random

#save plots in a folder
script_path = Path(__file__)
script_dir = script_path.parent
project_root = script_dir.parent
plots_dir = project_root / "plots" #plots directory
plots_dir.mkdir(exist_ok = True) #creates plots dir if it doesnt already exist
print(f"Plots will be saved to: {plots_dir}")

def get_degree_series(G):
    """
    Helper to extrcat the degree sequence of a graph G.
    """
    return [d for n, d in G.degree()]

def fit_power_law_alpha(G, name):
    """
    Estimates the Power Law exponent (alpha) for the degree
    distribution. Uses a log-log linear regression on the 
    probability density.
    P(k) ~ k^(alpha) => log(P(k)) ~ -alpha * log(k) + C

    Args:
        G (nx.DiGrah): The graph
        name (str): Name of the network

    Returns:
        alpha (float): The estimated exponent
    """
    print(f"--- Calculating Power Law Alpha for {name} ---")
    
    degrees = get_degree_series(G)
    #i filret the zeors
    degrees = [d for d in degrees if d>0]
    
    #log-log binning to smooth the tail
    #i use logarithmic bins to avoid noise at high degrees
    min_deg, max_deg = min(degrees), max(degrees)
    bins = np.logspace(np.log10(min_deg), np.log10(max_deg+1), num=20)
    hist, bin_edges = np.histogram(degrees, bins=bins, density=True)
    #calculate bin centers
    bin_centers = (bin_edges[:-1] + bin_edges[1:]) / 2
    #filter out empty bins for regression
    mask = hist >0
    x_data = bin_centers[mask]
    y_data = hist[mask]
    #log-transform
    log_x = np.log10(x_data)
    log_y = np.log10(y_data)
    #linear regression
    slope, intercept, r_value, p_value, std_err = stats.linregress(log_x, log_y)
    
    alpha = -slope
    print(f"{name} | Estimated Alpha: {alpha:.4f} (R^2: {r_value**2:.4f})")
    
    #plots
    plt.figure(figsize=(8, 5))
    plt.scatter(log_x, log_y, label='Data (Log-Binned)', color='black', alpha=0.7)
    plt.plot(log_x, slope*log_x+intercept, color='red', label=f'Fit (alpha={alpha:.2f})')
    plt.title(f'Power Law Fit for {name}')
    plt.xlabel('log10(Degree)')
    plt.ylabel('log10(Probability)')
    plt.legend()
    plt.grid(True, linestyle='--', alpha=0.6)

    plt.tight_layout()
    output_filename = plots_dir / f'{name}_power_law_fit.png'
    plt.savefig(output_filename)
    print(f"Plot saved as {output_filename.name}")
    plt.show()
    plt.clf() #clean plot
    
    return alpha

def calculate_clustering_coefficient(G, name):
    """
    Calculates the average clustering coefficient

    Args:
        G (nx.DiGrah): The graph
        name (str): Name of the network

    Returns:
        avg_clustering (float): The average clustering coefficient of graph G.
    """
    print(f"--- Calculating Clustering Coefficient for {name} ---")
    avg_clustering = nx.average_clustering(G)
    print(f"{name} | Average Clustering Coefficient: {avg_clustering:.6f}")
    return avg_clustering

def calculate_average_path_length(G, name, sample_size=5000):
    """
    Calculates the Average Shortest Path Length on the LSCC.
    If the graph is large (>5000 nodes), it uses sampling to approximate.

    Args:
        G (nx.DiGrah): The graph
        name (str): Name of the network
        sample_size (int): Number of nodes to sample for large graphs

    Returns:
        avg_path (float): The average path length of lscc of graph G.
    """
    print(f"--- Calculating Average Path Length for {name} ---")
    
    #check for strong connectivity
    if not nx.is_strongly_connected(G):
        print(f"{name} is not strongly connected. Extracting LSCC...")
        largest_scc = max(nx.strongly_connected_components(G), key=len)
        S = G.subgraph(largest_scc).copy()
        print(f"{name} LSCC Size: {S.number_of_nodes()} nodes")
    else:
        S = G.copy()
    
    numOfNodes = S.number_of_nodes()
    
    #exact calculation for small graphs
    if numOfNodes <= 5000:
        avg_path = nx.average_shortest_path_length(S)
        print(f"{name} (LSCC) | Average Path Length: {avg_path:.4f}")
        return avg_path
    
    #sampling for large graphs
    else:
        print(f"{name} is too large for exact calculation. Estimating using {sample_size} random nodes...")
        try:
            nodes = list(S.nodes())
            #sample random source nodes
            sampled_sources = random.sample(nodes, min(sample_size, numOfNodes))
            total_length = 0
            total_paths = 0
            for source in sampled_sources:
                #compute shortest paths from this source to all reachable targets in S
                lengths = nx.single_source_shortest_path_length(S, source)
                #sum of lengths (excluding distance to self)
                total_length += sum(lengths.values())
                #count paths
                total_paths += len(lengths) - 1
            if total_paths > 0:
                avg_path = total_length / total_paths
                print(f"{name} (LSCC) | Estimated Average Path Length: {avg_path:.4f} (Sampled)")
                return avg_path
            else:
                return 0.0
        except Exception as e:
            print(f"Could not estimate path length for {name}: {e}")
            return None

def calculate_reciprocity(G, name):
    """
    Calculates Reciprocity: The ratio of edges that are bidirectional.

    Args:
        G (nx.DiGrah): The graph
        name (str): Name of the network

    Returns:
        reciprocity (float): The ratio of edges that are bidirectional.
    """
    print(f"--- Calculating Reciprocity for {name} ---")
    reciprocity = nx.reciprocity(G)
    print(f"{name} | Reciprocity: {reciprocity:.4f}")
    return reciprocity