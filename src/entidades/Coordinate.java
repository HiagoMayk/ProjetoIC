package entidades;

public class Coordinate implements Comparable<Coordinate>
{
	private int line;
	private int column;
	
	// As variáveis abaixo contém as distancias para as bordas da rede
	// o objetivo comparar esses valores para identificar o processador mais central
	private int down;
	private int top;
	private int right;
	private int left;
	
	public Coordinate(int line, int column)
	{
		this.line = line;
		this.column = column;
	}
	
	/*
	 * Calcula o quão central está a coordenada baseada na soma da diferença entre top e down e entre right e left
	 */
	public int differenceCalculator()
	{
		int topDown;
		int rightLeft;
		
		if(top > down)
		{
			topDown = top - down;
		}
		else
		{
			topDown = down - top;
		}
		
		if(right > left)
		{
			rightLeft = right - left; 
		}
		else
		{
			rightLeft = left - right;
		}
		
		return topDown + rightLeft;
	}
	
	public int compareTo(Coordinate otherCoordinate)
	{
        if(this.differenceCalculator() < otherCoordinate.differenceCalculator())
        {
            return -1;
        }
        
        if(this.differenceCalculator() > otherCoordinate.differenceCalculator()) 
        {
            return 1;
        }
        return 0;
    }
	
	public void setDown(int down)
	{
		this.down = down;
	}
	
	public void setTop(int top)
	{
		this.top = top;
	}
	
	public void setRight(int right)
	{
		this.right = right;
	}
	
	public void setLeft(int left)
	{
		this.left = left;
	}
	
	public int getLine()
	{
		return line;
	}
	
	public int getColumn()
	{
		return column;
	}
}
