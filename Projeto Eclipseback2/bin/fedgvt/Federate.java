import java.net.*;
public class Federate // classe que armazena as informações sobre os federados
{
	public String id; // id do federado
	public String ip; // endereço ip do federado
	public String port; // porta na qual ele recebe mensagens
	public int channel;
	
	public Federate (String id, String ip, String port)
	{
		this.id = id;
		this.ip = ip;
		this.port = port;
		channel = -1;
	}
	
	public String getIp() // retorna o endereço ip do federado
	{ return ip; }
	
	public String getPort() // retorna a porta
	{ return port; }
	
	public String getId()
	{ return id; }
	
	public int getChannel()
	{ return channel; }
}	