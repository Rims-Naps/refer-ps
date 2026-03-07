package com.ruse.world.content.instanceMananger;

import com.ruse.model.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GridSpawns {
	FOUR_BY_FOUR(4, new Position(2254, 3805, 3), new Position(2254, 3805, 3), 3),
	SIX_BY_SIX(6, new Position(2253, 3804, 3), new Position(2253, 3804, 3), 2);

	//FOUR_BY_FOUR(4, new Position(2253, 3804, 3), new Position(3597, 3228, 3), 3),
	//EIGHT_BY_EIGHT(6, new Position(2253, 3804, 3), new Position(3597, 3228, 3), 2);


	private int gridSize;
	private Position start;
	private Position playerPosition;
	private int offset;
}
