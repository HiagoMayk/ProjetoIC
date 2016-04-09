package entidades;

/**
 * @author mayk
 * Representaçao dos pacotes
 */
public class Pacote 
{
	private int priority;
	private Coordinate coordinate;
	private Processor source;
	private Processor destination;
	
	public Pacote(int priority, Processor source, Processor destination, int x, int y)
	{
		this.priority = priority;
		this.source = source;
		this.destination = destination;
		this.coordinate = new Coordinate(x, y);
	}

	public int getPriority() 
	{
		return priority;
	}

	public void setPriority(int priority) 
	{
		this.priority = priority;
	}

	public Processor getSource() 
	{
		return source;
	}

	public void setSource(Processor source) 
	{
		this.source = source;
	}

	public Processor getDestination() 
	{
		return destination;
	}

	public void setDestination(Processor destination)
	{
		this.destination = destination;
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
	
	public Coordinate getCoordinateSource() 
	{
		return this.source.getCoordinate();
	}
	
	public Coordinate getCoordinateDestination() 
	{
		return this.destination.getCoordinate();
	}
	
}
