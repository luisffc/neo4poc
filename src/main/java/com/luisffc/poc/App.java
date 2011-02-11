package com.luisffc.poc;


import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class App {
	
    public static void main( String[] args ) {
    	GraphDatabaseService db = new EmbeddedGraphDatabase("db/graph2");
    	
    	Transaction tx = db.beginTx();
    	try {
    		
    		Node fulano1 = db.createNode();
    		Node fulano2 = db.createNode();
    		Relationship relation = fulano1.createRelationshipTo(fulano2, TipoRelacionamentos.KNOWS);
    		
    		fulano1.setProperty("nome", "luis");
    		fulano2.setProperty("nome", "carol");
    		relation.setProperty("conhece", "conhece");
    		
    		System.out.print( fulano1.getProperty( "nome" ) + " " );
        	System.out.print( relation.getProperty( "conhece" ) + " ");
        	System.out.print( fulano2.getProperty( "nome" ) + " ");
    		
    		tx.success();
    	} finally {
    		
    		tx.finish();
		}
    	
    	db.shutdown();
    }
}
