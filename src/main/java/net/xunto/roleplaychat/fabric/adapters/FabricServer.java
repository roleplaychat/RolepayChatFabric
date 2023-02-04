package net.xunto.roleplaychat.fabric.adapters;

import com.google.common.collect.Streams;
import net.minecraft.server.MinecraftServer;
import net.xunto.roleplaychat.api.IServer;

public class FabricServer  implements IServer {
    private final MinecraftServer server;

    public FabricServer(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public FabricWorld[] getWorlds() {
        return Streams.stream(server.getWorlds()).map(FabricWorld::new).toArray(FabricWorld[]::new);
    }
}