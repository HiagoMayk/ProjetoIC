
public class Enlace 
{
	private int source;
	private int destination;
	private int acessos;
	
	public Enlace(int source, int destination)
	{
		this.source = source;
		this.destination = destination;
	}
	
	public int getSource()
	{
		return source;
	}
		
	public int getDestination()
	{
		return destination;
	}
	
	public void incrementaAcessos()
	{
		acessos++;
	}
	
	public int getAcessos()
	{
		return acessos;
	}
}
