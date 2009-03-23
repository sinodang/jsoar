/*
 * Copyright (c) 2008  Dave Ray <daveray@gmail.com>
 *
 * Created on Oct 31, 2008
 */
package org.jsoar.debugger;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.jsoar.kernel.memory.Wme;
import org.jsoar.kernel.symbols.Identifier;
import org.jsoar.kernel.symbols.Symbol;

/**
 * @author ray
 */
public class DefaultWmeTableModel extends AbstractTableModel
{
    private static final long serialVersionUID = -8187445208277014970L;
    
    private static final String[] columns = { "Id", "Attr", "Value", "Timetag", "Acceptable" };
    private static final Class<?>[] classes = {
            Identifier.class, Symbol.class, Symbol.class, Integer.class, String.class };
    
    private final List<Wme> wmes;
    
    /**
     * @param wmes
     */
    public DefaultWmeTableModel(List<Wme> wmes)
    {
        this.wmes = wmes;
    }
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return classes[columnIndex];
    }
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column)
    {
        return columns[column];
    }
    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount()
    {
        return columns.length;
    }
    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount()
    {
        return wmes.size();
    }
    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int r, int c)
    {
        Wme w = wmes.get(r);
        switch(c)
        {
        case 0: return w.getIdentifier();
        case 1: return w.getAttribute();
        case 2: return w.getValue();
        case 3: return w.getTimetag();
        case 4: return w.isAcceptable() ? "+" : "";
        }
        return null;
    }

    
}