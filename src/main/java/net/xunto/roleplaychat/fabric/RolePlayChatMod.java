package net.xunto.roleplaychat.fabric;

import static net.minecraft.util.registry.BuiltinRegistries.MESSAGE_TYPE;

import net.fabricmc.api.ModInitializer;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Decoration;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.xunto.roleplaychat.RoleplayChatCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RolePlayChatMod implements ModInitializer {

  public static final Logger LOGGER = LoggerFactory.getLogger("roleplaychat");

  public static final RegistryKey<MessageType> ROLEPLAYCHAT = RegistryKey.of(
      Registry.MESSAGE_TYPE_KEY,
      new Identifier("roleplaychat")
  );

  public void registerMessageType() {
    BuiltinRegistries.add(MESSAGE_TYPE,
        ROLEPLAYCHAT, new MessageType(
            Decoration.ofChat("%s%s"),
            Decoration.ofChat("%s%s")
        )
    );
  }

  @Override
  public void onInitialize() {
    RoleplayChatCore.instance.warmUpRenderer();

    this.registerMessageType();
  }
}
