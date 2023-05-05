package org.github.timwspence

import org.scalacheck.*
import org.scalacheck.Prop.*

class AdvancedAutopilotSuite extends munit.ScalaCheckSuite:

  test("no path") {
    val autopilot = Autopilot.Advanced(
      Grid(5, 5),
      Set(Position(1, 2), Position(3, 2), Position(2, 1), Position(2, 3))
    )

    assertEquals(autopilot.shortestPath(Position(2, 2), Position(0, 0)), None)
  }

  test("trivial") {
    val autopilot = Autopilot.Advanced(
      Grid(5, 5),
      Set.empty
    )

    assertEquals(
      autopilot.shortestPath(Position(2, 2), Position(2, 2)),
      Some(List(Position(2, 2)))
    )

  }

  test("adjacent") {
    val autopilot = Autopilot.Advanced(
      Grid(5, 5),
      Set.empty
    )

    assertEquals(
      autopilot.shortestPath(Position(2, 2), Position(2, 3)),
      Some(List(Position(2, 2), Position(2, 3)))
    )
  }

  property("agrees with Simple autopilot when no mountains") {

    val gen =
      for
        x <- Gen.choose(1, 50)
        y <- Gen.choose(1, 50)
        x1 <- Gen.choose(0, x - 1)
        y1 <- Gen.choose(0, y - 1)
        x2 <- Gen.choose(0, x - 1)
        y2 <- Gen.choose(0, y - 1)
      yield (Grid(x, y), Position(x1, y1), Position(x2, y2))

    forAll(gen) { (grid, start, finish) =>
      val simple = Autopilot.Simple(grid)
      val advanced = Autopilot.Advanced(grid, Set.empty)

      assertEquals(
        advanced.shortestPath(start, finish).map(_.length),
        Some(simple.shortestPath(start, finish).length)
      )

    }

  }

  test("degenerate - start on mountain") {
    val autopilot = Autopilot.Advanced(
      Grid(5, 5),
      Set(Position(2, 2))
    )

    assertEquals(
      autopilot.shortestPath(Position(2, 2), Position(2, 3)),
      None
    )
  }

  // The semantics of this case aren't well-defined but this
  // seemed like a reasonable definition
  test("degenerate - start and finish on mountain") {
    val autopilot = Autopilot.Advanced(
      Grid(5, 5),
      Set(Position(2, 2))
    )

    assertEquals(
      autopilot.shortestPath(Position(2, 2), Position(2, 2)),
      Some(List(Position(2, 2)))
    )
  }

  test("maneouver around mountain") {
    val autopilot = Autopilot.Advanced(
      Grid(17, 17),
      Set(Position(8, 8))
    )

    assertEquals(
      autopilot.shortestPath(Position(7, 8), Position(9, 8)),
      Some(
        List(
          Position(7, 8),
          Position(7, 9),
          Position(8, 9),
          Position(9, 9),
          Position(9, 8)
        )
      )
    )

  }

  test("wrap around to avoid mountain") {
    val autopilot = Autopilot.Advanced(
      Grid(5, 5),
      Set(Position(3, 2))
    )

    assertEquals(
      autopilot.shortestPath(Position(2, 2), Position(4, 2)),
      Some(
        List(
          Position(2, 2),
          Position(1, 2),
          Position(0, 2),
          Position(4, 2)
        )
      )
    )
  }
