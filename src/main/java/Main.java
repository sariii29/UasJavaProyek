import frame.AlamatViewFrame;
import frame.PengunjungViewFrame;
//import helpers.Koneksi;

public class Main {
    public static void main(String[] args) {
        //Koneksi.getConnection();
        //AlamatViewFrame viewFrame = new AlamatViewFrame();
        PengunjungViewFrame viewFrame = new PengunjungViewFrame();
        viewFrame.setVisible(true);
    }
}
