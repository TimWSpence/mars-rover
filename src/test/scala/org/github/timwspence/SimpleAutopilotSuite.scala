package org.github.timwspence

class SimpleAutopilotSuite extends munit.FunSuite:

  test("trivial") {
    val autopilot = Autopilot.Simple(
      Grid(5, 5)
    )

    assertEquals(
      autopilot.shortestPath(Position(2, 2), Position(2, 2)),
      List(Position(2, 2))
    )

  }

  test("adjacent") {
    val autopilot = Autopilot.Simple(
      Grid(5, 5)
    )

    assertEquals(
      autopilot.shortestPath(Position(2, 2), Position(2, 3)),
      List(Position(2, 2), Position(2, 3))
    )
  }

  test("wrap around") {
    val autopilot = Autopilot.Simple(
      Grid(5, 5)
    )

    assertEquals(
      autopilot.shortestPath(Position(1, 2), Position(4, 2)),
      List(
        Position(1, 2),
        Position(0, 2),
        Position(4, 2)
      )
    )
  }
