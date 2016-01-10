import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
		List<Vertex> nodes = new ArrayList<Vertex>();
		List<Edge> edges = new ArrayList<Edge>();
		int quantVertices = sc.nextInt();
		
		// Inicializa vértices
		for (int i = 0; i < quantVertices; i++)
		{
			Vertex location = new Vertex(i, "Node_" + i);
			nodes.add(location);		
		}

		int quantAresta = sc.nextInt();
		
		// Recebe o grafo do terminal
		for (int j = 0; j < quantAresta; j++) 
		{
			int no1 = sc.nextInt();
			int no2 = sc.nextInt();
			int custo = sc.nextInt();
			edges.add(new Edge(("Edge_"+j), nodes.get(no1), nodes.get(no2), custo));
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
		
		for (Vertex v : nodes)
		{
			// número do processador onde se quer inserir o processo
			int local = sc.nextInt();
			
			for(int i = 0; i < l; i++)
			{
				for(int j = 0; j < c; j++)
				{
					if(network[i][j].getId() == local)
					{
						network[i][j].setVertex(v);
					}
				}
			}
		}

		return network;
	}
}