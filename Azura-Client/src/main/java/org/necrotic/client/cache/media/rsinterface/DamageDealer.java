package org.necrotic.client.cache.media.rsinterface;

public class DamageDealer {

	public DamageDealer(String p, int damage) {
		this.p = p;
		this.damage = damage;
	}

	private String p;
	private int damage;

	public String getPlayer() {
		return this.p;
	}

	public int getDamage() {
		return this.damage;
	}
}
