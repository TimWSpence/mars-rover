package org.github.timwspence

import Command.*
import Direction.*

class RoverSuite extends munit.FunSuite:

  test("direction - rotate clockwise") {
    assertEquals(Up.rotateClockwise, Right)
    assertEquals(Right.rotateClockwise, Down)
    assertEquals(Down.rotateClockwise, Left)
    assertEquals(Left.rotateClockwise, Up)
  }

  test("direction - rotate anti-clockwise") {
    assertEquals(Up.rotateAntiClockwise, Left)
    assertEquals(Left.rotateAntiClockwise, Down)
    assertEquals(Down.rotateAntiClockwise, Right)
    assertEquals(Right.rotateAntiClockwise, Up)
  }

  test("run is stacksafe") {
    val simulation = Simulation(Grid(5, 5))
    simulation.run(List.fill(20000)(Forward))(Rover(Position(0, 0), Up))
  }
