
public class Processor 
{
	private int id;
	private Vertex vertex;
	
	public Processor(int id)
	{
		this.id = id;
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
}
