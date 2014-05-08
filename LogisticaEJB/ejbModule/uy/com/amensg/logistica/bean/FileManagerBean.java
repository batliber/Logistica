package uy.com.amensg.logistica.bean;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

import javax.ejb.Stateless;

import uy.com.amensg.logistica.entities.Archivo;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class FileManagerBean implements IFileManagerBean {

	public Collection<Archivo> listArchivos() {
		Collection<Archivo> result = new LinkedList<Archivo>();
		
		try {
			File folder = new File(Configuration.getInstance().getProperty("exportacion.carpeta"));
			
			File[] files = folder.listFiles(
				new FilenameFilter() {
					public boolean accept(File arg0, String arg1) {
						return !(new File(arg0.getAbsolutePath() + File.separator + arg1).isDirectory());
					}
				}
			);
			
			Arrays.sort(files, new Comparator<File>(){
				public int compare(File arg0, File arg1) {
					return - new Long(arg0.lastModified()).compareTo(new Long(arg1.lastModified()));
				}
			});
			
			for (File file : files) {
				Archivo archivo = new Archivo();
				archivo.setNombre(file.getName());
				
				result.add(archivo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}