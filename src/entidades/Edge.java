package entidades;
import java.util.ArrayList;

public class Edge
{
	private final String id;			// Id da aresta
	private final Vertex source;		// Vértice de origem
	private final Vertex destination;	// Vértice de destino
	private final int weight;			// Peso da aresta
	private int hops;					// Quantidades de hops da comunicação
	private ArrayList<Enlace> enlaces;
  
	public Edge(String id, Vertex source, Vertex destination, int weight) 
	{
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.enlaces = new ArrayList<Enlace>();
	}

	public void addEnlace(int s, int d)
	{
		Enlace e = new Enlace(s, d);
		enlaces.add(e);
	}
	
	public ArrayList<Enlace> getEnlaces()
	{
		return enlaces;
	}
	
	public void resetaEnlaces()
	{
		this.enlaces = new ArrayList<Enlace>();
	}
	
	/*
	 * retorna o id da aresta
	 */
	public String getId()
	{
		return id;
	}
	
	/*
	 * retorna o vértice de origem
	 */
	public Vertex getSource() 
	{
		return source;
	}
	
	/*
	 * retorna o vértice de destino
	 */
	public Vertex getDestination()
	{
		return destination;
	}
	
	/*
	 * returna o peso da aresta
	 */
	public int getWeight()
	{
		return weight;
	}
  
	public void incrementaHops()
	{
		this.hops++;
	}
	
	public void setHops(int h)
	{
		this.hops = h;
	}
	
	public int getHops()
	{
		return hops;
	}
	
	/*
	 * retorna uma string contendo o vértice de origem e o de destino
	 */
	@Override
	public String toString() 
	{
		return source + " " + destination;
	}
}