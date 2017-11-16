package com.rea.simulation.parser

import com.rea.simulation.parser.generated.SimulationCommandParser
import org.antlr.v4.runtime.tree.ParseTree
import org.antlr.v4.runtime._

/**
  * Tools for parsing commands into AST's
  */
object ParseTools {
  private val strategy:ANTLRErrorStrategy = new BailErrorStrategy

  /**
    * Generates an AST (if possible) from an unparsed command string.
    *
    * @param unparsedCommand    An unparsed command
    * @return                   A command AST
    */
  def parse(unparsedCommand: String): ParseTree = {
    val lexer  = new SimulationCommandBailOutLexer(CharStreams.fromString(unparsedCommand))
    lexer.removeErrorListeners()
    lexer.addErrorListener(ParseErrorListener)

    val parser = new SimulationCommandParser(new CommonTokenStream(lexer))
    parser.setErrorHandler(strategy)
    parser.instruction()
  }
}

case class Origin(line:Option[Int] = None, startPosition:Option[Int] = None)

case object ParseErrorListener extends BaseErrorListener {
  override def syntaxError(recognizer: Recognizer[_, _],
                           offendingSymbol: scala.Any,
                           line: Int,
                           charPositionInLine: Int,
                           msg: String,
                           e: RecognitionException): Unit = {
    val position = Origin(Some(line), Some(charPositionInLine))
    throw new ParseException(msg, position)
  }
}
