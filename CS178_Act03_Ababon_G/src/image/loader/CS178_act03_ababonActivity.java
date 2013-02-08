package image.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.widget.ImageView;

public class CS178_act03_ababonActivity extends Activity {
	
	private String[] image_url = new String[]{
			"http://ww1.prweb.com/prfiles/2010/05/11/1751474/gI_TodoforiPadAppIcon512.png.jpg",
			"http://cdn4.iosnoops.com/wp-content/uploads/2011/08/Icon-Gmail_large-250x250.png",
			"http://kelpbeds.files.wordpress.com/2012/02/lens17430451_1294953222linkedin-icon.jpg?w=450",
			"http://snapknot.com/blog/wp-content/uploads/2010/03/facebook-icon-copy.jpg",
			"https://lh3.googleusercontent.com/-ycDGy_fZVZc/AAAAAAAAAAI/AAAAAAAAAAc/Q0MmjxPCOzk/s250-c-k/photo.jpg"
	};
	
	private int no_of_files= image_url.length;
	private ImageView[] images = new ImageView[no_of_files];
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        images[0]=(ImageView) findViewById(R.id.image1);
        images[1]=(ImageView) findViewById(R.id.image2);
        images[2]=(ImageView) findViewById(R.id.image3);
        images[3]=(ImageView) findViewById(R.id.image4);
        images[4]=(ImageView) findViewById(R.id.image5);
        for(int i = 0 ; i < no_of_files ; i++ ){
        	images[i].setTag(image_url[i]);
        	new FetchFilesTask().execute(images[i]);
        }
       	 
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private class FetchFilesTask extends AsyncTask<ImageView, Void, Drawable> {

	    private ProgressDialog dialog = new ProgressDialog(CS178_act03_ababonActivity.this);
	    Drawable image;
	    ImageView imageView;
	    String URL;
	    protected void onPreExecute(){
	        dialog.setMessage("Fetching image from the Internet");
	        dialog.show();
	    }

	     protected Drawable doInBackground(ImageView... args) {

	    	 imageView=args[0];
	         URL = (String) args[0].getTag();    
	    	 image = ImageOperations(URL, "image.jpg");
	    	 
	         return image;
	     }

	     protected void onPostExecute(Drawable m_bitmap) {
	         dialog.dismiss();
	         if(m_bitmap != null)
	         {
	        	 imageView.setImageDrawable(m_bitmap);
	         }
	         else
	         {
	        	 imageView.setImageResource(R.drawable.notfound);
	         }
	                
	     }
	     
	     private Drawable ImageOperations(String url, String saveFilename) {
	         try {
	             InputStream is = (InputStream) this.fetch(url);
	             Drawable d = Drawable.createFromStream(is, "src");
	             return d;
	         } catch (MalformedURLException e) {
	             return null;
	         } catch (IOException e) {
	             return null;
	         }
	     }
	     
	     public Object fetch(String address) throws MalformedURLException,IOException {
	         URL url = new URL(address);
	         Object content = url.getContent();
	         return content;
	     }
	 }
    
    

	
}
