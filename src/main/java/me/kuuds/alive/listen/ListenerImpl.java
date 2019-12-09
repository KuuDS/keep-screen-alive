package me.kuuds.alive.listen;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ListenerImpl implements Listener {

  private  ScheduledExecutorService executorService;
  private List<EventFactory> eventFactories;
  private  Duration loopDuration;
  private Robot robot;

  public ListenerImpl(Duration loopDuration) {
    try {
      executorService = Executors.newSingleThreadScheduledExecutor();
      log.debug("loop duration is [{}]s.", loopDuration.getSeconds());
      this.loopDuration = loopDuration;
      eventFactories = new LinkedList<>();
      robot = new Robot();
    } catch (AWTException e) {
      log.error("fail to get robot.", e);
      System.exit(1);
    }
    Runtime.getRuntime().addShutdownHook(new Thread(() -> executorService.shutdown()));
  }

  @Override
  public Listener register(EventFactory factory) {
    log.debug("add event factory [{}].", factory.getClass().getTypeName());
    eventFactories.add(factory);
    return this;
  }

  @Override
  public Listener listen() {
    executorService.scheduleAtFixedRate(
      () -> {
        log.debug("start to keepalive.");
        boolean result = false;
        for (EventFactory eventFactory : eventFactories) {
          result = result || eventFactory.active();
        }
        if (result) {
          log.info("need keep alive.");
          keepAlive();
        }
      },
      10, loopDuration.getSeconds(), TimeUnit.SECONDS);
    return null;
  }

  void keepAlive() {
    log.debug("press num lock 1 time.");
    robot.keyPress(KeyEvent.VK_NUM_LOCK);

    log.debug("press num lock 2 times.");
    robot.keyPress(KeyEvent.VK_NUM_LOCK);
  }
}
