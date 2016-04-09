package entidades;

public class Result 
{
	private int totalHops;
	private int totalEnlaces;
	private float taxaReuso;
	
	public Result(int totalHops, int totalEnlaces, float taxaReuso)
	{
		this.totalHops = totalHops;
		this.totalEnlaces = totalEnlaces;
		this.taxaReuso = taxaReuso;
	}

	public int getTotalHops() 
	{
		return totalHops;
	}

	public void setTotalHops(int totalHops)
	{
		this.totalHops = totalHops;
	}

	public int getTotalEnlaces() 
	{
		return totalEnlaces;
	}

	public void setTotalEnlaces(int totalEnlaces)
	{
		this.totalEnlaces = totalEnlaces;
	}

	public float getTaxaReuso() 
	{
		return taxaReuso;
	}

	public void setTaxaReuso(float taxaReuso) 
	{
		this.taxaReuso = taxaReuso;
	}
}
