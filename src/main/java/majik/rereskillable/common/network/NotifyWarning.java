package majik.rereskillable.common.network;

import majik.rereskillable.Rereskillable;
import majik.rereskillable.client.Overlay;
import majik.rereskillable.common.skills.RequirementType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class NotifyWarning
{
    private final ResourceLocation resource;
    private final RequirementType type;

    public NotifyWarning(ResourceLocation resource, RequirementType type)
    {
        this.resource = resource;
        this.type = type;
    }
    
    public NotifyWarning(FriendlyByteBuf buffer)
    {
        resource = buffer.readResourceLocation();
        type = buffer.readEnum(RequirementType.class);
    }
    
    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeResourceLocation(resource);
        buffer.writeEnum(this.type);
    }
    
    public void handle(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> Overlay.showWarning(resource, type));
        context.get().setPacketHandled(true);
    }
    
    // Send Packet
    
    public static void send(Player player, ResourceLocation resource, RequirementType type)
    {
        Rereskillable.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new NotifyWarning(resource, type));
    }
}