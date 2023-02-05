package net.xunto.roleplaychat.fabric.adapters;

import java.util.Objects;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.api.IWorld;
import net.xunto.roleplaychat.api.Position;
import net.xunto.roleplaychat.fabric.FabricComponents;
import net.xunto.roleplaychat.framework.api.Request;
import net.xunto.roleplaychat.framework.text.Text;


public class FabricEntitySpeaker implements ISpeaker {

  private final Entity entity;

  public FabricEntitySpeaker(Entity entity) {
    this.entity = entity;
  }

  @Override
  public String getName() {
    return entity.getDisplayName().getString();
  }

  @Override
  public String getRealName() {
    return entity.getName().getString();
  }

  @Override
  public Position getPosition() {
    BlockPos position = entity.getBlockPos();
    return new Position(position.getX(), position.getY(), position.getZ());
  }

  @Override
  public IWorld getWorld() {
    return new FabricWorld((ServerWorld) entity.getEntityWorld());
  }

  @Override
  public UUID getUniqueID() {
    return entity.getUuid();
  }

  @Override
  public void sendMessage(Request request, Text text) {
    entity.sendMessage(FabricComponents.toTextComponent(text));
  }

  @Override
  public boolean hasPermission(String permission) {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FabricEntitySpeaker that)) {
      return false;
    }
    return Objects.equals(entity, that.entity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entity);
  }
}