package net.xunto.roleplaychat.fabric.adapters;

import java.util.Objects;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.MessageType.Parameters;
import net.minecraft.network.message.SentMessage;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.xunto.roleplaychat.fabric.FabricComponents;
import net.xunto.roleplaychat.fabric.RolePlayChatMod;
import net.xunto.roleplaychat.fabric.framework.FabricRequest;
import net.xunto.roleplaychat.framework.api.Request;
import net.xunto.roleplaychat.framework.text.Text;

public class FabricSpeaker extends FabricEntitySpeaker {

  private final ServerPlayerEntity player;

  public FabricSpeaker(ServerPlayerEntity player) {
    super(player);
    this.player = player;
  }

  @Override
  public void sendMessage(Request request, Text text) {
    if (!(request instanceof FabricRequest fabricRequest)) {
      super.sendMessage(request, text);
    } else {
      // Make signed content
      SignedMessage signedMessage = fabricRequest.getMessage()
          .withUnsignedContent(
              FabricComponents.toTextComponent(text)
          );

      // Build parameters for new message
      Parameters params = MessageType.params(
          RolePlayChatMod.ROLEPLAYCHAT,
          player.world.getRegistryManager(),
          net.minecraft.text.Text.literal("")
      );

      player.sendChatMessage(
          SentMessage.of(signedMessage),
          false,
          params
      );
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FabricSpeaker that)) {
      return false;
    }
    return Objects.equals(player, that.player);
  }
}