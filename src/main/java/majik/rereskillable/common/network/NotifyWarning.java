package majik.rereskillable.common.network;

import majik.rereskillable.Rereskillable;
import majik.rereskillable.client.Overlay;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class NotifyWarning
{
    private final ItemStack item;
    
    public NotifyWarning(ItemStack item)
    {
        this.item = item;
    }
    
    public NotifyWarning(PacketBuffer buffer)
    {
        item = buffer.readItem();
    }
    
    public void encode(PacketBuffer buffer)
    {
        buffer.writeItem(item);
    }
    
    public void handle(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> Overlay.showWarning(item));
        context.get().setPacketHandled(true);
    }
    
    // Send Packet
    
    public static void send(PlayerEntity player, ItemStack item)
    {
        Rereskillable.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new NotifyWarning(item));
    }
}