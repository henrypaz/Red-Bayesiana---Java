/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elvira;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openmarkov.core.exception.InvalidStateException;
import org.openmarkov.core.inference.InferenceAlgorithm;
import org.openmarkov.core.model.network.EvidenceCase;
import org.openmarkov.core.model.network.Finding;
import org.openmarkov.core.model.network.ProbNet;
import org.openmarkov.core.model.network.ProbNode;
import org.openmarkov.core.model.network.Util;
import org.openmarkov.core.model.network.Variable;
import org.openmarkov.core.model.network.potential.TablePotential;
import org.openmarkov.inference.variableElimination.VariableElimination;
import org.openmarkov.io.probmodel.PGMXReader;

/**
 *
 * @author Henry Paz
 */
public class SistemasExpertos extends javax.swing.JFrame {

    final private String bayesNetworkName = "SE";

    /**
     * Creates new form SistemasExpertos
     */
    public SistemasExpertos() {
        initComponents();
    }

    public void iniciar() {
        try {

            // Open the file containing the network
            InputStream file = new FileInputStream(new File("Modulo.pgmx"));

            // Load the Bayesian network
            PGMXReader pgmxReader = new PGMXReader();
            ProbNet redPrueba = pgmxReader.loadProbNet(file, bayesNetworkName).getProbNet();
            //System.out.println("Numero de nodos: " + redPrueba.getNumNodes());
            // Create an evidence case
            // (An evidence case is composed of a set of findings)

            List<ProbNode> listPro = redPrueba.getProbNodes();
            for (int i = 0; i < listPro.size(); i++) {
                ProbNode probNode = listPro.get(i);
//                System.out.println("Nombre: " + probNode.getName());
//                System.out.println("MostrarNet " + probNode.getProbNet());
//                System.out.println("Relevancia: " + probNode.getRelevance());
//                System.out.println("Tipo de Nodo: " + probNode.getNodeType().toString());

            }

            EvidenceCase evidence = new EvidenceCase();
            InferenceAlgorithm variableElimination = new VariableElimination(redPrueba);
            variableElimination.setPreResolutionEvidence(evidence);
            // We are interested in the posterior probabilities of the diseases
            Variable simulacion = redPrueba.getVariable("Simulacion");
            ArrayList<Variable> variablesOfInterest = new ArrayList<Variable>();
            //variablesOfInterest.add(disease1);
            variablesOfInterest.add(simulacion);

            // Compute the posterior probabilities
            HashMap<Variable, TablePotential> posteriorProbabilities =
                    variableElimination.getProbsAndUtilities();

            // Print the posterior probabilities on the standard output
            printResults(evidence, variablesOfInterest, posteriorProbabilities);

            String exa = String.valueOf(this.examen.getSelectedItem());
            evidence.addFinding(redPrueba, "Examenes_simu", exa);

            String part = String.valueOf(this.participacion.getSelectedItem());
            evidence.addFinding(redPrueba, "Participacion_Clase_sim",part);

            String deb = String.valueOf(this.deberes.getSelectedItem());
            evidence.addFinding(redPrueba, "Deberes_sim", deb);

            String trabgru = String.valueOf(this.trabajo.getSelectedItem());
            evidence.addFinding(redPrueba, "Trabajos_grupales_sim", trabgru);

            posteriorProbabilities = variableElimination.getProbsAndUtilities(variablesOfInterest);
            printResults(evidence, variablesOfInterest, posteriorProbabilities);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void printResults(EvidenceCase evidence, ArrayList<Variable> variablesOfInterest,
            HashMap<Variable, TablePotential> posteriorProbabilities) {
        // Print the findings
        String evidencias = "Evidencias: \n";
        for (Finding finding : evidence.getFindings()) {
            evidencias = evidencias + finding.getVariable() + ": " + finding.getState() + "\n";
        }
        //this.consola.setText(evidencias);
        // Print the posterior probability of the state "presente" of each variable of interest
        //System.out.println("Probabilidad: ");
        for (Variable variable : variablesOfInterest) {
            double value;
            TablePotential posteriorProbabilitiesPotential = posteriorProbabilities.get(variable);
            //System.out.print(variable + ": ");
            int stateIndex = -1;
            try {
                stateIndex = variable.getStateIndex("si");
                value = posteriorProbabilitiesPotential.values[stateIndex];
                this.resultado.setText(Util.roundedString(value, "0.001"));
            } catch (InvalidStateException e) {
                System.err.println("State \"presente\" not found for variable \""
                        + variable.getName() + "\".");
                e.printStackTrace();
            }
        }
        //System.out.println();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        examen = new javax.swing.JComboBox();
        trabajo = new javax.swing.JComboBox();
        deberes = new javax.swing.JComboBox();
        participacion = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        resultado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel1.setText("Examenes");

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel2.setText("Trab. grupal");

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel3.setText("Deberes");

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel4.setText("Part. Clase");

        examen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        examen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "alto", "medio", "bajo" }));

        trabajo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        trabajo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "alto", "medio", "bajo" }));

        deberes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        deberes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "alto", "medio", "bajo" }));

        participacion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        participacion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "alto", "medio", "bajo" }));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Ver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel5.setText("Probabilidad Aprobar Simulacion");

        resultado.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        resultado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(31, 31, 31)
                        .addComponent(examen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(participacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(deberes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(trabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(resultado, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(examen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(trabajo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(resultado)
                                    .addComponent(jButton1)))))
                    .addComponent(deberes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(participacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        iniciar();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SistemasExpertos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SistemasExpertos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SistemasExpertos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SistemasExpertos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SistemasExpertos().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox deberes;
    private javax.swing.JComboBox examen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox participacion;
    private javax.swing.JLabel resultado;
    private javax.swing.JComboBox trabajo;
    // End of variables declaration//GEN-END:variables
}
