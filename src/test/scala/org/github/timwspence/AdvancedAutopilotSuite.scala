package org.github.timwspence

class AdvancedAutopilotSuite extends munit.FunSuite:

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
