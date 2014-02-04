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
public class JugglerDataContainer extends CircuitDataContainer implements Cloneable{

	/**
	 * Juggler number
	 */
	private int jugglerNumber;
	/**
	 * Juggler's first preference circuit
	 */
	private int jugglerCircuitPreferenceFirst;
	/**
	 * Juggler's second preference circuit
	 */
	private int jugglerCircuitPreferenceSecond;
	/**
	 * Juggler's first preference circuit
	 */
	private int jugglerCircuitPreferenceThird;
	/**
	 * dot product Circuit vs. Juggler
	 */
	private int dotProduct = 0;
	/**
	 * Circuit assignment: default is -1, i.e., unassigned
	 */
	private int assignedCircuit = -1;

	public int getAssignedCircuit() {
		return assignedCircuit;
	}

	public void setAssignedCircuit(int assignedCircuit) {
		this.assignedCircuit = assignedCircuit;
	}

	public int getJugglerNumber() {
		return jugglerNumber;
	}

	public void setJugglerNumber(int jugglerNumber) {
		this.jugglerNumber = jugglerNumber;
	}

	public int getJugglerCircuitPreferenceFirst() {
		return jugglerCircuitPreferenceFirst;
	}

	public void setJugglerCircuitPreferenceFirst(int jugglerCircuitPreferenceFirst) {
		this.jugglerCircuitPreferenceFirst = jugglerCircuitPreferenceFirst;
	}

	public int getJugglerCircuitPreferenceSecond() {
		return jugglerCircuitPreferenceSecond;
	}

	public void setJugglerCircuitPreferenceSecond(int jugglerCircuitPreferenceSecond) {
		this.jugglerCircuitPreferenceSecond = jugglerCircuitPreferenceSecond;
	}

	public int getJugglerCircuitPreferenceThird() {
		return jugglerCircuitPreferenceThird;
	}

	public void setJugglerCircuitPreferenceThird(int jugglerCircuitPreferenceThird) {
		this.jugglerCircuitPreferenceThird = jugglerCircuitPreferenceThird;
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
	
	@Override // from p. 579 of Textbook ...
	public Object clone()throws CloneNotSupportedException{
		return super.clone();
	}

//	@Override
//	public int compare(Object arg0, Object arg1) {
//		// TODO Auto-generated method stub
//		JugglerDataContainer o1 = (JugglerDataContainer)arg0;
//		JugglerDataContainer o2 = (JugglerDataContainer)arg1;
//		
//		return o1.getCircuitNumber() - o2.getCircuitNumber();
//	}
}
