package com.exemplo.nightvision;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class NightVisionMod implements ClientModInitializer {

    private static KeyBinding keyBinding;
    private boolean nightVisionActive = false;

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.nightvision.toggle", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_G, 
                "category.nightvision"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                if (client.player != null) {
                    nightVisionActive = !nightVisionActive;

                    if (nightVisionActive) {
                        client.player.addStatusEffect(new StatusEffectInstance(
                                StatusEffects.NIGHT_VISION, StatusEffectInstance.INFINITE, 0, false, false
                        ));
                    } else {
                        client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                    }

                    client.player.sendMessage(Text.literal("Night vision toggled!"), true);
                }
            }
        });
    }
}
