package entidades;
public class Vertex implements Comparable<Vertex>
{
	final private int id;		// Id do vértice
	private String name;	// Nome do vértice
	private int outdegree;
	private int indegree;
	private int degree;
	private boolean alocated;
  
	public Vertex(int  id, String Vertex) 
	{
		this.id = id;
		this.name = name;
		this.alocated = false;
	}
	
	public void alocatedTrue()
	{
		this.alocated = true;
	}
	
	public void alocatedFalse()
	{
		this.alocated = false;
	}
	
	public boolean getAlocationStatus()
	{
		return alocated;
	}
	
	// Observe que os retornos estão invertidos 
	// Isso é para ordenar de forma decrescente
	public int compareTo(Vertex outraConta)
	{
        if (this.degree < outraConta.degree)
        {
            return 1;
        }
        
        if (this.degree > outraConta.degree) 
        {
            return -1;
        }
        return 0;
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
		return degree;
	}
	
	public void incrementsIndegree()
	{
		indegree++;
		degree++;
	}
	
	public void incrementsOutdegree()
	{
		outdegree++;
		degree++;
	}
	
	public int getOutdegree() 
	{
		return outdegree;
	}

	public void setOutdegree(int outdegree) 
	{
		this.outdegree = outdegree;
	}

	public int getIndegree() 
	{
		return indegree;
	}

	public void setIndegree(int indegree) 
	{
		this.indegree = indegree;
	}

	public int getDegree() 
	{
		return degree;
	}

	public void setDegree(int degree) 
	{
		this.degree = degree;
	}

	public boolean isAlocated() 
	{
		return alocated;
	}

	public void setAlocated(boolean alocated) 
	{
		this.alocated = alocated;
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