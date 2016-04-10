package entidades;

import java.util.ArrayList;

/**
 * @author mayk
 * Classe que representa o roteador
 */
public class Router 
{
	private Processor processor;
	private ArrayList<Pacote> buffer;
	
	public Router(Processor processor)
	{
		this.processor = processor;
		this.buffer = new ArrayList<Pacote>();
	}

	public void addBuffer(Pacote pacote)
	{
		boolean flag = false;
		
		if(buffer.size() == 0)
		{
			buffer.add(pacote);
			flag = true;
		}
		else
		{	//int index = 0;
			//Deu problema aqui, pois estava mudando o tamanho do ArrayLista com a inserção do novo elemento 
			for(Pacote p : buffer)
			{	//index++;
				if(pacote.getPriority() > p.getPriority())
				{
					buffer.add(buffer.indexOf(p), pacote);
					flag = true;
					break;
				}
			}
		}
		
		if(!flag)
		{
			buffer.add(pacote);	
		}
	}
	
	public Pacote removeBuffer()
	{
		if(buffer.size() == 0)
		{
			return null;
		}
		
		return buffer.get(0); 
	}
	
	public Processor getProcessor() 
	{
		return processor;
	}

	public void setProcessor(Processor processor) 
	{
		this.processor = processor;
	}

	public ArrayList<Pacote> getBuffer() 
	{
		return buffer;
	}

	public void setBuffer(ArrayList<Pacote> buffer) 
	{
		this.buffer = buffer;
	}
}
