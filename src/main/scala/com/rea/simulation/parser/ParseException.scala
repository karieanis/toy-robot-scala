package com.rea.simulation.parser

class ParseException(message: String, position: Origin)
  extends RuntimeException(s"Parsing error $message on line ${position.line} at position ${position.startPosition}")