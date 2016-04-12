package roteamentos;

import java.util.ArrayList;

import entidades.*;

public class RoteamentoXY_YX extends Roteamento
{
	public RoteamentoXY_YX(Graph grafo, Processor mapeamento[][], int linhas, int colunas)
	{
		super(grafo, mapeamento, linhas, colunas);
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
		while(count < grafo.getEdges().size())
		{
			for(int i = 0; i < linhas; i++)
			{
				for(int j = 0; j < colunas; j++)
				{
					//Remove o primeiro pacote do bufferIn
					//Assumimos que o pacote está sendo entregue ao processo que executa no processador referente a esse roteador
					count += roteadores[i][j].pacoteToHere();
				}
			}
			
			for(int i = 0; i < linhas; i++)
			{
				for(int j = 0; j < colunas; j++)
				{
					//Aplica a troca dos pacotes entre os buffers
					roteadores[i][j].changeBuffer();
				}
			}
			
			for(int i = 0; i < linhas; i++)
			{
				for(int j = 0; j < colunas; j++)
				{
					for(Pacote pacote : roteadores[i][j].getBufferOut())
					{
						pacote.setHops(pacote.getHops() + 1);
					}
				}
			}
			
			
			for(int i = 0; i < linhas; i++)
			{
				for(int j = 0; j < colunas; j++)
				{
					if(roteadores[i][j].getBufferOut().size() > 0)
					{
						//Não precisa verificar se os pacotes estão em ordem de prioridade no buffer, pois
						//isso já é feito na inserção dos pacotes no buffer na classe Router.
						
						//Devemos Verificar a coordenada do lugar de destino e mandar o pacote
						
						//Remove o primeiro pacote do buffer, lembre-se que esse tem a maior prioridade
						Pacote pacote = roteadores[i][j].getBufferOut().remove(0);
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
	                                        //pacote.setHops(pacote.getHops() + 1);
	                                        
	                                        roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                           //pacote.setHops(pacote.getHops() + 1); 
	                                           
	                                           roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                                    }
	                                    else
	                                    {
	                                           acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
	                                           pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
	                                           pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
	                                           //pacote.setHops(pacote.getHops() + 1);    
	                                           
	                                           roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                    //pacote.setHops(pacote.getHops() + 1);  
	                                    
	                                    roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                                   //pacote.setHops(pacote.getHops() + 1);  
	                                                   
	                                                   roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                                           }
	                                           else
	                                           {
	                                                   acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
	                                                   pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
	                                                   pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
	                                                   //pacote.setHops(pacote.getHops() + 1);     
	                                                   
	                                                   roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                               //pacote.setHops(pacote.getHops() + 1);  
	                                               
	                                               roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                                       //pacote.setHops(pacote.getHops() + 1);
	                                                       
	                                                       roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                                               }
	                                               else if((pacote.getCurrentCoordinate().getLine() > roteadores[i][j].getProcessor().getCoordinate().getLine()) &&
	                                                      ((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) || 
	                                                       (acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos() < acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId()).getAcessos())))
	                                               {
	                                                       acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
	                                                       pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
	                                                       pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() - 1);
	                                                       //pacote.setHops(pacote.getHops() + 1);   
	                                                       
	                                                       roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                                               }
	                                               else
	                                               {
	                                                       acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
	                                                       pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
	                                                       pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
	                                                       //pacote.setHops(pacote.getHops() + 1);   
	                                                       
	                                                       roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                    //pacote.setHops(pacote.getHops() + 1);   
	                                                   
	                                    roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                        //pacote.setHops(pacote.getHops() + 1); 
	                                                       
	                                        roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                                     }
	                                     else
	                                     {
	                                        acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
	                                        pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
	                                        pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);;
	                                        //pacote.setHops(pacote.getHops() + 1); 
	                                                       
	                                        roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                     //pacote.setHops(pacote.getHops() + 1);          
	                                           
	                                     roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                          //pacote.setHops(pacote.getHops() + 1);  
	                                               
	                                          roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                                   }
	                                   else
	                                   {
	                                   		acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
	                                        pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
	                                        pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);
	                                        //pacote.setHops(pacote.getHops() + 1); 
	                                               
	                                        roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                //pacote.setHops(pacote.getHops() + 1);  
	                                               
	                                roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
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
	                                    //pacote.setHops(pacote.getHops() + 1);  
	                                                       
	                                    roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                                 }
	                                 else if((pacote.getCurrentCoordinate().getLine() > pacote.getCoordinateDestination().getLine()) &&
	                                		((acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()) == null) || 
	                                		(acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId()).getAcessos() < acumulator.returnEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId()).getAcessos())))
	                                 		{
	                                	 		acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
	                                            pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
	                                            pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() - 1);
	                                            //pacote.setHops(pacote.getHops() + 1);     
	                                                       
	                                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                                         }
	                                         else
	                                         {
	                                        	 acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
	                                             pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
	                                             pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);
	                                             //pacote.setHops(pacote.getHops() + 1);    
	                                                       
	                                             roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                                          }
	                                 }
	                             }
	                        }   
	                        else if(pacote.getCurrentCoordinate().getLine() < pacote.getCoordinateDestination().getLine())
	                        {
	                        	acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
	                            pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
	                            pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() + 1);
	                            //pacote.setHops(pacote.getHops() + 1);
	                                   
	                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                        }
	                        else if(pacote.getCurrentCoordinate().getLine() > pacote.getCoordinateDestination().getLine())
	                        {
	                        	acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
	                            pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
	                            pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() - 1);
	                            //pacote.setHops(pacote.getHops()+1);
	                                   
	                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
	                        }
	                   }
	              }	
	         }
	    }
	}
		
}
	