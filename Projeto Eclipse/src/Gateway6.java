/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Flávio Migowski
 */
public class Gateway6 {

    public static ApplicationDCB App;
    public static Chat Fed;

    //Construtor vazio
    public Gateway6() {
    }

    public static void SetPointer(ApplicationDCB pointer) {
        App = pointer;

        System.out.println("Inicializando ChatWindow 6...");
        Fed = new Chat(6);
        Fed.setVisible(true);
    }
    
    //criacao:
    
    public static void setCheckpoint(String timestamp){
    	Fed.setCheckpoint(timestamp);
    }
    
    //fim
    

    ///////////////////////////////////////////////////////////////////////////
    // Métodos de tratamento do tempo para modo conservador (síncrono)
    ///////////////////////////////////////////////////////////////////////////
    public synchronized static String updateLVT(String federateLVT) // Recebe o LVT do federado e solicita atualização ao EF.
    {
        return (App.NewEF.updateLVT(federateLVT));
    }

    public static String returnGVT() // monitora o avanço do LVT do federado para não ultrapassar o GVT
    {
        return (App.NewDCB.getGVT());
    }

    public static void ProtocolConverter(int AttributeID) {
        InputAttribute A0 = null;
        String checkpoint;
        
        switch (AttributeID) {
            case 2: {
                A0 = App.NewEF.getAttributeReceived("2.0");
                if (A0 != null) {
                    App.NewEF.AttributeRemove(A0);
                    Fed.setReceivedText(A0.Value);
                    Fed.incrementaContador();
                }
                break;
            }
            //criacao:
            case 3: {
            	System.out.println("entrou no case 3!");
            	Fed.setCheckpoint(App.NewEF.getLVT());
            	break;
            }
            //fim
            case 444: {
                A0 = App.NewEF.getAttributeReceived("444.3");
                if (A0 != null) {
                    App.NewEF.AttributeRemove(A0);
                    Fed.rollback = true;
                    checkpoint = Fed.getCheckpoint(A0.LVT);
                    if ( checkpoint != null) {
                    	System.out.println("antimsg:");
                        App.NewEDCB.antiMessageTrigger(checkpoint);
                        System.out.println("chama o rollback:");
                        Fed.rollback(checkpoint);
                        //Fed.setChatLVT(updateLVT(A0.LVT));
                        System.out.println("update lvt:");
                        Fed.setChatLVT(updateLVT(checkpoint));
                        System.out.println("setreceivedText:");
                        Fed.setReceivedText(A0.Value);
                        Fed.rollback = false;
                        System.out.println("fim");
                    }
                }
                break;
            }
        }
    }

    //////////////////////////////// INICIO TRADUTOR ////////////////////////////////////////
    public static long ToLong(String value) {
        return Long.parseLong(value);
    }

    public static double ToDouble(String value) {
        return Double.parseDouble(value);
    }

    public static float ToFloat(String value) {
        return Float.parseFloat(value);
    }

    public static int ToInt(String value) {
        return Integer.parseInt(value);
    }

    public static char ToChar(String value) {
        return value.charAt(0);
    }

    public static char[] ToCharArray(String value) {
        return value.toCharArray();
    }

    public static boolean ToBoolean(String value) {
        boolean bool = false;
        String val = value.toUpperCase();

        if (val.compareTo("TRUE") == 0) {
            bool = true;
        }

        return bool;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    public static void UpdateAttribute(String Name, String Value, String timestamp) {
        App.NewEDCB.Update(Name, Value, timestamp);
    }

    public static void UpdateAttribute(String Name, int Value, String timestamp) {
        App.NewEDCB.Update(Name, String.valueOf(Value), timestamp);
    }

    public void UpdateAttribute(String Name, boolean Value, String timestamp) {
        App.NewEDCB.Update(Name, String.valueOf(Value), timestamp);
    }

    public void UpdateAttribute(String Name, float Value, String timestamp) {
        App.NewEDCB.Update(Name, String.valueOf(Value), timestamp);
    }

    public void UpdateAttribute(String Name, double Value, String timestamp) {
        App.NewEDCB.Update(Name, String.valueOf(Value), timestamp);
    }

    public void UpdateAttribute(String Name, long Value, String timestamp) {
        App.NewEDCB.Update(Name, String.valueOf(Value), timestamp);
    }

    public void UpdateAttribute(String Name, char Value, String timestamp) {
        App.NewEDCB.Update(Name, String.valueOf(Value), timestamp);
    }

    public void UpdateAttribute(String Name, char[] Value, String timestamp) {
        App.NewEDCB.Update(Name, String.valueOf(Value), timestamp);
    }
}
