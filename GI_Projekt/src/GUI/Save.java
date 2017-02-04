package GUI;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * 	author Michael Recla
 */

public class Save {
	// Pfad zur Speicherung von Dateien (hier: kml-File)
	public static String fileName;

	public static String saveTo() {

		JFileChooser chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		// Nur kml-Files dürfen gespeichert werden!
		FileNameExtensionFilter filter = new FileNameExtensionFilter("kml-Files", "kml");
		chooser.addChoosableFileFilter(filter);
		chooser.setDialogTitle("Speichern unter...");
		chooser.showSaveDialog(null);

		// Pfad wird übergeben
		fileName = chooser.getSelectedFile().toString();

		// gibt der Nutzer kein ".kml" ein, wird es hier ergänzt:
		if (!fileName.endsWith(".kml")) {
			return fileName + ".kml";
		}
		return fileName;
	}
}
