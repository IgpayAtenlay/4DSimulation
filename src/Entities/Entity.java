package Entities;

import Data.Dimention;
import Data.RotationDirection;

public abstract class Entity {
    public abstract void move(int distance, Dimention direction);
    public abstract void turn(double degree, RotationDirection direction);
}
