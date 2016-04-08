import java.util.ArrayList;
import java.util.Scanner;

import entidades.Acumulator;
import entidades.Edge;
import entidades.Enlace;
import entidades.Graph;
import entidades.Processor;
import entidades.Vertex;
import roteamentos.RoteamentoXY;
import roteamentos.RoteamentoXY_YX;
import inputOuput.Entrada;
import mapeamentos.PropostoV1;

public class Calculator
{
	public static void main(String args[]) throws CloneNotSupportedException
	{		
		  Entrada entrada = new Entrada();
		  
		  System.out.println("Digite o grafo de entrada: ");
		  System.out.println();
		  Graph grafo = entrada.lerGrafo();
		 
		  /*
		  System.out.println();
		  System.out.println("Digite o mapeamento: ");
		  System.out.println();
		  Processors mapeamento[][] = entrada.lerMapeamento(grafo.getVertexes());
		  */
		  
		  System.out.println();
		  System.out.println("Tarefas: ");
		  for(Vertex v : grafo.getVertexes())
		  {
			  System.out.println(v.getName());
		  }
		  
		  System.out.println();
		  System.out.println("Comunicações: ");
		  
		  for(Edge e : grafo.getEdges())
		  {
			  System.out.println(e.getSource().getName() + "\t" + "-" + "\t" + e.getDestination().getName() + "\t" + "->" + "\t" + e.getWeight());
		  }
		  
		  ArrayList<Edge> copyEdgers = new ArrayList<Edge>();
		  copyEdgers = grafo.copyEdgers(grafo.getEdges());
		  
		  //-------------- Parte do algoritmo PropostoHM, para usar a entrada por console é só
		  // descomentar o código acima e comentar esse. 
		  PropostoV1 hm = new PropostoV1(grafo.getVertexes(), grafo.getEdges());
		  
		  System.out.println("Digite a quantidade de linhas e colunas da rede: ");
		  Scanner sc = new Scanner(System.in);
		  int lin = sc.nextInt();
		  int col = sc.nextInt();
		  
		  if(lin * col >= grafo.getVertexes().size())
		  {
			  Processor mapeamento[][] = hm.execute(lin, col);
			  //--------------
			  
			  grafo.setEdges(copyEdgers);
			  
			  System.out.println();
			  System.out.println("Mapeamento: ");
			 	  
			  //Imprime rede
			  for(int i = 0; i < lin; i++)
			  {
				  for(int j = 0; j < col; j++)
				  {
					  if(mapeamento[i][j].getVertex() != null)
					  {
						  System.out.print(mapeamento[i][j].getVertex().getName() + " \t");
					  }
					  else
					  {
						  System.out.print(mapeamento[i][j].getId() + "\t");
					  }
				  }
				  System.out.println();
			  }
			  
			  System.out.println();
			  System.out.println("ALGORITMO XY: ");
			  RoteamentoXY r = new RoteamentoXY(grafo, mapeamento, lin, col);
			  r.executeFull();
			  r.printResult();
			  
			  grafo.zerarHops();
			  grafo.zerarEnlaces();
			  
			  System.out.println();
			  System.out.println("ALGORITMO XY-YX:");
			  RoteamentoXY_YX c = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
			  c.executeFull();
			  c.printResult();
		  }
		  else
		  {
			  System.out.println("Número de processos maior que o de processadores!");
		  }
		  
	}
}