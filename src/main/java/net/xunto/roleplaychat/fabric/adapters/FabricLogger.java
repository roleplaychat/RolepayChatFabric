package net.xunto.roleplaychat.fabric.adapters;

import net.xunto.roleplaychat.api.ILogger;
import net.xunto.roleplaychat.framework.text.Text;
import org.slf4j.Logger;

public class FabricLogger implements ILogger {

  private final Logger logger;

  public FabricLogger(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void log(Text text) {
    this.logger.info(text.getUnformattedText());
  }
}
