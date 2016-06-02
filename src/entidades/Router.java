package entidades;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author mayk
 * Classe que representa o roteador
 */
public class Router 
{
	private Processor processor;
	private ArrayList<Pacote> bufferOut; //Buffer dos pacotes que deveraão ser enviados para outro roteador
	private ArrayList<Pacote> bufferIn;  //Buffer dos pacotes que estão para execuar e dos que chegam
	private ArrayList<Pacote> bufferAux; //Buffer dos pacotes que estão no buffer a mais tempo. Isso garante que ele tenha maior precedência se comparado com os que chegaram agora
	private boolean direita;
	private boolean esquerda;
	private boolean cima;
	private boolean baixo;
	private int latenciaDireita;
	private int latenciaEsquerda;
	private int latenciaCima;
	private int latenciaBaixo;
	
	public Router(Processor processor)
	{
		this.processor = processor;
		this.bufferOut = new ArrayList<Pacote>();
		this.bufferIn = new ArrayList<Pacote>();
		this.bufferAux = new ArrayList<Pacote>();
		direita = false;
		esquerda = false;
		cima = false; 
		baixo = false;
	}
	
	public void addBufferOut(Pacote pacote)
	{
		boolean flag = false;
		
		if(bufferOut.size() == 0)
		{
			bufferOut.add(pacote);
			flag = true;
		}
		else
		{	//int index = 0;
			//Deu problema aqui, pois estava mudando o tamanho do ArrayLista com a inserção do novo elemento 
			for(Pacote p : bufferOut)
			{	//index++;
				if(pacote.getPriority() > p.getPriority())
				{
					bufferOut.add(bufferOut.indexOf(p), pacote);
					flag = true;
					break;
				}
			}
		}
		
		if(!flag)
		{
			bufferOut.add(pacote);	
		}
	}

	public void addBufferIn(Pacote pacote)
	{
		boolean flag = false;
		
		if(bufferIn.size() == 0)
		{
			bufferIn.add(pacote);
			flag = true;
		}
		else
		{	//int index = 0;
			//Deu problema aqui, pois estava mudando o tamanho do ArrayLista com a inserção do novo elemento 
			for(Pacote p : bufferIn)
			{	//index++;
				if(pacote.getPriority() > p.getPriority())
				{
					bufferIn.add(bufferIn.indexOf(p), pacote);
					flag = true;
					break;
				}
			}
		}
		
		if(!flag)
		{
			bufferIn.add(pacote);	
		}
	}
	
	public void addBufferAux(Pacote pacote)
	{
		boolean flag = false;
		
		if(bufferAux.size() == 0)
		{
			bufferAux.add(pacote);
			flag = true;
		}
		else
		{	//int index = 0;
			//Deu problema aqui, pois estava mudando o tamanho do ArrayLista com a inserção do novo elemento 
			for(Pacote p : bufferAux)
			{	//index++;
				if(pacote.getPriority() > p.getPriority())
				{
					bufferAux.add(bufferAux.indexOf(p), pacote);
					flag = true;
					break;
				}
			}
		}
		
		if(!flag)
		{
			bufferAux.add(pacote);
		}
	}
	
	public void changeBuffer()
	{
		for(int i = 0; i < bufferAux.size(); i++)
		 {
			 addBufferOut(bufferAux.get(i));
		 }
		
		 for(int i = 0; i < bufferIn.size(); i++)
		 {
			 addBufferOut(bufferIn.get(i));
		 }
		 
		 bufferIn = new ArrayList<Pacote>();
		 bufferAux = new ArrayList<Pacote>();
	}
	
	//Simula entrega do pacote para o processador referente a esse roteador
	public int pacoteToHere()
	{
		int count = 0;
		Iterator<Pacote> itr = bufferIn.iterator();
	    while(itr.hasNext())
	    {
	        Pacote element = itr.next();
	         
	        if(processor != null && (element.getCoordinateDestination().getLine() == processor.getCoordinate().getLine() &&
	        element.getCoordinateDestination().getColumn() == processor.getCoordinate().getColumn()))
	        {
	        	/*//Apenas removemos, porém podemos inserir no processo em uma lista de pacotes recebidos para ter o controle
	        	//de quantos e quais pacotes foram recebidos por determinado processo
	        	System.out.println("--------------------------------------- ");
	  
	        	System.out.println("Coordenada: " + element.getCurrentCoordinate().getLine() + ", " + element.getCurrentCoordinate().getColumn());
	        	System.out.println("Prioridade: " + element.getPriority() );
	        	System.out.println("Origem: " + element.getSource().getId());
	        	System.out.println("Destino: " + element.getDestination().getId());
	        	System.out.println("Hops: " + element.getHops());
	        	System.out.println("Enlaces: " + element.getEnlaces().size());
	        	
	        	System.out.println();
	        	*/
	        	itr.remove();
	        	count++;
	        }
	     }
	    
	    return count;
	}
	
	public void incrementaLatencia()
	{
		int values[];
		Iterator<Pacote> itr = bufferAux.iterator();
	    while(itr.hasNext())
	    {
	        Pacote element = itr.next();
	        values = new int[4];
	        if(element.getCurrentCoordinate().getColumn() < element.getCoordinateDestination().getColumn())
            {
	        	values[0] = getLatenciaDireita();
			}
			else if(element.getCurrentCoordinate().getColumn() > element.getCoordinateDestination().getColumn())
			{
				values[1] = getLatenciaEsquerda();		
			}
			else if(element.getCurrentCoordinate().getLine() < element.getCoordinateDestination().getLine())
			{
				values[2] = getLatenciaBaixo();
			}
			else if(element.getCurrentCoordinate().getLine() > element.getCoordinateDestination().getLine())
			{
				values[3] = getLatenciaCima();
			}
	        
	        Arrays.sort(values);

	        for(int i = 0; i < 4; i++)
	        {
	        	if(values[i] != 0) 
	        	{
	        		element.setLatencia(element.getLatencia() + values[i]);
	        		break;
	        	}
	        }
	        //System.out.println(element.getLatencia());
	        //System.exit(0);
	     }
	}
	
	public Pacote removeBufferOut()
	{
		if(bufferOut.size() == 0)
		{
			return null;
		}
		
		return bufferOut.get(0); 
	}
	
	public Pacote removeBufferIn()
	{
		if(bufferIn.size() == 0)
		{
			return null;
		}
		
		return bufferIn.get(0);
	}
	
	public Processor getProcessor()
	{
		return processor;
	}

	public void setProcessor(Processor processor)
	{
		this.processor = processor;
	}

	public ArrayList<Pacote> getBufferOut() 
	{
		return bufferOut;
	}

	public void setBufferOut(ArrayList<Pacote> buffer) 
	{
		this.bufferOut = buffer;
	}
	
	public ArrayList<Pacote> getBufferIn() 
	{
		return bufferIn;
	}

	public void setBufferIn(ArrayList<Pacote> buffer) 
	{
		this.bufferIn = buffer;
	}
	
	public boolean isDireita() 
	{
		return direita;
	}

	public void setDireita(boolean direita) 
	{
		this.direita = direita;
	}

	public boolean isEsquerda() 
	{
		return esquerda;
	}

	public void setEsquerda(boolean esquerda) 
	{
		this.esquerda = esquerda;
	}

	public boolean isCima() 
	{
		return cima;
	}

	public void setCima(boolean cima) 
	{
		this.cima = cima;
	}

	public boolean isBaixo() 
	{
		return baixo;
	}

	public void setBaixo(boolean baixo) 
	{
		this.baixo = baixo;
	}
	
	public ArrayList<Pacote> getBufferAux() 
	{
		return bufferAux;
	}

	public int getLatenciaDireita()
	{
		return latenciaDireita;
	}

	public int getLatenciaEsquerda()
	{
		return latenciaEsquerda;
	}

	public int getLatenciaCima()
	{
		return latenciaCima;
	}

	public int getLatenciaBaixo()
	{
		return latenciaBaixo;
	}

	public void setBufferAux(ArrayList<Pacote> bufferAux) 
	{
		this.bufferAux = bufferAux;
	}

	public void setLatenciaDireita(int latencia_direita) 
	{
		this.latenciaDireita = latencia_direita;
	}

	public void setLatenciaEsquerda(int latencia_esquerda) 
	{
		this.latenciaEsquerda = latencia_esquerda;
	}

	public void setLatenciaCima(int latencia_cima) 
	{
		this.latenciaCima = latencia_cima;
	}

	public void setLatenciaBaixo(int latencia_baixo) 
	{
		this.latenciaBaixo = latencia_baixo;
	}
	
}
