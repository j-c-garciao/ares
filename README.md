# _ares_

_ares_ is a Java-based open-source desktop application for modelling instances of Maximum-flow problems (MFP) as flow networks. Also, _ares_ provides a process for computing not only the maximum, but also sub-maximal feasible flows. In such process, algorithm MFP2PNS transforms a given MFP-based flow network into a Process-Network Synthesis ([PNS](https://doi.org/10.1002/(SICI)1097-0037(199803)31:2%3C119::AID-NET6%3E3.0.CO;2-K)) problem depicted by a structure constructed with P-graphs [P-graphs](https://p-graph.org/). Then, algorithm ABB (Accelerated Branch and Bound) of the P-graph framework take this structure and its underlying constrains as inputs and identify the maximum and sub-maximal flows. Several examples are provided in folder [Examples](https://github.com/j-c-garciao/ares/blob/main/Examples/).

_ares_ relies on the application _pgraph_solver.exe_ (refer to folder [PNSStudio_v2022/Solver/](https://github.com/j-c-garciao/ares/blob/main/PNSStudio_v2022/Solver/)), which is part of the [P-graph Studio](https://p-graph.org/downloads/), for computing the feasible flows.

![Alt text](https://github.com/j-c-garciao/ares/blob/main/Screenshot/Screenshot.png?raw=true "ares Main window")

## Build with

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
<br>
![NetBeans IDE](https://img.shields.io/badge/NetBeansIDE-1B6AC6.svg?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
<br>
[P-graph Studio](https://p-graph.org/downloads/)

## Software Requirements

To execute _ares_, you must have the following software installed:

![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)
<br>
java version "17.0.8" 2023-07-18 LTS ([download](https://www.oracle.com/java/technologies/downloads/#java17)) --> Java(TM) SE Runtime Environment (build 17.0.8+9-LTS-211)
<br>
Microsoft .NET Framework 4.5.1 (x86 and x64) ([download](https://www.microsoft.com/en-US/download/details.aspx?id=40779))
<br>
Windows Installer 4.5 ([download](https://www.microsoft.com/es-es/download/details.aspx?id=8483))

## Executing _ares_

Download and unzip the file [aresIDE.zip](https://github.com/j-c-garciao/ares/blob/main/Distribution/ares.IDE.zip). Then double-clik on the file **ares.IDE.jar**. Any comment, do not hesitate to contact me! [Go to Contact section](#contact) 

## Contact

Juan Carlos García-Ojeda (aka JC) - jcgarciao [at] unicartagena.edu.co
