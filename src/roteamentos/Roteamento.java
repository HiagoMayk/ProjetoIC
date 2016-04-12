package roteamentos;

import java.util.ArrayList;

import entidades.*;

public class Roteamento
{
	protected Processor mapeamento[][];	// Rede para mapear os processos
	protected Router roteadores[][];
	protected Graph grafo; 				// Grafo da aplicação
	protected Acumulator acumulator;	// Armazena o valor dos acessos a cada enlace
	protected int linhas;				// Quantidade de linhas da rede
	protected int colunas;				// Quantidade de colunas da rede
	protected int count;
	protected Result result;
	protected ArrayList<Pacote> pacotes;
	
	public Roteamento(Graph grafo, Processor mapeamento[][], int linhas, int colunas)
	{
		this.grafo = grafo;
		this.mapeamento = mapeamento;
		this.acumulator = new Acumulator();
		this.linhas = linhas;
		this.colunas = colunas;
		this.roteadores = new Router[linhas][colunas];
		this.count = 0;
		this.result = new Result();
		this.pacotes = new ArrayList<Pacote>();
		
		//Sincroniza os roteadores, ou seja, insere os processos referente a cada roteador
		sincronizeRouters();
		//printRoteadores();
	}
	
	public void executeFull()
	{
		System.out.println("executeFull method not exists yet!");
	}
	
	public void executeByStep()
	{
		System.out.println("executeByStep method not exists yet!");
    }
	
	/*
	 * Pega todos os processoa para o qual o processo passado como parâmetro manda pacotes
	 */
	public ArrayList<Processor> comunicateTo(Processor proc)
	{
		ArrayList<Processor> procs = new ArrayList<Processor>();
		
		//Para toda comunicação existste
		for(Edge e : grafo.getEdges())
		{
			//Se o identificador do processo for o mesmo que o identificador 
			//da cominucação que está enviando o pacote
			if(proc.getVertex().getId() == e.getSource().getId())
			{
				//Percorre todos os roteadores para encontar o processo de destino do pacote
				//Isso será feito para poder pegar a referencia do processo assim como a cua coordenada
				for(int i = 0; i < linhas; i++)
				{
					for(int j = 0; j < colunas; j++)
					{
						if(mapeamento[i][j].getVertex() != null && mapeamento[i][j].getVertex().getId() == e.getDestination().getId())
						{
							procs.add(mapeamento[i][j]);
						}
					}
				}
			}
		}
		
		return procs;
	}
	
	//Imprime os bufferIn dos roteadores que possem processos rodando
	public void printBufferInRoutersWithProcess()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				if(roteadores[i][j].getProcessor().getVertex() != null)
				{
					System.out.println("-------------------- " + roteadores[i][j].getProcessor().getVertex().getName() + "-------------------- ");
					for(Pacote p : roteadores[i][j].getBufferIn())
					{
						System.out.println("Coordenada: " + p.getCurrentCoordinate().getLine() + ", " + p.getCurrentCoordinate().getColumn());
						System.out.println("Prioridade: " + p.getPriority() );
						System.out.println("Origem: " + p.getSource().getId());
						System.out.println("Destino: " + p.getDestination().getId());
						System.out.println();
					}
				}
			}
			
		}
	}
	
	//Imprime todos os rotedores que possuem pacotes no bufferIn e seus respectivos pacotes
	public void printBufferInRouters()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				if(roteadores[i][j].getBufferIn().size() != 0)
				{
					System.out.println("-------------------- Roteador (" + i + ", " + j + ") -------------------- ");
					for(Pacote p : roteadores[i][j].getBufferIn())
					{
						System.out.println("Coordenada: " + p.getCurrentCoordinate().getLine() + ", " + p.getCurrentCoordinate().getColumn());
						System.out.println("Prioridade: " + p.getPriority() );
						System.out.println("Origem: " + p.getSource().getId());
						System.out.println("Destino: " + p.getDestination().getId());
						System.out.println();
					}
				}
			}
		}
	}
	
	//Imprime os bufferOut dos roteadores que possem processos rodando
		
	public void printBufferOutRoutersWithProcess()
	{
			for(int i = 0; i < linhas; i++)
			{
				for(int j = 0; j < colunas; j++)
				{
					if(roteadores[i][j].getProcessor().getVertex() != null)
					{
						System.out.println("-------------------- " + roteadores[i][j].getProcessor().getVertex().getName() + "-------------------- ");
						for(Pacote p : roteadores[i][j].getBufferOut())
						{
							System.out.println("Coordenada: " + p.getCurrentCoordinate().getLine() + ", " + p.getCurrentCoordinate().getColumn());
							System.out.println("Prioridade: " + p.getPriority() );
							System.out.println("Origem: " + p.getSource().getId());
							System.out.println("Destino: " + p.getDestination().getId());
							System.out.println();
						}
					}
				}
				
			}
		}
		
		//Imprime todos os rotedores que possuem pacotes no buffeOut e seus respectivos pacotes
		public void printBufferOutRouters()
		{
			for(int i = 0; i < linhas; i++)
			{
				for(int j = 0; j < colunas; j++)
				{
					if(roteadores[i][j].getBufferOut().size() != 0)
					{
						System.out.println("-------------------- Roteador (" + i + ", " + j + ") -------------------- ");
						for(Pacote p : roteadores[i][j].getBufferOut())
						{
							System.out.println("Coordenada: " + p.getCurrentCoordinate().getLine() + ", " + p.getCurrentCoordinate().getColumn());
							System.out.println("Prioridade: " + p.getPriority() );
							System.out.println("Origem: " + p.getSource().getId());
							System.out.println("Destino: " + p.getDestination().getId());
							System.out.println();
						}
					}
				}
				
			}
		}
	
	
	public void printRoteadores()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				if(roteadores[i][j].getProcessor().getVertex() == null)
				{
					System.out.print(roteadores[i][j].getProcessor().getId() + "\t");
				}
				else
				{
					System.out.print(roteadores[i][j].getProcessor().getVertex().getName() + "\t");
				}
			}
			
			System.out.println();
		}
	}
	
	
	public void sincronizeRouters()
	{
		for(int i = 0; i < linhas; i++)
		{
			for(int j = 0; j < colunas; j++)
			{
				roteadores[i][j] = new Router(mapeamento[i][j]);
				if(mapeamento[i][j].getVertex() != null)
				{
					ArrayList<Processor> comunica = new ArrayList<Processor>();
					comunica = comunicateTo(roteadores[i][j].getProcessor());
					
					//Pode ocorrer a existencia de processos que não  enviam mensagens
					if(comunica.size() > 0)
					{
						for(int k = 0; k < roteadores[i][j].getProcessor().getVertex().getOutdegree(); k++)
						{
							if(roteadores[i][j].getProcessor().getVertex().getOutdegree() > 0)
							{
								//Contrutur de Pacote: 
								//Pacote(int priority, Processor source, Processor destination, int x, int y)
								//System.out.println(comunica.size() + " ====== " + roteadores[i][j].getProcessor().getVertex().getOutdegree());
								Pacote pacote = new Pacote(roteadores[i][j].getProcessor().getVertex().getOutdegree(), roteadores[i][j].getProcessor(), comunica.get(k), i, j);
								
								//Assumimos aqui também que um processo pode enviar mensagem para ele mesmo
								roteadores[i][j].addBufferIn(pacote);
								pacotes.add(pacote);
							}
						}
					}
				}
			}
		}
	}

	/*
	 * Imprime o resultado do acumulator
	 */
	public void printResultByStep()
	{
		result = new  Result();
		int hopAcumulator = 0;
		/*System.out.println();
		System.out.println("Caminhos percorridos e total de hops: ");*/
		for(Pacote e : pacotes)
		{
			  //System.out.println(e.getSource().getName() + "\t" + "-" + "\t" + e.getDestination().getName() + "\t" + "->" + "\t" + e.getWeight() + "\t Hops: " +  e.getHops());
			  
			  hopAcumulator += e.getHops();
			  
			  /*
			  for(Enlace en : e.getEnlaces())
			  {
				  System.out.println(en.getSource() + " - " + en.getDestination());
			  }
			  System.out.println();*/
		}

		/*System.out.println("Enlaces acessados:");
		acumulator.printAcumulator();*/
		
		result.setTotalHops(hopAcumulator);
		result.setTotalEnlaces(acumulator.getEnlace().size());
		
		System.out.println("Total de hops: " + result.getTotalHops());
		System.out.println("Quantidade de enlaces acessados: " + result.getTotalEnlaces());
		System.out.println("Total de reuso dos enlaces: " + result.totalReutilizado(acumulator));
		System.out.println("Taxa de reuso dos enlaces: " + result.calculaTaxaReuso(acumulator) + "%");
	}
	
	/*
	 * Imprime o resultado do acumulador
	 */
	public void printResultFull()
	{
		result = new  Result();
		int hopAcumulator = 0;
		/*System.out.println();
		System.out.println("Caminhos percorridos e total de hops: ");*/
		for(Edge e : grafo.getEdges())
		{
			  //System.out.println(e.getSource().getName() + "\t" + "-" + "\t" + e.getDestination().getName() + "\t" + "->" + "\t" + e.getWeight() + "\t Hops: " +  e.getHops());
			  
			  hopAcumulator += e.getHops();
			  
			  /*
			  for(Enlace en : e.getEnlaces())
			  {
				  System.out.println(en.getSource() + " - " + en.getDestination());
			  }
			  System.out.println();*/
		}

		/*System.out.println("Enlaces acessados:");
		acumulator.printAcumulator();*/
		
		result.setTotalHops(hopAcumulator);
		result.setTotalEnlaces(acumulator.getEnlace().size());
		
		System.out.println("Total de hops: " + result.getTotalHops());
		System.out.println("Quantidade de enlaces acessados: " + result.getTotalEnlaces());
		System.out.println("Total de reuso dos enlaces: " + result.totalReutilizado(acumulator));
		System.out.println("Taxa de reuso dos enlaces: " + result.calculaTaxaReuso(acumulator) + "%");
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

	public Acumulator getAcumulator() 
	{
		return acumulator;
	}

	public void setAcumulator(Acumulator acumulator) 
	{
		this.acumulator = acumulator;
	}

	public int getLinhas() 
	{
		return linhas;
	}

	public void setLinhas(int linhas) 
	{
		this.linhas = linhas;
	}

	public int getColunas() 
	{
		return colunas;
	}

	public void setColunas(int colunas) 
	{
		this.colunas = colunas;
	}
}
	