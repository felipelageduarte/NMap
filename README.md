#Nmap: A Novel Neighborhood Preservation Space-filling Algorithm



##ABSTRACT

Space-filling techniques seek to use as much as possible the visual space to represent a dataset, splitting it into regions that represent the data elements. Amongst those techniques, Treemaps have received wide attention due to its simplicity, reduced visual complexity, and compact use of the available space. Several different Treemap algorithms have been proposed, however the core idea is the same, to divide the visual space into rectangles with areas proportional to some data attribute or weight. Although pleasant layouts can be effectively produced by the existing techniques, most of them do not take into account relationships that might exist between different data elements when partitioning the visual space. This violates the distance-similarity metaphor, that is, close rectangles do not necessarily represent similar data elements. In this paper, we propose a novel approach, called Neighborhood Treemap (Nmap), that seeks to solve this limitation by employing a slice and scale strategy where the visual space is successively bisected on the horizontal or vertical directions and the bisections are scaled until one rectangle is defined per data element. Compared to the current techniques with the same similarity preservation goal, our approach presents the best results while being two to three orders of magnitude faster. The usefulness of Nmap is shown by two applications involving the organization of document collections and the construction of cartograms, illustrating its effectiveness on different scenarios.

##License
GNU GENERAL PUBLIC LICENSE Version 2, June 1991

##Website
[NMap website](http://nmap.vicg.icmc.usp.br/) (direct reference)

##How to cite
Duarte, F.S.L.G.; Sikansi, F.; Fatore, F.M.; Fadel, S.G.; Paulovich, F.V., "Nmap: A Novel Neighborhood Preservation Space-filling Algorithm," Visualization and Computer Graphics, IEEE Transactions on , vol.20, no.12, pp.2063,2071, Dec. 31 2014; doi: 10.1109/TVCG.2014.2346276;URL: http://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=6876012&isnumber=6935054

###Bibtex
```tex
@Article{nmap,
  Title                    = {Nmap: A Novel Neighborhood Preservation 
                                Space-filling Algorithm},
  Author                   = {Duarte, Felipe S. L. G. and Sikansi, Fabio H.  G.
                                and Fatore, Francisco M. and Fadel, Samuel G. 
                                and Paulovich, Fernando V.},
  Journal                  = {IEEE Transactions on Visualization and 
                                Computer Graphics},
  Year                     = {2014},
  Month                    = {August},
  Number                   = {99},
  Pages                    = {1},
  Volume                   = {PP},
  keywords                 = {Color;Conferences;Image color analysis;
                                Indexes;Layout;Shape;Visualization}, 
  ISSN                     = {1077-2626}
  Doi                      = {10.1109/TVCG.2014.2346276},
}
```
