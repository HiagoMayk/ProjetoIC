package mapeamentos;
import java.util.Collections;
import java.util.List;

import entidades.Edge;
import entidades.Processor;
import entidades.Vertex;

public class PropostoV1 extends PropostoHM
{
	public PropostoV1(List<Vertex> procs, List<Edge> comunications) {
		super(procs, comunications);
		// TODO Auto-generated constructor stub
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
		
		// Calcula a distancia máxima entre od processos
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
