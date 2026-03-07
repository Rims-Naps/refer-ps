package org.necrotic.client.media.renderable.actor;

import org.necrotic.client.cache.definition.AnimationDefinition;
import org.necrotic.client.media.AnimationSkeleton;
import org.necrotic.client.cache.definition.EntityDef;
import org.necrotic.client.cache.definition.AnimatedGraphic;
import org.necrotic.client.cache.media.rsinterface.DamageDealer;
import org.necrotic.client.media.renderable.Model;

import java.util.ArrayList;
import java.util.List;

public final class Npc extends Actor {

	public EntityDef definitionOverride;

	public Npc() {
	}

	@Override
	public Model getRotatedModel() {
		if (definitionOverride == null) {
			return null;
		}

		Model model = method450();

		if (model == null) {
			return null;
		}

		super.height = model.modelHeight;

		if (super.gfxId != -1 && super.currentAnim != -1) {
			AnimatedGraphic spotAnim = AnimatedGraphic.cache[super.gfxId];
			Model model_1 = spotAnim.getModel();

			if (model_1 != null) {
				int frame = spotAnim.animation.frameIDs[super.currentAnim];
				int nextFrame = spotAnim.animation.frameIDs[super.nextGraphicsAnimationFrame];
				int cycle1 = spotAnim.animation.delays[super.currentAnim];
				int cycle2 = super.animCycle;
				Model model_2 = new Model(true, AnimationSkeleton.isNullFrame(frame), false, model_1);
				model_2.translate(0, -super.graphicHeight, 0);
				model_2.createBones();
				// model_2.method470(frame);
				model_2.interpolateFrames(frame, nextFrame, cycle1, cycle2);
				model_2.triangleGroup = null;
				model_2.vertexGroups = null;

				if (spotAnim.getResizeX() != 128 || spotAnim.getResizeY() != 128) {
					model_2.scaleT(spotAnim.getResizeX(), spotAnim.getResizeX(), spotAnim.getResizeY());
				}

				model_2.light(64 + spotAnim.ambient, 850 + spotAnim.contrast, -30, -50, -30, true);
				Model[] aModel = {model, model_2};
				model = new Model(aModel);
			}
		}

		if (definitionOverride.npcSizeInSquares == 1) {
			model.aBoolean1659 = true;
		}

		return model;
	}

	@Override
	public boolean isVisible() {
		return definitionOverride != null;
	}

	private Model method450() {
		if (super.anim >= 0 && super.animationDelay == 0) {
			AnimationDefinition animation = AnimationDefinition.cache[super.anim];
			int currentFrame = AnimationDefinition.cache[super.anim].frameIDs[super.currentAnimFrame];
			int nextFrame = animation.frameIDs[super.nextAnimationFrame];
			int cycle1 = animation.delays[super.currentAnimFrame];
			int cycle2 = super.anInt1528;
			int i1 = -1;

			if (super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511) {
				i1 = AnimationDefinition.cache[super.anInt1517].frameIDs[super.currentForcedAnimFrame];
			}
			return definitionOverride.method164(i1, currentFrame, AnimationDefinition.cache[super.anim].animationFlowControl, nextFrame, cycle1, cycle2);
			// return definitionOverride.method164(i1, currentFrame,
			// AnimationDefinition.cache[super.anim].animationFlowControl);
		}

		int currentFrame = -1;
		int nextFrame = -1;
		int cycle1 = 0;
		int cycle2 = 0;

		if (super.anInt1517 >= 0) {
			AnimationDefinition animation = AnimationDefinition.cache[super.anInt1517];
			currentFrame = animation.frameIDs[super.currentForcedAnimFrame];
			nextFrame = animation.frameIDs[super.nextIdleAnimationFrame];
			cycle1 = animation.delays[super.currentForcedAnimFrame];
			cycle2 = super.frameDelay;
		}

		// return definitionOverride.method164(-1, currentFrame, null);
		return definitionOverride.method164(-1, currentFrame, null, nextFrame, cycle1, cycle2);
	}

	public List<DamageDealer> damageDealers = new ArrayList<DamageDealer>();

}
