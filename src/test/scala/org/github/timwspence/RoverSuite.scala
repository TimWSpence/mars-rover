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
    simulation.run(List.fill(200000)(Forward))(Rover(Position(0, 0), Up))
  }

  test("nonboundary movement") {
    val simulation = Simulation(Grid(5, 5))

    assertEquals(
      simulation.run(Forward)(Rover(Position(3, 3), Up)),
      Rover(Position(3, 4), Up)
    )

    assertEquals(
      simulation.run(Forward)(Rover(Position(3, 3), Right)),
      Rover(Position(4, 3), Right)
    )

    assertEquals(
      simulation.run(Forward)(Rover(Position(3, 3), Down)),
      Rover(Position(3, 2), Down)
    )

    assertEquals(
      simulation.run(Forward)(Rover(Position(3, 3), Left)),
      Rover(Position(2, 3), Left)
    )
  }

  test("boundary movement") {
    val simulation = Simulation(Grid(5, 5))

    assertEquals(
      simulation.run(Forward)(Rover(Position(4, 4), Up)),
      Rover(Position(4, 0), Up)
    )

    assertEquals(
      simulation.run(Forward)(Rover(Position(4, 4), Right)),
      Rover(Position(0, 4), Right)
    )

    assertEquals(
      simulation.run(Forward)(Rover(Position(0, 0), Down)),
      Rover(Position(0, 4), Down)
    )

    assertEquals(
      simulation.run(Forward)(Rover(Position(0, 0), Left)),
      Rover(Position(4, 0), Left)
    )
  }

  test("clockwise rotation") {
    val simulation = Simulation(Grid(5, 5))
    val position = Position(0, 0)

    assertEquals(
      simulation.run(Clockwise)(Rover(position, Up)),
      Rover(position, Right)
    )

    assertEquals(
      simulation.run(Clockwise)(Rover(position, Right)),
      Rover(position, Down)
    )

    assertEquals(
      simulation.run(Clockwise)(Rover(position, Down)),
      Rover(position, Left)
    )

    assertEquals(
      simulation.run(Clockwise)(Rover(position, Left)),
      Rover(position, Up)
    )

  }

  test("anti-clockwise rotation") {
    val simulation = Simulation(Grid(5, 5))
    val position = Position(0, 0)

    assertEquals(
      simulation.run(AntiClockwise)(Rover(position, Up)),
      Rover(position, Left)
    )

    assertEquals(
      simulation.run(AntiClockwise)(Rover(position, Left)),
      Rover(position, Down)
    )

    assertEquals(
      simulation.run(AntiClockwise)(Rover(position, Down)),
      Rover(position, Right)
    )

    assertEquals(
      simulation.run(AntiClockwise)(Rover(position, Right)),
      Rover(position, Up)
    )

  }
