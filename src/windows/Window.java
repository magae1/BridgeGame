/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package windows;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private long window;
    private int width, height;

    public Window() {
        this(640, 480);
    }
    public Window(int width, int height) {
        setSize(width, height);
    }

    public void createWindow(String windowTitle) throws IllegalStateException {
        if (!glfwInit())
            throw new IllegalStateException("Error!!: GLFW.glfwinit() doesn't work.");

        window = glfwCreateWindow(width, height, windowTitle, 0,0);

        if (window == 0)
            throw new IllegalStateException("Error!!: Window doesn't created.");

        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
    }
    public void swapBuffer() {
        glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(window);
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
}
