package Entities;

import Data.Dimention;

public abstract class Entity {
    public abstract void move(int distance, Dimention direction);
    public abstract void turn(double degree, Dimention direction);
}
