public class Storelvt {

	private String federation;
	private String federate;
   	private String lastLVT;

	public Storelvt (String newLVT, String fedon, String fedte)
	{
	    this.lastLVT = newLVT;
  	    this.federation = fedon;
	    this.federate = fedte;
    }

    public String returnFederation()
    {
		return (this.federation);
	}

	public String returnFederate()
	{
		return (this.federate);
	}

	public String returnlastLVT()
	{
		return (this.lastLVT);
	}

	public void updateLVT(String remoteLVT)
	{
		this.lastLVT = remoteLVT;
	}
}

