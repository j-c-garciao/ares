# P-Graph Studio 

This repository includes a P-Graph model file (.psgx) that can be opened, edited, and executed using P-Graph Studio, the official software environment of the P-Graph framework. This README provides instructions for installing P-Graph Studio and running the provided .psgx model file.

## Requirements

To work with the .psgx files included in this project, you need:

### P-Graph Studio (Windows application)

A Windows 10 or later PC

Optional: Microsoft Excel (for exporting solution tables)


### Installing P-Graph Studio

**1.** Visit the official P-Graph website:

``` bash
https://pgraph.org
```
**2. ** Go to:
``` bash 
Download → P-Graph Studio
```
**3. ** Select the appropriate installer (usually: P-Graph Studio 5.x for Windows).

**4. ** Download the installer .exe file.

**5. ** Run the installer and follow the steps:

	5.1 Accept license agreement
	5.2 Choose installation directory
	5.3 Complete installation

** 6.** Once installed, you will find P-Graph Studio in your Windows Start Menu.

### Opening a .psgx File

**1. ** Launch P-Graph Studio.

In the menu bar, click
File → Open…

Navigate to the models/ folder in this repository (or wherever you placed your files).

Select the desired file, for example:

´´´ bash
network_model.psgx
´´´

Click Open.
The full P-Graph model (materials, operating units, structure) will be displayed.

### Executing the P-Graph Model

To compute the optimal or sub-optimal solutions:

With the .psgx file open, go to the top menu and click:

Solve → Maximum Structure
(to compute the maximal structure)

Then select:

Solve → Solution Structure
(to obtain the optimal solution)

For multiple solutions (K-best):

Solve → All Solutions
or
Solve → Next Best Solution

After solving, the results window will show:

Solution structure graph

Material flows

Operating units used

Objective value

To export results:

Click Export → Excel, or

Right-click on the results table → Export


Each .psgx file is ready to open directly in P-Graph Studio.