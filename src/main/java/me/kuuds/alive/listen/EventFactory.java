package me.kuuds.alive.listen;

import java.awt.*;
import java.util.concurrent.Callable;

public abstract class EventFactory {

  private Robot robot;
  public abstract boolean active();

  public abstract Event event();

  public void setRobot(Robot robot){
    robot = robot;
  }

}
