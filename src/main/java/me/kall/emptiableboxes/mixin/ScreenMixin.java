package me.kall.emptiableboxes.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
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
    @Shadow @Final public List<Widget> renderables;

    @Inject(method = "render", at = @At("HEAD"))
    private void clear(PoseStack poseStack, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        long window = Minecraft.getInstance().getWindow().getWindow();
        boolean stateLeftShift = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS;
        boolean stateRightShift = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
        boolean stateRightMouse = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

        if (stateRightMouse && (stateRightShift || stateLeftShift)) {
            for (Widget renderable : this.renderables) {
                if (renderable instanceof EditBox) {
                    ((EditBox)renderable).setValue("");
                }
            }
        }
    }
}
