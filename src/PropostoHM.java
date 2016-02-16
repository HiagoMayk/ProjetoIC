import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PropostoHM
{
	private ArrayList<Edge> comunications;
	private ArrayList<Vertex> procs;
	Scanner sc = new Scanner(System.in);
	private int linhas;
	private int colunas;
	
	public PropostoHM(List<Vertex> procs, List<Edge> comunications)
	{
		this.comunications = (ArrayList<Edge>) comunications;
		this.procs = (ArrayList<Vertex>) procs;
	}
	
	public Processors[][] criateNetwork()
	{
		int id = 0;
		
		System.out.println("Digite a quantidade de linhas e colunas da rede: ");
		linhas = sc.nextInt();
		colunas = sc.nextInt();

		Processors network[][] = new Processors [linhas][colunas];
		
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				network[i][j] = new Processors(id);
				id++;
			}
		}
		return network;
	}
	
	public void calculateDegrees()
	{
		for(Vertex v: procs)
		{
			for(Edge e: comunications)
			{
				if(v.getId() == e.getSource().getId())
				{
					v.incrementsOutdegree();
				}
				
				if(v.getId() == e.getDestination().getId())
				{
					v.incrementsIndegree();
				}
			}
		}
	}
	
	public Processors[][] execute()
	{
		Processors network[][] = criateNetwork();
		
		calculateDegrees();
		
		/*
		for(Vertex v: procs)
		{
			System.out.println(v.getName() + " - " + v.getTotalDegree());
		}
		*/
		
		System.out.println("OK");
		return network;
	}
}
