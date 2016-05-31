import java.io.*;
import java.util.*;
import javax.swing.*;
import java.lang.*;

public class EF
{
	private int actualLVT;
	private ApplicationDCB App;
	public ArrayList InputRegisterList= new ArrayList();    // Lista de objetos contendo as informações sobre os atributos locais do federado (nome e tipo)
	public ArrayList InputAttributeQueue = new ArrayList();
	public int contElemIAQ = 0;
	public int lessTimestamp = 0;
	public String input;

	/////////////////////////////////////////////////////////////////////////////////////
	public EF(ApplicationDCB A) throws IOException // construtor
	{
		App = A;
		actualLVT = 0;
		System.out.println("EF Inicializado...");
	}

	////////////////////////////////////////////////////////////////////////////////////
	//   	SYNCHRONIZATION ( LVT / GVT )
	/////////////////////////////////////////////////////////////////////////////////////

	public String getLVT() // Retorna o LVT (Local Virtual Time)
	{
		return String.valueOf(actualLVT);
	}

    /////////////////////////////////////////////////////////////////////////////////////
	public synchronized String updateLVT(String newLVT)	// Recebe o LVT do federado e atualiza LVT local
	{
		//int oldLVT = actualLVT;
		if (Integer.parseInt(newLVT) <= Integer.parseInt(App.NewDCB.getGVT()))
		{
			actualLVT = Integer.parseInt(newLVT);
		}
		else {
			// esta condição ocorre quando LVT do federado origem é > GVT,
			// então, a mensagem não é enviada, e o LVT é igualado ao GVT
			actualLVT = Integer.parseInt(App.NewDCB.getGVT());
		}

		// Msg de atualização do GVT é enviada, desde que o federado não seja notime.
		// Isso reduz overhead mas exige que pelo menos um federado da federação seja sincrono
		if (App.FederateType.compareTo("notime")!= 0)
 			App.NewEDCB.Update1("444.2", String.valueOf(actualLVT),"0");

//System.out.println("LVT do " + App.UniqueFederateID + ": " + actualLVT);

		if (lessTimestamp <= actualLVT &&
			//	actualLVT > oldLVT &&
				contElemIAQ > 0) seekInputAttributeQueue();
		// se um federado avança no tempo sem enviar msg,
		// um seek precisa ser executado na lista de entrada
		return String.valueOf(actualLVT);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	//     END OF SYNCHRONIZATION
	/////////////////////////////////////////////////////////////////////////////////////
	public void Decode(Message Msg) throws IOException // Decodifica a mensagem
	{
		InputAttribute AttributeTemp = null;
		String Source = Msg.FederationSource + Msg.FederateSource;
//System.out.print(Msg.LVT + " ");
		AttributeTemp = new InputAttribute(Msg.AttributeID,
									  Msg.Value,
									  Source,
									  Msg.LVT,   // Fiz alteração aqui (o LVT é o timestamp da msg)
									  getAttributeType(Msg.AttributeID));

		if (Msg.AttributeID.compareTo("444.1") != 0) {

			InputAttributeQueue.add(AttributeTemp);
//System.out.println("Stored Msg from:" + AttributeTemp.Source + " Attrib.:" + Msg.AttributeID + " Tstp.:" + Msg.LVT);
			contElemIAQ++; // controla o numero de elementos na lista de entrada
			seekInputAttributeQueue();

		} else {
			// Se AttributeID == "444.1", atualiza GVT do federado local - proveniente de broadcast de atualizacao
			// e o valor de Msg.Value carrega o novo GVT

			App.NewDCB.updateGVT(Msg.Value);
			if (App.FederateType.compareTo("notime") == 0) {
				if (contElemIAQ > 0) seekInputAttributeQueue(); // Verifica se há msg's a serem enviadas antes de atualizar o GVT
				updateLVT(Msg.Value);
			}
			// Avança o LVT de federados 'notime'. Como antes desta operação é
			// disparado um seek na fila de entrada, não existe o risco do LVT
			// ultrapassar o timestamp de mensagens ainda não retiradas da lista
			// de entrada e enviadas ao federado.
//System.out.println("Novo GVT:" + App.NewDCB.getGVT());

		}

	}

	/////////////////////////////////////////////////////////////////////////////////////
	public synchronized void seekInputAttributeQueue ()
	{
		InputAttribute Temp = null;
		InputAttribute TempNext = null;

		// Encontra Msg's com menor LVT na lista e encaminha para execução pelo federado.
		// executa a busca na lista mais de uma vez, pois a lista pode conter
		// mais de uma mensagem com timestamp <= GVT
		while (InputAttributeQueue.size() > 0) //for (int y = 0; y < InputAttributeQueue.size();y++)
		{
//System.out.println("size:" + InputAttributeQueue.size());
			Temp = (InputAttribute) InputAttributeQueue.get(0);
//System.out.println("Source:" + Temp.Source + " MsgTst:" + Temp.LVT +" LVT:" + actualLVT + " GVT:" + App.NewDCB.getGVT() + " LTt:" + lessTimestamp);
			for (int x = 1; x < InputAttributeQueue.size(); x++)
			{
			  	TempNext = (InputAttribute) InputAttributeQueue.get(x);
				if (Integer.parseInt(TempNext.LVT) < Integer.parseInt(Temp.LVT))
  				   	Temp = (InputAttribute) InputAttributeQueue.get(x);
//System.out.println("Source:" + TempNext.Source + " MsgTst:" + TempNext.LVT + " LVT:" + actualLVT +" GVT:" + App.NewDCB.getGVT() + " LTt:" + lessTimestamp);
			}
			lessTimestamp = Integer.parseInt(Temp.LVT);

			// se Temp.LVT começar com 0 então é originário de notime
			// incluir tratamento
//System.out.print(Temp.LVT +" ");
			if (Temp.LVT.indexOf('0') == 0)
			{
				//System.out.println("TIMESTAMP DA MSG: "+lessTimestamp+" GVT:"+App.NewDCB.getGVT()+"TempUID:"+Temp.uid);
				App.NewGateway.Redirect(getProtocolConverterID(Temp.uid));
				if (App.FederateType.compareTo("notime") == 0) updateLVT(App.NewDCB.getGVT());
//System.out.print(Temp.LVT +" ** notime ** ");
			}

			// se Temp.LVT não começar com 0 então é originario de sincrono
			else if (lessTimestamp <= Integer.parseInt(App.NewDCB.getGVT()) && Temp.LVT.indexOf('0') != 0) {
	//System.out.println("TIMESTAMP DA MSG: "+lessTimestamp+" GVT:"+App.NewDCB.getGVT()+"TempUID:"+Temp.uid);

			// Marcador de tempo de inicio do tempo gasto para atualização do display
			//	Calendar hoje = Calendar.getInstance();
			//	System.out.println("Mandando mensagem com timestamp "+Temp.LVT+" em: "+hoje.getTimeInMillis());
				App.NewGateway.Redirect(getProtocolConverterID(Temp.uid));
				if (App.FederateType.compareTo("notime") == 0) updateLVT(String.valueOf(lessTimestamp));
			}
			else { 	break; }
		}
		// Ao enviar a mens ao federado, o timestamp (Msg.LVT) da mens pode passar
		// a ser o novo LVT, se assim o federado desejar. O federado também pode
		// simplesmente armazenar o msg numa lista, e executar o evento mais tarde.
		// Se o federado precisar alterar novamente o LVT
		// de acordo com seu tempo interno (se possuir), ele poderá faze-lo através do método
		// Gateway.updateLVT(...), respeitando o GVT.
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public String getAttributeType (String uid) throws IOException
	{
		InputRegister InputRegisterTemp = null;

		for (int x=0; x < InputRegisterList.size() ; x++)
		{
			InputRegisterTemp = (InputRegister) InputRegisterList.get(x);
			if (uid.compareTo(String.valueOf(InputRegisterTemp.uid)) == 0)
				break;
			else
				InputRegisterTemp = null;
		}
		return InputRegisterTemp.type;
	}


	/////////////////////////////////////////////////////////////////////////////////////
	public int getProtocolConverterID(String uid)
	{
		String pc_id="";
		char tmp = ' ';

		for (int i=0; i < uid.length(); i++)
		{
			tmp = uid.charAt(i);
			if (tmp != '.')
				pc_id = pc_id + tmp;
			else
				break;
		}
		return Integer.parseInt(pc_id);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public InputAttribute getAttributeReceived(String uid)
	{
		InputAttribute Temp = null;

		for (int x = 0; x < InputAttributeQueue.size(); x++)
		{
			Temp = (InputAttribute) InputAttributeQueue.get(x);
			if (uid.compareTo(Temp.uid) == 0 && Integer.parseInt(Temp.LVT) == lessTimestamp) {
				// procura por msg's na lista de espera que tenham timestamp == ao LVT atual
				// se houver, elas são enviadas ao federado
				break;
			}
			else
				Temp = null;
		}

		return Temp;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public InputAttribute getAttributeReceived(String uid, String Source, String LVT)
	{
		InputAttribute Temp = null;

		for (int x = 0; x < InputAttributeQueue.size(); x++)
		{
			Temp = (InputAttribute) InputAttributeQueue.get(x);
			if (uid.compareTo(Temp.uid) == 0 && Source.compareTo(Temp.Source)== 0 && LVT.compareTo(Temp.LVT) == 0)
				break;
			else
				Temp = null;
		}
		return Temp;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void AttributeRemove (InputAttribute AttribRemove)
	{
		for (int x = 0; x < InputAttributeQueue.size(); x++)
			if (AttribRemove == (InputAttributeQueue.get(x)))
			{
				InputAttributeQueue.remove(x);
				contElemIAQ--;  // controla o numero de msg's na lista de entrada
				break;
			}
	}
}