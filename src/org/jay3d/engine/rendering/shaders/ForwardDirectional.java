package org.jay3d.engine.rendering.shaders;

import org.jay3d.engine.core.math.Matrix4f;
import org.jay3d.engine.core.math.Transform;
import org.jay3d.engine.core.components.BaseLight;
import org.jay3d.engine.core.components.DirectionalLight;
import org.jay3d.engine.rendering.RenderingEngine;
import org.jay3d.engine.rendering.material.Material;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class ForwardDirectional extends Shader{
    private static final ForwardDirectional INSTANCE = new ForwardDirectional();

    public static ForwardDirectional getInstance(){
        return INSTANCE;
    }
    public ForwardDirectional(){
        super();

        addVertexShaderFromFile("Forward-directional.vs");
        addFragmentShaderFromFile("Forward-directional.fs");

        setAttribLocation("positon", 0);
        setAttribLocation("texCoord", 1);
        setAttribLocation("normal", 2);

        compileShader();

        addUniform("model");
        addUniform("mvp");

        addUniform("specularIntensity");
        addUniform("specularPower");
        addUniform("eyePos");

        addUniform("directionalLight.base.colour");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine){
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture("diffuseTexture").bind();

        setUniform("model", worldMatrix);
        setUniform("mvp", projectedMatrix);

        setUniformf("specularIntensity", material.getFloat("specularIntensity"));
        setUniformf("specularPower", material.getFloat("specularPower"));

        setUniform("eyePos", renderingEngine.getMainCamera().getTransform().getTranformedPos());

        setUniformDirectionalLight("directionalLight", (DirectionalLight) renderingEngine.getActiveLight());
    }

    public void setUniformBaseLight(String uniformName, BaseLight baseLight){
        setUniform(uniformName + ".colour", baseLight.getColour());
        setUniformf(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight){
        setUniformBaseLight(uniformName + ".base", directionalLight);
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }
}
