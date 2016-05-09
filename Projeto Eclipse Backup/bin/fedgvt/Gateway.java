import java.io.*;
/////////////////////////////// INICIO CLASSE GATEWAY ///////////////////////////////////////

public class Gateway
{
	public ApplicationDCB App;
	public int gVal;

	/////////////////////////////////////////////////////////////////////////////////////
	public Gateway(ApplicationDCB pointer)
	{
	   App = pointer;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// Esse metodo inicializa o Gateway
	// Recebe como parametro um indice para o Gateway (agora cada federado possui o seu...)
	// Armazena esse ponteiro no gVal, que será sempre utilizado pelo ProtocolConverter
	/////////////////////////////////////////////////////////////////////////////////////
	public void Start(int gatewayVal)
	{
		gVal= gatewayVal;

		switch (gVal)
		{
			case 444: // fedgvt
			{
				Gateway444.SetPointer(App);
				break;
			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// Esse metodo somente redireciona a chamada originada do EF para o Gateway
	// do respectivo federado ...
	/////////////////////////////////////////////////////////////////////////////////////
	public void Redirect(int AttributeID)
	{
	 	switch (gVal)
		{
			case 444:
			{
				System.out.println("Redirecionou para o FedGvt");
				Gateway444.ProtocolConverter(AttributeID);
				break;
			}

		}
	}
}
