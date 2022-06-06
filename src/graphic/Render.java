package graphic;

import graphic.rendertools.Shader;

public class Render {

    Shader shader;

    Render() {
        shader = new Shader("shader");
    }

    void render() {
        shader.bind();
        shader.setUniform("sampler", 0);

    }

}
