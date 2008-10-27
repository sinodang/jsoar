/*
 * Copyright (c) 2008  Dave Ray <daveray@gmail.com>
 *
 * Created on Oct 25, 2008
 */
package org.jsoar.debugger;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * @author ray
 */
public class Images
{
    public static final ImageIcon PAUSE = loadImage("/org/jsoar/debugger/pause.gif");
    public static final ImageIcon START = loadImage("/org/jsoar/debugger/start.gif");
    public static final ImageIcon STOP = loadImage("/org/jsoar/debugger/stop.gif");
    public static final ImageIcon REFRESH = loadImage("/org/jsoar/debugger/refresh.gif");
    public static final ImageIcon UNDO = loadImage("/org/jsoar/debugger/undo.gif");

    /**
     * @param string
     * @return
     */
    private static ImageIcon loadImage(String file)
    {
        URL url = Images.class.getResource(file);
        return new ImageIcon(url);
    }
}