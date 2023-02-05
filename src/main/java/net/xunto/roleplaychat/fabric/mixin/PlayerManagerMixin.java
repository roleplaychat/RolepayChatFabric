package net.xunto.roleplaychat.fabric.mixin;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.xunto.roleplaychat.RoleplayChatCore;
import net.xunto.roleplaychat.fabric.FabricComponents;
import net.xunto.roleplaychat.fabric.adapters.FabricSpeaker;
import net.xunto.roleplaychat.fabric.framework.FabricRequest;
import net.xunto.roleplaychat.framework.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerManager.class, priority = 999)
public abstract class PlayerManagerMixin {

  @Shadow
  public abstract void sendMessageHeader(SignedMessage message, Set<ServerPlayerEntity> except);

  @Inject(
      method = "broadcast(Lnet/minecraft/network/message/SignedMessage;Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/network/message/MessageType$Parameters;)V",
      at = @At("HEAD"),
      cancellable = true
  )
  private void onSendChatMessage(
      SignedMessage message,
      ServerPlayerEntity sender,
      MessageType.Parameters params,
      CallbackInfo ci
  ) {
    ci.cancel();

    // Check if chat message is allowed
    if (!ServerMessageEvents.ALLOW_CHAT_MESSAGE.invoker()
        .allowChatMessage(message, sender, params)) {
      // If the message header is not empty, send the header
      if (!message.headerSignature().isEmpty()) {
        sendMessageHeader(message, Collections.emptySet());
      }
      return;
    }

    // Pass the chat message to the RoleplayChatCore instance for processing
    List<Text> results = RoleplayChatCore.instance.process(
        new FabricRequest(
            message.getContent().getString(),
            new FabricSpeaker(sender),
            message,
            params
        )
    );

    for (Text result : results) {
      MutableText mutableText = FabricComponents.toTextComponent(result);
      ServerMessageEvents.CHAT_MESSAGE.invoker().onChatMessage(
          message.withUnsignedContent(mutableText),
          sender,
          params
      );
    }
  }
}
