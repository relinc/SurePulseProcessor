package net.relinc.processor.sample;

import net.relinc.processor.staticClasses.SPSettings;

public class TensionRoundSample extends Sample {

	private double diameter;

	public TensionRoundSample() {
		//setSampleType("Tension Round Sample");
	}
	
	
	
	public void setSpecificParameters(String des, String val) {
		if(des.equals("Diameter"))
			setDiameter(Double.parseDouble(val));
	}

	@Override
	public String getSpecificString() {
		return getDiameter() > 0 ? "Diameter"+delimiter+getDiameter()+SPSettings.lineSeperator : "";
	}
	
	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	@Override
	public double[] getTrueStressFromEngStressAndEngStrain(double[] engStress, double[] engStrain) {
		//eng stress and strain must be equal length and time-matched. 
		double[] trueStress = new double[engStrain.length];
		for(int i = 0; i < trueStress.length; i++){
			trueStress[i] = engStress[i] * (1 + engStrain[i]);
		}
		return trueStress;
	}
	
	private double getInitialCrossSectionalArea(){
		return Math.pow((getDiameter() / 2),2) * Math.PI;
	}
	
	@Override
	public double[] getEngineeringStressFromForce(double[] force){
		double[] stressValues = new double[force.length];
		for(int i = 0; i < stressValues.length; i++){
			stressValues[i] = force[i] / getInitialCrossSectionalArea(); //method is above
		}
		return stressValues;
	}
}
