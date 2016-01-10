public class Calculator
{	
	private Graph grafo; 				// Grafo da aplicação
	private Processors mapeamento[][];	// Rede para mapear os processos
	private Acumulator acumulator;		// Armazena o valor dos acessos a cada enlace
	private int linhas;					// Quantidade de linhas da rede
	private int colunas;				// Quantidade de colunas da rede
	
	public Calculator(Graph grafo, Processors mapeamento[][], int linhas, int colunas)
	{
		this.grafo = grafo;
		this.mapeamento = mapeamento;
		this.acumulator = new Acumulator(linhas, colunas);
		this.linhas = linhas;
		this.colunas = colunas;
	}

	/*
	 * Executa o cálculo dos acessos aos enlaces
	 */
	public void execute()
	{
		int linV = 0;
		int colV = 0;  
		int linU = 0;
		int colU = 0;
		boolean flagV = false;
		boolean flagU = false;
		
		for(Vertex v : grafo.getVertexes())
		{
			for(Vertex u: grafo.getVertexes())
			{
				flagV = false;
				flagU = false;
				
				for(Edge e : grafo.getEdges())
				{
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
							aux++;
						}
						
						while(aux > colU)
						{
							acumulator.incrementaEnlace(mapeamento[linV][aux].getId(), mapeamento[linV][aux-1].getId());
							aux--;
						}
						
						aux = linV;
						while(aux < linU)
						{
							acumulator.incrementaEnlace(mapeamento[aux][colU].getId(), mapeamento[aux+1][colU].getId());
							aux++;
						}
						
						while(aux > linU)
						{
							acumulator.incrementaEnlace(mapeamento[aux][colU].getId(), mapeamento[aux-1][colU].getId());
							aux--;
						}
						
						break;
					}
				}		
			}
		}
	}
	
	/*
	 * Imprime o resultado do acumulador
	 */
	public void printResult()
	{
		acumulator.printAcumulator();
	}
	
	public static void main(String args[])
	{		
		  Entrada entrada = new Entrada();
		  
		  System.out.println("Digite o grafo de entrada:");
		  Graph grafo = entrada.lerGrafo();
		  
		  for(Vertex v : grafo.getVertexes())
		  {
			  System.out.println(v.getId());
		  }
		  
		  for(Edge e : grafo.getEdges())
		  {
			  System.out.println(e.getSource().getId() + " - " + e.getDestination().getId() + " -> " + e.getWeight());
		  }
  
		  Processors mapeamento[][] = entrada.lerMapeamento(grafo.getVertexes());
		  
		   /*
		  for(int i = 0; i < 7; i++)
		  {
			  for(int j = 0; j < 6; j++)
			  {
				  System.out.print(mapeamento[i][j].getId() + "  ");
			  }
			  System.out.println();
		  }
		  */
		  
		  Calculator c = new Calculator(grafo, mapeamento, 7, 6);
		  c.execute();
		  c.printResult();
	}
}