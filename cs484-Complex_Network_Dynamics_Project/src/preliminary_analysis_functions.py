# src/preliminary_analysis.py

import networkx as nx
import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path
import numpy as np

#save plots in a folder
script_path = Path(__file__)
script_dir = script_path.parent
project_root = script_dir.parent
plots_dir = project_root / "plots" #plots directory
plots_dir.mkdir(exist_ok = True) #creates plots dir if it doesnt already exist
print(f"Plots will be saved to: {plots_dir}")

def create_graphs(df_btc, df_ep):
    """
    Transforms dataframes to networks graphs.
    It also adds a 'normalized_rating' attribute
    (all positive =1, all negative =-1) for fair comparison
    between the two dataframes.
    
    Args:
        df_btc (dataframe): Bitcoin OTC csv
        df_ep (dataframe): Epinions csv

    Returns:
        G_btc (NetworkX DiGraph): Bitcoin OTC graph
        G_ep (NetworkX DiGraph): Epinions graph
    """
    
    print("--- Creating NetworkX DiGraphs ---")
    
    #create Bitcoin OTC graph
    G_btc = nx.from_pandas_edgelist(
        df_btc,
        'source',
        'target',
        edge_attr=['rating', 'time'],
        create_using = nx.DiGraph()
    )
    #add normalized_rating attribute
    print("Adding 'normalized_rating' to Bitcoin OTC Graph...")
    for u,v,data in G_btc.edges(data=True):
        if data['rating'] > 0:
            G_btc[u][v]['normalized_rating'] = 1
        elif data['rating'] < 0:
            G_btc[u][v]['normalized_rating'] = -1
        else:
            G_btc[u][v]['normalized_rating'] = 0
    print(f"Bitcoin OTC Graph created")
    
    #create Epinions graph
    G_ep = nx.from_pandas_edgelist(
        df_ep,
        'source',
        'target',
        edge_attr=['rating'],
        create_using = nx.DiGraph()
    )
    print(f"Epinions Graph created")
    print("\n" + "="*50 + "\n")
    
    return G_btc, G_ep

def calculate_basic_stats(G, name):
    """
    Calculates and prints basic statistics for each graph

    Args:
        G (NetworkX DiGraph): A graph
        name (string): Title of each graph
    """
    print(f"--- Basic Statistics for {name} ---")
    num_nodes = G.number_of_nodes()
    num_edges = G.number_of_edges()
    density = nx.density(G)
    
    print(f"Total Nodes: {num_nodes}")
    print(f"Total Edges: {num_edges}")
    print(f"Density: {density:.6f}")
    
def plot_rating_distribution(df, name):
    """
    Creates and saves the plots of rating distribution
    (If its Bitcoin's dataframe, then it creates another plot with
    normalized (all negative = -1, all positive = 1) ratings, 
    for a fair comparison with Epinion's dataframe)

    Args:
        df (dataframe): The csv of each dataset
        name (string): Title of each dataframe
    """
    
    #find percentage of positivee and negative edges
    total_edges = len(df)
    positive_edges = len(df[df['rating'] > 0])
    negative_edges = len(df[df['rating'] < 0])
    
    positive_perc = (100 * positive_edges) / total_edges
    negative_perc = (100 * negative_edges) / total_edges
    
    print(f"--- {name} Positive/Negative Statistics ---")
    print(f"Total Ratings: {total_edges}")
    print(f"Positive: {positive_edges} ({positive_perc:.3f}%)")
    print(f"Negative: {negative_edges} ({negative_perc:.3f}%)")
    print("\n" + "="*50 + "\n")
    
    #creates plots with percentages
    print(f"Plotting Detailed Rating Distribution for {name}...")
    plt.figure(figsize=(12,7))
    
    #count and sort the frequency of each rating
    rating_perc = (df['rating'].value_counts(normalize=True)*100).sort_index()
    rating_perc.plot(kind='bar', color='blue', edgecolor='black')
    
    plt.title(f'Rating (Trust/Distrust) Distribution for {name}', fontsize=16)
    plt.xlabel('Rating', fontsize=12)
    plt.ylabel('Frequency (%)', fontsize=12)
    plt.xticks(rotation=0)
    plt.grid(axis='y', linestyle='--', alpha=0.7)
    plt.tight_layout()
    
    output_filename = plots_dir / f'{name}_rating_distribution.png'
    plt.savefig(output_filename)
    print(f"Plot saved as {output_filename.name}")
    plt.clf() #clean plot
    
    #NORMALIZED plot (only for BTC)
    if "Bitcoin" in name:
        print(f"Plotting Normalized Rating Distribution for {name}...")
        
        plt.figure(figsize=(12,7))
        norm_data = {'Negative ': negative_perc, 'Positive ': positive_perc}
        norm_series = pd.Series(norm_data)
        norm_series.plot(kind='bar', color='blue', edgecolor='black')
        plt.title(f'Normalized Rating Distribution for {name}', fontsize=16)
        plt.xlabel('Rating Type', fontsize=12)
        plt.ylabel('Percentage (%)', fontsize=12)
        plt.xticks(rotation=0)
        plt.grid(axis='y', linestyle='--', alpha=0.7)
        plt.tight_layout()
        
        norm_output_filename = plots_dir / f'{name}_rating_distribution_normalized.png'
        plt.savefig(norm_output_filename)
        print(f"Normalized plot saved as {norm_output_filename}")
        plt.clf()
    
def plot_degree_distribution(G, name):
    """
    Creates and saves the plots of degree distribution (in/out) in log-log scale

    Args:
        G (NetworkX DiGraph): A graph
        name (string): Title of each graph
    """
    
    print(f"Plotting Degree Distribution for {name}...")
    
    in_degrees = [d for n, d in G.in_degree() if d > 0]
    out_degrees = [d for n, d in G.out_degree() if d > 0]
    
    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(16, 7))
    
    #In Degree (creation of log-spaced bins)
    max_in = max(in_degrees)
    bins_in = np.logspace(np.log10(1), np.log10(max_in + 1), num=50)
    ax1.hist(in_degrees, bins = bins_in, color='blue', alpha=0.7, edgecolor='black')
    ax1.set_title(f'In-Degree Distribution for {name} (Log-Log)', fontsize=14)
    ax1.set_xlabel('In-Degree', fontsize=12)
    ax1.set_ylabel('Frequency', fontsize=12)
    ax1.set_xscale('log')
    ax1.set_yscale('log')
    
    #Out Degree (creation of log-spaced bins)
    max_out = max(out_degrees)
    bins_out = np.logspace(np.log10(1), np.log10(max_out + 1), num=50)
    ax2.hist(out_degrees, bins = bins_out, log=True, color='green', alpha=0.7, edgecolor='black')
    ax2.set_title(f'Out-Degree Distribution for {name} (Log-Log)', fontsize=14)
    ax2.set_xlabel('Out-Degree', fontsize=12)
    ax2.set_ylabel('Frequency', fontsize=12)
    ax2.set_xscale('log')
    ax2.set_yscale('log')
    
    plt.tight_layout()
    output_filename = plots_dir / f'{name}_degree_distribution.png'
    plt.savefig(output_filename)
    print(f"Plot saved as {output_filename.name}")
    plt.clf()
    
def plot_weighted_degree_distribution(G, name):
    """
    Creates and saves the plots of weighted degree distribution (in/out).
    These are not log-scale, because some weights are negative
    (If its Bitcoin's dataframe, then it creates another plot with
    normalized (all negative = -1, all positive = 1) ratings, 
    for a fair comparison with Epinion's dataframe)

    Args:
        G (NetworkX DiGraph): A graph
        name (string): Title of each graph
    """
    
    print(f"Plotting Weighted Degree Distribution for {name} (Real Weights)...")
    
    in_w_degrees = [d for n, d in G.in_degree(weight='rating')]
    out_w_degrees = [d for n, d in G.out_degree(weight='rating')]
    
    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(16, 7))
    
    #Weighted In Degree
    ax1.hist(in_w_degrees, bins = 50, color='blue', alpha=0.7, edgecolor='black')
    ax1.set_title(f'Weighted In-Degree Distribution for {name}', fontsize=14)
    ax1.set_xlabel('Sum of Incoming Ratings', fontsize=12)
    ax1.set_ylabel('Frequency', fontsize=12)
    ax1.grid(True, which="both", ls="--", alpha=0.5)
    
    #Weighted Out Degree
    ax2.hist(out_w_degrees, bins = 50, color='green', alpha=0.7, edgecolor='black')
    ax2.set_title(f'Weighted Out-Degree Distribution for {name}', fontsize=14)
    ax2.set_xlabel('Sum of Outgoing Ratings', fontsize=12)
    ax2.set_ylabel('Frequency', fontsize=12)
    ax2.grid(True, which="both", ls="--", alpha=0.5)
    
    plt.tight_layout()
    output_filename = plots_dir / f'{name}_weighted_degree_distribution.png'
    plt.savefig(output_filename)
    print(f"Plot saved as {output_filename.name}")
    plt.clf()
    
    #NORMALIZED plot (only for BTC)
    if "Bitcoin" in name:
        print(f"Plotting Normalized Weighted Degree Distribution for {name}...")
        
        in_w_degrees_norm = [d for n, d in G.in_degree(weight='normalized_rating')]
        out_w_degrees_norm = [d for n, d in G.out_degree(weight='normalized_rating')]
        fig_norm, (ax1_norm, ax2_norm) = plt.subplots(1, 2, figsize=(16,7))
        
        #Weighted In Degree
        ax1_norm.hist(in_w_degrees_norm, bins = 50, color='blue', alpha=0.7, edgecolor='black')
        ax1_norm.set_title(f'Normalized Weighted In-Degree Distribution for {name}', fontsize=14)
        ax1_norm.set_xlabel('Sum of Normalized Incoming Ratings (-1/+1)', fontsize=12)
        ax1_norm.set_ylabel('Frequency', fontsize=12)
        ax1_norm.grid(True, which="both", ls="--", alpha=0.5)
        
        #Weighted Out Degree
        ax2_norm.hist(out_w_degrees_norm, bins = 50, color='green', alpha=0.7, edgecolor='black')
        ax2_norm.set_title(f'Normalized Weighted Out-Degree Distribution for {name}', fontsize=14)
        ax2_norm.set_xlabel('Sum of Normalized Outgoing Ratings (-1/+1)', fontsize=12)
        ax2_norm.set_ylabel('Frequency', fontsize=12)
        ax2_norm.grid(True, which="both", ls="--", alpha=0.5)
        
        plt.tight_layout()
        norm_output_filename = plots_dir / f'{name}_weighted_degree_distribution_normalized.png'
        plt.savefig(norm_output_filename)
        print(f"Normalized plot saved as {norm_output_filename.name}")
        plt.clf()