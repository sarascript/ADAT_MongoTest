import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * Prueba para realizar conexión con MongoDB.
 * 
 * @author j
 *
 */
public class Principal {
	/**
	 * Main del proyecto.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		java.util.logging.Logger.getLogger("org.mongodb").setLevel(Level.OFF);
		System.out.println("Prueba conexión MongoDB");
		MongoClient mongo = crearConexion();

		if (mongo != null) {
			// printDatabases(mongo);
			// Si no existe la base de datos la crea
			DB db = mongo.getDB("prueba");

			// Crea una tabla si no existe y agrega datos
			DBCollection table = db.getCollection("colores");
			//insertDocument(mongo, table);
			//deleteDocument(mongo, table);
			//updateDocument(mongo, table);
			//findDocument(mongo, table);
			printCollection(mongo, table);

		} else {
			System.out.println("Error: Conexión no establecida");
		}
	}

	/**
	 * Clase para crear una conexión a MongoDB.
	 * 
	 * @return MongoClient conexión
	 */
	private static MongoClient crearConexion() {
		MongoClient mongo = null;
		mongo = new MongoClient("localhost", 27017);

		return mongo;
	}

	/**
	 * Clase que imprime por pantalla todas las bases de datos MongoDB.
	 * 
	 * @param mongo
	 *            conexión a MongoDB
	 */
	private static void printDatabases(MongoClient mongo) {
		System.out.println("Lista de bases de datos: ");
		List<String> dbs = mongo.getDatabaseNames();
		for (String db : dbs) {
			System.out.println(" - " + db);
		}
	}

	private static void printCollection(MongoClient mongo, DBCollection table) {
		System.out.println("Listar los registros de la tabla: ");
		DBCursor cur = table.find();
		while (cur.hasNext()) {
			System.out.println(" - " + cur.next().get("nombreColor") + " " + cur.curr().get("valorHexadec"));
		}
		System.out.println();
	}

	private static void insertDocument(MongoClient mongo, DBCollection table) {
		BasicDBObject document1 = new BasicDBObject();
		document1.put("nombreColor", "blanco");
		document1.put("valorHexadec", "#fff");

		table.insert(document1);
	}
	
	private static void deleteDocument(MongoClient mongo, DBCollection table) {
        table.remove(new BasicDBObject().append("nombreColor", "blanco"));
	}
	
	private static void updateDocument(MongoClient mongo, DBCollection table) {
		BasicDBObject updateValor = new BasicDBObject();
        updateValor.append("$set", new BasicDBObject().append("valorHexadec", "#000000"));

        BasicDBObject searchById = new BasicDBObject();
        searchById.append("nombreColor", "negro");

        table.updateMulti(searchById, updateValor);
	}
	
	private static void findDocument(MongoClient mongo, DBCollection table) {
        System.out.println("Listar los registros de la tabla cuyo nombre sea Negro: ");
        BasicDBObject query = new BasicDBObject();
        query.put("nombreColor", "negro");

        DBCursor cur2 = table.find(query);
        while (cur2.hasNext()) {
            System.out.println(" - " + cur2.next().get("nombreColor") + " " + cur2.curr().get("valorHexadec"));
        }
        System.out.println();
	}

}