import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Calculator
{
	private Graph grafo; 				// Grafo da aplicação
	private Processor mapeamento[][];	// Rede para mapear os processos
	private Acumulator acumulator;		// Armazena o valor dos acessos a cada enlace
	private int linhas;					// Quantidade de linhas da rede
	private int colunas;				// Quantidade de colunas da rede
	
	public Calculator(Graph grafo, Processor mapeamento[][], int linhas, int colunas)
	{
		this.grafo = grafo;
		this.mapeamento = mapeamento;
		this.acumulator = new Acumulator(linhas, colunas);
		this.linhas = linhas;
		this.colunas = colunas;
	}

	/*
	 * Executa o cálculo dos acessos aos enlaces usando o XY
	 */
	public void executeXY()
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
	
	/*
	 * Executa o cálculo dos acessos aos enlaces alternando entre o XY e o YX
	 */
	public void executeXY_YX()
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
						// Busca o local onde os processos foram mapeados
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
						int auxColV = colV;
						int auxLinV = linV;
						boolean flag = true;
						
						while(flag)
						{
							if(auxColV < colU)
							{
								//Primeira linha
								if(auxLinV == 0 ) //&& auxColV < colunas
								{
										if(acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()) == null || 
										 ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()) != null) && 
										  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()).getAcessos())))
										{
												acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
												e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
												auxColV++;
												e.incrementaHops();												
										}
										else
										{
											if((auxLinV < linU) &&
											  ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()) == null) || 
											   (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()).getAcessos()  < acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()).getAcessos())))
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
													auxLinV++;
													e.incrementaHops();													
											}
											else
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
													auxColV++;
													e.incrementaHops();													
											}
										}
								}
								else if(auxLinV == (linhas-1))
								{
									if(acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()) == null || 
									 ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()) != null) && 
									  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()).getAcessos())))
									{
											acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
											e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
											auxColV++;
											e.incrementaHops();											
									}
									else
									{
										if((auxLinV > linU) &&
										  ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()) == null) || 
										   (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()).getAcessos()  < acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()).getAcessos())))
										{
												acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
												e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
												auxLinV--;
												e.incrementaHops();												
										}
										else
										{
												acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
												e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
												auxColV++;
												e.incrementaHops();												
										}
									}
								}
								else
								{
									if(acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()) == null || 
									 ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()) != null) && 
									  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()) != null) && 
									  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()).getAcessos()) &&
									  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()).getAcessos())))
									{
											acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
											e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
											auxColV++;
											e.incrementaHops();											
									}
									else
									{
											if((auxLinV < linU) &&
											  ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()) == null) || 
											   (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()).getAcessos()  < acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()).getAcessos())))
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
													auxLinV++;
													e.incrementaHops();													
											}
											else if((auxLinV > linU) &&
												   ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()) == null) || 
													(acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()).getAcessos() < acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId()).getAcessos())))
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
													auxLinV--;
													e.incrementaHops();													
											}
											else
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV+1].getId());
													auxColV++;
													e.incrementaHops();													
											}
									}
								}
							}
							else if(auxColV > colU)
							{
								//Ultimo elemento da primeira linha
								if(auxLinV == 0)
								{
										if(acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()) == null ||
										 ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()) != null) &&
										  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()).getAcessos())))
										{
												acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
												e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
												auxColV--;
												e.incrementaHops();												
										}
										else
										{
											if((auxLinV < linU) &&
											  ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()) == null) ||
											   (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()).getAcessos()  < acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()).getAcessos())))
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
													auxLinV++;
													e.incrementaHops();													
											}
											else
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
													auxColV--;
													e.incrementaHops();													
											}
										}
								}
								else if(auxLinV == (linhas-1))
								{
									if(acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()) == null || 
									 ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()) != null) && 
									  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()).getAcessos())))
									{
										acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
										e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
										auxColV--;
										e.incrementaHops();										
									}
									else
									{
										if((auxLinV > linU) &&
										  ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()) == null) || 
										   (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()).getAcessos()  < acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()).getAcessos())))
										{
											acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
											e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
											auxLinV--;
											e.incrementaHops();											
										}
										else
										{
											acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
											e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
											auxColV--;
											e.incrementaHops();											
										}
									}
								}
								else
								{
									if(acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()) == null || 
									 ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()) != null) && 
									  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()) != null) && 
									  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()).getAcessos()) &&
									  (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()).getAcessos())))
									{
											acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
											e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
											auxColV--;
											e.incrementaHops();											
									}
									else
									{
											if((auxLinV < linU) &&
											  ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()) == null) || 
											   (acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId()).getAcessos() < acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()).getAcessos())))
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
													auxLinV++;
													e.incrementaHops();													
											}
											else if((auxLinV > linU) &&
												   ((acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()) == null) || 
													(acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId()).getAcessos() < acumulator.returnEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId()).getAcessos())))
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
													auxLinV--;
													e.incrementaHops();													
											}
											else
											{
													acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
													e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV][auxColV-1].getId());
													auxColV--;
													e.incrementaHops();													
											}
									}
								}
							}	
							else if(auxLinV < linU)
							{
								acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
								e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV+1][auxColV].getId());
								auxLinV++;
								e.incrementaHops();								
					 		}
							else if(auxLinV > linU)
							{
								acumulator.incrementaEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
								e.addEnlace(mapeamento[auxLinV][auxColV].getId(), mapeamento[auxLinV-1][auxColV].getId());
								auxLinV--;
								e.incrementaHops();
							}
							else
							{
								flag = false;
							}
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
	
	public static void main(String args[]) throws CloneNotSupportedException
	{		
		  Entrada entrada = new Entrada();
		  
		  int linhas = 0;
		  int colunas = 0;
		  
		  System.out.println("Digite o grafo de entrada: ");
		  System.out.println();
		  Graph grafo = entrada.lerGrafo();
		 
		  /*
		  System.out.println();
		  System.out.println("Digite o mapeamento: ");
		  System.out.println();
		  Processors mapeamento[][] = entrada.lerMapeamento(grafo.getVertexes());
		  */
		  
		  System.out.println();
		  System.out.println("Tarefas: ");
		  for(Vertex v : grafo.getVertexes())
		  {
			  System.out.println(v.getName());
		  }
		  
		  System.out.println();
		  System.out.println("Comunicações: ");
		  
		  for(Edge e : grafo.getEdges())
		  {
			  System.out.println(e.getSource().getName() + "\t" + "-" + "\t" + e.getDestination().getName() + "\t" + "->" + "\t" + e.getWeight());
		  }
		  
		  ArrayList<Edge> copyEdgers = new ArrayList<Edge>();
		  copyEdgers = grafo.copyEdgers(grafo.getEdges());
		  
		  //-------------- Parte do algoritmo PropostoHM, para usar a entrada por console é só
		  // descomentar o código acima e comentar esse. 
		  PropostoV1 hm = new PropostoV1(grafo.getVertexes(), grafo.getEdges());
		  
		  System.out.println("Digite a quantidade de linhas e colunas da rede: ");
		  Scanner sc = new Scanner(System.in);
		  int lin = sc.nextInt();
		  int col = sc.nextInt();
		  
		  if(lin * col >= grafo.getVertexes().size())
		  {
			  Processor mapeamento[][] = hm.execute(lin, col);
			  //--------------
			  
			  grafo.setEdges(copyEdgers);
			  
			  System.out.println();
			  System.out.println("Mapeamento: ");
			 	  
			  //Imprime rede
			  for(int i = 0; i < lin; i++)
			  {
				  for(int j = 0; j < col; j++)
				  {
					  if(mapeamento[i][j].getVertex() != null)
					  {
						  System.out.print(mapeamento[i][j].getVertex().getName() + " \t");
					  }
					  else
					  {
						  System.out.print(mapeamento[i][j].getId() + "\t");
					  }
				  }
				  System.out.println();
			  }
			  
			  System.out.println();
			  System.out.println("ALGORITMO XY: ");
			  Calculator c = new Calculator(grafo, mapeamento, lin, col);
			  c.executeXY();
			  c.printResult();
			  
			  grafo.zerarHops();
			  grafo.zerarEnlaces();
			  
			  System.out.println();
			  System.out.println("ALGORITMO XY-YX:");
			  c = new Calculator(grafo, mapeamento, lin, col);
			  c.executeXY_YX();
			  c.printResult();
		  }
		  else
		  {
			  System.out.println("Número de processos maior que o de processadores!");
		  }
		  
	}
}