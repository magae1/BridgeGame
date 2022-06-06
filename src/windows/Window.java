/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package windows;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private long window;
    private int width, height;
    private KeyMouseHandler keyMouseHandler;
    public static void setCallbacks() {
        GLFW.glfwSetErrorCallback(new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
            }
        });
    }
    public Window() {
        this(640, 480);
    }
    public Window(int width, int height) {
        setSize(width, height);
    }

    public void createWindow(String windowTitle) throws IllegalStateException {

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(width, height, windowTitle, 0, 0);

        if (window == 0)
            throw new IllegalStateException("Error!!: window doesn't created.");

        GLFW.glfwMakeContextCurrent(window);

        keyMouseHandler = new KeyMouseHandler(window);

        GL.createCapabilities();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        GLFW.glfwShowWindow(window);
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
    }
    public void swapBuffer() {
        GLFW.glfwSwapBuffers(window);
    }
    public void update() {
        keyMouseHandler.update();
        GLFW.glfwPollEvents();
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }
    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public long getWindow() {
        return window;
    }
    public KeyMouseHandler getKeyMouseHandler() {
        return keyMouseHandler;
    }
}
