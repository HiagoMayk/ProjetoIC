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
	
	public void setCoordinateToLinesDownLeftSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada + maxDist-1), colunaAlocada-1);

		int la = (linhaAlocada + maxDist-1);
		int ca = colunaAlocada-1;
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToLinesDownRightSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada + maxDist-1), colunaAlocada+1);
	
		int la = (linhaAlocada + maxDist-1);
		int ca = colunaAlocada+1;
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
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
	
	public void setCoordinateToLinesTopLeftSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada - maxDist+1), colunaAlocada-1);
		
		int la = (linhaAlocada - maxDist+1);
		int ca = colunaAlocada-1;
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToLinesTopRightSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate((linhaAlocada - maxDist+1), colunaAlocada+1);
		
		int la = (linhaAlocada - maxDist+1);
		int ca = colunaAlocada+1;
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
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
	
	public void setCoordinateToColumnsRightDownSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada+1, (colunaAlocada + maxDist-1));
		
		int la = linhaAlocada+1;
		int ca = (colunaAlocada + maxDist-1);
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsRightTopSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada-1, (colunaAlocada + maxDist-1));
		
		int la = linhaAlocada-1;
		int ca = (colunaAlocada + maxDist-1);
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
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
	
	public void setCoordinateToColumnsLeftTopSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada-1, (colunaAlocada - maxDist+1));
		
		int la = linhaAlocada-1;
		int ca = (colunaAlocada - maxDist+1);
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void setCoordinateToColumnsLeftDownSecond(ArrayList<Coordinate> coordinates)
	{
		Coordinate coord = new Coordinate(linhaAlocada+1, (colunaAlocada - maxDist+1));
		
		int la = linhaAlocada+1;
		int ca = (colunaAlocada - maxDist+1);
				
		coord.setDown(linhas - 1 - la);
		coord.setTop(la);
		coord.setRight(colunas - 1 - ca);
		coord.setLeft(ca);
		
		coordinates.add(coord);
	}
	
	public void firstCriterion(ArrayList<Coordinate> coordinates)
	{
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
	}
	
	public void secondCriterion(ArrayList<Coordinate> coordinates)
	{
		if(((linhaAlocada + maxDist-1) < linhas) && colunaAlocada > 0)
		{
			if(((linhas - (linhaAlocada + 1)) >= maxDist-1) && network[linhaAlocada + maxDist-1][colunaAlocada-1].getVertex() == null)
			{
				setCoordinateToLinesDownLeftSecond(coordinates);
			}
		}
		
		if(((linhaAlocada + maxDist-1) < linhas) && colunaAlocada < colunas-1)
		{
			if(((linhas - (linhaAlocada + 1)) >= maxDist-1) && network[linhaAlocada + maxDist-1][colunaAlocada+1].getVertex() == null)
			{
				setCoordinateToLinesDownRightSecond(coordinates);
			}
		}
		
		if((linhaAlocada - maxDist+1) >= 0 && (colunaAlocada > 0))
		{
			if((linhaAlocada >= maxDist-1) && network[linhaAlocada - maxDist+1][colunaAlocada-1].getVertex() == null)
			{
				setCoordinateToLinesTopLeftSecond(coordinates);
			}
		}
		
		if((linhaAlocada - maxDist+1) >= 0 && (colunaAlocada < colunas-1))
		{
			if((linhaAlocada >= maxDist-1) && network[linhaAlocada - maxDist+1][colunaAlocada+1].getVertex() == null)
			{
				setCoordinateToLinesTopRightSecond(coordinates);
			}
		}
		
		if((colunaAlocada + maxDist-1) < colunas && (linhaAlocada > 0))
		{
			if(((colunas - (colunaAlocada + 1)) >= maxDist-1) && network[linhaAlocada-1][colunaAlocada + maxDist-1].getVertex() == null)
			{
				setCoordinateToColumnsRightTopSecond(coordinates);
			}
		}
		
		if((colunaAlocada + maxDist-1) < colunas && (linhaAlocada < linhas-1))
		{
			if(((colunas - (colunaAlocada + 1)) >= maxDist-1) && network[linhaAlocada+1][colunaAlocada + maxDist-1].getVertex() == null)
			{
				setCoordinateToColumnsRightDownSecond(coordinates);
			}
		}
		
		if((colunaAlocada - maxDist+1) >= 0 && (linhaAlocada > 0))
		{
			if((colunaAlocada >= maxDist-1) && network[linhaAlocada-1][colunaAlocada - maxDist+1].getVertex() == null)
			{
				setCoordinateToColumnsLeftTopSecond(coordinates);
			}
		}
		
		if((colunaAlocada - maxDist+1) >= 0 && (linhaAlocada < linhas-1))
		{
			if((colunaAlocada >= maxDist-1) && network[linhaAlocada+1][colunaAlocada - maxDist+1].getVertex() == null)
			{
				setCoordinateToColumnsLeftDownSecond(coordinates);
			}
		}
	}
	
	public void alocateNext()
	{
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		
		// firstCriterion(coordinates);
		
		// Verificar bug neste método (em algum momento ele não seta o processo no mais central)
		// Obs: Verificar os métodos que ele usa um por um
		secondCriterion(coordinates);
				
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
		
		printNetwork();
		// Aloca os processos restantes aplicando os critérios de alocação
		for(int i = 0; i < 11; i++)
		{
			alocateNext();
			printNetwork();
			System.out.println("--------------------------");
		}
		
		//printNetwork();	

		return network;
	}
}
