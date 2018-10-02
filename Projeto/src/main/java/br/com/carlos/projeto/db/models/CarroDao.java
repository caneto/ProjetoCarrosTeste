package br.com.carlos.projeto.db.models;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.internal.IOException;

public class CarroDao {

    public Carro getCarroPorPlaca(String carroPlaca) {
        Realm realm = Realm.getDefaultInstance();

        try {
            Carro carro = realm.where(Carro.class)
                    .equalTo(CarroFields.t_placa,carroPlaca).findFirst();

            // Check if the data is not null, if null there is no user with these Id
            if (carro == null) {
                return null;
            }

            return carro;

        } catch (Exception e) {
            // print the error
            e.printStackTrace();
        }

        return null;
    }

    public Carro getCarroPorModelo(String modelo) {
        Realm realm = Realm.getDefaultInstance();

        try {
            Carro carro = realm.where(Carro.class)
                    .beginsWith(CarroFields.t_modelo, modelo).findFirst();

            // Check if the data is not null, if null there is no user with these Id
            if (carro == null) {
                return null;
            }

            return carro;

        } catch (Exception e) {
            // print the error
            e.printStackTrace();
        }

        return null;
    }

    public RealmResults<Carro> getAll() {
        Realm realm = Realm.getDefaultInstance();
        try {
            RealmResults<Carro> results = realm.where(Carro.class)
                    .findAll();

            if (results == null || results.size() == 0) {
                return null;
            }

            return results;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public void salvaCarro(Carro carro) {
        saveOrUpdate(carro);
    }

    public void adicionaCarro(Carro carro) {
        insertOrUpdate(carro);
    }

    protected void saveOrUpdate(Carro carro) {
        Realm realm = Realm.getDefaultInstance();
        try {
            //save pedido on realm
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(carro);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void insertOrUpdate(Carro carro) {
        Realm realm = Realm.getDefaultInstance();
        try {

            Number currentIdNum = realm.where(Carro.class).max(CarroFields.t_id);
            int nextId;
            if(currentIdNum == null) {
                nextId = 1;
            } else {
                nextId = currentIdNum.intValue() + 1;
            }

            carro.setId(nextId);

            //save pedido on realm
            realm.beginTransaction();
            realm.insertOrUpdate(carro);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delAll() {
        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction(); // open a transation
            realm.delete(Carro.class); // delete the data
            realm.commitTransaction(); // close the transation

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    public void delPorId(int carroId) {
        Realm realm = Realm.getDefaultInstance();

        try {
            Carro carro = realm.where(Carro.class)
                    .equalTo("id",carroId).findFirst();

            // Check if the data is not null, if null there is no user with these Id
            if (carro == null) {
                return;
            }

            realm.beginTransaction(); // open a transation
            carro.deleteFromRealm(); // delete the data
            realm.commitTransaction(); // close the transation

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

}