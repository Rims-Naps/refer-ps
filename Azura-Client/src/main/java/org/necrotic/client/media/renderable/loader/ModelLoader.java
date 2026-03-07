package org.necrotic.client.media.renderable.loader;

import org.necrotic.client.media.renderable.Model;
import org.necrotic.client.net.Buffer;

public class ModelLoader {
    public static void decodeCustomOld(Model def, byte[] var1) {
        boolean var2 = false;
        boolean var3 = false;
        Buffer var4 = new Buffer(var1);
        Buffer var5 = new Buffer(var1);
        Buffer var6 = new Buffer(var1);
        Buffer var7 = new Buffer(var1);
        Buffer var8 = new Buffer(var1);
        var4.pos = var1.length - 23;
        def.verticesCount = var4.getUnsignedShort();
        def.trianglesCount = var4.getUnsignedShort();
        def.texturesCount = var4.getUnsignedByte();


        int renderTypeOpcode = var4.getUnsignedByte();
        int renderPriorityOpcode = var4.getUnsignedByte();
        int triangleAlphaOpcode = var4.getUnsignedByte();
        int triangleSkinOpcode = var4.getUnsignedByte();
        int vertexLabelOpcode = var4.getUnsignedByte();
        int verticesXCoordinateOffset = var4.getUnsignedShort();

        int verticesYCoordinateOffset = var4.getUnsignedShort();
        int verticesZCoordinateOffset = var4.getUnsignedShort();
        int triangleIndicesOffset = var4.getUnsignedShort();

        int particleCount = var4.getUnsignedShort();

        boolean hasParticles = particleCount > 0;


        int pos = 0;

        int vertexFlagOffset = pos;
        pos += def.verticesCount;

        int triangleCompressTypeOffset = pos;
        pos += def.trianglesCount;

        int facePriorityOffset = pos;
        if (renderPriorityOpcode == 255) {
            pos += def.trianglesCount;
        }

        int triangleSkinOffset = pos;
        if (triangleSkinOpcode == 1) {
            pos += def.trianglesCount;
        }

        int renderTypeOffset = pos;
        if (renderTypeOpcode == 1) {
            pos += def.trianglesCount;
        }

        int vertexLabelsOffset = pos;
        if (vertexLabelOpcode == 1) {
            pos += def.verticesCount;
        }

        int triangleAlphaOffset = pos;
        if (triangleAlphaOpcode == 1) {
            pos += def.trianglesCount;
        }

        int indicesOffset = pos;
        pos += triangleIndicesOffset;

        int triangleColorOffset = pos;
        pos += def.trianglesCount * 2;

        int textureOffset = pos;
        pos += def.texturesCount * 6;

        int xOffset = pos;
        pos += verticesXCoordinateOffset;

        int yOffset = pos;
        pos += verticesYCoordinateOffset;

        int zOffset = pos;
        def.verticesX = new int[def.verticesCount];
        def.verticesY = new int[def.verticesCount];
        def.verticesZ = new int[def.verticesCount];
        def.trianglesX = new int[def.trianglesCount];
        def.trianglesY = new int[def.trianglesCount];
        def.trianglesZ = new int[def.trianglesCount];
        if (def.texturesCount > 0) {
            // textureMap = new short[texturesCount];
            def.texturesX = new int[def.texturesCount];
            def.texturesY = new int[def.texturesCount];
            def.texturesZ = new int[def.texturesCount];
        }

        if (vertexLabelOpcode == 1)
            def.vertexData = new int[def.verticesCount];


        if (renderTypeOpcode == 1) {
            def.types = new int[def.trianglesCount];
            // faceTexture = new short[trianglesCount];
            // triangleMaterial = new int[trianglesCount];
            //faceTextureMasks = new byte[trianglesCount];
        }

        if (renderPriorityOpcode == 255)
            def.priorities = new int[def.trianglesCount];
        else
            def.priority = (byte) renderPriorityOpcode;

        if (triangleAlphaOpcode == 1)
            def.alphas = new int[def.trianglesCount];

        if (triangleSkinOpcode == 1)
            def.triangleLabels = new int[def.trianglesCount];

        def.colors = new int[def.trianglesCount];
        var4.pos = vertexFlagOffset;
        var5.pos = xOffset;
        var6.pos = yOffset;
        var7.pos = zOffset;
        var8.pos = vertexLabelsOffset;
        int baseX = 0;
        int baseY = 0;
        int baseZ = 0;

        for (int point = 0; point < def.verticesCount; point++) {
            int flag = var4.getUnsignedByte();

            int x = 0;
            if ((flag & 0x1) != 0) {
                x = var5.getSignedSmart();
            }

            int y = 0;
            if ((flag & 0x2) != 0) {
                y = var6.getSignedSmart();
            }
            int z = 0;
            if ((flag & 0x4) != 0) {
                z = var7.getSignedSmart();
            }

            def.verticesX[point] = baseX + x;
            def.verticesY[point] = baseY + y;
            def.verticesZ[point] = baseZ + z;
            baseX = def.verticesX[point];
            baseY = def.verticesY[point];
            baseZ = def.verticesZ[point];
            if (vertexLabelOpcode == 1) {
                def.vertexData[point] = var8.getUnsignedByte();
            }
        }


        var4.pos = triangleColorOffset;
        var5.pos = renderTypeOffset;
        var6.pos = facePriorityOffset;
        var7.pos = triangleAlphaOffset;
        var8.pos = triangleSkinOffset;

        for (int face = 0; face < def.trianglesCount; face++) {
            int color = var4.getUnsignedShort();
            def.colors[face] = (short) color;

            if (renderTypeOpcode == 1) {
                def.types[face] = var5.getUnsignedByte();
            }
            if (renderPriorityOpcode == 255) {
                def.priorities[face] = var6.getSignedByte();
            }

            if (triangleAlphaOpcode == 1) {
                def.alphas[face] = var7.getUnsignedByte();

            }
            if (triangleSkinOpcode == 1) {
                def.triangleLabels[face] = var8.getUnsignedByte();
            }

        }
        var4.pos = indicesOffset;
        var5.pos = triangleCompressTypeOffset;
        int a = 0;
        int b = 0;
        int c = 0;
        int offset = 0;
        int coordinate;

        for (int face = 0; face < def.trianglesCount; face++) {
            int opcode = var5.getUnsignedByte();

            if (opcode == 1) {
                a = (var4.getSignedSmart() + offset);
                offset = a;
                b = (var4.getSignedSmart() + offset);
                offset = b;
                c = (var4.getSignedSmart() + offset);
                offset = c;
                def.trianglesX[face] = a;
                def.trianglesY[face] = b;
                def.trianglesZ[face] = c;

            }
            if (opcode == 2) {
                b = c;
                c = (var4.getSignedSmart() + offset);
                offset = c;
                def.trianglesX[face] = a;
                def.trianglesY[face] = b;
                def.trianglesZ[face] = c;
            }
            if (opcode == 3) {
                a = c;
                c = (var4.getSignedSmart() + offset);
                offset = c;
                def.trianglesX[face] = a;
                def.trianglesY[face] = b;
                def.trianglesZ[face] = c;
            }
            if (opcode == 4) {
                coordinate = a;
                a = b;
                b = coordinate;
                c = (var4.getSignedSmart() + offset);
                offset = c;
                def.trianglesX[face] = a;
                def.trianglesY[face] = b;
                def.trianglesZ[face] = c;
            }

        }
        var4.pos = textureOffset;

        for (int face = 0; face < def.texturesCount; face++) {
            //textureMap[face] = 0;
            def.texturesX[face] = (short) var4.getUnsignedShort();
            def.texturesY[face] = (short) var4.getUnsignedShort();
            def.texturesZ[face] = (short) var4.getUnsignedShort();
        }
        def.verticesParticle = new int[def.verticesCount];
        if (hasParticles) {
            var4.pos = (var1.length - 23) - (particleCount * 4);
            for (int vertex = 0; vertex < particleCount; vertex++) {
                int index = var4.getUnsignedShort();
                int definitionIndex = var4.getUnsignedShort();
                def.verticesParticle[index] = definitionIndex;
            }
        }

        if (def.types == null) {
            def.types = new int[def.trianglesCount];
        }
        //   System.out.println("Tri info = " + Arrays.toString(types));
    }
}
