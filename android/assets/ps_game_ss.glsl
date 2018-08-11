#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
uniform sampler2D u_lightTexture;

void main() {
    vec4 light = texture2D(u_lightTexture, v_texCoords);
    vec4 color = texture2D(u_texture, v_texCoords);

    gl_FragColor = light * color;
}

