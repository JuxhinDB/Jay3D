package org.jay3d.engine.rendering.mesh;

import org.jay3d.engine.core.math.Vector2f;
import org.jay3d.engine.core.math.Vector3f;
import org.jay3d.util.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Juxhin on 27/09/14.
 * Do not distribute without permission
 */
public class OBJModel {
    private ArrayList<Vector3f> positions;
    private ArrayList<Vector2f> texCoords;
    private ArrayList<Vector3f> normals;
    private ArrayList<OBJIndex> indices;
    private boolean hasTexCoords;
    private boolean hasNormals;

    public OBJModel(String fileName) {
        positions = new ArrayList<>();
        texCoords = new ArrayList<>();
        normals = new ArrayList<>();
        indices = new ArrayList<>();
        hasTexCoords = false;
        hasNormals = false;

        BufferedReader meshReader;

        try{
            meshReader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = meshReader.readLine()) != null){
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);
                if(tokens.length == 0 || tokens[0].equalsIgnoreCase("#"))
                    continue;
                else if(tokens[0].equalsIgnoreCase("v")){
                    positions.add(new Vector3f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3])));
                }else if(tokens[0].equals("vt")){
                    texCoords.add(new Vector2f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2])));
                }else if(tokens[0].equals("vn")){
                    normals.add(new Vector3f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3])));
                }
                else if(tokens[0].equalsIgnoreCase("f")){
                    for(int i = 0; i < tokens.length - 3; i++) {
                        indices.add(parseOBJIndex(tokens[1]));
                        indices.add(parseOBJIndex(tokens[2 + i]));
                        indices.add(parseOBJIndex(tokens[3 + i]));
                    }
                }
            }
            meshReader.close();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public IndexedModel toIndexedModel(){
        IndexedModel result = new IndexedModel();
        HashMap<Integer, Integer> indexMap = new HashMap<>();

        int currentVertexIndex = 0;
        for (int i = 0; i < indices.size(); i++) {
            OBJIndex currentIndex = indices.get(i);

            Vector3f currentPos = positions.get(currentIndex.vertexIndex);
            Vector2f currentTexCoord;
            Vector3f currentNormal;

            if (hasTexCoords)
                currentTexCoord = texCoords.get(currentIndex.texCoordIndex);
            else
                currentTexCoord = new Vector2f(0, 0);

            if (hasNormals)
                currentNormal = normals.get(currentIndex.normalIndex);
            else
                currentNormal = new Vector3f(0, 0, 0);

            int previousVertexIndex = -1;

            for (int j = 0; j < i; j++) {
                OBJIndex oldIndex = indices.get(j);

                if (currentIndex.vertexIndex == oldIndex.vertexIndex
                        && currentIndex.texCoordIndex == oldIndex.texCoordIndex
                        && currentIndex.normalIndex == oldIndex.normalIndex) {
                    previousVertexIndex = j;
                    break;
                }
            }

            if (previousVertexIndex == -1) {
                indexMap.put(i, currentVertexIndex);

                result.getPositions().add(currentPos);
                result.getTextCoordinates().add(currentTexCoord);
                result.getNormals().add(currentNormal);
                System.out.println(currentVertexIndex);
                result.getIndices().add(currentVertexIndex);
                currentVertexIndex++;
            } else
                result.getIndices().add(indexMap.get(previousVertexIndex));
        }

        return result;
    }

    private OBJIndex parseOBJIndex(String token){
        String[] values = token.split("/");

        OBJIndex result = new OBJIndex();
        result.vertexIndex = Integer.parseInt(values[0].split("/")[0]) - 1;

        if(values.length > 1){
            hasTexCoords = true;
            result.texCoordIndex = Integer.parseInt(values[1].split("/")[0]) - 1;

            if(values.length > 2){
                hasNormals = true;
                result.normalIndex = Integer.parseInt(values[2].split("/")[0]) - 1;
            }
        }

        return result;
    }
}
