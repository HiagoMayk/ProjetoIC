package entidades;

public class Result 
{
	private int totalHops;
	private int totalEnlaces;
	private int totalHopsParalelismo;
	
	private int latencia_pct_maior_latencia;
	private int hops_pct_maior_latencia;
	private int latencia_pct_maior_hop;
	private int hops_pct_maior_hop;
	private int latencia_somatorio;
	private int latencia_media;
	private int enlaces_acessasdos;
	private int somatorio_acessos_enlaces;
	private int enlaces_reusados;
	private int enlaces_reuso;
	private float taxa_reuso;
	private int total_flits;
	private int somatorio_acessos_flits;
	private int reuso_flits;
	private float taxa_reuso_flits;
	
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
	
	public int totalUsoFlits(Acumulator acumulator)
	{
		int totalReuso = 0;
		
		for(Enlace e: acumulator.getEnlace())
		{
			totalReuso = totalReuso + e.getFlits();
		}
		
		return totalReuso;
	}
	
	/*
	 * Pega a quantidde total de flits dos enlaces que foram reusados
	 */
	public int totalReutilizadoFlits(Acumulator acumulator)
	{
		int totalReuso = 0;
		
		for(Enlace e: acumulator.getEnlace())
		{
			if(e.getAcessos() > 1)
			{
				totalReuso = totalReuso + e.getFlits();
			}
		}
		
		return totalReuso;
	}
	
	public float calculaTaxaReusoFlits(Acumulator acumulator)
	{
		return (100 * totalReutilizadoFlits(acumulator)) / totalUsoFlits(acumulator);
	}

	
	//Quantidade de vezes que os enlaces reutilizados foram acessados
	public int totalReutilizado(Acumulator acumulator)
	{
		return totalUso(acumulator) - getTotalEnlaces();
	}
	
	//Percentual da quantidade de vezes total de acesso aos enlaces reusados pelos acessos toral a todos os enlaces
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
	
	public int getLatencia_pct_maior_latencia() 
	{
		return latencia_pct_maior_latencia;
	}

	public void setLatencia_pct_maior_latencia(int latencia_pct_maior_latencia) 
	{
		this.latencia_pct_maior_latencia = latencia_pct_maior_latencia;
	}

	public int getHops_pct_maior_latencia() 
	{
		return hops_pct_maior_latencia;
	}

	public void setHops_pct_maior_latencia(int hops_pct_maior_latencia) 
	{
		this.hops_pct_maior_latencia = hops_pct_maior_latencia;
	}

	public int getLatencia_pct_maior_hop() 
	{
		return latencia_pct_maior_hop;
	}

	public void setLatencia_pct_maior_hop(int latencia_pct_maior_hop) 
	{
		this.latencia_pct_maior_hop = latencia_pct_maior_hop;
	}

	public int getHops_pct_maior_hop() 
	{
		return hops_pct_maior_hop;
	}

	public void setHops_pct_maior_hop(int hops_pct_maior_hop) 
	{
		this.hops_pct_maior_hop = hops_pct_maior_hop;
	}

	public int getLatencia_somatorio() 
	{
		return latencia_somatorio;
	}

	public void setLatencia_somatorio(int latencia_somatorio) 
	{
		this.latencia_somatorio = latencia_somatorio;
	}

	public int getLatencia_media() 
	{
		return latencia_media;
	}

	public void setLatencia_media(int latencia_media) 
	{
		this.latencia_media = latencia_media;
	}

	public int getEnlaces_acessasdos() 
	{
		return enlaces_acessasdos;
	}

	public void setEnlaces_acessasdos(int enlaces_acessasdos) 
	{
		this.enlaces_acessasdos = enlaces_acessasdos;
	}

	public int getSomatorio_acessos_enlaces() 
	{
		return somatorio_acessos_enlaces;
	}

	public void setSomatorio_acessos_enlaces(int somatorio_acessos_enlaces) 
	{
		this.somatorio_acessos_enlaces = somatorio_acessos_enlaces;
	}

	public int getEnlaces_reusados() 
	{
		return enlaces_reusados;
	}

	public void setEnlaces_reusados(int enlaces_reusados) 
	{
		this.enlaces_reusados = enlaces_reusados;
	}

	public int getEnlaces_reuso() 
	{
		return enlaces_reuso;
	}

	public void setEnlaces_reuso(int enlaces_reuso) 
	{
		this.enlaces_reuso = enlaces_reuso;
	}

	public float getTaxa_reuso() 
	{
		return taxa_reuso;
	}

	public void setTaxa_reuso(float taxa_reuso) 
	{
		this.taxa_reuso = taxa_reuso;
	}

	public int getTotal_flits() 
	{
		return total_flits;
	}

	public void setTotal_flits(int total_flits) 
	{
		this.total_flits = total_flits;
	}

	public int getSomatorio_acessos_flits() 
	{
		return somatorio_acessos_flits;
	}

	public void setSomatorio_acessos_flits(int somatorio_acessos_flits) 
	{
		this.somatorio_acessos_flits = somatorio_acessos_flits;
	}

	public int getReuso_flits() 
	{
		return reuso_flits;
	}

	public void setReuso_flits(int reuso_flits) 
	{
		this.reuso_flits = reuso_flits;
	}

	public float getTaxa_reuso_flits() 
	{
		return taxa_reuso_flits;
	}

	public void setTaxa_reuso_flits(float taxa_reuso_flits) 
	{
		this.taxa_reuso_flits = taxa_reuso_flits;
	}
}
