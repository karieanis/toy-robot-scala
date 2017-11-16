package com.rea.simulation.parser

import com.rea.simulation.parser.generated.SimulationCommandLexer
import org.antlr.v4.runtime.{CharStream, LexerNoViableAltException}

class SimulationCommandBailOutLexer(input: CharStream) extends SimulationCommandLexer(input) {
  override def recover(e: LexerNoViableAltException): Unit = throw new RuntimeException(e)
}
