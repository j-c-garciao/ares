from gurobipy import Model, GRB

# ----------------------------------------------------------
# Corrected Max-Flow Model using Super-Source and Super-Sink
# ----------------------------------------------------------

# Original network edges and capacities
edges = {
    ("Z1", "Z2"): 2000,
    ("Z1", "Z3"): 1600,
    ("Z10", "Z11"): 500,
    ("Z2", "Z3"): 250,
    ("Z2", "Z4"): 3000,
    ("Z2", "Z5"): 1000,
    ("Z3", "Z8"): 1000,
    ("Z3", "Z9"): 400,
    ("Z4", "Z5"): 2000,
    ("Z4", "Z6"): 2000,
    ("Z4", "Z7"): 3000,
    ("Z5", "Z7"): 2000,
    ("Z6", "Z11"): 1500,
    ("Z6", "Z7"): 2000,
    ("Z7", "Z11"): 1200,
    ("Z8", "Z10"): 1600,
    ("Z8", "Z9"): 1000,
    ("Z9", "Z10"): 500
}

source = "Z1"
sink = "Z11"

# Collect all nodes
nodes = sorted({i for (i, j) in edges} | {j for (i, j) in edges})

# ----------------------------------------------------------
# Build a model
# ----------------------------------------------------------
m = Model("Corrected_MaxFlow_Z_Network")

# ----------------------------------------------------------
# Add super-source SS and super-sink TT
# ----------------------------------------------------------
SS = "SS"
TT = "TT"

# Augment edge set for super nodes
aug_edges = {(i, j): cap for (i, j), cap in edges.items()}
aug_edges[(SS, source)] = GRB.INFINITY   # unlimited arc from SS → source
aug_edges[(sink, TT)] = GRB.INFINITY     # unlimited arc from sink → TT

# ----------------------------------------------------------
# Create flow variables
# ----------------------------------------------------------
flow = m.addVars(aug_edges.keys(), name="f", lb=0)

# ----------------------------------------------------------
# Capacity constraints
# ----------------------------------------------------------
for (i, j), cap in aug_edges.items():
    if cap < GRB.INFINITY:
        m.addConstr(flow[i, j] <= cap, name=f"cap_{i}_{j}")

# ----------------------------------------------------------
# Flow conservation constraints
# ----------------------------------------------------------
all_nodes = nodes + [SS, TT]

for n in all_nodes:
    inflow = sum(flow[i, n] for (i, j) in aug_edges if j == n)
    outflow = sum(flow[n, j] for (i, j) in aug_edges if i == n)

    # SS: only outflow allowed
    if n == SS:
        m.addConstr(outflow - inflow >= 0, name="SS_balance")

    # TT: only inflow allowed
    elif n == TT:
        m.addConstr(inflow - outflow >= 0, name="TT_balance")

    # All internal nodes must satisfy flow conservation
    else:
        m.addConstr(inflow == outflow, name=f"node_bal_{n}")

# ----------------------------------------------------------
# Objective: Maximize flow from source to sink
# ----------------------------------------------------------
m.setObjective(flow[sink, TT], GRB.MAXIMIZE)

# ----------------------------------------------------------
# Optimize
# ----------------------------------------------------------
m.optimize()

# ----------------------------------------------------------
# Print results
# ----------------------------------------------------------
if m.status == GRB.OPTIMAL:
    print("\n============================")
    print(" Max Flow Result ")
    print("============================")
    print(f"Max flow from {source} → {sink}:", m.objVal)
    print("Flow distribution (only non-zero flows):")

    for (i, j) in aug_edges:
        if i in [SS] or j in [TT]:
            continue  # Skip artificial arcs
        val = flow[i, j].x
        if val > 1e-6:
            print(f"{i} -> {j}: {val}")
