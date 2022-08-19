package com.mycompany.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileView;


public class ImagemChooser extends JFileChooser {
    
    private static final int ICON_SIZE = 32;
    private static final Image LOADING_IMAGE = new BufferedImage(ICON_SIZE, ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
    private final Pattern imageFilePattern = Pattern.compile(".+?\\.(png|jpe?g|gif|tiff?)$", Pattern.CASE_INSENSITIVE);
    private final Map<Object, ImageIcon> imageCache = new WeakHashMap<>();
    

    public ImagemChooser() {
    }

    public ImagemChooser(File currentDirectory) {
        super(currentDirectory);
    }
    
    {
    setFileView(new ImagemThumb());
    }
    
    private class ImagemThumb extends FileView {
        
        private final ExecutorService executor = Executors.newCachedThreadPool();
        
        @Override
        public Icon getIcon(File file) {
            if(!imageFilePattern.matcher(file.getName()).matches()) {
                return null;
            }
            
            synchronized(imageCache) {
                ImageIcon icon = imageCache.get(file);
                
                if(icon == null) {
                    icon = new ImageIcon(LOADING_IMAGE);
                    imageCache.put(file, icon);
                    executor.submit(new ImagemThumbLoader(icon, file));
                }
                
                return icon;
            }
        }
    }
    
    private class ImagemThumbLoader implements Runnable {
        private final ImageIcon icon;
        private final File file;

        public ImagemThumbLoader(ImageIcon icon, File file) {
            this.icon = icon;
            this.file = file;
        }
        
        public void run() {
            
            ImageIcon newIcon = new ImageIcon(file.getAbsolutePath());
            Image imagem = newIcon.getImage().getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
            icon.setImage(imagem);
            
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    repaint();
                }
            });
        }
    }
    
    
    
}
