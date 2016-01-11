import java.util.ArrayList;
import java.util.List;

public class Acumulator
{
	private List<Enlace> enlace; // Armazena o uso de cada enlaces
	
	public Acumulator(int linhas, int colunas)
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
	 * Retorna a lista de enlaces
	 */
	public List<Enlace> getEnlace()
	{
		return enlace;
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
}