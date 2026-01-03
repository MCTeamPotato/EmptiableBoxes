package me.kall.emptiableboxes.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow @Final public List<Renderable> renderables;

    @Inject(method = "render", at = @At("HEAD"))
    private void clear(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        long window = Minecraft.getInstance().getWindow().getWindow();
        boolean stateLeftShift = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS;
        boolean stateRightShift = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
        boolean stateRightMouse = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

        if (stateRightMouse && (stateRightShift || stateLeftShift)) {
            for (Renderable renderable : this.renderables) {
                if (renderable instanceof EditBox) {
                    ((EditBox)renderable).setValue("");
                }
            }
        }
    }
}
