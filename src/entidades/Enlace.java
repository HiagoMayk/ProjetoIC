package entidades;

public class Enlace
{
	private int source;
	private int destination;
	private int acessos;
	private int flits;
	private Pacote pacote;
	
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
	
	public void incrementaFlits(int flits)
	{
		this.flits += flits;
	}
	
	public int getAcessos()
	{
		return acessos;
	}
	
	public Pacote getPacote()
	{
		return pacote;
	}

	public void setPacote(Pacote pacote)
	{
		this.pacote = pacote;
	}
	
	public void deletePacote()
	{
		this.pacote = null;
	}
	
	public void setFlits(int flits)
	{
		this.flits = flits;
	}
	
	public int getFlits()
	{
		return this.flits;
	}
}