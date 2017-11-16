package com.rea.simulation

import java.io.{ByteArrayOutputStream, StringReader}

import org.junit.Assert
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FunSuite, Matchers}

/**
  * Basic e2e test of toy-robot simulation application
  */
@RunWith(classOf[JUnitRunner])
class SimulationSuite extends FunSuite with Matchers with TableDrivenPropertyChecks {
  val scenarios = Table(
    (
      "input",
      "expected"
    ),
    (
      """|PLACE 0,0,NORTH
|MOVE
|REPORT
""".stripMargin,
"""|0,1,NORTH
""".
        stripMargin
    ),
    (
      """|PLACE 0,0,NORTH
|LEFT
|REPORT
""".stripMargin,
"""|0,0,WEST
""".
        stripMargin
    ),
    (
      """|PLACE 1,2,EAST
|MOVE
|MOVE
|LEFT
|MOVE
|REPORT
""".stripMargin,
"""|3,3,NORTH
""".stripMargin
    )
  )

  test("All scenarios inputs give the expected output") {
    forAll(scenarios) {
      (input:String, expected:String) =>
        val reader = new StringReader(input)
        val writer = new ByteArrayOutputStream()

        Console.withIn(reader) {
          Console.withOut(writer) {
            Simulation.main(null)
          }
        }

        // using hamcrest matchers as I couldn't think of a nice way of using existing scala matchers
        // to test string equality ignoring whitespace
        Assert.assertThat(
          writer.toString("UTF-8"),
          org.hamcrest.Matchers.is(org.hamcrest.Matchers.equalToIgnoringWhiteSpace(expected))
        )
    }
  }
}
