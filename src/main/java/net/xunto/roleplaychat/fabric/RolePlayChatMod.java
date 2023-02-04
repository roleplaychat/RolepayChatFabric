package net.xunto.roleplaychat.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.util.registry.RegistryKey;
import net.xunto.roleplaychat.RoleplayChatCore;
import net.xunto.roleplaychat.fabric.adapters.FabricSpeaker;
import net.xunto.roleplaychat.fabric.framework.FabricRequest;
import net.xunto.roleplaychat.framework.api.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolePlayChatMod implements ModInitializer {

  public static final Logger LOGGER = LoggerFactory.getLogger("roleplaychat");

  @Override
  public void onInitialize() {
    LOGGER.info("Hello Fabric world!");

    RoleplayChatCore.instance.warmUpRenderer();

    ServerMessageEvents.ALLOW_CHAT_MESSAGE.register(
        (message, sender, typeKey) -> {

          RoleplayChatCore.instance.process(
              new FabricRequest(
                  message.getContent().getString(),
                  new FabricSpeaker(sender),
                  message,
                  typeKey
              )
          );

          return false;
        }
    );
  }
}
