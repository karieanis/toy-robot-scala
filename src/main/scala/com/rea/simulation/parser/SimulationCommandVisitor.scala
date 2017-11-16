package com.rea.simulation.parser

import com.rea.simulation.command._
import com.rea.simulation.model.{Direction, Orientation, Point}
import com.rea.simulation.parser.generated.{SimulationCommandBaseVisitor, SimulationCommandParser}
import org.antlr.v4.runtime.tree.RuleNode

object SimulationCommandVisitor {
  def apply(): SimulationCommandVisitor = new SimulationCommandVisitor()
}

/**
  * The simulation command visitor visits one of four potential command requests as defined in the simulation command grammar,
  * extracting the appropriate parts of the provided command context before requesting the a command be provided by the
  * Command companion object (factory), without being explicitly aware of the implementation of the command.
  */
class SimulationCommandVisitor extends SimulationCommandBaseVisitor[Command] {
  override def visitInstruction(ctx: SimulationCommandParser.InstructionContext): Command = visit(ctx.getChild(0))

  override def visitPlace(ctx: SimulationCommandParser.PlaceContext): Command = {
    val pointCtx = ctx.point()

    // ask for a place command, at the location and orientation as provided in the command context
    Command.place(
      Point(pointCtx.x().getText.toInt, pointCtx.y().getText.toInt),
      Orientation.valueOf(ctx.orientation().getText)
    )
  }

  override def visitMove(ctx: SimulationCommandParser.MoveContext): Command = Command.move()

  override def visitTurn(ctx: SimulationCommandParser.TurnContext): Command = Command.turn(Direction.valueOf(ctx.getText))

  override def visitReport(ctx: SimulationCommandParser.ReportContext): Command = Command.report()

}
