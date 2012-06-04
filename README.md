#Introduction

DataSpork is an analysis toolkit for performing statistical analysis of 
computer simulation data. Despite the wide use and increasing importance 
of computer simulation methods such a Monte Carlo and Molecular Dynamics 
in many fields of science, robust software tools for data analysis are 
still not available. DataSpork was designed to perform statistical 
analysis common to most simulations methods, as well as allow for extension 
to other data types and analysis tasks

## Authors and history

DataSpork was written at the University of Illinois in the late 1990's 
by John Shumway for Prof. David Ceperley's research group and materials
simulation course.  Erik Draeger continued development for the next two
years. John Shumway maintains the package, mostly for student use.

## Build and run instructions

You can download the jar file directly for github:
https://github.com/downloads/shumway/DataSpork/dataspork.jar

If you wish to build from source, running ant in the home directory builds 
the jar file.

The jar file can be run with:

java -jar dataspork.jar

You may find it handy to set this as a UNIX alias using the full path to
the jar file,

alias ds='java -jar $HOME/bin/dataspork.jar'

## Notes

This program is OLD! It needs to be compiled with -soruce 1.4, as specified
in the ant file. I hope to fix that soon.