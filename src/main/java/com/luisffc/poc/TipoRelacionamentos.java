package com.luisffc.poc;

import org.neo4j.graphdb.RelationshipType;


public enum TipoRelacionamentos implements RelationshipType {
	KNOWS,
	SEGUE,
	ESCREVEU
}
