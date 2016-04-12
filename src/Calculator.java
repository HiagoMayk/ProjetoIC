import java.util.ArrayList;
import java.util.Scanner;

import entidades.Edge;
import entidades.Graph;
import entidades.Processor;
import entidades.Vertex;
import roteamentos.RoteamentoXY;
import roteamentos.RoteamentoXY_YX;
import inputOuput.Entrada;
import mapeamentos.PropostoV1;
import mapeamentos.PropostoV2;

public class Calculator
{	
	public Entrada entrada;
	public Graph grafo;
	public Processor mapeamento[][];
	public PropostoV1 hm;
	
	public Calculator()
	{
		entrada = new Entrada();
		grafo = new Graph();
	}
	
	public void init()
	{
		 grafo = entrada.lerGrafo();
		 
		 //printTasks();
		 //printComunications();
		 
		 //Usado para copiar somente os valores e não a instância dos edges
		 ArrayList<Edge> copyEdgers = new ArrayList<Edge>();
		 copyEdgers = grafo.copyEdgers(grafo.getEdges());
		 
		 //Instancia o mapeamento proposto
		 hm = new PropostoV1(grafo.getVertexes(), grafo.getEdges());
		  
		 System.out.println("Digite a quantidade de linhas e colunas da rede: ");
		 Scanner sc = new Scanner(System.in);
		 int lin = sc.nextInt();
		 int col = sc.nextInt();
		 getMapeamento(lin, col, copyEdgers);
		 //printMapeamento(lin, col);
		 executaRotemento(lin, col);
	}
	
	public void getMapeamento(int lin, int col, ArrayList<Edge> copyEdgers)
	{
		if(lin * col >= grafo.getVertexes().size())
		{
			  mapeamento = hm.execute(lin, col);
			  grafo.setEdges(copyEdgers);
		 }
		 else
		 {
			 System.out.println("Número de processos maior que o de processadores!");
		 }
	}
	
	public void printMapeamento(int lin, int col)
	{
		 System.out.println();
		 System.out.println("Mapeamento: ");
		 	  
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
	}
	
	public void executaRotemento(int lin, int col)
	{
		RoteamentoXY xyFull = new RoteamentoXY(grafo, mapeamento, lin, col);
		xyFull.printRoteadores();
		System.out.println();
		System.out.println("ALGORITMO XY FULL:");
		  
		xyFull.executeFull();
		xyFull.printResultFull();
		  
		grafo.zerarHops();
		grafo.zerarEnlaces();
		  
		System.out.println();
		System.out.println("ALGORITMO XY BY STEP:");
		RoteamentoXY xyStep = new RoteamentoXY(grafo, mapeamento, lin, col);
		xyStep.executeByStep();
		xyStep.printResultByStep();
		
		grafo.zerarHops();
		grafo.zerarEnlaces();
		
		System.out.println();
		
		RoteamentoXY_YX xy_xyFull = new RoteamentoXY_YX(grafo, mapeamento, lin, col);

		System.out.println();
		System.out.println("ALGORITMO XY_YX FULL:");
		  
		xy_xyFull.executeFull();
		xy_xyFull.printResultFull();
		  
		grafo.zerarHops();
		grafo.zerarEnlaces();
		  
		System.out.println();
		System.out.println("ALGORITMO XY_YX BY STEP:");
		RoteamentoXY_YX xy_xyStep = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
		xy_xyStep.executeByStep();
		xy_xyStep.printResultByStep();
	}
	
	public void printTasks()
	{
		System.out.println();
		  System.out.println("Tarefas: ");
		  for(Vertex v : grafo.getVertexes())
		  {
			  System.out.println(v.getName());
		  }
	}
	
	public void printComunications()
	{
		System.out.println();
		  System.out.println("Comunicações: ");
		  
		  for(Edge e : grafo.getEdges())
		  {
			  System.out.println(e.getSource().getName() + "\t" + "-" + "\t" + e.getDestination().getName() + "\t" + "->" + "\t" + e.getWeight());
		  }
	}
	
	public Entrada getEntrada() 
	{
		return entrada;
	}

	public void setEntrada(Entrada entrada) 
	{
		this.entrada = entrada;
	}

	public Graph getGrafo()
	{
		return grafo;
	}

	public void setGrafo(Graph grafo) 
	{
		this.grafo = grafo;
	}

	public Processor[][] getMapeamento()
	{
		return mapeamento;
	}

	public void setMapeamento(Processor[][] mapeamento) 
	{
		this.mapeamento = mapeamento;
	}

	public PropostoV1 getHm()
	{
		return hm;
	}

	public void setHm(PropostoV1 hm)
	{
		this.hm = hm;
	}
	
	public static void main(String args[]) throws CloneNotSupportedException
	{		
		  Calculator c = new Calculator();
		  
		  c.init();
	}
}