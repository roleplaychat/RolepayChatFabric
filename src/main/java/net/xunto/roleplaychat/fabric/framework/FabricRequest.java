package net.xunto.roleplaychat.fabric.framework;

import net.minecraft.network.message.MessageType.Parameters;
import net.minecraft.network.message.SignedMessage;
import net.xunto.roleplaychat.fabric.adapters.FabricSpeaker;
import net.xunto.roleplaychat.framework.api.Request;

public class FabricRequest extends Request {

  private final SignedMessage message;
  private final Parameters typeKey;

  public FabricRequest(String string, FabricSpeaker requester, SignedMessage message,
      Parameters typeKey) {
    super(string, requester);
    this.message = message;
    this.typeKey = typeKey;
  }

  public SignedMessage getMessage() {
    return message;
  }

  public Parameters getTypeKey() {
    return typeKey;
  }
}
