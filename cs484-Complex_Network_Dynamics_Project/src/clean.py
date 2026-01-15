# src/clean.py

import os
import pandas as pd
from pathlib import Path

script_path = Path(__file__) #gets the path of this file
script_dir = script_path.parent #gets the path of the src folder
project_root = script_dir.parent #gets the path of the folder project484_4975
btc_path = project_root / "datasets" / "bitcoinotc.csv"
epn_path = project_root / "datasets" / "epinions.csv"

def clean_data():
    """
    Loads and cleans the datasets data
    1)removes lines with non existing values
    2)removes self-loops
    3)removes duplicates
    4)converts timestamps to datetime objects
    5)removes lines with invalid ratings
    """
    
    print(f"Loading data from: {btc_path}")
    if not btc_path.exists(): #check if the path is correct
        print(f"Error: File not found at {btc_path}")
        return None, None
    
    #load bitcoinotc.csv
    try:
        df_btc = pd.read_csv(
            btc_path,
            sep=',',
            header=None,
            names=['source', 'target', 'rating', 'time']
        )
        
        #starts cleaning
        print("--- Bitcoin OTC Data Loaded Successfully ---")
        print(f"Bitcoin OTC | Number of lines before cleaning: {len(df_btc)}")
        
        #step1: check for missing values
        missing_btc = df_btc.isnull().sum().sum()
        if missing_btc > 0:
            print(f"Bitcoin OTC | {missing_btc} missing values detected!")
            df_btc.dropna(inplace=True)
        
        #step2: check for self-loops
        self_loops_btc = len(df_btc[df_btc['source'] == df_btc['target']])
        if self_loops_btc > 0:
            print(f"Bitcoin OTC | {self_loops_btc} self-loops detected!")
            df_btc = df_btc[df_btc['source'] != df_btc['target']]
        
        #step3: remove duplicates
        duplicates_btc = df_btc.duplicated().sum()
        if duplicates_btc > 0:
            print(f"Bitcoin OTC | {duplicates_btc} duplicates detected!")
            df_btc.drop_duplicates(inplace=True)
            
        #step4: convert timestamp to datetime
        df_btc['time'] = pd.to_datetime(df_btc['time'], unit='s')
        
        #step5: check ratings
        valid_btc_ratings = list(range(-10, 11)) #-10, -9, -8, ..., 8, 9, 10
        invalid_ratings_btc = df_btc[~df_btc['rating'].isin(valid_btc_ratings)]
        if not invalid_ratings_btc.empty:
            print(f"Bitcoin OTC | {len(invalid_ratings_btc)} invalid ratings detected!")
            df_btc = df_btc[df_btc['rating'].isin(valid_btc_ratings)]
        df_btc['rating'] = df_btc['rating'].astype(int)
        
        print(f"Bitcoin OTC | Number of lines after cleaning: {len(df_btc)}")
        print("\n" + "="*50 + "\n")
    except Exception as e:
        print(f"Error loading Bitcoin OTC data: {e}")
        return None, None
    
    print(f"Loading data from: {epn_path}")
    if not epn_path.exists(): #check if the path is correct
        print(f"Error: File not found at {epn_path}")
        return None, None
    
    #load epinions.csv
    try:
        df_ep = pd.read_csv(
            epn_path,
            comment='#',
            sep='\t',
            header=None,
            names=['source', 'target', 'rating']
        )
        
        #starts cleaning
        print("--- Epinions Data Loaded Successfully ---")
        print(f"Epinions | Number of lines before cleaning: {len(df_ep)}")
        
        #step1: check for missing values
        missing_ep = df_ep.isnull().sum().sum()
        if missing_ep > 0:
            print(f"Epinions | {missing_ep} missing values detected!")
            df_ep.dropna(inplace=True)
        
        #step2: check for self-loops
        self_loops_ep = len(df_ep[df_ep['source'] == df_ep['target']])
        if self_loops_ep > 0:
            print(f"Epinions | {self_loops_ep} self-loops detected!")
            df_ep = df_ep[df_ep['source'] != df_ep['target']]
        
        #step3: remove duplicates
        duplicates_ep = df_ep.duplicated().sum()
        if duplicates_ep > 0:
            print(f"Epinions | {duplicates_ep} duplicates detected!")
            df_ep.drop_duplicates(inplace=True)
        
        #step4: check ratings
        valid_ep_ratings = [-1,1]
        invalid_ratings_ep = df_ep[~df_ep['rating'].isin(valid_ep_ratings)]
        if not invalid_ratings_ep.empty:
            print(f"Epinions | {len(invalid_ratings_ep)} invalid ratings detected!")
            df_ep = df_ep[df_ep['rating'].isin(valid_ep_ratings)]
        df_ep['rating'] = df_ep['rating'].astype(int)
        
        print(f"Epinions | Number of lines after cleaning: {len(df_ep)}")
        print("\n" + "="*50 + "\n")
    except Exception as e:
        print(f"Error loading Epinions data: {e}")
        return None, None
    
    return df_btc, df_ep