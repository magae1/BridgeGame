package windows;/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */

import org.lwjgl.BufferUtils;
import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

public class KeyMouseHandler {
    private long window;
    private boolean[] mouseButtons;
    private boolean[] keys;
    public KeyMouseHandler(long window) {
        this.window = window;
        mouseButtons = new boolean[GLFW_MOUSE_BUTTON_LAST];
        keys = new boolean[GLFW_KEY_LAST];
    }
    private KeyMouseHandler() {

    }
    public void update() {
        for (int i = 0; i < GLFW_KEY_LAST; i++)
            keys[i] = isKeyDown(i);
        for (int i = 0; i < GLFW_MOUSE_BUTTON_LAST; i++)
            mouseButtons[i] = isMouseButtonDown(i);
    }
    public boolean isKeyPressed(int key) {
        return isKeyDown(key) && !keys[key];
    }
    public boolean isMousePressed (int mouseButton) {
        return isMouseButtonDown(mouseButton) && !mouseButtons[mouseButton];
    }
    public double getMousePressedX() {
        if(isMousePressed(GLFW_MOUSE_BUTTON_LEFT)){
            return getMouseX();
        }
        else{
            return 0;
        }
    }
    public double getMousePressedY(){
        if(isMousePressed(GLFW_MOUSE_BUTTON_LEFT) && !isMouseReleased(GLFW_MOUSE_BUTTON_LEFT)){
            return getMouseY();
        }
        else {
            return 0;
        }
    }
    private boolean isKeyDown(int key) {
        return glfwGetKey(window, key) == 1;
    }
    private boolean isKeyReleased (int key) {
        return !isKeyDown(key) && keys[key];
    }
    private boolean isMouseButtonDown(int mouseButton) {
        return glfwGetMouseButton(window, mouseButton) == 1;
    }
    private boolean isMouseReleased(int mouseButton) {
        return !isMouseButtonDown(mouseButton) && mouseButtons[mouseButton];
    }
    private double getMouseX() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, buffer, null);
        return buffer.get(0);
    }
    private double getMouseY() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(window, null, buffer);
        return buffer.get(0);
    }
}