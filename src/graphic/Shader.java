/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package graphic;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int program;
    private int vs, fs;

    public Shader(String filename) {
        program = glCreateProgram();

        vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs, readFile(filename+".vs"));
        glCompileShader(vs);
        if (glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(vs));
            System.exit(1);
        }

        fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs, readFile(filename+".fs"));
        glCompileShader(fs);
        if (glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(fs));
            System.exit(1);
        }

        glAttachShader(program, vs);
        glAttachShader(program, fs);

        glBindAttribLocation(program, 0,"vertices");
        glBindAttribLocation(program, 1, "textures");

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
    }
    public void bind() {
        glUseProgram(program);
    }
    public void setUniform(String uniformName, int value) {
        int location = glGetUniformLocation(program, uniformName);
        if (location != -1) {
            glUniform1i(location, value);
        }
    }
    public void setUniform(String uniformName, Vector4f value) {
        int location = glGetUniformLocation(program, uniformName);
        if (location != -1) glUniform4f(location, value.x, value.y, value.z, value.w);
    }
    public void setUniform(String uniformName, Matrix4f value) {
        int location = glGetUniformLocation(program, uniformName);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        if (location != -1) {
            glUniformMatrix4fv(location, false, buffer);
        }
    }
    private String readFile(String filename) {
        StringBuilder string = new StringBuilder();
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(new File("src/graphic/shaders/" + filename)));
            String line;
            while((line = br.readLine()) != null){
                   string.append(line);
                   string.append("\n");
            }
            br.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return string.toString();
    }
    protected void finalize() {
        glDetachShader(program, vs);
        glDetachShader(program, fs);
        glDeleteShader(vs);
        glDeleteShader(fs);
        glDeleteProgram(program);
    }
}
