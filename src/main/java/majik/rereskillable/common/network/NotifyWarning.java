package majik.rereskillable.common.network;

import majik.rereskillable.Rereskillable;
import majik.rereskillable.client.Overlay;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
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
    
    public NotifyWarning(PacketBuffer buffer)
    {
        resource = buffer.readResourceLocation();
    }
    
    public void encode(PacketBuffer buffer)
    {
        buffer.writeResourceLocation(resource);
    }
    
    public void handle(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> Overlay.showWarning(resource));
        context.get().setPacketHandled(true);
    }
    
    // Send Packet
    
    public static void send(PlayerEntity player, ResourceLocation resource)
    {
        Rereskillable.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new NotifyWarning(resource));
    }
}