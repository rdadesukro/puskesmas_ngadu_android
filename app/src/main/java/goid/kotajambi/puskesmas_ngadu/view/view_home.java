package goid.kotajambi.puskesmas_ngadu.view;



import java.util.List;

import goid.kotajambi.puskesmas_ngadu.model.bener.ResultItem_bener;
import goid.kotajambi.puskesmas_ngadu.model.model_berita.PostsItem;
import goid.kotajambi.puskesmas_ngadu.model.slider.IsiItem_slider;

/**
 * This class represents the country view interface.
 *
 * @author Jean Carlos (Github: @jeancsanchez)
 * @date 09/03/18.
 * Jesus loves you.
 */
public interface view_home {

   // void countriesReady(List<Result_laporan_saya> countries);



    void berita(List<PostsItem> data);

    void bener(List<IsiItem_slider> bener);


}
