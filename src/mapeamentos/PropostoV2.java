package mapeamentos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entidades.Coordinate;
import entidades.Edge;
import entidades.Processor;
import entidades.Vertex;

public class PropostoV2 extends PropostoHM
{	
	private Vertex lastProc;
	private ArrayList<Vertex> alocated;
	
	public PropostoV2(List<Vertex> procs, List<Edge> comunications) {
		super(procs, comunications);
		// TODO Auto-generated constructor stub
	}
	
	public void alocateFirst()
	{
		linhaAlocada = (linhas/2);
		colunaAlocada = (colunas/2);
		network[linhaAlocada][colunaAlocada].setVertex(procs.get(0));
		lastProc = procs.get(0);
		procs.get(0).alocatedTrue();
		alocated = new ArrayList<Vertex>();
		alocated.add(lastProc);
	}
	
	public boolean isElementAlocated(Vertex v)
	{
		for(Vertex aux: alocated)
		{
			if(v.getId() == aux.getId())
			{
				return true;
			}
		}
		return false;
	}
	
	public Vertex chooseNext()
	{
		Edge e = null;
		int cost = 0;
		
		for(Edge v : comunications)
		{
			if((lastProc.getId() == v.getDestination().getId()) && !isElementAlocated(v.getSource()) && cost <= v.getWeight())
			{
				e = v;
				cost = v.getWeight();
			}
			
			if((lastProc.getId() == v.getSource().getId()) && !isElementAlocated(v.getDestination()) && cost <= v.getWeight())
			{
				e = v;
				cost = v.getWeight();
			}
		}
		
		if(e == null)
		{
			for(Vertex v: procs)
			{
				if(v.getAlocationStatus() == false)
				{
					return v;
				}
			}
		}
		
		if((lastProc.getId() == e.getDestination().getId()))
		{
			return e.getSource();
		}
		
		if((lastProc.getId() == e.getSource().getId()))
		{
			return e.getDestination();
		}
		
		return null;
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
				
				lastProc = chooseNext();
				lastProc.alocatedTrue();
				network[linhaAlocada][colunaAlocada].setVertex(lastProc);
				alocated.add(lastProc);
				return true;
			}
			else if(secondCriterion(coordinates))
			{
				Collections.sort(coordinates);
				
				linhaAlocada = coordinates.get(0).getLine();
				colunaAlocada = coordinates.get(0).getColumn();
				
				lastProc = chooseNext();
				lastProc.alocatedTrue();
				network[linhaAlocada][colunaAlocada].setVertex(lastProc);
				alocated.add(lastProc);
				return true;
			}
			else
			{
				maxDist--;
			}
		}
		return false;
	}

	public Processor[][] execute(int linhas, int colunas)
	{
		this.linhas = linhas;
		this.colunas = colunas;
				
		// Cria a rede
		network = criateNetwork(linhas, colunas);
		
		int totalProcs = procs.size();
		
		// Calcula os graus de todos os vértices
		calculateDegrees();
		
		// Ordena de forma decrescente de graus totais
		Collections.sort(procs);
		
		for(Vertex v: procs)
		{
			System.out.println(v.getName() + " - " + v.getTotalDegree());
		}
		
		// Calcula a distancia máxima entre os processos
		calculateMaxDist();
		
		// Aloca o primeiro processo no centro
		alocateFirst();
		
		//printNetwork();
		// Aloca os processos restantes aplicando os critérios de alocação
		for(int i = 1; i < procs.size(); i++)
		{
			alocateNext(i);
			printNetwork();
			System.out.println("--------------------------");
		}
		
		//printNetwork();	

		return network;
	}
}
