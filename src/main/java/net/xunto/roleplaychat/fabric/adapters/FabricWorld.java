package net.xunto.roleplaychat.fabric.adapters;

import net.minecraft.world.World;
import net.xunto.roleplaychat.api.IServer;
import net.xunto.roleplaychat.api.ISpeaker;
import net.xunto.roleplaychat.api.IWorld;

public class FabricWorld implements IWorld {

  private final World world;

  public FabricWorld(World world) {
    this.world = world;
  }

  @Override
  public IServer getServer() {
    return new FabricServer(world.getServer());
  }

  @Override
  public ISpeaker[] getPlayers() {
    return world.getPlayers().stream().map(FabricSpeaker::new).toArray(ISpeaker[]::new);
  }
}
