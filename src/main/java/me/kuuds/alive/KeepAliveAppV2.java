/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.kuuds.alive;

import lombok.extern.slf4j.Slf4j;
import me.kuuds.alive.listen.DefaultEventFactory;
import me.kuuds.alive.listen.Listener;
import me.kuuds.alive.ui.TrayBuilder;

import java.time.Duration;

/**
 * @author kuuds
 * @since 0.0.1
 */
@Slf4j
public class KeepAliveAppV2 {

  private static Duration keepAlivePeriod;
  private final static Duration DEFAULT_KEEP_ALIVE_PERIOD = Duration.ofMinutes(10);

  public static void main(String[] args) {
    for (int i = 0; i < args.length; ++i) {
      String param = args[i];
      try {
        switch (param) {
          case "--period":
            keepAlivePeriod = Duration.ofSeconds(Integer.parseInt(args[++i]));
            break;
        }
      } catch (NumberFormatException e) {
        log.error("illegal arguments [{}], value [{}].", args[i-1], args[i], e);
      } catch (IndexOutOfBoundsException e) {
        log.error("illegal arguments [{}], value null", args[i-1], e);
      }
    }

    log.info("start tray");
    TrayBuilder.build();

    log.info("start listener.");
    Listener listener = Listener.listener(keepAlivePeriod == null ? DEFAULT_KEEP_ALIVE_PERIOD : keepAlivePeriod);
    listener.register(new DefaultEventFactory());
    listener.listen();
  }
}
