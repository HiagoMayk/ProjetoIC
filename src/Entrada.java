import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Entrada
{
	Scanner sc = new Scanner(System.in);  // Variável para entrada de dados

	/*
	 * Ler o grafo da aplicação
	 * Retorna o grafo
	 */
	public Graph lerGrafo()
	{	
		//Map<String, Integer> dic = new HashMap<String, Integer>();
		List<Vertex> nodes = new ArrayList<Vertex>();
		List<Edge> edges = new ArrayList<Edge>();
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
					//System.out.print(v.getName()+"|");
				}
				
				if(v.getName() != null && v.getName().equals(no2))
				{
					flag2 = true;
					//System.out.print(v.getName());
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
	 * Ler o mapeamento dos processadores na rede
	 * Retorna o mapeamento
	 */
	public Processors[][] lerMapeamento(List<Vertex> nodes)
	{
		int id = 0;
		int l = sc.nextInt();
		int c = sc.nextInt();
		Processors network[][] = new Processors [l][c];
		
		for(int i = 0; i < l; i++)
		{
			for(int j = 0; j < c; j++)
			{
				network[i][j] = new Processors(id);
				id++;
			}
		}
		
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
}