/*
 * algoritmos probabilisticos de criação de checkpoint
 * analise de escanolabilidade 
 * 
 * automação do teste
 * referência!
 * 
 * 
 * INFERÊNCIA PROBABILISTICA
 * PROBABILIDADE CONJUNTIVA
 * 
 * refinar a automação
 * ACHAR O ARTIGO!
 * re-executar as mensagens depois de rollback
 * 
 * 
 * -----criar um projeto de implementação do z-path
 * 
 * 
 * verificar maneira para não excluir a mensagem que gera o rollback
 * 
 * 
 * "sempre gera anti-mensagem" = EDCB linha 55~
 * 
 * 
 * citar o artigo que fala sobre o ÍNDICE de checkpoint
 * não é 100%, mas aumentou a chance do estado ser seguro
 * 
 * citar outros artigos que implementam outras técnicas que são "melhores"
 * -explicar porque não deu para desenvolver essas técnicas melhores-
 * 
 * 
 * 
 * 10/05/2016
 * 
 * contar o tempo gasto em cada rollback ( (tempo atual no rollback) - checkpoint utilizado)
 * contar o total deste tempo em toda a simulação
 * 
 * contar quantos checkpoints foram inúteis ao realizar rollback
 * 
 * contar quantas anti-mensagens foram geradas com o rollback
 * 
 * tempo total simulado
 * 
 * 
 * 
 */


/**
 *
 * @author  FlÃ¡vio Migowski
 */
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import jdk.management.resource.internal.TotalResourceContext;

public class Chat extends javax.swing.JFrame {

    private int id;
    private String chatLVT = "0";
    private String chatLVTaux;
    private String lastEventTimestamp = "0";
    private int contadorDeMensagens = 0;
    private boolean initAtualizaLVT;
    public static boolean rollback = false;
    private ArrayList<String> checkpointsArray = new ArrayList<String>();
    
    public int CkpIndex;
    
    public int totalTempoRollback;
    
    public int totalCheckpointsInuteis; 
    
    public int totalRollbacks;
    
    public int mensagensChat5[];
    
    
    /** Inicializa o chat com o id do Federado.
    @param id id do federado*/
    public Chat(int id) {
        this.id = id;
        
        //inicia a variavel CkpIndex com 0
        CkpIndex = 0;
        
        //inicia a variavel de total de tempo de rollback e total de checkpoints inuteis em 0
        totalTempoRollback = 0;
        totalCheckpointsInuteis = 0;
        totalRollbacks = 0;
        
        
        //Seta o LookAndFeel para o GTK++
        try {
            //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.gtk.GTKLookAndFeel()); ALTERAÇÃO PARA FUNCIONAR NO WINDOWS.
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
        MensagensAutomaticas ChatTest = new MensagensAutomaticas(this);
        Thread chatT = new Thread(ChatTest);
        chatT.start();
        timeStampText.setText("0");
    }

    /**Edita o texto no quadro de mensagens recebidas.
     * @param value Ã© o valor enviado na mensagem, que Ã© editado e vai para a tela.
     */
    public void setReceivedText(String value) {
        lastEventTimestamp = chatLVT;
        
        
        //alteração para criar um checkpoint caso a mensagem recebida contenha o checkpoint INDEX maior do que o atual.
        if(id != 5){
        	
        	String[] splt = value.split("®");
        	
        	if(splt.length == 2){
        		String indexReceived = splt[1];
        		System.out.println("Recebeu o Index: "+indexReceived);
        		System.out.println("Index atual: "+CkpIndex);
        		int indexReceivedINT = Integer.parseInt(indexReceived);
        		if(indexReceivedINT > CkpIndex){
        			System.out.println("Criando checkpoint baseado no Index de mensagem recebida.");
        			this.setCheckpoint(this.chatLVT);
        			CkpIndex = indexReceivedINT;
        		}
        		
        	}
        }
        
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
                System.out.println("Mensagem recebida do federado 6! previsão: "+this.getLVT()+" + " + aux);
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
        CkpIndex++;
    }

    /**Pega o checkpoint necessÃ¡rio para poder entregar a mensagem no tempo passado
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
                totalCheckpointsInuteis ++;
                if(CkpIndex < 1) CkpIndex--;
                //System.out.println("Adicionou na lista de exclusão o checkpoint:"+auxiliar);
            } else {
            	//System.out.println("Atualizando checkpoint para retornar:"+auxiliar);
                checkpoint = auxiliar;
                
            }
        }
        if (checkpoint != null) {
            for (String aux : listaCheckpointsRemocao) {
                checkpointsArray.remove(aux);
            }
        }
        System.out.println("Total de Checkpoints Inuteis: "+totalCheckpointsInuteis);
        return checkpoint;
    }

    /**Retorna o lvt do federado.
     * @return chatLVT
     */
    public String getLVT() {
        return chatLVT;
    }

    /**Retorna o texto na Ã­ntegra das mensagens recebidas
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
	        //System.out.println("sem a soma:"+recipientComboBox.getSelectedIndex());
	        try {
	            Integer.valueOf(timeStampText.getText());
	            switch (id) {
	                case 5: {
	                    Gateway5.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText()+"®"+CkpIndex, timeStampText.getText());
	                    break;
	                }
	                case 6: {
	                    Gateway6.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText()+"®"+CkpIndex, timeStampText.getText());
	                    break;
	                }
	                case 7: {
	                    Gateway7.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText()+"®"+CkpIndex, timeStampText.getText());
	                    break;
	                }
	                case 8: {
	                    Gateway8.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText()+"®"+CkpIndex, timeStampText.getText());
	                    break;
	                }
	                case 9: {
	                    Gateway9.UpdateAttribute("1." + ((String) recipientComboBox.getSelectedItem()).split(" ")[1], message.getText()+"®"+CkpIndex, timeStampText.getText());
	                    break;
	                }
	            }
	            message.setText("");
	            lastEventTimestamp = chatLVT;
	            this.incrementaContador();
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "O valor do Timestamp deve ser um nÃºmero inteiro.");
	            e.printStackTrace();
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "O valor do Recipient deve ser um nÃºmero inteiro.");
	    }
	}//GEN-LAST:event_sendButtonActionPerformed
	
	public void setChatLVT(String chatLVT){
	    this.chatLVT = chatLVT;
	    this.chatLVTaux = chatLVT;
	    
	    
	    
	    //System.out.println("Atualizou o LVT para: "+chatLVT);
	}

    public void rollback(String Momento) {
    	
    	int tempoRollback = 0;
    	
    	tempoRollback = Integer.parseInt(chatLVT) - Integer.parseInt(Momento);
    	
    	
    	totalTempoRollback += tempoRollback;
    	
    	totalRollbacks++;
    	
    	System.out.println("Rollback "+totalRollbacks+", LVT Atual: "+chatLVT+", LVT do rollback: "+Momento+" Somatorio de rollbacks: "+totalTempoRollback);
    	
        PriorityQueue<Message> Lista = null;
        PriorityQueue<Message> ListaParaRemocao = new PriorityQueue<Message>(1, new Message());
        switch (id) {
            case 5: {
                //Federado 5 nÃ£o realiza rollback;
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
            	//System.out.println("LVT da mensagem '"+s.Value+"' sendo testada"+s.LVT);
                if (Integer.valueOf(s.LVT) > Integer.valueOf(Momento)){  // && Integer.valueOf(s.LVT) != Integer.valueOf(Momento)) {
                	
                    todasMensagens += s.Value+"\n"; // comeco a acrescentar o \n para se assemelhar com a mensagem que está na tela.
                    //System.out.println("mensagem a ser apagada devido ao rollback: " + s.Value);
                    ListaParaRemocao.add(s);
                	
                }
                if (it.hasNext()) {
                    s = it.next();
                } else {
                    s = null;
                }
            }
        }

        //RemoÃ§Ã£o das mensagens perdidas
        if (ListaParaRemocao.size() > 0) {
            for (Message m : ListaParaRemocao) {
                switch (id) {
                    case 5: {
                        //Federado 5 nÃ£o realiza rollback;
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
                //Federado 5 nÃ£o realiza rollback;
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
        	 * alterações: Já que as mensagens a serem removidas estão vindo com \n para ficarem igual as que estão na tela,
        	 * a String todasMensagens também contém as mernsagens "de trás pra frente" em ordem de envio.
        	 * pego a última do split, que na verdade é a primeira que deve ser removida da tela
        	 * e reescrevo o "receivedText" da posição 0 até o inicio da mensagem a ser apagada.
        	 */
            //System.out.println("Mensagens para serem retiradas: " + todasMensagens);
            String teste[] = todasMensagens.split("\n");
            //System.out.println("Length:"+teste.length);
            //System.out.println(teste);
            //System.out.println("fim do teste");
            //if(teste.length == 1) return; //caso só tenha uma mensagem a ser removida significa que é a nova mensagem.
            String teste2;
            if(teste.length == 1){
            	teste2 = teste[0];
            }else{
            	teste2 = teste[0]; //primeira posicao das mensagens a serem removidas, para apagar a partir deste.
            	//tem que ser [0] para o teste automático, e [1] para testar manualmente
            }
            //System.out.println("Teste2:"+teste2);
            //System.out.println("Mensagem do setText:"+ this.getReceivedText());
            int posicao = this.getReceivedText().lastIndexOf(teste2);
            //System.out.println("Posicaoo na substring: "+posicao);
            if (posicao != -1) {
                this.receivedText.setText(this.getReceivedText().substring(0, posicao));
            } else {
                //this.receivedText.setText(""); //não zera o "setReceivedText pois provavelmente não havia mensagem para ser apagada.
            }
            //System.out.println("Terminou o rollback, chatlvt:"+chatLVT);
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
     * Ã‰ uma Thread para atualizar o GVT (advindo do fedgvt) na tela e o LVT do federado
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
                //SincronizaÃ§Ã£o
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
                    /*try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }*/
                    //System.out.println("Atualizando LVT: "+chatLVT+" aux: "+chatLVTaux);
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
                if(chatLVT.compareTo("-1") == 0){
                	System.out.println("Total de rollbacks realizados: "+totalRollbacks);
        	    	System.out.println("Total de tempo em rollback: "+totalTempoRollback);
        	    	System.out.println("Total de checkpoints inúteis: "+totalCheckpointsInuteis);
        	    	System.out.println("Checkpoint Index: "+CkpIndex);
        	    	System.out.println("Finalizando Chat!");
        	    }
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
                //Se a quantidade de mensagens trocadas for considerÃ¡vel, maior que 10
                if (outerClass.contadorDeMensagens > 10) {
                    outerClass.setCheckpoint(outerClass.chatLVT);
                }

                tempo = Integer.valueOf(outerClass.chatLVT) - Integer.valueOf(outerClass.lastEventTimestamp);
                provavelCheckpoint = Integer.valueOf(outerClass.lastEventTimestamp);
                provavelCheckpoint += 10;
                //Se passou mais de 5000 desde o Ãºltimo evento e ainda nÃ£o tem checkpoint
                if ((tempo > 5000) && !(outerClass.checkpointsArray.contains(provavelCheckpoint.toString()))) {
                    System.out.println("LastEventTimestamp: " + lastEventTimestamp);
                    outerClass.setCheckpoint(provavelCheckpoint.toString());

                }
            }
        }
    }*/
    
    private class MensagensAutomaticas implements Runnable{

        private Chat outerClass;
        
        private int cont = 0;
        
        public MensagensAutomaticas(Chat pointer){ // construtor
            outerClass = pointer;
        }

        public void run(){
        	System.out.println("Startou a thread!");
        	
        	
        	
            while(true){

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                	System.out.println("Exception no sleep!");
                	System.out.println(ex);
                }
                switch(id){//cada chat enviará uma série de mensagens
                    case 5:
                    	//////////////////////////////////////////////////////////////chat 5
                    	if(outerClass.chatLVT.compareTo("200") == 0){
                            Gateway5.UpdateAttribute("1.6", "Msg chat 5 aos 200-500"+"®"+outerClass.CkpIndex, "500");
                        }
                    	if(outerClass.chatLVT.compareTo("300") == 0){
                            Gateway5.UpdateAttribute("1.8", "Msg do 1.5 aos 700"+"®"+outerClass.CkpIndex, "700");
                        }
                    	if(outerClass.chatLVT.compareTo("1000") == 0){
                            Gateway5.UpdateAttribute("1.6", "Msg chat 5 aos 1200"+"®"+outerClass.CkpIndex, "1200");
                        }
                    	if(outerClass.chatLVT.compareTo("1000") == 0){
                            Gateway5.UpdateAttribute("1.7", "Msg chat 5 aos 1200"+"®"+outerClass.CkpIndex, "1200");
                        }
                    	if(outerClass.chatLVT.compareTo("3500") == 0){
                            Gateway5.UpdateAttribute("1.6", "Msg chat 5 aos 4000"+"®"+outerClass.CkpIndex, "4000");
                        }
                    	if(outerClass.chatLVT.compareTo("13500") == 0){
                            Gateway5.UpdateAttribute("1.7", "Msg chat 5 aos 13700"+"®"+outerClass.CkpIndex, "13700");
                        }
                        break;
                    case 6:
                    	//////////////////////////////////////////////////////////////chat 6
                        if(outerClass.chatLVT.compareTo("1200" ) == 0 && cont == 0){
                            Gateway6.UpdateAttribute("1.8", "Msg chat 6 1200-1500"+"®"+outerClass.CkpIndex, "1500");
                            cont++;
                        }
                        if(outerClass.chatLVT.compareTo("1300") == 0 && cont == 1){
                        	System.out.println("Avancando LVT... de 1300 para 2700");
                    		outerClass.chatLVT = Gateway6.updateLVT("2700");
                            cont++;
                        }
                        if(outerClass.chatLVT.compareTo("4000") == 0 && cont == 2){
                        	System.out.println("Avancando LVT... de 4000 para 5000");
                    		outerClass.chatLVT = Gateway6.updateLVT("5000");
                            cont++;
                        }
                        if(outerClass.chatLVT.compareTo("6400") ==0 && cont == 3){
                            Gateway6.UpdateAttribute("1.8", "Mensagem chat 6 6700"+"®"+outerClass.CkpIndex, "6700");
                            cont++;
                    	}
                        if(outerClass.chatLVT.compareTo("7400") ==0 && cont == 4){
                            Gateway6.UpdateAttribute("1.8", "Mensagem chat 6 7700"+"®"+outerClass.CkpIndex, "7700");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("7800") == 0 && cont == 5){
                        	System.out.println("Avancando LVT... de 7800 para 8600");
                    		outerClass.chatLVT = Gateway6.updateLVT("8600");
                            cont++;
                        }
                        if(outerClass.chatLVT.compareTo("10000") == 0 && cont == 6){
                        	System.out.println("Avancando LVT... de 10000 para 11600");
                    		outerClass.chatLVT = Gateway6.updateLVT("11600");
                            cont++;
                        }
                        break;
                    case 7:
                    	//////////////////////////////////////////////////////////////chat 7
                    	if(outerClass.chatLVT.compareTo("6000") ==0 && cont == 0){
                            Gateway7.UpdateAttribute("1.6", "Mensagem chat 7 6200"+"®"+outerClass.CkpIndex, "6200");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("6900") ==0 && cont == 1){
                            Gateway7.UpdateAttribute("1.6", "Mensagem chat 7 7200"+"®"+outerClass.CkpIndex, "7200");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("8000") ==0 && cont == 2){
                            Gateway7.UpdateAttribute("1.6", "Mensagem chat 7 8300"+"®"+outerClass.CkpIndex, "8300");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("8100") == 0 && cont == 3){
                        	System.out.println("Avancando LVT... de 8100 para 8900");
                    		outerClass.chatLVT = Gateway7.updateLVT("8900");
                            cont++;
                        }
                    	if(outerClass.chatLVT.compareTo("10000") == 0 && cont == 4){
                        	System.out.println("Avancando LVT... de 10000 para 10900");
                    		outerClass.chatLVT = Gateway7.updateLVT("10900");
                            cont++;
                        }
                        break;
                    case 8:
                    	//////////////////////////////////////////////////////////////chat 8
                    	if(outerClass.chatLVT.compareTo("1800") ==0 && cont == 0){
                            Gateway8.UpdateAttribute("1.6", "Mensagem chat 8 2200"+"®"+outerClass.CkpIndex, "2200");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("1500") == 0 && cont == 1){
                        	System.out.println("Avancando LVT... de 1500 para 2200");
                    		outerClass.chatLVT = Gateway8.updateLVT("2200");
                            cont++;
                        }
                    	if(outerClass.chatLVT.compareTo("6000") ==0 && cont == 2){
                            Gateway8.UpdateAttribute("1.7", "Mensagem chat 8 6200"+"®"+outerClass.CkpIndex, "6200");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("6900") ==0 && cont == 3){
                            Gateway8.UpdateAttribute("1.7", "Mensagem chat 8 7200"+"®"+outerClass.CkpIndex, "7200");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("8400") ==0 && cont == 4){
                            Gateway8.UpdateAttribute("1.7", "Mensagem chat 8 8600"+"®"+outerClass.CkpIndex, "8600");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("10000") == 0 && cont == 5){
                        	System.out.println("Avancando LVT... de 10000 para 10100");
                    		outerClass.chatLVT = Gateway8.updateLVT("10100");
                            cont++;
                        }
                        break;
                    case 9:
                    	////////////////////////////////////////////////////////////// chat 9
                    	if(outerClass.chatLVT.compareTo("1800") ==0 && cont == 0){
                            Gateway9.UpdateAttribute("1.5", "Mensagem chat 9 2200"+"®"+outerClass.CkpIndex, "2200");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("5000") ==0 && cont == 1){
                            Gateway9.UpdateAttribute("1.7", "Mensagem chat 9 5200"+"®"+outerClass.CkpIndex, "5200");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("5000") ==0 && cont == 2){
                            Gateway9.UpdateAttribute("1.8", "Mensagem chat 9 5200"+"®"+outerClass.CkpIndex, "5200");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("5500") ==0 && cont == 3){
                            Gateway9.UpdateAttribute("1.7", "Mensagem chat 9 5700"+"®"+outerClass.CkpIndex, "5700");
                            cont++;
                    	}
                    	if(outerClass.chatLVT.compareTo("5500") ==0 && cont == 4){
                            Gateway9.UpdateAttribute("1.8", "Mensagem chat 9 5700"+"®"+outerClass.CkpIndex, "5700");
                            cont++;
                    	}
                        break;
                }

            }
        }

    }

    
    
}
