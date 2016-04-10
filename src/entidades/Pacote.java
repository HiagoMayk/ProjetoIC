package entidades;

import java.util.ArrayList;

/**
 * @author mayk
 * Representaçao dos pacotes
 */
public class Pacote 
{
	private int priority;
	private Coordinate currentCoordinate;
	private Processor source;
	private Processor destination;
	private int hops;
	private ArrayList<Enlace> enlaces;

	public Pacote(int priority, Processor source, Processor destination, int x, int y)
	{
		this.priority = priority;
		this.source = source;
		this.destination = destination;
		this.currentCoordinate = new Coordinate(x, y);
		this.enlaces = new ArrayList<Enlace>();
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

	public void setCurrentCoordinate(int x, int y) 
	{
		this.currentCoordinate = new Coordinate(x, y);
	}
	
	public void setCurrentCoordinate(Coordinate coordinate)
	{
		this.currentCoordinate = coordinate;
	}
	
	public void setCurrentCoordinateColumn(int y) 
	{
		this.currentCoordinate.setColumn(y);
	}
	
	public void setCurrentCoordinateLine(int x) 
	{
		this.currentCoordinate.setLine(x);
	}
	
	public Coordinate getCoordinateSource() 
	{
		return this.source.getCoordinate();
	}
	
	public Coordinate getCoordinateDestination() 
	{
		return this.destination.getCoordinate();
	}
	
	public int getHops() 
	{
		return hops;
	}

	public void setHops(int hops) 
	{
		this.hops = hops;
	}

	public Coordinate getCurrentCoordinate() 
	{
		return currentCoordinate;
	}
	
	public void addEnlace(int x, int y) 
	{
		this.enlaces.add(new Enlace(x, y));
	}
	
	public ArrayList<Enlace> getEnlaces() 
	{
		return enlaces;
	}

	public void setEnlaces(ArrayList<Enlace> enlaces) 
	{
		this.enlaces = enlaces;
	}

}
