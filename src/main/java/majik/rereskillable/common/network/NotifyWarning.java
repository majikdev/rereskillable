package majik.rereskillable.common.network;

import majik.rereskillable.Rereskillable;
import majik.rereskillable.client.Overlay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class NotifyWarning
{
    private final ResourceLocation resource;
    
    public NotifyWarning(ResourceLocation resource)
    {
        this.resource = resource;
    }
    
    public NotifyWarning(FriendlyByteBuf buffer)
    {
        resource = buffer.readResourceLocation();
    }
    
    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeResourceLocation(resource);
    }
    
    public void handle(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> Overlay.showWarning(resource));
        context.get().setPacketHandled(true);
    }
    
    // Send Packet
    
    public static void send(Player player, ResourceLocation resource)
    {
        Rereskillable.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new NotifyWarning(resource));
    }
}