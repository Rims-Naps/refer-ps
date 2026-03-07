package org.necrotic.client.media.renderable;

import org.necrotic.Configuration;
import org.necrotic.client.*;
import org.necrotic.client.net.Buffer;
import org.necrotic.client.cache.FileOperations;
import org.necrotic.client.net.Stream;
import org.necrotic.client.net.requester.OnDemandFetcherParent;
import org.necrotic.client.media.Raster;
import org.necrotic.client.media.AnimationSkeleton;
import org.necrotic.client.media.AnimationSkin;
import org.necrotic.client.media.VertexNormal;
import org.necrotic.client.cache.media.particles.Particle;
import org.necrotic.client.cache.media.particles.ParticleDefinition;
import org.necrotic.client.cache.media.particles.ParticleVector;
import org.necrotic.client.media.Rasterizer3D;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Model extends Renderable {

    private boolean scaledVertices;
    private int offX = 0;
    private int offY = 0;
    private int offZ = 0;
    private int lastRenderedRotation = 0;
    public int[] verticesParticle;
    private static final int POLYGON_COUNT = 30000;
    private static final int VERTICES_COUNT = 12000;
    public static boolean objectExists;
    private static boolean[] hidden = new boolean[POLYGON_COUNT];
    private static boolean[] hide = new boolean[POLYGON_COUNT];
    public static Model aModel_1621 = new Model(true);
    private static int animateX;
    private static int animateY;
    private static int animateZ;
    public static int currentCursorX;
    public static int currentCursorY;
    public static int objectsRendered;
    private static int[] anIntArray1622 = new int[VERTICES_COUNT];
    private static int[] anIntArray1623 = new int[VERTICES_COUNT];
    private static int[] anIntArray1624 = new int[VERTICES_COUNT];
    private static int[] anIntArray1625 = new int[VERTICES_COUNT];
    private static int[] viewportX = new int[POLYGON_COUNT];
    private static int[] viewportY = new int[POLYGON_COUNT];
    private static int[] anIntArray1667 = new int[POLYGON_COUNT];
    private static int[] viewportTextureX = new int[POLYGON_COUNT];
    private static int[] viewportTextureY = new int[POLYGON_COUNT];
    private static int[] viewportTextureZ = new int[POLYGON_COUNT];
    private static int[] vertexPerspectiveDepth = new int[POLYGON_COUNT];

    private static int[] depthListIndices = new int[1800];
    private static int[] anIntArray1673 = new int[12];
    private static int[] anIntArray1675 = new int[VERTICES_COUNT];
    private static int[] anIntArray1676 = new int[VERTICES_COUNT];
    private static int[] anIntArray1677 = new int[12];
    private static int[] anIntArray1678 = new int[10];
    private static int[] anIntArray1679 = new int[10];
    private static int[] anIntArray1680 = new int[10];
    public static int[] mapObjIds = new int[1000];
    public static int[] anIntArray1688 = new int[1000];
    private static int[][] faceLists = new int[8000][512];
    private static int[][] anIntArrayArray1674 = new int[12][VERTICES_COUNT];
    private static OnDemandFetcherParent aOnDemandFetcherParent_1662;

    public static void method459(int i, OnDemandFetcherParent onDemandFetcherParent) {
        aOnDemandFetcherParent_1662 = onDemandFetcherParent;
    }

    public static HashMap<Integer, byte[]> mapRS2 = new HashMap<>();

    public static final File raw = new File(Signlink.getCacheDirectory() + "data" + File.separator + "raw" + File.separator);

    private static java.util.List<Integer> encryptedModels = new ArrayList<>();

    public static void preloadCustomEncoded() {
        try (java.util.stream.Stream<Path> paths = Files.walk(Paths.get(Signlink.getCacheDirectory() + "data/osrs/"))) {
            paths.filter(Files::isRegularFile).forEach(path -> {
                byte[] data;
                try {
                    data = Files.readAllBytes(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String name = path.getFileName().toString();
                int id = Integer.parseInt(name.substring(0, name.lastIndexOf(".")));
                mapRS2.put(id, data);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initEncrypted() { // ? ur forcing it to use another decoder for all files in the raw directory lol oo and i also said this yday, look
        if (raw.exists()) {
            for (File file : raw.listFiles()) {
                if (file.getName().equalsIgnoreCase("desktop.ini"))
                    continue;
                String name = file.getName().replaceAll(".dat", "");
                int id = Integer.parseInt(name);
                encryptedModels.add(id);
            }
        }
        //System.out.println("Initialized " + encryptedModels.size() + " encrypted models!");
    }

    public static byte[] getData(final int model) {
        if (raw.exists()) {
            final String rawDat = raw.getAbsolutePath() + File.separator + model + ".dat";
            if (new File(rawDat).exists()) {
                load(FileOperations.readFile(rawDat), model);
            }
        }
        return mapRS2.get(model);
    }

    public static void load(byte[] data, int id) {
        if (data == null) {
            return;
        }
        mapRS2.put(id, data);
    }

    public static Model fetchModel(int model) {
        final byte[] data = getData(model);
        if (data != null) {
            return new Model(data, model);
        }
        aOnDemandFetcherParent_1662.get(model);
        return null;
    }

    public static boolean isModelFetched(int model) {
        final byte[] data = getData(model);
        if (data != null) {
            return true;
        }
        aOnDemandFetcherParent_1662.get(model);
        return false;
    }

    private static final int method481(int i, int j, int k) {
        if (i == 65535) {
            return 0;
        }

        if ((k & 2) == 2) {
            if (j < 0) {
                j = 0;
            } else if (j > 127) {
                j = 127;
            }

            j = 127 - j;
            return j;
        }

        j = j * (i & 0x7f) >> 7;

        if (j < 2) {
            j = 2;
        } else if (j > 126) {
            j = 126;
        }

        return (i & 0xff80) + j;
    }

    public static void nullify() {
        mapRS2.clear();
        mapRS2 = null;
        hidden = null;
        hide = null;
        viewportY = null;
        anIntArray1667 = null;
        viewportTextureX = null;
        viewportTextureY = null;
        viewportTextureZ = null;
        vertexPerspectiveDepth = null;
        depthListIndices = null;
        faceLists = null;
        anIntArray1673 = null;
        anIntArrayArray1674 = null;
        anIntArray1675 = null;
        anIntArray1676 = null;
        anIntArray1677 = null;
    }

    private boolean aBoolean1618;
    public boolean aBoolean1659;
    public VertexNormal[] vertexNormals;
    public int verticesCount;
    public int trianglesCount;
    public int priority;
    public int texturesCount;
    public int anInt1646;
    public int anInt1647;
    public int anInt1648;
    public int anInt1649;
    public int XYZMag;
    public int bottomY;
    private int diameter;
    private int radius;
    public int anInt1654;
    public int[] verticesX;
    public int[] verticesY;
    public int[] verticesZ;
    public int[] trianglesX;
    public int[] trianglesY;
    public int[] trianglesZ;
    private int[] colorsX;
    private int[] colorsY;
    private int[] colorsZ;
    public int[] types;
    public int[] priorities;
    public int[] alphas;
    public int[] colors;
    public int[] texturesX;
    public int[] texturesY;
    public int[] texturesZ;
    public int[] vertexData;
    public int[] triangleLabels;
    public int[][] vertexGroups;
    public int[][] triangleGroup;


    public void decode317Custom(byte[] data, int id) {
        Buffer first = new Buffer(data);
        Buffer second = new Buffer(data);
        Buffer third = new Buffer(data);
        Buffer fourth = new Buffer(data);
        Buffer fifth = new Buffer(data);
        first.pos = data.length - 23;
        verticesCount = first.getUnsignedShort();
        trianglesCount = first.getUnsignedShort();
        texturesCount = first.getUnsignedByte();

        int renderTypeOpcode = first.getUnsignedByte();
        int renderPriorityOpcode = first.getUnsignedByte();
        int triangleAlphaOpcode = first.getUnsignedByte();
        int triangleSkinOpcode = first.getUnsignedByte();
        int vertexLabelOpcode = first.getUnsignedByte();
        int verticesXCoordinateOffset = first.getUnsignedShort();

        int verticesYCoordinateOffset = first.getUnsignedShort();
        int verticesZCoordinateOffset = first.getUnsignedShort();
        int triangleIndicesOffset = first.getUnsignedShort();

        int particleCount = first.getUnsignedShort();

        boolean hasParticles = particleCount > 0;


        int pos = 0;

        int vertexFlagOffset = pos;
        pos += verticesCount;

        int triangleCompressTypeOffset = pos;
        pos += trianglesCount;

        int facePriorityOffset = pos;
        if (renderPriorityOpcode == 255) {
            pos += trianglesCount;
        }

        int triangleSkinOffset = pos;
        if (triangleSkinOpcode == 1) {
            pos += trianglesCount;
        }

        int renderTypeOffset = pos;
        if (renderTypeOpcode == 1) {
            pos += trianglesCount;
        }

        int vertexLabelsOffset = pos;
        if (vertexLabelOpcode == 1) {
            pos += verticesCount;
        }

        int triangleAlphaOffset = pos;
        if (triangleAlphaOpcode == 1) {
            pos += trianglesCount;
        }

        int indicesOffset = pos;
        pos += triangleIndicesOffset;

        int triangleColorOffset = pos;
        pos += trianglesCount * 2;

        int textureOffset = pos;
        pos += texturesCount * 6;

        int xOffset = pos;
        pos += verticesXCoordinateOffset;

        int yOffset = pos;
        pos += verticesYCoordinateOffset;

        int zOffset = pos;

        verticesX = new int[verticesCount];
        verticesY = new int[verticesCount];
        verticesZ = new int[verticesCount];
        trianglesX = new int[trianglesCount];
        trianglesY = new int[trianglesCount];
        trianglesZ = new int[trianglesCount];
        if (texturesCount > 0) {
           // textureMap = new short[texturesCount];
            texturesX = new int[texturesCount];
            texturesY = new int[texturesCount];
            texturesZ = new int[texturesCount];
        }

        if (vertexLabelOpcode == 1)
            vertexData = new int[verticesCount];


        if (renderTypeOpcode == 1) {
            types = new int[trianglesCount];
           // faceTexture = new short[trianglesCount];
           // triangleMaterial = new int[trianglesCount];
            //faceTextureMasks = new byte[trianglesCount];
        }

        if (renderPriorityOpcode == 255)
            priorities = new int[trianglesCount];
        else
            priority = (byte) renderPriorityOpcode;

        if (triangleAlphaOpcode == 1)
            alphas = new int[trianglesCount];

        if (triangleSkinOpcode == 1)
            triangleLabels = new int[trianglesCount];

        colors = new int[trianglesCount];
        first.pos = vertexFlagOffset;
        second.pos = xOffset;
        third.pos = yOffset;
        fourth.pos = zOffset;
        fifth.pos = vertexLabelsOffset;
        int baseX = 0;
        int baseY = 0;
        int baseZ = 0;

        for (int point = 0; point < verticesCount; point++) {
            int flag = first.getUnsignedByte();

            int x = 0;
            if ((flag & 0x1) != 0) {
                x = second.getSignedSmart();
            }

            int y = 0;
            if ((flag & 0x2) != 0) {
                y = third.getSignedSmart();
            }
            int z = 0;
            if ((flag & 0x4) != 0) {
                z = fourth.getSignedSmart();
            }

            verticesX[point] = baseX + x;
            verticesY[point] = baseY + y;
            verticesZ[point] = baseZ + z;
            baseX = verticesX[point];
            baseY = verticesY[point];
            baseZ = verticesZ[point];
            if (vertexLabelOpcode == 1) {
                vertexData[point] = fifth.getUnsignedByte();
            }
        }


        first.pos = triangleColorOffset;
        second.pos = renderTypeOffset;
        third.pos = facePriorityOffset;
        fourth.pos = triangleAlphaOffset;
        fifth.pos = triangleSkinOffset;

        for (int face = 0; face < trianglesCount; face++) {
            int color = first.getUnsignedShort();
            colors[face] = (short) color;

            if (renderTypeOpcode == 1) {
                types[face] = second.getUnsignedByte();
            }
            if (renderPriorityOpcode == 255) {
                priorities[face] = third.getSignedByte();
            }

            if (triangleAlphaOpcode == 1) {
                alphas[face] = fourth.getUnsignedByte();

            }
            if (triangleSkinOpcode == 1) {
                triangleLabels[face] = fifth.getUnsignedByte();
            }

        }
        first.pos = indicesOffset;
        second.pos = triangleCompressTypeOffset;
        int a = 0;
        int b = 0;
        int c = 0;
        int offset = 0;
        int coordinate;

        for (int face = 0; face < trianglesCount; face++) {
            int opcode = second.getUnsignedByte();

            if (opcode == 1) {
                a = (first.getSignedSmart() + offset);
                offset = a;
                b = (first.getSignedSmart() + offset);
                offset = b;
                c = (first.getSignedSmart() + offset);
                offset = c;
                trianglesX[face] = a;
                trianglesY[face] = b;
                trianglesZ[face] = c;

            }
            if (opcode == 2) {
                b = c;
                c = (first.getSignedSmart() + offset);
                offset = c;
                trianglesX[face] = a;
                trianglesY[face] = b;
                trianglesZ[face] = c;
            }
            if (opcode == 3) {
                a = c;
                c = (first.getSignedSmart() + offset);
                offset = c;
                trianglesX[face] = a;
                trianglesY[face] = b;
                trianglesZ[face] = c;
            }
            if (opcode == 4) {
                coordinate = a;
                a = b;
                b = coordinate;
                c = (first.getSignedSmart() + offset);
                offset = c;
                trianglesX[face] = a;
                trianglesY[face] = b;
                trianglesZ[face] = c;
            }

        }
        first.pos = textureOffset;

        for (int face = 0; face < texturesCount; face++) {
            //textureMap[face] = 0;
            texturesX[face] = (short) first.getUnsignedShort();
            texturesY[face] = (short) first.getUnsignedShort();
            texturesZ[face] = (short) first.getUnsignedShort();
        }
        verticesParticle = new int[verticesCount];
        if (hasParticles) {
            first.pos = (data.length - 23) - (particleCount * 4);
            for (int vertex = 0; vertex < particleCount; vertex++) {
                int index = first.getUnsignedShort();
                int definitionIndex = first.getUnsignedShort();
                verticesParticle[index] = definitionIndex;
            }
        }

        if (types == null) {
            types = new int[trianglesCount];
        }
        if (id == 130409) {
            upscale(1.1);
        }
        //   System.out.println("Tri info = " + Arrays.toString(types));
    }

    private Model(boolean flag) {
        aBoolean1618 = true;
        aBoolean1659 = false;

        if (!flag) {
            aBoolean1618 = !aBoolean1618;
        }
    }

    public Model(boolean flag, boolean flag1, boolean flag2, Model model) {
        aBoolean1618 = true;
        aBoolean1659 = false;
        verticesCount = model.verticesCount;
        trianglesCount = model.trianglesCount;
        texturesCount = model.texturesCount;

        if (flag2) {
            verticesParticle = model.verticesParticle;
            verticesX = model.verticesX;
            verticesY = model.verticesY;
            verticesZ = model.verticesZ;
        } else {
            verticesParticle = new int[verticesCount];
            verticesX = new int[verticesCount];
            verticesY = new int[verticesCount];
            verticesZ = new int[verticesCount];

            for (int j = 0; j < verticesCount; j++) {
                verticesParticle[j] = model.verticesParticle[j];
                verticesX[j] = model.verticesX[j];
                verticesY[j] = model.verticesY[j];
                verticesZ[j] = model.verticesZ[j];
            }
        }

        if (flag) {
            colors = model.colors;
        } else {
            colors = new int[trianglesCount];

            for (int k = 0; k < trianglesCount; k++) {
                colors[k] = model.colors[k];
            }
        }

        if (flag1) {
            alphas = model.alphas;
        } else {
            alphas = new int[trianglesCount];

            if (model.alphas == null) {
                for (int l = 0; l < trianglesCount; l++) {
                    alphas[l] = 0;
                }
            } else {
                for (int i1 = 0; i1 < trianglesCount; i1++) {
                    alphas[i1] = model.alphas[i1];
                }
            }
        }

        vertexData = model.vertexData;
        triangleLabels = model.triangleLabels;
        types = model.types;
        trianglesX = model.trianglesX;
        trianglesY = model.trianglesY;
        trianglesZ = model.trianglesZ;
        priorities = model.priorities;
        priority = model.priority;
        texturesX = model.texturesX;
        texturesY = model.texturesY;
        texturesZ = model.texturesZ;
        scaledVertices = model.scaledVertices;
    }

    public Model(boolean flag, boolean flag1, Model model) {
        aBoolean1618 = true;
        aBoolean1659 = false;
        verticesCount = model.verticesCount;
        trianglesCount = model.trianglesCount;
        texturesCount = model.texturesCount;

        if (flag) {
            verticesY = new int[verticesCount];

            for (int j = 0; j < verticesCount; j++) {
                verticesY[j] = model.verticesY[j];
            }
        } else {
            verticesY = model.verticesY;
        }

        if (flag1) {
            colorsX = new int[trianglesCount];
            colorsY = new int[trianglesCount];
            colorsZ = new int[trianglesCount];

            for (int k = 0; k < trianglesCount; k++) {
                colorsX[k] = model.colorsX[k];
                colorsY[k] = model.colorsY[k];
                colorsZ[k] = model.colorsZ[k];
            }

            types = new int[trianglesCount];

            if (model.types == null) {
                for (int l = 0; l < trianglesCount; l++) {
                    types[l] = 0;
                }
            } else {
                for (int i1 = 0; i1 < trianglesCount; i1++) {
                    types[i1] = model.types[i1];
                }
            }

            super.aVertexNormalArray1425 = new VertexNormal[verticesCount];

            for (int j1 = 0; j1 < verticesCount; j1++) {
                VertexNormal vertexNormal = super.aVertexNormalArray1425[j1] = new VertexNormal();
                VertexNormal vertexNormal_1 = model.aVertexNormalArray1425[j1];
                vertexNormal.x = vertexNormal_1.x;
                vertexNormal.y = vertexNormal_1.y;
                vertexNormal.z = vertexNormal_1.z;
                vertexNormal.magnitude = vertexNormal_1.magnitude;
            }

            vertexNormals = model.vertexNormals;
        } else {
            colorsX = model.colorsX;
            colorsY = model.colorsY;
            colorsZ = model.colorsZ;
            types = model.types;
        }

        verticesParticle = model.verticesParticle;
        verticesX = model.verticesX;
        verticesZ = model.verticesZ;
        colors = model.colors;
        alphas = model.alphas;
        priorities = model.priorities;
        priority = model.priority;
        trianglesX = model.trianglesX;
        trianglesY = model.trianglesY;
        trianglesZ = model.trianglesZ;
        texturesX = model.texturesX;
        texturesY = model.texturesY;
        texturesZ = model.texturesZ;
        super.modelHeight = model.modelHeight;
        XYZMag = model.XYZMag;
        radius = model.radius;
        diameter = model.diameter;
        anInt1646 = model.anInt1646;
        anInt1648 = model.anInt1648;
        anInt1649 = model.anInt1649;
        anInt1647 = model.anInt1647;
        scaledVertices = model.scaledVertices;
    }

    private static final boolean allPris10 = true;

    //come back here
    private Model(byte[] data, int modelId) {
        if (modelId > 50000 && data[data.length - 1] == -1 && data[data.length - 2] == -1 && data[data.length - 3] == -1) {
            decode317Custom(data, modelId);
        } else if (data[data.length - 1] == -1 && data[data.length - 2] == -1) {
            read622Model(data, modelId);
        } else {
            readOldModel(data);
        }

        int[] priorityFix = new int[]{64448, 100_009};
        for (int z : priorityFix) {
            if (modelId == z) {
                if (priorities == null) {
                    priorities = new int[trianglesCount];
                }
                for (z = 0; z < this.priorities.length; z++) {
                    this.priorities[z] = 10;
                }
            }
        }
        int[] stopPriorityOverride = {
            10884, 14826, 2537
        };
        for (int i : stopPriorityOverride) {
            if (modelId == i)
                return;
        }
        if (allPris10) {
            if (priorities == null) {
                priorities = new int[trianglesCount];
            }

            Arrays.fill(priorities, 10);
        }
    }

    public void recolour(int original, int target) {
        for (int k = 0; k < trianglesCount; k++) {
            if (colors[k] == original) {
                colors[k] = target;
            }
        }
    }

    public Model(int i, Model[] amodel) {
        aBoolean1618 = true;
        aBoolean1659 = false;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        verticesCount = 0;
        trianglesCount = 0;
        texturesCount = 0;
        priority = -1;

        for (int k = 0; k < i; k++) {
            Model model = amodel[k];

            if (model != null) {
                verticesCount += model.verticesCount;
                trianglesCount += model.trianglesCount;
                texturesCount += model.texturesCount;
                flag |= model.types != null;

                if (model.priorities != null) {
                    flag1 = true;
                } else {
                    if (priority == -1) {
                        priority = model.priority;
                    }

                    if (priority != model.priority) {
                        flag1 = true;
                    }
                }

                flag2 |= model.alphas != null;
                flag3 |= model.triangleLabels != null;
                scaledVertices |= model.scaledVertices;
            }
        }

        verticesParticle = new int[verticesCount];
        verticesX = new int[verticesCount];
        verticesY = new int[verticesCount];
        verticesZ = new int[verticesCount];
        vertexData = new int[verticesCount];
        trianglesX = new int[trianglesCount];
        trianglesY = new int[trianglesCount];
        trianglesZ = new int[trianglesCount];
        texturesX = new int[texturesCount];
        texturesY = new int[texturesCount];
        texturesZ = new int[texturesCount];

        if (flag) {
            types = new int[trianglesCount];
        }

        if (flag1) {
            priorities = new int[trianglesCount];
        }

        if (flag2) {
            alphas = new int[trianglesCount];
        }

        if (flag3) {
            triangleLabels = new int[trianglesCount];
        }

        colors = new int[trianglesCount];
        verticesCount = 0;
        trianglesCount = 0;
        texturesCount = 0;
        int l = 0;

        for (int i1 = 0; i1 < i; i1++) {
            Model model_1 = amodel[i1];

            if (model_1 != null) {
                for (int j1 = 0; j1 < model_1.trianglesCount; j1++) {
                    if (flag) {
                        if (model_1.types == null) {
                            types[trianglesCount] = 0;
                        } else {
                            int k1 = model_1.types[j1];

                            if ((k1 & 2) == 2) {
                                k1 += l << 2;
                            }

                            types[trianglesCount] = k1;
                        }
                    }

                    if (flag1) {
                        if (model_1.priorities == null) {
                            priorities[trianglesCount] = model_1.priority;
                        } else {
                            priorities[trianglesCount] = model_1.priorities[j1];
                        }
                    }

                    if (flag2) {
                        if (model_1.alphas == null) {
                            alphas[trianglesCount] = 0;
                        } else {
                            alphas[trianglesCount] = model_1.alphas[j1];
                        }
                    }

                    if (flag3 && model_1.triangleLabels != null) {
                        triangleLabels[trianglesCount] = model_1.triangleLabels[j1];
                    }

                    colors[trianglesCount] = model_1.colors[j1];
                    trianglesX[trianglesCount] = method465(model_1, model_1.trianglesX[j1]);
                    trianglesY[trianglesCount] = method465(model_1, model_1.trianglesY[j1]);
                    trianglesZ[trianglesCount] = method465(model_1, model_1.trianglesZ[j1]);
                    trianglesCount++;
                }

                for (int l1 = 0; l1 < model_1.texturesCount; l1++) {
                    texturesX[texturesCount] = method465(model_1, model_1.texturesX[l1]);
                    texturesY[texturesCount] = method465(model_1, model_1.texturesY[l1]);
                    texturesZ[texturesCount] = method465(model_1, model_1.texturesZ[l1]);
                    texturesCount++;
                }

                l += model_1.texturesCount;
            }
        }
    }

    public Model(Model[] amodel) {
        int i = 2;
        aBoolean1618 = true;
        aBoolean1659 = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        verticesCount = 0;
        trianglesCount = 0;
        texturesCount = 0;
        priority = -1;

        for (int k = 0; k < i; k++) {
            Model model = amodel[k];
            if (model != null) {
                verticesCount += model.verticesCount;
                trianglesCount += model.trianglesCount;
                texturesCount += model.texturesCount;
                flag1 |= model.types != null;
                if (model.priorities != null) {
                    flag2 = true;
                } else {
                    if (priority == -1) {
                        priority = model.priority;
                    }
                    if (priority != model.priority) {
                        flag2 = true;
                    }
                }
                flag3 |= model.alphas != null;
                flag4 |= model.colors != null;
                scaledVertices |= model.scaledVertices;
            }
        }

        verticesParticle = new int[verticesCount];
        verticesX = new int[verticesCount];
        verticesY = new int[verticesCount];
        verticesZ = new int[verticesCount];
        trianglesX = new int[trianglesCount];
        trianglesY = new int[trianglesCount];
        trianglesZ = new int[trianglesCount];
        colorsX = new int[trianglesCount];
        colorsY = new int[trianglesCount];
        colorsZ = new int[trianglesCount];
        texturesX = new int[texturesCount];
        texturesY = new int[texturesCount];
        texturesZ = new int[texturesCount];
        if (flag1) {
            types = new int[trianglesCount];
        }
        if (flag2) {
            priorities = new int[trianglesCount];
        }
        if (flag3) {
            alphas = new int[trianglesCount];
        }
        if (flag4) {
            colors = new int[trianglesCount];
        }
        verticesCount = 0;
        trianglesCount = 0;
        texturesCount = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < i; j1++) {
            Model model_1 = amodel[j1];
            if (model_1 != null) {
                int k1 = verticesCount;
                for (int l1 = 0; l1 < model_1.verticesCount; l1++) {
                    int x = model_1.verticesX[l1];
                    int y = model_1.verticesY[l1];
                    int z = model_1.verticesZ[l1];
                    int p = model_1.verticesParticle[l1];
                    if (scaledVertices && !model_1.scaledVertices) {
                        x <<= 2;
                        y <<= 2;
                        z <<= 2;
                    }
                    verticesX[verticesCount] = x;
                    verticesY[verticesCount] = y;
                    verticesZ[verticesCount] = z;
                    verticesParticle[verticesCount] = p;
                    verticesCount++;
                }

                for (int i2 = 0; i2 < model_1.trianglesCount; i2++) {
                    trianglesX[trianglesCount] = model_1.trianglesX[i2] + k1;
                    trianglesY[trianglesCount] = model_1.trianglesY[i2] + k1;
                    trianglesZ[trianglesCount] = model_1.trianglesZ[i2] + k1;
                    colorsX[trianglesCount] = model_1.colorsX[i2];
                    colorsY[trianglesCount] = model_1.colorsY[i2];
                    colorsZ[trianglesCount] = model_1.colorsZ[i2];
                    if (flag1) {
                        if (model_1.types == null) {
                            types[trianglesCount] = 0;
                        } else {
                            int j2 = model_1.types[i2];
                            if ((j2 & 2) == 2) {
                                j2 += i1 << 2;
                            }
                            types[trianglesCount] = j2;
                        }
                    }
                    if (flag2) {
                        if (model_1.priorities == null) {
                            priorities[trianglesCount] = model_1.priority;
                        } else {
                            priorities[trianglesCount] = model_1.priorities[i2];
                        }
                    }
                    if (flag3) {
                        if (model_1.alphas == null) {
                            alphas[trianglesCount] = 0;
                        } else {
                            alphas[trianglesCount] = model_1.alphas[i2];
                        }
                    }
                    if (flag4 && model_1.colors != null) {
                        colors[trianglesCount] = model_1.colors[i2];
                    }

                    trianglesCount++;
                }

                for (int k2 = 0; k2 < model_1.texturesCount; k2++) {
                    texturesX[texturesCount] = model_1.texturesX[k2] + k1;
                    texturesY[texturesCount] = model_1.texturesY[k2] + k1;
                    texturesZ[texturesCount] = model_1.texturesZ[k2] + k1;
                    texturesCount++;
                }

                i1 += model_1.texturesCount;
            }
        }

        method466();
    }

    public void method464(Model model, boolean flag) {
        verticesCount = model.verticesCount;
        trianglesCount = model.trianglesCount;
        texturesCount = model.texturesCount;
        if (anIntArray1622.length < verticesCount) {
            anIntArray1622 = new int[verticesCount + 10000];
            anIntArray1623 = new int[verticesCount + 10000];
            anIntArray1624 = new int[verticesCount + 10000];
        }
        verticesParticle = new int[verticesCount];
        verticesX = anIntArray1622;
        verticesY = anIntArray1623;
        verticesZ = anIntArray1624;
        for (int k = 0; k < verticesCount; k++) {
            verticesParticle[k] = model.verticesParticle[k];
            verticesX[k] = model.verticesX[k];
            verticesY[k] = model.verticesY[k];
            verticesZ[k] = model.verticesZ[k];
        }

        if (flag) {
            alphas = model.alphas;
        } else {
            if (anIntArray1625.length < trianglesCount) {
                anIntArray1625 = new int[trianglesCount + 100];
            }
            alphas = anIntArray1625;
            if (model.alphas == null) {
                for (int l = 0; l < trianglesCount; l++) {
                    alphas[l] = 0;
                }

            } else {
                for (int i1 = 0; i1 < trianglesCount; i1++) {
                    alphas[i1] = model.alphas[i1];
                }

            }
        }
        types = model.types;
        colors = model.colors;
        priorities = model.priorities;
        priority = model.priority;
        triangleGroup = model.triangleGroup;
        vertexGroups = model.vertexGroups;
        trianglesX = model.trianglesX;
        trianglesY = model.trianglesY;
        trianglesZ = model.trianglesZ;
        colorsX = model.colorsX;
        colorsY = model.colorsY;
        colorsZ = model.colorsZ;
        texturesX = model.texturesX;
        texturesY = model.texturesY;
        texturesZ = model.texturesZ;
        scaledVertices = model.scaledVertices;
    }

    private final int method465(Model model, int i) {
        int j = -1;
        int p = model.verticesParticle[i];
        int k = model.verticesX[i];
        int l = model.verticesY[i];
        int i1 = model.verticesZ[i];
        if (scaledVertices && !model.scaledVertices) {
            k <<= 2;
            l <<= 2;
            i1 <<= 2;
        }
        for (int j1 = 0; j1 < verticesCount; j1++) {
            if (k != verticesX[j1] || l != verticesY[j1] || i1 != verticesZ[j1]) {
                continue;
            }
            j = j1;
            break;
        }

        if (j == -1) {
            verticesParticle[verticesCount] = p;
            verticesX[verticesCount] = k;
            verticesY[verticesCount] = l;
            verticesZ[verticesCount] = i1;
            if (model.vertexData != null) {
                vertexData[verticesCount] = model.vertexData[i];
            }
            j = verticesCount++;
        }
        return j;
    }

    public void method466() {
        super.modelHeight = 0;
        XYZMag = 0;
        bottomY = 0;
        for (int i = 0; i < verticesCount; i++) {
            int j = verticesX[i];
            int k = verticesY[i];
            int l = verticesZ[i];
            if (scaledVertices) {
                j >>= 2;
                k >>= 2;
                l >>= 2;
            }
            if (-k > super.modelHeight) {
                super.modelHeight = -k;
            }
            if (k > bottomY) {
                bottomY = k;
            }
            int i1 = j * j + l * l;
            if (i1 > XYZMag) {
                XYZMag = i1;
            }
        }
        XYZMag = (int) (Math.sqrt(XYZMag) + 0.98999999999999999D);
        radius = (int) (Math.sqrt(XYZMag * XYZMag + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        diameter = radius + (int) (Math.sqrt(XYZMag * XYZMag + bottomY * bottomY) + 0.98999999999999999D);
    }

    public void method467() {
        super.modelHeight = 0;
        bottomY = 0;
        for (int i = 0; i < verticesCount; i++) {
            int j = verticesY[i];
            if (-j > super.modelHeight) {
                super.modelHeight = -j;
            }
            if (j > bottomY) {
                bottomY = j;
            }
        }

        radius = (int) (Math.sqrt(XYZMag * XYZMag + super.modelHeight * super.modelHeight) + 0.98999999999999999D);
        diameter = radius + (int) (Math.sqrt(XYZMag * XYZMag + bottomY * bottomY) + 0.98999999999999999D);
    }

    private void method468(int i) {
        super.modelHeight = 0;
        XYZMag = 0;
        bottomY = 0;
        anInt1646 = 0xf423f;
        anInt1647 = 0xfff0bdc1;
        anInt1648 = 0xfffe7961;
        anInt1649 = 0x1869f;
        for (int j = 0; j < verticesCount; j++) {
            int k = verticesX[j];
            int l = verticesY[j];
            int i1 = verticesZ[j];
            if (scaledVertices) {
                k >>= 2;
                l >>= 2;
                i1 >>= 2;
            }
            if (k < anInt1646) {
                anInt1646 = k;
            }
            if (k > anInt1647) {
                anInt1647 = k;
            }
            if (i1 < anInt1649) {
                anInt1649 = i1;
            }
            if (i1 > anInt1648) {
                anInt1648 = i1;
            }
            if (-l > super.modelHeight) {
                super.modelHeight = -l;
            }
            if (l > bottomY) {
                bottomY = l;
            }
            int j1 = k * k + i1 * i1;
            if (j1 > XYZMag) {
                XYZMag = j1;
            }
        }

        XYZMag = (int) Math.sqrt(XYZMag);
        radius = (int) Math.sqrt(XYZMag * XYZMag + super.modelHeight * super.modelHeight);
        if (i != 21073) {
            return;
        } else {
            diameter = radius + (int) Math.sqrt(XYZMag * XYZMag + bottomY * bottomY);
            return;
        }
    }

    public void createBones() {
        if (vertexData != null) {
            int[] ai = new int[256];
            int j = 0;
            for (int l = 0; l < verticesCount; l++) {
                int j1 = vertexData[l];
                ai[j1]++;
                if (j1 > j) {
                    j = j1;
                }
            }

            vertexGroups = new int[j + 1][];
            for (int k1 = 0; k1 <= j; k1++) {
                vertexGroups[k1] = new int[ai[k1]];
                ai[k1] = 0;
            }

            for (int j2 = 0; j2 < verticesCount; j2++) {
                int l2 = vertexData[j2];
                vertexGroups[l2][ai[l2]++] = j2;
            }

            vertexData = null;
        }
        if (triangleLabels != null) {
            int[] ai1 = new int[256];
            int k = 0;
            for (int i1 = 0; i1 < trianglesCount; i1++) {
                int l1 = triangleLabels[i1];
                ai1[l1]++;
                if (l1 > k) {
                    k = l1;
                }
            }

            triangleGroup = new int[k + 1][];
            for (int i2 = 0; i2 <= k; i2++) {
                triangleGroup[i2] = new int[ai1[i2]];
                ai1[i2] = 0;
            }

            for (int k2 = 0; k2 < trianglesCount; k2++) {
                int i3 = triangleLabels[k2];
                triangleGroup[i3][ai1[i3]++] = k2;
            }

            triangleLabels = null;
        }
    }

    public void applyTransform(int i) {
        if (vertexGroups == null) {
            return;
        }
        if (i == -1) {
            return;
        }
        AnimationSkeleton class36 = AnimationSkeleton.forId(i);
        if (class36 == null) {
            return;
        }
        AnimationSkin class18 = class36.animationSkin;
        animateX = 0;
        animateY = 0;
        animateZ = 0;
        for (int k = 0; k < class36.stepCount; k++) {
            int l = class36.opCodeTable[k];
            method472(class18.opcodes[l], class18.skinList[l], class36.translater_x[k], class36.translater_y[k], class36.translater_z[k]);
        }

    }

    public void interpolateFrames(int firstFrame, int nextFrame, int end, int cycle) {
        if (!Configuration.TWEENING_ENABLED) {
            applyTransform(nextFrame);
            return;
        }
        try {
            if (vertexGroups != null && firstFrame != -1) {
                AnimationSkeleton currentAnimation = AnimationSkeleton.forId(firstFrame);
                if (currentAnimation == null) {
                    applyTransform(nextFrame);
                    return;
                }
                AnimationSkin list1 = currentAnimation.animationSkin;
                animateX = 0;
                animateY = 0;
                animateZ = 0;
                AnimationSkeleton nextAnimation = null;
                AnimationSkin list2 = null;
                if (nextFrame != -1) {
                    nextAnimation = AnimationSkeleton.forId(nextFrame);
                    if (nextAnimation.animationSkin != list1) {
                        nextAnimation = null;
                    }
                    list2 = nextAnimation.animationSkin;
                }
                if (nextAnimation == null || list2 == null) {
                    for (int i_263_ = 0; i_263_ < currentAnimation.stepCount; i_263_++) {
                        int i_264_ = currentAnimation.opCodeTable[i_263_];
                        method472(list1.opcodes[i_264_], list1.skinList[i_264_], currentAnimation.translater_x[i_263_], currentAnimation.translater_y[i_263_], currentAnimation.translater_z[i_263_]);

                    }
                } else {
                    for (int i1 = 0; i1 < currentAnimation.stepCount; i1++) {
                        int n1 = currentAnimation.opCodeTable[i1];
                        int opcode = list1.opcodes[n1];
                        int[] skin = list1.skinList[n1];
                        int x = currentAnimation.translater_x[i1];
                        int y = currentAnimation.translater_y[i1];
                        int z = currentAnimation.translater_z[i1];
                        boolean found = false;
                        for (int i2 = 0; i2 < nextAnimation.stepCount; i2++) {
                            int n2 = nextAnimation.opCodeTable[i2];
                            if (list2.skinList[n2].equals(skin)) {
                                if (opcode != 2) {
                                    x += (nextAnimation.translater_x[i2] - x) * cycle / end;
                                    y += (nextAnimation.translater_y[i2] - y) * cycle / end;
                                    z += (nextAnimation.translater_z[i2] - z) * cycle / end;
                                } else {
                                    x &= 0xff;
                                    y &= 0xff;
                                    z &= 0xff;
                                    int dx = nextAnimation.translater_x[i2] - x & 0xff;
                                    int dy = nextAnimation.translater_y[i2] - y & 0xff;
                                    int dz = nextAnimation.translater_z[i2] - z & 0xff;
                                    if (dx >= 128) {
                                        dx -= 256;
                                    }
                                    if (dy >= 128) {
                                        dy -= 256;
                                    }
                                    if (dz >= 128) {
                                        dz -= 256;
                                    }
                                    x = x + dx * cycle / end & 0xff;
                                    y = y + dy * cycle / end & 0xff;
                                    z = z + dz * cycle / end & 0xff;
                                }
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            if (opcode != 3 && opcode != 2) {
                                x = x * (end - cycle) / end;
                                y = y * (end - cycle) / end;
                                z = z * (end - cycle) / end;
                            } else if (opcode == 3) {
                                x = (x * (end - cycle) + (cycle << 7)) / end;
                                y = (y * (end - cycle) + (cycle << 7)) / end;
                                z = (z * (end - cycle) + (cycle << 7)) / end;
                            } else {
                                x &= 0xff;
                                y &= 0xff;
                                z &= 0xff;
                                int dx = -x & 0xff;
                                int dy = -y & 0xff;
                                int dz = -z & 0xff;
                                if (dx >= 128) {
                                    dx -= 256;
                                }
                                if (dy >= 128) {
                                    dy -= 256;
                                }
                                if (dz >= 128) {
                                    dz -= 256;
                                }
                                x = x + dx * cycle / end & 0xff;
                                y = y + dy * cycle / end & 0xff;
                                z = z + dz * cycle / end & 0xff;
                            }
                        }
                        method472(opcode, skin, x, y, z);
                    }
                }
            }

        } catch (Exception e) {
            applyTransform(firstFrame);
        }
    }

    public void method471(int[] ai, int j, int k) {
        if (k == -1) {
            return;
        }
        if (ai == null || j == -1) {
            applyTransform(k);
            return;
        }
        AnimationSkeleton class36 = AnimationSkeleton.forId(k);
        if (class36 == null) {
            return;
        }
        AnimationSkeleton class36_1 = AnimationSkeleton.forId(j);
        if (class36_1 == null) {
            applyTransform(k);
            return;
        }
        AnimationSkin class18 = class36.animationSkin;
        animateX = 0;
        animateY = 0;
        animateZ = 0;
        int l = 0;
        int i1 = ai[l++];
        for (int j1 = 0; j1 < class36.stepCount; j1++) {
            int k1;
            for (k1 = class36.opCodeTable[j1]; k1 > i1; i1 = ai[l++]) {
                ;
            }
            if (k1 != i1 || class18.opcodes[k1] == 0) {
                method472(class18.opcodes[k1], class18.skinList[k1], class36.translater_x[j1], class36.translater_y[j1], class36.translater_z[j1]);
            }
        }

        animateX = 0;
        animateY = 0;
        animateZ = 0;
        l = 0;
        i1 = ai[l++];
        for (int l1 = 0; l1 < class36_1.stepCount; l1++) {
            int i2;
            for (i2 = class36_1.opCodeTable[l1]; i2 > i1; i1 = ai[l++]) {
                ;
            }
            if (i2 == i1 || class18.opcodes[i2] == 0) {
                method472(class18.opcodes[i2], class18.skinList[i2], class36_1.translater_x[l1], class36_1.translater_y[l1], class36_1.translater_z[l1]);
            }
        }

    }

    private void method472(int i, int[] ai, int j, int k, int l) {

        int i1 = ai.length;
        if (i == 0) {
            int j1 = 0;
            animateX = 0;
            animateY = 0;
            animateZ = 0;
            for (int k2 = 0; k2 < i1; k2++) {
                int l3 = ai[k2];
                if (l3 < vertexGroups.length) {
                    int[] ai5 = vertexGroups[l3];
                    for (int j6 : ai5) {
                        animateX += verticesX[j6] >> (scaledVertices ? 2 : 0);
                        animateY += verticesY[j6] >> (scaledVertices ? 2 : 0);
                        animateZ += verticesZ[j6] >> (scaledVertices ? 2 : 0);
                        j1++;
                    }

                }
            }

            if (j1 > 0) {
                animateX = animateX / j1 + j;
                animateY = animateY / j1 + k;
                animateZ = animateZ / j1 + l;
                return;
            } else {
                animateX = j;
                animateY = k;
                animateZ = l;
                return;
            }
        }
        if (i == 1) {
            for (int k1 = 0; k1 < i1; k1++) {
                int l2 = ai[k1];
                if (l2 < vertexGroups.length) {
                    int[] ai1 = vertexGroups[l2];
                    for (int element : ai1) {
                        int j5 = element;
                        verticesX[j5] += j << (scaledVertices ? 2 : 0);
                        verticesY[j5] += k << (scaledVertices ? 2 : 0);
                        verticesZ[j5] += l << (scaledVertices ? 2 : 0);
                    }

                }
            }

            return;
        }
        if (i == 2) {
            for (int l1 = 0; l1 < i1; l1++) {
                int i3 = ai[l1];
                if (i3 < vertexGroups.length) {
                    int[] ai2 = vertexGroups[i3];
                    for (int element : ai2) {
                        int k5 = element;
                        verticesX[k5] -= animateX << (scaledVertices ? 2 : 0);
                        verticesY[k5] -= animateY << (scaledVertices ? 2 : 0);
                        verticesZ[k5] -= animateZ << (scaledVertices ? 2 : 0);
                        int k6 = (j & 0xff) * 8;
                        int l6 = (k & 0xff) * 8;
                        int i7 = (l & 0xff) * 8;
                        if (i7 != 0) {
                            int j7 = Rasterizer3D.SINE[i7];
                            int i8 = Rasterizer3D.COSINE[i7];
                            int l8 = verticesY[k5] * j7 + verticesX[k5] * i8 >> 16;
                            verticesY[k5] = verticesY[k5] * i8 - verticesX[k5] * j7 >> 16;
                            verticesX[k5] = l8;
                        }
                        if (k6 != 0) {
                            int k7 = Rasterizer3D.SINE[k6];
                            int j8 = Rasterizer3D.COSINE[k6];
                            int i9 = verticesY[k5] * j8 - verticesZ[k5] * k7 >> 16;
                            verticesZ[k5] = verticesY[k5] * k7 + verticesZ[k5] * j8 >> 16;
                            verticesY[k5] = i9;
                        }
                        if (l6 != 0) {
                            int l7 = Rasterizer3D.SINE[l6];
                            int k8 = Rasterizer3D.COSINE[l6];
                            int j9 = verticesZ[k5] * l7 + verticesX[k5] * k8 >> 16;
                            verticesZ[k5] = verticesZ[k5] * k8 - verticesX[k5] * l7 >> 16;
                            verticesX[k5] = j9;
                        }
                        verticesX[k5] += animateX << (scaledVertices ? 2 : 0);
                        verticesY[k5] += animateY << (scaledVertices ? 2 : 0);
                        verticesZ[k5] += animateZ << (scaledVertices ? 2 : 0);
                    }

                }
            }
            return;
        }
        if (i == 3) {
            for (int i2 = 0; i2 < i1; i2++) {
                int j3 = ai[i2];
                if (j3 < vertexGroups.length) {
                    int[] ai3 = vertexGroups[j3];
                    for (int element : ai3) {
                        int l5 = element;
                        verticesX[l5] -= animateX << (scaledVertices ? 2 : 0);
                        verticesY[l5] -= animateY << (scaledVertices ? 2 : 0);
                        verticesZ[l5] -= animateZ << (scaledVertices ? 2 : 0);
                        verticesX[l5] = verticesX[l5] * j / 128;
                        verticesY[l5] = verticesY[l5] * k / 128;
                        verticesZ[l5] = verticesZ[l5] * l / 128;
                        verticesX[l5] += animateX << (scaledVertices ? 2 : 0);
                        verticesY[l5] += animateY << (scaledVertices ? 2 : 0);
                        verticesZ[l5] += animateZ << (scaledVertices ? 2 : 0);
                    }
                }
            }
            return;
        }
        if (i == 5 && triangleGroup != null && alphas != null) {
            for (int j2 = 0; j2 < i1; j2++) {
                int k3 = ai[j2];
                if (k3 < triangleGroup.length) {
                    int[] ai4 = triangleGroup[k3];
                    for (int element : ai4) {
                        int i6 = element;
                        alphas[i6] += j * 8;
                        if (alphas[i6] < 0) {
                            alphas[i6] = 0;
                        }
                        if (alphas[i6] > 255) {
                            alphas[i6] = 255;
                        }
                    }
                }
            }
        }
    }

    public void method473() {
        for (int j = 0; j < verticesCount; j++) {
            int k = verticesX[j];
            verticesX[j] = verticesZ[j];
            verticesZ[j] = -k;
        }
    }

    public void rotateX(int i) {
        int k = Rasterizer3D.SINE[i];
        int l = Rasterizer3D.COSINE[i];

        for (int i1 = 0; i1 < verticesCount; i1++) {
            int j1 = verticesY[i1] * l - verticesZ[i1] * k >> 16;
            verticesZ[i1] = verticesY[i1] * k + verticesZ[i1] * l >> 16;
            verticesY[i1] = j1;
        }
    }

    public void scale(double factor) {
        for (int i = 0; i < verticesCount; ++i) {
            verticesY[i] *= factor;
            verticesX[i] *= factor;
            verticesZ[i] *= factor;
        }
    }

    public void translate(int i, int j, int l) {
        if (scaledVertices) {
            i <<= 2;
            j <<= 2;
            l <<= 2;
        }
        for (int i1 = 0; i1 < verticesCount; i1++) {
            verticesX[i1] += i;
            verticesY[i1] += j;
            verticesZ[i1] += l;
        }
    }

    public void method476(int i, int j) {
        for (int k = 0; k < trianglesCount; k++) {
            if (colors[k] == i)
                colors[k] = j;
        }
    }

    public void method477() {
        for (int j = 0; j < verticesCount; j++) {
            verticesZ[j] = -verticesZ[j];
        }
        for (int k = 0; k < trianglesCount; k++) {
            int l = trianglesX[k];
            trianglesX[k] = trianglesZ[k];
            trianglesZ[k] = l;
        }
    }

    public void scaleT(int i, int j, int l) {
        for (int i1 = 0; i1 < verticesCount; i1++) {
            verticesX[i1] = verticesX[i1] * i / 128;
            verticesY[i1] = verticesY[i1] * l / 128;
            verticesZ[i1] = verticesZ[i1] * j / 128;
        }

    }

    public final void light(int i, int j, int k, int l, int i1, boolean flag) {
        int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
        int k1 = j * j1 >> 8;
        if (colorsX == null) {
            colorsX = new int[trianglesCount];
            colorsY = new int[trianglesCount];
            colorsZ = new int[trianglesCount];
        }
        if (super.aVertexNormalArray1425 == null) {
            super.aVertexNormalArray1425 = new VertexNormal[verticesCount];
            for (int l1 = 0; l1 < verticesCount; l1++) {
                super.aVertexNormalArray1425[l1] = new VertexNormal();
            }

        }
        for (int i2 = 0; i2 < trianglesCount; i2++) {
            if (colors != null && alphas != null) {
                if (colors[i2] == 65535
                        /*
                         * || (anIntArray1640[i2] == 0 // Black Triangles 633 // Models
                         * - Fixes Gwd walls // & Black models )
                         */ || colors[i2] == 16705) {
                    alphas[i2] = 255;
                }
            }
            int j2 = trianglesX[i2]; // the fuck, u have duplicate model data..
            int l2 = trianglesY[i2];
            int i3 = trianglesZ[i2];
            int j3 = verticesX[l2] - verticesX[j2] >> (scaledVertices ? 2 : 0);
            int k3 = verticesY[l2] - verticesY[j2] >> (scaledVertices ? 2 : 0);
            int l3 = verticesZ[l2] - verticesZ[j2] >> (scaledVertices ? 2 : 0);
            int i4 = verticesX[i3] - verticesX[j2] >> (scaledVertices ? 2 : 0);
            int j4 = verticesY[i3] - verticesY[j2] >> (scaledVertices ? 2 : 0);
            int k4 = verticesZ[i3] - verticesZ[j2] >> (scaledVertices ? 2 : 0);
            int l4 = k3 * k4 - j4 * l3;
            int i5 = l3 * i4 - k4 * j3;
            int j5;
            for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192 || j5 < -8192; j5 >>= 1) {
                l4 >>= 1;
                i5 >>= 1;
            }

            int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
            if (k5 <= 0) {
                k5 = 1;
            }
            l4 = l4 * 256 / k5;
            i5 = i5 * 256 / k5;
            j5 = j5 * 256 / k5;

            if (types == null || (types[i2] & 1) == 0) {

                VertexNormal vertexNormal_2 = super.aVertexNormalArray1425[j2];
                vertexNormal_2.x += l4;
                vertexNormal_2.y += i5;
                vertexNormal_2.z += j5;
                vertexNormal_2.magnitude++;
                vertexNormal_2 = super.aVertexNormalArray1425[l2];
                vertexNormal_2.x += l4;
                vertexNormal_2.y += i5;
                vertexNormal_2.z += j5;
                vertexNormal_2.magnitude++;
                vertexNormal_2 = super.aVertexNormalArray1425[i3];
                vertexNormal_2.x += l4;
                vertexNormal_2.y += i5;
                vertexNormal_2.z += j5;
                vertexNormal_2.magnitude++;

            } else {

                int l5 = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
                colorsX[i2] = method481(colors[i2], l5, types[i2]);

            }
        }

        if (flag) {
            method480(i, k1, k, l, i1);
        } else {
            vertexNormals = new VertexNormal[verticesCount];
            for (int k2 = 0; k2 < verticesCount; k2++) {
                VertexNormal vertexNormal = super.aVertexNormalArray1425[k2];
                VertexNormal vertexNormal_1 = vertexNormals[k2] = new VertexNormal();
                vertexNormal_1.x = vertexNormal.x;
                vertexNormal_1.y = vertexNormal.y;
                vertexNormal_1.z = vertexNormal.z;
                vertexNormal_1.magnitude = vertexNormal.magnitude;
            }

        }
        if (flag) {
            method466();
            return;
        } else {
            method468(21073);
            return;
        }
    }


    public void light1(int i, int j, int k, int l, int i1, boolean flag) {
        try {
            int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
            int k1 = j * j1 >> 8;
            if (colorsX == null) {
                colorsX = new int[numberOfTriangleFaces];
                colorsY = new int[numberOfTriangleFaces];
                colorsZ = new int[numberOfTriangleFaces];
            }
            if (super.aVertexNormalArray1425 == null) {
                super.aVertexNormalArray1425 = new VertexNormal[verticesCount];
                for (int l1 = 0; l1 < verticesCount; l1++) {
                    super.aVertexNormalArray1425[l1] = new VertexNormal();
                }

            }
            for (int i2 = 0; i2 < numberOfTriangleFaces; i2++) {
                if (colors != null && alphas != null) {
                    if (colors[i2] == 65535 || colors[i2] == 1 || colors[i2] == 16705 || colors[i2] == 255) {
                        alphas[i2] = 255;
                    }
                }
                int j2 = trianglesX[i2];
                int l2 = trianglesY[i2];
                int i3 = trianglesZ[i2];
                int j3 = verticesX[l2] - verticesX[j2];
                int k3 = verticesY[l2] - verticesY[j2];
                int l3 = verticesZ[l2] - verticesZ[j2];
                int i4 = verticesX[i3] - verticesX[j2];
                int j4 = verticesY[i3] - verticesY[j2];
                int k4 = verticesZ[i3] - verticesZ[j2];
                int l4 = k3 * k4 - j4 * l3;
                int i5 = l3 * i4 - k4 * j3;
                int j5;
                for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192 || j5 < -8192; j5 >>= 1) {
                    l4 >>= 1;
                    i5 >>= 1;
                }

                int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
                if (k5 <= 0) {
                    k5 = 1;
                }
                l4 = (l4 * 256) / k5;
                i5 = (i5 * 256) / k5;
                j5 = (j5 * 256) / k5;

                if (types == null || (types[i2] & 1) == 0) {

                    VertexNormal vertexNormal_2 = super.aVertexNormalArray1425[j2];
                    vertexNormal_2.x += l4;
                    vertexNormal_2.y += i5;
                    vertexNormal_2.z += j5;
                    vertexNormal_2.magnitude++;
                    vertexNormal_2 = super.aVertexNormalArray1425[l2];
                    vertexNormal_2.x += l4;
                    vertexNormal_2.y += i5;
                    vertexNormal_2.z += j5;
                    vertexNormal_2.magnitude++;
                    vertexNormal_2 = super.aVertexNormalArray1425[i3];
                    vertexNormal_2.x += l4;
                    vertexNormal_2.y += i5;
                    vertexNormal_2.z += j5;
                    vertexNormal_2.magnitude++;
                    vertexNormal_2 = null;

                } else {

                    int l5 = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
                    colorsX[i2] = method481(colors[i2], l5, colors[i2]);

                }
            }

            if (flag) {
                method480(i, k1, k, l, i1);
            } else {
                vertexNormals = new VertexNormal[verticesCount];
                for (int k2 = 0; k2 < verticesCount; k2++) {
                    VertexNormal vertexNormal = super.aVertexNormalArray1425[k2];
                    VertexNormal vertexNormal_1 = vertexNormals[k2] = new VertexNormal();
                    vertexNormal_1.x = vertexNormal.x;
                    vertexNormal_1.y = vertexNormal.y;
                    vertexNormal_1.z = vertexNormal.z;
                    vertexNormal_1.magnitude = vertexNormal.magnitude;
                }

            }
            if (flag) {
                method466();
                return;
            } else {
                method468(21073);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void method480(int i, int j, int k, int l, int i1) {
        for (int j1 = 0; j1 < trianglesCount; j1++) {
            int k1 = trianglesX[j1];
            int i2 = trianglesY[j1];
            int j2 = trianglesZ[j1];
            if (types == null) {
                int i3 = colors[j1];
                VertexNormal vertexNormal = super.aVertexNormalArray1425[k1];
                int k2 = i + (k * vertexNormal.x + l * vertexNormal.y + i1 * vertexNormal.z) / (j * vertexNormal.magnitude);
                colorsX[j1] = method481(i3, k2, 0);
                vertexNormal = super.aVertexNormalArray1425[i2];
                k2 = i + (k * vertexNormal.x + l * vertexNormal.y + i1 * vertexNormal.z) / (j * vertexNormal.magnitude);
                colorsY[j1] = method481(i3, k2, 0);
                vertexNormal = super.aVertexNormalArray1425[j2];
                k2 = i + (k * vertexNormal.x + l * vertexNormal.y + i1 * vertexNormal.z) / (j * vertexNormal.magnitude);
                colorsZ[j1] = method481(i3, k2, 0);
            } else if ((types[j1] & 1) == 0) {
                int j3 = colors[j1];
                int k3 = types[j1];
                VertexNormal vertexNormal_1 = super.aVertexNormalArray1425[k1];
                int l2 = i + (k * vertexNormal_1.x + l * vertexNormal_1.y + i1 * vertexNormal_1.z) / (j * vertexNormal_1.magnitude);
                colorsX[j1] = method481(j3, l2, k3);
                vertexNormal_1 = super.aVertexNormalArray1425[i2];
                l2 = i + (k * vertexNormal_1.x + l * vertexNormal_1.y + i1 * vertexNormal_1.z) / (j * vertexNormal_1.magnitude);
                colorsY[j1] = method481(j3, l2, k3);
                vertexNormal_1 = super.aVertexNormalArray1425[j2];
                l2 = i + (k * vertexNormal_1.x + l * vertexNormal_1.y + i1 * vertexNormal_1.z) / (j * vertexNormal_1.magnitude);
                colorsZ[j1] = method481(j3, l2, k3);
            }
        }

        super.aVertexNormalArray1425 = null;
        vertexNormals = null;
        vertexData = null;
        triangleLabels = null;
        if (types != null) {
            for (int l1 = 0; l1 < trianglesCount; l1++) {
                if ((types[l1] & 2) == 2) {
                    return;
                }
            }

        }
        colors = null;
    }

    public void addParticleDefinition(int particleDefinition, int amount) {
        for (int i = 0; i < amount; i++) {
            int vert = Client.RANDOM.nextInt(verticesCount);
            verticesParticle[vert] = particleDefinition;
        }
    }

    public final void renderSingle(int j, int k, int l, int i1, int j1, int k1) {
        int i = 0;
        int l1 = Rasterizer3D.textureInt1;
        int i2 = Rasterizer3D.textureInt2;
        int j2 = Rasterizer3D.SINE[i];
        int k2 = Rasterizer3D.COSINE[i];
        int l2 = Rasterizer3D.SINE[j];
        int i3 = Rasterizer3D.COSINE[j];
        int j3 = Rasterizer3D.SINE[k];
        int k3 = Rasterizer3D.COSINE[k];
        int l3 = Rasterizer3D.SINE[l];
        int i4 = Rasterizer3D.COSINE[l];
        int j4 = j1 * l3 + k1 * i4 >> 16;
        for (int k4 = 0; k4 < verticesCount; k4++) {
            int l4 = verticesX[k4] << (scaledVertices ? 0 : 2);
            int i5 = verticesY[k4] << (scaledVertices ? 0 : 2);
            int j5 = verticesZ[k4] << (scaledVertices ? 0 : 2);
            if (k != 0) {
                int k5 = i5 * j3 + l4 * k3 >> 16;
                i5 = i5 * k3 - l4 * j3 >> 16;
                l4 = k5;
            }
            if (i != 0) {
                int l5 = i5 * k2 - j5 * j2 >> 16;
                j5 = i5 * j2 + j5 * k2 >> 16;
                i5 = l5;
            }
            if (j != 0) {
                int i6 = j5 * l2 + l4 * i3 >> 16;
                j5 = j5 * i3 - l4 * l2 >> 16;
                l4 = i6;
            }
            l4 += i1 << 2;
            i5 += j1 << 2;
            j5 += k1 << 2;
            int j6 = i5 * i4 - j5 * l3 >> 16;
            j5 = i5 * l3 + j5 * i4 >> 16;
            i5 = j6;
            anIntArray1667[k4] = (j5 >> 2) - j4;
            vertexPerspectiveDepth[k4] = (j5 >> 2);
            if (j5 == 0) {
                return;
            }
            viewportX[k4] = l1 + (l4 << 9) / j5;
            viewportY[k4] = i2 + (i5 << 9) / j5;
            if (texturesCount > 0) {
                viewportTextureX[k4] = l4 >> 2;
                viewportTextureY[k4] = i5 >> 2;
                viewportTextureZ[k4] = j5 >> 2;
            }
        }

        try {
            translateToScreen(false, false, 0, 0, 0);
            return;
        } catch (Exception _ex) {
            _ex.printStackTrace();
            return;
        }
    }

    public static int farClip = 3500;

    @Override
    public void render(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int uid, int newuid, int bufferOffset) {
        offX = j1 + Client.instance.xCameraPos;
        offY = k1 + Client.instance.zCameraPos;
        offZ = l1 + Client.instance.yCameraPos;
        lastRenderedRotation = i;
        int j2 = l1 * i1 - j1 * l >> 16;
        int k2 = k1 * j + j2 * k >> 16;
        int l2 = XYZMag * k >> 16;
        int i3 = k2 + l2;
        if (i3 <= 50 || k2 >= Configuration.MODEL_CLAMPING) {
            return;
        }
        int j3 = l1 * l + j1 * i1 >> 16;
        int k3 = j3 - XYZMag << Client.viewDistance;
        if (k3 / i3 >= Raster.centerY) {
            return;
        }
        int l3 = j3 + XYZMag << Client.viewDistance;
        if (l3 / i3 <= -Raster.centerY) {
            return;
        }
        int i4 = k1 * k - j2 * j >> 16;
        int j4 = XYZMag * j >> 16;
        int k4 = i4 + j4 << Client.viewDistance;
        if (k4 / i3 <= -Raster.middleY) {
            return;
        }
        int l4 = j4 + (super.modelHeight * k >> 16);
        int i5 = i4 - l4 << Client.viewDistance;
        if (i5 / i3 >= Raster.middleY) {
            return;
        }
        int j5 = l2 + (super.modelHeight * j >> 16);
        boolean flag = false;
        if (k2 - j5 <= 50) {
            flag = true;
        }
        boolean flag1 = false;
        if (uid > 0 && objectExists) {
            int k5 = k2 - l2;
            if (k5 <= 50) {
                k5 = 50;
            }
            if (j3 > 0) {
                k3 /= i3;
                l3 /= k5;
            } else {
                l3 /= i3;
                k3 /= k5;
            }
            if (i4 > 0) {
                i5 /= i3;
                k4 /= k5;
            } else {
                k4 /= i3;
                i5 /= k5;
            }
            int i6 = currentCursorX - Rasterizer3D.textureInt1;
            int k6 = currentCursorY - Rasterizer3D.textureInt2;
            if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4) {
                if (aBoolean1659) {
                    mapObjIds[objectsRendered] = newuid;
                    anIntArray1688[objectsRendered++] = uid;
                } else {
                    flag1 = true;
                }
            }
        }
        int l5 = Rasterizer3D.textureInt1;
        int j6 = Rasterizer3D.textureInt2;
        int l6 = 0;
        int i7 = 0;
        if (i != 0) {
            l6 = Rasterizer3D.SINE[i];
            i7 = Rasterizer3D.COSINE[i];
        }
        for (int j7 = 0; j7 < verticesCount; j7++) {
            int k7 = verticesX[j7] << (scaledVertices ? 0 : 2);
            int l7 = verticesY[j7] << (scaledVertices ? 0 : 2);
            int i8 = verticesZ[j7] << (scaledVertices ? 0 : 2);
            if (i != 0) {
                int j8 = i8 * l6 + k7 * i7 >> 16;
                i8 = i8 * i7 - k7 * l6 >> 16;
                k7 = j8;
            }
            k7 += j1 << 2;
            l7 += k1 << 2;
            i8 += l1 << 2;
            int k8 = i8 * l + k7 * i1 >> 16;
            i8 = i8 * i1 - k7 * l >> 16;
            k7 = k8;
            k8 = l7 * k - i8 * j >> 16;
            i8 = l7 * j + i8 * k >> 16;
            l7 = k8;
            anIntArray1667[j7] = (i8 >> 2) - k2;
            vertexPerspectiveDepth[j7] = (i8 >> 2);
            if (i8 >= 50) {
                viewportX[j7] = l5 + (k7 << Client.viewDistance) / i8;
                viewportY[j7] = j6 + (l7 << Client.viewDistance) / i8;
            } else {
                viewportX[j7] = -5000;
                flag = true;
            }
            if (flag || texturesCount > 0) {
                viewportTextureX[j7] = k7 >> 2;
                viewportTextureY[j7] = l7 >> 2;
                viewportTextureZ[j7] = i8 >> 2;
            }
        }
        try {
            translateToScreen(flag, flag1, uid, newuid, bufferOffset);
            return;
        } catch (Exception _ex) {
            return;
        }
    }

    private final void translateToScreen(boolean flag, boolean flag1, int i, int id, int bufferOffset) {
        for (int j = 0; j < diameter; j++) {
            depthListIndices[j] = 0;
        }

        for (int k = 0; k < trianglesCount; k++) {
            if (types == null || types[k] != -1) {
                int l = trianglesX[k];
                int k1 = trianglesY[k];
                int j2 = trianglesZ[k];
                int i3 = viewportX[l];
                int l3 = viewportX[k1];
                int k4 = viewportX[j2];
                if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
                    hide[k] = true;
                    int j5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2]) / 3 + radius;
                    faceLists[j5][depthListIndices[j5]++] = k;
                } else {
                    if (flag1 && method486(currentCursorX, currentCursorY, viewportY[l], viewportY[k1], viewportY[j2], i3, l3, k4)) {
                        mapObjIds[objectsRendered] = id;
                        anIntArray1688[objectsRendered++] = i;
                        flag1 = false;
                    }
                    if ((i3 - l3) * (viewportY[j2] - viewportY[k1]) - (viewportY[l] - viewportY[k1]) * (k4 - l3) > 0) {
                        hide[k] = false;
                        if (i3 < 0 || l3 < 0 || k4 < 0 || i3 > Raster.maxRight || l3 > Raster.maxRight || k4 > Raster.maxRight) {
                            hidden[k] = true;
                        } else {
                            hidden[k] = false;
                        }
                        int k5 = (anIntArray1667[l] + anIntArray1667[k1] + anIntArray1667[j2]) / 3 + radius;
                        faceLists[k5][depthListIndices[k5]++] = k;
                    }
                }
            }
        }

        if (priorities == null) {
            for (int i1 = diameter - 1; i1 >= 0; i1--) {
                int l1 = depthListIndices[i1];
                if (l1 > 0) {
                    int[] ai = faceLists[i1];
                    for (int j3 = 0; j3 < l1; j3++) {
                        draw(ai[j3], bufferOffset);
                    }

                }
            }

            return;
        }
        for (int j1 = 0; j1 < 12; j1++) {
            anIntArray1673[j1] = 0;
            anIntArray1677[j1] = 0;
        }

        for (int i2 = diameter - 1; i2 >= 0; i2--) {
            int k2 = depthListIndices[i2];
            if (k2 > 0) {
                int[] ai1 = faceLists[i2];
                for (int i4 = 0; i4 < k2; i4++) {
                    int l4 = ai1[i4];
                    int l5 = priorities[l4];
                    int j6 = anIntArray1673[l5]++;
                    anIntArrayArray1674[l5][j6] = l4;
                    if (l5 < 10) {
                        anIntArray1677[l5] += i2;
                    } else if (l5 == 10) {
                        anIntArray1675[j6] = i2;
                    } else {
                        anIntArray1676[j6] = i2;
                    }
                }

            }
        }

        int l2 = 0;
        if (anIntArray1673[1] > 0 || anIntArray1673[2] > 0) {
            l2 = (anIntArray1677[1] + anIntArray1677[2]) / (anIntArray1673[1] + anIntArray1673[2]);
        }
        int k3 = 0;
        if (anIntArray1673[3] > 0 || anIntArray1673[4] > 0) {
            k3 = (anIntArray1677[3] + anIntArray1677[4]) / (anIntArray1673[3] + anIntArray1673[4]);
        }
        int j4 = 0;
        if (anIntArray1673[6] > 0 || anIntArray1673[8] > 0) {
            j4 = (anIntArray1677[6] + anIntArray1677[8]) / (anIntArray1673[6] + anIntArray1673[8]);
        }
        int i6 = 0;
        int k6 = anIntArray1673[10];
        int[] ai2 = anIntArrayArray1674[10];
        int[] ai3 = anIntArray1675;
        if (i6 == k6) {
            i6 = 0;
            k6 = anIntArray1673[11];
            ai2 = anIntArrayArray1674[11];
            ai3 = anIntArray1676;
        }
        int i5;
        if (i6 < k6) {
            i5 = ai3[i6];
        } else {
            i5 = -1000;
        }
        for (int l6 = 0; l6 < 10; l6++) {
            while (l6 == 0 && i5 > l2) {
                draw(ai2[i6++], bufferOffset);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6) {
                    i5 = ai3[i6];
                } else {
                    i5 = -1000;
                }
            }
            while (l6 == 3 && i5 > k3) {
                draw(ai2[i6++], bufferOffset);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6) {
                    i5 = ai3[i6];
                } else {
                    i5 = -1000;
                }
            }
            while (l6 == 5 && i5 > j4) {
                draw(ai2[i6++], bufferOffset);
                if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                    i6 = 0;
                    k6 = anIntArray1673[11];
                    ai2 = anIntArrayArray1674[11];
                    ai3 = anIntArray1676;
                }
                if (i6 < k6) {
                    i5 = ai3[i6];
                } else {
                    i5 = -1000;
                }
            }
            int i7 = anIntArray1673[l6];
            int[] ai4 = anIntArrayArray1674[l6];
            for (int j7 = 0; j7 < i7; j7++) {
                draw(ai4[j7], bufferOffset);
            }

        }

        while (i5 != -1000) {
            draw(ai2[i6++], bufferOffset);
            if (i6 == k6 && ai2 != anIntArrayArray1674[11]) {
                i6 = 0;
                ai2 = anIntArrayArray1674[11];
                k6 = anIntArray1673[11];
                ai3 = anIntArray1676;
            }
            if (i6 < k6) {
                i5 = ai3[i6];
            } else {
                i5 = -1000;
            }
        }
        if (!Client.safeRenderer)
            for (int m = 0; m < verticesCount; m++) {
                int n = m;

                int pid = verticesParticle[n] - 1;
                if (pid < 0) {
                    continue;
                }

                ParticleDefinition def = ParticleDefinition.cache[pid];
                int pX = verticesX[n] >> (scaledVertices ? 2 : 0);
                int pY = verticesY[n] >> (scaledVertices ? 2 : 0);
                int pZ = verticesZ[n] >> (scaledVertices ? 2 : 0);
                int depth = vertexPerspectiveDepth[n];
                if (lastRenderedRotation != 0) {
                    int sine = Rasterizer3D.SINE[lastRenderedRotation];
                    int cosine = Rasterizer3D.COSINE[lastRenderedRotation];
                    int rotatedX = pZ * sine + pX * cosine >> 16;
                    pZ = pZ * cosine - pX * sine >> 16;
                    pX = rotatedX;
                }
                pX += offX;
                pZ += offZ;
                ParticleVector basePos = new ParticleVector(pX, -pY, pZ);
                for (int p = 0; p < def.getSpawnRate(); p++) {
                    Particle particle = new Particle(def, basePos, depth, pid);
                    Client.instance.addParticle(particle);
                }
            }
    }

    private void draw(int face, int key) {
        if (hide[face]) {
            hide(face, key);
            return;
        }
        int j = trianglesX[face];
        int k = trianglesY[face];
        int l = trianglesZ[face];
        Rasterizer3D.hidden = hidden[face];
        if (alphas == null) {
            Rasterizer3D.alpha = 0;
        } else {
            Rasterizer3D.alpha = alphas[face];
        }
        int type = types == null ? 0 : types[face] & 3;

        //Models
        if (type == 0) {
            Rasterizer3D.drawGouraudTriangle(viewportY[j], viewportY[k], viewportY[l], viewportX[j], viewportX[k], viewportX[l], colorsX[face], colorsY[face], colorsZ[face], vertexPerspectiveDepth[j], vertexPerspectiveDepth[k], vertexPerspectiveDepth[l], key);
        } else if (type == 1) {
            Rasterizer3D.drawFlatTriangle(viewportY[j], viewportY[k], viewportY[l], viewportX[j], viewportX[k], viewportX[l], Rasterizer3D.hsl2rgb[colorsX[face]], vertexPerspectiveDepth[j], vertexPerspectiveDepth[k], vertexPerspectiveDepth[l], key);
            //Textured Models
        } else if (type == 2) {
            int j1 = types[face] >> 2;
            int l1 = texturesX[j1];
            int j2 = texturesY[j1];
            int l2 = texturesZ[j1];
            Rasterizer3D.drawTexturedTriangle(viewportY[j], viewportY[k], viewportY[l], viewportX[j], viewportX[k], viewportX[l], colorsX[face], colorsY[face], colorsZ[face], viewportTextureX[l1], viewportTextureX[j2], viewportTextureX[l2], viewportTextureY[l1], viewportTextureY[j2], viewportTextureY[l2], viewportTextureZ[l1], viewportTextureZ[j2], viewportTextureZ[l2], colors[face], vertexPerspectiveDepth[j], vertexPerspectiveDepth[k], vertexPerspectiveDepth[l], key);

        } else if (type == 3) {
            int k1 = types[face] >> 2;
            int i2 = texturesX[k1];
            int k2 = texturesY[k1];
            int i3 = texturesZ[k1];
            Rasterizer3D.drawTexturedTriangle(viewportY[j], viewportY[k], viewportY[l], viewportX[j], viewportX[k], viewportX[l], colorsX[face], colorsX[face], colorsX[face], viewportTextureX[i2], viewportTextureX[k2], viewportTextureX[i3], viewportTextureY[i2], viewportTextureY[k2], viewportTextureY[i3], viewportTextureZ[i2], viewportTextureZ[k2], viewportTextureZ[i3], colors[face], vertexPerspectiveDepth[j], vertexPerspectiveDepth[k], vertexPerspectiveDepth[l], key);
        }
    }

    private void hide(int i, int bufferOffset) {
        if (colors != null) {
            if (colors[i] == 65535) {
                return;
            }
        }
        int j = Rasterizer3D.textureInt1;
        int k = Rasterizer3D.textureInt2;
        int l = 0;
        int i1 = trianglesX[i];
        int j1 = trianglesY[i];
        int k1 = trianglesZ[i];
        int l1 = viewportTextureZ[i1];
        int i2 = viewportTextureZ[j1];
        int j2 = viewportTextureZ[k1];

        if (l1 >= 50) {
            anIntArray1678[l] = viewportX[i1];
            anIntArray1679[l] = viewportY[i1];
            anIntArray1680[l++] = colorsX[i];
        } else {
            int k2 = viewportTextureX[i1];
            int k3 = viewportTextureY[i1];
            int k4 = colorsX[i];
            if (j2 >= 50) {
                int k5 = (50 - l1) * Rasterizer3D.anIntArray1469[j2 - l1];
                anIntArray1678[l] = j + (k2 + ((viewportTextureX[k1] - k2) * k5 >> 16) << Client.viewDistance) / 50;
                anIntArray1679[l] = k + (k3 + ((viewportTextureY[k1] - k3) * k5 >> 16) << Client.viewDistance) / 50;
                anIntArray1680[l++] = k4 + ((colorsZ[i] - k4) * k5 >> 16);
            }
            if (i2 >= 50) {
                int l5 = (50 - l1) * Rasterizer3D.anIntArray1469[i2 - l1];
                anIntArray1678[l] = j + (k2 + ((viewportTextureX[j1] - k2) * l5 >> 16) << Client.viewDistance) / 50;
                anIntArray1679[l] = k + (k3 + ((viewportTextureY[j1] - k3) * l5 >> 16) << Client.viewDistance) / 50;
                anIntArray1680[l++] = k4 + ((colorsY[i] - k4) * l5 >> 16);
            }
        }
        if (i2 >= 50) {
            anIntArray1678[l] = viewportX[j1];
            anIntArray1679[l] = viewportY[j1];
            anIntArray1680[l++] = colorsY[i];
        } else {
            int l2 = viewportTextureX[j1];
            int l3 = viewportTextureY[j1];
            int l4 = colorsY[i];
            if (l1 >= 50) {
                int i6 = (50 - i2) * Rasterizer3D.anIntArray1469[l1 - i2];
                anIntArray1678[l] = j + (l2 + ((viewportTextureX[i1] - l2) * i6 >> 16) << Client.viewDistance) / 50;
                anIntArray1679[l] = k + (l3 + ((viewportTextureY[i1] - l3) * i6 >> 16) << Client.viewDistance) / 50;
                anIntArray1680[l++] = l4 + ((colorsX[i] - l4) * i6 >> 16);
            }
            if (j2 >= 50) {
                int j6 = (50 - i2) * Rasterizer3D.anIntArray1469[j2 - i2];
                anIntArray1678[l] = j + (l2 + ((viewportTextureX[k1] - l2) * j6 >> 16) << Client.viewDistance) / 50;
                anIntArray1679[l] = k + (l3 + ((viewportTextureY[k1] - l3) * j6 >> 16) << Client.viewDistance) / 50;
                anIntArray1680[l++] = l4 + ((colorsZ[i] - l4) * j6 >> 16);
            }
        }
        if (j2 >= 50) {
            anIntArray1678[l] = viewportX[k1];
            anIntArray1679[l] = viewportY[k1];
            anIntArray1680[l++] = colorsZ[i];
        } else {
            int i3 = viewportTextureX[k1];
            int i4 = viewportTextureY[k1];
            int i5 = colorsZ[i];
            if (i2 >= 50) {
                int k6 = (50 - j2) * Rasterizer3D.anIntArray1469[i2 - j2];
                anIntArray1678[l] = j + (i3 + ((viewportTextureX[j1] - i3) * k6 >> 16) << Client.viewDistance) / 50;
                anIntArray1679[l] = k + (i4 + ((viewportTextureY[j1] - i4) * k6 >> 16) << Client.viewDistance) / 50;
                anIntArray1680[l++] = i5 + ((colorsY[i] - i5) * k6 >> 16);
            }
            if (l1 >= 50) {
                int l6 = (50 - j2) * Rasterizer3D.anIntArray1469[l1 - j2];
                anIntArray1678[l] = j + (i3 + ((viewportTextureX[i1] - i3) * l6 >> 16) << Client.viewDistance) / 50;
                anIntArray1679[l] = k + (i4 + ((viewportTextureY[i1] - i4) * l6 >> 16) << Client.viewDistance) / 50;
                anIntArray1680[l++] = i5 + ((colorsX[i] - i5) * l6 >> 16);
            }
        }
        int j3 = anIntArray1678[0];
        int j4 = anIntArray1678[1];
        int j5 = anIntArray1678[2];
        int i7 = anIntArray1679[0];
        int j7 = anIntArray1679[1];
        int k7 = anIntArray1679[2];
        if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
            Rasterizer3D.hidden = false;
            if (l == 3) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Raster.maxRight || j4 > Raster.maxRight || j5 > Raster.maxRight) {
                    Rasterizer3D.hidden = true;
                }
                int l7;
                if (types == null) {
                    l7 = 0;
                } else {
                    l7 = types[i] & 3;
                }
                if (l7 == 0) {
                    Rasterizer3D.drawGouraudTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], -1f, -1f, -1f, bufferOffset);
                } else if (l7 == 1) {
                    Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, Rasterizer3D.hsl2rgb[colorsX[i]], -1f, -1f, -1f, bufferOffset);
                } else if (l7 == 2) {
					/*
					 * int k9 = anIntArray1643[j8];
					int k10 = anIntArray1644[j8];
					int k11 = anIntArray1645[j8];
					 */
                    int j8 = types[i] >> 2;
                    int k9 = texturesX[j8];
                    int k10 = texturesY[j8];
                    int k11 = texturesZ[j8];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], viewportTextureX[k9], viewportTextureX[k10], viewportTextureX[k11], viewportTextureY[k9], viewportTextureY[k10], viewportTextureY[k11], viewportTextureZ[k9], viewportTextureZ[k10], viewportTextureZ[k11], colors[i], -1f, -1f, -1f, bufferOffset);
                } else if (l7 == 3) {
                    int k8 = types[i] >> 2;
                    int l9 = texturesX[k8];
                    int l10 = texturesY[k8];
                    int l11 = texturesZ[k8];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, colorsX[i], colorsX[i], colorsX[i], viewportTextureX[l9], viewportTextureX[l10], viewportTextureX[l11], viewportTextureY[l9], viewportTextureY[l10], viewportTextureY[l11], viewportTextureZ[l9], viewportTextureZ[l10], viewportTextureZ[l11], colors[i], -1f, -1f, -1f, bufferOffset);
                }
            }
            if (l == 4) {
                if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Raster.maxRight || j4 > Raster.maxRight || j5 > Raster.maxRight || anIntArray1678[3] < 0 || anIntArray1678[3] > Raster.maxRight) {
                    Rasterizer3D.hidden = true;
                }
                int i8;
                if (types == null) {
                    i8 = 0;
                } else {
                    i8 = types[i] & 3;
                }
                if (i8 == 0) {
                    Rasterizer3D.drawGouraudTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], -1f, -1f, -1f, bufferOffset);
                    Rasterizer3D.drawGouraudTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], anIntArray1680[0], anIntArray1680[2], anIntArray1680[3], -1f, -1f, -1f, bufferOffset);
                    return;
                }
                if (i8 == 1) {
                    int l8 = Rasterizer3D.hsl2rgb[colorsX[i]];
                    Rasterizer3D.drawFlatTriangle(i7, j7, k7, j3, j4, j5, l8, -1f, -1f, -1f, bufferOffset);
                    Rasterizer3D.drawFlatTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], l8, -1f, -1f, -1f, bufferOffset);
                    return;
                }
                if (i8 == 2) {
                    int i9 = types[i] >> 2;
                    int i10 = texturesX[i9];
                    int i11 = texturesY[i9];
                    int i12 = texturesZ[i9];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, anIntArray1680[0], anIntArray1680[1], anIntArray1680[2], viewportTextureX[i10], viewportTextureX[i11], viewportTextureX[i12], viewportTextureY[i10], viewportTextureY[i11], viewportTextureY[i12], viewportTextureZ[i10], viewportTextureZ[i11], viewportTextureZ[i12], colors[i], -1f, -1f, -1f, bufferOffset);
                    Rasterizer3D.drawTexturedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], anIntArray1680[0], anIntArray1680[2], anIntArray1680[3], viewportTextureX[i10], viewportTextureX[i11], viewportTextureX[i12], viewportTextureY[i10], viewportTextureY[i11], viewportTextureY[i12], viewportTextureZ[i10], viewportTextureZ[i11], viewportTextureZ[i12], colors[i], -1f, -1f, -1f, bufferOffset);
                    return;
                }
                if (i8 == 3) {
                    int j9 = types[i] >> 2;
                    int j10 = texturesX[j9];
                    int j11 = texturesY[j9];
                    int j12 = texturesZ[j9];
                    Rasterizer3D.drawTexturedTriangle(i7, j7, k7, j3, j4, j5, colorsX[i], colorsX[i], colorsX[i], viewportTextureX[j10], viewportTextureX[j11], viewportTextureX[j12], viewportTextureY[j10], viewportTextureY[j11], viewportTextureY[j12], viewportTextureZ[j10], viewportTextureZ[j11], viewportTextureZ[j12], colors[i], -1f, -1f, -1f, bufferOffset);
                    Rasterizer3D.drawTexturedTriangle(i7, k7, anIntArray1679[3], j3, j5, anIntArray1678[3], colorsX[i], colorsX[i], colorsX[i], viewportTextureX[j10], viewportTextureX[j11], viewportTextureX[j12], viewportTextureY[j10], viewportTextureY[j11], viewportTextureY[j12], viewportTextureZ[j10], viewportTextureZ[j11], viewportTextureZ[j12], colors[i], -1f, -1f, -1f, bufferOffset);
                }
            }
        }
    }

    private boolean method486(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        if (j < k && j < l && j < i1)
            return false;
        if (j > k && j > l && j > i1)
            return false;
        if (i < j1 && i < k1 && i < l1)
            return false;
        return i <= j1 || i <= k1 || i <= l1;
    }

    private void read525Model(byte[] abyte0, int modelID) {
        Stream nc1 = new Stream(abyte0);
        Stream nc2 = new Stream(abyte0);
        Stream nc3 = new Stream(abyte0);
        Stream nc4 = new Stream(abyte0);
        Stream nc5 = new Stream(abyte0);
        Stream nc6 = new Stream(abyte0);
        Stream nc7 = new Stream(abyte0);
        nc1.position = abyte0.length - 23;
        int numVertices = nc1.getUnsignedShort();
        int numTriangles = nc1.getUnsignedShort();
        int numTextureTriangles = nc1.getUnsignedByte();
        int l1 = nc1.getUnsignedByte();
        boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
        int i2 = nc1.getUnsignedByte();
        int j2 = nc1.getUnsignedByte();
        int k2 = nc1.getUnsignedByte();
        int l2 = nc1.getUnsignedByte();
        int i3 = nc1.getUnsignedByte();
        int j3 = nc1.getUnsignedShort();
        int k3 = nc1.getUnsignedShort();
        int l3 = nc1.getUnsignedShort();
        int i4 = nc1.getUnsignedShort();
        int j4 = nc1.getUnsignedShort();
        int k4 = 0;
        int l4 = 0;
        int i5 = 0;
        byte[] x = null;
        byte[] O = null;
        byte[] J = null;
        byte[] F = null;
        byte[] cb = null;
        byte[] gb = null;
        byte[] lb = null;
        int[] kb = null;
        int[] y = null;
        int[] N = null;
        short[] D = null;
        int[] triangleColours2 = new int[numTriangles];
        if (numTextureTriangles > 0) {
            O = new byte[numTextureTriangles];
            nc1.position = 0;
            for (int j5 = 0; j5 < numTextureTriangles; j5++) {
                byte byte0 = O[j5] = nc1.getSignedByte();
                if (byte0 == 0) {
                    k4++;
                }
                if (byte0 >= 1 && byte0 <= 3) {
                    l4++;
                }
                if (byte0 == 2) {
                    i5++;
                }
            }
        }
        int k5 = numTextureTriangles;
        int l5 = k5;
        k5 += numVertices;
        int i6 = k5;
        if (l1 == 1) {
            k5 += numTriangles;
        }
        int j6 = k5;
        k5 += numTriangles;
        int k6 = k5;
        if (i2 == 255) {
            k5 += numTriangles;
        }
        int l6 = k5;
        if (k2 == 1) {
            k5 += numTriangles;
        }
        int i7 = k5;
        if (i3 == 1) {
            k5 += numVertices;
        }
        int j7 = k5;
        if (j2 == 1) {
            k5 += numTriangles;
        }
        int k7 = k5;
        k5 += i4;
        int l7 = k5;
        if (l2 == 1) {
            k5 += numTriangles * 2;
        }
        int i8 = k5;
        k5 += j4;
        int j8 = k5;
        k5 += numTriangles * 2;
        int k8 = k5;
        k5 += j3;
        int l8 = k5;
        k5 += k3;
        int i9 = k5;
        k5 += l3;
        int j9 = k5;
        k5 += k4 * 6;
        int k9 = k5;
        k5 += l4 * 6;
        int l9 = k5;
        k5 += l4 * 6;
        int i10 = k5;
        k5 += l4;
        int j10 = k5;
        k5 += l4;
        int k10 = k5;
        k5 += l4 + i5 * 2;
        verticesParticle = new int[numVertices];
        int[] vertexX = new int[numVertices];
        int[] vertexY = new int[numVertices];
        int[] vertexZ = new int[numVertices];
        int[] facePoint1 = new int[numTriangles];
        int[] facePoint2 = new int[numTriangles];
        int[] facePoint3 = new int[numTriangles];
        vertexData = new int[numVertices];
        types = new int[numTriangles];
        priorities = new int[numTriangles];
        alphas = new int[numTriangles];
        triangleLabels = new int[numTriangles];
        if (i3 == 1) {
            vertexData = new int[numVertices];
        }
        if (bool) {
            types = new int[numTriangles];
        }
        if (i2 == 255) {
            priorities = new int[numTriangles];
        } else {
        }
        if (j2 == 1) {
            alphas = new int[numTriangles];
        }
        if (k2 == 1) {
            triangleLabels = new int[numTriangles];
        }
        if (l2 == 1) {
            D = new short[numTriangles];
        }
        if (l2 == 1 && numTextureTriangles > 0) {
            x = new byte[numTriangles];
        }
        triangleColours2 = new int[numTriangles];
        int[] texTrianglesPoint1 = null;
        int[] texTrianglesPoint2 = null;
        int[] texTrianglesPoint3 = null;
        if (numTextureTriangles > 0) {
            texTrianglesPoint1 = new int[numTextureTriangles];
            texTrianglesPoint2 = new int[numTextureTriangles];
            texTrianglesPoint3 = new int[numTextureTriangles];
            if (l4 > 0) {
                kb = new int[l4];
                N = new int[l4];
                y = new int[l4];
                gb = new byte[l4];
                lb = new byte[l4];
                F = new byte[l4];
            }
            if (i5 > 0) {
                cb = new byte[i5];
                J = new byte[i5];
            }
        }
        nc1.position = l5;
        nc2.position = k8;
        nc3.position = l8;
        nc4.position = i9;
        nc5.position = i7;
        int l10 = 0;
        int i11 = 0;
        int j11 = 0;
        for (int k11 = 0; k11 < numVertices; k11++) {
            int l11 = nc1.getUnsignedByte();
            int j12 = 0;
            if ((l11 & 1) != 0) {
                j12 = nc2.readSignedSmart();
            }
            int l12 = 0;
            if ((l11 & 2) != 0) {
                l12 = nc3.readSignedSmart();
            }
            int j13 = 0;
            if ((l11 & 4) != 0) {
                j13 = nc4.readSignedSmart();
            }
            vertexX[k11] = l10 + j12;
            vertexY[k11] = i11 + l12;
            vertexZ[k11] = j11 + j13;
            l10 = vertexX[k11];
            i11 = vertexY[k11];
            j11 = vertexZ[k11];
            if (vertexData != null) {
                vertexData[k11] = nc5.getUnsignedByte();
            }
        }
        nc1.position = j8;
        nc2.position = i6;
        nc3.position = k6;
        nc4.position = j7;
        nc5.position = l6;
        nc6.position = l7;
        nc7.position = i8;
        for (int i12 = 0; i12 < numTriangles; i12++) {
            triangleColours2[i12] = nc1.getUnsignedShort();
            if (l1 == 1) {
                types[i12] = nc2.getSignedByte();
                if (types[i12] == 2) {
                    triangleColours2[i12] = 65535;
                }
                types[i12] = 0;
            }
            if (i2 == 255) {
                priorities[i12] = nc3.getSignedByte();
            }
            if (j2 == 1) {
                alphas[i12] = nc4.getSignedByte();
                if (alphas[i12] < 0) {
                    alphas[i12] = 256 + alphas[i12];
                }
            }
            if (k2 == 1) {
                triangleLabels[i12] = nc5.getUnsignedByte();
            }
            if (l2 == 1) {
                D[i12] = (short) (nc6.getUnsignedShort() - 1);
            }
            if (x != null) {
                if (D[i12] != -1) {
                    x[i12] = (byte) (nc7.getUnsignedByte() - 1);
                } else {
                    x[i12] = -1;
                }
            }
        }
        // /fix's triangle issue, but fucked up - no need, loading all 474-
        // models
        /*
         * try { for(int i12 = 0; i12 < numTriangles; i12++) {
         * triangleColours2[i12] = nc1.readUnsignedWord(); if(l1 == 1){
         * anIntArray1637[i12] = nc2.getSignedByte(); } if(i2 == 255){
         * anIntArray1638[i12] = nc3.getSignedByte(); } if(j2 == 1){
         * anIntArray1639[i12] = nc4.getSignedByte(); if(anIntArray1639[i12] <
         * 0) anIntArray1639[i12] = (256+anIntArray1639[i12]); } if(k2 == 1)
         * anIntArray1656[i12] = nc5.getUnsignedByte(); if(l2 == 1) D[i12] =
         * (short)(nc6.readUnsignedWord() - 1); if(x != null) if(D[i12] != -1)
         * x[i12] = (byte)(nc7.getUnsignedByte() -1); else x[i12] = -1; } }
         * catch (Exception ex) { }
         */
        nc1.position = k7;
        nc2.position = j6;
        int k12 = 0;
        int i13 = 0;
        int k13 = 0;
        int l13 = 0;
        for (int i14 = 0; i14 < numTriangles; i14++) {
            int j14 = nc2.getUnsignedByte();
            if (j14 == 1) {
                k12 = nc1.readSignedSmart() + l13;
                l13 = k12;
                i13 = nc1.readSignedSmart() + l13;
                l13 = i13;
                k13 = nc1.readSignedSmart() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 2) {
                i13 = k13;
                k13 = nc1.readSignedSmart() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 3) {
                k12 = k13;
                k13 = nc1.readSignedSmart() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 4) {
                int l14 = k12;
                k12 = i13;
                i13 = l14;
                k13 = nc1.readSignedSmart() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
        }
        nc1.position = j9;
        nc2.position = k9;
        nc3.position = l9;
        nc4.position = i10;
        nc5.position = j10;
        nc6.position = k10;
        for (int k14 = 0; k14 < numTextureTriangles; k14++) {
            int i15 = O[k14] & 0xff;
            if (i15 == 0) {
                texTrianglesPoint1[k14] = nc1.getUnsignedShort();
                texTrianglesPoint2[k14] = nc1.getUnsignedShort();
                texTrianglesPoint3[k14] = nc1.getUnsignedShort();
            }
            if (i15 == 1) {
                texTrianglesPoint1[k14] = nc2.getUnsignedShort();
                texTrianglesPoint2[k14] = nc2.getUnsignedShort();
                texTrianglesPoint3[k14] = nc2.getUnsignedShort();
                kb[k14] = nc3.getUnsignedShort();
                N[k14] = nc3.getUnsignedShort();
                y[k14] = nc3.getUnsignedShort();
                gb[k14] = nc4.getSignedByte();
                lb[k14] = nc5.getSignedByte();
                F[k14] = nc6.getSignedByte();
            }
            if (i15 == 2) {
                texTrianglesPoint1[k14] = nc2.getUnsignedShort();
                texTrianglesPoint2[k14] = nc2.getUnsignedShort();
                texTrianglesPoint3[k14] = nc2.getUnsignedShort();
                kb[k14] = nc3.getUnsignedShort();
                N[k14] = nc3.getUnsignedShort();
                y[k14] = nc3.getUnsignedShort();
                gb[k14] = nc4.getSignedByte();
                lb[k14] = nc5.getSignedByte();
                F[k14] = nc6.getSignedByte();
                cb[k14] = nc6.getSignedByte();
                J[k14] = nc6.getSignedByte();
            }
            if (i15 == 3) {
                texTrianglesPoint1[k14] = nc2.getUnsignedShort();
                texTrianglesPoint2[k14] = nc2.getUnsignedShort();
                texTrianglesPoint3[k14] = nc2.getUnsignedShort();
                kb[k14] = nc3.getUnsignedShort();
                N[k14] = nc3.getUnsignedShort();
                y[k14] = nc3.getUnsignedShort();
                gb[k14] = nc4.getSignedByte();
                lb[k14] = nc5.getSignedByte();
                F[k14] = nc6.getSignedByte();
            }
        }
        if (i2 != 255) {
            for (int i12 = 0; i12 < numTriangles; i12++) {
                priorities[i12] = i2;
            }
        }
        colors = triangleColours2;
        verticesCount = numVertices;
        trianglesCount = numTriangles;
        verticesX = vertexX;
        verticesY = vertexY;
        verticesZ = vertexZ;
        trianglesX = facePoint1;
        trianglesY = facePoint2;
        trianglesZ = facePoint3;
    }

    private void read622Model(byte[] abyte0, int modelID) {
        Stream nc1 = new Stream(abyte0);
        Stream nc2 = new Stream(abyte0);
        Stream nc3 = new Stream(abyte0);
        Stream nc4 = new Stream(abyte0);
        Stream nc5 = new Stream(abyte0);
        Stream nc6 = new Stream(abyte0);
        Stream nc7 = new Stream(abyte0);
        nc1.position = abyte0.length - 23;
        int numVertices = nc1.getUnsignedShort();
        int numTriangles = nc1.getUnsignedShort();
        int numTexTriangles = nc1.getUnsignedByte();
        int l1 = nc1.getUnsignedByte();
        boolean bool = (0x1 & l1 ^ 0xffffffff) == -2;
        boolean bool_26_ = (0x8 & l1) == 8;
        if (!bool_26_) {
            read525Model(abyte0, modelID);
            return;
        }
        int newformat = 0;
        if (bool_26_) {
            nc1.position -= 7;
            newformat = nc1.getUnsignedByte();
            scaledVertices = true;
            nc1.position += 6;
        }
        int i2 = nc1.getUnsignedByte();
        int j2 = nc1.getUnsignedByte();
        int k2 = nc1.getUnsignedByte();
        int l2 = nc1.getUnsignedByte();
        int i3 = nc1.getUnsignedByte();
        int j3 = nc1.getUnsignedShort();
        int k3 = nc1.getUnsignedShort();
        int l3 = nc1.getUnsignedShort();
        int i4 = nc1.getUnsignedShort();
        int j4 = nc1.getUnsignedShort();
        int k4 = 0;
        int l4 = 0;
        int i5 = 0;
        byte[] x = null;
        byte[] O = null;
        byte[] J = null;
        byte[] F = null;
        byte[] cb = null;
        byte[] gb = null;
        byte[] lb = null;
        int[] kb = null;
        int[] y = null;
        int[] N = null;
        short[] D = null;
        int[] triangleColours2 = new int[numTriangles];
        if (numTexTriangles > 0) {
            O = new byte[numTexTriangles];
            nc1.position = 0;
            for (int j5 = 0; j5 < numTexTriangles; j5++) {
                byte byte0 = O[j5] = nc1.getSignedByte();
                if (byte0 == 0) {
                    k4++;
                }
                if (byte0 >= 1 && byte0 <= 3) {
                    l4++;
                }
                if (byte0 == 2) {
                    i5++;
                }
            }
        }
        int k5 = numTexTriangles;
        int l5 = k5;
        k5 += numVertices;
        int i6 = k5;
        if (bool) {
            k5 += numTriangles;
        }
        if (l1 == 1) {
            k5 += numTriangles;
        }
        int j6 = k5;
        k5 += numTriangles;
        int k6 = k5;
        if (i2 == 255) {
            k5 += numTriangles;
        }
        int l6 = k5;
        if (k2 == 1) {
            k5 += numTriangles;
        }
        int i7 = k5;
        if (i3 == 1) {
            k5 += numVertices;
        }
        int j7 = k5;
        if (j2 == 1) {
            k5 += numTriangles;
        }
        int k7 = k5;
        k5 += i4;
        int l7 = k5;
        if (l2 == 1) {
            k5 += numTriangles * 2;
        }
        int i8 = k5;
        k5 += j4;
        int j8 = k5;
        k5 += numTriangles * 2;
        int k8 = k5;
        k5 += j3;
        int l8 = k5;
        k5 += k3;
        int i9 = k5;
        k5 += l3;
        int j9 = k5;
        k5 += k4 * 6;
        int k9 = k5;
        k5 += l4 * 6;
        int i_59_ = 6;
        if (newformat != 14) {
            if (newformat >= 15) {
                i_59_ = 9;
            }
        } else {
            i_59_ = 7;
        }
        int l9 = k5;
        k5 += i_59_ * l4;
        int i10 = k5;
        k5 += l4;
        int j10 = k5;
        k5 += l4;
        int k10 = k5;
        k5 += l4 + i5 * 2;
        verticesParticle = new int[numVertices];
        int[] vertexX = new int[numVertices];
        int[] vertexY = new int[numVertices];
        int[] vertexZ = new int[numVertices];
        int[] facePoint1 = new int[numTriangles];
        int[] facePoint2 = new int[numTriangles];
        int[] facePoint3 = new int[numTriangles];
        vertexData = new int[numVertices];
        types = new int[numTriangles];
        priorities = new int[numTriangles];
        alphas = new int[numTriangles];
        triangleLabels = new int[numTriangles];
        if (i3 == 1) {
            vertexData = new int[numVertices];
        }
        if (bool) {
            types = new int[numTriangles];
        }
        if (i2 == 255) {
            priorities = new int[numTriangles];
        } else {
        }
        if (j2 == 1) {
            alphas = new int[numTriangles];
        }
        if (k2 == 1) {
            triangleLabels = new int[numTriangles];
        }
        if (l2 == 1) {
            D = new short[numTriangles];
        }
        if (l2 == 1 && numTexTriangles > 0) {
            x = new byte[numTriangles];
        }
        triangleColours2 = new int[numTriangles];
        int[] texTrianglesPoint1 = null;
        int[] texTrianglesPoint2 = null;
        int[] texTrianglesPoint3 = null;
        if (numTexTriangles > 0) {
            texTrianglesPoint1 = new int[numTexTriangles];
            texTrianglesPoint2 = new int[numTexTriangles];
            texTrianglesPoint3 = new int[numTexTriangles];
            if (l4 > 0) {
                kb = new int[l4];
                N = new int[l4];
                y = new int[l4];
                gb = new byte[l4];
                lb = new byte[l4];
                F = new byte[l4];
            }
            if (i5 > 0) {
                cb = new byte[i5];
                J = new byte[i5];
            }
        }
        nc1.position = l5;
        nc2.position = k8;
        nc3.position = l8;
        nc4.position = i9;
        nc5.position = i7;
        int l10 = 0;
        int i11 = 0;
        int j11 = 0;
        for (int k11 = 0; k11 < numVertices; k11++) {
            int l11 = nc1.getUnsignedByte();
            int j12 = 0;
            if ((l11 & 1) != 0) {
                j12 = nc2.readSignedSmart();
            }
            int l12 = 0;
            if ((l11 & 2) != 0) {
                l12 = nc3.readSignedSmart();
            }
            int j13 = 0;
            if ((l11 & 4) != 0) {
                j13 = nc4.readSignedSmart();
            }
            vertexX[k11] = l10 + j12;
            vertexY[k11] = i11 + l12;
            vertexZ[k11] = j11 + j13;
            l10 = vertexX[k11];
            i11 = vertexY[k11];
            j11 = vertexZ[k11];
            if (vertexData != null) {
                vertexData[k11] = nc5.getUnsignedByte();
            }
        }
        nc1.position = j8;
        nc2.position = i6;
        nc3.position = k6;
        nc4.position = j7;
        nc5.position = l6;
        nc6.position = l7;
        nc7.position = i8;
        for (int i12 = 0; i12 < numTriangles; i12++) {
            triangleColours2[i12] = nc1.getUnsignedShort();
            if (l1 == 1) {
                types[i12] = nc2.getSignedByte();
                if (types[i12] == 2) {
                    triangleColours2[i12] = 65535;
                }
                types[i12] = 0;
            }
            if (i2 == 255) {
                priorities[i12] = nc3.getSignedByte();
            }
            if (j2 == 1) {
                alphas[i12] = nc4.getSignedByte();
                if (alphas[i12] < 0) {
                    alphas[i12] = 256 + alphas[i12];
                }
            }
            if (k2 == 1) {
                triangleLabels[i12] = nc5.getUnsignedByte();
            }
            if (l2 == 1) {
                D[i12] = (short) (nc6.getUnsignedShort() - 1);
            }
            if (x != null) {
                if (D[i12] != -1) {
                    x[i12] = (byte) (nc7.getUnsignedByte() - 1);
                } else {
                    x[i12] = -1;
                }
            }
        }

        nc1.position = k7;
        nc2.position = j6;
        int k12 = 0;
        int i13 = 0;
        int k13 = 0;
        int l13 = 0;
        for (int i14 = 0; i14 < numTriangles; i14++) {
            int j14 = nc2.getUnsignedByte();
            if (j14 == 1) {
                k12 = nc1.readSignedSmart() + l13;
                l13 = k12;
                i13 = nc1.readSignedSmart() + l13;
                l13 = i13;
                k13 = nc1.readSignedSmart() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 2) {
                i13 = k13;
                k13 = nc1.readSignedSmart() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 3) {
                k12 = k13;
                k13 = nc1.readSignedSmart() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
            if (j14 == 4) {
                int l14 = k12;
                k12 = i13;
                i13 = l14;
                k13 = nc1.readSignedSmart() + l13;
                l13 = k13;
                facePoint1[i14] = k12;
                facePoint2[i14] = i13;
                facePoint3[i14] = k13;
            }
        }
        nc1.position = j9;
        nc2.position = k9;
        nc3.position = l9;
        nc4.position = i10;
        nc5.position = j10;
        nc6.position = k10;
        for (int k14 = 0; k14 < numTexTriangles; k14++) {
            int i15 = O[k14] & 0xff;
            if (i15 == 0) {
                texTrianglesPoint1[k14] = nc1.getUnsignedShort();
                texTrianglesPoint2[k14] = nc1.getUnsignedShort();
                texTrianglesPoint3[k14] = nc1.getUnsignedShort();
            }
            if (i15 == 1) {
                texTrianglesPoint1[k14] = nc2.getUnsignedShort();
                texTrianglesPoint2[k14] = nc2.getUnsignedShort();
                texTrianglesPoint3[k14] = nc2.getUnsignedShort();

                if (newformat < 15) {
                    kb[k14] = nc3.getUnsignedShort();

                    if (newformat >= 14) {
                        N[k14] = nc3.getTribyte(-1);
                    } else {
                        N[k14] = nc3.getUnsignedShort();
                    }

                    y[k14] = nc3.getUnsignedShort();
                } else {
                    kb[k14] = nc3.getTribyte(-1);
                    N[k14] = nc3.getTribyte(-1);
                    y[k14] = nc3.getTribyte(-1);
                }

                gb[k14] = nc4.getSignedByte();
                lb[k14] = nc5.getSignedByte();
                F[k14] = nc6.getSignedByte();
            }
            if (i15 == 2) {
                texTrianglesPoint1[k14] = nc2.getUnsignedShort();
                texTrianglesPoint2[k14] = nc2.getUnsignedShort();
                texTrianglesPoint3[k14] = nc2.getUnsignedShort();

                if (newformat >= 15) {
                    kb[k14] = nc3.getTribyte(-1);
                    N[k14] = nc3.getTribyte(-1);
                    y[k14] = nc3.getTribyte(-1);
                } else {
                    kb[k14] = nc3.getUnsignedShort();
                    if (newformat < 14) {
                        N[k14] = nc3.getUnsignedShort();
                    } else {
                        N[k14] = nc3.getTribyte(-1);
                    }
                    y[k14] = nc3.getUnsignedShort();
                }
                gb[k14] = nc4.getSignedByte();
                lb[k14] = nc5.getSignedByte();
                F[k14] = nc6.getSignedByte();
                cb[k14] = nc6.getSignedByte();
                J[k14] = nc6.getSignedByte();
            }
            if (i15 == 3) {
                texTrianglesPoint1[k14] = nc2.getUnsignedShort();
                texTrianglesPoint2[k14] = nc2.getUnsignedShort();
                texTrianglesPoint3[k14] = nc2.getUnsignedShort();
                if (newformat < 15) {
                    kb[k14] = nc3.getUnsignedShort();
                    if (newformat < 14) {
                        N[k14] = nc3.getUnsignedShort();
                    } else {
                        N[k14] = nc3.getTribyte(-1);
                    }
                    y[k14] = nc3.getUnsignedShort();
                } else {
                    kb[k14] = nc3.getTribyte(-1);
                    N[k14] = nc3.getTribyte(-1);
                    y[k14] = nc3.getTribyte(-1);
                }
                gb[k14] = nc4.getSignedByte();
                lb[k14] = nc5.getSignedByte();
                F[k14] = nc6.getSignedByte();
            }
        }
        if (i2 != 255) {
            for (int i12 = 0; i12 < numTriangles; i12++) {
                priorities[i12] = i2;
            }
        }
        colors = triangleColours2;
        verticesCount = numVertices;
        trianglesCount = numTriangles;
        verticesX = vertexX;
        verticesY = vertexY;
        verticesZ = vertexZ;
        trianglesX = facePoint1;
        trianglesY = facePoint2;
        trianglesZ = facePoint3;
        if (!scaledVertices) {
            downscale();
        }
        translate(0, 6, 0);
        if (priorities != null) {
            for (int j = 0; j < priorities.length; j++) {
                priorities[j] = 10;
            }
        }
    }


    public int numberOfTriangleFaces;

    public void setTexture(int fromColor, int fromcolor, int tex) {
        int foundAmt = 0;
        int set2 = 0;
        texturesCount = foundAmt;
        if (types == null) {
            types = new int[foundAmt];
        }
        if (colors == null) {
            colors = new int[foundAmt];
        }
        texturesX = new int[foundAmt];
        texturesY = new int[foundAmt];
        texturesZ = new int[foundAmt];


        for (int i = 0; i < numberOfTriangleFaces; i++) {
            if (colors[i] >= fromColor && colors[i] <= fromcolor) {


                colors[i] = tex;
                types[i] = 3 + set2;
                set2 += 4;
            }
        }
    }

    private void readOldModel(byte[] data) {
        aBoolean1659 = false;
        verticesCount = 0;
        Stream first = new Stream(data);
        Stream second = new Stream(data);
        Stream third = new Stream(data);
        Stream fourth = new Stream(data);
        Stream fifth = new Stream(data);
        first.position = data.length - 18;
        int i = first.readUnsignedWord();
        int i_320_ = first.readUnsignedWord();
        int i_321_ = first.getUnsignedByte();
        int i_322_ = first.getUnsignedByte();
        int i_323_ = first.getUnsignedByte();
        int i_324_ = first.getUnsignedByte();
        int i_325_ = first.getUnsignedByte();
        int i_326_ = first.getUnsignedByte();
        int i_327_ = first.readUnsignedWord();
        int i_328_ = first.readUnsignedWord();
        int i_329_ = first.readUnsignedWord();
        int i_330_ = first.readUnsignedWord();
        int i_331_ = 0;
        int i_332_ = i_331_;
        i_331_ += i;
        int i_333_ = i_331_;
        i_331_ += i_320_;
        int i_334_ = i_331_;
        if (i_323_ == 255) {
            i_331_ += i_320_;
        } else {
            i_334_ = -i_323_ - 1;
        }
        int i_335_ = i_331_;
        if (i_325_ == 1) {
            i_331_ += i_320_;
        } else {
            i_335_ = -1;
        }
        int i_336_ = i_331_;
        if (i_322_ == 1) {
            i_331_ += i_320_;
        } else {
            i_336_ = -1;
        }
        int i_337_ = i_331_;
        if (i_326_ == 1) {
            i_331_ += i;
        } else {
            i_337_ = -1;
        }
        int i_338_ = i_331_;
        if (i_324_ == 1) {
            i_331_ += i_320_;
        } else {
            i_338_ = -1;
        }
        int i_339_ = i_331_;
        i_331_ += i_330_;
        int i_340_ = i_331_;
        i_331_ += i_320_ * 2;
        int i_341_ = i_331_;
        i_331_ += i_321_ * 6;
        int i_342_ = i_331_;
        i_331_ += i_327_;
        int i_343_ = i_331_;
        i_331_ += i_328_;
        int i_344_ = i_331_;
        i_331_ += i_329_;
        verticesCount = i; // ???????????????
        trianglesCount = i_320_;
        texturesCount = i_321_;
        verticesParticle = new int[i];
        verticesX = new int[i];
        verticesY = new int[i];
        verticesZ = new int[i];
        trianglesX = new int[i_320_];
        trianglesY = new int[i_320_];
        trianglesZ = new int[i_320_];
        texturesX = new int[i_321_];
        texturesY = new int[i_321_];
        texturesZ = new int[i_321_];
        if (i_337_ >= 0) {
            vertexData = new int[i];
        }
        if (i_336_ >= 0) {
            types = new int[i_320_];
        }
        if (i_334_ >= 0) {
            priorities = new int[i_320_];
        } else {
            priority = -i_334_ - 1;
        }
        if (i_338_ >= 0) {
            alphas = new int[i_320_];
        }
        if (i_335_ >= 0) {
            triangleLabels = new int[i_320_];
        }
        colors = new int[i_320_];
        first.position = i_332_;
        second.position = i_342_;
        third.position = i_343_;
        fourth.position = i_344_;
        fifth.position = i_337_;
        int i_345_ = 0;
        int i_346_ = 0;
        int i_347_ = 0;
        for (int i_348_ = 0; i_348_ < i; i_348_++) {
            int i_349_ = first.getUnsignedByte();
            int i_350_ = 0;
            if ((i_349_ & 0x1) != 0) {
                i_350_ = second.readSignedSmart();
            }
            int i_351_ = 0;
            if ((i_349_ & 0x2) != 0) {
                i_351_ = third.readSignedSmart();
            }
            int i_352_ = 0;
            if ((i_349_ & 0x4) != 0) {
                i_352_ = fourth.readSignedSmart();
            }
            verticesX[i_348_] = i_345_ + i_350_;
            verticesY[i_348_] = i_346_ + i_351_;
            verticesZ[i_348_] = i_347_ + i_352_;
            i_345_ = verticesX[i_348_];
            i_346_ = verticesY[i_348_];
            i_347_ = verticesZ[i_348_];
            if (vertexData != null) {
                vertexData[i_348_] = fifth.getUnsignedByte();
            }
        }
        first.position = i_340_;
        second.position = i_336_;
        third.position = i_334_;
        fourth.position = i_338_;
        fifth.position = i_335_;
        for (int i_353_ = 0; i_353_ < i_320_; i_353_++) {
            colors[i_353_] = first.readUnsignedWord();
            if (types != null) {
                types[i_353_] = second.getUnsignedByte();
            }
            if (priorities != null) {
                priorities[i_353_] = third.getUnsignedByte();
            }
            if (alphas != null) {
                alphas[i_353_] = fourth.getUnsignedByte();
            }
            if (triangleLabels != null) {
                triangleLabels[i_353_] = fifth.getUnsignedByte();
            }
        }
        first.position = i_339_;
        second.position = i_333_;
        int i_354_ = 0;
        int i_355_ = 0;
        int i_356_ = 0;
        int i_357_ = 0;
        for (int i_358_ = 0; i_358_ < i_320_; i_358_++) {
            int i_359_ = second.getUnsignedByte();
            if (i_359_ == 1) {
                i_354_ = first.readSignedSmart() + i_357_;
                i_357_ = i_354_;
                i_355_ = first.readSignedSmart() + i_357_;
                i_357_ = i_355_;
                i_356_ = first.readSignedSmart() + i_357_;
                i_357_ = i_356_;
                trianglesX[i_358_] = i_354_;
                trianglesY[i_358_] = i_355_;
                trianglesZ[i_358_] = i_356_;
            }
            if (i_359_ == 2) {
                i_355_ = i_356_;
                i_356_ = first.readSignedSmart() + i_357_;
                i_357_ = i_356_;
                trianglesX[i_358_] = i_354_;
                trianglesY[i_358_] = i_355_;
                trianglesZ[i_358_] = i_356_;
            }
            if (i_359_ == 3) {
                i_354_ = i_356_;
                i_356_ = first.readSignedSmart() + i_357_;
                i_357_ = i_356_;
                trianglesX[i_358_] = i_354_;
                trianglesY[i_358_] = i_355_;
                trianglesZ[i_358_] = i_356_;
            }
            if (i_359_ == 4) {
                int i_360_ = i_354_;
                i_354_ = i_355_;
                i_355_ = i_360_;
                i_356_ = first.readSignedSmart() + i_357_;
                i_357_ = i_356_;
                trianglesX[i_358_] = i_354_;
                trianglesY[i_358_] = i_355_;
                trianglesZ[i_358_] = i_356_;
            }
        }
        first.position = i_341_;
        for (int i_361_ = 0; i_361_ < i_321_; i_361_++) {
            texturesX[i_361_] = first.readUnsignedWord();
            texturesY[i_361_] = first.readUnsignedWord();
            texturesZ[i_361_] = first.readUnsignedWord();
        }

        if (i == 83801 || i == 98005) {
            if (priorities != null) {
                for (int j1 = 0; j1 < priorities.length; j1++) {
                    priorities[j1] = 11;
                }
            }
        }
    }

    //
    public void setTexture(int fromColor, int tex) {
        int foundAmt = 0;
        int set2 = 0;
        for (int i = 0; i < colors.length; i++) {
            if (fromColor == colors[i]) {
                foundAmt++;
            }
        }
        texturesCount = foundAmt;
        if (types == null) {
            types = new int[foundAmt];
        }
        if (colors == null) {
            colors = new int[foundAmt];
        }
        texturesX = new int[foundAmt];
        texturesY = new int[foundAmt];
        texturesZ = new int[foundAmt];
        int assigned = 0;
        for (int i = 0; i < trianglesCount; i++) {
            if (fromColor == colors[i]) {
                colors[i] = tex;
                types[i] = 3 + set2;
                set2 += 4;
                texturesX[assigned] = trianglesX[i];
                texturesY[assigned] = trianglesY[i];
                texturesZ[assigned] = trianglesZ[i];
                assigned++;
            }
        }
    }

    public void setTexture(int tex) {
        texturesCount = trianglesCount;
        int set2 = 0;
        if (types == null) {
            types = new int[trianglesCount];
        }
        if (colors == null) {
            colors = new int[trianglesCount];
        }
        texturesX = new int[trianglesCount];
        texturesY = new int[trianglesCount];
        texturesZ = new int[trianglesCount];

        for (int i = 0; i < trianglesCount; i++) {
            colors[i] = tex;
            types[i] = 3 + set2;
            set2 += 4;
            texturesX[i] = trianglesX[i];
            texturesY[i] = trianglesY[i];
            texturesZ[i] = trianglesZ[i];
        }
    }

    public void method1337(int j) {
        for (int k = 0; k < trianglesCount; k++) {
            colors[k] = j;
        }
    }

    public void method1338(int j) {
        j += 100;
        int kcolor = 0;
        for (int k = 0; k < trianglesCount; k++) {
            kcolor = colors[k];
            if (k + j >= 0) {
                colors[k] = kcolor + j;
            }
        }
    }

    public void method1339(int j) {
        j += 1;
        for (int k = 0; k < trianglesCount; k++) {
            if (k + j >= 0) {
                colors[k] = k + j;
            }
        }
    }

    public void downscale() {
        for (int i = 0; i < verticesCount; ++i) {
            System.out.println("Pre DownscaleX: " + verticesX[i]);
            verticesX[i] = verticesX[i] + 7 >> 2;
            System.out.println("Post DownscaleX: " + verticesX[i]);
            System.out.println("Pre DownscaleY: " + verticesY[i]);
            verticesY[i] = verticesY[i] + 7 >> 2;
            System.out.println("Post DownscaleY: " + verticesY[i]);
            System.out.println("Pre DownscaleZ: " + verticesZ[i]);
            verticesZ[i] = verticesZ[i] + 7 >> 2;
            System.out.println("Post DownscaleZ: " + verticesZ[i]);
        }
    }

    public void upscale() {
        for (int i = 0; i < verticesCount; ++i) {
            verticesX[i] = verticesX[i] + 7 << 2;
            verticesY[i] = verticesY[i] + 7 << 2;
            verticesZ[i] = verticesZ[i] + 7 << 2;
        }
    }

    public void scale2(int i) {
        for (int i1 = 0; i1 < verticesCount; i1++) {
            verticesX[i1] = verticesX[i1] >> i;
            verticesY[i1] = verticesY[i1] >> i;
            verticesZ[i1] = verticesZ[i1] >> i;
        }
    }

    public void scale2Old(int i) {
        for (int i1 = 0; i1 < verticesCount; i1++) {
            verticesX[i1] = verticesX[i1] / i;
            verticesY[i1] = verticesY[i1] / i;
            verticesZ[i1] = verticesZ[i1] / i;
        }
    }

    public void upscale(double i) {
        for (int i1 = 0; i1 < verticesCount; i1++) {
            System.out.println("Pre upscaleX: " + verticesX[i1]);
            verticesX[i1] = (int) ( verticesX[i1] * i);
            System.out.println("Post upscaleX: " + verticesX[i1]);
            System.out.println("Pre upscaleY: " + verticesY[i1]);
            verticesY[i1] = (int) (verticesY[i1] * i);
            System.out.println("Post upscaleY: " + verticesY[i1]);
            System.out.println("Pre upscaleZ: " + verticesZ[i1]);
            verticesZ[i1] = (int) (verticesZ[i1] * i);
            System.out.println("Post upscaleZ: " + verticesZ[i1]);
        }
    }
}
