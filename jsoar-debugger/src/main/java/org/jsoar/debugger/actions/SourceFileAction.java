/*
 * Copyright (c) 2008  Dave Ray <daveray@gmail.com>
 *
 * Created on Oct 25, 2008
 */
package org.jsoar.debugger.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.concurrent.Callable;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.jsoar.tcl.SoarTclException;
import org.jsoar.tcl.SoarTclInterface;
import org.jsoar.util.FileTools;

/**
 * @author ray
 */
public class SourceFileAction extends AbstractDebuggerAction
{
    private static final long serialVersionUID = -7639843952865259437L;
    
    private String lastDir = System.getProperty("user.dir");

    public SourceFileAction(ActionManager manager)
    {
        super(manager, "Source File ...");
    }

    /* (non-Javadoc)
     * @see org.jsoar.debugger.actions.AbstractDebuggerAction#update()
     */
    @Override
    public void update()
    {
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        JFileChooser chooser = new JFileChooser(lastDir);
        chooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f)
            {
                return f.isDirectory() || (f.isFile() && "soar".equals(FileTools.getExtension(f.getName())));
            }

            @Override
            public String getDescription()
            {
                return "Soar Files (*.soar)";
            }});
        
        if(JFileChooser.CANCEL_OPTION == chooser.showOpenDialog(getApplication().getTopLevelAncestor()))
        {
            return;
        }
        
        final File f = chooser.getSelectedFile();
        lastDir = f.getParentFile().getAbsolutePath();
        
        final SoarTclInterface tcl = getApplication().getTcl();
        getApplication().getAgentProxy().execute(new Callable<Void>() {

            @Override
            public Void call()
            {
                try
                {
                    tcl.sourceFile(f.getAbsolutePath());
                }
                catch (SoarTclException e)
                {
                    tcl.getAgent().getPrinter().error(e.getMessage());
                }
                return null;
            }}, getApplication().newUpdateCompleter(false));
        
    }

}