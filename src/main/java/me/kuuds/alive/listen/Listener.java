package me.kuuds.alive.listen;

import java.time.Duration;

public interface Listener {

  static Listener listener(Duration duration){
    return new ListenerImpl(duration);
  }

  Listener register(EventFactory factory);

  Listener listen();
}
