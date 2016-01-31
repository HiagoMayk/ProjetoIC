import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph implements Cloneable
{
	private List<Vertex> vertexes;	// Vértices do grafo
	private List<Edge> edges;		// Arestas do grafo

	public Graph(List<Vertex> vertexes, List<Edge> edges) 
	{
		this.vertexes = vertexes;
		this.edges = edges;
	}
	
	public Graph(List<Vertex> vertexes) 
	{
		this.vertexes = vertexes;
		this.edges = new ArrayList<Edge>();
	}
	
	/*
	 * Insere a lista de vértices
	 */
	public void setVertexes(List<Vertex> vertexes) 
	{
		this.vertexes = vertexes;
	}

	/*
	 * Insere a lista de arestas
	 */
	public void setEdges(List<Edge> edges) 
	{
		this.edges = edges;
	}

	public void zerarHops()
	{
		for(Edge e: edges)
		{
			e.setHops(0);
		}
	}
	
	/*
	 * Retorna a lista de vértices
	 */
	public List<Vertex> getVertexes() 
	{
		return vertexes;
	}

	/*
	 * Retorna a lista de arestas
	 */
	public List<Edge> getEdges() 
	{
		return edges;
	}
}