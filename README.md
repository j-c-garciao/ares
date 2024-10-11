# _ares_

_ares_ is a Java-based open-source desktop application for modelling instances of Maximum-flow problems (MFP) as flow networks. Also, _ares_ provides a process for computing not only the maximum, but also sub-maximal feasible flows. In such process, algorithm MFP2PNS transforms a given MFP-based flow network into a Process-Network Synthesis ([PNS](https://doi.org/10.1002/(SICI)1097-0037(199803)31:2%3C119::AID-NET6%3E3.0.CO;2-K)) problem depicted by a structure constructed with P-graphs [P-graphs](https://p-graph.org/). Then, algorithm ABB (Accelerated Branch and Bound) of the P-graph framework take this structure and its underlying constrains as inputs and identify the maximum and sub-maximal flows. Several examples are provided in folder [Examples](https://github.com/j-c-garciao/ares/blob/main/Examples/).

_ares_ relies on the application _pgraph_solver.exe_ (refer to folder [PNSStudio_v2022/Solver/](https://github.com/j-c-garciao/ares/blob/main/PNSStudio_v2022/Solver/)), which is part of the [P-graph Studio](https://p-graph.org/downloads/), for computing the feasible flows.

![Alt text](https://github.com/j-c-garciao/ares/blob/main/Screenshot/Screenshot.png?raw=true "ares Main window")

## Examples

In folder [Examples](https://github.com/j-c-garciao/ares/tree/main/Examples) seven examples are listed. Such examples correspond to different flow networks documented in the scientific literature. Example 1 is documented in the book _Introduction to Algorithms_ by Cormen et. al [link](https://dl.ebooksworld.ir/books/Introduction.to.Algorithms.4th.Leiserson.Stein.Rivest.Cormen.MIT.Press.9780262046305.EBooksWorld.ir.pdf). Example 2 is extracted from a paper of 2012 in the _Journal of Computational and Applied Mathematics_ [link](https://core.ac.uk/download/pdf/82706025.pdf). Also, Examples 3, 4, and 5 are adapted from a manuscript published at the _ Arabian Journal for Science and Engineering_ in 2023 [link](https://www.springerprofessional.de/en/complete-limits-of-flow-network-based-on-critical-flow-concept-m/23529306) In addition, Example 6 is taken from [internet](https://www.coursehero.com/file/p6injoft/The-BMZ-Maximum-Flow-Problem-The-BMZ-Company-is-a-European-manufacturer-of/). Finally, Example 7 is adapted from a paper published in the _Sustainable Energy, Grids and Networks Journal_ in 2023[link](https://www.sciencedirect.com/science/article/pii/S2352467721001156?via%3Dihub) For each example, two folders are listed (ares and p-graph). In folder b2p, you can find a file with extension .b2p which can be open in the _ares_ tool. Also, you can find another subfolder named solutions where you can find the .in and .out files required and generated my the [PNS_Solver](https://github.com/j-c-garciao/ares/blob/main/PNSStudio_v2022/Solver/), respectively. On the other hand, in folder pgsx, you can find a file with extension .pgsx which can be open in the [P-graph Studio](https://p-graph.org/)

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
