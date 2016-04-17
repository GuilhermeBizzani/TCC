import java.util.*;
import java.io.*;
import javax.swing.*;

// Fedgvt

public class Fedgvt {

   	int num, lessLVT, localLVT = 0;
   	int newGVT, actualGVT = 0;
   	public ArrayList listlvt = new ArrayList();
   	Storelvt newLVT;
   	int lookahead = 100;
   	private String varFedgvt;

   	public Fedgvt ()
   	{
	   System.out.println("FEDGVT Inicializado...");
	   //varFedgvt = JOptionPane.showInputDialog  // Caixa de dialogo
	   //	     ("Insira um valor inteiro para lookahead:\n");
	   //if (Integer.parseInt(varFedgvt) > 0) lookahead = Integer.parseInt(varFedgvt);

   	}

   	public void calcGVT(String remoteTime, String origem)   // recebe LVT remoto pela String remoteTime
   	{

	    	// Calcula GVT sincrono e atualiza a federação

		seekLVT(remoteTime,origem);
		newGVT = updateGVT(remoteTime);
		newGVT = newGVT + lookahead;  // No GVT sincrono, adiciona valor do lookahead

		//System.out.println("Recebeu 1 msg...");

		if (newGVT != actualGVT) // Rever! Neste caso, se newGVT for menor, também atualiza. Isso é desejado?
	    	{
			actualGVT = newGVT;
			Gateway444.UpdateAttribute ("444.3",actualGVT,String.valueOf(actualGVT));
			// System.out.println("Enviou 4 msgs...");
			// Broadcast do novo GVT para demais federados
			// No config.xml do Fedgvt deve existir uma tag <DESTINATION para cada federado da federação
		}

	    System.out.println("  GVT atual: " + actualGVT +
	   			"  Federation: " + origem.substring(0,1) +
	   			"  Federate: " + origem.substring(1));
	}

	public boolean seekLVT(String remoteTime, String origem)
	{
		Storelvt Temp = null;

		for (int x = 0; x < listlvt.size(); x++)
		{
			Temp = (Storelvt) listlvt.get(x);
			if ((origem.substring(0,1).compareTo(Temp.returnFederation()) == 0) &
					(origem.substring(1).compareTo(Temp.returnFederate()) == 0))
			{
				// Faz advertencia de que LVT de federado retrocedeu no tempo
				//if (Integer.parseInt(remoteTime) < Integer.parseInt(Temp.returnlastLVT()))
				//	System.out.println("Warning: LVT de origem:" + origem + " retrocedeu!");
				// atualiza valor de LVT de federado que já consta na lista
				Temp.updateLVT(remoteTime);
				return (true);
			}
		}
		// Inclui Federado novo que ainda não tinha registro na lista de LVT's
		newLVT = new Storelvt(remoteTime,origem.substring(0,1),
 	   									origem.substring(1));
   	   	listlvt.add(newLVT);
   	   	return (true);
   	}

   	public int updateGVT(String remoteTime)  // Realiza busca na lista de LVT's e atualiza GVT
   	{
		Storelvt Temp = null;
		lessLVT = Integer.parseInt(remoteTime);
		for (int x = 0; x < listlvt.size(); x++)
		{
			Temp = (Storelvt) listlvt.get(x);
			if (Integer.parseInt(Temp.returnlastLVT()) < lessLVT)
				lessLVT = Integer.parseInt(Temp.returnlastLVT());
			//System.out.println(Temp.returnFederate() + " " + Temp.returnlastLVT());
		}
		return lessLVT;
	}

public static void main(String args[])  { new Fedgvt(); }

} // end of Fedgvt class

