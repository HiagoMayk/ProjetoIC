public class Vertex
{
	final private int id;		// Id do vértice
	private String name;	// Nome do vértice
	private int outdegree;
	private int indegree;
  
	public Vertex(int  id, String name) 
	{
		this.id = id;
		this.name = name;
	}
  
	/*
	 * Retorna o id do vértice
	 */
	public int getId()
	{
		return id;
	}

	/*
	 * Retorna o nome do vértice
	 */
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public int getTotalDegree()
	{
		return indegree + outdegree;
	}
	
	public void incrementsIndegree()
	{
		indegree++;
	}
	
	public void incrementsOutdegree()
	{
		outdegree++;
	}
	
	/*
	 * Retorna resultado do armazenamento na tabela hash
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * Compara dois objetos
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		Vertex other = (Vertex) obj;
		if(id != other.id)
		{
			return false;
		}
		if(name == null) 
		{
			if(other.name != null)
			{
				return false;
			}
		} 
		else if(!name.equals(other.name))
		{
			return false;
		}
		return true;
	}

	/*
	 * Retorna o nome do vértice
	 */
	@Override
	public String toString() 
	{
		return name;
	}
} 