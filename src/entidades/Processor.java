package entidades;

public class Processor 
{
	private int id;
	private Vertex vertex;
	private Coordinate coordinate;
	
	public Processor(int id)
	{
		this.id = id;
	}
	
	public Processor(int id, Coordinate coordinate)
	{
		this.id = id;
		this.coordinate = coordinate;
	}
	
	public Processor(int id, int x, int y)
	{
		this.id = id;
		this.coordinate = new Coordinate(x, y); 
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setVertex(Vertex v)
	{
		this.vertex = v;
	}
	
	public Vertex getVertex()
	{
		return vertex;
	}
	
	public Coordinate getCoordinate() 
	{
		return coordinate;
	}
	
	public void setCoordinate(int x, int y)
	{
		this.coordinate = new Coordinate(x, y);
	}
	
	public void setCoordinate(Coordinate coordinate) 
	{
		this.coordinate = coordinate;
	}

	public void setId(int id) 
	{
		this.id = id;
	}
}
