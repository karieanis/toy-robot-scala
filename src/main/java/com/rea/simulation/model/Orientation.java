package com.rea.simulation.model;

/**
 * Orientation enumerable. Dictates the allowable orientations for an entity. The constructor
 * orientation index is important; the enum ordinal is not.
 */
public enum Orientation {
  NORTH(0),
  EAST(1),
  SOUTH(2),
  WEST(3);

  private final int index; // explicit orientation index

  Orientation(final int index) {
    this.index = index;
  }

  final public int index() {
    return index;
  }
}
