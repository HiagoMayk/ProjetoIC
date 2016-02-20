import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PropostoHM
{
	private ArrayList<Edge> comunications;
	private ArrayList<Vertex> procs;
	private Scanner sc = new Scanner(System.in);
	private Processors network[][];
	private int linhas;
	private int colunas;
	private int maxDist;
	
	// As variaveis seguintes guardam as ultimas coordenadas de alocação de tarefas 
	int linhaAlocada;
	int colunaAlocada;
	
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
	
	public void printNetwork()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				if(network[i][j].getVertex() == null)
				{
					System.out.print(network[i][j].getId() + "\t");
				}
				else
				{
					System.out.print(network[i][j].getVertex().getName() + "\t");
				}
			}
			
			System.out.println();
		}
	}
	
	public void alocateFirst()
	{
		linhaAlocada = (linhas/2);
		colunaAlocada = (colunas/2);
		network[linhaAlocada][colunaAlocada].setVertex(procs.remove(0));
	}
	
	public void calculateMaxDist()
	{
		maxDist = ((linhas * colunas) / procs.size()) - 1; 
	}
	
	public void setCoordinateToLinesDown(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada + maxDist), colunaAlocada);
		
		coord.setDown((linhas-1) - (linhaAlocada + maxDist));
		coord.setTop((linhaAlocada + maxDist));
		coord.setRight(colunas - (colunaAlocada + 1));
		coord.setLeft(colunaAlocada);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToLinesTop(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada - maxDist), colunaAlocada);
		
		coord.setDown((linhas-1) - (linhaAlocada - maxDist));
		coord.setTop(linhaAlocada - maxDist);
		coord.setRight(colunas - (colunaAlocada + 1));
		coord.setLeft(colunaAlocada);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsRight(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada, (colunaAlocada + maxDist));
		
		coord.setDown((colunas-1) - (colunaAlocada + maxDist));
		coord.setTop(colunaAlocada + maxDist);
		coord.setRight(linhas - (linhaAlocada + 1));
		coord.setLeft(linhaAlocada);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsLeft(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada, (colunaAlocada - maxDist));
		
		coord.setDown((colunas-1) - (colunaAlocada - maxDist));
		coord.setTop(colunaAlocada - maxDist);
		coord.setRight(linhas - (linhaAlocada + 1));
		coord.setLeft(linhaAlocada);
		
		coordinates.add(coord);
	}
	
	public void alocateNext()
	{
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		
		if((linhaAlocada + maxDist) < linhas)
		{
			if(((linhas - (linhaAlocada + 1)) >= maxDist) && network[linhaAlocada + maxDist][colunaAlocada].getVertex() == null)
			{
				setCoordinateToLinesDown(coordinates);
			}
		}
		
		if((linhaAlocada - maxDist) >= 0)
		{
			if((linhaAlocada >= maxDist) && network[linhaAlocada - maxDist][colunaAlocada].getVertex() == null)
			{
				setCoordinateToLinesTop(coordinates);
			}
		
		}
		
		if((colunaAlocada + maxDist) < colunas)
		{
			if(((colunas - (colunaAlocada + 1)) >= maxDist) && network[linhaAlocada][colunaAlocada + maxDist].getVertex() == null)
			{
				setCoordinateToColumnsRight(coordinates);
			}
		}
		
		if((colunaAlocada - maxDist) >= 0)
		{
			if((colunaAlocada >= maxDist) && network[linhaAlocada][colunaAlocada - maxDist].getVertex() == null)
			{
				setCoordinateToColumnsLeft(coordinates);
			}
		}
			
		Collections.sort(coordinates);
		
		linhaAlocada = coordinates.get(0).getLine();
		colunaAlocada = coordinates.get(0).getColumn();
		network[linhaAlocada][colunaAlocada].setVertex(procs.remove(0));
	}
	
	public Processors[][] execute()
	{
		// Cria a rede
		network = criateNetwork();
		
		int totalProcs = procs.size();
		
		// Calcula os graus de todos os vértices
		calculateDegrees();
		
		// Ordena de forma decrescente de graus totais
		Collections.sort(procs);
		
		for(Vertex v: procs)
		{
			System.out.println(v.getName() + " - " + v.getTotalDegree());
		}
		
		// Calcula a distancia máxima entre od processos
		calculateMaxDist();
		
		// Aloca o primeiro processo no centro
		alocateFirst();
		
		// Aloca os processos restantes aplicando os critérios de alocação
		for(int i = 0; i < 8; i++)
		{
			alocateNext();
		}
		
		printNetwork();	

		return network;
	}
}
