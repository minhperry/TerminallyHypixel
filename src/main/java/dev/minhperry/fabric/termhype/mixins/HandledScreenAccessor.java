package dev.minhperry.fabric.termhype.mixins;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HandledScreen.class)
public interface HandledScreenAccessor {
    @Accessor("x")
    int getX();

    @Accessor("y")
    int getY();

    @Accessor
    int getBackgroundWidth();

    @Accessor
    int getBackgroundHeight();

    @Mutable
    @Accessor("handler")
    void setHandler(ScreenHandler handler);

    @Accessor
    Slot getFocusedSlot();
}
