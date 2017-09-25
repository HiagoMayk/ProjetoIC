import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import entidades.Edge;
import entidades.Graph;
import entidades.Processor;
import entidades.Vertex;
import roteamentos.Roteamento;
import roteamentos.RoteamentoXY;
import roteamentos.RoteamentoXY_YX;
import inputOuput.Entrada;
import main.java.com.rits.cloning.Cloner;
import mapeamentos.PropostoV1;
import mapeamentos.PropostoV2;
import mapeamentos.Sequencial;


public class Calculator
{	
	public Entrada entrada;
	public Graph grafo;
	public ArrayList<Processor[][]> mapeamentos;
	public PropostoV1 hmv1;
	public PropostoV2 hmv2;
	public Sequencial seq;
	
	public Calculator()
	{
		entrada = new Entrada();
		grafo = new Graph();
		mapeamentos = new ArrayList<Processor[][]>();
	}
	
	// Implementado para gerar entradas customizadas
	public void myCode() throws IOException
	{
		Cloner cloner = new Cloner();
		mapeamentos = new ArrayList<Processor[][]>();
		ArrayList<Edge> copyEdgers;
		Scanner sc = new Scanner(System.in);
		grafo = entrada.lerGrafo();
		
		String app = "r10";
		
		FileWriter arq = new FileWriter("/home/hiago/Documentos/SiNoC/Mapeamentos_variacoes/Results_CastNet/results.out");
		PrintWriter gravarArq = new PrintWriter(arq);
		
		System.out.println("Digite a quantidade de linhas e colunas da rede: ");
		
		//Instancia o mapeamento Sequencial
		 //seq = new Sequencial(grafo.getVertexes(), grafo.getEdges());
		
		int lin = sc.nextInt();
		int col = sc.nextInt();
		
		copyEdgers = new ArrayList<Edge>();
		copyEdgers = grafo.copyEdgers(grafo.getEdges());
		
		//Instancia o mapeamento proposto
		//getMapeamentoSequencial(lin, col, copyEdgers);
		mapeamentos = entrada.lerMapsArquivo(grafo.getVertexes(), lin, col, "/home/hiago/Documentos/SiNoC/Mapeamentos_variacoes/Results_CastNet/"+app+".txt");
		
		grafo.setEdges(copyEdgers);
		
		//getMapeamento(lin, col, copyEdgers);
		//printMapeamento(lin, col);
		//executaRotemento(lin, col);
		
		//Usado para copiar somente os valores e não a instância dos edges
		copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
		
		//-----------------------------------------------------------------------------------------------------------
		
		//System.out.println();
		//System.out.println("ALGORITMO XY BY STEP:");
		//gravarArq.printf("\n");
		//gravarArq.printf("ALGORITMO XY BY STEP:");
		//gravarArq.printf("\n");
		for(Processor[][] mapeamento: mapeamentos)
		{
			RoteamentoXY xyStepa = new RoteamentoXY(grafo, mapeamento, lin, col);
			
			xyStepa.executeByStep();	
			//System.out.println("HOP");
			//System.out.println("hop");
			xyStepa.ResultByStep(copyEdgers);
			//xyStepa.printResultByStep();
			//showResults(xyStepa, copyEdgers);
			System.out.print(xyStepa.getResult().getLatencia_pct_maior_latencia() + " ");
			//gravarArq.printf(""+xyStepa.getResult().getLatencia_pct_maior_latencia() + " ");
			//gravarArq.printf("\n");
			
		}
		
		grafo.zerarHops();
		grafo.zerarEnlaces();
		
		//-----------------------------------------------------------------------------------------------------------

		//Usado para copiar somente os valores e não a instância dos edges
		copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
		  
		grafo.zerarHops();
		grafo.zerarEnlaces();
		  
		//System.out.println();
		//System.out.println("ALGORITMO XY_YX BY STEP:");
		
		//gravarArq.printf("\n");
		//gravarArq.printf("ALGORITMO XY_YX BY STEP:");
		//gravarArq.printf("\n");
		for(Processor[][] mapeamento: mapeamentos)
		{
			RoteamentoXY_YX xy_xyStepq = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
			xy_xyStepq.executeByStep();
		
			xy_xyStepq.ResultByStep(copyEdgers);
			//xy_xyStepq.printResultByStep();					    
			//showResults(xy_xyStepq, copyEdgers);
			System.out.println(xy_xyStepq.getResult().getLatencia_pct_maior_latencia());
			//gravarArq.printf(""+xy_xyStepq.getResult().getLatencia_pct_maior_latencia());
			//gravarArq.printf("\n");
		}
		
		//arq.close();
	}
	
	public void init()
	{
		Boolean flag = true;
		while(flag)
		{	
			mapeamentos = new ArrayList<Processor[][]>();
			Scanner sc = new Scanner(System.in);
			ArrayList<Edge> copyEdgers;
			System.out.println("=================    SiNoC    =================");
			System.out.println("1 \t \t Mapeamento por console;");
			System.out.println("2 \t \t Mapeamento V1_Decrecente;");
			System.out.println("3 \t \t Mapeamento V1_Crescente;");
			System.out.println("4 \t \t Mapeamento Sequencial;");
			System.out.println("5 \t \t Mapeamento por arquivo;");
			System.out.println("0 \t \t Sair.");
			System.out.print(">>>");
			 
			int opcao = sc.nextInt();
			int lin;
			int col;
			 
			switch(opcao)
			{
				case 1: // Console
					grafo = entrada.lerGrafo();
					 
					//printTasks();
					//printComunications();
					
					System.out.println("Digite a quantidade de linhas e colunas da rede: ");
					
					lin = sc.nextInt();
					col = sc.nextInt();
					
					copyEdgers = new ArrayList<Edge>();
					copyEdgers = grafo.copyEdgers(grafo.getEdges());
					
					//Instancia o mapeamento proposto
					mapeamentos.add(entrada.lerMapConsole(grafo.getVertexes(), lin, col));
					
					grafo.setEdges(copyEdgers);
					
					//getMapeamento(lin, col, copyEdgers);
					//printMapeamento(lin, col);
					executaRotemento(lin, col);
					
					System.out.print(">>>");
					opcao = sc.nextInt();
					break;
					 
				case 2: // Decrescente
					 grafo = entrada.lerGrafo();
					 
					 printTasks();
					 printComunications();
					
					 //Usado para copiar somente os valores e não a instância dos edges
					 copyEdgers = new ArrayList<Edge>();
					 copyEdgers = grafo.copyEdgers(grafo.getEdges());
					
					 //Instancia o mapeamento proposto
					 hmv1 = new PropostoV1(grafo.getVertexes(), grafo.getEdges());
					  
					 System.out.println("Digite a quantidade de linhas e colunas da rede: ");
					
					 lin = sc.nextInt();
					 col = sc.nextInt();
					 getMapeamentoV1(lin, col, copyEdgers);
					 
					 printMapeamento(lin, col);
					 
					 executaRotemento(lin, col);
					 
					 System.out.print(">>>");
					 opcao = sc.nextInt();
					 break;
					 
				case 3: // Crescente
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
					 
				case 4: // Sequencial
					grafo = entrada.lerGrafo();
					 
					 //printTasks();
					 //printComunications();
					 
					 //Usado para copiar somente os valores e não a instância dos edges
					 copyEdgers = new ArrayList<Edge>();
					 copyEdgers = grafo.copyEdgers(grafo.getEdges());
					 
					 //Instancia o mapeamento Sequencial
					 seq = new Sequencial(grafo.getVertexes(), grafo.getEdges());
					  
					 System.out.println("Digite a quantidade de linhas e colunas da rede: ");
					
					 lin = sc.nextInt();
					 col = sc.nextInt();
					 getMapeamentoSequencial(lin, col, copyEdgers);
					 //printMapeamento(lin, col);
					 executaRotemento(lin, col);
					 
					 System.out.print(">>>");
					 opcao = sc.nextInt();
					 break;
				
				case 5: // Arquivo
					grafo = entrada.lerGrafo();
					 
					//printTasks();
					//printComunications();
					 
					System.out.println("Digite a quantidade de linhas e colunas da rede: ");
					
					lin = sc.nextInt();
					col = sc.nextInt();
					
					copyEdgers = new ArrayList<Edge>();
					copyEdgers = grafo.copyEdgers(grafo.getEdges());
					
					//Instancia o mapeamento proposto
					//mapeamento = entrada.lerMapConsole(grafo.getVertexes(), lin, col);
					
					// Precisa passar o caminho do arquivo
					mapeamentos = entrada.lerMapsArquivo(grafo.getVertexes(), lin, col, "/path");
					
					grafo.setEdges(copyEdgers);
					
					//getMapeamento(lin, col, copyEdgers);
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
			  mapeamentos.add(hmv1.execute(lin, col));
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
			  mapeamentos.add(hmv2.execute(lin, col));
			  grafo.setEdges(copyEdgers);
		 }
		 else
		 {
			 System.out.println("Número de processos maior que o de processadores!");
		 }
	}
	
	public void getMapeamentoSequencial(int lin, int col, ArrayList<Edge> copyEdgers)
	{
		if(lin * col >= grafo.getVertexes().size())
		{
			  mapeamentos.add(seq.execute(lin, col));
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
		 
		 for(Processor [][] mapeamento : mapeamentos)
		 {
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
						 System.out.print("null" + "\t");
						 //System.out.print(mapeamento[i][j].getId() + "\t");
					 }
				 }
				 System.out.println();
			 }
			 
			 System.out.println();
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
				case 1: // XY full
					for(Processor[][] mapeamento: mapeamentos)
					{
						RoteamentoXY xyFull1 = new RoteamentoXY(grafo, mapeamento, lin, col);
						xyFull1.printRoteadores();
						System.out.println();
						System.out.println("ALGORITMO XY FULL:");
						  
						xyFull1.executeFull();
						
						xyFull1.ResultFull();
						xyFull1.printResultFull();
						
						//Usado para copiar somente os valores e não a instância dos edges
						//copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
						
						//showResults(xyFull1, copyEdgers);
					}
					flag = false;
					break;
			
				case 2: // XY By step
					for(Processor[][] mapeamento: mapeamentos)
					{
						//Precisa rodar o full também para pegar o desemprenho o pacote que mais demora no parelelismo também no full 
						//RoteamentoXY xyFull2 = new RoteamentoXY(grafo, mapeamento, lin, col);
						//xyFull2.printRoteadores();
				
						//xyFull2.executeFull();
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
						
						xyStep.ResultByStep(copyEdgers);
						xyStep.printResultByStep();
						
						//showResults(xyStep, copyEdgers);
					}
					
					flag = false;
					break;
					
				case 3: //XY_YX full
					for(Processor[][] mapeamento: mapeamentos)
					{
						RoteamentoXY_YX xy_xyFull1 = new RoteamentoXY_YX(grafo, mapeamento, lin, col);

						System.out.println();
						System.out.println("ALGORITMO XY_YX FULL:");
						  
						xy_xyFull1.executeFull();
						
						xy_xyFull1.ResultFull();
						xy_xyFull1.printResultFull();
						
						//Usado para copiar somente os valores e não a instância dos edges
						//copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
						
						//showResults(xy_xyFull1, copyEdgers);
					}
					
					flag = false;
					break;
					
				case 4: // XY_YX by step
					for(Processor[][] mapeamento: mapeamentos)
					{
						//RoteamentoXY_YX xy_xyFull2 = new RoteamentoXY_YX(grafo, mapeamento, lin, col);

						//xy_xyFull2.executeFull();
						//xy_xyFull2.printResultFull();
						
						//Usado para copiar somente os valores e não a instância dos edges
						copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
						  
						grafo.zerarHops();
						grafo.zerarEnlaces();
						  
						System.out.println();
						System.out.println("ALGORITMO XY_YX BY STEP:");
						RoteamentoXY_YX xy_xyStep = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
						xy_xyStep.executeByStep();
						
						xy_xyStep.ResultByStep(copyEdgers);
					    xy_xyStep.printResultByStep();
					    
					    //showResults(xy_xyStep, copyEdgers);
					}
					
				    flag = false;
					break;
					
				case 5: // XY e XY_YX By Step
					for(Processor[][] mapeamento: mapeamentos)
					{
						//RoteamentoXY xyFulla = new RoteamentoXY(grafo, mapeamento, lin, col);
						//xyFulla.printRoteadores();
						  
						//xyFulla.executeFull();
						//xyFulla.printResultFull();
						
						//Usado para copiar somente os valores e não a instância dos edges
						copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
						
						//showResults(xyFulla, copyEdgers);
							
						grafo.zerarHops();
						grafo.zerarEnlaces();
						
						//-----------------------------------------------------------------------------------------------------------
						
						System.out.println();
						System.out.println("ALGORITMO XY BY STEP:");
						RoteamentoXY xyStepa = new RoteamentoXY(grafo, mapeamento, lin, col);
						xyStepa.executeByStep();	
						
						xyStepa.ResultByStep(copyEdgers);
						xyStepa.printResultByStep();
						//showResults(xyStepa, copyEdgers);
						
						grafo.zerarHops();
						grafo.zerarEnlaces();
						
						//-----------------------------------------------------------------------------------------------------------
						//RoteamentoXY_YX xy_xyFullc = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
						  
						//xy_xyFullc.executeFull();	
						
						//xy_xyFullc.printResultFull();
						//showResults(xy_xyFullc, copyEdgers);
		
						//-----------------------------------------------------------------------------------------------------------
				
						//Usado para copiar somente os valores e não a instância dos edges
						copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
						  
						grafo.zerarHops();
						grafo.zerarEnlaces();
						  
						System.out.println();
						System.out.println("ALGORITMO XY_YX BY STEP:");
						RoteamentoXY_YX xy_xyStepq = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
						xy_xyStepq.executeByStep();
						
						xy_xyStepq.ResultByStep(copyEdgers);
					    xy_xyStepq.printResultByStep();					    
					    //showResults(xy_xyStepq, copyEdgers);
					    
					}
					
				    flag = false;
					break;
				case 6: // Todos
					int index = 1;
					for(Processor[][] mapeamento: mapeamentos)
					{
						System.out.println("Mapeamento: " + index);
						RoteamentoXY xyFulla2 = new RoteamentoXY(grafo, mapeamento, lin, col);
						//xyFulla2.printRoteadores();
						System.out.println();
						System.out.println("ALGORITMO XY FULL:");
						  
						xyFulla2.executeFull();
						
						xyFulla2.ResultFull();
						xyFulla2.printResultFull();
						
						//Usado para copiar somente os valores e não a instância dos edges
						copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
						
						//showResults(xyFulla2, copyEdgers);
						
						grafo.zerarHops();
						grafo.zerarEnlaces();
						
						//-----------------------------------------------------------------------------------------------------------
						
						//System.out.println();
						//System.out.println("ALGORITMO XY BY STEP:");
						RoteamentoXY xyStepa3 = new RoteamentoXY(grafo, mapeamento, lin, col);
						xyStepa3.executeByStep();	
						
						xyStepa3.ResultByStep(copyEdgers);
						xyStepa3.printResultByStep();
						//showResults(xyStepa3, copyEdgers);
						
						grafo.zerarHops();
						grafo.zerarEnlaces();
						
						//-----------------------------------------------------------------------------------------------------------
						RoteamentoXY_YX xy_xyFullcc = new RoteamentoXY_YX(grafo, mapeamento, lin, col);

						System.out.println();
						System.out.println("ALGORITMO XY_YX FULL:");
						  
						xy_xyFullcc.executeFull();	
						
						xy_xyFullcc.ResultFull();
						xy_xyFullcc.printResultFull();
						//showResults(xy_xyFullcc, copyEdgers);
		
						//-----------------------------------------------------------------------------------------------------------
				
						//Usado para copiar somente os valores e não a instância dos edges
						copyEdgers = (ArrayList<Edge>) cloner.deepClone(grafo.getEdges());
						  
						grafo.zerarHops();
						grafo.zerarEnlaces();
						  
						System.out.println();
						System.out.println("ALGORITMO XY_YX BY STEP:");
						RoteamentoXY_YX xy_xyStepqq = new RoteamentoXY_YX(grafo, mapeamento, lin, col);
						xy_xyStepqq.executeByStep();
						
						xy_xyStepqq.ResultByStep(copyEdgers);
					    xy_xyStepqq.printResultByStep();
						//showResults(xy_xyStepqq, copyEdgers);
					}
					
				    flag = false;
					break;
			}
		}
	}
	/*
	public void showResults(Roteamento roteamento, ArrayList<Edge> edgers)
	{
		int opcao;
		Boolean flag = true;
		while(flag)
		{
			Scanner sc = new Scanner(System.in);
			System.out.println("==================== Resultados ====================");
			System.out.println("1 \t \t Full");
			System.out.println("2 \t \t By Step");
			System.out.println("6 \t \t Todos");
			System.out.print(">>>");
			 
			opcao = sc.nextInt();
			
			switch(opcao)
			{
				case 1: 
					System.out.println();
					System.out.println("ALGORITMO XY FULL:");
					for(Processor[][] mapeamento: mapeamentos)
					{
						roteamento.ResultFull();
						roteamento.printResultFull();
					}
					flag = false;
					break;
			
				case 2:
					System.out.println();
					System.out.println("ALGORITMO XY By Step:");
					for(Processor[][] mapeamento: mapeamentos)
					{
						roteamento.ResultByStep(edgers);
						roteamento.printResultByStep();
					}
					
					flag = false;
					break;
			}
		}
	}
	*/
	
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

	public ArrayList<Processor[][]> getMapeamento()
	{
		return mapeamentos;
	}

	public void setMapeamento(ArrayList<Processor[][]> mapeamento) 
	{
		this.mapeamentos = mapeamento;
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
		  
		  //c.init();
		  
		  try
		  {
			  c.myCode();
		  }
		  catch (IOException e) 
		  {
			  System.err.printf("Erro na abertura do arquivo: %s.\n",
			  e.getMessage());
		  }
		  
	}

}