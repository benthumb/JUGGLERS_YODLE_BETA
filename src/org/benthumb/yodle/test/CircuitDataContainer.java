package org.benthumb.yodle.test;

public class CircuitDataContainer {

	/**
	 * Circuit number
	 */
	private final int circuitNumber;
	/**
	 * Juggler's hand to eye coordination
	 */
	private final int jugglerHandToEyeCoordination;
	/**
	 * Juggler's endurance
	 */
	private final int jugglerEndurance;
	/**
	 * Juggler's pizzazz
	 */
	private final int jugglerPizzazz;

	public CircuitDataContainer(int circuitNumber,
			int jugglerHandToEyeCoordination, int jugglerEndurance,
			int jugglerPizzazz) {
		super();
		this.circuitNumber = circuitNumber;
		this.jugglerHandToEyeCoordination = jugglerHandToEyeCoordination;
		this.jugglerEndurance = jugglerEndurance;
		this.jugglerPizzazz = jugglerPizzazz;
	}

	public int getCircuitNumber() {
		return circuitNumber;
	}

	public int getJugglerHandToEyeCoordination() {
		return jugglerHandToEyeCoordination;
	}

	public int getJugglerEndurance() {
		return jugglerEndurance;
	}

	public int getJugglerPizzazz() {
		return jugglerPizzazz;
	}

}
