package com.ns.gui.model;

import java.util.List;

import javax.swing.table.TableModel;

import com.ns.model.Task;

public interface TasklistTableModel extends TableModel {

    void setData(List<Task> data);

}
