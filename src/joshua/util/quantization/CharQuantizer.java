package joshua.util.quantization;

import java.nio.ByteBuffer;

public class CharQuantizer extends StatelessQuantizer {

	public float read(ByteBuffer stream) {
		return (float) stream.getChar();
	}

	public void write(ByteBuffer stream, float value) {
		stream.putChar((char) value);
	}

	@Override
	public String getKey() {
		return "char";
	}	
}