package roteamentos;

import java.util.ArrayList;

import entidades.*;

public class RoteamentoXY_YX
{
	private Graph grafo; 				// Grafo da aplicação
	private Processor mapeamento[][];	// Rede para mapear os processos
	private Acumulator acumulator;		// Armazena o valor dos acessos a cada enlace
	private int linhas;					// Quantidade de linhas da rede
	private int colunas;				// Quantidade de colunas da rede
	private Router roteadores[][];
	
	public RoteamentoXY_YX(Graph grafo, Processor mapeamento[][], int linhas, int colunas)
	{
		this.grafo = grafo;
		this.mapeamento = mapeamento;
		this.acumulator = new Acumulator();
		this.linhas = linhas;
		this.colunas = colunas;
		this.roteadores = new Router[linhas][colunas];
		
		//Sincroniza os roteadores, ou seja, insere os processos referente a cada roteador
		sincronizeRouters();
		printRoteadores();
		//printBufferRoutersWithProcess();
		
		System.out.println("Antes:===========================");
		printBufferRoutersWithoutProcess();
		executeByStep();
		
		System.out.println("Depois:===========================");
		printBufferRoutersWithoutProcess();
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
						//Busca o local onde os processos foram mapeados
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
	
	public void executeByStep()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				if(roteadores[i][j].getBuffer().size() > 0)
				{
					//Não precisa verificar se os pacotes estão em ordem de prioridade no buffer, pois
					//isso já é feito na inserção dos pacotes no buffer na classe Router.
					
					//Devemos Verificar a coordenada do lugar de destino e mandar o pacote
					
					//Remove o primeiro pacote do buffer, lembre-se que esse tem a maior prioridade
					Pacote pacote = roteadores[i][j].getBuffer().remove(0);
					if(pacote.getCurrentCoordinate().getColumn() < pacote.getCoordinateDestination().getColumn())
		            {
						//Primeira linha
                        if(pacote.getCurrentCoordinate().getLine() == 0)
                        {
                                if(acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()) == null || 
                                 ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()) != null) && 
                                  (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()).getAcessos() <=
                                   acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos())))
                                {
                                        acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                        pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn() + 1].getId());
                                        pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
                                        pacote.setHops(pacote.getHops() + 1);
                                        
                                        roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                }
                                else
                                {
                                    if((pacote.getCurrentCoordinate().getLine() < pacote.getCoordinateDestination().getLine()) &&
                                      ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) || 
                                       (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos() <
                                       acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()).getAcessos())))
                                    {
                                           acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                                           pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                                           pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() + 1);
                                           pacote.setHops(pacote.getHops() + 1); 
                                           
                                           roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                    }
                                    else
                                    {
                                           acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                           pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                           pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
                                           pacote.setHops(pacote.getHops() + 1);    
                                           
                                           roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                    }
                                }
                        }
                        else if(pacote.getCurrentCoordinate().getLine() == (linhas-1))
                        {
                            if(acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()) == null || 
                             ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) != null) && 
                              (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()).getAcessos() <=
                              acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos())))
                            {
                                    acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                    pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                    pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
                                    pacote.setHops(pacote.getHops() + 1);  
                                    
                                    roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                            }
                            else
                            {
                            	if((pacote.getCurrentCoordinate().getLine() > pacote.getCoordinateDestination().getLine()) &&
                                        ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) || 
                                        (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos() < 
                                        acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()).getAcessos())))
                                         {
                                                   acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                                                   pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                                                   pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() - 1);
                                                   pacote.setHops(pacote.getHops() + 1);  
                                                   
                                                   roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                           }
                                           else
                                           {
                                                   acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                                   pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                                   pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
                                                   pacote.setHops(pacote.getHops() + 1);     
                                                   
                                                   roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                           }
                                       }
                                   }
                                   else
                                   {
                                       if(acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()) == null || 
                                        ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()) != null) && 
                                         (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) != null) && 
                                         (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos()) &&
                                         (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos())))
                                       {
                                               acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                               pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                               pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
                                               pacote.setHops(pacote.getHops() + 1);  
                                               
                                               roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                       }
                                       else
                                       {
                                               if((pacote.getCurrentCoordinate().getLine() < pacote.getCoordinateDestination().getLine()) &&
                                                 ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) || 
                                                  (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos()  < acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()).getAcessos())))
                                               {
                                                       acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                                                       pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                                                       pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() + 1);;
                                                       pacote.setHops(pacote.getHops() + 1);
                                                       
                                                       roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                               }
                                               else if((pacote.getCurrentCoordinate().getLine() > roteadores[i][j].getProcessor().getCoordinate().getLine()) &&
                                                      ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) || 
                                                       (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos() < acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()).getAcessos())))
                                               {
                                                       acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                                                       pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                                                       pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() - 1);
                                                       pacote.setHops(pacote.getHops() + 1);   
                                                       
                                                       roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                               }
                                               else
                                               {
                                                       acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                                       pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                                                       pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
                                                       pacote.setHops(pacote.getHops() + 1);   
                                                       
                                                       roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                               }
                                       }
                             }
                        }
                        else if(pacote.getCurrentCoordinate().getColumn() > pacote.getCoordinateDestination().getColumn())
                        {
                        	//Ultimo elemento da primeira linha
                        	if(pacote.getCurrentCoordinate().getLine() == 0)
                            {
                        		if(acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()) == null ||
                                ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()) != null) &&
                                (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos())))
                                {
                        			acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                    pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                    pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);
                                    pacote.setHops(pacote.getHops() + 1);   
                                                   
                                    roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                 }
                                 else
                                 {
                                    if((pacote.getCurrentCoordinate().getLine() < pacote.getCoordinateDestination().getLine()) &&
                                    ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) ||
                                    (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos()  < acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()).getAcessos())))
                                     {
                                     	acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                                        pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                                        pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() + 1);
                                        pacote.setHops(pacote.getHops() + 1); 
                                                       
                                        roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                     }
                                     else
                                     {
                                        acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                        pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                        pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);;
                                        pacote.setHops(pacote.getHops() + 1); 
                                                       
                                        roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                     }
                                 }
                             }
                             else if(pacote.getCurrentCoordinate().getLine() == (linhas-1))
                             {
                            	 if(acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()) == null || 
                                 ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) != null) && 
                                 (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos())))
                                 {
                            		 acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                     pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                     pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);
                                     pacote.setHops(pacote.getHops() + 1);          
                                           
                                     roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                  }
                                  else
                                  {
                                	  if((pacote.getCurrentCoordinate().getLine() > pacote.getCoordinateDestination().getLine()) &&
                                      ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) || 
                                      (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos()  < acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()).getAcessos())))
                                      {
                                		  acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                                          pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                                          pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() - 1);
                                          pacote.setHops(pacote.getHops() + 1);  
                                               
                                          roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                   }
                                   else
                                   {
                                   		acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                        pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                        pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);
                                        pacote.setHops(pacote.getHops() + 1); 
                                               
                                        roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                   }
                              }
                        }
                        else
                        {
                        	if(acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()) == null || 
                            ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()) != null) && 
                            (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) != null) && 
                            (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos()) &&
                            (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()).getAcessos() <= acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos())))
                            {
                        		acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);
                                pacote.setHops(pacote.getHops() + 1);  
                                               
                                roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                            }
                            else
                            {
                            	if((pacote.getCurrentCoordinate().getLine() < pacote.getCoordinateDestination().getLine()) &&
                                ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) || 
                                (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos() < acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()).getAcessos())))
                                {
                            		acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                                    pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                                    pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() + 1);
                                    pacote.setHops(pacote.getHops() + 1);  
                                                       
                                    roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                 }
                                 else if((pacote.getCurrentCoordinate().getLine() > pacote.getCoordinateDestination().getLine()) &&
                                		((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) || 
                                		(acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos() < acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()).getAcessos())))
                                 		{
                                	 		acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                                            pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                                            pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() - 1);
                                            pacote.setHops(pacote.getHops() + 1);     
                                                       
                                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                         }
                                         else
                                         {
                                        	 acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                             pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                                             pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);
                                             pacote.setHops(pacote.getHops() + 1);    
                                                       
                                             roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                                          }
                                 }
                             }
                        }   
                        else if(pacote.getCurrentCoordinate().getLine() < pacote.getCoordinateDestination().getLine())
                        {
                        	acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                            pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
                            pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() + 1);
                            pacote.setHops(pacote.getHops() + 1);
                                   
                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                        }
                        else if(pacote.getCurrentCoordinate().getLine() > pacote.getCoordinateDestination().getLine())
                        {
                        	acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                            pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
                            pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() - 1);
                            pacote.setHops(pacote.getHops()+1);
                                   
                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBuffer(pacote);
                        }
                   }
              }
         }
    }
	
	/*
	 * Pega todos os processoa para o qual o processo parassado como parâmetro manda pacotes
	 */
	public ArrayList<Processor> comunicateTo(Processor proc)
	{
		ArrayList<Processor> procs = new ArrayList<Processor>();
		
		//Para toda comunicação existste
		for(Edge e : grafo.getEdges())
		{
			//Se o identificador do processo for o mesmo que o identificador 
			//da cominucação que está enviando o pacote
			if(proc.getVertex().getId() == e.getSource().getId())
			{
				//Percorre todos os roteadores para encontar o processo de destino do pacote
				//Isso será feito para poder pegar a referencia do processo assim como a cua coordenada
				for(int i = 0; i < linhas; i++)
				{
					for(int j = 0; j < colunas; j++)
					{
						if(mapeamento[i][j].getVertex() != null && mapeamento[i][j].getVertex().getId() == e.getDestination().getId())
						{
							procs.add(mapeamento[i][j]);
						}
					}
				}
			}
		}
		
		return procs;
	}
	
	//Imprime os buffers dos roteadores que possem processos rodando
	public void printBufferRoutersWithProcess()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				if(roteadores[i][j].getProcessor().getVertex() != null)
				{
					System.out.println("-------------------- " + roteadores[i][j].getProcessor().getVertex().getName() + "-------------------- ");
					for(Pacote p : roteadores[i][j].getBuffer())
					{
						System.out.println("Coordenada: " + p.getCurrentCoordinate().getLine() + ", " + p.getCurrentCoordinate().getColumn());
						System.out.println("Prioridade: " + p.getPriority() );
						System.out.println("Origem: " + p.getSource().getId());
						System.out.println("Destino: " + p.getDestination().getId());
						System.out.println();
					}
				}
			}
			
		}
	}
	
	//Imprime todos os rotedores que possuem pacotes no buffer e seus respectivos pacotes
	public void printBufferRoutersWithoutProcess()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				if(roteadores[i][j].getBuffer().size() != 0)
				{
					System.out.println("-------------------- Roteador (" + i + ", " + j + ") -------------------- ");
					for(Pacote p : roteadores[i][j].getBuffer())
					{
						System.out.println("Coordenada: " + p.getCurrentCoordinate().getLine() + ", " + p.getCurrentCoordinate().getColumn());
						System.out.println("Prioridade: " + p.getPriority() );
						System.out.println("Origem: " + p.getSource().getId());
						System.out.println("Destino: " + p.getDestination().getId());
						System.out.println();
					}
				}
			}
			
		}
	}
	
	public void printRoteadores()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				if(roteadores[i][j].getProcessor().getVertex() == null)
				{
					System.out.print(roteadores[i][j].getProcessor().getId() + "\t");
				}
				else
				{
					System.out.print(roteadores[i][j].getProcessor().getVertex().getName() + "\t");
				}
			}
			
			System.out.println();
		}
	}
	
	public void sincronizeRouters()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				roteadores[i][j] = new Router(mapeamento[i][j]);
				if(mapeamento[i][j].getVertex() != null)
				{
					ArrayList<Processor> comunica = new ArrayList<Processor>();
					comunica = comunicateTo(roteadores[i][j].getProcessor());
					
					//Pode ocorrer a existencia de processos que não  enviam mensagens
					if(comunica.size() > 0)
					{
						for(int k = 0; k < roteadores[i][j].getProcessor().getVertex().getOutdegree(); k++)
						{
							if(roteadores[i][j].getProcessor().getVertex().getOutdegree() > 0)
							{
								//Contrutur de Pacote: 
								//Pacote(int priority, Processor source, Processor destination, int x, int y)
								//System.out.println(comunica.size() + " ====== " + roteadores[i][j].getProcessor().getVertex().getOutdegree());
								Pacote p = new Pacote(roteadores[i][j].getProcessor().getVertex().getOutdegree(), roteadores[i][j].getProcessor(), comunica.get(k), i, j);
								roteadores[i][j].addBuffer(p);
							}
						}
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
		//System.out.println();
		//System.out.println("Total de hops e caminhos percorridos: ");
		for(Edge e : grafo.getEdges())
		{
			  //System.out.println(e.getSource().getName() + "\t" + "-" + "\t" + e.getDestination().getName() + "\t" + "->" + "\t" + e.getWeight() + "\t Hops: " +  e.getHops());
			  
			  hopAcumulator += e.getHops();
			  
			  /*
			  for(Enlace en : e.getEnlaces())
			  {
				  System.out.println(en.getSource() + " - " + en.getDestination());
			  }
			  System.out.println();
			  */
		}

		//System.out.println("Enlaces acessados:");
		//acumulator.printAcumulator();
		
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
	