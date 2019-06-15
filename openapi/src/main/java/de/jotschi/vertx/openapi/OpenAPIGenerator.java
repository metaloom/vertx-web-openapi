package de.jotschi.vertx.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.tags.Tag;

public class OpenAPIGenerator {

	public void generator() throws JsonProcessingException {
		OpenAPI spec = new OpenAPI();

		PathItem i = new PathItem();
		i.setDescription("blar");
		Operation op = new Operation();
		op.setDescription("blub");
		op.summary("The summary");
		i.setPost(op);
		spec.path("/test", i);
		
		Tag tag = new Tag();
		tag.setDescription("blub");
		spec.addTagsItem(tag);

		String yaml = Yaml.pretty().writeValueAsString(spec);
		System.out.println(yaml);
	}

	public static void main(String[] args) throws JsonProcessingException {
		new OpenAPIGenerator().generator();
	}

}
