package org.necrotic.client.scene;

import org.necrotic.Configuration;
import org.necrotic.client.media.Rasterizer3D;
import org.necrotic.client.media.VertexNormal;
import org.necrotic.client.Client;
import org.necrotic.client.collection.NodeList;
import org.necrotic.client.media.Raster;
import org.necrotic.client.media.renderable.Model;
import org.necrotic.client.media.renderable.Renderable;
import org.necrotic.client.scene.tile.*;

public final class Scene {

	//ADDED
	public static final int MAX_FAR_Z = Configuration.MAX_TILE_CLAMPING; //Tile Clamping technically
	public static int farZ = Configuration.TILE_CLAMPING;
	public static int renderDistanceClamp = Configuration.RENDER_DISTANCE_CLAMP;
	public static int viewDistance = 9;

	private static boolean isClicked;

	private static boolean[][] TILE_VISIBILITY_MAP;

	private static boolean[][][][] TILE_VISIBILITY_MAPS = new boolean[8][32][MAX_FAR_Z * 2 + 1][MAX_FAR_Z * 2 + 1];

	private static NodeList tileCache = new NodeList();

	private static GameObject[] aClass28Array462 = new GameObject[100];

	private static final SceneCluster[] A_SCENE_CLUSTER_ARRAY_476 = new SceneCluster[500];
	private static SceneCluster[][] aSceneClusterArrayArray474;
	private static int tilesRemaining;
	private static int plane;
	private static int tileCount;
	private static int minRegionX;
	private static int maxRegionX;
	private static int minRegionY;
	private static int maxRegionY;
	private static int xCameraPositionTile;
	private static int absoluteCameraY;
	private static int xCameraPosition;
	private static int zCameraPosition;
	private static int yCameraPosition;
	private static int yCurveSine;
	private static int yCurveCosine;
	private static int xCurveSine;
	private static int xCurveCosine;
	private static int mouseX;
	private static int mouseY;
	public static int clickedTileX = -1;
	public static int clickedTileY = -1;
	private static final int anInt472;
	private static int anInt475;
	private static int anInt493;
	private static int anInt494;
	private static int anInt495;
	private static int anInt496;
	private static int anInt497;
	private static int anInt498;
	private static final int[] anIntArray463 = {53, -53, -53, 53};
	private static final int[] anIntArray464 = {-53, -53, 53, 53};
	private static final int[] anIntArray465 = {-45, 45, 45, -45};
	private static final int[] anIntArray466 = {45, 45, -45, -45};
	private static int[] anIntArray473;
	private static final int[] anIntArray478 = {19, 55, 38, 155, 255, 110, 137, 205, 76};
	private static final int[] anIntArray479 = {160, 192, 80, 96, 0, 144, 80, 48, 160};
	private static final int[] anIntArray480 = {76, 8, 137, 4, 0, 1, 38, 2, 19};
	private static final int[] anIntArray481 = {0, 0, 2, 0, 0, 2, 1, 1, 0};
	private static final int[] anIntArray482 = {2, 0, 0, 2, 0, 0, 0, 4, 4};
	private static final int[] anIntArray483 = {0, 4, 4, 8, 0, 0, 8, 0, 0};
	private static final int[] anIntArray484 = {1, 1, 0, 0, 0, 8, 0, 0, 8};
	private static final int[] textureRGBColour = {41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 43086, 41, 41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 3131, 41, 41, 41};
	public static boolean lowDetail = true;
	
	static {
		anInt472 = 4;
		anIntArray473 = new int[anInt472];
		aSceneClusterArrayArray474 = new SceneCluster[anInt472][500];
	}

	
	static void method277(int i, int j, int k, int l, int i1, int j1, int l1, int i2) {
		SceneCluster sceneCluster = new SceneCluster();
		sceneCluster.anInt787 = j / 128;
		sceneCluster.anInt788 = l / 128;
		sceneCluster.anInt789 = l1 / 128;
		sceneCluster.anInt790 = i1 / 128;
		sceneCluster.anInt791 = i2;
		sceneCluster.anInt792 = j;
		sceneCluster.anInt793 = l;
		sceneCluster.anInt794 = l1;
		sceneCluster.anInt795 = i1;
		sceneCluster.anInt796 = j1;
		sceneCluster.anInt797 = k;
		aSceneClusterArrayArray474[i][anIntArray473[i]++] = sceneCluster;
	}

	public static void method310(int i, int j, int k, int l, int[] ai) {
		anInt495 = 0;
		anInt496 = 0;
		anInt497 = k;
		anInt498 = l;
		anInt493 = k / 2;
		anInt494 = l / 2;
		boolean[][][][] aflag = new boolean[9][32][MAX_FAR_Z * 2 + 3][MAX_FAR_Z * 2 + 3];
		for (int i1 = 128; i1 <= 384; i1 += 32) {
			for (int j1 = 0; j1 < 2048; j1 += 64) {
				yCurveSine = Rasterizer3D.SINE[i1];
				yCurveCosine = Rasterizer3D.COSINE[i1];
				xCurveSine = Rasterizer3D.SINE[j1];
				xCurveCosine = Rasterizer3D.COSINE[j1];
				int l1 = (i1 - 128) / 32;
				int j2 = j1 / 64;
				for (int l2 = -26; l2 <= 26; l2++) {
					for (int j3 = -26; j3 <= 26; j3++) {
						int k3 = l2 * 128;
						int i4 = j3 * 128;
						boolean flag2 = false;
						for (int k4 = -i; k4 <= j; k4 += 128) {
							if (!method311(ai[l1] + k4, i4, k3)) {
								continue;
							}
							flag2 = true;
							break;
						}

						aflag[l1][j2][l2 + MAX_FAR_Z + 1][j3 + MAX_FAR_Z + 1] = flag2;
					}

				}

			}

		}

		for (int k1 = 0; k1 < 8; k1++) {
			for (int i2 = 0; i2 < 32; i2++) {
				for (int k2 = -farZ; k2 < farZ; k2++) {
					for (int i3 = -farZ; i3 < farZ; i3++) {
						boolean flag1 = false;
						label0:
						for (int l3 = -1; l3 <= 1; l3++) {
							for (int j4 = -1; j4 <= 1; j4++) {
								if (aflag[k1][i2][k2 + l3 + MAX_FAR_Z + 1][i3 + j4 + MAX_FAR_Z + 1]) {
									flag1 = true;
								} else if (aflag[k1][(i2 + 1) % 31][k2 + l3 + MAX_FAR_Z + 1][i3 + j4 + MAX_FAR_Z + 1]) {
									flag1 = true;
								} else if (aflag[k1 + 1][i2][k2 + l3 + MAX_FAR_Z + 1][i3 + j4 + MAX_FAR_Z + 1]) {
									flag1 = true;
								} else {
									if (!aflag[k1 + 1][(i2 + 1) % 31][k2 + l3 + MAX_FAR_Z + 1][i3 + j4 + MAX_FAR_Z + 1]) {
										continue;
									}

									flag1 = true;
								}

								break label0;
							}
						}

						TILE_VISIBILITY_MAPS[k1][i2][k2 + farZ][i3 + farZ] = flag1;
					}
				}
			}
		}
	}

	static boolean gpu = false;
	private static boolean method311(int i, int j, int k) {
		int l = j * xCurveSine + k * xCurveCosine >> 16;
		int i1 = j * xCurveCosine - k * xCurveSine >> 16;
		int j1 = i * yCurveSine + i1 * yCurveCosine >> 16;
		int k1 = i * yCurveCosine - i1 * yCurveSine >> 16;

		if (j1 < 50 || j1 > renderDistanceClamp && !gpu) {
			return false;
		}

		int l1 = anInt493 + (l * Client.viewDistance) / j1;
		int i2 = anInt494 + (k1 * Client.viewDistance) / j1;
		return l1 >= anInt495 && l1 <= anInt497 && i2 >= anInt496 && i2 <= anInt498;
	}

	public static void nullify() {
		aClass28Array462 = null;
		anIntArray473 = null;
		aSceneClusterArrayArray474 = null;
		tileCache = null;
		TILE_VISIBILITY_MAPS = null;
		TILE_VISIBILITY_MAP = null;
	}

	private boolean aBoolean434;
	private final int planeCount;
	private final int width;
	private final int length;
	private int activePlane;
	private int anInt488;
	private final int[] anIntArray486;
	private final int[] anIntArray487;
	private final int[][] anIntArrayArray489 = {new int[16], {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
	private final int[][] anIntArrayArray490 = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3}, {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
	private final int[][][] tileHeights;
	private final int[][][] anIntArrayArrayArray445;
	private final Tile[][][] tiles;
	private final GameObject[] obj5Cache;
	private int obj5CacheCurrPos;

	public Scene(int[][][] ai) {
		int i = 104;// was parameter
		int j = 104;// was parameter
		int k = 4;// was parameter
		aBoolean434 = true;
		obj5Cache = new GameObject[5000];
		anIntArray486 = new int[10000];
		anIntArray487 = new int[10000];
		planeCount = k;
		width = j;
		length = i;
		tiles = new Tile[k][j][i];
		anIntArrayArrayArray445 = new int[k][j + 1][i + 1];
		tileHeights = ai;
		initToNull();
	}

	public void clearObj5Cache() {
		for (int i = 0; i < obj5CacheCurrPos; i++) {
			GameObject gameObject = obj5Cache[i];
			method289(gameObject);
			obj5Cache[i] = null;
		}

		obj5CacheCurrPos = 0;
	}

	public void initToNull() {
		for (int j = 0; j < planeCount; j++) {
			for (int k = 0; k < width; k++) {
				for (int i1 = 0; i1 < length; i1++) {
					tiles[j][k][i1] = null;
				}

			}

		}
		for (int l = 0; l < anInt472; l++) {
			for (int j1 = 0; j1 < anIntArray473[l]; j1++) {
				aSceneClusterArrayArray474[l][j1] = null;
			}

			anIntArray473[l] = 0;
		}

		for (int k1 = 0; k1 < obj5CacheCurrPos; k1++) {
			obj5Cache[k1] = null;
		}

		obj5CacheCurrPos = 0;
		for (int l1 = 0; l1 < aClass28Array462.length; l1++) {
			aClass28Array462[l1] = null;
		}

	}

	public void initTiles(int i) {
		activePlane = i;
		for (int k = 0; k < width; k++) {
			for (int l = 0; l < length; l++) {
				if (tiles[i][k][l] == null) {
					tiles[i][k][l] = new Tile(i, k, l);
				}
			}
		}
	}

	void method276(int i, int j) {
		Tile class30_sub3 = tiles[0][j][i];
		for (int l = 0; l < 3; l++) {
			Tile class30_sub3_1 = tiles[l][j][i] = tiles[l + 1][j][i];
			if (class30_sub3_1 != null) {
				class30_sub3_1.plane--;
				for (int j1 = 0; j1 < class30_sub3_1.anInt1317; j1++) {
					GameObject class28 = class30_sub3_1.obj5Array[j1];
					if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == j && class28.anInt525 == i) {
						class28.anInt517--;
					}
				}
			}
		}

		if (tiles[0][j][i] == null) {
			tiles[0][j][i] = new Tile(0, j, i);
		}

		tiles[0][j][i].aClass30_Sub3_1329 = class30_sub3;
		tiles[3][j][i] = null;
	}

	void method278(int i, int j, int k, int l) {
		Tile class30_sub3 = tiles[i][j][k];

		if (class30_sub3 != null) {
			tiles[i][j][k].anInt1321 = l;
		}
	}

	void method279(int i, int j, int k, int l, int i1, int overlaytex, int underlaytex, int k1, int l1, int i2, int j2, int k2, int l2, int i3, int j3, int k3, int l3, int i4, int j4, int k4, int l4, boolean tex) {
		if (l == 0) {
			SceneTilePaint class43 = new SceneTilePaint(k2, l2, i3, j3, underlaytex, k4, false, tex);

			for (int i5 = i; i5 >= 0; i5--) {
				if (tiles[i5][j][k] == null) {
					tiles[i5][j][k] = new Tile(i5, j, k);
				}
			}

			tiles[i][j][k].aClass43_1311 = class43;
			return;
		}

		if (l == 1) {
			SceneTilePaint class43_1 = new SceneTilePaint(k3, l3, i4, j4, overlaytex, l4, k1 == l1 && k1 == i2 && k1 == j2, tex);

			for (int j5 = i; j5 >= 0; j5--) {
				if (tiles[j5][j][k] == null) {
					tiles[j5][j][k] = new Tile(j5, j, k);
				}
			}

			tiles[i][j][k].aClass43_1311 = class43_1;
			return;
		}

		SceneTileModel class40 = new SceneTileModel(k, k3, j3, i2, overlaytex, underlaytex, i4, i1, k2, k4, i3, j2, l1, k1, l, j4, l3, l2, j, l4, tex);

		for (int k5 = i; k5 >= 0; k5--) {
			if (tiles[k5][j][k] == null) {
				tiles[k5][j][k] = new Tile(k5, j, k);
			}
		}

		tiles[i][j][k].aClass40_1312 = class40;
	}

	public void addGroundDecoration(int i, int j, int k, Renderable class30_sub2_sub4, byte byte0, int i1, int j1, int var) {
		if (class30_sub2_sub4 == null) {
			return;
		}

		GroundObject class49 = new GroundObject();
		class49.aClass30_Sub2_Sub4_814 = class30_sub2_sub4;
		class49.anInt812 = j1 * 128 + 64;
		class49.anInt813 = k * 128 + 64;
		class49.anInt811 = j;
		class49.uid = i1;
		class49.setNewUID(var);
		class49.aByte816 = byte0;
		if (tiles[i][j1][k] == null) {
			tiles[i][j1][k] = new Tile(i, j1, k);
		}

		tiles[i][j1][k].obj3 = class49;
	}

	public void method281(int i, int j, Renderable class30_sub2_sub4, int k, Renderable class30_sub2_sub4_1, Renderable class30_sub2_sub4_2, int l, int i1) {
		GroundItem groundItem = new GroundItem();
		groundItem.aClass30_Sub2_Sub4_48 = class30_sub2_sub4_2;
		groundItem.anInt46 = i * 128 + 64;
		groundItem.anInt47 = i1 * 128 + 64;
		groundItem.anInt45 = k;
		groundItem.uid = j;
		groundItem.aClass30_Sub2_Sub4_49 = class30_sub2_sub4;
		groundItem.aClass30_Sub2_Sub4_50 = class30_sub2_sub4_1;
		int j1 = 0;
		Tile class30_sub3 = tiles[l][i][i1];

		if (class30_sub3 != null) {
			for (int k1 = 0; k1 < class30_sub3.anInt1317; k1++) {
				if (class30_sub3.obj5Array[k1].aClass30_Sub2_Sub4_521 instanceof Model) {
					int l1 = ((Model) class30_sub3.obj5Array[k1].aClass30_Sub2_Sub4_521).anInt1654;
					if (l1 > j1) {
						j1 = l1;
					}
				}
			}
		}

		groundItem.anInt52 = j1;

		if (tiles[l][i][i1] == null) {
			tiles[l][i][i1] = new Tile(l, i, i1);
		}

		tiles[l][i][i1].obj4 = groundItem;
	}

	void method282(int i, Renderable class30_sub2_sub4, int j, int k, byte byte0, int l, Renderable class30_sub2_sub4_1, int i1, int j1, int k1, int var) {
		if (class30_sub2_sub4 == null && class30_sub2_sub4_1 == null) {
			return;
		}

		WallObject wallObject = new WallObject();
		wallObject.uid = j;
		wallObject.setNewUID(var);
		wallObject.aByte281 = byte0;
		wallObject.anInt274 = l * 128 + 64;
		wallObject.anInt275 = k * 128 + 64;
		wallObject.anInt273 = i1;
		wallObject.aClass30_Sub2_Sub4_278 = class30_sub2_sub4;
		wallObject.aClass30_Sub2_Sub4_279 = class30_sub2_sub4_1;
		wallObject.orientation = i;
		wallObject.orientation1 = j1;
		for (int l1 = k1; l1 >= 0; l1--) {
			if (tiles[l1][l][k] == null) {
				tiles[l1][l][k] = new Tile(l1, l, k);
			}
		}

		tiles[k1][l][k].obj1 = wallObject;
	}

	void method283(int i, int j, int k, int i1, int j1, int k1, Renderable class30_sub2_sub4, int l1, byte byte0, int i2, int j2) {
		if (class30_sub2_sub4 == null) {
			return;
		}

		DecorativeObject class26 = new DecorativeObject();
		class26.uid = i;
		class26.aByte506 = byte0;
		class26.anInt500 = l1 * 128 + 64 + j1;
		class26.anInt501 = j * 128 + 64 + i2;
		class26.anInt499 = k1;
		class26.aClass30_Sub2_Sub4_504 = class30_sub2_sub4;
		class26.anInt502 = j2;
		class26.anInt503 = k;
		for (int k2 = i1; k2 >= 0; k2--) {
			if (tiles[k2][l1][j] == null) {
				tiles[k2][l1][j] = new Tile(k2, l1, j);
			}
		}

		tiles[i1][l1][j].obj2 = class26;
	}

	public boolean addObject(int i, byte byte0, int j, int k, Renderable class30_sub2_sub4, int l, int i1, int j1, int k1, int l1, int var) {
		if (class30_sub2_sub4 == null) {
			return true;
		} else {
			int i2 = l1 * 128 + 64 * l;
			int j2 = k1 * 128 + 64 * k;
			return method287(i1, l1, k1, l, k, i2, j2, j, class30_sub2_sub4, j1, false, i, byte0, var);
		}
	}

	public boolean method285(int i, int j, int k, int l, int i1, int j1, int k1, Renderable class30_sub2_sub4, boolean flag) {
		if (class30_sub2_sub4 == null) {
			return true;
		}
		int l1 = k1 - j1;
		int i2 = i1 - j1;
		int j2 = k1 + j1;
		int k2 = i1 + j1;
		if (flag) {
			if (j > 640 && j < 1408) {
				k2 += 128;
			}
			if (j > 1152 && j < 1920) {
				j2 += 128;
			}
			if (j > 1664 || j < 384) {
				i2 -= 128;
			}
			if (j > 128 && j < 896) {
				l1 -= 128;
			}
		}
		l1 /= 128;
		i2 /= 128;
		j2 /= 128;
		k2 /= 128;
		return method287(i, l1, i2, j2 - l1 + 1, k2 - i2 + 1, k1, i1, k, class30_sub2_sub4, j, true, l, (byte) 0, 0);
	}

	public boolean method286(int j, int k, Renderable class30_sub2_sub4, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2) {
		return class30_sub2_sub4 == null || method287(j, l1, k2, i2 - l1 + 1, i1 - k2 + 1, j1, k, k1, class30_sub2_sub4, l, true, j2, (byte) 0, 0);
	}

	private boolean method287(int i, int j, int k, int l, int i1, int j1, int k1, int l1, Renderable class30_sub2_sub4, int i2, boolean flag, int j2, byte byte0, int var) {
		for (int k2 = j; k2 < j + l; k2++) {
			for (int l2 = k; l2 < k + i1; l2++) {
				if (k2 < 0 || l2 < 0 || k2 >= width || l2 >= length) {
					return false;
				}
				Tile class30_sub3 = tiles[i][k2][l2];
				if (class30_sub3 != null && class30_sub3.anInt1317 >= 5) {
					return false;
				}
			}

		}

		GameObject class28 = new GameObject();
		class28.uid = j2;
		class28.setNewUID(var);
		class28.aByte530 = byte0;
		class28.anInt517 = i;
		class28.anInt519 = j1;
		class28.anInt520 = k1;
		class28.anInt518 = l1;
		class28.aClass30_Sub2_Sub4_521 = class30_sub2_sub4;
		class28.anInt522 = i2;
		class28.anInt523 = j;
		class28.anInt525 = k;
		class28.anInt524 = j + l - 1;
		class28.anInt526 = k + i1 - 1;
		for (int i3 = j; i3 < j + l; i3++) {
			for (int j3 = k; j3 < k + i1; j3++) {
				int k3 = 0;
				if (i3 > j) {
					k3++;
				}
				if (i3 < j + l - 1) {
					k3 += 4;
				}
				if (j3 > k) {
					k3 += 8;
				}
				if (j3 < k + i1 - 1) {
					k3 += 2;
				}
				for (int l3 = i; l3 >= 0; l3--) {
					if (tiles[l3][i3][j3] == null) {
						tiles[l3][i3][j3] = new Tile(l3, i3, j3);
					}
				}

				Tile class30_sub3_1 = tiles[i][i3][j3];
				class30_sub3_1.obj5Array[class30_sub3_1.anInt1317] = class28;
				class30_sub3_1.anIntArray1319[class30_sub3_1.anInt1317] = k3;
				class30_sub3_1.anInt1320 |= k3;
				class30_sub3_1.anInt1317++;
			}

		}

		if (flag) {
			obj5Cache[obj5CacheCurrPos++] = class28;
		}
		return true;
	}

	private void method289(GameObject class28) {
		for (int j = class28.anInt523; j <= class28.anInt524; j++) {
			for (int k = class28.anInt525; k <= class28.anInt526; k++) {
				Tile class30_sub3 = tiles[class28.anInt517][j][k];
				if (class30_sub3 != null) {
					for (int l = 0; l < class30_sub3.anInt1317; l++) {
						if (class30_sub3.obj5Array[l] != class28) {
							continue;
						}
						class30_sub3.anInt1317--;
						for (int i1 = l; i1 < class30_sub3.anInt1317; i1++) {
							class30_sub3.obj5Array[i1] = class30_sub3.obj5Array[i1 + 1];
							class30_sub3.anIntArray1319[i1] = class30_sub3.anIntArray1319[i1 + 1];
						}

						class30_sub3.obj5Array[class30_sub3.anInt1317] = null;
						break;
					}

					class30_sub3.anInt1320 = 0;
					for (int j1 = 0; j1 < class30_sub3.anInt1317; j1++) {
						class30_sub3.anInt1320 |= class30_sub3.anIntArray1319[j1];
					}
				}
			}
		}
	}

	void method290(int i, int k, int l, int i1) {
		Tile class30_sub3 = tiles[i1][l][i];

		if (class30_sub3 == null) {
			return;
		}

		DecorativeObject class26 = class30_sub3.obj2;

		if (class26 != null) {
			int j1 = l * 128 + 64;
			int k1 = i * 128 + 64;
			class26.anInt500 = j1 + (class26.anInt500 - j1) * k / 16;
			class26.anInt501 = k1 + (class26.anInt501 - k1) * k / 16;
		}
	}

	public void method291(int i, int j, int k, byte byte0) {
		Tile class30_sub3 = tiles[j][i][k];

		if (byte0 != -119) {
			aBoolean434 = !aBoolean434;
		}

		if (class30_sub3 != null) {
			class30_sub3.obj1 = null;
		}
	}

	public void method292(int j, int k, int l) {
		Tile class30_sub3 = tiles[k][l][j];
		if (class30_sub3 != null) {
			class30_sub3.obj2 = null;
		}
	}

	public void method293(int i, int k, int l) {
		Tile class30_sub3 = tiles[i][k][l];
		if (class30_sub3 == null) {
			return;
		}

		for (int j1 = 0; j1 < class30_sub3.anInt1317; j1++) {
			GameObject class28 = class30_sub3.obj5Array[j1];

			if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == k && class28.anInt525 == l) {
				method289(class28);
				return;
			}
		}
	}

	public void method294(int i, int j, int k) {
		Tile class30_sub3 = tiles[i][k][j];
		if (class30_sub3 == null) {
			return;
		}

		class30_sub3.obj3 = null;
	}

	public void method295(int i, int j, int k) {
		Tile class30_sub3 = tiles[i][j][k];
		if (class30_sub3 != null) {
			class30_sub3.obj4 = null;
		}
	}

	public WallObject method296(int i, int j, int k) {
		Tile class30_sub3 = tiles[i][j][k];
		if (class30_sub3 == null) {
			return null;
		} else {
			return class30_sub3.obj1;
		}
	}

	public DecorativeObject method297(int i, int k, int l) {
		Tile class30_sub3 = tiles[l][i][k];
		if (class30_sub3 == null) {
			return null;
		} else {
			return class30_sub3.obj2;
		}
	}

	public GameObject method298(int i, int j, int k) {
		Tile class30_sub3 = tiles[k][i][j];
		if (class30_sub3 == null) {
			return null;
		}

		for (int l = 0; l < class30_sub3.anInt1317; l++) {
			GameObject class28 = class30_sub3.obj5Array[l];

			if ((class28.uid >> 29 & 3) == 2 && class28.anInt523 == i && class28.anInt525 == j) {
				return class28;
			}
		}

		return null;
	}

	public GroundObject method299(int i, int j, int k) {
		Tile class30_sub3 = tiles[k][j][i];

		if (class30_sub3 == null || class30_sub3.obj3 == null) {
			return null;
		} else {
			return class30_sub3.obj3;
		}
	}

	public int method300(int i, int j, int k) {
		Tile tile = tiles[i][j][k];
		if (tile == null || tile.obj1 == null) {
			return 0;
		} else {
			return tile.obj1.uid;
		}
	}

	// fetchWallObjectNewUID
	public int fetchWallObjectNewUID(int i, int j, int k) {
		Tile tile = tiles[i][j][k];
		if (tile == null || tile.obj1 == null) {
			return 0;
		} else {
			return tile.obj1.getNewUID();
		}
	}

	public int method301(int i, int j, int l) {
		Tile tile = tiles[i][j][l];
		if (tile == null || tile.obj2 == null) {
			return 0;
		} else {
			return tile.obj2.uid;
		}
	}

	// fetchWallDecorationNewUID
	public int fetchWallDecorationNewUID(int i, int j, int l) {
		Tile tile = tiles[i][j][l];
		if (tile == null || tile.obj2 == null) {
			return 0;
		} else {
			return tile.obj2.getNewUID();
		}
	}

	// fetchObjectMeshUID
	public int method302(int z, int x, int y) {
		if (z == 4) {
			z = 0;
		}
		Tile tile = tiles[z][x][y];
		if (tile == null) {
			return 0;
		}
		for (int l = 0; l < tile.anInt1317; l++) {
			GameObject interactableObject = tile.obj5Array[l];
			if ((interactableObject.uid >> 29 & 3) == 2 && interactableObject.anInt523 == x && interactableObject.anInt525 == y) {
				return interactableObject.uid;
			}
		}
		return 0;
	}

	// fetchObjectMeshNewUID
	public int fetchObjectMeshNewUID(int z, int x, int y) {
		Tile tile = tiles[z][x][y];
		if (tile == null) {
			return 0;
		}
		for (int l = 0; l < tile.anInt1317; l++) {
			GameObject interactableObject = tile.obj5Array[l];
			if ((interactableObject.uid >> 29 & 3) == 2 && interactableObject.anInt523 == x && interactableObject.anInt525 == y) {
				return interactableObject.getNewUID();
			}
		}
		return 0;
	}

	// fetchGroundDecorationUID
	public int method303(int i, int j, int k) {
		Tile tile = tiles[i][j][k];
		if (tile == null || tile.obj3 == null) {
			return 0;
		} else {
			return tile.obj3.uid;
		}
	}

	// fetchGroundDecorationNewUID
	public int fetchGroundDecorationNewUID(int i, int j, int k) {
		Tile tile = tiles[i][j][k];
		if (tile == null || tile.obj3 == null) {
			return 0;
		} else {
			return tile.obj3.getNewUID();
		}
	}

	public int fetchObjectIDTagForPosition(int z, int x, int y, int objectMesh) {
		Tile tile = tiles[z][x][y];
		if (tile == null) {
			return -1;
		}
		if (tile.obj1 != null && tile.obj1.uid == objectMesh) {
			return tile.obj1.aByte281 & 0xff;
		}
		if (tile.obj2 != null && tile.obj2.uid == objectMesh) {
			return tile.obj2.aByte506 & 0xff;
		}
		if (tile.obj3 != null && tile.obj3.uid == objectMesh) {
			return tile.obj3.aByte816 & 0xff;
		}
		for (int i1 = 0; i1 < tile.anInt1317; i1++) {
			// anInt1317 = count?
			if (tile.obj5Array[i1].uid == objectMesh) {
				return tile.obj5Array[i1].aByte530 & 0xff;
			}
		}

		return -1;
	}

	void method305(int i, int k, int i1) {
		int j = 100;
		int l = 5500;
		int k1 = l >> 4;

		for (int l1 = 0; l1 < planeCount; l1++) {
			for (int i2 = 0; i2 < width; i2++) {
				for (int j2 = 0; j2 < length; j2++) {
					Tile class30_sub3 = tiles[l1][i2][j2];

					if (class30_sub3 != null) {
						WallObject class10 = class30_sub3.obj1;
						if (class10 != null && class10.aClass30_Sub2_Sub4_278 != null && class10.aClass30_Sub2_Sub4_278.aVertexNormalArray1425 != null) {
							method307(l1, 1, 1, i2, j2, (Model) class10.aClass30_Sub2_Sub4_278);
							if (class10.aClass30_Sub2_Sub4_279 != null && class10.aClass30_Sub2_Sub4_279.aVertexNormalArray1425 != null) {
								method307(l1, 1, 1, i2, j2, (Model) class10.aClass30_Sub2_Sub4_279);
								method308((Model) class10.aClass30_Sub2_Sub4_278, (Model) class10.aClass30_Sub2_Sub4_279, 0, 0, 0, false);
								((Model) class10.aClass30_Sub2_Sub4_279).method480(j, k1, k, i, i1);
							}
							((Model) class10.aClass30_Sub2_Sub4_278).method480(j, k1, k, i, i1);
						}
						for (int k2 = 0; k2 < class30_sub3.anInt1317; k2++) {
							GameObject class28 = class30_sub3.obj5Array[k2];
							if (class28 != null && class28.aClass30_Sub2_Sub4_521 != null && class28.aClass30_Sub2_Sub4_521.aVertexNormalArray1425 != null) {
								method307(l1, class28.anInt524 - class28.anInt523 + 1, class28.anInt526 - class28.anInt525 + 1, i2, j2, (Model) class28.aClass30_Sub2_Sub4_521);
								((Model) class28.aClass30_Sub2_Sub4_521).method480(j, k1, k, i, i1);
							}
						}

						GroundObject class49 = class30_sub3.obj3;
						if (class49 != null && class49.aClass30_Sub2_Sub4_814.aVertexNormalArray1425 != null) {
							method306(i2, l1, (Model) class49.aClass30_Sub2_Sub4_814, j2);
							((Model) class49.aClass30_Sub2_Sub4_814).method480(j, k1, k, i, i1);
						}
					}
				}

			}

		}

	}

	private void method306(int i, int j, Model model, int k) {
		if (i < width) {
			Tile class30_sub3 = tiles[j][i + 1][k];
			if (class30_sub3 != null && class30_sub3.obj3 != null && class30_sub3.obj3.aClass30_Sub2_Sub4_814.aVertexNormalArray1425 != null) {
				method308(model, (Model) class30_sub3.obj3.aClass30_Sub2_Sub4_814, 128, 0, 0, true);
			}
		}
		if (k < width) {
			Tile class30_sub3_1 = tiles[j][i][k + 1];
			if (class30_sub3_1 != null && class30_sub3_1.obj3 != null && class30_sub3_1.obj3.aClass30_Sub2_Sub4_814.aVertexNormalArray1425 != null) {
				method308(model, (Model) class30_sub3_1.obj3.aClass30_Sub2_Sub4_814, 0, 0, 128, true);
			}
		}
		if (i < width && k < length) {
			Tile class30_sub3_2 = tiles[j][i + 1][k + 1];
			if (class30_sub3_2 != null && class30_sub3_2.obj3 != null && class30_sub3_2.obj3.aClass30_Sub2_Sub4_814.aVertexNormalArray1425 != null) {
				method308(model, (Model) class30_sub3_2.obj3.aClass30_Sub2_Sub4_814, 128, 0, 128, true);
			}
		}
		if (i < width && k > 0) {
			Tile class30_sub3_3 = tiles[j][i + 1][k - 1];
			if (class30_sub3_3 != null && class30_sub3_3.obj3 != null && class30_sub3_3.obj3.aClass30_Sub2_Sub4_814.aVertexNormalArray1425 != null) {
				method308(model, (Model) class30_sub3_3.obj3.aClass30_Sub2_Sub4_814, 128, 0, -128, true);
			}
		}
	}

	private void method307(int i, int j, int k, int l, int i1, Model model) {
		boolean flag = true;
		int j1 = l;
		int k1 = l + j;
		int l1 = i1 - 1;
		int i2 = i1 + k;
		for (int j2 = i; j2 <= i + 1; j2++) {
			if (j2 != planeCount) {
				for (int k2 = j1; k2 <= k1; k2++) {
					if (k2 >= 0 && k2 < width) {
						for (int l2 = l1; l2 <= i2; l2++) {
							if (l2 >= 0 && l2 < length && (!flag || k2 >= k1 || l2 >= i2 || l2 < i1 && k2 != l)) {
								Tile class30_sub3 = tiles[j2][k2][l2];
								if (class30_sub3 != null) {
									int i3 = (tileHeights[j2][k2][l2] + tileHeights[j2][k2 + 1][l2] + tileHeights[j2][k2][l2 + 1] + tileHeights[j2][k2 + 1][l2 + 1]) / 4 - (tileHeights[i][l][i1] + tileHeights[i][l + 1][i1] + tileHeights[i][l][i1 + 1] + tileHeights[i][l + 1][i1 + 1]) / 4;
									WallObject class10 = class30_sub3.obj1;
									if (class10 != null && class10.aClass30_Sub2_Sub4_278 != null && class10.aClass30_Sub2_Sub4_278.aVertexNormalArray1425 != null) {
										method308(model, (Model) class10.aClass30_Sub2_Sub4_278, (k2 - l) * 128 + (1 - j) * 64, i3, (l2 - i1) * 128 + (1 - k) * 64, flag);
									}
									if (class10 != null && class10.aClass30_Sub2_Sub4_279 != null && class10.aClass30_Sub2_Sub4_279.aVertexNormalArray1425 != null) {
										method308(model, (Model) class10.aClass30_Sub2_Sub4_279, (k2 - l) * 128 + (1 - j) * 64, i3, (l2 - i1) * 128 + (1 - k) * 64, flag);
									}
									for (int j3 = 0; j3 < class30_sub3.anInt1317; j3++) {
										GameObject class28 = class30_sub3.obj5Array[j3];
										if (class28 != null && class28.aClass30_Sub2_Sub4_521 != null && class28.aClass30_Sub2_Sub4_521.aVertexNormalArray1425 != null) {
											int k3 = class28.anInt524 - class28.anInt523 + 1;
											int l3 = class28.anInt526 - class28.anInt525 + 1;
											method308(model, (Model) class28.aClass30_Sub2_Sub4_521, (class28.anInt523 - l) * 128 + (k3 - j) * 64, i3, (class28.anInt525 - i1) * 128 + (l3 - k) * 64, flag);
										}
									}

								}
							}
						}

					}
				}

				j1--;
				flag = false;
			}
		}

	}

	private void method308(Model model, Model model_1, int i, int j, int k, boolean flag) {
		anInt488++;
		int l = 0;
		int[] ai = model_1.verticesX;
		int i1 = model_1.verticesCount;
		for (int j1 = 0; j1 < model.verticesCount; j1++) {
			VertexNormal vertexNormal = model.aVertexNormalArray1425[j1];
			VertexNormal vertexNormal_1 = model.vertexNormals[j1];
			if (vertexNormal_1.magnitude != 0) {
				int i2 = model.verticesY[j1] - j;
				if (i2 <= model_1.bottomY) {
					int j2 = model.verticesX[j1] - i;
					if (j2 >= model_1.anInt1646 && j2 <= model_1.anInt1647) {
						int k2 = model.verticesZ[j1] - k;
						if (k2 >= model_1.anInt1649 && k2 <= model_1.anInt1648) {
							for (int l2 = 0; l2 < i1; l2++) {
								VertexNormal vertexNormal_2 = model_1.aVertexNormalArray1425[l2];
								VertexNormal vertexNormal_3 = model_1.vertexNormals[l2];
								if (j2 == ai[l2] && k2 == model_1.verticesZ[l2] && i2 == model_1.verticesY[l2] && vertexNormal_3.magnitude != 0) {
									vertexNormal.x += vertexNormal_3.x;
									vertexNormal.y += vertexNormal_3.y;
									vertexNormal.z += vertexNormal_3.z;
									vertexNormal.magnitude += vertexNormal_3.magnitude;
									vertexNormal_2.x += vertexNormal_1.x;
									vertexNormal_2.y += vertexNormal_1.y;
									vertexNormal_2.z += vertexNormal_1.z;
									vertexNormal_2.magnitude += vertexNormal_1.magnitude;
									l++;
									anIntArray486[j1] = anInt488;
									anIntArray487[l2] = anInt488;
								}
							}

						}
					}
				}
			}
		}

		if (l < 3 || !flag) {
			return;
		}
		for (int k1 = 0; k1 < model.trianglesCount; k1++) {
			if (anIntArray486[model.trianglesX[k1]] == anInt488 && anIntArray486[model.trianglesY[k1]] == anInt488 && anIntArray486[model.trianglesZ[k1]] == anInt488) {
				model.types[k1] = -1;
			}
		}

		for (int l1 = 0; l1 < model_1.trianglesCount; l1++) {
			if (anIntArray487[model_1.trianglesX[l1]] == anInt488 && anIntArray487[model_1.trianglesY[l1]] == anInt488 && anIntArray487[model_1.trianglesZ[l1]] == anInt488) {
				model_1.types[l1] = -1;
			}
		}

	}

	public void method309(int[] ai, int i, int k, int l, int i1) {
		int j = 512;// was parameter
		Tile class30_sub3 = tiles[k][l][i1];
		if (class30_sub3 == null) {
			return;
		}
		SceneTilePaint class43 = class30_sub3.aClass43_1311;
		if (class43 != null) {
			if (Configuration.hdMinimap && class43.anInt716 != 12345678) {
				if (class43.anInt722 == 0) {
					return;
				}
				int hs = class43.anInt716 & ~0x7f;
				int l1 = class43.anInt719 & 0x7f;
				int l2 = class43.neColor & 0x7f;
				int l3 = (class43.anInt716 & 0x7f) - l1;
				int l4 = (class43.anInt717 & 0x7f) - l2;
				l1 <<= 2;
				l2 <<= 2;
				for (int k1 = 0; k1 < 4; k1++) {
					if (!class43.textured) {
						ai[i] = Rasterizer3D.hsl2rgb[hs | l1 >> 2];
						ai[i + 1] = Rasterizer3D.hsl2rgb[hs | l1 * 3 + l2 >> 4];
						ai[i + 2] = Rasterizer3D.hsl2rgb[hs | l1 + l2 >> 3];
						ai[i + 3] = Rasterizer3D.hsl2rgb[hs | l1 + l2 * 3 >> 4];
					} else {
						int j1 = class43.anInt722;
						int lig = 0xff - ((l1 >> 1) * (l1 >> 1) >> 8);
						ai[i] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
						lig = 0xff - ((l1 * 3 + l2 >> 3) * (l1 * 3 + l2 >> 3) >> 8);
						ai[i + 1] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
						lig = 0xff - ((l1 + l2 >> 2) * (l1 + l2 >> 2) >> 8);
						ai[i + 2] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
						lig = 0xff - ((l1 + l2 * 3 >> 3) * (l1 + l2 * 3 >> 3) >> 8);
						ai[i + 3] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
					}
					l1 += l3;
					l2 += l4;
					i += j;
				}
				return;
			}
			int j1 = class43.anInt722;
			if (j1 == 0) {
				return;
			}
			for (int k1 = 0; k1 < 4; k1++) {
				ai[i] = j1;
				ai[i + 1] = j1;
				ai[i + 2] = j1;
				ai[i + 3] = j1;
				i += j;
			}

			return;
		}
		SceneTileModel class40 = class30_sub3.aClass40_1312;
		if (class40 == null) {
			return;
		}
		int l1 = class40.anInt684;
		int i2 = class40.anInt685;
		int j2 = class40.anInt686;
		int k2 = class40.anInt687;
		int[] ai1 = anIntArrayArray489[l1];
		int[] ai2 = anIntArrayArray490[i2];
		int l2 = 0;
		if (Configuration.hdMinimap && class40.color62 != 12345678) {
			int hs1 = class40.color62 & ~0x7f;
			int l11 = class40.color92 & 0x7f;
			int l21 = class40.color82 & 0x7f;
			int l31 = (class40.color62 & 0x7f) - l11;
			int l41 = (class40.color72 & 0x7f) - l21;
			l11 <<= 2;
			l21 <<= 2;
			for (int k1 = 0; k1 < 4; k1++) {
				if (!class40.textured) {
					if (ai1[ai2[l2++]] != 0) {
						ai[i] = Rasterizer3D.hsl2rgb[hs1 | l11 >> 2];
					}
					if (ai1[ai2[l2++]] != 0) {
						ai[i + 1] = Rasterizer3D.hsl2rgb[hs1 | l11 * 3 + l21 >> 4];
					}
					if (ai1[ai2[l2++]] != 0) {
						ai[i + 2] = Rasterizer3D.hsl2rgb[hs1 | l11 + l21 >> 3];
					}
					if (ai1[ai2[l2++]] != 0) {
						ai[i + 3] = Rasterizer3D.hsl2rgb[hs1 | l11 + l21 * 3 >> 4];
					}
				} else {
					int j1 = k2;
					if (ai1[ai2[l2++]] != 0) {
						int lig = 0xff - ((l11 >> 1) * (l11 >> 1) >> 8);
						ai[i] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
					}
					if (ai1[ai2[l2++]] != 0) {
						int lig = 0xff - ((l11 * 3 + l21 >> 3) * (l11 * 3 + l21 >> 3) >> 8);
						ai[i + 1] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
					}
					if (ai1[ai2[l2++]] != 0) {
						int lig = 0xff - ((l11 + l21 >> 2) * (l11 + l21 >> 2) >> 8);
						ai[i + 2] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
					}
					if (ai1[ai2[l2++]] != 0) {
						int lig = 0xff - ((l11 + l21 * 3 >> 3) * (l11 + l21 * 3 >> 3) >> 8);
						ai[i + 3] = ((j1 & 0xff00ff) * lig & ~0xff00ff) + ((j1 & 0xff00) * lig & 0xff0000) >> 8;
					}
				}
				l11 += l31;
				l21 += l41;
				i += j;
			}
			if (j2 != 0 && class40.color61 != 12345678) {
				i -= j << 2;
				l2 -= 16;
				hs1 = class40.color61 & ~0x7f;
				l11 = class40.color91 & 0x7f;
				l21 = class40.color81 & 0x7f;
				l31 = (class40.color61 & 0x7f) - l11;
				l41 = (class40.color71 & 0x7f) - l21;
				l11 <<= 2;
				l21 <<= 2;
				for (int k1 = 0; k1 < 4; k1++) {
					if (ai1[ai2[l2++]] == 0) {
						ai[i] = Rasterizer3D.hsl2rgb[hs1 | l11 >> 2];
					}
					if (ai1[ai2[l2++]] == 0) {
						ai[i + 1] = Rasterizer3D.hsl2rgb[hs1 | l11 * 3 + l21 >> 4];
					}
					if (ai1[ai2[l2++]] == 0) {
						ai[i + 2] = Rasterizer3D.hsl2rgb[hs1 | l11 + l21 >> 3];
					}
					if (ai1[ai2[l2++]] == 0) {
						ai[i + 3] = Rasterizer3D.hsl2rgb[hs1 | l11 + l21 * 3 >> 4];
					}
					l11 += l31;
					l21 += l41;
					i += j;
				}
			}
			return;
		}
		if (j2 != 0) {
			for (int i3 = 0; i3 < 4; i3++) {
				ai[i] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				ai[i + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				ai[i + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				ai[i + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				i += j;
			}

			return;
		}
		for (int j3 = 0; j3 < 4; j3++) {
			if (ai1[ai2[l2++]] != 0) {
				ai[i] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 1] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 2] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 3] = k2;
			}
			i += j;
		}

	}

	public void click(int i, int j) {
		isClicked = true;
		mouseX = j;
		mouseY = i;
		clickedTileX = -1;
		clickedTileY = -1;
	}

	public void render(int cameraX, int cameraY, int xCurve, int cameraZ, int plane, int yCurve) {
		Rasterizer3D.clearDepthBuffer();
		if (cameraX < 0) {
			cameraX = 0;
		} else if (cameraX >= width * 128) {
			cameraX = width * 128 - 1;
		}
		if (cameraY < 0) {
			cameraY = 0;
		} else if (cameraY >= length * 128) {
			cameraY = length * 128 - 1;
		}
		tileCount++;
		int distance = farZ;
		yCurveSine = Rasterizer3D.SINE[yCurve];
		yCurveCosine = Rasterizer3D.COSINE[yCurve];
		xCurveSine = Rasterizer3D.SINE[xCurve];
		xCurveCosine = Rasterizer3D.COSINE[xCurve];
		TILE_VISIBILITY_MAP = TILE_VISIBILITY_MAPS[(yCurve - 128) / 32][xCurve / 64];
		xCameraPosition = cameraX;
		zCameraPosition = cameraZ;
		yCameraPosition = cameraY;
		xCameraPositionTile = cameraX / 128;
		absoluteCameraY = cameraY / 128;
		Scene.plane = plane;
		minRegionX = xCameraPositionTile - distance;
		if (minRegionX < 0) {
			minRegionX = 0;
		}
		minRegionY = absoluteCameraY - distance;
		if (minRegionY < 0) {
			minRegionY = 0;
		}
		maxRegionX = xCameraPositionTile + distance;
		if (maxRegionX > width) {
			maxRegionX = width;
		}
		maxRegionY = absoluteCameraY + distance;
		if (maxRegionY > length) {
			maxRegionY = length;
		}
		processCulling();
		tilesRemaining = 0;
		for (int z = activePlane; z < planeCount; z++) {
			Tile[][] tiles = this.tiles[z];
			for (int x = minRegionX; x < maxRegionX; x++) {
				for (int y = minRegionY; y < maxRegionY; y++) {
					Tile tile = tiles[x][y];
					if (tile != null) {
						if (tile.anInt1321 > plane || !TILE_VISIBILITY_MAP[x - xCameraPositionTile + distance]
								[y - absoluteCameraY + distance] && tileHeights[z][x][y] - cameraZ < 1) {
							tile.visible = false;
							tile.rendersVisible = false;
							tile.anInt1325 = 0;
						} else {
							tile.visible = true;
							tile.rendersVisible = true;
							tile.aBoolean1324 = tile.anInt1317 > 0;
							tilesRemaining++;
						}
					}
				}

			}

		}

		for (int z = activePlane; z < planeCount; z++) {
			Tile[][] tiles = this.tiles[z];
			for (int dx = -farZ; dx <= 0; dx++) {
				int maxX = xCameraPositionTile + dx;
				int minX = xCameraPositionTile - dx;
				if (maxX >= minRegionX || minX < maxRegionX) {
					for (int dy = -farZ; dy <= 0; dy++) {
						int maxY = absoluteCameraY + dy;
						int minY = absoluteCameraY - dy;
						if (maxX >= minRegionX) {
							if (maxY >= minRegionY) {
								Tile tile = tiles[maxX][maxY];
								if (tile != null && tile.visible) {
									renderTile(tile, true);
								}
							}
							if (minY < maxRegionY) {
								Tile class30_sub3_2 = tiles[maxX][minY];
								if (class30_sub3_2 != null && class30_sub3_2.visible) {
									renderTile(class30_sub3_2, true);
								}
							}
						}
						if (minX < maxRegionX) {
							if (maxY >= minRegionY) {
								Tile class30_sub3_3 = tiles[minX][maxY];
								if (class30_sub3_3 != null && class30_sub3_3.visible) {
									renderTile(class30_sub3_3, true);
								}
							}
							if (minY < maxRegionY) {
								Tile tile = tiles[minX][minY];
								if (tile != null && tile.visible) {
									renderTile(tile, true);
								}
							}
						}
						if (tilesRemaining == 0) {
							isClicked = false;
							return;
						}
					}

				}
			}

		}

		for (int z = activePlane; z < planeCount; z++) {
			Tile[][] tiles = this.tiles[z];
			for (int x = -farZ; x <= 0; x++) {
				int maxX = xCameraPositionTile + x;
				int minX = xCameraPositionTile - x;
				if (maxX >= minRegionX || minX < maxRegionX) {
					for (int y = -farZ; y <= 0; y++) {
						int maxY = absoluteCameraY + y;
						int minY = absoluteCameraY - y;
						if (maxX >= minRegionX) {
							if (maxY >= minRegionY) {
								Tile tile = tiles[maxX][maxY];
								if (tile != null && tile.visible) {
									renderTile(tile, false);
								}
							}
							if (minY < maxRegionY) {
								Tile tile = tiles[maxX][minY];
								if (tile != null && tile.visible) {
									renderTile(tile, false);
								}
							}
						}
						if (minX < maxRegionX) {
							if (maxY >= minRegionY) {
								Tile tile = tiles[minX][maxY];
								if (tile != null && tile.visible) {
									renderTile(tile, false);
								}
							}
							if (minY < maxRegionY) {
								Tile tile = tiles[minX][minY];
								if (tile != null && tile.visible) {
									renderTile(tile, false);
								}
							}
						}
						if (tilesRemaining == 0) {
							isClicked = false;
							return;
						}
					}

				}
			}

		}

		isClicked = false;
	}

	private void renderTile(Tile newTile, boolean visible) {
		tileCache.insertHead(newTile);
		do {
			Tile front;
			do {
				front = (Tile) tileCache.popHead();
				if (front == null) {
					return;
				}
			} while (!front.rendersVisible);
			int i = front.tileX;
			int j = front.tileY;
			int k = front.plane;
			int l = front.originalPlane;
			Tile[][] aclass30_sub3 = this.tiles[k];
			if (front.visible) {
				if (visible) {
					if (k > 0) {
						Tile class30_sub3_2 = this.tiles[k - 1][i][j];
						if (class30_sub3_2 != null && class30_sub3_2.rendersVisible) {
							continue;
						}
					}
					if (i <= xCameraPositionTile && i > minRegionX) {
						Tile class30_sub3_3 = aclass30_sub3[i - 1][j];
						if (class30_sub3_3 != null && class30_sub3_3.rendersVisible && (class30_sub3_3.visible || (front.anInt1320 & 1) == 0)) {
							continue;
						}
					}
					if (i >= xCameraPositionTile && i < maxRegionX - 1) {
						Tile class30_sub3_4 = aclass30_sub3[i + 1][j];
						if (class30_sub3_4 != null && class30_sub3_4.rendersVisible && (class30_sub3_4.visible || (front.anInt1320 & 4) == 0)) {
							continue;
						}
					}
					if (j <= absoluteCameraY && j > minRegionY) {
						Tile class30_sub3_5 = aclass30_sub3[i][j - 1];
						if (class30_sub3_5 != null && class30_sub3_5.rendersVisible && (class30_sub3_5.visible || (front.anInt1320 & 8) == 0)) {
							continue;
						}
					}
					if (j >= absoluteCameraY && j < maxRegionY - 1) {
						Tile class30_sub3_6 = aclass30_sub3[i][j + 1];
						if (class30_sub3_6 != null && class30_sub3_6.rendersVisible && (class30_sub3_6.visible || (front.anInt1320 & 2) == 0)) {
							continue;
						}
					}
				} else {
					visible = true;
				}
				front.visible = false;
				if (front.aClass30_Sub3_1329 != null) {
					Tile class30_sub3_7 = front.aClass30_Sub3_1329;
					if (class30_sub3_7.aClass43_1311 != null) {
						if (!method320(0, i, j)) {
							renderPlainTile(class30_sub3_7.aClass43_1311, 0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, i, j);
						}
					} else if (class30_sub3_7.aClass40_1312 != null && !method320(0, i, j)) {
						renderShapedTile(i, yCurveSine, xCurveSine, class30_sub3_7.aClass40_1312, yCurveCosine, j, xCurveCosine);
					}
					WallObject class10 = class30_sub3_7.obj1;
					if (class10 != null) {
						class10.aClass30_Sub2_Sub4_278.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class10.anInt274 - xCameraPosition, class10.anInt273 - zCameraPosition, class10.anInt275 - yCameraPosition, class10.uid, class10.getNewUID(), 10);
					}
					for (int i2 = 0; i2 < class30_sub3_7.anInt1317; i2++) {
						GameObject class28 = class30_sub3_7.obj5Array[i2];
						if (class28 != null) {
							class28.aClass30_Sub2_Sub4_521.render(class28.anInt522, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class28.anInt519 - xCameraPosition, class28.anInt518 - zCameraPosition, class28.anInt520 - yCameraPosition, class28.uid, class28.getNewUID(), 17);
						}
					}

				}
				boolean flag1 = false;
				if (front.aClass43_1311 != null) {
					if (!method320(l, i, j)) {
						flag1 = true;
						renderPlainTile(front.aClass43_1311, l, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, i, j);
					}
				} else if (front.aClass40_1312 != null && !method320(l, i, j)) {
					flag1 = true;
					renderShapedTile(i, yCurveSine, xCurveSine, front.aClass40_1312, yCurveCosine, j, xCurveCosine);
				}
				int j1 = 0;
				int j2 = 0;
				WallObject class10_3 = front.obj1;
				DecorativeObject class26_1 = front.obj2;
				if (class10_3 != null || class26_1 != null) {
					if (xCameraPositionTile == i) {
						j1++;
					} else if (xCameraPositionTile < i) {
						j1 += 2;
					}
					if (absoluteCameraY == j) {
						j1 += 3;
					} else if (absoluteCameraY > j) {
						j1 += 6;
					}
					j2 = anIntArray478[j1];
					front.anInt1328 = anIntArray480[j1];
				}
				if (class10_3 != null) {
					if ((class10_3.orientation & anIntArray479[j1]) != 0) {
						if (class10_3.orientation == 16) {
							front.anInt1325 = 3;
							front.anInt1326 = anIntArray481[j1];
							front.anInt1327 = 3 - front.anInt1326;
						} else if (class10_3.orientation == 32) {
							front.anInt1325 = 6;
							front.anInt1326 = anIntArray482[j1];
							front.anInt1327 = 6 - front.anInt1326;
						} else if (class10_3.orientation == 64) {
							front.anInt1325 = 12;
							front.anInt1326 = anIntArray483[j1];
							front.anInt1327 = 12 - front.anInt1326;
						} else {
							front.anInt1325 = 9;
							front.anInt1326 = anIntArray484[j1];
							front.anInt1327 = 9 - front.anInt1326;
						}
					} else {
						front.anInt1325 = 0;
					}
					if ((class10_3.orientation & j2) != 0 && !method321(l, i, j, class10_3.orientation)) {
						class10_3.aClass30_Sub2_Sub4_278.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class10_3.anInt274 - xCameraPosition, class10_3.anInt273 - zCameraPosition, class10_3.anInt275 - yCameraPosition, class10_3.uid, class10_3.getNewUID(), 10);
					}
					if ((class10_3.orientation1 & j2) != 0 && !method321(l, i, j, class10_3.orientation1)) {
						class10_3.aClass30_Sub2_Sub4_279.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class10_3.anInt274 - xCameraPosition, class10_3.anInt273 - zCameraPosition, class10_3.anInt275 - yCameraPosition, class10_3.uid, class10_3.getNewUID(), 10);
					}
				}
				if (class26_1 != null && !method322(l, i, j, class26_1.aClass30_Sub2_Sub4_504.modelHeight)) {
					if ((class26_1.anInt502 & j2) != 0) {
						class26_1.aClass30_Sub2_Sub4_504.render(class26_1.anInt503, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class26_1.anInt500 - xCameraPosition, class26_1.anInt499 - zCameraPosition, class26_1.anInt501 - yCameraPosition, class26_1.uid, class26_1.getNewUID(), 15);
					} else if ((class26_1.anInt502 & 0x300) != 0) {
						int j4 = class26_1.anInt500 - xCameraPosition;
						int l5 = class26_1.anInt499 - zCameraPosition;
						int k6 = class26_1.anInt501 - yCameraPosition;
						int i8 = class26_1.anInt503;
						int k9;
						if (i8 == 1 || i8 == 2) {
							k9 = -j4;
						} else {
							k9 = j4;
						}
						int k10;
						if (i8 == 2 || i8 == 3) {
							k10 = -k6;
						} else {
							k10 = k6;
						}
						if ((class26_1.anInt502 & 0x100) != 0 && k10 < k9) {
							int i11 = j4 + anIntArray463[i8];
							int k11 = k6 + anIntArray464[i8];
							class26_1.aClass30_Sub2_Sub4_504.render(i8 * 512 + 256, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, i11, l5, k11, class26_1.uid, class26_1.getNewUID(), 15);
						}
						if ((class26_1.anInt502 & 0x200) != 0 && k10 > k9) {
							int j11 = j4 + anIntArray465[i8];
							int l11 = k6 + anIntArray466[i8];
							class26_1.aClass30_Sub2_Sub4_504.render(i8 * 512 + 1280 & 0x7ff, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, j11, l5, l11, class26_1.uid, class26_1.getNewUID(), 15);
						}
					}
				}
				if (flag1) {
					GroundObject class49 = front.obj3;
					if (class49 != null) {
						class49.aClass30_Sub2_Sub4_814.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class49.anInt812 - xCameraPosition, class49.anInt811 - zCameraPosition, class49.anInt813 - yCameraPosition, class49.uid, class49.getNewUID(), 15);
					}
					GroundItem groundItem_1 = front.obj4;
					if (groundItem_1 != null && groundItem_1.anInt52 == 0) {
						if (groundItem_1.aClass30_Sub2_Sub4_49 != null) {
							groundItem_1.aClass30_Sub2_Sub4_49.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, groundItem_1.anInt46 - xCameraPosition, groundItem_1.anInt45 - zCameraPosition, groundItem_1.anInt47 - yCameraPosition, groundItem_1.uid, groundItem_1.getNewUID(), 5);
						}
						if (groundItem_1.aClass30_Sub2_Sub4_50 != null) {
							groundItem_1.aClass30_Sub2_Sub4_50.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, groundItem_1.anInt46 - xCameraPosition, groundItem_1.anInt45 - zCameraPosition, groundItem_1.anInt47 - yCameraPosition, groundItem_1.uid, groundItem_1.getNewUID(), 5);
						}
						if (groundItem_1.aClass30_Sub2_Sub4_48 != null) {
							groundItem_1.aClass30_Sub2_Sub4_48.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, groundItem_1.anInt46 - xCameraPosition, groundItem_1.anInt45 - zCameraPosition, groundItem_1.anInt47 - yCameraPosition, groundItem_1.uid, groundItem_1.getNewUID(), 5);
						}
					}
				}
				int k4 = front.anInt1320;
				if (k4 != 0) {
					if (i < xCameraPositionTile && (k4 & 4) != 0) {
						Tile class30_sub3_17 = aclass30_sub3[i + 1][j];
						if (class30_sub3_17 != null && class30_sub3_17.rendersVisible) {
							tileCache.insertHead(class30_sub3_17);
						}
					}
					if (j < absoluteCameraY && (k4 & 2) != 0) {
						Tile class30_sub3_18 = aclass30_sub3[i][j + 1];
						if (class30_sub3_18 != null && class30_sub3_18.rendersVisible) {
							tileCache.insertHead(class30_sub3_18);
						}
					}
					if (i > xCameraPositionTile && (k4 & 1) != 0) {
						Tile class30_sub3_19 = aclass30_sub3[i - 1][j];
						if (class30_sub3_19 != null && class30_sub3_19.rendersVisible) {
							tileCache.insertHead(class30_sub3_19);
						}
					}
					if (j > absoluteCameraY && (k4 & 8) != 0) {
						Tile class30_sub3_20 = aclass30_sub3[i][j - 1];
						if (class30_sub3_20 != null && class30_sub3_20.rendersVisible) {
							tileCache.insertHead(class30_sub3_20);
						}
					}
				}
			}
			if (front.anInt1325 != 0) {
				boolean flag2 = true;
				for (int k1 = 0; k1 < front.anInt1317; k1++) {
					if (front.obj5Array[k1].anInt528 == tileCount || (front.anIntArray1319[k1] & front.anInt1325) != front.anInt1326) {
						continue;
					}
					flag2 = false;
					break;
				}

				if (flag2) {
					WallObject class10_1 = front.obj1;
					if (!method321(l, i, j, class10_1.orientation)) {
						class10_1.aClass30_Sub2_Sub4_278.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class10_1.anInt274 - xCameraPosition, class10_1.anInt273 - zCameraPosition, class10_1.anInt275 - yCameraPosition, class10_1.uid, class10_1.getNewUID(), 10);
					}
					front.anInt1325 = 0;
				}
			}
			if (front.aBoolean1324) {
				try {
					int i1 = front.anInt1317;
					front.aBoolean1324 = false;
					int l1 = 0;
					label0:
					for (int k2 = 0; k2 < i1; k2++) {
						GameObject class28_1 = front.obj5Array[k2];
						if (class28_1.anInt528 == tileCount) {
							continue;
						}
						for (int k3 = class28_1.anInt523; k3 <= class28_1.anInt524; k3++) {
							for (int l4 = class28_1.anInt525; l4 <= class28_1.anInt526; l4++) {
								Tile class30_sub3_21 = aclass30_sub3[k3][l4];
								if (class30_sub3_21.visible) {
									front.aBoolean1324 = true;
								} else {
									if (class30_sub3_21.anInt1325 == 0) {
										continue;
									}
									int l6 = 0;
									if (k3 > class28_1.anInt523) {
										l6++;
									}
									if (k3 < class28_1.anInt524) {
										l6 += 4;
									}
									if (l4 > class28_1.anInt525) {
										l6 += 8;
									}
									if (l4 < class28_1.anInt526) {
										l6 += 2;
									}
									if ((l6 & class30_sub3_21.anInt1325) != front.anInt1327) {
										continue;
									}
									front.aBoolean1324 = true;
								}
								continue label0;
							}

						}

						aClass28Array462[l1++] = class28_1;
						int i5 = xCameraPositionTile - class28_1.anInt523;
						int i6 = class28_1.anInt524 - xCameraPositionTile;
						if (i6 > i5) {
							i5 = i6;
						}
						int i7 = absoluteCameraY - class28_1.anInt525;
						int j8 = class28_1.anInt526 - absoluteCameraY;
						if (j8 > i7) {
							class28_1.anInt527 = i5 + j8;
						} else {
							class28_1.anInt527 = i5 + i7;
						}
					}

					while (l1 > 0) {
						int i3 = -50;
						int l3 = -1;
						for (int j5 = 0; j5 < l1; j5++) {
							GameObject class28_2 = aClass28Array462[j5];
							if (class28_2.anInt528 != tileCount) {
								if (class28_2.anInt527 > i3) {
									i3 = class28_2.anInt527;
									l3 = j5;
								} else if (class28_2.anInt527 == i3) {
									int j7 = class28_2.anInt519 - xCameraPosition;
									int k8 = class28_2.anInt520 - yCameraPosition;
									int l9 = aClass28Array462[l3].anInt519 - xCameraPosition;
									int l10 = aClass28Array462[l3].anInt520 - yCameraPosition;
									if (j7 * j7 + k8 * k8 > l9 * l9 + l10 * l10) {
										l3 = j5;
									}
								}
							}
						}

						if (l3 == -1) {
							break;
						}
						GameObject class28_3 = aClass28Array462[l3];
						class28_3.anInt528 = tileCount;
						if (!method323(l, class28_3.anInt523, class28_3.anInt524, class28_3.anInt525, class28_3.anInt526, class28_3.aClass30_Sub2_Sub4_521.modelHeight)) {
							class28_3.aClass30_Sub2_Sub4_521.render(class28_3.anInt522, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class28_3.anInt519 - xCameraPosition, class28_3.anInt518 - zCameraPosition, class28_3.anInt520 - yCameraPosition, class28_3.uid, class28_3.getNewUID(), 17);
						}
						for (int k7 = class28_3.anInt523; k7 <= class28_3.anInt524; k7++) {
							for (int l8 = class28_3.anInt525; l8 <= class28_3.anInt526; l8++) {
								Tile class30_sub3_22 = aclass30_sub3[k7][l8];
								if (class30_sub3_22.anInt1325 != 0) {
									tileCache.insertHead(class30_sub3_22);
								} else if ((k7 != i || l8 != j) && class30_sub3_22.rendersVisible) {
									tileCache.insertHead(class30_sub3_22);
								}
							}

						}

					}
					if (front.aBoolean1324) {
						continue;
					}
				} catch (Exception _ex) {
					front.aBoolean1324 = false;
				}
			}
			if (!front.rendersVisible || front.anInt1325 != 0) {
				continue;
			}
			if (i <= xCameraPositionTile && i > minRegionX) {
				Tile class30_sub3_8 = aclass30_sub3[i - 1][j];
				if (class30_sub3_8 != null && class30_sub3_8.rendersVisible) {
					continue;
				}
			}
			if (i >= xCameraPositionTile && i < maxRegionX - 1) {
				Tile class30_sub3_9 = aclass30_sub3[i + 1][j];
				if (class30_sub3_9 != null && class30_sub3_9.rendersVisible) {
					continue;
				}
			}
			if (j <= absoluteCameraY && j > minRegionY) {
				Tile class30_sub3_10 = aclass30_sub3[i][j - 1];
				if (class30_sub3_10 != null && class30_sub3_10.rendersVisible) {
					continue;
				}
			}
			if (j >= absoluteCameraY && j < maxRegionY - 1) {
				Tile class30_sub3_11 = aclass30_sub3[i][j + 1];
				if (class30_sub3_11 != null && class30_sub3_11.rendersVisible) {
					continue;
				}
			}
			front.rendersVisible = false;
			tilesRemaining--;
			GroundItem groundItem = front.obj4;
			if (groundItem != null && groundItem.anInt52 != 0) {
				if (groundItem.aClass30_Sub2_Sub4_49 != null) {
					groundItem.aClass30_Sub2_Sub4_49.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, groundItem.anInt46 - xCameraPosition, groundItem.anInt45 - zCameraPosition - groundItem.anInt52, groundItem.anInt47 - yCameraPosition, groundItem.uid, groundItem.getNewUID(), 5);
				}
				if (groundItem.aClass30_Sub2_Sub4_50 != null) {
					groundItem.aClass30_Sub2_Sub4_50.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, groundItem.anInt46 - xCameraPosition, groundItem.anInt45 - zCameraPosition - groundItem.anInt52, groundItem.anInt47 - yCameraPosition, groundItem.uid, groundItem.getNewUID(), 5);
				}
				if (groundItem.aClass30_Sub2_Sub4_48 != null) {
					groundItem.aClass30_Sub2_Sub4_48.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, groundItem.anInt46 - xCameraPosition, groundItem.anInt45 - zCameraPosition - groundItem.anInt52, groundItem.anInt47 - yCameraPosition, groundItem.uid, groundItem.getNewUID(), 5);
				}
			}
			if (front.anInt1328 != 0) {
				DecorativeObject class26 = front.obj2;
				if (class26 != null && !method322(l, i, j, class26.aClass30_Sub2_Sub4_504.modelHeight)) {
					if ((class26.anInt502 & front.anInt1328) != 0) {
						class26.aClass30_Sub2_Sub4_504.render(class26.anInt503, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class26.anInt500 - xCameraPosition, class26.anInt499 - zCameraPosition, class26.anInt501 - yCameraPosition, class26.uid, class26.getNewUID(), 15);
					} else if ((class26.anInt502 & 0x300) != 0) {
						int l2 = class26.anInt500 - xCameraPosition;
						int j3 = class26.anInt499 - zCameraPosition;
						int i4 = class26.anInt501 - yCameraPosition;
						int k5 = class26.anInt503;
						int j6;
						if (k5 == 1 || k5 == 2) {
							j6 = -l2;
						} else {
							j6 = l2;
						}
						int l7;
						if (k5 == 2 || k5 == 3) {
							l7 = -i4;
						} else {
							l7 = i4;
						}
						if ((class26.anInt502 & 0x100) != 0 && l7 >= j6) {
							int i9 = l2 + anIntArray463[k5];
							int i10 = i4 + anIntArray464[k5];
							class26.aClass30_Sub2_Sub4_504.render(k5 * 512 + 256, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, i9, j3, i10, class26.uid, class26.getNewUID(), 15);
						}
						if ((class26.anInt502 & 0x200) != 0 && l7 <= j6) {
							int j9 = l2 + anIntArray465[k5];
							int j10 = i4 + anIntArray466[k5];
							class26.aClass30_Sub2_Sub4_504.render(k5 * 512 + 1280 & 0x7ff, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, j9, j3, j10, class26.uid, class26.getNewUID(), 15);
						}
					}
				}
				WallObject class10_2 = front.obj1;
				if (class10_2 != null) {
					if ((class10_2.orientation1 & front.anInt1328) != 0 && !method321(l, i, j, class10_2.orientation1)) {
						class10_2.aClass30_Sub2_Sub4_279.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class10_2.anInt274 - xCameraPosition, class10_2.anInt273 - zCameraPosition, class10_2.anInt275 - yCameraPosition, class10_2.uid, class10_2.getNewUID(), 10);
					}
					if ((class10_2.orientation & front.anInt1328) != 0 && !method321(l, i, j, class10_2.orientation)) {
						class10_2.aClass30_Sub2_Sub4_278.render(0, yCurveSine, yCurveCosine, xCurveSine, xCurveCosine, class10_2.anInt274 - xCameraPosition, class10_2.anInt273 - zCameraPosition, class10_2.anInt275 - yCameraPosition, class10_2.uid, class10_2.getNewUID(), 10);
					}
				}
			}
			if (k < planeCount - 1) {
				Tile class30_sub3_12 = tiles[k + 1][i][j];
				if (class30_sub3_12 != null && class30_sub3_12.rendersVisible) {
					tileCache.insertHead(class30_sub3_12);
				}
			}
			if (i < xCameraPositionTile) {
				Tile class30_sub3_13 = aclass30_sub3[i + 1][j];
				if (class30_sub3_13 != null && class30_sub3_13.rendersVisible) {
					tileCache.insertHead(class30_sub3_13);
				}
			}
			if (j < absoluteCameraY) {
				Tile class30_sub3_14 = aclass30_sub3[i][j + 1];
				if (class30_sub3_14 != null && class30_sub3_14.rendersVisible) {
					tileCache.insertHead(class30_sub3_14);
				}
			}
			if (i > xCameraPositionTile) {
				Tile class30_sub3_15 = aclass30_sub3[i - 1][j];
				if (class30_sub3_15 != null && class30_sub3_15.rendersVisible) {
					tileCache.insertHead(class30_sub3_15);
				}
			}
			if (j > absoluteCameraY) {
				Tile class30_sub3_16 = aclass30_sub3[i][j - 1];
				if (class30_sub3_16 != null && class30_sub3_16.rendersVisible) {
					tileCache.insertHead(class30_sub3_16);
				}
			}
		} while (true);
	}

	private void renderPlainTile(SceneTilePaint sceneTilePaint, int plane, int ySin, int yCos, int xSin, int xCos, int tileX, int tileY) {
		int xC;
		int xA = xC = (tileX << 7) - xCameraPosition;
		int yB;
		int yA = yB = (tileY << 7) - yCameraPosition;
		int xD;
		int xB = xD = xA + 128;
		int yC;
		int yD = yC = yA + 128;
		int zA = tileHeights[plane][tileX][tileY] - zCameraPosition;
		int zB = tileHeights[plane][tileX + 1][tileY] - zCameraPosition;
		int zD = tileHeights[plane][tileX + 1][tileY + 1] - zCameraPosition;
		int zC = tileHeights[plane][tileX][tileY + 1] - zCameraPosition;
		int temp = yA * xSin + xA * xCos >> 16;
		yA = yA * xCos - xA * xSin >> 16;
		xA = temp;
		temp = zA * yCos - yA * ySin >> 16;
		yA = zA * ySin + yA * yCos >> 16;
		zA = temp;
		if (yA < 50) {//camNear
			return;
		}
		temp = yB * xSin + xB * xCos >> 16;
		yB = yB * xCos - xB * xSin >> 16;
		xB = temp;
		temp = zB * yCos - yB * ySin >> 16;
		yB = zB * ySin + yB * yCos >> 16;
		zB = temp;
		if (yB < 50) {
			return;
		}
		temp = yD * xSin + xD * xCos >> 16;
		yD = yD * xCos - xD * xSin >> 16;
		xD = temp;
		temp = zD * yCos - yD * ySin >> 16;
		yD = zD * ySin + yD * yCos >> 16;
		zD = temp;
		if (yD < 50) {
			return;
		}
		temp = yC * xSin + xC * xCos >> 16;
		yC = yC * xCos - xC * xSin >> 16;
		xC = temp;
		temp = zC * yCos - yC * ySin >> 16;
		yC = zC * ySin + yC * yCos >> 16;
		zC = temp;
		if (yC < 50) {
			return;
		}
		int screenXA = Rasterizer3D.textureInt1 + (xA << Client.viewDistance) / yA;
		int screenYA = Rasterizer3D.textureInt2 + (zA << Client.viewDistance) / yA;
		int screenXB = Rasterizer3D.textureInt1 + (xB << Client.viewDistance) / yB;
		int screenYB = Rasterizer3D.textureInt2 + (zB << Client.viewDistance) / yB;
		int screenXC = Rasterizer3D.textureInt1 + (xC << Client.viewDistance) / yC;
		int screenYC = Rasterizer3D.textureInt2 + (zC << Client.viewDistance) / yC;
		int screenXD = Rasterizer3D.textureInt1 + (xD << Client.viewDistance) / yD;
		int screenYD = Rasterizer3D.textureInt2 + (zD << Client.viewDistance) / yD;
		Rasterizer3D.alpha = 0;
		if ((screenXD - screenXC) * (screenYB - screenYC) - (screenYD - screenYC) * (screenXB - screenXC) > 0) {
			Rasterizer3D.hidden = screenXD < 0 || screenXC < 0 || screenXB < 0 || screenXD > Raster.maxRight || screenXC > Raster.maxRight || screenXB > Raster.maxRight;
			if (isClicked && isMouseWithinTriangle(mouseX, mouseY, screenYD, screenYC, screenYB, screenXD, screenXC, screenXB)) {
				clickedTileX = tileX;
				clickedTileY = tileY;
			}
			if (sceneTilePaint.texture == -1 || sceneTilePaint.texture > 50) {
				if (sceneTilePaint.neColor != 0xbc614e) {
					Rasterizer3D.drawGouraudTriangle(screenYD, screenYC, screenYB, screenXD, screenXC, screenXB, sceneTilePaint.neColor, sceneTilePaint.anInt719, sceneTilePaint.anInt717, yD, yC, yB, 0);
				}
			} else if (!lowDetail) {
				if (sceneTilePaint.aBoolean721) {
					Rasterizer3D.drawTexturedTriangle(screenYD, screenYC, screenYB, screenXD, screenXC, screenXB, sceneTilePaint.neColor, sceneTilePaint.anInt719, sceneTilePaint.anInt717, xA, xB, xC, zA, zB, zC, yA, yB, yC, sceneTilePaint.texture, yA, yB, yC, 0);
				} else {
					Rasterizer3D.drawTexturedTriangle(screenYD, screenYC, screenYB, screenXD, screenXC, screenXB, sceneTilePaint.neColor, sceneTilePaint.anInt719, sceneTilePaint.anInt717, xD, xC, xB, zD, zC, zB, yD, yC, yB, sceneTilePaint.texture, yD, yC, yB, 0);
				}
			} else {
				int i7 = textureRGBColour[sceneTilePaint.texture];
				Rasterizer3D.drawGouraudTriangle(screenYD, screenYC, screenYB, screenXD, screenXC, screenXB, mixColour(i7, sceneTilePaint.neColor), mixColour(i7, sceneTilePaint.anInt719), mixColour(i7, sceneTilePaint.anInt717), yD, yC, yB, 0);
			}
		}
		if ((screenXA - screenXB) * (screenYC - screenYB) - (screenYA - screenYB) * (screenXC - screenXB) > 0) {
			Rasterizer3D.hidden = screenXA < 0 || screenXB < 0 || screenXC < 0 || screenXA > Raster.maxRight || screenXB > Raster.maxRight || screenXC > Raster.maxRight;
			if (isClicked && isMouseWithinTriangle(mouseX, mouseY, screenYA, screenYB, screenYC, screenXA, screenXB, screenXC)) {
				clickedTileX = tileX;
				clickedTileY = tileY;
			}
			if (sceneTilePaint.texture == -1 || sceneTilePaint.texture > 50) {
				if (sceneTilePaint.anInt716 != 0xbc614e) {
					
						Rasterizer3D.drawGouraudTriangle(screenYA, screenYB, screenYC, screenXA, screenXB, screenXC, sceneTilePaint.anInt716, sceneTilePaint.anInt717, sceneTilePaint.anInt719, yA, yB, yC, 0);
					
				}
			} else {
				if (!lowDetail) {
					Rasterizer3D.drawTexturedTriangle(screenYA, screenYB, screenYC, screenXA, screenXB, screenXC, sceneTilePaint.anInt716, sceneTilePaint.anInt717, sceneTilePaint.anInt719, xA, xB, xC, zA, zB, zC, yA, yB, yC, sceneTilePaint.texture, yA, yB, yC, 0);
				} else {
					int j7 = textureRGBColour[sceneTilePaint.texture];
					Rasterizer3D.drawGouraudTriangle(screenYA, screenYB, screenYC, screenXA, screenXB, screenXC, mixColour(j7, sceneTilePaint.anInt716), mixColour(j7, sceneTilePaint.anInt717), mixColour(j7, sceneTilePaint.anInt719), yA, yB, yC, 0);
				}
			}
		}
	}

	private void renderShapedTile(int tX, int ysin, int xsin, SceneTileModel sceneTileModel, int ycos, int tY, int xcos) {
		//Configuration.DEPTH_BUFFER = false;
		int triangleCount = sceneTileModel.originalVertexX.length;
		for (int l1 = 0; l1 < triangleCount; l1++) {
			int viewspaceX = sceneTileModel.originalVertexX[l1] - xCameraPosition;
			int viewspaceY = sceneTileModel.originalVertexY[l1] - zCameraPosition;
			int viewspaceZ = sceneTileModel.originalVertexZ[l1] - yCameraPosition;
			int k3 = viewspaceZ * xsin + viewspaceX * xcos >> 16;
			viewspaceZ = viewspaceZ * xcos - viewspaceX * xsin >> 16;
			viewspaceX = k3;
			k3 = viewspaceY * ycos - viewspaceZ * ysin >> 16;
			viewspaceZ = viewspaceY * ysin + viewspaceZ * ycos >> 16;
			viewspaceY = k3;
			if (viewspaceZ < 50) {//50 = camnear
				return;
			}
			if (sceneTileModel.triangleTexture != null) {
				SceneTileModel.viewSpaceX[l1] = viewspaceX;
				SceneTileModel.viewSpaceY[l1] = viewspaceY;
				SceneTileModel.viewSpaceZ[l1] = viewspaceZ;
			}
			SceneTileModel.screenX[l1] = Rasterizer3D.textureInt1 + (viewspaceX << Client.viewDistance) / viewspaceZ;
			SceneTileModel.screenY[l1] = Rasterizer3D.textureInt2 + (viewspaceY << Client.viewDistance) / viewspaceZ;
			SceneTileModel.depthPoint[l1] = viewspaceZ;
		}

		Rasterizer3D.alpha = 0;
		triangleCount = sceneTileModel.triangleA.length;
		for (int triIdx = 0; triIdx < triangleCount; triIdx++) {
			int indexA = sceneTileModel.triangleA[triIdx];
			int indexB = sceneTileModel.triangleB[triIdx];
			int indexC = sceneTileModel.triangleC[triIdx];
			int x1 = SceneTileModel.screenX[indexA];
			int y1 = SceneTileModel.screenY[indexA];
			int x2 = SceneTileModel.screenX[indexB];
			int y2 = SceneTileModel.screenY[indexB];
			int x3 = SceneTileModel.screenX[indexC];
			int y3 = SceneTileModel.screenY[indexC];
			//ADDED FOR ZBUFFER
			int z1 = SceneTileModel.depthPoint[indexA];
			int z2 = SceneTileModel.depthPoint[indexB];
			int z3 = SceneTileModel.depthPoint[indexC];
			
			if ((x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2) > 0) {
				Rasterizer3D.hidden = x1 < 0 || x2 < 0 || x3 < 0 || x1 > Raster.maxRight || x2 > Raster.maxRight || x3 > Raster.maxRight;
				if (isClicked && isMouseWithinTriangle(mouseX, mouseY, y1, y2, y3, x1, x2, x3)) {
					clickedTileX = tX;
					clickedTileY = tY;
				}
				if (sceneTileModel.triangleTexture == null
						|| sceneTileModel.triangleTexture[triIdx] == -1
						|| sceneTileModel.triangleTexture[triIdx] > 50) {
					if (sceneTileModel.triangleHSLA[triIdx] != 0xbc614e) {
						//find out what this is used for
						//if (sceneTileModel.aBoolean683 || sceneTileModel.triangleTexture[triIdx] == 505) {
							Rasterizer3D.drawGouraudTriangle(y1, y2, y3, x1, x2, x3, sceneTileModel.triangleHSLA[triIdx], sceneTileModel.anIntArray677[triIdx], sceneTileModel.anIntArray678[triIdx], z1, z2, z3, 0);
						}
				} else if (!lowDetail) {
					if (sceneTileModel.flat) {
						Rasterizer3D.drawTexturedTriangle(y1, y2, y3, x1, x2, x3, sceneTileModel.triangleHSLA[triIdx], sceneTileModel.anIntArray677[triIdx], sceneTileModel.anIntArray678[triIdx], SceneTileModel.viewSpaceX[0], SceneTileModel.viewSpaceX[1], SceneTileModel.viewSpaceX[3], SceneTileModel.viewSpaceY[0], SceneTileModel.viewSpaceY[1], SceneTileModel.viewSpaceY[3], SceneTileModel.viewSpaceZ[0], SceneTileModel.viewSpaceZ[1], SceneTileModel.viewSpaceZ[3], sceneTileModel.triangleTexture[triIdx], z1, z2, z3, 0);
					} else {
						Rasterizer3D.drawTexturedTriangle(y1, y2, y3, x1, x2, x3, sceneTileModel.triangleHSLA[triIdx], sceneTileModel.anIntArray677[triIdx], sceneTileModel.anIntArray678[triIdx], SceneTileModel.viewSpaceX[indexA], SceneTileModel.viewSpaceX[indexB], SceneTileModel.viewSpaceX[indexC], SceneTileModel.viewSpaceY[indexA], SceneTileModel.viewSpaceY[indexB], SceneTileModel.viewSpaceY[indexC], SceneTileModel.viewSpaceZ[indexA], SceneTileModel.viewSpaceZ[indexB], SceneTileModel.viewSpaceZ[indexC], sceneTileModel.triangleTexture[triIdx], z1, z2, z3, 0);
					}
				} else {
					int colour = textureRGBColour[sceneTileModel.triangleTexture[triIdx]];
					Rasterizer3D.drawGouraudTriangle(y1, y2, y3, x1, x2, x3, mixColour(colour, sceneTileModel.triangleHSLA[triIdx]), mixColour(colour, sceneTileModel.anIntArray677[triIdx]), mixColour(colour, sceneTileModel.anIntArray678[triIdx]), z1, z2, z3, 0);
				}
			}
		}
	//	Configuration.DEPTH_BUFFER = true;
	}

	private int mixColour(int j, int k) {
		k = 127 - k;
		k = k * (j & 0x7f) / 160;
		if (k < 2) {
			k = 2;
		} else if (k > 126) {
			k = 126;
		}
		return (j & 0xff80) + k;
	}

	private boolean isMouseWithinTriangle(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
		if (j < k && j < l && j < i1) {
			return false;
		}
		if (j > k && j > l && j > i1) {
			return false;
		}
		if (i < j1 && i < k1 && i < l1) {
			return false;
		}
		if (i > j1 && i > k1 && i > l1) {
			return false;
		}
		int i2 = (j - k) * (k1 - j1) - (i - j1) * (l - k);
		int j2 = (j - i1) * (j1 - l1) - (i - l1) * (k - i1);
		int k2 = (j - l) * (l1 - k1) - (i - k1) * (i1 - l);
		return i2 * k2 > 0 && k2 * j2 > 0;
	}

	private void processCulling() {
		int j = anIntArray473[plane];
		SceneCluster[] aclass47 = aSceneClusterArrayArray474[plane];
		anInt475 = 0;
		for (int k = 0; k < j; k++) {
			SceneCluster sceneCluster = aclass47[k];
			if (sceneCluster.anInt791 == 1) {
				int l = sceneCluster.anInt787 - xCameraPositionTile + farZ;
				if (l < 0 || l > 50) {
					continue;
				}
				int k1 = sceneCluster.anInt789 - absoluteCameraY + farZ;
				if (k1 < 0) {
					k1 = 0;
				}
				int j2 = sceneCluster.anInt790 - absoluteCameraY + farZ;
				if (j2 > 50) {
					j2 = 50;
				}
				boolean flag = false;
				while (k1 <= j2) {
					if (TILE_VISIBILITY_MAP[l][k1++]) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					continue;
				}
				int j3 = xCameraPosition - sceneCluster.anInt792;
				if (j3 > 32) {
					sceneCluster.anInt798 = 1;
				} else {
					if (j3 >= -32) {
						continue;
					}
					sceneCluster.anInt798 = 2;
					j3 = -j3;
				}
				sceneCluster.anInt801 = (sceneCluster.anInt794 - yCameraPosition << 8) / j3;
				sceneCluster.anInt802 = (sceneCluster.anInt795 - yCameraPosition << 8) / j3;
				sceneCluster.anInt803 = (sceneCluster.anInt796 - zCameraPosition << 8) / j3;
				sceneCluster.anInt804 = (sceneCluster.anInt797 - zCameraPosition << 8) / j3;
				A_SCENE_CLUSTER_ARRAY_476[anInt475++] = sceneCluster;
				continue;
			}
			if (sceneCluster.anInt791 == 2) {
				int i1 = sceneCluster.anInt789 - absoluteCameraY + farZ;
				if (i1 < 0 || i1 > 50) {
					continue;
				}
				int l1 = sceneCluster.anInt787 - xCameraPositionTile + farZ;
				if (l1 < 0) {
					l1 = 0;
				}
				int k2 = sceneCluster.anInt788 - xCameraPositionTile + farZ;
				if (k2 > 50) {
					k2 = 50;
				}
				boolean flag1 = false;
				while (l1 <= k2) {
					if (TILE_VISIBILITY_MAP[l1++][i1]) {
						flag1 = true;
						break;
					}
				}
				if (!flag1) {
					continue;
				}
				int k3 = yCameraPosition - sceneCluster.anInt794;
				if (k3 > 32) {
					sceneCluster.anInt798 = 3;
				} else {
					if (k3 >= -32) {
						continue;
					}
					sceneCluster.anInt798 = 4;
					k3 = -k3;
				}
				sceneCluster.anInt799 = (sceneCluster.anInt792 - xCameraPosition << 8) / k3;
				sceneCluster.anInt800 = (sceneCluster.anInt793 - xCameraPosition << 8) / k3;
				sceneCluster.anInt803 = (sceneCluster.anInt796 - zCameraPosition << 8) / k3;
				sceneCluster.anInt804 = (sceneCluster.anInt797 - zCameraPosition << 8) / k3;
				A_SCENE_CLUSTER_ARRAY_476[anInt475++] = sceneCluster;
			} else if (sceneCluster.anInt791 == 4) {
				int j1 = sceneCluster.anInt796 - zCameraPosition;
				if (j1 > 128) {
					int i2 = sceneCluster.anInt789 - absoluteCameraY + farZ;
					if (i2 < 0) {
						i2 = 0;
					}
					int l2 = sceneCluster.anInt790 - absoluteCameraY + farZ;
					if (l2 > 50) {
						l2 = 50;
					}
					if (i2 <= l2) {
						int i3 = sceneCluster.anInt787 - xCameraPositionTile + farZ;
						if (i3 < 0) {
							i3 = 0;
						}
						int l3 = sceneCluster.anInt788 - xCameraPositionTile + farZ;
						if (l3 > 50) {
							l3 = 50;
						}
						boolean flag2 = false;
						label0:
						for (int i4 = i3; i4 <= l3; i4++) {
							for (int j4 = i2; j4 <= l2; j4++) {
								if (!TILE_VISIBILITY_MAP[i4][j4]) {
									continue;
								}
								flag2 = true;
								break label0;
							}

						}

						if (flag2) {
							sceneCluster.anInt798 = 5;
							sceneCluster.anInt799 = (sceneCluster.anInt792 - xCameraPosition << 8) / j1;
							sceneCluster.anInt800 = (sceneCluster.anInt793 - xCameraPosition << 8) / j1;
							sceneCluster.anInt801 = (sceneCluster.anInt794 - yCameraPosition << 8) / j1;
							sceneCluster.anInt802 = (sceneCluster.anInt795 - yCameraPosition << 8) / j1;
							A_SCENE_CLUSTER_ARRAY_476[anInt475++] = sceneCluster;
						}
					}
				}
			}
		}

	}

	private boolean method320(int i, int j, int k) {
		int l = anIntArrayArrayArray445[i][j][k];
		if (l == -tileCount) {
			return false;
		}
		if (l == tileCount) {
			return true;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		if (method324(i1 + 1, tileHeights[i][j][k], j1 + 1) && method324(i1 + 128 - 1, tileHeights[i][j + 1][k], j1 + 1) && method324(i1 + 128 - 1, tileHeights[i][j + 1][k + 1], j1 + 128 - 1) && method324(i1 + 1, tileHeights[i][j][k + 1], j1 + 128 - 1)) {
			anIntArrayArrayArray445[i][j][k] = tileCount;
			return true;
		} else {
			anIntArrayArrayArray445[i][j][k] = -tileCount;
			return false;
		}
	}

	private boolean method321(int i, int j, int k, int l) {
		if (!method320(i, j, k)) {
			return false;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		int k1 = tileHeights[i][j][k] - 1;
		int l1 = k1 - 120;
		int i2 = k1 - 230;
		int j2 = k1 - 238;
		if (l < 16) {
			if (l == 1) {
				if (i1 > xCameraPosition) {
					if (!method324(i1, k1, j1)) {
						return false;
					}
					if (!method324(i1, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1, l1, j1)) {
						return false;
					}
					if (!method324(i1, l1, j1 + 128)) {
						return false;
					}
				}
				return method324(i1, i2, j1) && method324(i1, i2, j1 + 128);
			}
			if (l == 2) {
				if (j1 < yCameraPosition) {
					if (!method324(i1, k1, j1 + 128)) {
						return false;
					}
					if (!method324(i1 + 128, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1, l1, j1 + 128)) {
						return false;
					}
					if (!method324(i1 + 128, l1, j1 + 128)) {
						return false;
					}
				}
				return method324(i1, i2, j1 + 128) && method324(i1 + 128, i2, j1 + 128);
			}
			if (l == 4) {
				if (i1 < xCameraPosition) {
					if (!method324(i1 + 128, k1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1 + 128, l1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, l1, j1 + 128)) {
						return false;
					}
				}
				return method324(i1 + 128, i2, j1) && method324(i1 + 128, i2, j1 + 128);
			}
			if (l == 8) {
				if (j1 > yCameraPosition) {
					if (!method324(i1, k1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, k1, j1)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1, l1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, l1, j1)) {
						return false;
					}
				}
				return method324(i1, i2, j1) && method324(i1 + 128, i2, j1);
			}
		}
		if (!method324(i1 + 64, j2, j1 + 64)) {
			return false;
		}
		if (l == 16) {
			return method324(i1, i2, j1 + 128);
		}
		if (l == 32) {
			return method324(i1 + 128, i2, j1 + 128);
		}
		if (l == 64) {
			return method324(i1 + 128, i2, j1);
		}
		if (l == 128) {
			return method324(i1, i2, j1);
		} else {
			System.out.println("Warning unsupported wall type");
			return true;
		}
	}

	private boolean method322(int i, int j, int k, int l) {
		if (!method320(i, j, k)) {
			return false;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		return method324(i1 + 1, tileHeights[i][j][k] - l, j1 + 1) 
				&& method324(i1 + 128 - 1, tileHeights[i][j + 1][k] - l, j1 + 1) 
				&& method324(i1 + 128 - 1, tileHeights[i][j + 1][k + 1] - l, j1 + 128 - 1) 
				&& method324(i1 + 1, tileHeights[i][j][k + 1] - l, j1 + 128 - 1);
	}

	private boolean method323(int i, int j, int k, int l, int i1, int j1) {
		if (j == k && l == i1) {
			if (!method320(i, j, l)) {
				return false;
			}
			int k1 = j << 7;
			int i2 = l << 7;
			return method324(k1 + 1, tileHeights[i][j][l] - j1, i2 + 1) && method324(k1 + 128 - 1, tileHeights[i][j + 1][l] - j1, i2 + 1) && method324(k1 + 128 - 1, tileHeights[i][j + 1][l + 1] - j1, i2 + 128 - 1) && method324(k1 + 1, tileHeights[i][j][l + 1] - j1, i2 + 128 - 1);
		}
		for (int l1 = j; l1 <= k; l1++) {
			for (int j2 = l; j2 <= i1; j2++) {
				if (anIntArrayArrayArray445[i][l1][j2] == -tileCount) {
					return false;
				}
			}

		}

		int k2 = (j << 7) + 1;
		int l2 = (l << 7) + 2;
		int i3 = tileHeights[i][j][l] - j1;
		if (!method324(k2, i3, l2)) {
			return false;
		}
		int j3 = (k << 7) - 1;
		if (!method324(j3, i3, l2)) {
			return false;
		}
		int k3 = (i1 << 7) - 1;
		return method324(k2, i3, k3) && method324(j3, i3, k3);
	}

	private boolean method324(int i, int j, int k) {
		for (int l = 0; l < anInt475; l++) {
			SceneCluster sceneCluster = A_SCENE_CLUSTER_ARRAY_476[l];
			if (sceneCluster.anInt798 == 1) {
				int i1 = sceneCluster.anInt792 - i;
				if (i1 > 0) {
					int j2 = sceneCluster.anInt794 + (sceneCluster.anInt801 * i1 >> 8);
					int k3 = sceneCluster.anInt795 + (sceneCluster.anInt802 * i1 >> 8);
					int l4 = sceneCluster.anInt796 + (sceneCluster.anInt803 * i1 >> 8);
					int i6 = sceneCluster.anInt797 + (sceneCluster.anInt804 * i1 >> 8);
					if (k >= j2 && k <= k3 && j >= l4 && j <= i6) {
						return true;
					}
				}
			} else if (sceneCluster.anInt798 == 2) {
				int j1 = i - sceneCluster.anInt792;
				if (j1 > 0) {
					int k2 = sceneCluster.anInt794 + (sceneCluster.anInt801 * j1 >> 8);
					int l3 = sceneCluster.anInt795 + (sceneCluster.anInt802 * j1 >> 8);
					int i5 = sceneCluster.anInt796 + (sceneCluster.anInt803 * j1 >> 8);
					int j6 = sceneCluster.anInt797 + (sceneCluster.anInt804 * j1 >> 8);
					if (k >= k2 && k <= l3 && j >= i5 && j <= j6) {
						return true;
					}
				}
			} else if (sceneCluster.anInt798 == 3) {
				int k1 = sceneCluster.anInt794 - k;
				if (k1 > 0) {
					int l2 = sceneCluster.anInt792 + (sceneCluster.anInt799 * k1 >> 8);
					int i4 = sceneCluster.anInt793 + (sceneCluster.anInt800 * k1 >> 8);
					int j5 = sceneCluster.anInt796 + (sceneCluster.anInt803 * k1 >> 8);
					int k6 = sceneCluster.anInt797 + (sceneCluster.anInt804 * k1 >> 8);
					if (i >= l2 && i <= i4 && j >= j5 && j <= k6) {
						return true;
					}
				}
			} else if (sceneCluster.anInt798 == 4) {
				int l1 = k - sceneCluster.anInt794;
				if (l1 > 0) {
					int i3 = sceneCluster.anInt792 + (sceneCluster.anInt799 * l1 >> 8);
					int j4 = sceneCluster.anInt793 + (sceneCluster.anInt800 * l1 >> 8);
					int k5 = sceneCluster.anInt796 + (sceneCluster.anInt803 * l1 >> 8);
					int l6 = sceneCluster.anInt797 + (sceneCluster.anInt804 * l1 >> 8);
					if (i >= i3 && i <= j4 && j >= k5 && j <= l6) {
						return true;
					}
				}
			} else if (sceneCluster.anInt798 == 5) {
				int i2 = j - sceneCluster.anInt796;
				if (i2 > 0) {
					int j3 = sceneCluster.anInt792 + (sceneCluster.anInt799 * i2 >> 8);
					int k4 = sceneCluster.anInt793 + (sceneCluster.anInt800 * i2 >> 8);
					int l5 = sceneCluster.anInt794 + (sceneCluster.anInt801 * i2 >> 8);
					int i7 = sceneCluster.anInt795 + (sceneCluster.anInt802 * i2 >> 8);
					if (i >= j3 && i <= k4 && k >= l5 && k <= i7) {
						return true;
					}
				}
			}
		}

		return false;
	}
}
