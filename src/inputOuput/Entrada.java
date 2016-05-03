package inputOuput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entidades.Edge;
import entidades.Graph;
import entidades.Processor;
import entidades.Vertex;

public class Entrada
{
	Scanner sc = new Scanner(System.in);  // Variável para entrada de dados
	private int linhasMap;
	private int colunasMap;

	/*
	 * Ler o grafo da aplicação
	 * Retorna o grafo
	 */
	public Graph lerGrafo()
	{	
		List<Vertex> nodes = new ArrayList<Vertex>();
		List<Edge> edges = new ArrayList<Edge>();
		
		System.out.println("Digite o grafo de entrada: ");
		System.out.println();
		 
		int quantVertices = sc.nextInt();
		
		// Inicializa vértices
		for (int i = 0; i < quantVertices; i++)
		{
			Vertex location = new Vertex(i, null);
			nodes.add(location);
		}

		int quantAresta = sc.nextInt();
		
		// Recebe o grafo do terminal
		for (int j = 0; j < quantAresta; j++) 
		{
			Boolean flag1 = false;
			Boolean flag2 = false;
			
			String no1 = sc.next();
			String no2 = sc.next();
			int custo = sc.nextInt();
			
			int ino1 = 0;
			int ino2 = 0;
			
			for(Vertex v : nodes)
			{
				if(v.getName() != null && v.getName().equals(no1))
				{
					flag1 = true;
				}
				
				if(v.getName() != null && v.getName().equals(no2))
				{
					flag2 = true;
				}
			}
			
			if(!flag1)
			{
				int i = 0;
				for(Vertex v : nodes)
				{
					if(v.getName() == null)
					{
						v.setName(no1);
						ino1 = i;
						break;
					}
					i++;
				}
			}
			else
			{
				int i = 0;
				for(Vertex v : nodes)
				{
					if(v.getName().equals(no1))
					{
						ino1 = i;
						break;
					}
					i++;
				}
			}
			
			if(!flag2)
			{
				int i = 0;
				for(Vertex v : nodes)
				{
					if(v.getName() == null)
					{
						v.setName(no2);
						ino2 = i;
						break;
					}
					i++;
				}
			}
			else
			{
				int i = 0;
				for(Vertex v : nodes)
				{
					if(v.getName().equals(no2))
					{
						ino2 = i;
						break;
					}
					i++;
				}
			}
			edges.add(new Edge(("Edge_"+j), nodes.get(ino1), nodes.get(ino2), custo));	
		}

		Graph graph = new Graph(nodes, edges);
		return graph;
	}
	
	/*
	 * Ler o mapeamento dos processadores na rede pelo console
	 * Retorna o mapeamento
	 */
	public Processor[][] lerMapConsole(List<Vertex> nodes, int l, int c)
	{
		int id = 0;
		
		linhasMap = l;
		colunasMap = c;
		Processor network[][] = new Processor [l][c];
		
		for(int i = 0; i < l; i++)
		{
			for(int j = 0; j < c; j++)
			{
				network[i][j] = new Processor(id);
				id++;
			}
		}
		
		System.out.println("Digite o mapeamento desejado:");
		System.out.print(">>>");
		// número do processador onde se quer inserir o processo
		for(int k = 0; k < nodes.size(); k++)
		{
			int local = sc.nextInt();
			String proc = sc.next();
				
			for(int i = 0; i < l; i++)
			{
				for(int j = 0; j < c; j++)
				{
					if(network[i][j].getId() == local)
					{
						for (Vertex v : nodes)
						{
							if(v.getName().equals(proc))
							{
								network[i][j].setVertex(v);
							}
						}
					}
				}
			}	
		}

		return network;
	}
	
	public int getLinhasMap()
	{
		return linhasMap;
	}
	
	public int getColunasMap()
	{
		return colunasMap;
	}
}