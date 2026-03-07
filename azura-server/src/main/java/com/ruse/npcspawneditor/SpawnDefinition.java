package com.ruse.npcspawneditor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ruse.model.Direction;
import com.ruse.model.Position;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@JsonPropertyOrder("npc-id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SpawnDefinition {
    @EqualsAndHashCode.Include
    int npcId;
    Direction face;
    @EqualsAndHashCode.Include
    Position position;
    @JsonProperty("coordinator")
    WalkingPolicy walkingPolicy;
}