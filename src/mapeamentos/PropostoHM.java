package mapeamentos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entidades.Coordinate;
import entidades.Edge;
import entidades.Processor;
import entidades.Vertex;

public class PropostoHM
{
	protected ArrayList<Edge> comunications;
	protected ArrayList<Vertex> procs;
	protected Processor network[][];
	protected int linhas;
	protected int colunas;
	protected int maxDist;
	
	//As variáveis seguintes guardam as ultimas coordenadas de alocação de tarefas 
	int linhaAlocada;
	int colunaAlocada;
	
	public PropostoHM(List<Vertex> procs, List<Edge> comunications)
	{
		this.comunications = (ArrayList<Edge>) comunications;
		this.procs = (ArrayList<Vertex>) procs;
	}
	
	public Processor[][] criateNetwork(int linhas, int colunas)
	{
		int id = 0;
		
		Processor network[][] = new Processor [linhas][colunas];
		
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{	
				//Cria um processador e insere sua respectiva coordenada.
				//Essa coordenada irá facilitar a implementação do método executeByStep. 
				network[i][j] = new Processor(id, i, j);
				
				//Incrementa o valor do identificador do processador
				id++;
			}
		}
		return network;
	}
	
	/*
	 * Método que incrementa os graus de saída e de entrada dos processos.
	 * Esses graus sevem de comparação para o mapeamento e também para as comunicações.
	 * Na implementação do simulador, esses graus serão a prioridade dos pacotes enviados por determinado processo. 
	 */
	public void calculateDegrees()
	{
		for(Vertex v: procs)
		{
			for(Edge e: comunications)
			{
				if(v.getId() == e.getSource().getId())
				{
					v.incrementsOutdegree();
				}
				
				if(v.getId() == e.getDestination().getId())
				{
					v.incrementsIndegree();
				}
			}
		}
	}
	
	public void printNetwork()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				if(network[i][j].getVertex() == null)
				{
					System.out.print(network[i][j].getId() + "\t");
				}
				else
				{
					System.out.print(network[i][j].getVertex().getName() + "\t");
				}
			}
			
			System.out.println();
		}
	}
	
	public void alocateFirst()
	{
		linhaAlocada = (linhas/2);
		colunaAlocada = (colunas/2);
		network[linhaAlocada][colunaAlocada].setVertex(procs.get(0));
	}
	
	public void calculateMaxDist()
	{
		maxDist = ((linhas * colunas) / procs.size()); 
	}
	
	public void setCoordinateToLinesDown(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada + maxDist), colunaAlocada);
		 
		coord.setDown((linhas-1) - (linhaAlocada + maxDist));
		coord.setTop((linhaAlocada + maxDist));
		coord.setRight(colunas - (colunaAlocada + 1));
		coord.setLeft(colunaAlocada);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToLinesDownLeftSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada + maxDist-1), colunaAlocada-1);

		int la = (linhaAlocada + maxDist-1);
		int ca = colunaAlocada-1;
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToLinesDownRightSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada + maxDist-1), colunaAlocada+1);
	
		int la = (linhaAlocada + maxDist-1);
		int ca = colunaAlocada+1;
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToLinesTop(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada - maxDist), colunaAlocada);
		
		coord.setDown((linhas-1) - (linhaAlocada - maxDist));
		coord.setTop(linhaAlocada - maxDist);
		coord.setRight(colunas - (colunaAlocada + 1));
		coord.setLeft(colunaAlocada);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToLinesTopLeftSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada - maxDist+1), colunaAlocada-1);
		
		int la = (linhaAlocada - maxDist+1);
		int ca = colunaAlocada-1;
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToLinesTopRightSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada - maxDist+1), colunaAlocada+1);
		
		int la = (linhaAlocada - maxDist+1);
		int ca = colunaAlocada+1;
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsRight(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada, (colunaAlocada + maxDist));
		
		coord.setDown((colunas-1) - (colunaAlocada + maxDist));
		coord.setTop(colunaAlocada + maxDist);
		coord.setRight(linhas - (linhaAlocada + 1));
		coord.setLeft(linhaAlocada);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsRightDownSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada+1, (colunaAlocada + maxDist-1));
		
		int la = linhaAlocada+1;
		int ca = (colunaAlocada + maxDist-1);
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsRightTopSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada-1, (colunaAlocada + maxDist-1));
		
		int la = linhaAlocada-1;
		int ca = (colunaAlocada + maxDist-1);
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsLeft(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada, (colunaAlocada - maxDist));
		
		coord.setDown((colunas-1) - (colunaAlocada - maxDist));
		coord.setTop(colunaAlocada - maxDist);
		coord.setRight(linhas - (linhaAlocada + 1));
		coord.setLeft(linhaAlocada);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsLeftTopSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada-1, (colunaAlocada - maxDist+1));
		
		int la = linhaAlocada-1;
		int ca = (colunaAlocada - maxDist+1);
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsLeftDownSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada+1, (colunaAlocada - maxDist+1));
		
		int la = linhaAlocada+1;
		int ca = (colunaAlocada - maxDist+1);
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public boolean firstCriterion(ArrayList<Coordinate> coordinates)
	{
		boolean b1 = false;
		boolean b2 = false;
		boolean b3 = false;
		boolean b4 = false;
		
		if((linhaAlocada + maxDist) < linhas)
		{
			if(((linhas - (linhaAlocada + 1)) >= maxDist) && network[linhaAlocada + maxDist][colunaAlocada].getVertex() == null)
			{
				setCoordinateToLinesDown(coordinates);
				b1 = true;
			}
		}
		
		if((linhaAlocada - maxDist) >= 0)
		{
			if((linhaAlocada >= maxDist) && network[linhaAlocada - maxDist][colunaAlocada].getVertex() == null)
			{
				setCoordinateToLinesTop(coordinates);
				b2 = true;
			}
		}
		
		if((colunaAlocada + maxDist) < colunas)
		{
			if(((colunas - (colunaAlocada + 1)) >= maxDist) && network[linhaAlocada][colunaAlocada + maxDist].getVertex() == null)
			{
				setCoordinateToColumnsRight(coordinates);
				b3 = true;
			}
		}
		
		if((colunaAlocada - maxDist) >= 0)
		{
			if((colunaAlocada >= maxDist) && network[linhaAlocada][colunaAlocada - maxDist].getVertex() == null)
			{
				setCoordinateToColumnsLeft(coordinates);
				b4 = true;
			}
		}
		
		if(b1 || b2 || b3 || b4)
		{
			return true;
		}
		
		return false;

	}
	
	public boolean secondCriterion(ArrayList<Coordinate> coordinates)
	{
		boolean b1 = false;
		boolean b2 = false;
		boolean b3 = false;
		boolean b4 = false;
		boolean b5 = false;
		boolean b6 = false;
		boolean b7 = false;
		boolean b8 = false;
		
		if(((linhaAlocada + maxDist-1) < linhas) && colunaAlocada > 0)
		{
			if(((linhas - (linhaAlocada + 1)) >= maxDist-1) && network[linhaAlocada + maxDist-1][colunaAlocada-1].getVertex() == null)
			{
				setCoordinateToLinesDownLeftSecond(coordinates);
				b1 = true;
			}
		}
		
		if(((linhaAlocada + maxDist-1) < linhas) && colunaAlocada < colunas-1)
		{
			if(((linhas - (linhaAlocada + 1)) >= maxDist-1) && network[linhaAlocada + maxDist-1][colunaAlocada+1].getVertex() == null)
			{
				setCoordinateToLinesDownRightSecond(coordinates);
				b2 = true;
			}
		}
		
		if((linhaAlocada - maxDist+1) >= 0 && (colunaAlocada > 0))
		{
			if((linhaAlocada >= maxDist-1) && network[linhaAlocada - maxDist+1][colunaAlocada-1].getVertex() == null)
			{
				setCoordinateToLinesTopLeftSecond(coordinates);
				b3 = true;
			}
		}
		
		if((linhaAlocada - maxDist+1) >= 0 && (colunaAlocada < colunas-1))
		{
			if((linhaAlocada >= maxDist-1) && network[linhaAlocada - maxDist+1][colunaAlocada+1].getVertex() == null)
			{
				setCoordinateToLinesTopRightSecond(coordinates);
				b4 = true;
			}
		}
		
		if((colunaAlocada + maxDist-1) < colunas && (linhaAlocada > 0))
		{
			if(((colunas - (colunaAlocada + 1)) >= maxDist-1) && network[linhaAlocada-1][colunaAlocada + maxDist-1].getVertex() == null)
			{
				setCoordinateToColumnsRightTopSecond(coordinates);
				b5 = true;
			}
		}
		
		if((colunaAlocada + maxDist-1) < colunas && (linhaAlocada < linhas-1))
		{
			if(((colunas - (colunaAlocada + 1)) >= maxDist-1) && network[linhaAlocada+1][colunaAlocada + maxDist-1].getVertex() == null)
			{
				setCoordinateToColumnsRightDownSecond(coordinates);
				b6 = true;
			}
		}
		
		if((colunaAlocada - maxDist+1) >= 0 && (linhaAlocada > 0))
		{
			if((colunaAlocada >= maxDist-1) && network[linhaAlocada-1][colunaAlocada - maxDist+1].getVertex() == null)
			{
				setCoordinateToColumnsLeftTopSecond(coordinates);
				b7 = true;
			}
		}
		
		if((colunaAlocada - maxDist+1) >= 0 && (linhaAlocada < linhas-1))
		{
			if((colunaAlocada >= maxDist-1) && network[linhaAlocada+1][colunaAlocada - maxDist+1].getVertex() == null)
			{
				setCoordinateToColumnsLeftDownSecond(coordinates);
				b8 = true;
			}
		}
		
		if(b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean alocateNext(int index)
	{
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		
		while(maxDist > 0)
		{
			if(firstCriterion(coordinates))
			{
				Collections.sort(coordinates);
				
				linhaAlocada = coordinates.get(0).getLine();
				colunaAlocada = coordinates.get(0).getColumn();
				network[linhaAlocada][colunaAlocada].setVertex(procs.get(index));
				return true;
			}
			else if(secondCriterion(coordinates))
			{
				Collections.sort(coordinates);
				
				linhaAlocada = coordinates.get(0).getLine();
				colunaAlocada = coordinates.get(0).getColumn();
				network[linhaAlocada][colunaAlocada].setVertex(procs.get(index));
				return true;
			}
			else
			{
				maxDist--;
			}
		}
		return false;
	}
	
	public ArrayList<Edge> getComunications() 
	{
		return comunications;
	}

	public void setComunications(ArrayList<Edge> comunications)
	{
		this.comunications = comunications;
	}

	public ArrayList<Vertex> getProcs() 
	{
		return procs;
	}

	public void setProcs(ArrayList<Vertex> procs) 
	{
		this.procs = procs;
	}

	public Processor[][] getNetwork() 
	{
		return network;
	}

	public void setNetwork(Processor[][] network) 
	{
		this.network = network;
	}

	public int getLinhas() 
	{
		return linhas;
	}

	public void setLinhas(int linhas)
	{
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas)
	{
		this.colunas = colunas;
	}

	public int getMaxDist() 
	{
		return maxDist;
	}

	public void setMaxDist(int maxDist)
	{
		this.maxDist = maxDist;
	}

	public int getLinhaAlocada() 
	{
		return linhaAlocada;
	}

	public void setLinhaAlocada(int linhaAlocada)
	{
		this.linhaAlocada = linhaAlocada;
	}

	public int getColunaAlocada()
	{
		return colunaAlocada;
	}

	public void setColunaAlocada(int colunaAlocada) 
	{
		this.colunaAlocada = colunaAlocada;
	}
}
