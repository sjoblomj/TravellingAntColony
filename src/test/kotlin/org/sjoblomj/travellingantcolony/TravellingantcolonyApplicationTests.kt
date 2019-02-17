package org.sjoblomj.travellingantcolony

import kotlinx.collections.immutable.immutableListOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.sjoblomj.travellingantcolony.domain.Place
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class TravellingAntColonyApplicationTests {

  @Test fun contextLoads() {
  }

  @Test fun handlesWrongArgumentsGracefully() {
    assertThat(parsePlaces(arrayOf(""))).isEqualTo(immutableListOf<Place>())
    assertThat(parsePlaces(arrayOf("apa", "bepa"))).isEqualTo(immutableListOf<Place>())
    assertThat(parsePlaces(arrayOf("71"))).isEqualTo(immutableListOf<Place>())
    assertThat(parsePlaces(arrayOf("71,71,many,arguments"))).isEqualTo(immutableListOf<Place>())
    assertThat(parsePlaces(arrayOf("71,71.twelve"))).isEqualTo(immutableListOf<Place>())
  }

  @Test fun canParseArguments() {
    val str = arrayOf("12.00,13.0131", "3.71,16.8492,Apa")
    val places = parsePlaces(str)

    val expected = immutableListOf(Place(12.00, 13.0131), Place(3.71, 16.8492, "Apa"))
    assertThat(places).isEqualTo(expected)
  }

  @Test fun parsesWhatItCan() {
    val expected = immutableListOf(Place(71.0, 71.0), Place(68.12, 63.63))
    assertThat(parsePlaces(arrayOf("71,71", "71,71.twelve", "68.12,63.63"))).isEqualTo(expected)
  }

  @Test fun runsBruteforcer() {
    // TODO: Not really a test
    main(arrayOf("71,71", "90,90", "68.12,63.63"))
  }
}
