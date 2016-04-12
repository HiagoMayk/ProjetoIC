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
							 acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()+1].getId());
                             pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn() + 1].getId());
                             pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() + 1);
                             //pacote.setHops(pacote.getHops() + 1);
                             
                             roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);
						}
						else if(pacote.getCurrentCoordinate().getColumn() > pacote.getCoordinateDestination().getColumn())
						{
                			acumulator.incrementaEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                            pacote.addEnlace(mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].getId(), mapeamento[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()-1].getId());
                            pacote.getCurrentCoordinate().setColumn(pacote.getCurrentCoordinate().getColumn() - 1);
                            //pacote.setHops(pacote.getHops() + 1);   
                                           
                            roteadores[pacote.getCurrentCoordinate().getLine()][pacote.getCurrentCoordinate().getColumn()].addBufferIn(pacote);		
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
	