package br.upe.ui;

import br.upe.controller.TarefaControlador;
import br.upe.model.Tarefa;
import br.upe.model.TarefaTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaPrincipal {
    private JPanel pnlMain;
    private JTextField txtDescricaoTarefa;
    private JButton btnAdicionarTarefa;
    private JPanel pnlAdicionar;
    private JTable tblTarefas;
    private JCheckBox chkExibirFinalizadas;

    private List<Tarefa> tarefas;

    private TarefaControlador controlador;

    public TelaPrincipal() {
        super();
        tarefas = new ArrayList<>();
        btnAdicionarTarefa.addActionListener(e -> {
            adicionarTarefa(txtDescricaoTarefa.getText());
            txtDescricaoTarefa.setText("");
        });
        chkExibirFinalizadas.addActionListener(e -> {
            boolean selecionado = ((JCheckBox) e.getSource()).isSelected();
            controlador.exibirFinalizadas(selecionado);
        });
    }

    private void adicionarTarefa(String texto) {
        texto = texto.trim();
        if (texto.isEmpty()) {
            return;
        }
        Tarefa tarefa = new Tarefa(texto, tarefas.size());
        controlador.adicionarTarefaAtiva(tarefa);
        tblTarefas.revalidate();
        tblTarefas.repaint();
    }

    public JPanel getPnlMain() {
        return this.pnlMain;
    }

    private void createUIComponents() {
        controlador = new TarefaControlador();
        tblTarefas = new JTable(controlador.getTarefaTableModel());
        tblTarefas.getColumnModel().getColumn(0).setMaxWidth(20);


        // ---- coluna da lixeira ----
        tblTarefas.getColumnModel().getColumn(2).setMaxWidth(30);
        tblTarefas.setRowHeight(24);

        DefaultTableCellRenderer rendererLixeira = new DefaultTableCellRenderer();
        rendererLixeira.setHorizontalAlignment(SwingConstants.CENTER);
        rendererLixeira.setIcon(new ImageIcon(getClass().getResource("/lixeira.png")));
        tblTarefas.getColumnModel().getColumn(2).setCellRenderer(rendererLixeira);
        tblTarefas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tblTarefas.rowAtPoint(e.getPoint());
                int coluna = tblTarefas.columnAtPoint(e.getPoint());
                if (linha >= 0 && coluna == 2) {
                    controlador.removerTarefa(linha);
                }
            }
        });
        }
    }


