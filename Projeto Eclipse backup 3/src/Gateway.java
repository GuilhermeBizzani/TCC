
import java.io.*;
/////////////////////////////// INICIO CLASSE GATEWAY ///////////////////////////////////////

public class Gateway {

    public ApplicationDCB App;
    public int gVal;

    /////////////////////////////////////////////////////////////////////////////////////
    public Gateway(ApplicationDCB pointer) {
        App = pointer;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // Esse metodo inicializa o Gateway
    // Recebe como parametro um indice para o Gateway (agora cada federado possui o seu...)
    // Armazena esse ponteiro no gVal, que ser� sempre utilizado pelo ProtocolConverter
    /////////////////////////////////////////////////////////////////////////////////////
    public void Start(int gatewayVal) {
        gVal = gatewayVal;
        switch (gVal) {
            case 5: {
                Gateway5.SetPointer(App);
                break;
            }
            case 6: {
                Gateway6.SetPointer(App);
                break;
            }
            case 7: {
                Gateway7.SetPointer(App);
                break;
            }
            case 8: {
                Gateway8.SetPointer(App);
                break;
            }
            case 9: {
                Gateway9.SetPointer(App);
                break;
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // Esse metodo somente redireciona a chamada originada do EF para o Gateway
    // do respectivo federado ...
    /////////////////////////////////////////////////////////////////////////////////////
    public void Redirect(int AttributeID) {
        switch (gVal) {
            case 5: {
                System.out.println("Redirecionou 5 para: " + AttributeID);
                Gateway5.ProtocolConverter(AttributeID);
                break;
            }
            case 6: {
                System.out.println("Redirecionou 6 para: " + AttributeID);
                Gateway6.ProtocolConverter(AttributeID);
                break;
            }
            case 7: {
                System.out.println("Redirecionou 7 para: " + AttributeID);
                Gateway7.ProtocolConverter(AttributeID);
                break;
            }
            case 8: {
                System.out.println("Redirecionou 8 para: " + AttributeID);
                Gateway8.ProtocolConverter(AttributeID);
                break;
            }
            case 9: {
                System.out.println("Redirecionou 9 para: " + AttributeID);
                Gateway9.ProtocolConverter(AttributeID);
                break;
            }
        }
    }
}
