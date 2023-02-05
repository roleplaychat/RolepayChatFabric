package net.xunto.roleplaychat.fabric;

import net.fabricmc.api.ModInitializer;
import net.xunto.roleplaychat.RoleplayChatCore;
import net.xunto.roleplaychat.fabric.adapters.FabricLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolePlayChatMod implements ModInitializer {

  public static final Logger LOGGER = LoggerFactory.getLogger("roleplaychat");
  public static final FabricLogger FABRIC_LOGGER = new FabricLogger(LOGGER);

  @Override
  public void onInitialize() {
    RoleplayChatCore.instance.warmUpRenderer();
    RoleplayChatCore.instance.setLogger(FABRIC_LOGGER);
  }
}
