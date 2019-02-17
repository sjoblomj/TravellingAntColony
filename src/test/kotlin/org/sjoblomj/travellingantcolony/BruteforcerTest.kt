package org.sjoblomj.travellingantcolony

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.toImmutableList
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.sjoblomj.travellingantcolony.domain.Place
import java.lang.Math.sqrt

class BruteforcerTest {

  @Test fun calculateLength_EmptyInput() {
    val emptyList = immutableListOf<Place>()
    assertThat(calculateLength(emptyList)).isEqualTo(0.0)

    val listOfOne = immutableListOf(Place(1.0, 2.0, "Oslo"))
    assertThat(calculateLength(listOfOne)).isEqualTo(0.0)
  }

  @Test fun calculateLength_ManyPlaces() {
    val listOfTwo = immutableListOf(Place(2.0, 1.0, "Oslo"), Place(2.0, 6.0, "Gothenburg"))
    assertThat(calculateLength(listOfTwo)).isEqualTo(5.0)

    val listWithTwoIdenticalPlaces = immutableListOf(Place(2.0, 1.0), Place(2.0, 6.0), Place(2.0, 6.0))
    assertThat(calculateLength(listWithTwoIdenticalPlaces)).isEqualTo(5.0)

    val thereAndBackAgain = immutableListOf(Place(2.0, 1.0), Place(2.0, 6.0), Place(2.0, 1.0))
    assertThat(calculateLength(thereAndBackAgain)).isEqualTo(10.0)

    val listOfThree = immutableListOf(Place(2.0, 1.0), Place(2.0, 6.0), Place(7.0, 6.0))
    assertThat(calculateLength(listOfThree)).isEqualTo(10.0)

    val listOfFour = immutableListOf(Place(2.0, 1.0), Place(12.0,  6.0), Place(7.0, 6.0), Place(7.0, 61.0))
    assertThat(calculateLength(listOfFour)).isEqualTo(5 * sqrt(5.0) + 5 + 55)
  }

  @Test fun permutationTest() {
    val a = Place(0.0, 0.0, "a")
    val b = Place(1.0, 1.0, "b")
    val c = Place(2.0, 2.0, "c")

    val emptyList = immutableListOf<Place>()
    assertThat(permutation(emptyList)).isEqualTo(immutableListOf(immutableListOf<ImmutableList<Place>>()))

    val listOfOne = immutableListOf(a)
    assertThat(permutation(listOfOne)).isEqualTo(immutableListOf(immutableListOf(a)))

    val listOfTwo = immutableListOf(a, b)
    assertThat(permutation(listOfTwo)).isEqualTo(immutableListOf(immutableListOf(a, b), immutableListOf(b, a)))

    val listOfThree = immutableListOf(a, b, c)
    val expected = immutableListOf(
        immutableListOf(a, b, c),
        immutableListOf(a, c, b),
        immutableListOf(b, a, c),
        immutableListOf(b, c, a),
        immutableListOf(c, a, b),
        immutableListOf(c, b, a))
    assertThat(permutation(listOfThree)).isEqualTo(expected)
  }

  @Test fun permutation2Test() {
    val a = Place(0.0, 0.0, "a")
    val b = Place(1.0, 1.0, "b")
    val c = Place(2.0, 2.0, "c")

    val emptyList = immutableListOf<Place>()
    assertThat(permutation2(emptyList).toList()).isEqualTo(immutableListOf(immutableListOf<ImmutableList<Place>>()))

    val listOfOne = immutableListOf(a)
    assertThat(permutation2(listOfOne).toList()).isEqualTo(immutableListOf(immutableListOf(a)))

    val listOfTwo = immutableListOf(a, b)
    assertThat(permutation2(listOfTwo).toList()).isEqualTo(immutableListOf(immutableListOf(a, b), immutableListOf(b, a)))

    val listOfThree = immutableListOf(a, b, c)
    val expected = immutableListOf(
        immutableListOf(a, b, c),
        immutableListOf(a, c, b),
        immutableListOf(b, a, c),
        immutableListOf(b, c, a),
        immutableListOf(c, a, b),
        immutableListOf(c, b, a))
    assertThat(permutation2(listOfThree).toList()).containsAll(expected)
  }

  @Test fun greedySolver() {
    val a = Place(0.0, 0.0, "a")
    val b = Place(4.0, 1.0, "b")
    val c = Place(5.0, 6.0, "c")
    val d = Place(2.0, 2.0, "d")
    val e = Place(9.0, 7.0, "e")

    val list = immutableListOf(b, c, d, e)
    val expected = immutableListOf(a, d, b, c, e, a)
    assertThat(greedySolution(list, a)).isEqualTo(expected)
  }

  @Test fun bruteforceWithInvalidInputs() {
    val emptyList = immutableListOf<Place>()
    assertThat(bruteforce(emptyList)).isEqualTo(emptyList)

    val listOfOne = immutableListOf(Place(1.0, 2.0))
    assertThat(bruteforce(listOfOne)).isEqualTo(listOfOne)
  }

  @Test fun bruteforceInputOfTwo() {
    val a = Place(0.0, 0.0, "a")
    val b = Place(4.0, 1.0, "b")

    val listOfTwo = immutableListOf(a, b)
    val expected = immutableListOf(a, b, a)
    assertThat(bruteforce(listOfTwo)).isEqualTo(expected)
  }

  @Test fun bruteforce() {

    val list = mutableListOf<Place>()
    list.add(Place(14.843435, 64.155902))
    list.add(Place(369.829464, 394.798983))
    list.add(Place(70.148814, 102.961104))
    list.add(Place(216.875209, 260.844929))
    list.add(Place(499.454448, 196.360554))
    list.add(Place(276.895963, 4.798338))
    list.add(Place(450.317844, 463.120257))
    list.add(Place(144.357250, 48.303781))
    list.add(Place(229.398167, 340.463102))
//    list.add(Place(221.398167, 341.463102))
    list.add(Place(108.504974, 460.106655))


    val expected = mutableListOf<Place>()
    expected.add(Place(14.843435, 64.155902))
    expected.add(Place(70.148814, 102.961104))
    expected.add(Place(216.875209, 260.844929))
    expected.add(Place(229.398167, 340.463102))
    expected.add(Place(108.504974, 460.106655))
    expected.add(Place(369.829464, 394.798983))
    expected.add(Place(450.317844, 463.120257))
    expected.add(Place(499.454448, 196.360554))
    expected.add(Place(276.895963, 4.798338))
    expected.add(Place(144.357250, 48.303781))
    expected.add(Place(14.843435, 64.155902))

    assertThat(bruteforce(list.toImmutableList())).isEqualTo(expected.toImmutableList())
  }

  @Test fun bruteforce2() {

    val list = mutableListOf<Place>()
    list.add(Place(14.843435, 64.155902))
    list.add(Place(369.829464, 394.798983))
    list.add(Place(70.148814, 102.961104))
    list.add(Place(216.875209, 260.844929))
    list.add(Place(499.454448, 196.360554))
    list.add(Place(276.895963, 4.798338))
    list.add(Place(450.317844, 463.120257))
    list.add(Place(144.357250, 48.303781))
    list.add(Place(229.398167, 340.463102))
  list.add(Place(221.398167, 341.463102))
  list.add(Place(222.398167, 342.463102))
    list.add(Place(108.504974, 460.106655))


    val expected = mutableListOf<Place>()
    expected.add(Place(14.843435, 64.155902))
    expected.add(Place(70.148814, 102.961104))
    expected.add(Place(216.875209, 260.844929))
    expected.add(Place(229.398167, 340.463102))
  expected.add(Place(222.398167, 342.463102))
  expected.add(Place(221.398167, 341.463102))
    expected.add(Place(108.504974, 460.106655))
    expected.add(Place(369.829464, 394.798983))
    expected.add(Place(450.317844, 463.120257))
    expected.add(Place(499.454448, 196.360554))
    expected.add(Place(276.895963, 4.798338))
    expected.add(Place(144.357250, 48.303781))
    expected.add(Place(14.843435, 64.155902))

    assertThat(bruteforce2(list.toImmutableList())).isEqualTo(expected.toImmutableList())
  }
}
