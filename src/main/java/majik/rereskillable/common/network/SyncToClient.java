package majik.rereskillable.common.network;

import majik.rereskillable.Rereskillable;
import majik.rereskillable.common.capabilities.SkillModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class SyncToClient
{
    private final CompoundTag skillModel;
    
    public SyncToClient(CompoundTag skillModel)
    {
        this.skillModel = skillModel;
    }
    
    public SyncToClient(FriendlyByteBuf buffer)
    {
        skillModel = buffer.readNbt();
    }
    
    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeNbt(skillModel);
    }
    
    public void handle(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> SkillModel.get().deserializeNBT(skillModel));
        context.get().setPacketHandled(true);
    }
    
    // Send Packet
    
    public static void send(Player player)
    {
        Rereskillable.NETWORK.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new SyncToClient(SkillModel.get(player).serializeNBT()));
    }
}