package org.github.timwspence

import cats.data.AndThen

class Simulation private (private val grid: Grid):

  import Direction.*
  import Command.*

  def run(command: Command): Rover => Rover =
    AndThen({ case Rover(position @ Position(x, y), direction) =>
      command match
        case Forward =>
          direction match
            case Up    => Rover(Position(x, (y + 1) % grid.y), direction)
            case Right => Rover(Position((x + 1) % grid.x, y), direction)
            case Down =>
              Rover(Position(x, (y + grid.y - 1) % grid.y), direction)
            case Left =>
              Rover(Position((x + grid.x - 1) % grid.x, y), direction)
        case Clockwise     => Rover(position, direction.rotateClockwise)
        case AntiClockwise => Rover(position, direction.rotateAntiClockwise)
    })

  def run(commands: List[Command]): Rover => Rover =
    commands.foldLeft(AndThen(identity[Rover]))((f, cmd) => f.andThen(run(cmd)))

object Simulation:
  def apply(grid: Grid): Simulation = new Simulation(grid)
