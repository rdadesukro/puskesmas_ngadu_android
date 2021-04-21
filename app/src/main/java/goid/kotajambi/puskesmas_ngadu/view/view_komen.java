package goid.kotajambi.puskesmas_ngadu.view;



import java.util.List;

import goid.kotajambi.puskesmas_ngadu.model.komen.Result_komen;
import goid.kotajambi.puskesmas_ngadu.model.laporan_komen.ResultItem_laporan_komen;

/**
 * This class represents the country view interface.
 *
 * @author Jean Carlos (Github: @jeancsanchez)
 * @date 09/03/18.
 * Jesus loves you.
 */
public interface view_komen {

   // void countriesReady(List<Result_laporan_saya> countries);




    void komen(List<Result_komen> bener);
    void  jumlah(String jumlah);
    void  status(String status);
    void laporan_komen(List<ResultItem_laporan_komen> laporan_komen);


}
