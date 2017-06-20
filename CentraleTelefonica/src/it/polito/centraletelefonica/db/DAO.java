package it.polito.centraletelefonica.db;

public interface DAO {

	public void instert(Object obj);

	public void update(Object obj);

	public void delete(Object obj);
	
	public void getObject(Object primaryKey);
	
	public void loadAll();

}
