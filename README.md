# Travelling Ant Colony
This is a simple brute-forcer for finding a solution to the [Travelling Salesman Problem](https://en.wikipedia.org/wiki/Travelling_salesman_problem). The program starts out by greedily calculating a (hopefully) decent path by picking the nearest unvisited node, after which it will evalute every other permutation of nodes. When determining a permutation, it could be that after visiting only a subset of nodes, the path already exceeds the record. In that case, the algorithm will stop early and not waste time calculating the rest of the permutation.

The code was written to experiment with Kotlin. It uses several Kotlin features, such as immutable data structures, extension functions, typealiases, tail recursion, sequence generation, smart casting, local functions, default parameter values etc.


## About
Written by Johan Sjöblom.

Written in Kotlin, using Maven and Spring.
