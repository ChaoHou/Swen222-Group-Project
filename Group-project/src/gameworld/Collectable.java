package gameworld;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Collectable {
	public void toOutputStream(DataOutputStream dout) throws IOException;
}

