package io.app.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class EntityManagerUtils {

	public static <T> String getTableName(EntityManager em, Class<T> entityClass) {
		/*
		 * Check if the specified class is present in the metamodel. Throws
		 * IllegalArgumentException if not.
		 */
		Metamodel meta = em.getMetamodel();
		EntityType<T> entityType = meta.entity(entityClass);
		// Check whether @Table annotation is present on the class.
		Table t = entityClass.getAnnotation(Table.class);

		String tableName = (t == null) ? entityType.getName().toUpperCase() : t
				.name();
		return tableName;
	}

	/**
	 * Doit renvoyer la liste de pair nomColonne, nomSequence
	 */
	public static <T> List<String> getSequencesName(Class<T> entityClass) {
		//On récupère la liste des fields qui ont une annotation GenericGenerator
		List<GenericGenerator> fieldsWithGenericGenerator = Arrays.asList(entityClass.getDeclaredFields()).stream()
											  .filter(f ->{
												  return f.getAnnotation(GenericGenerator.class)!=null;
											  })
											  .map( f -> f.getAnnotation(GenericGenerator.class))
											  .collect(Collectors.toList());
		
		if(fieldsWithGenericGenerator.isEmpty()){
			return new ArrayList<>();
		}else{
			return fieldsWithGenericGenerator.stream().map(gg ->getSequenceName(gg)).collect(Collectors.toList());
		}
	}
	
	/**
	 * Doit renvoyer une liste de pair  nomColonne, nomSequence
	 * 
	 * @param entityClass
	 * @return
	 */
	public static <T> List<Pair<String,String>> getColumnAndSequence(Class<T> entityClass) {
		//On récupère la liste des fields qui ont une annotation GenericGenerator
		Stream<Field> fieldsWithGenericGenerator = getAllFieldWithAnnotation(entityClass,GenericGenerator.class);
		
		return fieldsWithGenericGenerator.map( f ->{
			String column=getColumnName(f);
			String sequence=getSequenceName(f.getAnnotation(GenericGenerator.class));
			return new Pair<String, String>(column, sequence);
		})
		.collect(Collectors.toList());
	}
	

	private static String getColumnName(Field f) {
		Column column=f.getAnnotation(Column.class);
		String columnName = column==null?f.getName():column.name();
		return columnName;
	}

	public static <T> Stream<Field> getAllFieldWithAnnotation(Class<T> entityClass, Class<? extends Annotation> annotation){
		return  Arrays.asList(entityClass.getDeclaredFields()).stream()
				  .filter(f ->{
					  return f.getAnnotation(annotation)!=null;
				  });
	}
	
	private static String getSequenceName(GenericGenerator gg){
		Stream<Parameter> params=Arrays.asList(gg.parameters()).stream();
		return params.filter(p -> p.name().equalsIgnoreCase(SequenceStyleGenerator.SEQUENCE_PARAM)).map(p -> p.value()).findFirst().orElseThrow(() -> new RuntimeException("GenericGenerator présent sans nom de séquence"));
	}
	
	
	public static Stream<Class<?>> getAllEntities(EntityManager em){
		Metamodel meta = em.getMetamodel();
		return meta.getEntities().stream().map(et -> et.getJavaType());
	}

}
