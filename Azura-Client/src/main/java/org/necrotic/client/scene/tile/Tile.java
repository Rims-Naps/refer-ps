package org.necrotic.client.scene.tile;

import org.necrotic.client.collection.Node;

public final class Tile extends Node {

	public boolean visible;
	public boolean rendersVisible;
	public boolean aBoolean1324;
	public Tile aClass30_Sub3_1329;
	public SceneTileModel aClass40_1312;
	public SceneTilePaint aClass43_1311;
	public int plane;
	public final int tileX;
	public final int tileY;
	public final int originalPlane;
	public int anInt1317;
	public int anInt1320;
	public int anInt1321;
	public int anInt1325;
	public int anInt1326;
	public int anInt1327;
	public int anInt1328;
	public final int[] anIntArray1319;
	public WallObject obj1;
	public DecorativeObject obj2;
	public GroundObject obj3;
	public GroundItem obj4;
	public final GameObject[] obj5Array;

	public Tile(int i, int j, int k) {
		obj5Array = new GameObject[5];
		anIntArray1319 = new int[5];
		originalPlane = plane = i;
		tileX = j;
		tileY = k;
	}

}
