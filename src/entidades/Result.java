package entidades;

public class Result 
{
	private int totalHops;
	private int totalEnlaces;
	private int totalHopsParalelismo;
	
	public Result(int totalHops, int totalEnlaces, float taxaReuso)
	{
		this.totalHops = totalHops;
		this.totalEnlaces = totalEnlaces;
	}
	
	public Result()
	{
		this.totalHopsParalelismo = 0;
		this.totalHops = 0;
		this.totalEnlaces = 0;
	}
	
	public int totalUso(Acumulator acumulator)
	{
		int totalReuso = 0;
		
		for(Enlace e: acumulator.getEnlace())
		{
			totalReuso = totalReuso + e.getAcessos();
		}
		
		return totalReuso;
	}
	
	public int totalReuso(Acumulator acumulator)
	{
		int totalReuso = 0;
		
		for(Enlace e: acumulator.getEnlace())
		{
			if(e.getAcessos() > 1)
			{
				totalReuso = totalReuso + 1;
			}
		}
		
		return totalReuso;
	}
	
	public int totalReutilizado(Acumulator acumulator)
	{
		return totalUso(acumulator) - getTotalEnlaces();
	}
	
	public float calculaTaxaReuso(Acumulator acumulator)
	{
		return (100 * totalReutilizado(acumulator)) / totalUso(acumulator);
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
	
	public int getTotalHopsParalelismo() 
	{
		return totalHopsParalelismo;
	}

	public void setTotalHopsParalelismo(int totalHopsParalelismo)
	{
		this.totalHopsParalelismo = totalHopsParalelismo;
	}
}
