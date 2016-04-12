package entidades;

public class Result 
{
	private int totalHops;
	private int totalEnlaces;
	
	public Result(int totalHops, int totalEnlaces, float taxaReuso)
	{
		this.totalHops = totalHops;
		this.totalEnlaces = totalEnlaces;
	}
	
	public Result()
	{
		this.totalHops = 0;
		this.totalEnlaces = 0;
	}
	
	public int totalReutilizado(Acumulator acumulator)
	{
		int totalReutilizado = 0;
		
		for(Enlace e: acumulator.getEnlace())
		{
			if(e.getAcessos() > 1)
			{
				totalReutilizado = totalReutilizado + e.getAcessos() - 1;
			}
		}
		
		return totalReutilizado;
	}
	
	
	public float calculaTaxaReuso(Acumulator acumulator)
	{
		int totalAcessos = 0;
		
		for(Enlace e: acumulator.getEnlace())
		{
			totalAcessos = totalAcessos + e.getAcessos();
		}
		
		return (100 * totalReutilizado(acumulator)) / totalAcessos;
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
}
