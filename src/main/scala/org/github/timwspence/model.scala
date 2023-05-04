package org.github.timwspence

enum Command:
  case Forward, Clockwise, AntiClockwise

case class Position(x: Int, y: Int)

case class Grid(x: Int, y: Int)

enum Direction:
  case Up, Right, Down, Left

  def rotateClockwise: Direction = this match
    case Up => Right
    case Right => Down
    case Down => Left
    case Left => Up

  def rotateAntiClockwise: Direction = this match
    case Up => Left
    case Left => Down
    case Down => Right
    case Right => Up

case class Rover(position: Position, direction: Direction)
