package bws.customerrelation.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

import bws.customerrelation.DAL.Gateway.GetJSONFromAPI;
import bws.customerrelation.Model.BECanvas;
import bws.customerrelation.Model.BECompany;

/**
 * Created by Jacob Ferdinandsen on 12-08-2015.
 */
public class DAOCompany {

    Context _context;
    SQLiteDatabase _db;
    SQLiteStatement _sql;
    //Dummy use
    String _INSERT = "INSERT INTO " + DAConstants.TABLE_CLIENT + "(Firstname, Lastname, Email, Password, Company, PhoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
    //DL
    String _INSERTCLIENT = "INSERT INTO " + DAConstants.TABLE_CLIENTLIST + "(Id ,Firstname, Lastname, Email, Password, Company, PhoneNumber) VALUES (?,?, ?, ?, ?, ?, ?)";
    String _INSERTCANVAS = "INSERT INTO " + DAConstants.TABLE_CANVAS + "(ClientID, Canvas) VALUES (? ,?)";

    public DAOCompany(Context context) {
        _context = context;
        OpenHelper openHelper = new OpenHelper(_context);
        _db = openHelper.getWritableDatabase();
    }

    public long insert(BECompany client) {
        _sql = _db.compileStatement(_INSERT);
        _sql.bindString(1, client.getFirstName());
        _sql.bindString(2, client.getLastName());
        _sql.bindString(3, client.getEmail());
        _sql.bindString(4, client.getPassword());
        _sql.bindString(5, client.getCompany());
        _sql.bindString(6, "" + client.getPhoneNumber());
        return _sql.executeInsert();
    }

    public ArrayList<BECompany> getCompanyFromApi() {
        ArrayList<BECompany> companyList = new ArrayList<>();
        String URL = "";
        JSONObject obj;
        GetJSONFromAPI api = new GetJSONFromAPI();
        api.execute(URL);
        try {
            obj = api.get();
            companyList = ConvertFromJsonToBE(obj);
        } catch (Exception e) {
            Log.e("Api get", "Error when trying to connect to api", e);
        }
        return companyList;
    }

    //KONVERTER JSON TIL BECOMPANY OG ADD TIL ARRAYLIST
    private ArrayList<BECompany> ConvertFromJsonToBE(JSONObject obj) {
        //TODO
        return null;
    }

    public ArrayList<BECompany> getAllClients() {
        ArrayList<BECompany> clients = new ArrayList<BECompany>();
        Cursor cursor = _db.query(DAConstants.TABLE_CLIENT,
                new String[]{"Id", "Firstname", "Lastname", "Email", "Password", "Company", "PhoneNumber"},
                null, null, null, null,
                "Id desc");
        if (cursor.moveToFirst()) {
            do {
                clients.add(new BECompany(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return clients;
    }

    public ArrayList<BECompany> getAllClientsFromDevice() {
        ArrayList<BECompany> clients = new ArrayList<BECompany>();
        Cursor cursor = _db.query(DAConstants.TABLE_CLIENTLIST,
                new String[]{"Id", "Firstname", "Lastname", "Email", "Password", "Company", "PhoneNumber"},
                null, null, null, null,
                "Firstname desc");
        if (cursor.moveToFirst()) {
            do {
                clients.add(new BECompany(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return clients;
    }

    public void deleteAllClients() {
        _db.execSQL("DELETE FROM " + DAConstants.TABLE_CLIENTLIST + " WHERE ID != " + 100000);

    }

    public long insertClientOnList(BECompany client) {
        _sql = _db.compileStatement(_INSERTCLIENT);
        _sql.bindString(1, "" + client.getId());
        _sql.bindString(2, client.getFirstName());
        _sql.bindString(3, client.getLastName());
        _sql.bindString(4, client.getEmail());
        _sql.bindString(5, client.getPassword());
        _sql.bindString(6, client.getCompany());
        _sql.bindString(7, "" + client.getPhoneNumber());
        return _sql.executeInsert();
    }

    public long insertCanvas(String canvas, BECompany client) {
        _sql = _db.compileStatement(_INSERTCANVAS);
        _sql.bindString(1, "" + client.getId());
        _sql.bindString(2, canvas);
        return _sql.executeInsert();
    }

    public ArrayList<BECanvas> getAllCanvasByClientId(BECompany client) {
        ArrayList<BECanvas> canvasList = new ArrayList<>();
        Cursor cursor = _db.query(DAConstants.TABLE_CANVAS,
                new String[]{"Id", "ClientId", "Canvas"},
                "ClientId=?", new String[]{"" + client.getId()}, null, null,
                "Id desc");
        if (cursor.moveToFirst()) {
            do {
                canvasList.add(new BECanvas(cursor.getInt(0), cursor.getInt(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return canvasList;
    }
    //Hent en specifik user's, Client liste ud fra AD
//    public ArrayList<BECompany> getUsersClients(BEUser user) {
//
//        ArrayList<BECompany> clients = new ArrayList<BECompany>();
//        Cursor cursor = _db.query( //INDSÆT DET KORREKTE );
//        if (cursor.moveToFirst()) {
//            clients.add(new BECompany(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
//        }
//        if (cursor != null && !cursor.isClosed()) {
//            cursor.close();
//        }
//        return clients;
//    }
}
