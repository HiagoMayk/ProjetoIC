package entidades;

public class Comunica 
{
	private Processor proc;
	private int peso;
	
	public Comunica(Processor proc, int peso)
	{
		this.proc = proc;
		this.peso = peso;
	}

	public Processor getProc() 
	{
		return proc;
	}

	public int getPeso() 
	{
		return peso;
	}

	public void setProc(Processor proc) 
	{
		this.proc = proc;
	}

	public void setPeso(int peso)
	{
		this.peso = peso;
	}
	
}
