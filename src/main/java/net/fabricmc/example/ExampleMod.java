package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class ExampleMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;
            for (Entity target : client.world.getEntities()) {
                if (target instanceof LivingEntity && target != client.player) {
                    if (client.player.distanceTo(target) < 4.0 && ((LivingEntity) target).isAlive()) {
                        client.interactionManager.attackEntity(client.player, target);
                        client.player.swingHand(Hand.MAIN_HAND);
                        break; 
                    }
                }
            }
            for (Entity e : client.world.getEntities()) {
                if (e != client.player) e.setGlowing(true);
            }
        });
    }
}
