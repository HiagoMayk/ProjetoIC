package inputOuput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
		
		calculateDegrees(graph);
		
		return graph;
	}
	
	/*
	 * Método que incrementa os graus de saída e de entrada dos processos.
	 * Esses graus sevem de comparação para o mapeamento e também para as comunicações.
	 * Na implementação do simulador, esses graus serão a prioridade dos pacotes enviados por determinado processo. 
	 */
	public void calculateDegrees(Graph graph)
	{
		for(Vertex v: graph.getVertexes())
		{
			for(Edge e: graph.getEdges())
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
		
		//Cria os processos com seus respectivos iIDs
		for(int i = 0; i < l; i++)
		{
			for(int j = 0; j < c; j++)
			{
				network[i][j] = new Processor(id, i, j);
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
								v.setAlocated(true); //Muda status para alocado
							}
						}
					}
				}
			}	
		}

		return network;
	}
	
	/*
	 * Ler o mapeamento dos processadores na rede por arquivo
	 * Ler uma sequencia de papeamentos e aplica as métricas sobre ele dando as médias gerais
	 * Retorna um ArrayList de Mapeamentos de 
	 */
	public void lerMapsArquivo(List<Vertex> nodes, int l, int c)
	{
		//System.out.printf("Informe o nome de arquivo texto:\n");
	    //String nome = sc.nextLine();

	    System.out.printf("\nConteúdo do arquivo texto:\n");
	    try 
	    {
	    	FileReader arq = new FileReader("/home/mayk/Documentos/Workspace/ProjetoIC/InstanciasTCC/pipe.in");
	     	BufferedReader lerArq = new BufferedReader(arq);
	 
	     	String linha = lerArq.readLine(); // lê a primeira linha
	     	// a variável "linha" recebe o valor "null" quando o processo
	     	// de repetição atingir o final do arquivo texto
	     	while (linha != null) 
	     	{
	     		System.out.printf("%s\n", linha);
	 
	     		linha = lerArq.readLine(); // lê da segunda até a última linha
	     	}
	 
	     	arq.close();
	    } 
	    catch (IOException e) 
	    {
	    	System.err.printf("Erro na abertura do arquivo: %s.\n",
	    	e.getMessage());
	    }
	 
	    System.out.println();
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