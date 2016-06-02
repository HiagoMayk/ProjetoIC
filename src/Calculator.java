import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import entidades.Edge;
import entidades.Graph;
import entidades.Processor;
import entidades.Vertex;
import roteamentos.RoteamentoXY;
import roteamentos.RoteamentoXY_YX;
import inputOuput.Entrada;
import main.java.com.rits.cloning.Cloner;
import mapeamentos.PropostoV1;
import mapeamentos.PropostoV2;

public class Calculator
{	
	public Entrada entrada;
	public Graph grafo;
	public Processor mapeamento[][];
	public PropostoV1 hmv1;
	public PropostoV2 hmv2;
	
	public Calculator()
	{
		entrada = new Entrada();
		grafo = new Graph();
	}
	
	public void init()
	{
		Boolean flag = true;
		while(flag)
		{	
			Scanner sc = new Scanner(System.in);
			ArrayList<Edge> copyEdgers;
			System.out.println("========================================");
			System.out.println("1 \t \t Mapeamento por console");
			System.out.println("2 \t \t Mapeamento padrão V1");
			System.out.println("3 \t \t Mapeamento padrão V2");
			System.out.println("0 \t \t Sair");
			System.out.print(">>>");
			 
			int opcao = sc.nextInt();
			int lin;
			int col;
			 
			switch(opcao)
			{
				case 1: 
					grafo = entrada.lerGrafo();
					 
					//printTasks();
					//printComunications();
					 
					System.out.println("Digite a quantidade de linhas e colunas da rede: ");
					
					lin = sc.nextInt();
					col = sc.nextInt();
					
					copyEdgers = new ArrayList<Edge>();
					copyEdgers = grafo.copyEdgers(grafo.getEdges());
					
					//Instancia o mapeamento proposto
					mapeamento = entrada.lerMapConsole(grafo.getVertexes(), lin, col);
					
					grafo.setEdges(copyEdgers);
					
					//getMapeamento(lin, col, copyEdgers);
					//printMapeamento(lin, col);
					executaRotemento(lin, col);
					
					System.out.print(">>>");
					opcao = sc.nextInt();
					break;
					 
				case 2:
					 grafo = entrada.lerGrafo();
					 
					 //printTasks();
					 //printComunications();
					 
					 //Usado para copiar somente os valores e não a instância dos edges
					 copyEdgers = new ArrayList<Edge>();
					 copyEdgers = grafo.copyEdgers(grafo.getEdges());
					
					 //Instancia o mapeamento proposto
					 hmv1 = new PropostoV1(grafo.getVertexes(), grafo.getEdges());
					  
					 System.out.println("Digite a quantidade de linhas e colunas da rede: ");
					
					 lin = sc.nextInt();
					 col = sc.nextInt();
					 getMapeamentoV1(lin, col, copyEdgers);
					 //printMapeamento(lin, col);
					 executaRotemento(lin, col);
					 
					 System.out.print(">>>");
					 opcao = sc.nextInt();
					 break;
					 
				case 3:
					 grafo = entrada.lerGrafo();
					 
					 //printTasks();
					 //printComunications();
					 
					 //Usado para copiar somente os valores e não a instância dos edges
					 copyEdgers = new ArrayList<Edge>();
					 copyEdgers = grafo.copyEdgers(grafo.getEdges());
					 
					 //Instancia o mapeamento proposto
					 hmv2 = new PropostoV2(grafo.getVertexes(), grafo.getEdges());
					  
					 System.out.println("Digite a quantidade de linhas e colunas da rede: ");
					
					 lin = sc.nextInt();
					 col = sc.nextInt();
					 getMapeamentoV2(lin, col, copyEdgers);
					 //printMapeamento(lin, col);
					 executaRotemento(lin, col);
					 
					 System.out.print(">>>");
					 opcao = sc.nextInt();
					 break;
					 
				case 0:
					System.exit(0);
			}
		}	 	 
	}
	
	public void getMapeamentoV1(int lin, int col, ArrayList<Edge> copyEdgers)
	{
		if(lin * col >= grafo.getVertexes().size())
		{
			  mapeamento = hmv1.execute(lin, col);
			  grafo.setEdges(copyEdgers);
		 }
		 else
		 {
			 System.out.println("Número de processos maior que o de processadores!");
		 }
	}
	
	public void getMapeamentoV2(int lin, int col, ArrayList<Edge> copyEdgers)
	{
		if(lin * col >= grafo.getVertexes().size())
		{
			  mapeamento = hmv2.execute(lin, col);
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
		Cloner cloner=new Cloner();
		int opcao;
		Boolean flag = true;
		ArrayList<Edge> copyEdgers;
		while(flag)
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("========================================");
			System.out.println("1 \t \t Rotemento XY Full");
			System.out.println("2 \t \t Rotemento XY By Step");
			System.out.println("3 \t \t Rotemento XY_YX Full");
			System.out.println("4 \t \t Rotemento XY_YX By Step");
			System.out.println("5 \t \t Rotemento XY e XY_YX By Step");
			System.out.println("6 \t \t Todos");
			System.out.print(">>>");
			 
			opcao = sc.nextInt();
			
			switch(opcao)
			{
				case 1: 
					RoteamentoXY xyFull1 = new RoteamentoXY(grafo, mapeamento, lin, col);
					xyFull1.printRoteadores();
					System.out.println();
					System.out.println("ALGORITMO XY FULL:");
					  
					xyFull1.executeFull();
					xyFull1.printResultFull();
					flag = false;
					break;
			
				case 2:
					//Precisa rodar o full também para pegar o desemprenho o pacote que mais demora no parelelismo também no full 
					RoteamentoXY xyFull2 = new RoteamentoXY(grafo, mapeamento, lin, col);
					//xyFull2.printRoteadores();
			
					xyFull2.executeFull();
					//xyFull2.printResultFull();
					
					//Usado para copiar somente os valores e não a instância dos edges
					copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
					
					grafo.zerarHops();
					grafo.zerarEnlaces();
					
					/*
					for(Edge e : copyEdgers)
					{
						System.out.println(e.getHops());		
					}
					*/
					
					System.out.println();
					System.out.println("ALGORITMO XY BY STEP:");
					RoteamentoXY xyStep = new RoteamentoXY(grafo, mapeamento, lin, col);
					xyStep.executeByStep();				    
					xyStep.printResultByStep(copyEdgers);
					flag = false;
					break;
					
				case 3:
					RoteamentoXY_YX xy_xyFull1 = new RoteamentoXY_YX(grafo, mapeamento, lin, col);

					System.out.println();
					System.out.println("ALGORITMO XY_YX FULL:");
					  
					xy_xyFull1.executeFull();	
					xy_xyFull1.printResultFull();
					flag = false;
					break;
					
				case 4:
					RoteamentoXY_YX xy_xyFull2 = new RoteamentoXY_YX(grafo, mapeamento, lin, col);

					xy_xyFull2.executeFull();
					//xy_xyFull2.printResultFull();
					
					//Usado para copiar somente os valores e não a instância dos edges
					copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
					  
					grafo.zerarHops();
					grafo.zerarEnlaces();
					  
					System.out.println();
					System.out.println("ALGORITMO XY_YX BY STEP:");
					RoteamentoXY_YX xy_xyStep = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
					xy_xyStep.executeByStep();
				    xy_xyStep.printResultByStep(copyEdgers);
				    flag = false;
					break;
					
				case 5:
					
					RoteamentoXY xyFulla = new RoteamentoXY(grafo, mapeamento, lin, col);
					xyFulla.printRoteadores();
					  
					xyFulla.executeFull();
					//xyFulla.printResultFull();
					
					//Usado para copiar somente os valores e não a instância dos edges
					copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
					
					grafo.zerarHops();
					grafo.zerarEnlaces();
					
					//-----------------------------------------------------------------------------------------------------------
					
					System.out.println();
					System.out.println("ALGORITMO XY BY STEP:");
					RoteamentoXY xyStepa = new RoteamentoXY(grafo, mapeamento, lin, col);
					xyStepa.executeByStep();				    
					xyStepa.printResultByStep(copyEdgers);
					
					grafo.zerarHops();
					grafo.zerarEnlaces();
					
					//-----------------------------------------------------------------------------------------------------------
					RoteamentoXY_YX xy_xyFullc = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
					  
					xy_xyFullc.executeFull();	
					//xy_xyFullc.printResultFull();
	
					//-----------------------------------------------------------------------------------------------------------
			
					//Usado para copiar somente os valores e não a instância dos edges
					copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
					  
					grafo.zerarHops();
					grafo.zerarEnlaces();
					  
					System.out.println();
					System.out.println("ALGORITMO XY_YX BY STEP:");
					RoteamentoXY_YX xy_xyStepq = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
					xy_xyStepq.executeByStep();
				    xy_xyStepq.printResultByStep(copyEdgers);
				    flag = false;
					break;
				case 6:
					
					RoteamentoXY xyFulla2 = new RoteamentoXY(grafo, mapeamento, lin, col);
					xyFulla2.printRoteadores();
					System.out.println();
					System.out.println("ALGORITMO XY FULL:");
					  
					xyFulla2.executeFull();
					xyFulla2.printResultFull();
					
					//Usado para copiar somente os valores e não a instância dos edges
					copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
					
					grafo.zerarHops();
					grafo.zerarEnlaces();
					
					//-----------------------------------------------------------------------------------------------------------
					
					System.out.println();
					System.out.println("ALGORITMO XY BY STEP:");
					RoteamentoXY xyStepa3 = new RoteamentoXY(grafo, mapeamento, lin, col);
					xyStepa3.executeByStep();				    
					xyStepa3.printResultByStep(copyEdgers);
					
					grafo.zerarHops();
					grafo.zerarEnlaces();
					
					//-----------------------------------------------------------------------------------------------------------
					RoteamentoXY_YX xy_xyFullcc = new RoteamentoXY_YX(grafo, mapeamento, lin, col);

					System.out.println();
					System.out.println("ALGORITMO XY_YX FULL:");
					  
					xy_xyFullcc.executeFull();	
					xy_xyFullcc.printResultFull();
	
					//-----------------------------------------------------------------------------------------------------------
			
					//Usado para copiar somente os valores e não a instância dos edges
					copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
					  
					grafo.zerarHops();
					grafo.zerarEnlaces();
					  
					System.out.println();
					System.out.println("ALGORITMO XY_YX BY STEP:");
					RoteamentoXY_YX xy_xyStepqq = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
					xy_xyStepqq.executeByStep();
				    xy_xyStepqq.printResultByStep(copyEdgers);
				    flag = false;
					break;
			}
		}
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

	public PropostoV1 getHmV1()
	{
		return hmv1;
	}

	public void setHmV1(PropostoV1 hm)
	{
		this.hmv1 = hm;
	}
	
	public static void main(String args[]) throws CloneNotSupportedException
	{		
		  Calculator c = new Calculator();
		  
		  c.init();
	}
}