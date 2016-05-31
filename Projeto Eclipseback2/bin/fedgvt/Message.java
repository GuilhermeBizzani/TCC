import java.io.*;

///////////////////////////////// INICIO CLASSE MESSAGE /////////////////////////////////////		
		
	public class Message implements Serializable
	{
		public String FederationSource;
		public String FederateSource;
		public String FederationDestination;
		public String FederateDestination;
		public String AttributeID;
		public String Value;
		public String LVT;
		public String OwnerFederate;
		public String NextOwnerFederate;
		
		public Message (String Operation, String FederationSource,String FederateSource,String FederationDestination,String FederateDestination,String AttributeID, String Value,String LVT,String OwnerFederate,String NextOwnerFederate)
		{
			this.FederationSource = FederationSource;
			this.FederateSource = FederateSource;
			this.FederationDestination = FederationDestination;
			this.FederateDestination = FederateDestination;
			this.AttributeID = AttributeID;
			this.Value = Value;
			this.LVT = LVT;
			this.OwnerFederate = OwnerFederate;
			this.NextOwnerFederate = NextOwnerFederate;
		}
	}
		
//////////////////////////////// FIM CLASSE MESSAGE /////////////////////////////////////////
	