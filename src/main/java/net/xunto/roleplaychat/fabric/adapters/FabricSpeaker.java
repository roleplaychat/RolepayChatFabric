package net.xunto.roleplaychat.fabric.adapters;

import java.util.Objects;
import net.minecraft.server.network.ServerPlayerEntity;

public class FabricSpeaker extends FabricEntitySpeaker {

  private final ServerPlayerEntity player;

  public FabricSpeaker(ServerPlayerEntity player) {
    super(player);
    this.player = player;
  }

  //    @Override
//    public boolean hasPermission(String permission) {
//        return PermissionAPI.hasPermission(this.player, permission);
//    }

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