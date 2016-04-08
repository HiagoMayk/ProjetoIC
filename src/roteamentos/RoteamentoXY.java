package roteamentos;

import entidades.Acumulator;
import entidades.Edge;
import entidades.Enlace;
import entidades.Graph;
import entidades.Processor;
import entidades.Vertex;

public class RoteamentoXY
{
	private Graph grafo; 				// Grafo da aplicação
	private Processor mapeamento[][];	// Rede para mapear os processos
	private Acumulator acumulator;		// Armazena o valor dos acessos a cada enlace
	private int linhas;					// Quantidade de linhas da rede
	private int colunas;				// Quantidade de colunas da rede
	
	public RoteamentoXY(Graph grafo, Processor mapeamento[][], int linhas, int colunas)
	{
		this.grafo = grafo;
		this.mapeamento = mapeamento;
		this.acumulator = new Acumulator(linhas, colunas);
		this.linhas = linhas;
		this.colunas = colunas;
	}

	public void executeFull()
	{
		int linV = 0;
		int colV = 0;
		int linU = 0;
		int colU = 0;
		boolean flagV = false;
		boolean flagU = false;
		
		for(Edge e : grafo.getEdges())
		{
			for(Vertex v : grafo.getVertexes())
			{
				for(Vertex u: grafo.getVertexes())
				{
					flagV = false;
					flagU = false;
					
					if(v.getId() == e.getSource().getId() && u.getId() == e.getDestination().getId())
					{
						for(int i = 0; i < linhas; i++)
						{	
							for(int j = 0; j < colunas; j++)
							{
								if(mapeamento[i][j].getVertex() != null && mapeamento[i][j].getVertex().getId() == v.getId())
								{
									linV = i;
									colV = j;
									flagV = true;
								}
								
								if(mapeamento[i][j].getVertex() != null && mapeamento[i][j].getVertex().getId() == u.getId())
								{
									linU = i;
									colU = j;
									flagU = true;
								}
								
								if(flagV && flagU)
								{
									break;
								}
							}
							
							if(flagV && flagU)
							{
								break;
							}
						}					
					}
					
					if(flagV && flagU)
					{
						int aux = colV;
						while(aux < colU)
						{
							acumulator.incrementaEnlace(mapeamento[linV][aux].getId(), mapeamento[linV][aux+1].getId());
							e.addEnlace(mapeamento[linV][aux].getId(), mapeamento[linV][aux+1].getId());
							aux++;
							e.incrementaHops();
						}
						
						while(aux > colU)
						{
							acumulator.incrementaEnlace(mapeamento[linV][aux].getId(), mapeamento[linV][aux-1].getId());
							e.addEnlace(mapeamento[linV][aux].getId(), mapeamento[linV][aux-1].getId());
							aux--;
							e.incrementaHops();
			
						}
						
						aux = linV;
						while(aux < linU)
						{
							acumulator.incrementaEnlace(mapeamento[aux][colU].getId(), mapeamento[aux+1][colU].getId());
							e.addEnlace(mapeamento[aux][colU].getId(), mapeamento[aux+1][colU].getId());
							aux++;
							e.incrementaHops();
							
						}
						
						while(aux > linU)
						{
							acumulator.incrementaEnlace(mapeamento[aux][colU].getId(), mapeamento[aux-1][colU].getId());
							e.addEnlace(mapeamento[aux][colU].getId(), mapeamento[aux-1][colU].getId());
							aux--;
							e.incrementaHops();
							
						}
						
						break;
					}
				}		
			}
		}
	}
	
	public int totalReutilizado()
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
	
	public float calculaTaxaReuso()
	{
		int totalAcessos = 0;
		
		for(Enlace e: acumulator.getEnlace())
		{
			totalAcessos = totalAcessos + e.getAcessos();
		}
		
		return (100 * totalReutilizado()) / totalAcessos;
	}
	
	/*
	 * Imprime o resultado do acumulador
	 */
	public void printResult()
	{
		int hopAcumulator = 0;
		System.out.println();
		System.out.println("Total de hops e caminhos percorridos: ");
		for(Edge e : grafo.getEdges())
		{
			  System.out.println(e.getSource().getName() + "\t" + "-" + "\t" + e.getDestination().getName() + "\t" + "->" + "\t" + e.getWeight() + "\t Hops: " +  e.getHops());
			  
			  hopAcumulator = hopAcumulator + e.getHops();
			  
			  for(Enlace en : e.getEnlaces())
			  {
				  System.out.println(en.getSource() + " - " + en.getDestination());
			  }
			  System.out.println();
		}

		System.out.println("Enlaces acessados:");
		acumulator.printAcumulator();
		
		System.out.println("Total de hops: " + hopAcumulator);
		System.out.println("Quantidade de enlaces acessados: " + acumulator.getEnlace().size());
		System.out.println("Total de reuso dos enlaces: " + totalReutilizado());
		System.out.println("Taxa de reuso dos enlaces: " + calculaTaxaReuso() + "%");
	}
	
	public Graph getGrafo() 
	{
		return grafo;
	}

	public void setGrafo(Graph grafo) 
	{
		this.grafo = grafo;
	}

	public Processor[][] getMapeamento() 
	{
		return mapeamento;
	}

	public void setMapeamento(Processor[][] mapeamento) 
	{
		this.mapeamento = mapeamento;
	}

	public Acumulator getAcumulator() 
	{
		return acumulator;
	}

	public void setAcumulator(Acumulator acumulator) 
	{
		this.acumulator = acumulator;
	}

	public int getLinhas() 
	{
		return linhas;
	}

	public void setLinhas(int linhas) 
	{
		this.linhas = linhas;
	}

	public int getColunas() 
	{
		return colunas;
	}

	public void setColunas(int colunas) 
	{
		this.colunas = colunas;
	}
}
	