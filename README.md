# ares

_ares_ is a Java-based open-source desktop application for modelling instances of Maximum-flow problems (MFP) as flow networks. Also, _ares_ provides a process for computing not only the optimal, but also sub-optimal feasible flows. In such process, algorithm MFP2PNS transforms a given MFP-based flow network into a Process-Network Synthesis (PNS) problem depicted by a structure constructed with P-graphs [P-graphs](https://p-graph.org/). Then, algorithm ABB (Accelerated Branch and Bound) of the P-graph framework take this structure and its underlying constrains as inputs and identify the optimal and sub-optimal flows. Several examples can be downloaded from the folder Examples [Examples](https://github.com/j-c-garciao/ares/blob/main/Examples/).

_ares_ relies on the application _pgraph_solver.exe_ (refer to folder PNSStudio_v2022/Solver [PNSStudio_v2022/Solver/](https://github.com/j-c-garciao/ares/blob/main/PNSStudio_v2022/Solver/)), which is part of the P-graph Studio [P-graph Studio](https://p-graph.org/downloads/), for computing the feasible flows.

## Contact

Juan Carlos García-Ojeda (aka JC) - jcgarciao [at] unicartagena.edu.co
