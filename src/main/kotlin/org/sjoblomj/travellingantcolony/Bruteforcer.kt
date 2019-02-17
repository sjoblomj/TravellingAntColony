package org.sjoblomj.travellingantcolony

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.toImmutableList
import org.sjoblomj.travellingantcolony.domain.Place
import kotlin.math.sqrt

typealias Places = ImmutableList<Place>
fun Places.tail(): Places = this.removeAt(0)

fun bruteforce(places: Places): Places {
  if (places.size < 2)
    return places

  val firstNode = places.first()
  val permutations = generateAllPermutations(places.removeAt(0))

  return findShortestPath(permutations, firstNode)
}

fun bruteforce2(places: Places): Places {
  if (places.size < 2)
    return places

  val firstNode = places.first()
  val permutations = generateAllPermutations2(places.removeAt(0))

  return findShortestPath2(permutations, firstNode)
}

private fun generateAllPermutations(places: Places): ImmutableList<Places> {
  val startTime = System.currentTimeMillis()
  val permutations = permutation(places)
  print("Generated ${permutations.size} permutations in ${System.currentTimeMillis() - startTime} ms.\n")
  return permutations
}

private fun generateAllPermutations2(places: Places): Sequence<Places> {
  val startTime = System.currentTimeMillis()
  val permutations = permutation2(places)
  print("Generated permutations in ${System.currentTimeMillis() - startTime} ms.\n")
  return permutations
}

private fun findShortestPath(permutations: ImmutableList<Places>, firstNode: Place): Places {
  val greedyTime = System.currentTimeMillis()
  var bestPerm = greedySolution(permutations[0], firstNode)
  print("Generated greedy solution in ${System.currentTimeMillis() - greedyTime} ms.\n")

  var bestLength = calculateLength(bestPerm) ?: Double.MAX_VALUE
  printPermutation(bestPerm, bestLength)

  val iterationTime = System.currentTimeMillis()
  for (places in permutations) {
    val wholeTrip = (immutableListOf(firstNode) + places + firstNode).toImmutableList()
    val tripLength = calculateLength(wholeTrip, bestLength)

    if (tripLength != null) {
      bestPerm = wholeTrip
      bestLength = tripLength
      printPermutation(bestPerm, bestLength)
    }
  }
  print("Found best solution among the ${permutations.size} possible in ${System.currentTimeMillis() - iterationTime} ms.\n")
  return bestPerm
}

private fun findShortestPath2(permutations: Sequence<Places>, firstNode: Place): Places {
  val greedy = greedySolution(permutations.first(), firstNode)
  val greedyLength = calculateLength(greedy) ?: Double.MAX_VALUE
  printPermutation(greedy, greedyLength)

  val startTime = System.currentTimeMillis()
  val bestPerm = findBestPermutation(permutations.iterator(), firstNode, greedy, greedyLength)
  print("Found best solution possible in ${System.currentTimeMillis() - startTime} ms.\n")
  return bestPerm
}

private tailrec fun findBestPermutation(permutations: Iterator<Places>, firstNode: Place, bestPerm: Places, bestLength: Double): Places {
  return if (!permutations.hasNext()) {
    bestPerm

  } else {
    val places = permutations.next() // Get the next permutation and move the iterator one step forward
    val wholeTrip = (immutableListOf(firstNode) + places + firstNode).toImmutableList()
    val tripLength = calculateLength(wholeTrip, bestLength)

    val bestPermutation = if (tripLength != null) {
      printPermutation(wholeTrip, tripLength)
      wholeTrip
    } else {
      bestPerm
    }

    return findBestPermutation(permutations, firstNode, bestPermutation, tripLength ?: bestLength)
  }
}

private fun findShortestPath3(permutations: Sequence<Places>, firstNode: Place): Places {

  val startTime = System.currentTimeMillis()

  val bestPerm = permutations
      .map { getTripAndLength(it, firstNode) }
      .minBy { it.second }
      ?.first ?: permutations.first()

  printPermutation(bestPerm, calculateLength(bestPerm) ?: Double.MAX_VALUE)
  print("Found best solution in ${System.currentTimeMillis() - startTime} ms.\n")
  return bestPerm
}

private fun getTripAndLength(places: Places, firstNode: Place): Pair<Places, Double> {
  val wholeTrip = (immutableListOf(firstNode) + places + firstNode).toImmutableList()
  val tripLength = calculateLength(wholeTrip)
  return Pair(wholeTrip, tripLength ?: Double.MAX_VALUE)
}

private fun printPermutation(places: Places, length: Double) {
  print("Shortest length: ${length.toString().padEnd(18, '0')}. Permutation: $places\n")
}

fun greedySolution(places: Places, startAndEnd: Place): Places {
  if (places.isEmpty())
    return places
  val solution = greedySolution(immutableListOf(startAndEnd), places)
  return (solution + startAndEnd).toImmutableList()
}

private fun greedySolution(visitedPlaces: Places, unvisitedPlaces: Places): Places {

  if (unvisitedPlaces.isEmpty())
    return visitedPlaces

  val closestPlace = unvisitedPlaces
      .map { Pair(it, distance(visitedPlaces.last(), it)) }
      .minBy { it.second }
      ?.first ?: unvisitedPlaces.first()

  return greedySolution(visitedPlaces.add(closestPlace), unvisitedPlaces.remove(closestPlace))
}

fun permutation(unvisitedPlaces: Places, visitedPlaces: Places = immutableListOf()): ImmutableList<Places> {
  if (unvisitedPlaces.isEmpty())
    return immutableListOf(visitedPlaces)

  return unvisitedPlaces
      .flatMap { permutation(unvisitedPlaces.remove(it), visitedPlaces.add(it)) }
      .toImmutableList()
}

fun permutation3(unvisitedPlaces: Places, visitedPlaces: Places = immutableListOf()): Sequence<Places> {

  if (unvisitedPlaces.isEmpty())
    return immutableListOf(visitedPlaces).asSequence()

  return unvisitedPlaces
      .flatMap { permutation3(unvisitedPlaces.remove(it), visitedPlaces.add(it)).asIterable() }
      .asSequence()
}


/*
// https://github.com/dkandalov/kotlin-99/blob/master/src/org/kotlin99/common/collections.kt
fun <T> ImmutableList<T>.permutationsSeq(): Sequence<ImmutableList<T>> {
  if (size <= 1) return sequenceOf(this)
  val head = first()
  return tail().permutationsSeq().flatMap { tailPermutation ->
    (0..tailPermutation.size).asSequence().map { i ->
      tailPermutation.apply { add(i, head) }
    }
  }
}
*/


fun Places.permutationsSeq(): Sequence<Places> {
  if (size <= 1) return sequenceOf(this)
  val head = first()
  return tail().permutationsSeq().flatMap { tailPermutation ->
    (0..tailPermutation.size).asSequence().map { i ->
      tailPermutation.add(i, head)
    }
  }
}


fun permutation2(places: Places): Sequence<Places> {
  if (places.size <= 1) return sequenceOf(places)
  val head = places.first()
  return permutation2(places.tail()).flatMap { tailPermutation ->
    (0..tailPermutation.size).asSequence().map { i ->
      tailPermutation.add(i, head)
    }
  }
}

fun calculateLength(places: Places,
                    terminationSum: Double = Double.MAX_VALUE,
                    sum: Double = 0.0): Double? {

  return when {
    sum >= terminationSum -> null // We are not done calculating, but already know this permutation is suboptimal.
    places.size < 2       -> sum
    else                  -> calculateLength(places.removeAt(0),
                                             terminationSum,
                                             sum + distance(places[0], places[1]))
  }
}

private fun distance(p0: Place, p1: Place): Double {
  fun pow(a: Double) = Math.pow(a, 2.0)
  return sqrt(pow(p0.x - p1.x) + pow(p0.y - p1.y))
}
