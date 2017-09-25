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
	public ArrayList<Comunica> comunicateTo(Processor proc)
	{
		ArrayList<Comunica> procs = new ArrayList<Comunica>();
		
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
							procs.add(new Comunica(mapeamento[i][j], e.getWeight()));
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
					System.out.print("null" + "\t");
					//System.out.print(roteadores[i][j].getProcessor().getId() + "\t");
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
					ArrayList<Comunica> comunica = comunicateTo(roteadores[i][j].getProcessor());
					
					//Pode ocorrer a existencia de processos que não  enviam mensagens
					if(comunica.size() > 0)
					{
						for(int k = 0; k < roteadores[i][j].getProcessor().getVertex().getOutdegree(); k++)
						{
							if(roteadores[i][j].getProcessor().getVertex().getOutdegree() > 0)
							{
								//Contrutur de Pacote: 
								//Pacote(int priority, Processor source, Processor destination, int x, int y)
								//System.out.println(roteadores[i][j].getProcessor().getVertex().getName() + " ====== " + comunica.size() + " ====== " + roteadores[i][j].getProcessor().getVertex().getOutdegree() );
								
								// ERRO por aqui
								Pacote pacote = new Pacote(roteadores[i][j].getProcessor().getVertex().getOutdegree(), roteadores[i][j].getProcessor(), comunica.get(k).getProc(), i, j, comunica.get(k).getPeso());
								
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
	 * Calcula e armazena em result o resultado do acumulator
	 */
	public void ResultByStep(ArrayList<Edge> edges)
	{
		result = new  Result();
		int hopAcumulator = 0;
		int maiorLatencia = 0;
		int maiorHop = 0;
		int maiorSemParalelismo = 0;
		int totalFlits = 0;
		Pacote pacote1 = null;
		Pacote pacote2 = null;
		int totalLatencia = 0;
		/*System.out.println();
		System.out.println("Caminhos percorridos e total de hops: ");*/
		for(Pacote e : pacotes)
		{
			  //System.out.println(e.getSource().getVertex().getName() + "\t" + "-" + "\t" + e.getDestination().getVertex().getName() + "\t" + "->" + "\t" + e.getFlits() + "\t Hops: " +  e.getHops() + "\t Latencia: " + e.getLatencia());
			  
			  hopAcumulator += e.getHops();
			  totalFlits += e.getFlits();
			  totalLatencia += e.getLatencia();
			  if(e.getLatencia() > maiorLatencia)
			  {
				  maiorLatencia = e.getLatencia();
				  pacote1 = e;
			  }
			  
			  if(e.getHops() > maiorHop)
			  {
				  maiorHop = e.getHops();
				  pacote2 = e;
			  }
			  
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
		
		result.setLatencia_pct_maior_latencia(pacote1.getLatencia());
		result.setHops_pct_maior_latencia(pacote1.getHops());
		result.setLatencia_pct_maior_hop(pacote2.getLatencia());
		result.setHops_pct_maior_hop(pacote2.getHops());
		result.setLatencia_somatorio(totalLatencia);
		result.setLatencia_media((totalLatencia / pacotes.size()));
		result.setTotalEnlaces(result.getTotalEnlaces());
		result.setSomatorio_acessos_enlaces(result.totalUso(acumulator));
		result.setEnlaces_reuso(result.totalReuso(acumulator));
		result.setEnlaces_reusados(result.totalReutilizado(acumulator));
		result.setTaxa_reuso(result.calculaTaxaReuso(acumulator));
		result.setTotal_flits(totalFlits);
		result.setSomatorio_acessos_flits(result.totalUsoFlits(acumulator));
		result.setReuso_flits(result.totalReutilizadoFlits(acumulator));
		result.setTaxa_reuso_flits(result.calculaTaxaReusoFlits(acumulator));
	}
	
	/*
	 * Imprime o resultado do acumulator
	 */
	public void printResultByStep()
	{	
		System.out.println("(Pacote maior latencia) Lantencia: " + result.getLatencia_pct_maior_latencia());
		System.out.println("(Pacote maior latencia) Hops: " + result.getHops_pct_maior_latencia());
		System.out.println("(Pacote maior hop) Lantencia: " + result.getLatencia_pct_maior_hop());
		System.out.println("(Pacote maior hop) Hops: " + result.getHops_pct_maior_hop());
		System.out.println("Somatório das latencias: " + result.getLatencia_somatorio());
		System.out.println("Latencia Média: " + result.getLatencia_media());
		//System.out.println("Hops do mais lento (com atraso) SEM paralelismo:" + maiorSemParalelismo);
		//System.out.println("Somatório de hops: " + result.getTotalHops());
		System.out.println("Enlaces acessados: " + result.getTotalEnlaces());
		System.out.println("Somatorio de acessos aos enlaces: " + result.getSomatorio_acessos_enlaces());
		System.out.println("Enlaces reusados: " + result.getEnlaces_reuso());
		System.out.println("Reuso dos enlaces: " + result.getEnlaces_reusados());
		System.out.println("Taxa de reuso dos enlaces: " + result.getTaxa_reuso() + "%");
	
		System.out.println("Total Flits:" + result.getTotal_flits());
		System.out.println("Somatorio de acessos aos enlaces (em flits): " + result.getSomatorio_acessos_flits());
		System.out.println("Reuso dos enlaces (em flits): " + result.getReuso_flits());
		System.out.println("Taxa de reuso dos enlaces (em flits): " + result.getTaxa_reuso_flits() + "%");
	}
	
	/*
	 * Calcula e armazena em result o resultado do acumulador
	 */
	public void ResultFull()
	{
		result = new  Result();
		int hopAcumulator = 0;
		int maior = 0;
		/*System.out.println();
		System.out.println("Caminhos percorridos e total de hops: ");*/
		for(Edge e : grafo.getEdges())
		{
			  //System.out.println(e.getSource().getName() + "\t" + "-" + "\t" + e.getDestination().getName() + "\t" + "->" + "\t" + e.getWeight() + "\t Hops: " +  e.getHops());
			  
			  hopAcumulator += e.getHops();
			  if(e.getHops() > maior)
			  {
				  maior = e.getHops();
			  }
			  
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
		
		result.setLatencia_pct_maior_latencia(result.getTotalHops());
		result.setHops_pct_maior_latencia(result.getTotalHops());
		result.setLatencia_pct_maior_hop(result.getTotalHops());
		result.setHops_pct_maior_hop(result.getTotalHops());
		result.setLatencia_somatorio(-1);
		result.setLatencia_media(-1);
		result.setTotalEnlaces(result.getTotalEnlaces());
		result.setSomatorio_acessos_enlaces(result.totalUso(acumulator));		
		result.setEnlaces_reuso(result.totalReuso(acumulator));		
		result.setEnlaces_reusados(result.totalReutilizado(acumulator));		
		result.setTaxa_reuso(result.calculaTaxaReuso(acumulator));
		result.setTotal_flits(-1);
		result.setSomatorio_acessos_flits(-1);
		result.setReuso_flits(-1);
		result.setTaxa_reuso_flits(-1);
	}
	
	/*
	 * Imprime o resultado do acumulador
	 */
	public void printResultFull()
	{
		//Total de hops: tempo total de entrega de todos os pacotes 
		System.out.println("Latencia: " + result.getLatencia_pct_maior_latencia());
		
		//Quantidade de enlaces: quatidade de enlaces usados: quantidade de enlaces que foram criados para transportar pacotes;
		System.out.println("Enlaces acessados: " + result.getTotalEnlaces());
		
		//Total de acesso aos enlaces: somatório dos acessos de todos os enlaces
		System.out.println("Somatorio de acessos aos enlaces: " + result.getSomatorio_acessos_enlaces());
		
		//Quantidadede enlaces reusados: quantidadede enlaces criados que foram reusados
		System.out.println("Enlaces reusados: " + result.getEnlaces_reuso());
		
		//Total de reuso dos enlaces: Diferença entre o somatorio de acessos aos enlaces e a quantidade de enlaces usados
		System.out.println("Acessos aos enlaces reusados: " + result.getEnlaces_reusados());
		
		//Taxa de reuso dos enlaces: formila = (100 * [total_de_reuso_dos_enlaces])/[Total_de_acessos_aos_enlaces] 
		System.out.println("Taxa de reuso dos enlaces: " + result.getTaxa_reuso() + "%");
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
	
	public Result getResult() 
	{
		return result;
	}

	public void setResult(Result result) 
	{
		this.result = result;
	}
}
	