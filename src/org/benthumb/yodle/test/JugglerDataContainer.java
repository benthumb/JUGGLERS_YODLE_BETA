package org.benthumb.yodle.test;

/*
 hand to eye coordination (H), 
 endurance (E) and 
 pizzazz (P) 
 */

/**
 * @author Paul
 * 
 */
public class JugglerDataContainer extends CircuitDataContainer {

	/**
	 * Juggler number
	 */
	private final int jugglerNumber;
	/**
	 * Juggler's first preference circuit
	 */
	private final int jugglerCircuitPreferenceFirst;
	/**
	 * Juggler's second preference circuit
	 */
	private final int jugglerCircuitPreferenceSecond;
	/**
	 * Juggler's first preference circuit
	 */
	private final int jugglerCircuitPreferenceThird;
	/**
	 * dot product Circuit vs. Juggler
	 */
	private int dotProduct = 0;

	/**
	 * @param circuitNumber
	 * @param jugglerHandToEyeCoordination
	 * @param jugglerEndurance
	 * @param jugglerPizzazz
	 * @param jugglerCircuitPreferenceFirst
	 * @param jugglerCircuitPreferenceSecond
	 * @param jugglerCircuitPreferenceThird
	 */
	public JugglerDataContainer(int jugglerNumber, int circuitNumber,
			int jugglerHandToEyeCoordination, int jugglerEndurance,
			int jugglerPizzazz, int jugglerCircuitPreferenceFirst,
			int jugglerCircuitPreferenceSecond,
			int jugglerCircuitPreferenceThird) {
		super(circuitNumber, jugglerHandToEyeCoordination, jugglerEndurance,
				jugglerPizzazz);
		this.jugglerNumber = jugglerNumber;
		this.jugglerCircuitPreferenceFirst = jugglerCircuitPreferenceFirst;
		this.jugglerCircuitPreferenceSecond = jugglerCircuitPreferenceSecond;
		this.jugglerCircuitPreferenceThird = jugglerCircuitPreferenceThird;
	}

	public int getJugglerNumber() {
		return this.jugglerNumber;
	}

	public int getJugglerCircuitPreferenceFirst() {
		return jugglerCircuitPreferenceFirst;
	}

	public int getJugglerCircuitPreferenceSecond() {
		return jugglerCircuitPreferenceSecond;
	}

	public int getJugglerCircuitPreferenceThird() {
		return jugglerCircuitPreferenceThird;
	}

	public void setDotProduct(CircuitDataContainer circuitDataContainer) {
		this.dotProduct = calculateDotProduct(circuitDataContainer);
	}

	public int getDotProduct() {
		return this.dotProduct;
	}

	private int calculateDotProduct(CircuitDataContainer circuitDataContainer) {
		// The concept: (2i + 4j + 5k) * (5i + 22j + 90k) = 548

		CircuitDataContainer vO = circuitDataContainer;
		JugglerDataContainer vT = this;

		int I = 0;
		int J = 0;
		int K = 0;
		int dP;

		I = vO.getJugglerEndurance() * vT.getJugglerEndurance();
		J = vO.getJugglerHandToEyeCoordination()
				* vT.getJugglerHandToEyeCoordination();
		K = vO.getJugglerPizzazz() * vT.getJugglerPizzazz();

		dP = I + J + K;
		// System.out.println("The dot product is: " + dP + " !!");
		return dP;

	}
}
