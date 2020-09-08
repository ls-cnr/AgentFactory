/*
 * ImageRecoverer.java
 *
 * Created on 12 maggio 2003, 13.57
 */

package ptk_resource;

import javax.swing.ImageIcon;
import java.net.*;

/**
 *
 * @author  luca
 */
public class ImageRecoverer {
	private ImageIcon image_icon;
	
	/** Creates a new instance of ImageRecoverer */
	public ImageRecoverer(String icon_name) {
		URL iconURL = getClass().getResource("/ptk_resource/images/"+icon_name+".gif");
		if (iconURL != null) {
			image_icon = new ImageIcon(iconURL);
		} 
	}
	
	public ImageRecoverer(URL icon_url) {
		if (icon_url != null) {
			image_icon = new ImageIcon(icon_url);
		}
	}

	public ImageIcon getIcon() {
		return image_icon;
	}
	
	public static void main(String[] args) {
		ImageRecoverer image_recoverer = new ImageRecoverer("my_image");
	}
}
