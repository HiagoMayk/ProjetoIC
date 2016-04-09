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
		{
			for(Pacote p : buffer)
			{
				if(pacote.getPriority() > p.getPriority())
				{
					buffer.add(buffer.indexOf(p), pacote);
					flag = true;
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
