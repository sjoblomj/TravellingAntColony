package org.sjoblomj.travellingantcolony

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.sjoblomj.travellingantcolony.domain.Place
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class TravellingAntColonyApplication

fun main(args: Array<String>) {
  val places = parsePlaces(args)
  bruteforce(places)
  SpringApplication.run(TravellingAntColonyApplication::class.java, *args)
}

fun parsePlaces(args: Array<String>): ImmutableList<Place> {
  return args.mapNotNull { parsePlace(it) }.toImmutableList()
}

private fun parsePlace(place: String): Place? {
  val strings = place.split(",")
  if (strings.size != 2 && strings.size != 3) {
    println("'$place' did not contain two coordinates")
    return null
  }

  return try {
    val c0 = strings[0].toDouble()
    val c1 = strings[1].toDouble()
    val name = if (strings.size == 2) "" else strings[2]

    Place(c0, c1, name)

  } catch (e: Exception) {
    println("'$place' did not contain parseable coordinates")
    null
  }
}
