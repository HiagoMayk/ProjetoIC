package roteamentos;

import entidades.Acumulator;
import entidades.Edge;
import entidades.Enlace;
import entidades.Graph;
import entidades.Pacote;
import entidades.Processor;
import entidades.Vertex;

public class RoteamentoXY extends Roteamento
{

	public RoteamentoXY(Graph grafo, Processor mapeamento[][], int linhas, int colunas)
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
					//insere os do in no out
					roteadores[i][j].incrementaLatencia();
				}
			}
			
			for(int i = 0; i < linhas; i++)
			{
				for(int j = 0; j < colunas; j++)
				{
					//Aplica a troca dos pacotes entre os buffers
					//insere os do in no out
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
					roteadores[i][j].setBaixo(true);
					roteadores[i][j].setCima(true);
					roteadores[i][j].setDireita(true);
					roteadores[i][j].setEsquerda(true);
					roteadores[i][j].setLatenciaDireita(0);
					roteadores[i][j].setLatenciaEsquerda(0);
					roteadores[i][j].setLatenciaBaixo(0);
					roteadores[i][j].setLatenciaCima(0);
					
						while((roteadores[i][j].getBufferOut().size() > 0) && (roteadores[i][j].isBaixo() || roteadores[i][j].isCima() || roteadores[i][j].isDireita() || roteadores[i][j].isEsquerda()))
						{
							//Não precisa verificar se os pacotes estão em ordem de prioridade no buffer, pois
							//isso já é feito na inserção dos pacotes no buffer na classe Router.
							
							//Devemos verificar a coordenada do lugar de destino e mandar o pacote
							
							//Remove o primeiro pacote do buffer, lembre-se que esse tem a maior prioridade
							Pacote pacote = roteadores[i][j].getBufferOut().remove(0);
							if(roteadores[i][j].isDireita() && pacote.getCurrentCoordinate().getColumn() < pacote.getCoordinateDestination().getColumn())
				            {
								 acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
								 acumulator.incrementaFlits(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId(), pacote.getFlits());
							
								 pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn() + 1].getId());
	                             pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
	                             roteadores[i][j].setDireita(false);
	                             roteadores[i][j].setLatenciaDireita(pacote.getFlits());
	                             
	                             // conta a lantencia do salto
	                             pacote.setLatencia(pacote.getLatencia() + 1);
	                             //pacote.setHops(pacote.getHops() + 1);
	                             
	                             roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
							}
							else if(roteadores[i][j].isEsquerda() && pacote.getCurrentCoordinate().getColumn() > pacote.getCoordinateDestination().getColumn())
							{
	                			acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
	                			acumulator.incrementaFlits(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId(), pacote.getFlits());

	                			pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
	                            pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);
	                            //pacote.setHops(pacote.getHops() + 1);   
	                            roteadores[i][j].setEsquerda(false);
	                            roteadores[i][j].setLatenciaEsquerda(pacote.getFlits());
	                         
	                            pacote.setLatencia(pacote.getLatencia() + 1);
	                
	                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);		
							}
							else if(roteadores[i][j].isBaixo() && pacote.getCurrentCoordinate().getLine() < pacote.getCoordinateDestination().getLine())
							{
								acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
								acumulator.incrementaFlits(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId(), pacote.getFlits());
								
								pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()+1][pacote.getCurrentCoordinate().getColumn()].getId());
	                            pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() + 1);
	                            //pacote.setHops(pacote.getHops() + 1);
	                            roteadores[i][j].setBaixo(false);
	                            roteadores[i][j].setLatenciaBaixo(pacote.getFlits());
	                            pacote.setLatencia(pacote.getLatencia() + 1);
	                                   
	                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
							}
							else if(roteadores[i][j].isCima() && pacote.getCurrentCoordinate().getLine() > pacote.getCoordinateDestination().getLine())
							{
								acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
								acumulator.incrementaFlits(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId(), pacote.getFlits());

								pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()-1][pacote.getCurrentCoordinate().getColumn()].getId());
	                            pacote.getCurrentCoordinate().setLine(pacote.getCurrentCoordinate().getLine() - 1);
	                            //pacote.setHops(pacote.getHops()+1);
	                            roteadores[i][j].setCima(false);
	                            roteadores[i][j].setLatenciaCima(pacote.getFlits());
	                            pacote.setLatencia(pacote.getLatencia() + 1);
	                                   
	                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);			
							}
							else
							{
								roteadores[i][j].addBufferAux(pacote);
							}
						}
				}
	         }
	    }
	}
}
	