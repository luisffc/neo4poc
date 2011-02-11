package com.luisffc.poc;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public class TiuIter {


	static GraphDatabaseService db;
	
	public static void main(String[] args) {
		db = new EmbeddedGraphDatabase("db/graphTT");
	
		Transaction tx = db.beginTx();
    	try {
    		
    		Node luis = db.createNode();
    		luis.setProperty("nome", "luis");
    		
    		Node carol = db.createNode();
    		carol.setProperty("nome", "carol");
    		
    		Node marcao = db.createNode();
    		marcao.setProperty("nome", "marcao");
    		
    		seguir(luis, carol);
    		seguir(luis, marcao);
    		
    		escreverMsg(carol, "Bom dia!");
    		escreverMsg(carol, "Boa tarde!");
    		escreverMsg(carol, "Boa noite!");
    		
    		escreverMsg(marcao, "opa!");
    		escreverMsg(marcao, "iae!");
    		escreverMsg(marcao, "ta ligado?");
    		
    		lerMsgs(luis);
    		
    		System.out.println( luis.getProperty( "nome" ));
        	System.out.println( carol.getProperty( "nome" ));
        	
    		tx.success();
    	} finally {
    		
    		tx.finish();
		}
    	
    	db.shutdown();
	}
	
	public static void lerMsgs(Node usuario){
		Iterable<Relationship> followings = usuario.getRelationships(TipoRelacionamentos.SEGUE);
		for (Relationship following : followings) {
			Iterable<Relationship> posts = following.getEndNode().getRelationships(TipoRelacionamentos.ESCREVEU);
			for (Relationship post : posts) {
				String mensagem = (String) post.getEndNode().getProperty("texto");
				System.out.println(following.getEndNode().getProperty("nome") + ": " + mensagem);
			}
		}
		
	}
	
	public static void seguir(Node usuario, Node quem){
		usuario.createRelationshipTo(quem, TipoRelacionamentos.SEGUE);
	}
	
	public static void escreverMsg(Node usuario, String msg){
		Node mensagem = db.createNode();
		mensagem.setProperty("texto", msg);
		usuario.createRelationshipTo(mensagem, TipoRelacionamentos.ESCREVEU);
	}

}
