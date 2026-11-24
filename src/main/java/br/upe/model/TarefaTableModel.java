package br.upe.model;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class TarefaTableModel extends AbstractTableModel {
    public static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    private List<Tarefa> tarefasFinalizadas;
    private List<Tarefa> tarefasAtivas;

    private boolean exibirFinalizadas;

    public TarefaTableModel() {
        tarefasAtivas = new ArrayList<>();
        tarefasFinalizadas = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return tarefasAtivas.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarefa tarefa = tarefasAtivas.get(rowIndex);
        switch (columnIndex) {
            case 0 : return tarefa.isFinalizada();
            case 1 : return tarefa.getDescricao();
            case 2 : return tarefa.getDataVencimento() == null ? "" : tarefa.getDataVencimento().format(FORMATO_DATA);
        }
        return null;
    }

    public Class getColumnClass(int c) {
        switch (c) {
            case 0 : return Boolean.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 3 : return String.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0 || columnIndex == 1;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Tarefa tarefa = tarefasAtivas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                tarefa.setFinalizada((Boolean) aValue);
                if (this.exibirFinalizadas) {
                    if ((Boolean) aValue) {
                        tarefasFinalizadas.add(tarefa);
                    } else {
                        tarefasFinalizadas.remove(tarefa);
                    }
                } else {
                    if ((Boolean) aValue) {
                        tarefasAtivas.remove(tarefa);
                        tarefasFinalizadas.add(tarefa);
                    }
                }
                this.fireTableDataChanged();
                break;
            case 1:
                String novoTexto = aValue.toString().trim();
                if (!novoTexto.isEmpty()) {
                    tarefa.setDescricao(novoTexto);
                    this.fireTableCellUpdated(rowIndex, columnIndex);
                }
                break;
        }

    }
    public void removerTarefa(int rowIndex) {
        Tarefa tarefa = tarefasAtivas.remove(rowIndex);
        tarefasFinalizadas.remove(tarefa);
        this.fireTableRowsDeleted(rowIndex, rowIndex);
    }


    public void setExibirFinalizadas(boolean selecionado) {
        this.exibirFinalizadas = selecionado;
        if (this.exibirFinalizadas) {
            this.tarefasAtivas.addAll(this.tarefasFinalizadas);
        } else {
            this.tarefasAtivas.removeAll(this.tarefasFinalizadas);
        }
        this.fireTableDataChanged();
    }

    public List<Tarefa> getTarefasFinalizadas() {
        return tarefasFinalizadas;
    }

    public void setTarefasFinalizadas(List<Tarefa> tarefasFinalizadas) {
        this.tarefasFinalizadas = tarefasFinalizadas;
    }

    public List<Tarefa> getTarefasAtivas() {
        return tarefasAtivas;
    }

    public void setTarefasAtivas(List<Tarefa> tarefasAtivas) {
        this.tarefasAtivas = tarefasAtivas;
    }

    public boolean isExibirFinalizadas() {
        return exibirFinalizadas;
    }

}
