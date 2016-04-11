package entidades;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author mayk
 * Classe que representa o roteador
 */
public class Router 
{
	private Processor processor;
	private ArrayList<Pacote> bufferOut; //Buffer dos pacotes que deveraão ser enviados para outro roteador
	private ArrayList<Pacote> bufferIn;  //Buffer dos pacotes que estão para execuar e dos que chegam
	
	public Router(Processor processor)
	{
		this.processor = processor;
		this.bufferOut = new ArrayList<Pacote>();
		this.bufferIn = new ArrayList<Pacote>();
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
	
	public void changeBuffer()
	{
		 for(int i = 0; i < bufferIn.size(); i++)
		 {
			 addBufferOut(bufferIn.get(i));
		 }
		 
		 bufferIn = new ArrayList<Pacote>();
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
	        	//Apenas removemos, porém podemos inserir no processo em uma lista de pacotes recebidos para ter o controle
	        	//de quantos e quais pacotes foram recebidos por determinado processo
	        	System.out.println("--------------------------------------- ");
	  
	        	System.out.println("Coordenada: " + element.getCurrentCoordinate().getLine() + ", " + element.getCurrentCoordinate().getColumn());
	        	System.out.println("Prioridade: " + element.getPriority() );
	        	System.out.println("Origem: " + element.getSource().getId());
	        	System.out.println("Destino: " + element.getDestination().getId());
	        	System.out.println("Hops: " + element.getHops());
	        	System.out.println("Enlaces: " + element.getEnlaces().size());
	        	
	        	System.out.println();
	        	
	        	itr.remove();
	        	count++;
	        }
	     }
	    
	    return count;
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
	
	
}
