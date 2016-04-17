import java.net.*;
import java.io.*;
import java.util.*;

public class DCB
{
	private DCBListen NewListen;
	private ApplicationDCB App;

	private int GVT=0;

	public ArrayList FederationList = new ArrayList();  // Lista de objetos contendo as informações sobre as federações e federados
	public static Send[] threads = new Send[10];        // Array de objetos utilizado para armazenar as threads Send (envio de mensagens)
	public static int currentThread = 0;                // Armazena o número de threads criadas

	// vars utilizadas na contagem de msgs
	public int conte = 0, contr = 0;

	/////////////////////////////////////////////////////////////////////////////////////
	public DCB (ApplicationDCB A)
	{
		App = A;
		System.out.println("DCB Inicializado...");
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void Start() throws IOException // construtor
	{
		NewListen = new DCBListen();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public class Send extends Thread                 // Thread responsável pelo envio de mensagens aos federados
	{							                     // É criada uma thread para cada federado
		private Socket socket;
		private ObjectOutputStream dcboutput;
		private ObjectInputStream dcbinput;
		private Federation TmpFederation;
		private Federate TmpFederate;
		private Federate Temp;
		private String DestinationIp;
		private Message MessageToSend;

		public Send (String federation, String federate) throws IOException  // construtor
		{
			super("this");

			TmpFederation = getFederation(federation);     		// Busca o objeto federação a partir do id contido no objeto Mensagem
			TmpFederate = TmpFederation.getFederate(federate);  // Busca o objeto federado (pertencente ao objeto Federação retornado na linha anterior) parametro -> id contido no objeto Mensagem

			InetAddress addr = InetAddress.getByName(TmpFederate.getIp());      // tentativa de conexão --> endereço ip
			socket = new Socket(addr, Integer.parseInt(TmpFederate.getPort())); //                      --> porta

			dcboutput = new ObjectOutputStream(socket.getOutputStream());
			dcbinput = new ObjectInputStream(socket.getInputStream());

			MessageToSend = null;
		}

		public synchronized void SendMessage (Message Msg) // Recebe a mensagem a ser enviada
		{
			MessageToSend = Msg;  // Mensagem a ser enviada
			notify();  			  // Sinaliza a thread que existe uma requisição de envio de mensagem
		}

		public synchronized void run()
		{
			while (true)
			{
				try
				{
					while (MessageToSend == null)
					{
						try
						{
							wait(); // Fica em modo de espera até que exista uma nova mensagem para ser enviada
						}
						catch (InterruptedException e) { continue; }
					}

					dcboutput.writeObject(MessageToSend); // envia o objeto Mensagem para o destino
					dcboutput.flush();

					//char Confirmation;
					//Confirmation = dcbinput.readChar();   // recebe a confirmação


					App.NewEDCB.waiting = false;   // indica ao EDCB que a mensagem enviada anteriormente foi recebida
	 				App.NewEDCB.SendNextMessage(); // e indica que a proxima mensagem pode ser enviada (garante a ordenação dos eventos)

					MessageToSend = null;
				}
				catch(IOException e)
				{
					try
					{
						dcboutput.close();
						socket.close();
					}
					catch (IOException x) {}
				}
			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void DCBSend (Message MessageToSend) throws IOException // envia um objeto Mensagem para o destino
	{															   // se ainda não existe uma thread responsável pelo
		Federation TmpFederation;								   // envio de mensagens ela é criada, senão
		Federate TmpFederate;									   // é utilizada a thread armazenada no array <threads>,
		Send snd;												   // cujo indice pode ser obtido através do método getChannel() do objeto federado

		TmpFederation = getFederation(MessageToSend.FederationDestination);
		TmpFederate = TmpFederation.getFederate(MessageToSend.FederateDestination);

// contador de msgs
//conte++;
//System.out.println("Enviadas:"+conte);

		if (TmpFederate.getIp().compareTo("local") == 0)
				{
				    App.LocalSend(MessageToSend.FederationDestination, MessageToSend.FederateDestination, MessageToSend);

					App.NewEDCB.waiting = false;     // para permitir um novo envio
			 		App.NewEDCB.SendNextMessage();   // por enquanto, na versão com sincronização ainda é necessário
				}
				else
				{
		    		if (TmpFederate.getChannel() == -1)  // Se ainda não existe uma conexão aberta com o federado
		    		{
		    			threads[currentThread] = new Send (MessageToSend.FederationDestination, MessageToSend.FederateDestination); // Cria uma nova thread para tratar a conexão

		    			threads[currentThread].start();

		    			TmpFederation.seFederateChannel(TmpFederate.getId(), currentThread);  // Seta no objeto federado o indice da thread que trata a conexão

		    			currentThread++;
		    		}

		    		threads[TmpFederate.getChannel()].SendMessage(MessageToSend);  // Indica para a thread que existe uma mensagem a ser enviada
   		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public class DCBReceive extends Thread // Thread responsável pelo tratamento da conexão recebida pelo DCBListen
	{									   // É criada uma thread para cada conexão de federado
	 	private Socket socket = null;	   // Essa thread recebe as mensagens enviadas e as repassa para o EF
	 	private ObjectInputStream dcbinput = null;
	 	private ObjectOutputStream dcboutput = null;
	 	private Message MessageReceived = null;

	 	public DCBReceive (Socket s) throws IOException // construtor
	    {
	      	socket = s;
	      	dcbinput = new ObjectInputStream(socket.getInputStream());
	      	dcboutput = new ObjectOutputStream(socket.getOutputStream());
	      	start();
		}

		public void run()
		{
        	while(true)
        	{
		  		try
		  		{
		  			try
		  			{

		  				MessageReceived = (Message) dcbinput.readObject();  // Recebe a mensagem e armazena no objeto MessageReceived
// Contador de msgs
//contr++;
//System.out.println("Recebidas:"+contr);

	 		   			App.NewEF.Decode(MessageReceived); // Envia mensagem para o EF

		 		   		//char Confirmation = '0';
	 		   			//dcboutput.writeChar(Confirmation); // Envia confirmação de recebimento
	 		   			//dcboutput.flush();
	 		   		}
					catch (ClassNotFoundException Excep) { }
		 		}
				catch (IOException e)
				{
					try
					{
						dcbinput.close();
						socket.close();
					}
					catch (IOException x) {}
				}
	    	}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	private class DCBListen extends Thread // Thread que fica esperando por novas conexões.
	{									   // Quando recebe uma conexão, cria uma thread DCBReceive que trata do recebimento da mensagem
		private ServerSocket s;			   // e apos isso volta ao estado de espera...
		private Socket socket;

		public DCBListen() throws IOException
		{
			s = new ServerSocket(App.LocalPort,100);  // Cria um Server Socket na porta especificada
			start();
		}

		public void run()
		{
			try
	   		{
	     		while (true)
		  		{
		  			try
		  			{
		  				socket = s.accept();    // Bloqueia enquanto uma conexão não ocorre
	      				new DCBReceive(socket); // Cria a thread DCBReceive para tratamento da conexão
	      			}
	        		catch(IOException e) { }
	      		}
	      	}
	    	finally
	    	{
	    		try
	    		{
	    			s.close();
	    		}
	    		catch(IOException e) { }
	    	}
	    }
	}

	///////////////////////////////////////////////////////////////////////////////////
	public Federation getFederation(String pid) // Retorna o objeto Federation que possui o id passado por parametro
	{
		Federation Temp = null;

		for (int x = 0; x < FederationList.size(); x++)
		{
			Temp = (Federation) FederationList.get(x);
			if (pid.compareTo(Temp.id) == 0)
				break;
		}
		return Temp;
	}
	///////////////////////////////////////////////////////////////////////////////////
	public synchronized String getGVT()  // Retorna GVT
	{
		return String.valueOf(GVT);
	}
	/////////////////////////////////////////////////////////////////////////////////////
	public void updateGVT(String newGVT)  // Atualiza GVT
	{
		GVT = Integer.parseInt(newGVT);
	}

}