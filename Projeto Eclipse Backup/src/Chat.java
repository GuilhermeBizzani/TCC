/*
 * algoritmos probabilisticos de criaÁ„o de checkpoint
 * analise de escanolabilidade 
 * 
 * 
 * automaÁ„o do teste
 * referÍncia!
 * 
 * 
 * criar um projeto de implementaÁ„o do z-path
 */


/**
 *
 * @author  Fl√°vio Migowski
 */
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Chat extends javax.swing.JFrame {

    private int id;
    private String chatLVT = "0";
    private String chatLVTaux;
    private String lastEventTimestamp = "0";
    private int contadorDeMensagens = 0;
    private boolean initAtualizaLVT;
    public static boolean rollback = false;
    private ArrayList<String> checkpointsArray = new ArrayList<String>();
    
    
    
   
    
    /** Inicializa o chat com o id do Federado.
    @param id id do federado*/
    public Chat(int id) {
        this.id = id;
        //Seta o LookAndFeel para o GTK++
        try {
            //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.gtk.GTKLookAndFeel());
            UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
        } catch (Exception ex) {
        }

        this.setTitle("ChatDCB " + new Integer(id).toString());
        switch (id) {
            case 5: {
                chatLVT = Gateway5.updateLVT("0");
                AtualizaLVT AtLVT = new AtualizaLVT(this);
                Thread teste = new Thread(AtLVT);
                teste.start();
                break;
            }
        }
        initComponents();
        //Marca o tipo do federado (notime, synchronous ou asynchronous)
        switch (id) {
            case 5: {
                if (Gateway5.App.FederateType.compareTo("notime") == 0) {
                    untimedButton.setSelected(true);
                } else if (Gateway5.App.FederateType.compareTo("synchronous") == 0) {
                    synchronousTimeButton.setSelected(true);
                } else if (Gateway5.App.FederateType.compareTo("asynchronous") == 0) {
                    assynchronousTimeButton.setSelected(true);
                }
                recipientComboBox.removeItemAt(0);

                break;
            }
            case 6: {
                if (Gateway6.App.FederateType.compareTo("notime") == 0) {
                    untimedButton.setSelected(true);
                } else if (Gateway6.App.FederateType.compareTo("synchronous") == 0) {
                    synchronousTimeButton.setSelected(true);
                } else if (Gateway6.App.FederateType.compareTo("asynchronous") == 0) {
                    assynchronousTimeButton.setSelected(true);
                }
                //CheckpointAnalyzer CpAn = new CheckpointAnalyzer(this);
                //Thread cpan = new Thread(CpAn);
                //cpan.start();
                this.setCheckpoint("0");
                recipientComboBox.removeItemAt(1);
                break;
            }
            case 7: {
                if (Gateway7.App.FederateType.compareTo("notime") == 0) {
                    untimedButton.setSelected(true);
                } else if (Gateway7.App.FederateType.compareTo("synchronous") == 0) {
                    synchronousTimeButton.setSelected(true);
                } else if (Gateway7.App.FederateType.compareTo("asynchronous") == 0) {
                    assynchronousTimeButton.setSelected(true);
                }
                //CheckpointAnalyzer CpAn = new CheckpointAnalyzer(this);
                //Thread cpan = new Thread(CpAn);
                //cpan.start();
                this.setCheckpoint("0");
                recipientComboBox.removeItemAt(2);
                break;
            }
            case 8: {
                if (Gateway8.App.FederateType.compareTo("notime") == 0) {
                    untimedButton.setSelected(true);
                } else if (Gateway8.App.FederateType.compareTo("synchronous") == 0) {
                    synchronousTimeButton.setSelected(true);
                } else if (Gateway8.App.FederateType.compareTo("asynchronous") == 0) {
                    assynchronousTimeButton.setSelected(true);
                }
                //CheckpointAnalyzer CpAn = new CheckpointAnalyzer(this);
                //Thread cpan = new Thread(CpAn);
                //cpan.start();
                this.setCheckpoint("0");
                recipientComboBox.removeItemAt(3);
                break;
            }
            case 9: {
                if (Gateway9.App.FederateType.compareTo("notime") == 0) {
                    untimedButton.setSelected(true);
                } else if (Gateway9.App.FederateType.compareTo("synchronous") == 0) {
                    synchronousTimeButton.setSelected(true);
                } else if (Gateway9.App.FederateType.compareTo("asynchronous") == 0) {
                    assynchronousTimeButton.setSelected(true);
                }
                //CheckpointAnalyzer CpAn = new CheckpointAnalyzer(this);
                //Thread cpan = new Thread(CpAn);
                //cpan.start();
                this.setCheckpoint("0");
                recipientComboBox.removeItemAt(4);
                break;
            }
        }
        timeStampText.setText("0");
    }

    /**Edita o texto no quadro de mensagens recebidas.
     * @param value √© o valor enviado na mensagem, que √© editado e vai para a tela.
     */
    public void setReceivedText(String value) {
        lastEventTimestamp = chatLVT;

        switch (id) {
            case 5: {
                this.receivedText.setText(receivedText.getText() + value + "\n");
                break;
            }
            case 6: {
                if (!initAtualizaLVT) {
                    initAtualizaLVT = true;
                    chatLVT = Gateway6.updateLVT("0");
                    AtualizaLVT AtLVT = new AtualizaLVT(this);
                    Thread teste = new Thread(AtLVT);
                    teste.start();
                    break;
                }
                /*
                this.qt6++;
                int aux = Integer.parseInt(this.getLVT()) / this.qt6;
                System.out.println("Mensagem recebida do federado 6! previs„o: "+this.getLVT()+" + " + aux);
                */
                this.receivedText.setText(receivedText.getText() + value + "\n");
                break;
            }
            case 7: {
                if (!initAtualizaLVT) {
                    initAtualizaLVT = true;
                    chatLVT = Gateway7.updateLVT("0");
                    AtualizaLVT AtLVT = new AtualizaLVT(this);
                    Thread teste = new Thread(AtLVT);
                    teste.start();
                    break;
                }
                this.receivedText.setText(receivedText.getText() + value + "\n");
                break;
            }
            case 8: {
                if (!initAtualizaLVT) {
                    initAtualizaLVT = true;
                    chatLVT = Gateway8.updateLVT("0");
                    AtualizaLVT AtLVT = new AtualizaLVT(this);
                    Thread teste = new Thread(AtLVT);
                    teste.start();
                    break;
                }
                this.receivedText.setText(receivedText.getText() + value + "\n");
                break;
            }
            case 9: {
                if (!initAtualizaLVT) {
                    initAtualizaLVT = true;
                    chatLVT = Gateway9.updateLVT("0");
                    AtualizaLVT AtLVT = new AtualizaLVT(this);
                    Thread teste = new Thread(AtLVT);
                    teste.start();
                    break;
                }
                this.receivedText.setText(receivedText.getText() + value + "\n");
                break;
            }
        }

    }

    /**Retorna o id do federado.
     * @return id
     */
    public int getId() {
        return id;
    }

    public void incrementaContador() {
        contadorDeMensagens++;
    }

    /**Adiciona um checkpoint na lista de checkpoints
     *
     * @param timestamp
     */
    public void setCheckpoint(String timestamp) {
        System.out.println("checkpoint criado: " + timestamp);
        checkpointsArray.add(timestamp);
    }

    /**Pega o checkpoint necess√°rio para poder entregar a mensagem no tempo passado
     *
     * @param timestamp
     * @return checkpoint
     */
    public String getCheckpoint(String timestamp) {
        Iterator<String> it = checkpointsArray.iterator();
        ArrayList<String> listaCheckpointsRemocao = new ArrayList<String>();
        String auxiliar, checkpoint = null;
        while (it.hasNext()) {
            auxiliar = it.next();
            if (Integer.valueOf(timestamp) < Integer.valueOf(auxiliar)) { //modificado
                listaCheckpointsRemocao.add(auxiliar);
                System.out.println("Adicionou na lista de exclus„o o checkpoint:"+auxiliar);
            } else {
            	System.out.println("Atualizando checkpoint para retornar:"+auxiliar);
                checkpoint = auxiliar;
                
            }
        }
        if (checkpoint != null) {
            for (String aux : listaCheckpointsRemocao) {
                checkpointsArray.remove(aux);
            }
        }
        System.out.println("checkpoint do rollback: " + checkpoint);
        return checkpoint;
    }

    /**Retorna o lvt do federado.
     * @return chatLVT
     */
    public String getLVT() {
        return chatLVT;
    }

    /**Retorna o texto na √≠ntegra das mensagens recebidas
     * @return receivedText.getText()
     */
    public String getReceivedText() {
        return this.receivedText.getText();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        receivedLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        receivedText = new javax.swing.JTextArea();
        messageLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();
        recipientLabel = new javax.swing.JLabel();
        timeStampLabel = new javax.swing.JLabel();
        timeStampText = new javax.swing.JTextField();
        gvtLabel = new javax.swing.JLabel();
        lvtLabel = new javax.swing.JLabel();
        untimedButton = new javax.swing.JRadioButton();
        synchronousTimeButton = new javax.swing.JRadioButton();
        assynchronousTimeButton = new javax.swing.JRadioButton();
        sendButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        recipientComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        receivedLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        receivedLabel.setText("Received");

        receivedText.setColumns(20);
        receivedText.setEditable(false);
        receivedText.setRows(5);
        jScrollPane1.setViewportView(receivedText);

        messageLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        messageLabel.setText("Message");

        message.setColumns(20);
        message.setRows(5);
        jScrollPane2.setViewportView(message);

        recipientLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        recipientLabel.setText("Recipient");

        timeStampLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 12));
        timeStampLabel.setText("TimeStamp");

        gvtLabel.setText("GVT:");

        lvtLabel.setText("LVT:");

        untimedButton.setText("Untimed");
        untimedButton.setEnabled(false);

        synchronousTimeButton.setText("Synchronous");
        synchronousTimeButton.setEnabled(false);

        assynchronousTimeButton.setText("Asynchronous");
        assynchronousTimeButton.setEnabled(false);

        sendButton.setText("Send");
        sendButton.setPreferredSize(new java.awt.Dimension(60, 30));
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        closeButton.setText("Close");
        closeButton.setPreferredSize(new java.awt.Dimension(60, 30));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        recipientComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ChatDCB 5 - Synchronous", "ChatDCB 6 - Asynchronous", "ChatDCB 7 - Asynchronous", "ChatDCB 8 - Asynchronous", "ChatDCB 9 - No Time" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(timeStampLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(receivedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(recipientLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(recipientComboBox, 0, 262, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(closeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                    .addComponent(timeStampText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(synchronousTimeButton)
                    .addComponent(gvtLabel)
                    .addComponent(untimedButton)
                    .addComponent(lvtLabel)
                    .addComponent(assynchronousTimeButton))
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(receivedLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                    .addComponent(messageLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(recipientLabel)
                    .addComponent(recipientComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeStampLabel)
                    .addComponent(timeStampText))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(gvtLabel)
                .addGap(18, 18, 18)
                .addComponent(lvtLabel)
                .addGap(18, 18, 18)
                .addComponent(untimedButton)
                .addGap(12, 12, 12)
                .addComponent(synchronousTimeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(assynchronousTimeButton)
                .addContainerGap(215, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
    int opcao = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?", "Fechar ChatDCB", JOptionPane.YES_NO_OPTION);
    if (opcao == 0) {
        System.exit(0);
    }
}//GEN-LAST:event_closeButtonActionPerformed

private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
    try {
        Integer.valueOf(recipientComboBox.getSelectedIndex() + 4);
        try {
            Integer.valueOf(timeStampText.getText());
            switch (id) {
                case 5: {
                    Gateway5.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText(), timeStampText.getText());
                    break;
                }
                case 6: {
                    Gateway6.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText(), timeStampText.getText());
                    break;
                }
                case 7: {
                    Gateway7.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText(), timeStampText.getText());
                    break;
                }
                case 8: {
                    Gateway8.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText(), timeStampText.getText());
                    break;
                }
                case 9: {
                    Gateway9.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText(), timeStampText.getText());
                    break;
                }
            }
            message.setText("");
            lastEventTimestamp = chatLVT;
            this.incrementaContador();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "O valor do Timestamp deve ser um n√∫mero inteiro.");
            e.printStackTrace();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "O valor do Recipient deve ser um n√∫mero inteiro.");
    }
}//GEN-LAST:event_sendButtonActionPerformed

public void setChatLVT(String chatLVT){
    this.chatLVT = chatLVT;
    this.chatLVTaux = chatLVT;
}

    public void rollback(String Momento) {
        PriorityQueue<Message> Lista = null;
        PriorityQueue<Message> ListaParaRemocao = new PriorityQueue<Message>(1, new Message());
        switch (id) {
            case 5: {
                //Federado 5 n√£o realiza rollback;
                //Lista = Gateway5.App.NewEF.BufferReceivedMessages;
                //ListaR = Gateway5.App.NewEDCB.BufferSentMessages;
                break;
            }
            case 6: {
                Lista = Gateway6.App.NewEF.BufferReceivedMessages;
                break;
            }
            case 7: {
                Lista = Gateway7.App.NewEF.BufferReceivedMessages;
                break;
            }
            case 8: {
                Lista = Gateway8.App.NewEF.BufferReceivedMessages;
                break;
            }
            case 9: {
                Lista = Gateway9.App.NewEF.BufferReceivedMessages;
                break;
            }
        }
        String todasMensagens = "";
        Iterator<Message> it = Lista.iterator();
        if (it.hasNext()) {
            Message s = it.next();
            while (s != null) {
                //System.out.println("s.LVT: "+s.LVT +" Momento: "+ Momento);
                //Verifica quais mensagens devem ser deletadas
                if (Integer.valueOf(s.LVT) > Integer.valueOf(Momento)) {
                    todasMensagens += s.Value+"\n"; // comeco a acrescentar o \n para se assemelhar com a mensagem que est· na tela.
                    System.out.println("mensagem a ser apagada devido ao rollback: " + s.Value);
                    ListaParaRemocao.add(s);
                }
                if (it.hasNext()) {
                    s = it.next();
                } else {
                    s = null;
                }
            }
        }

        //Remo√ß√£o das mensagens perdidas
        if (ListaParaRemocao.size() > 0) {
            for (Message m : ListaParaRemocao) {
                switch (id) {
                    case 5: {
                        //Federado 5 n√£o realiza rollback;
                        //Gateway5.App.NewEF.BufferReceivedMessages.remove(m);
                        break;
                    }
                    case 6: {
                        Gateway6.App.NewEF.BufferReceivedMessages.remove(m);
                        break;
                    }
                    case 7: {
                        Gateway7.App.NewEF.BufferReceivedMessages.remove(m);
                        break;
                    }
                    case 8: {
                        Gateway8.App.NewEF.BufferReceivedMessages.remove(m);
                        break;
                    }
                    case 9: {
                        Gateway9.App.NewEF.BufferReceivedMessages.remove(m);
                        break;
                    }
                }
            }
        }

        //Recuar LVT para o Momento no Rollback;
        switch (id) {
            case 5: {
                //Federado 5 n√£o realiza rollback;
                break;
            }
            case 6: {
                System.out.println("LVT no Rollback: " + Gateway6.updateLVT(Momento));
                break;
            }
            case 7: {
                System.out.println("LVT no Rollback: " + Gateway7.updateLVT(Momento));
                break;
            }
            case 8: {
                System.out.println("LVT no Rollback: " + Gateway8.updateLVT(Momento));
                break;
            }
            case 9: {
                System.out.println("LVT no Rollback: " + Gateway9.updateLVT(Momento));
                break;
            }
        }
        this.chatLVT = Momento;
        this.chatLVTaux = Momento;
        if (todasMensagens.compareTo("") != 0) {
        	/*
        	 * alteraÁıes: J· que as mensagens a serem removidas est„o vindo com \n para ficarem igual as que est„o na tela,
        	 * a String todasMensagens tambÈm contÈm as mernsagens "de tr·s pra frente" em ordem de envio.
        	 * pego a ˙ltima do split, que na verdade È a primeira que deve ser removida da tela
        	 * e reescrevo o "receivedText" da posiÁ„o 0 atÈ o inicio da mensagem a ser apagada.
        	 */
            System.out.println("Mensagens para serem retiradas: " + todasMensagens);
            String[] teste = todasMensagens.split("\n");
            String teste2 = teste[0]; //primeira posicao das mensagens a serem removidas, para apagar a partir deste.
            System.out.println("Teste2:"+teste2);
            System.out.println("Mensagem do setText:"+ this.getReceivedText());
            int posicao = this.getReceivedText().lastIndexOf(teste2);
            System.out.println("Posicaoo na substring: "+posicao);
            if (posicao > 0) {
                this.receivedText.setText(this.getReceivedText().substring(0, posicao));
            } else {
                this.receivedText.setText("");
            }

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton assynchronousTimeButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel gvtLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lvtLabel;
    private javax.swing.JTextArea message;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel receivedLabel;
    private javax.swing.JTextArea receivedText;
    private javax.swing.JComboBox recipientComboBox;
    private javax.swing.JLabel recipientLabel;
    private javax.swing.JButton sendButton;
    private javax.swing.JRadioButton synchronousTimeButton;
    private javax.swing.JLabel timeStampLabel;
    private javax.swing.JTextField timeStampText;
    private javax.swing.JRadioButton untimedButton;
    // End of variables declaration//GEN-END:variables

    /**Classe privada AtualizaLVT.
     * √â uma Thread para atualizar o GVT (advindo do fedgvt) na tela e o LVT do federado
     */
    private class AtualizaLVT implements Runnable {

        private Chat outerClass;
        private Integer aux;
        private Integer auxGVT;

        public AtualizaLVT(Chat pointer) {
            outerClass = pointer;
        }

        @Override
        public void run() {
            chatLVTaux = chatLVT;
            if (outerClass.getId() == 5) {
                //Sincroniza√ß√£o
                Gateway5.UpdateAttribute("1.6", "", "0");
                Gateway5.UpdateAttribute("1.7", "", "0");
                Gateway5.UpdateAttribute("1.8", "", "0");
                Gateway5.UpdateAttribute("1.9", "", "0");
            }
            while (rollback == false) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                switch (outerClass.id) {
                    case 5: {
                        auxGVT = Integer.valueOf(Gateway5.returnGVT());
                        outerClass.gvtLabel.setText("GVT: " + auxGVT);
                        break;
                    }
                    case 6: {
                        auxGVT = Integer.valueOf(Gateway6.returnGVT());
                        outerClass.gvtLabel.setText("GVT: " + auxGVT);
                        break;
                    }
                    case 7: {
                        auxGVT = Integer.valueOf(Gateway7.returnGVT());
                        outerClass.gvtLabel.setText("GVT: " + auxGVT);
                        break;
                    }
                    case 8: {
                        auxGVT = Integer.valueOf(Gateway8.returnGVT());
                        outerClass.gvtLabel.setText("GVT: " + auxGVT);
                        break;
                    }
                    case 9: {
                        auxGVT = Integer.valueOf(Gateway9.returnGVT());
                        outerClass.gvtLabel.setText("GVT: " + auxGVT);
                        break;
                    }
                }
                outerClass.lvtLabel.setText("LVT: " + chatLVT);
                aux = new Integer(chatLVT);
                //lookahead de 100
                aux = aux + 100;
                while (chatLVT.compareTo(chatLVTaux) == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    switch (id) {
                        case 5: {
                            //aux -= 99;
                            chatLVT = Gateway5.updateLVT(aux.toString());
                            break;
                        }
                        case 6: {
                            //aux+=100;
                            chatLVT = Gateway6.updateLVT(aux.toString());
                            break;
                        }
                        case 7: {
                            //aux+=100;
                            chatLVT = Gateway7.updateLVT(aux.toString());
                            break;
                        }
                        case 8: {
                            //aux+=100;
                            chatLVT = Gateway8.updateLVT(aux.toString());
                            break;
                        }
                        case 9: {
                            chatLVT = Gateway9.updateLVT(aux.toString());
                            break;
                        }
                    }
                }
                chatLVTaux = chatLVT;
            }
        }
    }

  /*  private class CheckpointAnalyzer implements Runnable {

        private Chat outerClass;
        private Integer tempo,  provavelCheckpoint;

        public CheckpointAnalyzer(Chat pointer) {
            outerClass = pointer;
        }

        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                //Se a quantidade de mensagens trocadas for consider√°vel, maior que 10
                if (outerClass.contadorDeMensagens > 10) {
                    outerClass.setCheckpoint(outerClass.chatLVT);
                }

                tempo = Integer.valueOf(outerClass.chatLVT) - Integer.valueOf(outerClass.lastEventTimestamp);
                provavelCheckpoint = Integer.valueOf(outerClass.lastEventTimestamp);
                provavelCheckpoint += 10;
                //Se passou mais de 5000 desde o √∫ltimo evento e ainda n√£o tem checkpoint
                if ((tempo > 5000) && !(outerClass.checkpointsArray.contains(provavelCheckpoint.toString()))) {
                    System.out.println("LastEventTimestamp: " + lastEventTimestamp);
                    outerClass.setCheckpoint(provavelCheckpoint.toString());

                }
            }
        }
    }*/
}
