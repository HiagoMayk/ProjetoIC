package mapeamentos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entidades.Coordinate;
import entidades.Edge;
import entidades.Processor;
import entidades.Vertex;

public class Sequencial 
{
	protected ArrayList<Edge> comunications;
	protected ArrayList<Vertex> procs;
	protected Processor network[][];
	protected int linhas;
	protected int colunas;
		
	public Sequencial(List<Vertex> procs, List<Edge> comunications)
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
		
	public Processor[][] execute(int linhas, int colunas)
	{
		this.linhas = linhas;
		this.colunas = colunas;
				
		// Cria a rede
		network = criateNetwork(linhas, colunas);
		int index = 0;
		for(int i = 0; i<linhas; i++)
		{
			for(int j = 0; j<colunas; j++)
			{
				if(index < procs.size())
				{
					network[i][j].setVertex(procs.get(index));
					index++;
				}
			}
		}
		//printNetwork();
		return network;
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
	
}
