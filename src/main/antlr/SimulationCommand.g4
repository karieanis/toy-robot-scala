grammar SimulationCommand;

instruction: command EOF ;
command
  : PLACE point COMMA orientation #PLACE
  | MOVE   #MOVE
  | turn   #TURN
  | REPORT #REPORT
  ;

point: x COMMA y ;
orientation: NORTH | SOUTH | EAST | WEST ;
turn: LEFT | RIGHT ;

x: INT ;
y: INT ;

COMMA: ',' ;

PLACE: 'PLACE' ;
MOVE: 'MOVE' ;
REPORT: 'REPORT' ;

NORTH: 'NORTH' ;
SOUTH: 'SOUTH' ;
EAST: 'EAST' ;
WEST: 'WEST' ;

LEFT: 'LEFT' ;
RIGHT: 'RIGHT' ;


INT: DIGIT ;
WS: [ \t\r\n]+ -> channel(HIDDEN) ;

fragment DIGIT: [0-9] ;