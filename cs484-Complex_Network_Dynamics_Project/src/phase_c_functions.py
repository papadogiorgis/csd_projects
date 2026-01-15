# src/phase_c_functions.py

import networkx as nx
import numpy as np
import matplotlib.pyplot as plt
from pathlib import Path

#save plots in a folder
script_path = Path(__file__)
script_dir = script_path.parent
project_root = script_dir.parent
plots_dir = project_root / "plots" #plots directory
plots_dir.mkdir(exist_ok = True) #creates plots dir if it doesnt already exist
print(f"Plots will be saved to: {plots_dir}")

def get_triad_sign_type(signs):
    """
    Helper: classifies a triad based on the signs of its 3 edges.
    signs: list of 3 integers (+1 or -1)

    Types:
        Balanced: 0 or 2 negative edges
        Unbalanced: 1 or 3 negative edges
    """
    negative_count = signs.count(-1)
    if (negative_count == 0):
        return "T3 (+++) Balanced"
    elif (negative_count == 1):
        return "T2 (++-) Unbalanced"
    elif (negative_count == 2):
        return "T1 (+--) Balanced"
    else:
        return "T0 (---) Unbalanced"

def calculate_structural_balance(G, name):
    """
    Counts and classifies triangles based on Structural Balance Theory. Since
    the graph is directed, we convert to undirected to find the 'structural'
    triangles (A-B-C connection).

    Args:
        G (nx.DiGraph): The graph
        name (str): Name of rhe network
        
    Returns:
        triad_counts (array): array of the 4 types of triads and their counts
    """
    print(f"--- Analyzing Structural Balance for {name} ---")
    #create a simplified signed undirected graph
    G_undirected = nx.Graph()
    for u, v, data in G.edges(data=True):
        rating = data.get('rating', 0)
        sign = 1 if rating>0 else -1
        if G_undirected.has_edge(u, v):
            if G_undirected[u][v]['sign'] == 1 and sign == -1:
                G_undirected[u][v]['sign'] = -1
        else:
            G_undirected.add_edge(u, v, sign=sign)
            
    print(f"{name} | Converted to undirected signed graph with {G_undirected.number_of_nodes()} nodes and {G_undirected.number_of_edges()} edges.")
    
    triad_counts = {
        "T3 (+++) Balanced": 0,
        "T2 (++-) Unbalanced": 0,
        "T1 (+--) Balanced": 0,
        "T0 (---) Unbalanced": 0
    }
    
    #triangle finding
    #pre-compute adjency sets for O(1) lookups
    #this uses some ram but much less than clique enumeration
    #i tried using clique enumeration but ended up using 90% ram without a result (beautiful, aint it?)
    adj = {n: set(G_undirected[n]) for n in G_undirected}
    #sort nodes to ensure we process u<v<w once
    nodes = sorted(G_undirected.nodes())
    count = 0
    for u in nodes:
        #get neighbors
        neighbors_u = adj[u]
        #only look for neighbors v that are larger than u
        larger_neighbors = [v for v in neighbors_u if v > u]
        for v in larger_neighbors:
            #i look for w such that w is a neighbor of bot u and v and w>v
            common_neighbors = neighbors_u.intersection(adj[v])
            for w in common_neighbors:
                if w>v:
                    #found a unique triangle
                    s1 = G_undirected[u][v]['sign']
                    s2 = G_undirected[v][w]['sign']
                    s3 = G_undirected[w][u]['sign']
                    t_type = get_triad_sign_type([s1, s2, s3])
                    triad_counts[t_type] +=1
                    count +=1
        #print triangles so far
        if count>0 and count%500000 == 0:
            print(f"    ...found {count} triangles so far!")
    
    
    print(f"{name} | Total Triangles Found: {count}")
    
    #i calculate ratios
    total = max(count, 1)
    balanced = triad_counts["T3 (+++) Balanced"] + triad_counts["T1 (+--) Balanced"]
    unbalanced = triad_counts["T0 (---) Unbalanced"] + triad_counts["T2 (++-) Unbalanced"]
    print(f"{name} | Balanced Triangles: {balanced} ({balanced/total*100:.4f}%)")
    print(f"{name} | Unbalanced Triangles: {unbalanced} ({unbalanced/total*100:.4f}%)")
    
    #plots
    types = list(triad_counts.keys())
    counts = list(triad_counts.values())
    plt.figure(figsize=(10,6))
    colors = ['green', 'red', 'lightgreen', 'darkred']
    bars = plt.bar(types, counts, color=colors, edgecolor='black')
    plt.title(f'Structural Balance: Triad Types in {name}')
    plt.ylabel('Count')
    plt.grid(axis='y', linestyle='--', alpha=0.5)
    #add percentage labels on bars
    for b in bars:
        height = b.get_height()
        if height > 0:
            plt.text(b.get_x()+b.get_width()/2., height, f'{height/total*100:.2f}%', ha='center', va='bottom')
    plt.tight_layout()
    output_filename = plots_dir / f'{name}_structural_balance.png'
    plt.savefig(output_filename)
    print(f"Plot saved as {output_filename.name}")
    plt.show()
    plt.clf()
    
    return triad_counts

def simulate_attack(G, name, steps=20):
    """
    Simulates a targeted attack on hubs (nodes with high in-degree centrality).
    Removes nodes in batches and measures the size of the Giant Connected COmponent.
    """
    print(f"--- Simulating Robustness Attack (hub removal) for {name} ---")
    G_copy = G.copy()
    init_node_count = G.number_of_nodes()
    
    #calculate importance (total degree)
    degrees = dict(G_copy.degree())
    sorted_nodes = sorted(degrees.items(), key=lambda x: x[1], reverse=True)
    targets = [n for n, d in sorted_nodes]
    
    #remove up to 20% of top nodes
    max_removal_fraction = 0.2
    num_to_remove = int(init_node_count * max_removal_fraction)
    batch_size = int(num_to_remove / steps)
    if batch_size<1:
        batch_size = 1
    
    #x_values = % removed
    #y_values = % giant connected component
    x_values = [0]
    y_values = [100.0]
    
    #init giant connected component
    giantcc = max(nx.weakly_connected_components(G_copy), key=len)
    init_gcc_size = len(giantcc)
    print(f"    Initial Giant Connected Component Size: {init_gcc_size} nodes")
    removed_count = 0
    current_idx = 0
    
    for i in range(steps):
        nodes_to_remove = targets[current_idx: current_idx + batch_size]
        current_idx += batch_size
        G_copy.remove_nodes_from(nodes_to_remove)
        removed_count += len(nodes_to_remove)
        
        if G_copy.number_of_nodes()>0:
            giantcc = max(nx.weakly_connected_components(G_copy), key=len)
            cur_gcc_size = len(giantcc)
        else:
            cur_gcc_size = 0
        
        fraction_removed = (removed_count / init_node_count) * 100
        fraction_gc = (cur_gcc_size / init_gcc_size) * 100
        
        x_values.append(fraction_removed)
        y_values.append(fraction_gc)
        
        if fraction_gc < 1.0:
            top_x = ((100/steps)*i)*max_removal_fraction
            print(f"    Network collapsed early!! (removed top {top_x:.2f}%)")
            break
    
    return x_values, y_values

def compare_robustness(btc_data, ep_data):
    """
    Plots the comparison of two attack simulations.
    """
    print("--- Plotting Robustness Comparison ---")
    plt.figure(figsize=(10,6))
    bx, by = btc_data
    plt.plot(bx, by, marker='o', color='blue', label='Bitcoin OTC (Anonymous)', linewidth=2)
    ex, ey = ep_data
    plt.plot(ex, ey, marker='s', color='navy', label='Epinions (Pseudo-Identified)', linewidth=2, linestyle='--')
    plt.title('Network Robustness: Targeted Attack on Hubs')
    plt.xlabel('% of Nodes Removed (Top Hubs)')
    plt.ylabel('% of Giant Connected Component Remaining')
    plt.legend()
    plt.grid(True, linestyle='--', alpha=0.6)
    plt.tight_layout()
    output_filename = plots_dir / 'robustness_comparison.png'
    plt.savefig(output_filename)
    print(f"Comparison plot saved as {output_filename.name}")
    plt.show()
    plt.clf()