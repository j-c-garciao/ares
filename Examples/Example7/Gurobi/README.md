# Gurobi Optimization Model in Python

This repository contains a Python implementation of an optimization
model solved using **Gurobi**.\
The project includes the model script, input files, and utilities for
running and analyzing results.

## Requirements

### 1. Python (3.8 or newer)

Download from: https://www.python.org/downloads/

### 2. Gurobi Optimizer

``` bash
Install Gurobi and activate a valid license.\
Guide: https://www.gurobi.com/documentation/
``` 
### 3. Python Dependencies

``` bash
pip install gurobipy pandas numpy
``` 

## Setting Up the Gurobi License

Academic license activation:

``` bash
grbgetkey <LICENSE-KEY>
``` 

Check license:

``` bash
grbprobe
``` 

## How to Run the Model

``` bash
python gurobi_model.py
```

## Model Output

Example:

``` bash
Optimal objective: 2500.00
``` 