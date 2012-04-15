package sparser;

import java.util.ArrayList;
import java.util.List;

public class Code implements Entity {

	private List<Entity> program = new ArrayList<Entity>();

	public Entity execute(Scope scope) {
		Entity result = null;
		for(Entity entity : program) {
			result = entity.execute(scope);
		}
		return result;
	}

	public void appendEntity(Entity entity) {
		program.add(entity);
	}

	public List<Entity> getEntities() {
		return program;
	}
}
