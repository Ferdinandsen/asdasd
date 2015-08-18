package bws.customerrelation.Controller;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;

import bws.customerrelation.DAL.DAOClient;
import bws.customerrelation.Model.BEClient;

/**
 * Created by Jacob Ferdinandsen on 13-08-2015.
 */
public class ClientController {
    DAOClient _daoClient;
    Activity _activity;

    public ClientController(Activity context){
        _activity = context;
        _daoClient = new DAOClient(_activity);

    }
    public void createDummyClients() {
        int test = _daoClient.getAllClients().size();
        if (_daoClient.getAllClients().size() < 10) {
            _daoClient.insert(new BEClient("André", "Psy", "SejerejekillerxX@live.dk", "hej123", "BWS", 0));
            _daoClient.insert(new BEClient("Thomas", "Petersen", "PsykoMegetOverklar@msn.com", "hej123", "BWS", 0));
            _daoClient.insert(new BEClient("Bob", "Olesen", "bobolesen@hotmail.com", "hej123", "BWS", 0));
            _daoClient.insert(new BEClient("Kevin", "Ørskov", "yoyo@live.dk", "hej123", "BWS", 27242508));
            _daoClient.insert(new BEClient("Jacob", "Jakobsen", "feedthehorse@yumyum.com", "hej123", "BWS", 23839498));
            _daoClient.insert(new BEClient("Trine", "Hansen", "thansen@fakta.com", "hej123", "BWS", 0));
            _daoClient.insert(new BEClient("Anne", "Dahl", "adahl@gmail.com", "hej123", "BWS", 0));
            _daoClient.insert(new BEClient("Thue", "Emilsen", "Volume@onmypc.com", "hej123", "BWS", 0));
            _daoClient.insert(new BEClient("Mette", "Enevoldsen", "menevold@live.dk", "hej123", "BWS", 0));
            _daoClient.insert(new BEClient("Kasper", "Juul", "kjuu@live.dk", "hej123", "BWS", 0));
        }
    }

    public ArrayList<BEClient> getAllClients(){
        return _daoClient.getAllClients();
    }

    public void deleteAllClients() {
       _daoClient.deleteAllClients();
    }
}