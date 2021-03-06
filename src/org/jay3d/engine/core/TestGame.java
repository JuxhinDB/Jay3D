package org.jay3d.engine.core;

import org.jay3d.engine.core.components.*;
import org.jay3d.engine.core.math.Attenuation;
import org.jay3d.engine.core.math.Quaternion;
import org.jay3d.engine.core.math.Vector2f;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.engine.rendering.*;
import org.jay3d.engine.rendering.Material;
import org.jay3d.engine.rendering.Mesh;

/**
 * Please do note that this is just a temporary class to display some parts of the rendering engine.
 *
 * @author Juxhin Dyrmishi Brigjaj
 */
public class TestGame extends Game {

    public void init() {
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        Vertex[] vertices = new Vertex[]{new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2f(1.0f, 1.0f))};

        Vertex[] vertices2 = new Vertex[]{new Vertex(new Vector3f(-fieldWidth /10 , 0.0f, -fieldDepth /10 ), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-fieldWidth  /10 , 0.0f, fieldDepth /10 * 3), new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(fieldWidth  /10 * 3, 0.0f, -fieldDepth /10 ), new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(fieldWidth  /10 * 3, 0.0f, fieldDepth /10 * 3), new Vector2f(1.0f, 1.0f))};


        int indices[] = {0, 1, 2,
                2, 1, 3};

        int indices2[] = {0, 1, 2,
                2, 1, 3};

        Mesh mesh2 = new Mesh(vertices2, indices2);

        Mesh mesh = new Mesh(vertices, indices, true);
        Material material = new Material();
        material.addTexture( "diffuse" , new Texture("bricks.jpg"));
        material.addFloat( "specularIntensity", 1f);
        material.addFloat( "specularPower", 8f);

        Material material2 = new Material();
        material2.addTexture( "diffuse" , new Texture("test.png"));
        material2.addFloat( "specularIntensity", 1f);
        material2.addFloat( "specularPower", 8f);

        Material material3 = new Material();
        material3.addTexture( "diffuse" , new Texture("bricks3.jpg"));
        material3.addFloat( "specularIntensity", 1f);
        material3.addFloat( "specularPower", 8f);

        Mesh tempMesh = new Mesh("monkey3.obj");

        MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
        MeshRenderer meshRenderer1 = new MeshRenderer(mesh, material3);

        GameObject planeObject = new GameObject();
        planeObject.addComponent(meshRenderer1);
        planeObject.getTransform().setScale(new Vector3f(0.75f, 0.75f, 0.75f));
        planeObject.getTransform().getPos().set(0, -1, 5);

        GameObject directionalLightObject = new GameObject();
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.4f);
        directionalLightObject.addComponent(directionalLight);

        GameObject pointLightObject = new GameObject();
        PointLight pointLight = new PointLight(new Vector3f(0.5f, 0.5f, 0.5f), 0.6f, new Attenuation(0, 0, 1));
        pointLightObject.addComponent(pointLight);


        SpotLight spotLight = new SpotLight(new Vector3f(0,1,1), 0.4f,
                new Attenuation(0,0,0.1f), 0.7f);


        GameObject spotLightObject = new GameObject();
        spotLightObject.addComponent(spotLight);

        spotLightObject.getTransform().getPos().set(5, 0, 5);
        spotLightObject.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float)Math.toRadians(90.0f)));

        addObject(planeObject);
        addObject(directionalLightObject);
        addObject(pointLightObject);
        addObject(spotLightObject);

        GameObject meshObject1 = new GameObject();
        GameObject meshObject2 = new GameObject();
        GameObject meshObject3 = new GameObject().addComponent(new LookAtComponent())
                .addComponent(new MeshRenderer(tempMesh, material));
        GameObject meshObject4 = new GameObject().addComponent(new LookAtComponent())
                .addComponent(new MeshRenderer(tempMesh, material));
        GameObject meshObject5 = new GameObject().addComponent(new LookAtComponent())
                .addComponent(new MeshRenderer(tempMesh, material));

        meshObject1.getTransform().getPos().set(0, 2, 0);
        meshObject1.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), 0.4f));
        meshObject2.getTransform().getPos().set(0, 0, 5);

        meshObject1.addChild(meshObject2);
        meshObject2.addChild(new GameObject().addComponent(new FreeLook(0.125f)).addComponent(new FreeMove(10f)).addComponent(new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f)));

        addObject(meshObject1);
        addObject(meshObject3);
        addObject(meshObject4);
        addObject(meshObject5);

        meshObject3.getTransform().getPos().set(5, 1, 15);
        meshObject3.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float)Math.toRadians(-30)));

        meshObject4.getTransform().getPos().set(9, 1, 15);
        meshObject4.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float)Math.toRadians(-30)));

        meshObject5.getTransform().getPos().set(13, 1, 15);
        meshObject5.getTransform().setRot(new Quaternion(new Vector3f(0, 1, 0), (float)Math.toRadians(-30)));

        addObject(new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), material2)));

        directionalLight.getTransform().setRot(new Quaternion(new Vector3f(1, 0, 0), (float)Math.toRadians(-45)));
    }
}
