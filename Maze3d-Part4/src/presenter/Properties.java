package presenter;

import java.io.Serializable;

/**
 * <h1>Properties</h1>
 * @author Stas Fainberg
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Properties implements Serializable 
{

	String algoSolve;
	String algoCreate;
	String uitype;
	
	
	/**************************** Constructor of Properties ************************/
	@SuppressWarnings("unused")
	public Properties()
	{
		String algoSolve = null;
		String algoCreate = null;
		String uitype = null;
	}

	
	/********************************** Setters & Getters *************************/
	public String getUitype() {
		return uitype;
	}

	public void setUitype(String uitype) {
		this.uitype = uitype;
	}

	public String getAlgoSolve() 
	{
		return algoSolve;
	}
	
	public void setAlgoSolve(String algosolve)
	{
		this.algoSolve = algosolve;
	}
	
	public String getAlgoCreate() 
	{
		return algoCreate;
	}
	
	public void setAlgoCreate(String algocreate)
	{
		this.algoCreate = algocreate;
	}
	
	
}
