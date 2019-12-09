package me.kuuds.alive.listen;


public class DefaultEventFactory extends EventFactory{
  @Override
  public boolean active() {
    return true;
  }

  @Override
  public Event event() {
    return null;
  }
}
