# Gurobi Optimization Model in Python

This repository contains a Python implementation of an optimization
model solved using **Gurobi**.\
The project includes the model script, input files, and utilities for
running and analyzing results.

## Requirements

### 1. Python (3.8 or newer)

Download from: https://www.python.org/downloads/

### 2. Gurobi Optimizer

Install Gurobi and activate a valid license.\
Guide: https://www.gurobi.com/documentation/

### 3. Python Dependencies

    pip install gurobipy pandas numpy

## Setting Up the Gurobi License

Academic license activation:

    grbgetkey <LICENSE-KEY>

Check license:

    grbprobe

## How to Run the Model

    python main.py

Optional arguments:

    python main.py --input data/input.xlsx --k 10 --verbose 1

## Project Structure

    ├── main.py
    ├── model_utils.py
    ├── data/
    ├── results/
    ├── README.md
    └── requirements.txt

## Model Output

Example:

    Optimal objective: 2500.00
    Solution written to results/solution_01.txt

## Modifying Gurobi Parameters

    model.setParam("TimeLimit", 60)
    model.setParam("MIPGap", 0.001)
    model.setParam("OutputFlag", 1)

## Citation

Gurobi Optimization, LLC. "Gurobi Optimizer Reference Manual." (2024).
