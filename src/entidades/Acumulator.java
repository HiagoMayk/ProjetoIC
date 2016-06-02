package entidades;
import java.util.ArrayList;
import java.util.List;

public class Acumulator
{
	private List<Enlace> enlace; // Armazena o uso de cada enlaces

	public Acumulator()
	{
		this.enlace = new ArrayList<Enlace>();
	}

	/*
	 * Incrementa o valor de acesso e um determinado enlace
	 */
	public void incrementaEnlace(int i, int j)
	{
		boolean flag = false;
		
		for(Enlace e : enlace)
		{
			if((e.getSource() == i && e.getDestination() == j) || (e.getSource() == j && e.getDestination() == i))
			{
				e.incrementaAcessos();
				flag = true;
			}
		}
		
		if(flag == false)
		{
			Enlace e = new Enlace(i, j);
			e.incrementaAcessos();
			enlace.add(e);
		}
	}
	
	/*
	 * Incrementa o a quantidade de flits de um determinado enlace
	 */
	public void incrementaFlits(int i, int j, int flits)
	{
		boolean flag = false;
		
		for(Enlace e : enlace)
		{
			if((e.getSource() == i && e.getDestination() == j) || (e.getSource() == j && e.getDestination() == i))
			{
				e.incrementaFlits(flits);
				flag = true;
			}
		}
		
		if(flag == false)
		{
			Enlace e = new Enlace(i, j);
			e.incrementaFlits(flits);
			enlace.add(e);
		}
	}
	
	public Enlace returnEnlace(int i, int j)
	{
		for(Enlace e : enlace)
		{
			if((e.getSource() == i && e.getDestination() == j) || (e.getSource() == j && e.getDestination() == i))
			{
				return e;
			}
		}
		return null;
	}
	
	/*
	 * Retorna a lista de enlaces
	 */
	public List<Enlace> getEnlace()
	{
		return enlace;
	}
	
	public void setEnlace(int i, int j)
	{
		Enlace e = new Enlace(i, j);
		enlace.add(e);
	}
	
	/*
	 * Imprime todos os enlaces e a quantidade 
	 * de acesso a cada um deles
	 */
	public void printAcumulator()
	{
		for(Enlace e : enlace)
		{
			System.out.println(e.getSource() + " - " + e.getDestination() + ": " + e.getAcessos());
		}
	}

	public void setEnlace(List<Enlace> enlace)
	{
		this.enlace = enlace;
	}
}