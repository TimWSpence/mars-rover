package org.github.timwspence

import scala.collection.mutable.ListBuffer
import scala.collection.immutable.Queue

object Autopilot:
  /*
   * The shortest path on a grid with no obstacles is an L where we move
   * to the left or right and then up or down (or more precisely no path is
   * shorter than this)
   * */
  class Simple private (private val grid: Grid):
    def shortestPath(start: Position, finish: Position): List[Position] =
      val path = ListBuffer.empty[Position]
      if (start.x <= finish.x) {
        if (finish.x - start.x <= start.x + grid.x - finish.x) {
          0.to(finish.x - start.x).foreach { i =>
            path += Position(start.x + i, start.y)
          }
        } else {}
      } else {}
      path.toList

  /** Djikstra on a graph of equal weights degenerates to breadth-first search
    * (BFS)
    */
  class Advanced private (
      private val grid: Grid,
      private val mountains: Set[Position]
  ):

    // Optional because no path might exist
    def shortestPath(
        start: Position,
        finish: Position
    ): Option[List[Position]] =
      if (start == finish) Some(List(start))
      else {
        val ancestors = bfs(finish, Queue(start), Map.empty)
        ancestors.get(finish).map { _ =>
          @annotation.tailrec
          def reconstructPath(
              current: Position,
              path: List[Position]
          ): List[Position] =
            if (current == start) start :: path
            else {
              // We know this is present by construction
              val prev = ancestors(current)
              reconstructPath(prev, current :: path)
            }

          reconstructPath(finish, Nil)
        }
      }

    // Breadth-first search for finish position. Records a map of position to
    // parent on the shortest path from our start position, which can be used to
    // reconstruct the shortest path from start to finish
    @annotation.tailrec
    private def bfs(
        finish: Position,
        toVisit: Queue[Position],
        ancestors: Map[Position, Position]
    ): Map[Position, Position] =
      toVisit.dequeueOption match
        case None => ancestors
        case Some((pos, queue)) =>
          if (pos == finish) ancestors
          else {
            val ns = neighbours(pos).filter(p =>
              // Not a mountain and not already visited in our BFS
              !mountains.contains(p) && ancestors.get(p).isEmpty
            )
            bfs(finish, queue ++ ns, ancestors ++ ns.map(_ -> pos))
          }

    private def neighbours(p: Position): List[Position] =
      List(
        Position((p.x + 1) % grid.x, p.y),
        Position((grid.x + p.x - 1) % grid.x, p.y),
        Position(p.x, (p.y + 1) % grid.y),
        Position(p.x, (grid.y + p.y - 1) % grid.y)
      )

  object Advanced:
    def apply(grid: Grid, mountains: Set[Position]): Autopilot.Advanced =
      new Advanced(grid, mountains)
